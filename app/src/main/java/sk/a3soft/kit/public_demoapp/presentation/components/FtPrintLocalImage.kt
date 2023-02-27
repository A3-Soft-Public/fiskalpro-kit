@file:OptIn(ExperimentalPermissionsApi::class)

package sk.a3soft.kit.public_demoapp.presentation.components

import android.Manifest
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState

@Composable
fun FtPrintLocalImage(
    onClick: () -> Unit,
) {
    val storagePermissionState = rememberPermissionState(Manifest.permission.WRITE_EXTERNAL_STORAGE)

    Column {
        if (storagePermissionState.status.isGranted) {
            Button(
                onClick = onClick
            ) {
                Text("Send")
            }
        } else {
            Button(
                onClick = { storagePermissionState.launchPermissionRequest() }
            ) {
                Text("Request storage permission")
            }
        }
    }
}