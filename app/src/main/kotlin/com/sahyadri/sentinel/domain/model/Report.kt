package com.sahyadri.sentinel.domain.model

data class Report(
    val id: Int = 0,
    val userId: String,
    val alertType: String,
    val description: String,
    val imageUri: String,
    val latitude: Double,
    val longitude: Double,
    val timestamp: Long,
    val status: String = "Reported", // Reported, Verified, Team Dispatched
    val isSynced: Boolean = false
)
