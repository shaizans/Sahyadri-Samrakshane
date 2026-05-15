package com.sahyadri.sentinel.presentation.camera

import android.net.Uri

sealed class CameraState {
    object Idle : CameraState()
    object Capturing : CameraState()
    data class Success(val imageUri: Uri) : CameraState()
    data class Error(val message: String) : CameraState()
}
