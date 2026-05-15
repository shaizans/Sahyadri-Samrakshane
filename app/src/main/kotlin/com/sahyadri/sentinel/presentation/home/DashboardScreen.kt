package com.sahyadri.sentinel.presentation.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.sahyadri.sentinel.domain.model.AlertCategory
import com.sahyadri.sentinel.presentation.home.components.CategoryCard
import com.sahyadri.sentinel.presentation.theme.ForestGreen
import com.sahyadri.sentinel.presentation.theme.WarningRed

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    onCategoryClick: (AlertCategory) -> Unit
) {
    val categories = listOf(
        AlertCategory(
            "fire", "Forest Fire", "Report active wildfires or smoke", 
            Icons.Default.Whatshot, WarningRed
        ),
        AlertCategory(
            "logging", "Illegal Logging", "Report unauthorized tree cutting", 
            Icons.Default.FilterHdr, ForestGreen
        ),
        AlertCategory(
            "landslide", "Landslide", "Report soil erosion or road blocks", 
            Icons.Default.Terrain, Color(0xFF795548)
        ),
        AlertCategory(
            "wildlife", "Wildlife Sighting", "Log rare animals or conflicts", 
            Icons.Default.Pets, Color(0xFFFF9800)
        )
    )

    Scaffold(
        topBar = {
            LargeTopAppBar(
                title = { Text("Forest Sentinel") },
                colors = TopAppBarDefaults.largeTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(bottom = 16.dp)
        ) {
            item {
                Text(
                    text = "What would you like to report?",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }
            items(categories) { category ->
                CategoryCard(
                    category = category,
                    onClick = { onCategoryClick(category) }
                )
            }
        }
    }
}
