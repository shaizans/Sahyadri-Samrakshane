package com.sahyadri.sentinel.presentation.theme

import androidx.lifecycle.ViewModel
import com.sahyadri.sentinel.domain.theme.ThemeSettings
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class ThemeViewModel @Inject constructor(
    private val themeSettings: ThemeSettings
) : ViewModel() {
    val isDarkMode: StateFlow<Boolean?> = themeSettings.isDarkMode

    fun toggleDarkMode(currentSystemDark: Boolean) {
        val current = isDarkMode.value ?: currentSystemDark
        themeSettings.setDarkMode(!current)
    }

    fun setDarkMode(enabled: Boolean) {
        themeSettings.setDarkMode(enabled)
    }
}
