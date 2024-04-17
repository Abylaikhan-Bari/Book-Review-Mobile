import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.aikei.booklibrary.ui.feature.login.LoginViewModel

@Composable
fun LoginScreen(navController: NavController) {
    val loginViewModel: LoginViewModel = hiltViewModel()
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column {
        TextField(value = username, onValueChange = { username = it }, label = { Text("Username") })
        TextField(value = password, onValueChange = { password = it }, label = { Text("Password") })
        Button(onClick = {
            loginViewModel.login(username, password)
            // Navigate upon successful login
            navController.navigate("bookList")
        }) {
            Text("Login")
        }
    }
}

