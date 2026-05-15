package com.sahyadri.sentinel.presentation.location

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sahyadri.sentinel.domain.location.LocationTracker
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocationViewModel @Inject constructor(
    private val locationTracker: LocationTracker
) : ViewModel() {

    private val _locationState = MutableStateFlow<LocationState>(LocationState.Idle)
    val locationState: StateFlow<LocationState> = _locationState.asStateFlow()

    fun fetchCurrentLocation() {
        viewModelScope.launch {
            _locationState.value = LocationState.Loading
            val location = locationTracker.getCurrentLocation()
            if (location != null) {
                _locationState.value = LocationState.Success(location)
            } else {
                _locationState.value = LocationState.Error("Could not fetch location. Ensure GPS is enabled and permissions granted.")
            }
        }
    }
}
