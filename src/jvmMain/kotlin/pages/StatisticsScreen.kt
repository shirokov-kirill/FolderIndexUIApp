package pages

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
@Preview
fun statisticsScreen(
    handleSearch: (String) -> Unit,
    onBackClicked: () -> Unit
){
    var query by remember { mutableStateOf("") }
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(Modifier.weight(1F)) {
            Button(
                onClick = onBackClicked
            ){
                Text("Back")
            }
            Spacer(Modifier.width(5.dp))
            TextField(
                value = query,
                onValueChange = { query = it },
                label = {
                    Text("Query")
                }
            )
            Spacer(Modifier.width(5.dp))
            Button(
                onClick = { handleSearch(query) }
            ) {
                Text("Search")
            }
        }
        Row(Modifier.weight(7F)) {
            TODO()
        }
    }
}
