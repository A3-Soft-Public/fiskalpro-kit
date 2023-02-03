package sk.a3soft.kit.public_demoapp.presentation.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import sk.a3soft.kit.public_demoapp.R
import sk.a3soft.kit.public_demoapp.presentation.components.Title
import sk.a3soft.kit.public_demoapp.presentation.nativeprotocolclient.NativeProtocolClientScreenContainer

sealed class SamplesRoute(
    val root: String,
    val arguments: List<NamedNavArgument> = emptyList(),
) {
    object Root : SamplesRoute("samples")
    internal object Sample : SamplesRoute("sample")
    internal object NativeProtocolClient : SamplesRoute("native-protocol-client")
}

fun NavGraphBuilder.samplesGraph() {
    navigation(
        startDestination = SamplesRoute.Sample.root,
        route = SamplesRoute.Root.root,
    ) {
        composable(route = SamplesRoute.Sample) {
            val navController = LocalNavController.current
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    modifier = Modifier.padding(top = 16.dp, bottom = 16.dp),
                    text = stringResource(id = R.string.app_name),
                    style = MaterialTheme.typography.headlineLarge,
                )
                Title("Native protocol client")
                Button(onClick = {
                    navController.navigate(SamplesRoute.NativeProtocolClient.root)
                }) {
                    Text("Start")
                }
            }
        }
        composable(route = SamplesRoute.NativeProtocolClient) {
            NativeProtocolClientScreenContainer()
        }
    }
}

private fun NavGraphBuilder.composable(
    route: SamplesRoute,
    content: @Composable (NavBackStackEntry) -> Unit,
): Unit = composable(
    route = route.root,
    arguments = route.arguments,
    content = content,
)

val LocalNavController: ProvidableCompositionLocal<NavHostController> = compositionLocalOf {
    error("[LocalNavController] NavHostController not provided!")
}