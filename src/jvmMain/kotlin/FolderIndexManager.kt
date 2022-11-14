import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.*
import java.io.File
import java.util.function.Function
import java.util.regex.Pattern
import java.util.stream.Collectors

class FolderIndexManager {

    var index: Map<String, Map<String, Long>> by mutableStateOf(mapOf())
    var folder by mutableStateOf<String?>(null)

    private var lastQuery: String = ""
    var queried: Map<String, Long> by mutableStateOf(mapOf())

    private var coroutineScope = CoroutineScope(Dispatchers.Main)

    private fun validateQuery(input: String) =
        Pattern.compile("^([a-zA-z]+, )*[a-zA-z]*").matcher(input).matches()

    private fun parseQuery(input: String) = input.split(Pattern.compile(", "))

    fun search(query: String) {
        if(query == "") {
            lastQuery = ""
            queried = mapOf()
        } else if(validateQuery(query)){
            lastQuery = query
            val newQueried = mutableMapOf<String, Long>()
            val queriedList = parseQuery(query)
            for(word in queriedList) {
                var result = 0L
                for(fileName in index.keys) {
                    result += index[fileName]?.get(word) ?: 0
                }
                newQueried[word] = result
            }
            queried = newQueried
        }
    }

    fun reset() {
        coroutineScope.cancel()
        coroutineScope = CoroutineScope(Dispatchers.Main)
        index = mapOf()
        folder = null
    }

    fun setUpFolder(name: String) {
        check(File(name).isDirectory) { return }
        folder = name
        startCoroutineProcess()
    }

    private fun parseString(input: String): List<String> {
        val result = mutableListOf<String>()
        var newLine = ""
        for(ch in input) {
            if((ch in 'a'..'z') || (ch in 'A'..'Z')) {
                newLine += ch
            } else if(newLine != "") {
                result.add(newLine)
                newLine = ""
            }
        }
        return result
    }

    private fun buildIndex() {
        val resultMap = mutableMapOf<String, Map<String, Long>>()
        File(folder!!).walk().forEach {file ->
            val listOfFileWords = mutableListOf<String>()
            if(!file.isDirectory){
                file.readLines().forEach {
                    listOfFileWords.addAll(parseString(it))
                }
                resultMap[file.name] =
                    listOfFileWords.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
            }
        }
        index = resultMap
    }

    private fun startCoroutineProcess(){
        coroutineScope.launch {
            while (true) {
                delay(5000L)
                buildIndex()
                search(lastQuery)
            }
        }
    }

}