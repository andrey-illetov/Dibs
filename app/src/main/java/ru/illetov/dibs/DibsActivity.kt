package ru.illetov.dibs

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DibsActivity : ComponentActivity() {
    private val state: MutableStateFlow<UiState> = MutableStateFlow(
        UiState.Loading
    )
    private val viewModel: DibsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()

        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel
                    .state
                    .onEach { state.value = it }
                    .collect()
            }
        }
        splashScreen.setKeepOnScreenCondition {
            when(state.value) {
                UiState.Loading -> true
                is UiState.Success -> false
                is UiState.Error -> false
            }
        }
//        setContent {
//
//        }
    }
}
