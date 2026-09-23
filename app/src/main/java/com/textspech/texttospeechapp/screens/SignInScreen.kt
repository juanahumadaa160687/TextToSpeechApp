package com.textspech.texttospeechapp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.input.TextObfuscationMode
import androidx.compose.foundation.text.input.clearText
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.NavigationItemColors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SecureTextField
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfoV2
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteDefaults
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteItem
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteType
import androidx.compose.material3.adaptive.navigationsuite.rememberNavigationSuiteScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.textspech.texttospeechapp.R
import com.textspech.texttospeechapp.data.users
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignInScreen(navController: NavController) {

    val emailState = rememberTextFieldState("")
    val passwordState = rememberTextFieldState("")
    var showPassword by remember { mutableStateOf(false) }


    val navItems = listOf(
        NavItem("Iniciar\nSesión", "sign-in", R.drawable.ic_login),
        NavItem("Registrarse", "sign-up", R.drawable.ic_register),
        NavItem("Recuperar\nContraseña", "password-recovery", R.drawable.ic_forgot_password),
    )

    val state = rememberNavigationSuiteScaffoldState()

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    NavigationSuiteScaffold(
        modifier = Modifier.fillMaxSize(),
        navigationSuiteType = when {
            currentWindowAdaptiveInfoV2().windowSizeClass.minWidthDp <= 800 -> NavigationSuiteType.NavigationBar
            else -> NavigationSuiteType.NavigationRail
        },
        state = state,
        primaryActionContentHorizontalAlignment = Alignment.Start,
        navigationItemVerticalArrangement = Arrangement.Center,
        navigationSuiteColors = NavigationSuiteDefaults.colors(
            navigationBarContainerColor = colorResource(id = R.color.cta_color),
            navigationRailContainerColor = colorResource(id = R.color.cta_color),
        ),
        navigationItems = {
            navItems.forEach { item ->
                NavigationSuiteItem(
                    label = {
                        Text(
                            text = item.label,
                            textAlign = TextAlign.Center,
                            style =
                                when {
                                    currentWindowAdaptiveInfoV2().windowSizeClass.minWidthDp <= 800 -> MaterialTheme.typography.labelLarge
                                    else -> MaterialTheme.typography.labelMedium
                                }
                        )
                    },
                    icon = {
                        Icon(
                            painter = painterResource(id = item.iconResId),
                            contentDescription = item.label,
                            modifier = when {
                                currentWindowAdaptiveInfoV2().windowSizeClass.minWidthDp <= 800 -> Modifier.size(24.dp)
                                else -> Modifier.size(20.dp)
                            },
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
            snackbarHost = { SnackbarHost(hostState = snackbarHostState,
                snackbar = { data ->
                    Snackbar(
                        data,
                        actionOnNewLine = true,
                        containerColor = colorResource(id = R.color.cta_color),
                        contentColor = colorResource(id = R.color.secondary_text_color),
                        actionContentColor = colorResource(id = R.color.secondary_text_color)
                    )
                },
            )},

        ) { innerPadding ->

            Column(
                modifier = Modifier.fillMaxSize().padding(innerPadding),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                Card(
                    modifier = Modifier.fillMaxWidth().height(110.dp),
                    shape = RoundedCornerShape(bottomEnd = 110.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = colorResource(id = R.color.cta_color)
                    )
                ) {
                    Row(
                        modifier = Modifier.fillMaxSize().padding(bottom = 16.dp),
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.Bottom
                    ) {
                        IconButton(
                            onClick = { navController.popBackStack() }
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_back),
                                contentDescription = null,
                                tint = colorResource(id = R.color.secondary_text_color),
                                modifier = Modifier.size(24.dp)
                            )
                        }
                        Column(
                            horizontalAlignment = Alignment.Start,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "Bienvenido",
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                color = colorResource(id = R.color.secondary_text_color),
                                modifier = Modifier.padding(start = 16.dp)
                            )
                            Spacer(modifier = Modifier.size(4.dp))

                            Text(
                                text = "Inicia sesión para continuar",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Normal,
                                color = colorResource(id = R.color.secondary_text_color),
                                modifier = Modifier.padding(start = 16.dp)
                            )
                        }
                    }
                }
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                        .background(MaterialTheme.colorScheme.background),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center

                ) {

                    item {
                        Column(
                            modifier = Modifier.fillMaxSize()
                                .padding(start = 16.dp, end = 16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            TextField(
                                state = emailState,
                                label = { Text("Correo electrónico") },
                                placeholder = { Text("Ingrese su correo electrónico") },
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
                                        modifier = Modifier.size(24.dp)
                                            .clickable(onClick = { emailState.clearText() }),
                                        tint = colorResource(id = R.color.primary_color)
                                    )
                                },
                                leadingIcon = {
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_email),
                                        contentDescription = null,
                                        modifier = Modifier.size(24.dp),
                                        tint = colorResource(id = R.color.primary_color)
                                    )
                                },
                                modifier = Modifier.width(350.dp).height(56.dp)
                            )
                        }
                    }
                    item {
                        Column(
                            modifier = Modifier.fillMaxSize().padding(top = 36.dp, start = 16.dp, end = 16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            SecureTextField(
                                state = passwordState,
                                label = { Text("Contraseña") },
                                placeholder = { Text("Ingrese su contraseña") },
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
                                        painter = painterResource(id = if (showPassword) R.drawable.ic_visibility else R.drawable.ic_visibility_off),
                                        contentDescription = if (showPassword) "Ocultar contraseña" else "Mostrar contraseña",
                                        modifier = Modifier
                                            .requiredSize(20.dp)
                                            .clickable { showPassword = !showPassword },
                                    )
                                },
                                textObfuscationMode = if (showPassword) TextObfuscationMode.Visible else TextObfuscationMode.RevealLastTyped,
                                leadingIcon = {
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_password),
                                        contentDescription = null,
                                        modifier = Modifier.size(24.dp),
                                    )
                                },
                                modifier = Modifier.width(350.dp).height(56.dp)
                            )
                        }
                    }
                    item {
                        Spacer(modifier = Modifier.size(40.dp))

                        Column(
                            modifier = Modifier.fillMaxSize().padding(start = 16.dp, end = 16.dp, top = 28.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Button(
                                onClick = {

                                    val email = emailState.text
                                    val password = passwordState.text
                                    val usuario =
                                        users.find { it.email == email && it.password == password }

                                    if (email.isEmpty() || password.isEmpty()) {

                                        scope.launch {
                                            snackbarHostState.showSnackbar(
                                                message = "Por favor, complete todos los campos",
                                                actionLabel = "Aceptar",
                                                duration = SnackbarDuration.Short
                                            )
                                        }

                                    } else if (!email.contains("@")) {
                                        scope.launch {
                                            snackbarHostState.showSnackbar(
                                                message = "Por favor, ingrese un correo electrónico válido",
                                                actionLabel = "Aceptar",
                                                duration = SnackbarDuration.Short
                                            )
                                        }
                                    } else if (password.length < 8) {
                                        scope.launch {
                                            snackbarHostState.showSnackbar(
                                                message = "Por favor, ingrese una contraseña válida",
                                                actionLabel = "Aceptar",
                                                duration = SnackbarDuration.Short
                                            )
                                        }
                                    } else if (usuario == null) {
                                        scope.launch {
                                            snackbarHostState.showSnackbar(
                                                message = "Correo electrónico o contraseña incorrectos",
                                                actionLabel = "Aceptar",
                                                duration = SnackbarDuration.Short
                                            )
                                        }
                                    } else {
                                        navController.navigate("text-to-speech?user=${usuario.id}")
                                    }

                                },
                                modifier = Modifier.width(300.dp).height(56.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = colorResource(id = R.color.cta_color),
                                    contentColor = colorResource(id = R.color.secondary_text_color)
                                )
                            ) {
                                Text(
                                    text = "Iniciar sesión",
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

data class NavItem(
    val label: String,
    val route: String,
    val iconResId: Int
)