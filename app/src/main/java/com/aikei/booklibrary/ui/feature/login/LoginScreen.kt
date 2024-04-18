import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.aikei.booklibrary.ui.feature.login.LoginViewModel

@Composable
fun LoginScreen(navController: NavController) {
    val loginViewModel: LoginViewModel = hiltViewModel()
    val loginState by loginViewModel.loginState.observeAsState()
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    when (val state = loginState) {
        is LoginViewModel.LoginState.Success -> {
            LaunchedEffect(state) {
                // Navigate to bookList and pass the token
                navController.navigate("bookList/${state.token}")
            }
        }
        is LoginViewModel.LoginState.Error -> {
            // Show error message if needed, assuming you have some text to show it
            Text("Error: ${state.message}", color = MaterialTheme.colorScheme.error)
        }
        is LoginViewModel.LoginState.Loading -> {
            // Optionally show loading indicator
            Text("Loading...", style = MaterialTheme.typography.bodyMedium)
        }
        null -> {
            // No action needed or you can show default UI
        }
    }

    Column(modifier = Modifier.padding(PaddingValues(16.dp))) {
        Text(text = "Login", style = MaterialTheme.typography.headlineSmall)
        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Username") }
        )
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Password") }
        )
        Button(
            onClick = { loginViewModel.login(username, password) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Login")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = { navController.navigate("register") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Go to Register")
        }
    }
}
