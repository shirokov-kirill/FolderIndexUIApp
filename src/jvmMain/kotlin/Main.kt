// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
import androidx.compose.material.MaterialTheme
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import pages.inputFolderNameScreen
import pages.statisticsScreen

@Composable
@Preview
fun App() {
    MaterialTheme {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            val indexManager = remember { FolderIndexManager() }
            // uncomment the next line to see the produced Index
            // println(indexManager.index)
            println(indexManager.queried)
            if(indexManager.folder == null) {
                inputFolderNameScreen(
                    onSubmitClicked = indexManager::setUpFolder
                )
            } else {
                statisticsScreen(
                    displayData = indexManager.queried,
                    handleSearch = indexManager::search,
                    onBackClicked = indexManager::reset
                )
            }
        }
    }
}

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Folder Index Manager"
    ) {
        App()
    }
}
