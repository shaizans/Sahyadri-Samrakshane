package com.sahyadri.sentinel.presentation.report

import androidx.lifecycle.ViewModel
import com.sahyadri.sentinel.domain.repository.ReportRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ReportListViewModel @Inject constructor(
    private val reportRepository: ReportRepository
) : ViewModel() {
    val reports = reportRepository.getReports()
}
