package com.textspech.texttospeechapp.screens

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
import androidx.compose.foundation.text.input.TextObfuscationMode
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
import androidx.compose.material3.SecureTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
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
import com.textspech.texttospeechapp.models.User

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(navController: NavController) {

    val firstnameState = rememberTextFieldState("")
    val lastnameState = rememberTextFieldState("")
    val emailState = rememberTextFieldState("")
    val phoneState = rememberTextFieldState("")
    val passwordState = rememberTextFieldState("")
    val confirmPasswordState = rememberTextFieldState("")

    var showPassword by remember { mutableStateOf(false) }
    var showConfirmPassword by remember { mutableStateOf(false) }

    val navItems = listOf(
        NavItem("Iniciar\nSesión", "sign-in", R.drawable.ic_login),
        NavItem("Registrarse", "sign-up", R.drawable.ic_register),
        NavItem("Recuperar\nContraseña", "password-recovery", R.drawable.ic_forgot_password),
    )

    val state = rememberNavigationSuiteScaffoldState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

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
        Column(
            modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background).nestedScroll(scrollBehavior.nestedScrollConnection),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            MediumTopAppBar(
                title = {
                    Column(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
                        Text(
                            text = "Bienvenido",
                            style = MaterialTheme.typography.titleLarge,
                            color = colorResource(id = R.color.secondary_text_color)
                        )
                        Text(
                            text = "Registrate para comenzar a utilizar la aplicación",
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
                modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center

            ) {
                item {

                    Spacer(modifier = Modifier.size(32.dp))

                    Column(
                        modifier = Modifier.fillMaxSize().padding(top = 32.dp, start = 16.dp, end = 16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Bottom
                    ) {
                        TextField(
                            state = firstnameState,
                            label = { Text("Nombre") },
                            placeholder = { Text("Ingrese su Nombre") },
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
                                    modifier = Modifier.size(24.dp).clickable(onClick = {firstnameState.clearText()}),
                                    tint = colorResource(id = R.color.primary_color)
                                )
                            },
                            leadingIcon = {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_person),
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

                    Spacer(modifier = Modifier.size(32.dp))

                    Column(
                        modifier = Modifier.fillMaxSize().padding(top = 32.dp, start = 16.dp, end = 16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Bottom
                    ) {
                        TextField(
                            state = lastnameState,
                            label = { Text("Apellido") },
                            placeholder = { Text("Ingrese su apellido") },
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
                                    modifier = Modifier.size(24.dp).clickable(onClick = {lastnameState.clearText()}),
                                    tint = colorResource(id = R.color.primary_color)
                                )
                            },
                            leadingIcon = {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_person),
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

                    Spacer(modifier = Modifier.size(32.dp))

                    Column(
                        modifier = Modifier.fillMaxSize().padding(top = 32.dp, start = 16.dp, end = 16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Bottom
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
                                    modifier = Modifier.size(24.dp).clickable(onClick = {emailState.clearText()}),
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

                    Spacer(modifier = Modifier.size(32.dp))

                    Column(
                        modifier = Modifier.fillMaxSize().padding(top = 32.dp, start = 16.dp, end = 16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Bottom
                    ) {
                        TextField(
                            state = phoneState,
                            label = { Text("Teléfono") },
                            placeholder = { Text("Ingrese su teléfono") },
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
                                    modifier = Modifier.size(24.dp).clickable(onClick = {phoneState.clearText()}),
                                    tint = colorResource(id = R.color.primary_color)
                                )
                            },
                            leadingIcon = {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_phone),
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

                    Spacer(modifier = Modifier.size(32.dp))

                    Column(
                        modifier = Modifier.fillMaxSize().padding(top = 32.dp, start = 16.dp, end = 16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Bottom
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
                    Row(
                        modifier = Modifier.width(350.dp).padding(top = 8.dp, start = 16.dp, end = 16.dp),
                        horizontalArrangement = Arrangement.End
                    ) {
                        Text(text = "La  contraseña debe tener entre 8 y 16 caracteres, al menos un número y una letra", color = colorResource(id = R.color.tertiary_color), textAlign = TextAlign.End, fontSize = 12.sp)
                    }
                }
                item {

                    Spacer(modifier = Modifier.size(32.dp))

                    Column(
                        modifier = Modifier.fillMaxSize().padding(top = 32.dp, start = 16.dp, end = 16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Bottom
                    ) {
                        SecureTextField(
                            state = passwordState,
                            label = { Text("Confirmar Contraseña") },
                            placeholder = { Text("Confirma tu contraseña") },
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
                                    painter = painterResource(id = if (showConfirmPassword) R.drawable.ic_visibility else R.drawable.ic_visibility_off),
                                    contentDescription = if (showConfirmPassword) "Ocultar contraseña" else "Mostrar contraseña",
                                    modifier = Modifier
                                        .requiredSize(20.dp)
                                        .clickable { showConfirmPassword = !showConfirmPassword },
                                )
                            },
                            textObfuscationMode = if (showConfirmPassword) TextObfuscationMode.Visible else TextObfuscationMode.RevealLastTyped,
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
                    Spacer(modifier = Modifier.size(32.dp))

                    Column(
                        modifier = Modifier.fillMaxSize().padding(top = 32.dp, start = 16.dp, end = 16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Bottom
                    ) {
                        Button(
                            onClick = {
                                val id = users.size + 1
                                val newUser = User(id, firstnameState.toString(), lastnameState.toString(), emailState.toString(), passwordState.toString(), phoneState.toString())
                                users.add(newUser)

                                navController.navigate("sign-in")

                            },
                            modifier = Modifier.width(300.dp).height(56.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = colorResource(id = R.color.cta_color),
                                contentColor = colorResource(id = R.color.secondary_text_color)
                            )
                        ) {
                            Text(text = "Registrarse", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                        }
                        Spacer(modifier = Modifier.size(32.dp))
                    }
                }
            }
        }
    }
}