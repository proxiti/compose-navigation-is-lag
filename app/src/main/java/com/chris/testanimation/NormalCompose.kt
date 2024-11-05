package com.chris.testanimation

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "page1",
        enterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Left,
                tween(700)
            )
        },
        // 下层退出动画
        exitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Left,
                tween(700)
            ) { -300 }
        },
        // 上层弹出动画
        popExitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Right,
                tween(700)
            )
        },
        // 下层重新进入动画
        popEnterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Right,
                tween(700)
            ) { -300 }
        },
    ) {
        composable(
            route = "page1",
        ) {
            val context = LocalContext.current
            Page1(
                toPage2 = { navController.navigate("page2") },
                toPage2Activity = {
                    Page2Activity.navTo(context)
                }
            )
        }

        composable(
            route = "page2",
        ) {
            Page2()
        }

    }

}

@Composable
private fun Page1(
    toPage2: () -> Unit,
    toPage2Activity: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            modifier = Modifier
                .clickable {
                    toPage2()
                }
                .size(80.dp)
                .background(Color.LightGray),
            textAlign = TextAlign.Center,
            text = "navigation",
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            modifier = Modifier
                .clickable {
                    toPage2Activity()
                }
                .size(80.dp)
                .background(Color.LightGray),
            textAlign = TextAlign.Center,
            text = "activity",
        )
    }
}

@Composable
private fun Page2() {
    val vm: SecondViewModel = viewModel()
    val text by vm.textState.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Gray),
    ) {
        for (i in 1..15)
            Text(text = text)
    }
}

class Page2Activity : ComponentActivity() {
    companion object {
        fun navTo(context: Context) {
            val intent = Intent(context, Page2Activity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            if (context !is Activity) {
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Page2()
        }
    }
}