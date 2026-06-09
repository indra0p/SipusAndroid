package com.sipus.feature.staff.screen

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.sipus.core.data.remote.dto.*
import com.sipus.core.ui.component.*
import com.sipus.core.ui.theme.*
import com.sipus.core.util.toRupiah
import com.sipus.core.util.formatDate
import com.sipus.feature.staff.StaffUiState

@Composable
fun StaffDashboardScreen(state: StaffUiState, onNavigate: (String) -> Unit, onRefresh: () -> Unit) {
    LazyColumn(Modifier.fillMaxSize(), contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {
        item {
            GradientCard(gradient = Brush.linearGradient(listOf(GradientTeal, GradientCyan))) {
                Text("Dashboard Petugas", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = Color.White)
                Text("Kelola perpustakaan", color = Color.White.copy(0.85f), style = MaterialTheme.typography.bodyMedium)
                state.dashboard?.let { d ->
                    Spacer(Modifier.height(12.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                        DashStat("Pengunjung", "${d.occupancy}", Color.White)
                        DashStat("Pending", "${d.pendingPeminjaman + d.pendingPengembalian + d.pendingPerpanjangan}", Color.White)
                        DashStat("Overdue", "${d.peminjamanTerlambat}", Color.White)
                    }
                }
            }
        }
        item {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                StatCard("Anggota", "${state.dashboard?.totalAnggota ?: 0}", Icons.Outlined.People, Primary, Modifier.weight(1f))
                StatCard("Buku", "${state.dashboard?.totalBuku ?: 0}", Icons.Outlined.LibraryBooks, Secondary, Modifier.weight(1f))
            }
        }
        item {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                StatCard("Aktif Pinjam", "${state.dashboard?.pinjamanAktif ?: 0}", Icons.Outlined.BookmarkBorder, StatusInfo, Modifier.weight(1f))
                StatCard("Denda", (state.dashboard?.totalDendaBelumLunas ?: 0.0).toRupiah(), Icons.Outlined.Warning, StatusError, Modifier.weight(1f))
            }
        }
        item { Text("Menu Petugas", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold) }
        item {
            val menus = listOf(
                Triple("Scan QR", Icons.Outlined.QrCodeScanner, "scan"),
                Triple("Persetujuan", Icons.Outlined.Checklist, "approvals"),
                Triple("Pengunjung", Icons.Outlined.People, "visitors"),
                Triple("Cari Anggota", Icons.Outlined.PersonSearch, "search_patron"),
            )
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                menus.chunked(2).forEach { row ->
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        row.forEach { (t, ic, r) ->
                            StaffMenuCard(t, ic, Modifier.weight(1f)) { onNavigate(r) }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun DashStat(label: String, value: String, color: Color) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(value, fontSize = 22.sp, fontWeight = FontWeight.Bold, color = color)
        Text(label, style = MaterialTheme.typography.labelSmall, color = color.copy(0.8f))
    }
}

@Composable
private fun StaffMenuCard(title: String, icon: ImageVector, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Card(modifier = modifier.clickable { onClick() }, shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerLow), elevation = CardDefaults.cardElevation(1.dp)) {
        Row(Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(Modifier.size(44.dp).clip(RoundedCornerShape(12.dp)).background(Secondary.copy(0.1f)), contentAlignment = Alignment.Center) {
                Icon(icon, null, tint = Secondary, modifier = Modifier.size(24.dp))
            }
            Spacer(Modifier.width(12.dp))
            Text(title, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Medium)
        }
    }
}

// ===== Scan QR (Sempurna dengan Kamera & Galeri) =====
@Composable
fun ScanScreen(state: StaffUiState, onScan: (String, String) -> Unit, onClear: () -> Unit, onBack: () -> Unit) {
    var barcode by remember { mutableStateOf("") }
    var tipe by remember { mutableStateOf("checkin") }
    val context = LocalContext.current
    
    // Camera Permission Checking
    var hasCameraPermission by remember {
        mutableStateOf(
            androidx.core.content.ContextCompat.checkSelfPermission(
                context,
                android.Manifest.permission.CAMERA
            ) == android.content.pm.PackageManager.PERMISSION_GRANTED
        )
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        hasCameraPermission = isGranted
    }

    // Gallery Image Picker Launcher
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: android.net.Uri? ->
        uri?.let {
            try {
                val image = com.google.mlkit.vision.common.InputImage.fromFilePath(context, uri)
                val scanner = com.google.mlkit.vision.barcode.BarcodeScanning.getClient()
                scanner.process(image)
                    .addOnSuccessListener { barcodes ->
                        val rawValue = barcodes.firstOrNull()?.rawValue
                        if (rawValue != null) {
                            barcode = rawValue
                            onScan(rawValue, tipe)
                        } else {
                            Toast.makeText(context, "QR/Barcode tidak ditemukan di gambar", Toast.LENGTH_LONG).show()
                        }
                    }
                    .addOnFailureListener {
                        Toast.makeText(context, "Gagal memproses gambar: ${it.localizedMessage}", Toast.LENGTH_LONG).show()
                    }
            } catch (e: Exception) {
                Toast.makeText(context, "Gagal membuka gambar", Toast.LENGTH_SHORT).show()
            }
        }
    }

    Scaffold(topBar = { SipusTopBar("Scan QR Kunjungan", onBack = onBack) }) { padding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Mode Selector: Check-In vs Check-Out
            item {
                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(24.dp))
                        .background(MaterialTheme.colorScheme.surfaceContainerLow)
                        .padding(4.dp),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    listOf("checkin" to "Check-In", "checkout" to "Check-Out").forEach { (v, label) ->
                        val selected = tipe == v
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(20.dp))
                                .background(if (selected) Primary else Color.Transparent)
                                .clickable { tipe = v }
                                .padding(horizontal = 16.dp, vertical = 8.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = label,
                                color = if (selected) Color.White else MaterialTheme.colorScheme.onSurface,
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp
                            )
                        }
                    }
                }
            }

            // Live Camera Preview
            item {
                if (hasCameraPermission) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(240.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(Color.Black),
                        contentAlignment = Alignment.Center
                    ) {
                        CameraPreview(onBarcodeScanned = { raw ->
                            if (barcode != raw) {
                                barcode = raw
                                onScan(raw, tipe)
                            }
                        })
                        
                        // Scanner visual frame
                        Box(
                            modifier = Modifier
                                .size(160.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(Color.White.copy(0.08f))
                                .align(Alignment.Center)
                        )
                    }
                } else {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(180.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(MaterialTheme.colorScheme.surfaceContainerLow),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(16.dp)) {
                            Icon(Icons.Outlined.CameraAlt, null, tint = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.size(44.dp))
                            Spacer(Modifier.height(6.dp))
                            Text("Akses Kamera Dinonaktifkan", fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
                            Text("Izinkan akses kamera untuk scanning otomatis", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant, textAlign = TextAlign.Center)
                            Spacer(Modifier.height(10.dp))
                            Button(onClick = { permissionLauncher.launch(android.Manifest.permission.CAMERA) }, shape = RoundedCornerShape(8.dp)) {
                                Text("Berikan Izin")
                            }
                        }
                    }
                }
            }

            // Gallery Trigger Option
            item {
                OutlinedButton(
                    onClick = { imagePickerLauncher.launch("image/*") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(Icons.Default.PhotoLibrary, null)
                    Spacer(Modifier.width(8.dp))
                    Text("Pilih Gambar dari Galeri")
                }
            }

            item {
                HorizontalDivider(color = MaterialTheme.colorScheme.surfaceVariant, modifier = Modifier.padding(vertical = 4.dp))
            }

            // Manual Entry Fallback
            item {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("NIM Anggota (Manual)", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                    OutlinedTextField(
                        value = barcode,
                        onValueChange = { barcode = it },
                        placeholder = { Text("Contoh: 1010123") },
                        leadingIcon = { Icon(Icons.Outlined.Person, null) },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        singleLine = true
                    )
                    Spacer(Modifier.height(4.dp))
                    SipusButton(
                        text = "Proses Check-${if (tipe == "checkin") "In" else "Out"}",
                        onClick = { if (barcode.isNotBlank()) onScan(barcode, tipe) },
                        modifier = Modifier.fillMaxWidth(),
                        isLoading = state.isLoading,
                        icon = Icons.Default.Send
                    )
                }
            }

            // Display Results
            item {
                state.checkInResult?.let { r ->
                    val success = r.message?.contains("berhasil", ignoreCase = true) == true
                    Card(
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = if (success) StatusSuccess.copy(0.12f) else StatusError.copy(0.12f)
                        ),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(Modifier.padding(16.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    if (success) Icons.Default.CheckCircle else Icons.Default.Error,
                                    null,
                                    tint = if (success) StatusSuccess else StatusError
                                )
                                Spacer(Modifier.width(8.dp))
                                Text(
                                    text = if (success) "Proses Berhasil" else "Proses Gagal",
                                    fontWeight = FontWeight.Bold,
                                    color = if (success) StatusSuccess else StatusError
                                )
                            }
                            Spacer(Modifier.height(8.dp))
                            Text(r.message ?: "-", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.SemiBold)
                            r.user?.let { u ->
                                Spacer(Modifier.height(6.dp))
                                Text("Nama: ${u.nama}", style = MaterialTheme.typography.bodySmall)
                                Text("NIM: ${u.username}", style = MaterialTheme.typography.bodySmall)
                                Text("Pinjaman Aktif: ${r.pinjamanAktif} Buku", style = MaterialTheme.typography.bodySmall)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CameraPreview(onBarcodeScanned: (String) -> Unit) {
    val context = LocalContext.current
    val lifecycleOwner = androidx.lifecycle.compose.LocalLifecycleOwner.current
    val cameraProviderFuture = remember { ProcessCameraProvider.getInstance(context) }

    AndroidView(
        factory = { ctx ->
            val previewView = PreviewView(ctx)
            val executor = androidx.core.content.ContextCompat.getMainExecutor(ctx)
            cameraProviderFuture.addListener({
                val cameraProvider = cameraProviderFuture.get()
                val preview = Preview.Builder().build().apply {
                    setSurfaceProvider(previewView.surfaceProvider)
                }

                val imageAnalysis = ImageAnalysis.Builder()
                    .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                    .build()
                    .apply {
                        setAnalyzer(executor) { imageProxy ->
                            val mediaImage = imageProxy.image
                            if (mediaImage != null) {
                                val image = com.google.mlkit.vision.common.InputImage.fromMediaImage(
                                    mediaImage,
                                    imageProxy.imageInfo.rotationDegrees
                                )
                                val scanner = com.google.mlkit.vision.barcode.BarcodeScanning.getClient()
                                scanner.process(image)
                                    .addOnSuccessListener { barcodes ->
                                        val rawValue = barcodes.firstOrNull()?.rawValue
                                        if (rawValue != null) {
                                            onBarcodeScanned(rawValue)
                                        }
                                    }
                                    .addOnCompleteListener {
                                        imageProxy.close()
                                    }
                            } else {
                                imageProxy.close()
                            }
                        }
                    }

                val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
                try {
                    cameraProvider.unbindAll()
                    cameraProvider.bindToLifecycle(
                        lifecycleOwner,
                        cameraSelector,
                        preview,
                        imageAnalysis
                    )
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }, executor)
            previewView
        },
        modifier = Modifier.fillMaxSize()
    )
}

// ===== Approvals =====
@Composable
fun ApprovalsScreen(state: StaffUiState, onLoad: (String?) -> Unit, onAction: (Int, String, String) -> Unit, onBack: () -> Unit) {
    var tab by remember { mutableIntStateOf(0) }
    val tabs = listOf("Semua" to null, "Peminjaman" to "peminjaman", "Pengembalian" to "pengembalian", "Perpanjangan" to "perpanjangan")
    LaunchedEffect(tab) { onLoad(tabs[tab].second) }
    Scaffold(topBar = { SipusTopBar("Persetujuan", onBack = onBack) }) { padding ->
        Column(Modifier.fillMaxSize().padding(padding)) {
            ScrollableTabRow(selectedTabIndex = tab, edgePadding = 16.dp) {
                tabs.forEachIndexed { i, (t, _) -> Tab(selected = tab == i, onClick = { tab = i }, text = { Text(t) }) }
            }
            if (state.isLoading) LoadingScreen()
            else if (state.approvals.isEmpty()) EmptyScreen("Tidak ada yang menunggu")
            else LazyColumn(contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                items(state.approvals) { a -> ApprovalItem(a, onAction) }
            }
        }
    }
}

@Composable
private fun ApprovalItem(a: ApprovalDto, onAction: (Int, String, String) -> Unit) {
    val tipe = when {
        a.status == "Menunggu" -> "peminjaman"
        a.status == "Pengajuan_Kembali" -> "pengembalian"
        a.perpanjanganStatus == "requested" -> "perpanjangan"
        else -> "peminjaman"
    }
    Card(shape = RoundedCornerShape(14.dp), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerLow)) {
        Column(Modifier.padding(14.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Column(Modifier.weight(1f)) {
                    Text(a.buku?.judul ?: "-", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.SemiBold)
                    Text("${a.user?.nama} (${a.user?.username})", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
                StatusChip(if (tipe == "perpanjangan") "requested" else (a.status ?: "Menunggu"))
            }
            Spacer(Modifier.height(4.dp))
            Text("Pinjam: ${a.tglPinjam.formatDate()} • Batas: ${a.tglKembaliSeharusnya.formatDate()}",
                style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            if ((a.dendaEstimasi ?: 0) > 0) Text("Estimasi denda: ${a.dendaEstimasi?.toRupiah()}", style = MaterialTheme.typography.labelSmall, color = StatusError)
            Spacer(Modifier.height(8.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedButton(onClick = { onAction(a.idPeminjaman, "reject", tipe) }, modifier = Modifier.weight(1f), shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = StatusError)) { Text("Tolak") }
                Button(onClick = { onAction(a.idPeminjaman, "approve", tipe) }, modifier = Modifier.weight(1f), shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = StatusSuccess)) { Text("Setujui") }
            }
        }
    }
}

// ===== Search Patron =====
@Composable
fun SearchPatronScreen(state: StaffUiState, onSearch: (String) -> Unit, onPatronClick: (Int) -> Unit, onBack: () -> Unit) {
    var kw by remember { mutableStateOf("") }
    Scaffold(topBar = { SipusTopBar("Cari Anggota", onBack = onBack) }) { padding ->
        Column(Modifier.fillMaxSize().padding(padding)) {
            OutlinedTextField(value = kw, onValueChange = { kw = it }, placeholder = { Text("Nama / NIM...") },
                leadingIcon = { Icon(Icons.Default.Search, null) }, modifier = Modifier.fillMaxWidth().padding(16.dp),
                shape = RoundedCornerShape(14.dp), singleLine = true)
            LaunchedEffect(kw) { if (kw.length >= 2) onSearch(kw) }
            LazyColumn(contentPadding = PaddingValues(horizontal = 16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(state.patrons) { p ->
                    Card(Modifier.fillMaxWidth().clickable { onPatronClick(p.id) }, shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerLow)) {
                        Row(Modifier.padding(14.dp), verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Outlined.Person, null, tint = Primary)
                            Spacer(Modifier.width(12.dp))
                            Column(Modifier.weight(1f)) {
                                Text(p.nama ?: "-", fontWeight = FontWeight.SemiBold)
                                Text("${p.username} • ${p.role}", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            }
                            Icon(Icons.Default.ChevronRight, null)
                        }
                    }
                }
            }
        }
    }
}

// ===== Patron Profile =====
@Composable
fun PatronProfileScreen(state: StaffUiState, onBack: () -> Unit) {
    val p = state.patronProfile
    Scaffold(topBar = { SipusTopBar("Profil Anggota", onBack = onBack) }) { padding ->
        if (state.isLoading && p == null) LoadingScreen()
        else if (p == null) ErrorScreen("Anggota tidak ditemukan")
        else LazyColumn(Modifier.padding(padding), contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            item {
                Card(shape = RoundedCornerShape(16.dp)) {
                    Column(Modifier.padding(20.dp)) {
                        Text(p.nama ?: "-", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
                        Text("${p.username} • ${p.role}", color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Spacer(Modifier.height(12.dp))
                        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                            DashStat("Aktif", "${p.stats?.pinjamanAktif ?: 0}", Primary)
                            DashStat("Total", "${p.stats?.totalPinjam ?: 0}", Secondary)
                            DashStat("Denda", (p.stats?.totalDendaBelumLunas ?: 0.0).toRupiah(), StatusError)
                        }
                    }
                }
            }
            item { Text("Riwayat Terbaru", fontWeight = FontWeight.SemiBold) }
            items(p.riwayatTerbaru ?: emptyList()) { loan ->
                Card(shape = RoundedCornerShape(12.dp), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerLow)) {
                    Row(Modifier.padding(12.dp)) {
                        Column(Modifier.weight(1f)) {
                            Text(loan.judul ?: "-", style = MaterialTheme.typography.titleSmall)
                            Text(loan.tglPinjam.formatDate(), style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                        StatusChip(loan.status ?: "-")
                    }
                }
            }
        }
    }
}

// ===== Visitors =====
@Composable
fun VisitorsScreen(state: StaffUiState, onLoad: () -> Unit, onBack: () -> Unit) {
    LaunchedEffect(Unit) { onLoad() }
    Scaffold(topBar = { SipusTopBar("Pengunjung Saat Ini", onBack = onBack) }) { padding ->
        LazyColumn(Modifier.padding(padding), contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            state.visitorsData?.let { d ->
                item {
                    Card(shape = RoundedCornerShape(16.dp), colors = CardDefaults.cardColors(containerColor = Primary.copy(0.1f))) {
                        Row(Modifier.padding(16.dp), horizontalArrangement = Arrangement.spacedBy(24.dp)) {
                            DashStat("Di dalam", "${d.occupancy}", Primary)
                            DashStat("Check-in", "${d.totalCheckinToday}", StatusSuccess)
                            DashStat("Check-out", "${d.totalCheckoutToday}", StatusInfo)
                        }
                    }
                }
                items(d.visitors ?: emptyList()) { v ->
                    Card(shape = RoundedCornerShape(12.dp), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerLow)) {
                        Row(Modifier.padding(14.dp), verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Outlined.Person, null, tint = StatusSuccess)
                            Spacer(Modifier.width(12.dp))
                            Column(Modifier.weight(1f)) {
                                Text(v.nama ?: "-", fontWeight = FontWeight.SemiBold)
                                Text("${v.username} • ${v.durasi}", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            }
                        }
                    }
                }
            } ?: item { if (state.isLoading) LoadingScreen() }
        }
    }
}

// ===== Staff Profile & Logout Screen =====
@Composable
fun StaffProfileScreen(
    state: StaffUiState,
    onLoadProfile: () -> Unit,
    onLogout: () -> Unit,
    onBack: () -> Unit
) {
    LaunchedEffect(Unit) { onLoadProfile() }
    val p = state.profile
    Scaffold(topBar = { SipusTopBar("Profil Petugas", onBack = onBack) }) { padding ->
        if (state.isLoading && p == null) LoadingScreen()
        else if (p != null) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .clip(RoundedCornerShape(24.dp))
                        .background(Primary.copy(0.1f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Outlined.ManageAccounts, null, tint = Primary, modifier = Modifier.size(48.dp))
                }
                
                Card(shape = RoundedCornerShape(16.dp), modifier = Modifier.fillMaxWidth()) {
                    Column(Modifier.padding(20.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(p.nama ?: "-", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
                        Spacer(Modifier.height(4.dp))
                        Text("@${p.username}", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Spacer(Modifier.height(8.dp))
                        Box(modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(Secondary.copy(0.15f))
                            .padding(horizontal = 10.dp, vertical = 4.dp)) {
                            Text(p.role.uppercase(), style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold, color = Secondary)
                        }
                    }
                }

                Card(shape = RoundedCornerShape(16.dp), modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerLow)) {
                    Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("Email", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            Text(p.email ?: "-", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.SemiBold)
                        }
                        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("Status Akun", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            Text(p.status ?: "Aktif", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.SemiBold, color = StatusSuccess)
                        }
                    }
                }

                Spacer(Modifier.weight(1f))

                Button(
                    onClick = onLogout,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = StatusError)
                ) {
                    Icon(Icons.Default.Logout, null)
                    Spacer(Modifier.width(8.dp))
                    Text("Keluar (Logout)", fontWeight = FontWeight.Bold)
                }
            }
        } else {
            EmptyScreen("Gagal memuat profil petugas")
        }
    }
}
