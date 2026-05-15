package com.sahyadri.sentinel.domain.repository

import com.sahyadri.sentinel.core.util.Resource
import com.sahyadri.sentinel.domain.model.Report
import kotlinx.coroutines.flow.Flow

interface ReportRepository {
    suspend fun submitReport(report: Report): Resource<Unit>
    fun getReports(): Flow<List<Report>>
    suspend fun syncReports(): Resource<Unit>
}
