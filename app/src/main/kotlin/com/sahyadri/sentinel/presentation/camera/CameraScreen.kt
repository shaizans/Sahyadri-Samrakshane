package com.sahyadri.sentinel.presentation.camera

import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CameraScreen(
    onImageCaptured: (android.net.Uri) -> Unit,
    viewModel: CameraViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val cameraPermissionState = rememberPermissionState(android.Manifest.permission.CAMERA)
    
    val cameraState by viewModel.cameraState.collectAsState()

    val imageCapture = remember { ImageCapture.Builder().build() }
    val previewView = remember { PreviewView(context) }

    LaunchedEffect(cameraPermissionState.status.isGranted) {
        if (cameraPermissionState.status.isGranted) {
            val cameraProviderFuture = ProcessCameraProvider.getInstance(context)
            cameraProviderFuture.addListener({
                val cameraProvider = cameraProviderFuture.get()
                val preview = Preview.Builder().build().also {
                    it.setSurfaceProvider(previewView.surfaceProvider)
                }

                try {
                    cameraProvider.unbindAll()
                    cameraProvider.bindToLifecycle(
                        lifecycleOwner,
                        CameraSelector.DEFAULT_BACK_CAMERA,
                        preview,
                        imageCapture
                    )
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }, context.mainExecutor)
        } else {
            cameraPermissionState.launchPermissionRequest()
        }
    }

    LaunchedEffect(cameraState) {
        if (cameraState is CameraState.Success) {
            onImageCaptured((cameraState as CameraState.Success).imageUri)
            viewModel.resetState()
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        if (cameraPermissionState.status.isGranted) {
            AndroidView(
                factory = { previewView },
                modifier = Modifier.fillMaxSize()
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 32.dp),
                contentAlignment = Alignment.BottomCenter
            ) {
                if (cameraState is CameraState.Capturing) {
                    CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
                } else {
                    FloatingActionButton(
                        onClick = { viewModel.captureImage(imageCapture, context) },
                        containerColor = MaterialTheme.colorScheme.primary
                    ) {
                        Icon(Icons.Default.PhotoCamera, contentDescription = "Capture")
                    }
                }
            }
        } else {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text("Camera permission is required")
                Button(onClick = { cameraPermissionState.launchPermissionRequest() }) {
                    Text("Request Permission")
                }
            }
        }
        
        if (cameraState is CameraState.Error) {
            Snackbar {
                Text((cameraState as CameraState.Error).message)
            }
        }
    }
}
