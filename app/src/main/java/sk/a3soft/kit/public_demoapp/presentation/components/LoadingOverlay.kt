package sk.a3soft.kit.public_demoapp.presentation.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import sk.a3soft.kit.tool.common.doNothing

@Composable
fun LoadingOverlay(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit = doNothing,
) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = modifier,
            shape = CircleShape,
        ) {
            CircularProgressIndicator(
                modifier = Modifier.padding(4.dp),
                color = MaterialTheme.colorScheme.primary,
            )
        }
    }
}
