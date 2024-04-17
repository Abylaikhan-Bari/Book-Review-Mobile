package com.aikei.booklibrary.ui

import LoginScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.aikei.booklibrary.ui.feature.bookdetail.BookDetailScreen
import com.aikei.booklibrary.ui.feature.booklist.BookListScreen
import com.aikei.booklibrary.ui.feature.register.RegistrationScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BookLibraryApp()
        }
    }
}

@Composable
fun BookLibraryApp() {
    val navController = rememberNavController()
    MaterialTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            NavHost(navController = navController, startDestination = "login") {
                composable("login") { LoginScreen(navController) }
                composable("register") { RegistrationScreen(navController) }
                composable("bookList") { BookListScreen(navController) }
                composable(
                    "bookDetail/{token}/{bookId}",
                    arguments = listOf(
                        navArgument("token") { type = NavType.StringType },
                        navArgument("bookId") { type = NavType.StringType }
                    )
                ) { backStackEntry ->
                    val token = backStackEntry.arguments?.getString("token") ?: ""
                    val bookId = backStackEntry.arguments?.getString("bookId") ?: ""
                    BookDetailScreen(token, bookId)
                }
            }
        }
    }
}
