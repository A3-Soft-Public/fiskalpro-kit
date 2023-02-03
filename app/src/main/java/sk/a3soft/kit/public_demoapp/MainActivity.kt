package sk.a3soft.kit.public_demoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import sk.a3soft.kit.public_demoapp.presentation.navigation.LocalNavController
import sk.a3soft.kit.public_demoapp.presentation.navigation.SamplesRoute
import sk.a3soft.kit.public_demoapp.presentation.navigation.samplesGraph
import sk.a3soft.kit.public_demoapp.ui.theme.DemoAppTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()
            DemoAppTheme {
                CompositionLocalProvider(
                    LocalNavController provides navController,
                ) {
                    // A surface container using the 'background' color from the theme
                    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                        NavHost(
                            navController = navController,
                            startDestination = SamplesRoute.Root.root,
                        ) {
                            samplesGraph()
                        }
                    }
                }
            }
        }
    }
}