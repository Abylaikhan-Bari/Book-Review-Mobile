package com.aikei.booklibrary.ui.common

import LoginScreen
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.aikei.booklibrary.ui.feature.addbook.AddBookScreen
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

                composable("login") {
                    LoginScreen(navController)
                    BackHandler(true) {
                        // Or do nothing
                        Log.i("LOG_TAG", "Clicked back")
                    }
                }
                composable("register") {
                    RegistrationScreen(navController)
                    BackHandler(true) {
                        // Or do nothing
                        Log.i("LOG_TAG", "Clicked back")
                    }
                }
                composable("bookList/{token}", arguments = listOf(navArgument("token") { type = NavType.StringType })) { backStackEntry ->
                    val token = backStackEntry.arguments?.getString("token") ?: ""
                    BookListScreen(navController, token)
                    BackHandler(true) {
                        // Or do nothing
                        Log.i("LOG_TAG", "Clicked back")
                    }
                }
                composable(
                    "bookDetail/{token}/{bookId}",
                    arguments = listOf(
                        navArgument("token") { type = NavType.StringType },
                        navArgument("bookId") { type = NavType.StringType }
                    )

                ) { backStackEntry ->
                    val token = backStackEntry.arguments?.getString("token") ?: ""
                    val bookId = backStackEntry.arguments?.getString("bookId") ?: ""
                    BookDetailScreen(navController, token, bookId)
                    BackHandler(true) {
                        // Or do nothing
                        Log.i("LOG_TAG", "Clicked back")
                    }
                }
                composable("addBook/{token}") { backStackEntry ->
                    val token = backStackEntry.arguments?.getString("token") ?: ""
                    AddBookScreen(navController, token)
                    BackHandler(true) {
                        navController.navigateUp()
                        Log.i("LOG_TAG", "Clicked back")
                    }
                }
                composable("settings") {
                    SettingsScreen(navController)
                    BackHandler(true) {
                        // Or do nothing
                        Log.i("LOG_TAG", "Clicked back")
                    }
                }
            }
        }
    }
}

