package widgets

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import kotlinx.coroutines.delay

@Composable
fun PopupNotification(showPopup: MutableState<Boolean>, text: String) {

    if (showPopup.value) {
        Dialog(onDismissRequest = { showPopup.value = false}) {
            Surface(
                shape = MaterialTheme.shapes.medium,
                elevation = 8.dp
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(text = text)
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(onClick = {
                        showPopup.value = false
                    }) {
                        Text("Закрыть")
                    }
                }
            }
        }

        LaunchedEffect(Unit) {
            delay(5000) // Задержка на 5 секунд
            showPopup.value = false
        }
    }
}