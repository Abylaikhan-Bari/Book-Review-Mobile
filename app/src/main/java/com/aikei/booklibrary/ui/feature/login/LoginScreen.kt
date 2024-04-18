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
fun LoginScreen(navController: NavController, token: String? = null) {
    val loginViewModel: LoginViewModel = hiltViewModel()
    val loginState by loginViewModel.loginState.observeAsState()
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    if (token != null) {
        loginViewModel.setToken(token)
    }

    when (loginState) {
        is LoginViewModel.LoginState.Success -> {
            // If login is successful, navigate to the book list and make sure to clear the login state
            LaunchedEffect(loginState) {
                navController.navigate("bookList")
                loginViewModel.clearLoginState() // You need to implement this to reset the login state
            }
        }
        is LoginViewModel.LoginState.Error -> {
            val errorMessage = (loginState as LoginViewModel.LoginState.Error).message
            // Show error message
        }
        else -> {

        }
        // Handle other states if necessary
    }

    Column(modifier = Modifier.padding(PaddingValues(16.dp))) {
        Text(text = "Login", style = MaterialTheme.typography.headlineSmall)
        OutlinedTextField(value = username, onValueChange = { username = it },modifier = Modifier.fillMaxWidth(), label = { Text("Username") })
        OutlinedTextField(value = password, onValueChange = { password = it }, modifier = Modifier.fillMaxWidth(), label = { Text("Password") })
        Button(onClick = {
            loginViewModel.login(username, password)
        },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Login")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = {
            // Navigate to registration screen
            navController.navigate("register")
        },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Go to Register")
        }
    }
}
