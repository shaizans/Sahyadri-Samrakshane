package com.sahyadri.sentinel.data.repository

import android.content.Context
import androidx.work.*
import com.sahyadri.sentinel.core.util.Resource
import com.sahyadri.sentinel.data.local.dao.ReportDao
import com.sahyadri.sentinel.data.local.entity.ReportEntity
import com.sahyadri.sentinel.data.worker.SyncWorker
import com.sahyadri.sentinel.domain.model.Report
import com.sahyadri.sentinel.domain.repository.ReportRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ReportRepositoryImpl @Inject constructor(
    private val reportDao: ReportDao,
    @ApplicationContext private val context: Context
) : ReportRepository {

    override suspend fun submitReport(report: Report): Resource<Unit> {
        return try {
            reportDao.insertReport(ReportEntity.fromReport(report))
            scheduleSync()
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

    override suspend fun syncReports(): Resource<Unit> {
        // This will be expanded in Phase 8 with Firebase logic
        return Resource.Success(Unit)
    }

    private fun scheduleSync() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val syncRequest = OneTimeWorkRequestBuilder<SyncWorker>()
            .setConstraints(constraints)
            .setBackoffCriteria(BackoffPolicy.EXPONENTIAL, WorkRequest.MIN_BACKOFF_MILLIS, java.util.concurrent.TimeUnit.MILLISECONDS)
            .build()

        WorkManager.getInstance(context).enqueueUniqueWork(
            "report_sync",
            ExistingWorkPolicy.REPLACE,
            syncRequest
        )
    }
}
