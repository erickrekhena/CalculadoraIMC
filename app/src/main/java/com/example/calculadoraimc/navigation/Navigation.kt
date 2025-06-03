package com.example.calculadoraimc.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.calculadoraimc.screen.MainScreen

@Composable
fun Navigation(modifier: Modifier, navHostController: NavHostController){
    NavHost(navController = navHostController, startDestination = "principal"){

        composable("principal") {
            MainScreen(modifier)
        }
    }

}