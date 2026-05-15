package com.sahyadri.sentinel.data.repository

import com.sahyadri.sentinel.core.util.Resource
import com.sahyadri.sentinel.data.local.dao.ReportDao
import com.sahyadri.sentinel.data.local.entity.ReportEntity
import com.sahyadri.sentinel.domain.model.Report
import com.sahyadri.sentinel.domain.repository.ReportRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ReportRepositoryImpl @Inject constructor(
    private val reportDao: ReportDao
) : ReportRepository {

    override suspend fun submitReport(report: Report): Resource<Unit> {
        return try {
            reportDao.insertReport(ReportEntity.fromReport(report))
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Failed to save report locally")
        }
    }

    override fun getReports(): Flow<List<Report>> {
        return reportDao.getAllReports().map { entities ->
            entities.map { it.toReport() }
        }
    }
}
