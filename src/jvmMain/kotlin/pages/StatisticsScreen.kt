package pages

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
@Preview
fun myCard(
    word: String,
    count: Long
){
    Card(modifier = Modifier
        .padding(10.dp)
        .fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(10.dp)) {
            Text(word, fontSize = 25.sp, fontWeight = FontWeight.W700, modifier = Modifier.padding(10.dp))
            Text(count.toString(), color = Color.Gray, modifier = Modifier.padding(10.dp))
        }
    }
}

@Composable
@Preview
fun statisticsScreen(
    displayData: Map<String, Long>,
    handleSearch: (String) -> Unit,
    onBackClicked: () -> Unit
){
    var query by remember { mutableStateOf("") }
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(Modifier.weight(1F).padding(10.dp, 5.dp)) {
            Button(
                onClick = onBackClicked
            ){
                Text("Back")
            }
            Spacer(Modifier.width(5.dp))
            Box(
                modifier = Modifier.width(260.dp)
            ){
                TextField(
                    value = query,
                    onValueChange = { query = it },
                    label = {
                        Text("Query")
                    },
                    visualTransformation = VisualTransformation.None
                )
            }
            Spacer(Modifier.width(5.dp))
            Button(
                onClick = { handleSearch(query) }
            ) {
                Text("Search")
            }
        }
        Row(Modifier.weight(7F)) {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .verticalScroll(rememberScrollState())
            ) {
                for((word, count) in displayData) {
                    myCard(
                        word = word,
                        count = count
                    )
                }
            }
        }
    }
}
