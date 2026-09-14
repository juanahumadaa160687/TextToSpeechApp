package com.textspech.texttospeechapp.screens

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.NavigationItemColors
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteDefaults
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteItem
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffoldValue
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteType
import androidx.compose.material3.adaptive.navigationsuite.rememberNavigationSuiteScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.textspech.texttospeechapp.R
import com.textspech.texttospeechapp.`class`.SpeechToTextManager
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SpeechToTextScreen(
    navController: NavController,
    user: Int?,
) {

    val navItems = listOf(
        NavItem("Voz a\nTexto", "speech-to-text?user={id}", R.drawable.ic_microphone),
        NavItem("Texto a\nVoz", "text-to-speech?user={id}", R.drawable.ic_text),
        NavItem("Cerrar\nSesión", "index", R.drawable.ic_logout),
    )

    val context = LocalContext.current

    val speechManager = remember { SpeechToTextManager(context) }
    val speechState by speechManager.state.collectAsState()

    var hasPermission by remember { mutableStateOf(false) }
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        hasPermission = isGranted
    }

    DisposableEffect(Unit) {
        onDispose { speechManager.cleanUp() }
    }

    val state = rememberNavigationSuiteScaffoldState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    NavigationSuiteScaffold(
        modifier = Modifier.fillMaxSize(),
        navigationSuiteType = NavigationSuiteType.NavigationBar,
        state = state,
        primaryActionContentHorizontalAlignment = Alignment.Start,
        navigationItemVerticalArrangement = Arrangement.Center,
        navigationSuiteColors = NavigationSuiteDefaults.colors(
            navigationBarContainerColor = colorResource(id = R.color.cta_color),
        ),
        navigationItems = {
            navItems.forEach { item ->
                NavigationSuiteItem(
                    label = {
                        Text(
                            text = item.label,
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.labelLarge,
                        )
                    },
                    icon = {
                        Icon(
                            painter = painterResource(id = item.iconResId),
                            contentDescription = item.label,
                            modifier = Modifier.requiredSize(24.dp),
                        )
                    },
                    onClick = {
                        navController.navigate(item.route)
                    },
                    selected = navController.currentDestination?.route == item.route,
                    colors = NavigationItemColors(
                        selectedTextColor = colorResource(id = R.color.secondary_text_color),
                        selectedIconColor = colorResource(id = R.color.secondary_text_color),
                        unselectedTextColor = MaterialTheme.colorScheme.background,
                        unselectedIconColor = MaterialTheme.colorScheme.background,
                        selectedIndicatorColor = MaterialTheme.colorScheme.tertiary,
                        disabledTextColor = MaterialTheme.colorScheme.background,
                        disabledIconColor = MaterialTheme.colorScheme.background,
                    )
                )
            }
        }
    ) {
        Column(
            modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background).nestedScroll(scrollBehavior.nestedScrollConnection),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            MediumTopAppBar(
                title = {
                    Column(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
                        Text(
                            text = "Bienvenido otra vez",
                            style = MaterialTheme.typography.titleLarge,
                            color = colorResource(id = R.color.secondary_text_color)
                        )
                        Text(
                            text = "Inicia sesión para continuar",
                            style = MaterialTheme.typography.labelLarge,
                            color = colorResource(id = R.color.secondary_text_color)
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth()
                    .background(colorResource(id = R.color.surface_color)),
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_back),
                            contentDescription = "Volver",
                            modifier = Modifier.size(24.dp),
                            tint = colorResource(id = R.color.secondary_text_color)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = colorResource(id = R.color.cta_color),
                    titleContentColor = colorResource(id = R.color.secondary_text_color),
                    navigationIconContentColor = colorResource(id = R.color.secondary_text_color),
                    actionIconContentColor = colorResource(id = R.color.secondary_text_color),
                    scrolledContainerColor = colorResource(id = R.color.cta_color),
                    subtitleContentColor = colorResource(id = R.color.secondary_text_color)
                ),
                scrollBehavior = scrollBehavior,
            )
        }
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Text(
                    text = when {
                        speechState.isListening -> "Escuchando..."
                        speechState.error != null -> "Error: ${speechState.error}"
                        else -> "Presiona el botón para iniciar la grabación"
                    },
                    style = MaterialTheme.typography.bodyLarge,
                )
            }
            item {
                Spacer(modifier = Modifier.padding(24.dp))
                Button(
                    onClick = {
                        if (hasPermission) {
                            if (speechState.isListening) {
                                speechManager.stopListening()
                            } else {
                                speechManager.startListening()
                            }
                        } else{
                            permissionLauncher.launch(android.Manifest.permission.RECORD_AUDIO)
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(id = R.color.cta_color),
                    )
                ) {
                    Text( text = if (speechState.isListening) "Detener grabación" else "Hablar")
                }
            }
        }
    }
}