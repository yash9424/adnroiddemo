/*
 * Copyright 2023 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.platform.connectivity.audio

import android.Manifest
import android.media.AudioDeviceInfo
import android.media.AudioManager
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.content.getSystemService
import com.example.platform.connectivity.audio.datasource.PlatformAudioSource
import com.example.platform.connectivity.audio.viewmodel.AudioDeviceUI
import com.example.platform.connectivity.audio.viewmodel.AudioDeviceViewModel
import com.example.platform.connectivity.audio.viewmodel.getDeviceName
import com.example.platform.connectivity.audio.viewmodel.getStatusColor
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import com.google.android.catalog.framework.annotations.Sample

@Sample(
    name = "Audio Manager",
    description = "This sample will show you how get all audio sources and set an audio device. Covers Bluetooth, LEA, Wired and internal speakers",
    documentation = "https://developer.android.com/guide/topics/media-apps/media-apps-overview",
)
@OptIn(ExperimentalPermissionsApi::class)
@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun AudioSample() {
    val context = LocalContext.current
    val audioManager = context.getSystemService<AudioManager>()!!
    val viewModel = AudioDeviceViewModel(PlatformAudioSource(audioManager))

    val recordingAudioPermission = rememberPermissionState(
        Manifest.permission.RECORD_AUDIO,
    )

    if (recordingAudioPermission.status.isGranted) {
        AudioSampleScreen(viewModel)
    } else {
        PermissionWidget(recordingAudioPermission)
    }
}


@OptIn(ExperimentalPermissionsApi::class)
@Composable
private fun PermissionWidget(permissionsState: PermissionState) {
    var showRationale by remember(permissionsState) {
        mutableStateOf(false)
    }

    if (showRationale) {
        AlertDialog(
            onDismissRequest = { showRationale = false },
            title = {
                Text(text = "")
            },
            text = {
                Text(text = "")
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        permissionsState.launchPermissionRequest()
                    },
                ) {
                    Text("Continue")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showRationale = false
                    },
                ) {
                    Text("Dismiss")
                }
            },
        )
    }

    Button(
        onClick = {
            if (permissionsState.status.shouldShowRationale) {
                showRationale = true
            } else {
                permissionsState.launchPermissionRequest()
            }
        },
    ) {
        Text(text = "Grant Permission")
    }
}

@RequiresApi(Build.VERSION_CODES.S)
@Composable
private fun AudioSampleScreen(viewModel: AudioDeviceViewModel) {
    val context = LocalContext.current
    val uiStateAvailableDevices by viewModel.availableDeviceUiState.collectAsState()
    val uiStateActiveDevice by viewModel.activeDeviceUiState.collectAsState()
    val uiStateErrorMessage by viewModel.errorUiState.collectAsState()
    val uiStateRecording by viewModel.isRecording.collectAsState()

    if (uiStateErrorMessage != null) {
        LaunchedEffect(uiStateErrorMessage) {
            Toast.makeText(context, uiStateErrorMessage, Toast.LENGTH_LONG).show()
            viewModel.onErrorMessageShown()
        }
    }

    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {
        ActiveAudioSource(uiStateActiveDevice)
        Row {
            Button(onClick = { viewModel.onToggleAudioRecording() }) {
                val recordingText = if (uiStateRecording) {
                    "Stop Recording"
                } else {
                    "Start Recording"
                }
                Text(text = recordingText)
            }
        }
    }
    Text(
        text = stringResource(id = R.string.selectdevice),
        modifier = Modifier.padding(8.dp, 12.dp),
        style = MaterialTheme.typography.displayMedium,
    )
    AvailableDevicesList(uiStateAvailableDevices, viewModel::setAudioDevice)
}


@Composable
private fun AvailableDevicesList(
    audioDeviceWidgetUiState: AudioDeviceViewModel.AudioDeviceListUiState,
    onDeviceSelected: (AudioDeviceInfo) -> Unit,
) {
    when (audioDeviceWidgetUiState) {
        AudioDeviceViewModel.AudioDeviceListUiState.Loading -> {
            CircularProgressIndicator()
        }

        is AudioDeviceViewModel.AudioDeviceListUiState.Success -> {
            ListOfAudioDevices(audioDeviceWidgetUiState.audioDevices, onDeviceSelected)
        }
    }
}

@Composable
private fun ActiveAudioSource(activeAudioDeviceUiState: AudioDeviceViewModel.ActiveAudioDeviceUiState) =
    when (activeAudioDeviceUiState) {
        AudioDeviceViewModel.ActiveAudioDeviceUiState.NotActive -> {
            ActiveAudioSource(
                title = stringResource(id = R.string.nodevice),
                subTitle = "",
                resId = R.drawable.phone_icon,
            )
        }

        is AudioDeviceViewModel.ActiveAudioDeviceUiState.OnActiveDevice -> {
            ActiveAudioSource(
                title = stringResource(id = R.string.connected),
                subTitle = activeAudioDeviceUiState.audioDevice.getDeviceName(),
                resId = activeAudioDeviceUiState.audioDevice.resIconId,
            )
        }
    }

/**
 * Shows user the active audio source
 */
@Composable
private fun ActiveAudioSource(title: String, subTitle: String, resId: Int) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = 12.dp),
    ) {
        Icon(
            painterResource(resId),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
        )
        Column {
            Text(
                title, modifier = Modifier.padding(8.dp, 0.dp),
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.headlineMedium,
            )
            Text(
                subTitle, modifier = Modifier.padding(8.dp, 0.dp),
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.headlineSmall,
            )
        }
    }
}

/**
 * Build an list of Audio Devices we can connect to
 */
@Composable
private fun ListOfAudioDevices(
    devices: List<AudioDeviceUI>,
    onDeviceSelected: (AudioDeviceInfo) -> Unit,
) {
    LazyColumn {
        items(devices) { item ->
            AudioItem(audioDevice = item, onDeviceSelected = onDeviceSelected)
        }
    }
}

/**
 * Displays the audio device with Icon and Text
 */
@Composable
private fun AudioItem(
    audioDevice: AudioDeviceUI,
    onDeviceSelected: (AudioDeviceInfo) -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onDeviceSelected(audioDevice.audioDeviceInfo) },
    ) {
        Row(
            modifier = Modifier.padding(12.dp, 8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                painterResource(audioDevice.resIconId),
                contentDescription = null,
                tint = Color.White,
            )
            Text(
                text = audioDevice.getDeviceName(),
                color = audioDevice.getStatusColor(),
            )
        }
    }
}
