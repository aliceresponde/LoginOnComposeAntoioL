package com.aliceresponde.loginoncomposeantoiol

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.aliceresponde.loginoncomposeantoiol.ui.theme.LoginOnComposeAntoioLTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = "Login") {
                composable("Login") {
                    LoginForm(
                        onLoginClicked = { navController.navigate("Home") }
                    )
                }
                composable("Home") { HomeScreen() }
            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun HomeScreen() {
        Screen {
            Text(
                text = "Home",
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.primaryContainer)
                    .fillMaxSize()
            )
        }
    }

    // is not to possible to preview a composable with a un initialized listener/callback parameter
    @Composable
    fun LoginForm(onLoginClicked: () -> Unit) {
        Screen {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
            ) {
                var user by remember { mutableStateOf("") }
                var pass by remember { mutableStateOf("") }
                val isEnabled = user.isNotBlank() && pass.isNotBlank()

                UserField(value = user, onValueChange = { user = it })
                PasswordField(value = pass, onValueChange = { pass = it })
                LoginButton(isEnabled = isEnabled, onClick = { onLoginClicked() })
            }
        }
    }

    @Composable
    private fun LoginButton(isEnabled: Boolean = false, onClick: () -> Unit) {
        Button(onClick = onClick, enabled = isEnabled) {
            Text(text = "Login")
        }
    }

    @Composable
    fun UserField(value: String, onValueChange: (String) -> Unit) {
        OutlinedTextField(value = value, onValueChange = onValueChange, label = { Text(text = "Username") })
    }

    @Composable
    fun PasswordField(value: String, onValueChange: (String) -> Unit) {
        var isPasswordVisible by remember { mutableStateOf(false) }

        OutlinedTextField(value = value,
            onValueChange = onValueChange,
            label = { Text(text = "Password") },
            visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                IconToggleButton(checked = isPasswordVisible, onCheckedChange = { isPasswordVisible = it }) {
                    val icon = if (isPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff
                    Icon(icon, contentDescription = "Password visibility")
                }
            })
    }

    @Composable
    private fun Screen(content: @Composable () -> Unit) {
        LoginOnComposeAntoioLTheme {
            // A surface container using the 'background' color from the theme
            Surface(content = content, modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background)
        }
    }
}