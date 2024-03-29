package sk.a3soft.kit.public_demoapp.presentation.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun Title(text: String) {
    Text(
        modifier = Modifier.padding(top = 16.dp, bottom = 8.dp, start = 8.dp, end = 8.dp),
        text = text,
        style = MaterialTheme.typography.headlineSmall,
        textAlign = TextAlign.Center,
    )
}
