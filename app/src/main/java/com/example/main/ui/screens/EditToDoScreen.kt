package com.example.main.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.main.R
import com.example.main.model.MainViewModel



@Composable
fun EditToDoScreen(
    viewModel: MainViewModel,
    navController: NavController
) {

    val state by viewModel.state.collectAsState()
    val todo = state.currentToDo

    // State Variablen für die OutlinedTextFields
    // vorbelegen mit Infos aus currentToDo
    // werden nur lokal und temporär benötigt
    var title by remember { mutableStateOf(todo.title) }
    var subject by remember { mutableStateOf(todo.subject) }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(30.dp)
            .padding(top = 100.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = title,
            onValueChange = { title = it  },
            label = { Text(stringResource(id = R.string.enterTitleLabel)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )
        OutlinedTextField(
            value = subject,
            onValueChange = { subject = it },
            label = { Text(stringResource(id = R.string.enterSubjectLabel)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Dismiss Button; gehe zurück zum MainScreen ohne irgendeine Änderung
            Button(
                onClick = { navController.popBackStack() },
                modifier = Modifier.weight(1f)
            ) {
                Text(stringResource(id = R.string.dismissButton))
            }
            Spacer(modifier = Modifier.width(16.dp))
            // Confirm Button; Füge editiertes ToDo der Liste hinzu. Übernimm die ID
            Button(
                onClick = {
                    viewModel.addToList(todo.copy(title = title, subject = subject))
                    navController.popBackStack()
                },
                modifier = Modifier.weight(1f)
            ) {
                Text(stringResource(id = R.string.confirmButton))
            }
        }
    }
}