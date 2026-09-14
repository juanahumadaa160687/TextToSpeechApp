package com.textspech.texttospeechapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.textspech.texttospeechapp.screens.ChangePasswordScreen
import com.textspech.texttospeechapp.screens.IndexScreen
import com.textspech.texttospeechapp.screens.PasswordRecoveryScreen
import com.textspech.texttospeechapp.screens.SignInScreen
import com.textspech.texttospeechapp.screens.SignUpScreen
import com.textspech.texttospeechapp.screens.SpeechToTextScreen
import com.textspech.texttospeechapp.screens.TextToSpeechScreen

@Composable
fun AppNavigation(){


    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "index") {

        composable("index") {
            IndexScreen(navController = navController)
        }

        composable("sign-in") {
            SignInScreen(navController = navController)
        }

        composable("sign-up") {
            SignUpScreen(navController = navController)
        }

        composable("password-recovery") {
            PasswordRecoveryScreen(navController = navController)
        }

        composable("change-password?user={email}",
            arguments = listOf(navArgument("email")
            {   type = NavType.StringType
                defaultValue = ""
                nullable = true
            })
        ) {backStackEntry ->
            val email = backStackEntry.arguments?.getString("email")
            ChangePasswordScreen(navController = navController, email = email)
        }

        composable("text-to-speech?user={id}",
            arguments = listOf(navArgument("id")
            {   type = NavType.IntType
                defaultValue = 0
            })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("id")
            TextToSpeechScreen(navController = navController, user = id)
        }

        composable("speech-to-text?user={id}",
            arguments = listOf(navArgument("id")
                {   type = NavType.IntType
                    defaultValue = 0
                }
            )
        ){backStackEntry ->
            val id = backStackEntry.arguments?.getInt("id")
            SpeechToTextScreen(navController = navController, user = id,)

        }
    }
}