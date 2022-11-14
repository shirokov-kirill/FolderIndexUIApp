package pages

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
@Preview
fun inputFolderNameScreen(
    onSubmitClicked: (String) -> Unit
) {
    Column(Modifier.padding(30.dp, 5.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        var text by remember { mutableStateOf("") }
        TextField(
            value = text,
            onValueChange = { text = it },
            label = {
                Text("Folder name")
            }
        )
        Button(onClick = { onSubmitClicked(text) }) {
            Text("Submit")
        }
    }
}