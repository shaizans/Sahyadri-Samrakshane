package com.sahyadri.sentinel.data.repository

import android.content.Context
import android.net.Uri
import androidx.work.*
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.sahyadri.sentinel.core.util.Resource
import com.sahyadri.sentinel.data.local.dao.ReportDao
import com.sahyadri.sentinel.data.local.entity.ReportEntity
import com.sahyadri.sentinel.data.worker.SyncWorker
import com.sahyadri.sentinel.domain.model.Report
import com.sahyadri.sentinel.domain.repository.ReportRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ReportRepositoryImpl @Inject constructor(
    private val reportDao: ReportDao,
    private val firestore: FirebaseFirestore,
    private val storage: FirebaseStorage,
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
        return try {
            val unsyncedReports = reportDao.getUnsyncedReports()
            unsyncedReports.forEach { reportEntity ->
                val report = reportEntity.toReport()
                val remoteImageUrl = if (report.imageUri.isNotEmpty() && !report.imageUri.startsWith("http")) {
                    uploadImage(Uri.parse(report.imageUri))
                } else {
                    report.imageUri
                }

                val remoteReport = report.copy(imageUri = remoteImageUrl, isSynced = true)
                
                firestore.collection("reports")
                    .document() // Use auto ID or hash
                    .set(remoteReport)
                    .await()

                reportDao.updateReport(reportEntity.copy(isSynced = true, imageUri = remoteImageUrl))
            }
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Sync failed")
        }
    }

    private suspend fun uploadImage(uri: Uri): String {
        val fileName = "reports/${System.currentTimeMillis()}.jpg"
        val ref = storage.reference.child(fileName)
        ref.putFile(uri).await()
        return ref.downloadUrl.await().toString()
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
