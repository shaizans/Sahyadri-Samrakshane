package com.sahyadri.sentinel.presentation.report

import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sahyadri.sentinel.core.util.Resource
import com.sahyadri.sentinel.domain.location.LocationTracker
import com.sahyadri.sentinel.domain.model.Report
import com.sahyadri.sentinel.domain.repository.AuthRepository
import com.sahyadri.sentinel.domain.repository.ReportRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReportViewModel @Inject constructor(
    private val reportRepository: ReportRepository,
    private val authRepository: AuthRepository,
    private val locationTracker: LocationTracker,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val categoryId: String = savedStateHandle["categoryId"] ?: "General"
    private val imageUriStr: String? = savedStateHandle["imageUri"]

    private val _reportState = MutableStateFlow<ReportFormState>(ReportFormState.Idle)
    val reportState: StateFlow<ReportFormState> = _reportState.asStateFlow()

    fun submitReport(description: String) {
        viewModelScope.launch {
            _reportState.value = ReportFormState.Loading
            
            val location = locationTracker.getCurrentLocation()
            if (location == null) {
                _reportState.value = ReportFormState.Error("GPS location required for reports.")
                return@launch
            }

            val user = authRepository.getCurrentUser()
            val report = Report(
                userId = user?.uid ?: "Anonymous",
                alertType = categoryId,
                description = description,
                imageUri = imageUriStr ?: "",
                latitude = location.latitude,
                longitude = location.longitude,
                timestamp = System.currentTimeMillis()
            )

            when (val result = reportRepository.submitReport(report)) {
                is Resource.Success -> _reportState.value = ReportFormState.Success
                is Resource.Error -> _reportState.value = ReportFormState.Error(result.message!!)
                else -> {}
            }
        }
    }
}

sealed class ReportFormState {
    object Idle : ReportFormState()
    object Loading : ReportFormState()
    object Success : ReportFormState()
    data class Error(val message: String) : ReportFormState()
}
