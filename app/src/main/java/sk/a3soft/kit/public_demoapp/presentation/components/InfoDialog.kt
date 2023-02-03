package sk.a3soft.kit.public_demoapp.presentation.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable

@Composable
fun InfoDialog(
    message: String,
    onClose: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = onClose,
        title = {
            Text(text = "Info")
        },
        text = {
            Text(text = message)
        },
        confirmButton = {
            TextButton(onClick = onClose) {
                Text(text = "Close")
            }
        },
    )
}
