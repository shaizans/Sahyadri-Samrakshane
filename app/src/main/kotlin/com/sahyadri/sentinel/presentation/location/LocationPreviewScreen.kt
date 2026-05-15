package com.sahyadri.sentinel.presentation.location

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun LocationPreviewScreen(
    viewModel: LocationViewModel = hiltViewModel()
) {
    val locationState by viewModel.locationState.collectAsState()
    val permissionState = rememberMultiplePermissionsState(
        permissions = listOf(
            android.Manifest.permission.ACCESS_FINE_LOCATION,
            android.Manifest.permission.ACCESS_COARSE_LOCATION
        )
    )

    LaunchedEffect(permissionState.allPermissionsGranted) {
        if (permissionState.allPermissionsGranted) {
            viewModel.fetchCurrentLocation()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (!permissionState.allPermissionsGranted) {
            Text("Location permission is needed to tag reports accurately.")
            Button(onClick = { permissionState.launchMultiplePermissionRequest() }) {
                Text("Grant Permissions")
            }
        } else {
            when (val state = locationState) {
                is LocationState.Loading -> CircularProgressIndicator()
                is LocationState.Success -> {
                    val latLng = LatLng(state.location.latitude, state.location.longitude)
                    Text("Coordinates: ${state.location.latitude}, ${state.location.longitude}")
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    val cameraPositionState = rememberCameraPositionState {
                        position = CameraPosition.fromLatLngZoom(latLng, 15f)
                    }

                    GoogleMap(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .padding(vertical = 8.dp),
                        cameraPositionState = cameraPositionState
                    ) {
                        Marker(
                            state = MarkerState(position = latLng),
                            title = "Your Location"
                        )
                    }
                }
                is LocationState.Error -> {
                    Text(text = state.message, color = MaterialTheme.colorScheme.error)
                    Button(onClick = { viewModel.fetchCurrentLocation() }) {
                        Text("Retry")
                    }
                }
                else -> {
                    Button(onClick = { viewModel.fetchCurrentLocation() }) {
                        Text("Fetch GPS Coordinates")
                    }
                }
            }
        }
    }
}
