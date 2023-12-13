package com.example.androidexam.ui.createquiz

import android.widget.Toast
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.androidexam.model.Category
import com.example.androidexam.model.Difficulty
import com.example.androidexam.model.Questions

/// Create a new game screen
/// This screen is used to select the category,
// amount of question and difficulty level.
/// When the user clicks on the start quiz button
@Composable
fun CreateGame(
    navController: NavHostController, viewModel:
    CreateQuizViewModel = viewModel(factory = CreateQuizViewModel.Factory)
) {
    var selectedCategory by remember { mutableStateOf(Category.GeneralKnowledge) }
    var selectedQuestions by remember { mutableStateOf(Questions.Five) }
    var selectedDifficulty by remember { mutableStateOf(Difficulty.Easy) }

    val quizApiState = viewModel.quizApiState

    LaunchedEffect(quizApiState) {
        if (quizApiState is QuizApiState.Success) {
            navController.navigate("QuizScreen")
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Create a new game",
            style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(50.dp))

        Text(
            text = "Select amount of question:",
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(8.dp))


        Dropdown(
            items = Questions.entries,
            selectedItem = selectedQuestions,
            onItemSelected = { selectedQuestions = it },
            labelName = { it.number.toString() }
        )
        Spacer(modifier = Modifier.height(50.dp))

        Text(
            text = "Select a category:",
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(8.dp))

        Dropdown(
            items = Category.entries,
            selectedItem = selectedCategory,
            onItemSelected = { selectedCategory = it },
            labelName = { it.displayName }
        )

        Spacer(modifier = Modifier.height(50.dp))

        Text(
            text = "Select a difficulty:",
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(8.dp))

        Dropdown(
            items = Difficulty.entries,
            selectedItem = selectedDifficulty,
            onItemSelected = { selectedDifficulty = it },
            labelName = { it.level }
        )

        Spacer(modifier = Modifier.height(50.dp))
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(onClick = { navController.navigateUp() })
            {
                Text("Back")
            }

            Spacer(modifier = Modifier.width(50.dp))

            Button(onClick = {
                viewModel.startQuiz(
                    selectedCategory,
                    selectedQuestions, selectedDifficulty
                )
            }) {
                Text("Start Quiz")
            }
        }
        when (quizApiState) {
            is QuizApiState.Idle -> {}
            is QuizApiState.Loading -> CircularProgressIndicator()
            is QuizApiState.Error -> {Toast.makeText(null, "Error creating quiz",
                Toast.LENGTH_LONG).show() }
            is QuizApiState.Success -> {}
        }

    }
}

/// Creates a dropdown list with a textfield and a dropdown menu.
/// The dropdown menu is displayed when the textfield is clicked.
/// It is type T in order to be able to use it for different types of data.
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> Dropdown(
    items: List<T>,
    selectedItem: T,
    onItemSelected: (T) -> Unit,
    labelName: (T) -> String
) {
    var isExpanded by remember { mutableStateOf(false) }
    val selectedItemLabel = labelName(selectedItem)

    ExposedDropdownMenuBox(
        expanded = isExpanded,
        onExpandedChange = { isExpanded = !isExpanded }
    ) {
        TextField(
            value = selectedItemLabel,
            onValueChange = {},
            readOnly = true,
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded) },
            colors = TextFieldDefaults.textFieldColors(
                containerColor = MaterialTheme.colorScheme.background,
                focusedTrailingIconColor = MaterialTheme.colorScheme.primary,
            ),
            modifier = Modifier
                .menuAnchor()
                .clickable { isExpanded = true }
        )

        DropdownMenu(
            expanded = isExpanded,
            onDismissRequest = { isExpanded = false }
        ) {
            items.forEach { item ->
                val itemLabel = labelName(item)
                DropdownMenuItem(
                    text = { Text(text = itemLabel) },
                    onClick = {
                        onItemSelected(item)
                        isExpanded = false
                    }
                )
            }
        }
    }
}