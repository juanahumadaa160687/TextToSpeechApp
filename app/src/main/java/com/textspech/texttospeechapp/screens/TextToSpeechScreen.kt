package com.textspech.texttospeechapp.screens

import android.speech.tts.TextToSpeech
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.input.clearText
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.NavigationItemColors
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import com.textspech.texttospeechapp.R
import com.textspech.texttospeechapp.data.users
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import java.util.Locale
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteDefaults
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteItem
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffoldValue
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteType
import androidx.compose.material3.adaptive.navigationsuite.rememberNavigationSuiteScaffoldState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.textspech.texttospeechapp.data.recordedPhrases
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextToSpeechScreen(navController: NavController, user: Int?) {

    val phraseState = rememberTextFieldState()

    val navItems = listOf(
        NavItem("Texto a\nVoz", "text-to-speech?user={id}", R.drawable.ic_text),
        NavItem("Voz a\nTexto", "speech-to-text?user={id}", R.drawable.ic_microphone),
        NavItem("Cerrar\nSesión", "index", R.drawable.ic_logout),
    )

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val state = rememberNavigationSuiteScaffoldState()

    val user = users.find { it.id == user }

    val context = LocalContext.current
    var textToSpeak by remember { mutableStateOf("") }

    var textToSpeech by remember { mutableStateOf<TextToSpeech?>(null) }
    var isInitialized by remember { mutableStateOf(false) }

    DisposableEffect(Unit) {
        val tts = TextToSpeech(context) { status ->
            if (status == TextToSpeech.SUCCESS) {
                isInitialized = true
            }
        }
        tts.language = Locale("es", "CL")
        textToSpeech = tts
        onDispose {
            tts.stop()
            tts.shutdown()
        }
    }

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
        Scaffold(
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection)
        ) { innerPadding ->

            Column(
                modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background).padding(innerPadding),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                TopAppBar(
                    title = {
                        Column(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
                            Text(
                                text = "Hola ${user?.firstname}",
                                style = MaterialTheme.typography.titleLarge,
                                color = colorResource(id = R.color.secondary_text_color)
                            )
                            Text(
                                text = "Convierte tus frases de texto a voz",
                                style = MaterialTheme.typography.labelLarge,
                                color = colorResource(id = R.color.secondary_text_color)
                            )
                        }
                    },
                    modifier = Modifier.fillMaxWidth().background(colorResource(id = R.color.surface_color)),
                    navigationIcon = {
                        IconButton( onClick = { navController.popBackStack() } ) {
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

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    item {
                        OutlinedTextField(
                            value = textToSpeak,
                            onValueChange = { textToSpeak = it },
                            label = { Text("Texto a convertir a voz") },
                            modifier = Modifier.padding(16.dp)
                        )

                        Spacer(modifier = Modifier.padding(24.dp))

                        Button(
                            onClick = {
                                if (isInitialized) {
                                    textToSpeech?.speak(
                                        textToSpeak,
                                        TextToSpeech.QUEUE_FLUSH,
                                        null,
                                        null
                                    )
                                }
                            },
                            enabled = isInitialized && textToSpeak.isNotBlank()
                        ) {
                            Text("Convertir a voz")
                        }

                        Spacer(modifier = Modifier.padding(24.dp))

                        recordedPhrases.forEach { phrase ->
                            if (phrase.user.id == user?.id) {
                                phrase.phrases.forEach { recordedPhrase ->
                                    Text(
                                        text = recordedPhrase,
                                        color = Color.Black,
                                        modifier = Modifier.padding(8.dp).clickable {
                                            textToSpeak = recordedPhrase
                                        },
                                        textAlign = TextAlign.Center
                                    )
                                    Spacer(modifier = Modifier.padding(8.dp))
                                }
                            }
                        }
                    }
                    item {
                        Spacer(modifier = Modifier.padding(24.dp))

                        TextField(
                            state = phraseState,
                            label = { Text("Ingrese una frase") },
                            placeholder = { Text("Ingrese una frase") },
                            colors = TextFieldDefaults.colors(
                                focusedTextColor = colorResource(id = R.color.tertiary_color),
                                unfocusedTextColor = colorResource(id = R.color.primary_color),
                                focusedContainerColor = Color.Transparent,
                                unfocusedContainerColor = Color.Transparent,
                                focusedIndicatorColor = colorResource(id = R.color.tertiary_color),
                                unfocusedIndicatorColor = colorResource(id = R.color.primary_color),
                                focusedLabelColor = colorResource(id = R.color.tertiary_color),
                                unfocusedLabelColor = colorResource(id = R.color.primary_color),
                                focusedLeadingIconColor = colorResource(id = R.color.tertiary_color),
                                unfocusedLeadingIconColor = colorResource(id = R.color.primary_color),
                                focusedTrailingIconColor = colorResource(id = R.color.tertiary_color),
                                unfocusedTrailingIconColor = colorResource(id = R.color.primary_color),
                            ),
                            trailingIcon = {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_clear),
                                    contentDescription = null,
                                    modifier = Modifier.size(24.dp).clickable(onClick = {phraseState.clearText()}),
                                    tint = colorResource(id = R.color.primary_color)
                                )
                            },
                        )

                        Spacer(modifier = Modifier.padding(20.dp))

                        Button(
                            onClick = {
                                recordedPhrases.find { it.user.id == user?.id }?.phrases?.add(phraseState.text.toString())
                                phraseState.clearText()
                                navController.navigate("text-to-speech?user=${user?.id}")
                            },
                            modifier = Modifier.width(200.dp).height(56.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = colorResource(id = R.color.cta_color),
                            )
                        ) {
                            Text(text = "Guardar", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                        }

                        Spacer(modifier = Modifier.padding(20.dp))

                        Button(
                            onClick = {
                                recordedPhrases.find { it.user.id == user?.id }?.phrases?.clear()
                            },
                            modifier = Modifier.width(200.dp).height(56.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.error,
                            )
                        ) {
                            Text(text = "Borrar Frases", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}
