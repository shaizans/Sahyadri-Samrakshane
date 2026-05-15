package com.sahyadri.sentinel.presentation.report.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.sahyadri.sentinel.presentation.theme.ForestGreen

@Composable
fun StatusTimeline(
    currentStatus: String,
    modifier: Modifier = Modifier
) {
    val statuses = listOf("Reported", "Verified", "Team Dispatched")
    val currentIndex = statuses.indexOf(currentStatus).coerceAtLeast(0)

    Column(modifier = modifier.fillMaxWidth()) {
        statuses.forEachIndexed { index, status ->
            val isCompleted = index <= currentIndex
            val color by animateColorAsState(
                targetValue = if (isCompleted) ForestGreen else Color.Gray,
                label = "StatusColor"
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(vertical = 4.dp)
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Surface(
                        modifier = Modifier.size(24.dp),
                        shape = CircleShape,
                        color = color
                    ) {
                        if (isCompleted) {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.padding(4.dp)
                            )
                        }
                    }
                    if (index < statuses.size - 1) {
                        Box(
                            modifier = Modifier
                                .width(2.dp)
                                .height(32.dp)
                                .background(if (index < currentIndex) ForestGreen else Color.Gray)
                        )
                    }
                }

                Spacer(modifier = Modifier.width(16.dp))

                Text(
                    text = status,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = if (index == currentIndex) FontWeight.Bold else FontWeight.Normal,
                    color = if (index == currentIndex) MaterialTheme.colorScheme.primary else Color.Gray
                )
            }
        }
    }
}
