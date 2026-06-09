package com.sipus.feature.auth

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sipus.core.ui.theme.*

@Composable
fun LoginScreen(
    state: AuthUiState,
    onLogin: (String, String) -> Unit,
    onGoRegister: () -> Unit,
    onClearError: () -> Unit
) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var showPassword by remember { mutableStateOf(false) }

    LaunchedEffect(state.error) { /* snackbar handled by parent */ }

    Column(
        modifier = Modifier.fillMaxSize().background(
            Brush.verticalGradient(listOf(GradientStart, GradientEnd, Color(0xFF7C3AED)))
        ).statusBarsPadding().navigationBarsPadding()
    ) {
        // Header
        Spacer(Modifier.height(60.dp))
        Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 32.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(Icons.Outlined.LocalLibrary, null, tint = Color.White, modifier = Modifier.size(64.dp))
            Spacer(Modifier.height(16.dp))
            Text("SIPUS", fontSize = 32.sp, fontWeight = FontWeight.Bold, color = Color.White)
            Text("Sistem Informasi Perpustakaan", style = MaterialTheme.typography.bodyMedium, color = Color.White.copy(0.8f))
        }

        Spacer(Modifier.height(40.dp))

        // Login Card
        Card(
            modifier = Modifier.fillMaxSize(),
            shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
            Column(
                modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Spacer(Modifier.height(8.dp))
                Text("Masuk", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
                Text("Masuk ke akun perpustakaan Anda", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)

                Spacer(Modifier.height(8.dp))

                OutlinedTextField(
                    value = username, onValueChange = { username = it; onClearError() },
                    label = { Text("Username / NIM") },
                    leadingIcon = { Icon(Icons.Outlined.Person, null) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
                )

                OutlinedTextField(
                    value = password, onValueChange = { password = it; onClearError() },
                    label = { Text("Password") },
                    leadingIcon = { Icon(Icons.Outlined.Lock, null) },
                    trailingIcon = {
                        IconButton(onClick = { showPassword = !showPassword }) {
                            Icon(if (showPassword) Icons.Default.VisibilityOff else Icons.Default.Visibility, null)
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    singleLine = true,
                    visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Done)
                )

                AnimatedVisibility(visible = state.error != null) {
                    Card(colors = CardDefaults.cardColors(containerColor = StatusError.copy(0.1f)), shape = RoundedCornerShape(12.dp)) {
                        Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.ErrorOutline, null, tint = StatusError, modifier = Modifier.size(20.dp))
                            Spacer(Modifier.width(8.dp))
                            Text(state.error ?: "", style = MaterialTheme.typography.bodySmall, color = StatusError)
                        }
                    }
                }

                Spacer(Modifier.height(8.dp))

                Button(
                    onClick = { onLogin(username, password) },
                    modifier = Modifier.fillMaxWidth().height(52.dp),
                    enabled = !state.isLoading,
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Primary)
                ) {
                    if (state.isLoading) {
                        CircularProgressIndicator(modifier = Modifier.size(22.dp), color = Color.White, strokeWidth = 2.dp)
                    } else {
                        Text("Masuk", fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
                    }
                }

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                    Text("Belum punya akun? ", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Text("Daftar", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold, color = Primary,
                        modifier = Modifier.clickable { onGoRegister() })
                }
            }
        }
    }
}

@Composable
fun RegisterScreen(
    state: AuthUiState,
    onRegister: (String, String, String, String, String) -> Unit,
    onGoLogin: () -> Unit,
    onClearError: () -> Unit
) {
    var username by remember { mutableStateOf("") }
    var nama by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var jenkel by remember { mutableStateOf("Laki-laki") }
    var showPassword by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize().background(
            Brush.verticalGradient(listOf(GradientStart, GradientEnd))
        ).statusBarsPadding().navigationBarsPadding()
    ) {
        Spacer(Modifier.height(40.dp))
        Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 32.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(Icons.Outlined.LocalLibrary, null, tint = Color.White, modifier = Modifier.size(48.dp))
            Spacer(Modifier.height(8.dp))
            Text("Buat Akun Baru", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color.White)
        }

        Spacer(Modifier.height(24.dp))

        Card(
            modifier = Modifier.fillMaxSize(),
            shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
            Column(
                modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Spacer(Modifier.height(4.dp))

                OutlinedTextField(
                    value = username, onValueChange = { username = it; onClearError() },
                    label = { Text("NIM / Username") }, leadingIcon = { Icon(Icons.Outlined.Badge, null) },
                    modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(14.dp), singleLine = true
                )
                OutlinedTextField(
                    value = nama, onValueChange = { nama = it; onClearError() },
                    label = { Text("Nama Lengkap") }, leadingIcon = { Icon(Icons.Outlined.Person, null) },
                    modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(14.dp), singleLine = true
                )
                OutlinedTextField(
                    value = email, onValueChange = { email = it; onClearError() },
                    label = { Text("Email") }, leadingIcon = { Icon(Icons.Outlined.Email, null) },
                    modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(14.dp), singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
                )
                OutlinedTextField(
                    value = password, onValueChange = { password = it; onClearError() },
                    label = { Text("Password") }, leadingIcon = { Icon(Icons.Outlined.Lock, null) },
                    trailingIcon = {
                        IconButton(onClick = { showPassword = !showPassword }) {
                            Icon(if (showPassword) Icons.Default.VisibilityOff else Icons.Default.Visibility, null)
                        }
                    },
                    modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(14.dp), singleLine = true,
                    visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation()
                )

                Text("Jenis Kelamin", style = MaterialTheme.typography.labelLarge)
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    listOf("Laki-laki", "Perempuan").forEach { jk ->
                        FilterChip(
                            selected = jenkel == jk, onClick = { jenkel = jk },
                            label = { Text(jk) },
                            leadingIcon = if (jenkel == jk) {{ Icon(Icons.Default.Check, null, Modifier.size(16.dp)) }} else null
                        )
                    }
                }

                AnimatedVisibility(visible = state.error != null) {
                    Card(colors = CardDefaults.cardColors(containerColor = StatusError.copy(0.1f)), shape = RoundedCornerShape(12.dp)) {
                        Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.ErrorOutline, null, tint = StatusError, modifier = Modifier.size(20.dp))
                            Spacer(Modifier.width(8.dp))
                            Text(state.error ?: "", style = MaterialTheme.typography.bodySmall, color = StatusError)
                        }
                    }
                }

                Spacer(Modifier.height(4.dp))

                Button(
                    onClick = { onRegister(username, nama, password, email, jenkel) },
                    modifier = Modifier.fillMaxWidth().height(52.dp),
                    enabled = !state.isLoading, shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Primary)
                ) {
                    if (state.isLoading) CircularProgressIndicator(Modifier.size(22.dp), Color.White, strokeWidth = 2.dp)
                    else Text("Daftar", fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
                }

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                    Text("Sudah punya akun? ", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Text("Masuk", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold, color = Primary,
                        modifier = Modifier.clickable { onGoLogin() })
                }
            }
        }
    }
}

@Composable
fun SplashScreen() {
    Box(
        modifier = Modifier.fillMaxSize().background(Brush.verticalGradient(listOf(GradientStart, GradientEnd))),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(Icons.Outlined.LocalLibrary, null, tint = Color.White, modifier = Modifier.size(80.dp))
            Spacer(Modifier.height(16.dp))
            Text("SIPUS", fontSize = 36.sp, fontWeight = FontWeight.Bold, color = Color.White)
            Text("Sistem Informasi Perpustakaan", style = MaterialTheme.typography.bodyLarge, color = Color.White.copy(0.8f))
            Spacer(Modifier.height(32.dp))
            CircularProgressIndicator(color = Color.White.copy(0.7f), strokeWidth = 3.dp)
        }
    }
}
