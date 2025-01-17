package io.aayush.relabs.ui.screens.reply

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import io.aayush.relabs.R
import io.aayush.relabs.network.data.post.PostReply
import io.aayush.relabs.ui.navigation.Screen
import io.aayush.relabs.ui.screens.thread.ThreadViewModel
import io.aayush.relabs.ui.theme.XDAYellow

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun ReplyScreen(
    navHostController: NavHostController,
    threadID: Int,
    viewModel: ThreadViewModel = hiltViewModel()
) {
    var text by remember { mutableStateOf("") }
    var posting by remember { mutableStateOf(false) }

    val postReply: PostReply? by viewModel.postReply.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = postReply) {
        if (postReply != null && postReply!!.success) {
            navHostController.navigateUp()
        } else {
            posting = false
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = Screen.Reply.title)) },
                navigationIcon = {
                    IconButton(onClick = { navHostController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = ""
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                text = { Text(text = stringResource(id = Screen.Reply.title)) },
                icon = {
                    if (posting) {
                        CircularProgressIndicator()
                    } else {
                        Image(
                            painter = painterResource(id = Screen.Reply.icon),
                            contentDescription = ""
                        )
                    }
                },
                onClick = {
                    if (!posting && text.isNotBlank()) {
                        posting = true
                        viewModel.postReply(threadID, text)
                    }
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.Top)
        ) {
            OutlinedButton(
                onClick = { navHostController.navigate(Screen.Thread.withID(4200559)) },
                shape = RoundedCornerShape(10.dp),
                border = BorderStroke(1.dp, XDAYellow),
                modifier = Modifier.padding(horizontal = 10.dp)
            ) {
                Text(text = stringResource(id = R.string.follow_rules))
            }
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.5f)
                    .padding(horizontal = 15.dp),
                value = text,
                onValueChange = { newText -> text = newText },
                enabled = !posting,
                shape = RoundedCornerShape(10.dp)
            )
        }
    }
}
