package com.sahyadri.sentinel.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sahyadri.sentinel.domain.model.Report

@Entity(tableName = "reports")
data class ReportEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val userId: String,
    val alertType: String,
    val description: String,
    val imageUri: String,
    val latitude: Double,
    val longitude: Double,
    val timestamp: Long,
    val status: String,
    val isSynced: Boolean
) {
    fun toReport(): Report = Report(
        id = id,
        userId = userId,
        alertType = alertType,
        description = description,
        imageUri = imageUri,
        latitude = latitude,
        longitude = longitude,
        timestamp = timestamp,
        status = status,
        isSynced = isSynced
    )

    companion object {
        fun fromReport(report: Report): ReportEntity = ReportEntity(
            id = report.id,
            userId = report.userId,
            alertType = report.alertType,
            description = report.description,
            imageUri = report.imageUri,
            latitude = report.latitude,
            longitude = report.longitude,
            timestamp = report.timestamp,
            status = report.status,
            isSynced = report.isSynced
        )
    }
}
