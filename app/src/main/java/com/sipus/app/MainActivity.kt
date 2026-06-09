package com.sipus.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.sipus.core.ui.component.LoadingScreen
import com.sipus.core.ui.component.StatCard
import com.sipus.core.ui.theme.SipusTheme
import com.sipus.core.util.Constants
import com.sipus.feature.auth.*
import com.sipus.feature.mahasiswa.MahasiswaViewModel
import com.sipus.feature.mahasiswa.screen.*
import com.sipus.feature.staff.StaffViewModel
import com.sipus.feature.staff.screen.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent { SipusTheme { SipusApp() } }
    }
}

@Composable
fun SipusApp() {
    val authVm: AuthViewModel = hiltViewModel()
    val authState by authVm.state.collectAsStateWithLifecycle()

    when {
        authState.isCheckingSession -> SplashScreen()
        !authState.isLoggedIn -> AuthFlow(authVm, authState)
        Constants.isStaff(authState.role) -> StaffMainFlow(onLogout = { authVm.logout() })
        else -> MahasiswaMainFlow(onLogout = { authVm.logout() })
    }
}

@Composable
fun AuthFlow(vm: AuthViewModel, state: AuthUiState) {
    val navController = rememberNavController()
    NavHost(navController, startDestination = "login") {
        composable("login") {
            LoginScreen(state, onLogin = vm::login, onGoRegister = { navController.navigate("register") }, onClearError = vm::clearError)
        }
        composable("register") {
            RegisterScreen(state, onRegister = vm::register, onGoLogin = { navController.popBackStack() }, onClearError = vm::clearError)
        }
    }
}

@Composable
fun MahasiswaMainFlow(onLogout: () -> Unit) {
    val navController = rememberNavController()
    val vm: MahasiswaViewModel = hiltViewModel()
    val state by vm.state.collectAsStateWithLifecycle()
    var currentTab by remember { mutableIntStateOf(0) }

    // Handle snackbar messages
    val snackbarHostState = remember { SnackbarHostState() }
    LaunchedEffect(state.error, state.successMsg) {
        state.error?.let { snackbarHostState.showSnackbar(it); vm.clearMessages() }
        state.successMsg?.let { snackbarHostState.showSnackbar(it); vm.clearMessages() }
    }

    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
    val showBottomBar = currentRoute in listOf("mhs_dashboard", "mhs_books", "mhs_loans", "mhs_profile")

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        bottomBar = {
            if (showBottomBar) {
                NavigationBar {
                    NavigationBarItem(currentTab == 0, onClick = { currentTab = 0; navController.navigateSingle("mhs_dashboard") },
                        icon = { Icon(if (currentTab == 0) Icons.Filled.Home else Icons.Outlined.Home, null) }, label = { Text("Beranda") })
                    NavigationBarItem(currentTab == 1, onClick = { currentTab = 1; navController.navigateSingle("mhs_books"); vm.searchBooks("") },
                        icon = { Icon(if (currentTab == 1) Icons.Filled.Search else Icons.Outlined.Search, null) }, label = { Text("Buku") })
                    NavigationBarItem(currentTab == 2, onClick = { currentTab = 2; navController.navigateSingle("mhs_loans"); vm.loadLoans() },
                        icon = { Icon(if (currentTab == 2) Icons.Filled.BookmarkBorder else Icons.Outlined.BookmarkBorder, null) }, label = { Text("Pinjaman") })
                    NavigationBarItem(currentTab == 3, onClick = { currentTab = 3; navController.navigateSingle("mhs_profile"); vm.loadProfile() },
                        icon = { Icon(if (currentTab == 3) Icons.Filled.Person else Icons.Outlined.Person, null) }, label = { Text("Profil") })
                }
            }
        }
    ) { padding ->
        NavHost(navController, "mhs_dashboard", Modifier.padding(padding)) {
            composable("mhs_dashboard") {
                MhsDashboardScreen(state, onNavigate = { route ->
                    when (route) {
                        "books" -> { currentTab = 1; navController.navigateSingle("mhs_books"); vm.searchBooks("") }
                        "card" -> { vm.loadBarcode(); navController.navigate("mhs_card") }
                        "loans" -> { currentTab = 2; navController.navigateSingle("mhs_loans"); vm.loadLoans() }
                        "history" -> navController.navigate("mhs_history")
                        "fines" -> navController.navigate("mhs_fines")
                        "visits" -> navController.navigate("mhs_visits")
                    }
                }, onRefresh = vm::loadDashboard)
            }
            composable("mhs_books") {
                BookCatalogScreen(state, onSearch = vm::searchBooks, onBookClick = { vm.loadBookDetail(it); navController.navigate("mhs_book_detail") },
                    onBack = { currentTab = 0; navController.navigateSingle("mhs_dashboard") })
            }
            composable("mhs_book_detail") {
                BookDetailScreen(state, onBack = navController::popBackStack, onBorrow = vm::requestLoan)
            }
            composable("mhs_loans") {
                LoansScreen(state, onLoadLoans = vm::loadLoans, onReturn = vm::requestReturn,
                    onExtend = vm::requestExtension, onBack = { currentTab = 0; navController.navigateSingle("mhs_dashboard") })
            }
            composable("mhs_card") {
                LibraryCardScreen(state, onBack = navController::popBackStack)
            }
            composable("mhs_history") {
                HistoryScreen(state, onLoad = vm::loadHistory, onBack = navController::popBackStack)
            }
            composable("mhs_fines") {
                FinesScreen(state, onLoad = vm::loadFines, onPay = vm::payFine, onDispute = vm::disputeFine, onBack = navController::popBackStack)
            }
            composable("mhs_visits") {
                VisitScreen(state, onLoadStatus = vm::loadVisitStatus, onLoadHistory = vm::loadVisitHistory, onBack = navController::popBackStack)
            }
            composable("mhs_profile") {
                ProfileScreen(state, onLogout = onLogout, onBack = { currentTab = 0; navController.navigateSingle("mhs_dashboard") })
            }
        }
    }
}

@Composable
fun StaffMainFlow(onLogout: () -> Unit) {
    val navController = rememberNavController()
    val vm: StaffViewModel = hiltViewModel()
    val state by vm.state.collectAsStateWithLifecycle()
    var currentTab by remember { mutableIntStateOf(0) }

    val snackbarHostState = remember { SnackbarHostState() }
    LaunchedEffect(state.error, state.successMsg) {
        state.error?.let { snackbarHostState.showSnackbar(it); vm.clearMessages() }
        state.successMsg?.let { snackbarHostState.showSnackbar(it); vm.clearMessages() }
    }

    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
    val showBottomBar = currentRoute in listOf("staff_dashboard", "staff_approvals", "staff_scan", "staff_profile")

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        bottomBar = {
            if (showBottomBar) {
                NavigationBar {
                    NavigationBarItem(currentTab == 0, onClick = { currentTab = 0; navController.navigateSingle("staff_dashboard"); vm.loadDashboard() },
                        icon = { Icon(if (currentTab == 0) Icons.Filled.Dashboard else Icons.Outlined.Dashboard, null) }, label = { Text("Dashboard") })
                    NavigationBarItem(currentTab == 1, onClick = { currentTab = 1; navController.navigateSingle("staff_approvals"); vm.loadApprovals() },
                        icon = { Icon(if (currentTab == 1) Icons.Filled.Checklist else Icons.Outlined.Checklist, null) }, label = { Text("Approval") })
                    NavigationBarItem(currentTab == 2, onClick = { currentTab = 2; navController.navigateSingle("staff_scan") },
                        icon = { Icon(if (currentTab == 2) Icons.Filled.QrCodeScanner else Icons.Outlined.QrCodeScanner, null) }, label = { Text("Scan") })
                    NavigationBarItem(currentTab == 3, onClick = { currentTab = 3; navController.navigateSingle("staff_profile"); vm.loadProfile() },
                        icon = { Icon(if (currentTab == 3) Icons.Filled.Person else Icons.Outlined.Person, null) }, label = { Text("Profil") })
                }
            }
        }
    ) { padding ->
        NavHost(navController, "staff_dashboard", Modifier.padding(padding)) {
            composable("staff_dashboard") {
                StaffDashboardScreen(state, onNavigate = { route ->
                    when (route) {
                        "scan" -> { currentTab = 2; navController.navigateSingle("staff_scan") }
                        "approvals" -> { currentTab = 1; navController.navigateSingle("staff_approvals"); vm.loadApprovals() }
                        "visitors" -> navController.navigate("staff_visitors")
                        "search_patron" -> navController.navigate("staff_search_patron")
                    }
                }, onRefresh = vm::loadDashboard)
            }
            composable("staff_approvals") {
                ApprovalsScreen(state, onLoad = vm::loadApprovals, onAction = vm::processApproval,
                    onBack = { currentTab = 0; navController.navigateSingle("staff_dashboard") })
            }
            composable("staff_scan") {
                ScanScreen(state, onScan = vm::checkInOut, onClear = vm::clearMessages,
                    onBack = { currentTab = 0; navController.navigateSingle("staff_dashboard") })
            }
            composable("staff_visitors") {
                VisitorsScreen(state, onLoad = vm::loadVisitors, onBack = navController::popBackStack)
            }
            composable("staff_search_patron") {
                SearchPatronScreen(state, onSearch = vm::searchPatrons,
                    onPatronClick = { vm.loadPatronProfile(it); navController.navigate("staff_patron_profile") },
                    onBack = navController::popBackStack)
            }
            composable("staff_patron_profile") {
                PatronProfileScreen(state, onBack = navController::popBackStack)
            }
            composable("staff_profile") {
                StaffProfileScreen(state, onLoadProfile = vm::loadProfile, onLogout = onLogout,
                    onBack = { currentTab = 0; navController.navigateSingle("staff_dashboard") })
            }
        }
    }
}

// Profile screen (shared for mahasiswa)
@Composable
private fun ProfileScreen(state: com.sipus.feature.mahasiswa.MhsUiState, onLogout: () -> Unit, onBack: () -> Unit) {
    val p = state.profile
    com.sipus.core.ui.component.SipusTopBar("Profil", onBack = onBack)
    if (state.isLoading && p == null) LoadingScreen()
    else {
        Column(Modifier.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Spacer(Modifier.height(56.dp))
            Card(shape = androidx.compose.foundation.shape.RoundedCornerShape(16.dp)) {
                Column(Modifier.padding(20.dp)) {
                    Text(p?.nama ?: "-", style = MaterialTheme.typography.headlineSmall, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                    Text("${p?.username} • ${p?.role}", color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Text(p?.email ?: "-", color = MaterialTheme.colorScheme.onSurfaceVariant, style = MaterialTheme.typography.bodySmall)
                }
            }
            p?.stats?.let { s ->
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    StatCard("Aktif", "${s.pinjamanAktif}", Icons.Outlined.BookmarkBorder, com.sipus.core.ui.theme.StatusInfo, Modifier.weight(1f))
                    StatCard("Selesai", "${s.selesai}", Icons.Outlined.CheckCircle, com.sipus.core.ui.theme.StatusSuccess, Modifier.weight(1f))
                }
            }
            Spacer(Modifier.weight(1f))
            Button(onClick = onLogout, modifier = Modifier.fillMaxWidth().height(50.dp),
                shape = androidx.compose.foundation.shape.RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(containerColor = com.sipus.core.ui.theme.StatusError)
            ) { Icon(Icons.Default.Logout, null); Spacer(Modifier.width(8.dp)); Text("Keluar") }
        }
    }
}

fun NavHostController.navigateSingle(route: String) {
    navigate(route) {
        popUpTo(graph.startDestinationId) { saveState = true }
        launchSingleTop = true
        restoreState = true
    }
}
