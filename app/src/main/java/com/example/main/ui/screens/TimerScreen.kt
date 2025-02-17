package com.example.main.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.main.R
import com.example.main.model.MainViewModel
import com.example.main.model.ToDoItem
import com.example.main.model.secondsToDayHourMinSecString
import com.example.main.model.timeDiffInSeconds
import kotlinx.coroutines.delay

@Composable
fun TimerScreen(
    viewModel: MainViewModel,
    navController: NavController
) {

    val state by viewModel.state.collectAsState()
    val todo = state.selectedToDo ?: ToDoItem()
    val timediff = timeDiffInSeconds(todo.dateString, todo.timeString)

    var remainingTime by remember { mutableStateOf(timediff) }
    LaunchedEffect(true) {
        while (remainingTime >  0) {
            delay(1000) // Delay for  1 second
            remainingTime--
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.padding(16.dp),
            text = todo.title,
            style = MaterialTheme.typography.headlineLarge
        )

        Spacer(modifier = Modifier.height(30.dp))

        ElevatedCard(
            modifier = Modifier.padding(40.dp),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 20.dp)
        ) {
            Text(
                modifier = Modifier.padding(40.dp),
                text = when {
                    todo.title.isEmpty() -> stringResource(id = R.string.noToDoSelected)
                    remainingTime > 0 -> secondsToDayHourMinSecString(remainingTime)
                    else -> stringResource(id = R.string.timeOver)
                },
                style = MaterialTheme.typography.headlineLarge
            )
        }
    }}