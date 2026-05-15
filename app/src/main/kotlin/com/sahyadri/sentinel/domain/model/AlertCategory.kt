package com.sahyadri.sentinel.domain.model

import androidx.compose.ui.graphics.vector.ImageVector

data class AlertCategory(
    val id: String,
    val title: String,
    val description: String,
    val icon: ImageVector,
    val color: androidx.compose.ui.graphics.Color
)
