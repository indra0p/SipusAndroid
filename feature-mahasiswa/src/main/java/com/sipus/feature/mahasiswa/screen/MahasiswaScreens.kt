package com.sipus.feature.mahasiswa.screen

import android.content.ContentValues
import android.graphics.Bitmap
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.google.zxing.BarcodeFormat
import com.google.zxing.qrcode.QRCodeWriter
import com.sipus.core.data.remote.dto.*
import com.sipus.core.ui.component.*
import com.sipus.core.ui.theme.*
import com.sipus.core.util.toRupiah
import com.sipus.core.util.formatDate
import com.sipus.feature.mahasiswa.MhsUiState

// Helper to save QR to gallery
fun saveBitmapToGallery(context: android.content.Context, bitmap: Bitmap, filename: String) {
    try {
        val resolver = context.contentResolver
        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, "$filename.png")
            put(MediaStore.MediaColumns.MIME_TYPE, "image/png")
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
                put(MediaStore.MediaColumns.RELATIVE_PATH, android.os.Environment.DIRECTORY_PICTURES + "/SipusApp")
                put(MediaStore.MediaColumns.IS_PENDING, 1)
            }
        }

        val imageUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
        if (imageUri != null) {
            resolver.openOutputStream(imageUri).use { out ->
                if (out != null) {
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
                        contentValues.clear()
                        contentValues.put(MediaStore.MediaColumns.IS_PENDING, 0)
                        resolver.update(imageUri, contentValues, null, null)
                    }
                    Toast.makeText(context, "QR Code berhasil disimpan ke Galeri", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(context, "Gagal mengunduh gambar", Toast.LENGTH_SHORT).show()
                }
            }
        } else {
            Toast.makeText(context, "Gagal membuat entri gambar", Toast.LENGTH_SHORT).show()
        }
    } catch (e: Exception) {
        Toast.makeText(context, "Error: ${e.localizedMessage}", Toast.LENGTH_LONG).show()
    }
}

// ===== Dashboard =====
@Composable
fun MhsDashboardScreen(
    state: MhsUiState,
    onNavigate: (String) -> Unit,
    onRefresh: () -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Welcome Header
        item {
            GradientCard(
                gradient = Brush.linearGradient(listOf(GradientStart, GradientEnd))
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Column(Modifier.weight(1f)) {
                        Text(
                            text = "Halo, ${state.userName}!",
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(
                            text = "Selamat datang kembali di SIPUS",
                            color = Color.White.copy(0.85f),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                    IconButton(
                        onClick = { onNavigate("card") },
                        modifier = Modifier.background(Color.White.copy(0.15f), CircleShape)
                    ) {
                        Icon(Icons.Outlined.QrCode, null, tint = Color.White)
                    }
                }

                state.dashboard?.let { d ->
                    Spacer(Modifier.height(16.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        QuickStat("Pinjaman", "${d.totalDipinjam}", Color.White)
                        QuickStat("Riwayat", "${d.totalRiwayat}", Color.White)
                        QuickStat("Denda", d.totalDenda.toRupiah(), Color.White)
                    }
                }
            }
        }

        // Stats Cards
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                StatCard(
                    title = "Buku Koleksi",
                    value = "${state.dashboard?.totalBuku ?: 0}",
                    icon = Icons.Outlined.MenuBook,
                    color = Primary,
                    modifier = Modifier.weight(1f)
                )
                StatCard(
                    title = "Pinjaman Aktif",
                    value = "${state.dashboard?.totalDipinjam ?: 0}",
                    icon = Icons.Outlined.BookmarkBorder,
                    color = Secondary,
                    modifier = Modifier.weight(1f)
                )
            }
        }

        // Action Menu Grid
        item {
            Text(
                text = "Layanan Perpustakaan",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
        }

        item {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    MenuCard(
                        title = "Katalog Buku",
                        icon = Icons.Outlined.LibraryBooks,
                        subtitle = "Cari & ajukan pinjam",
                        tint = Primary,
                        modifier = Modifier.weight(1f)
                    ) { onNavigate("books") }

                    MenuCard(
                        title = "Kartu Digital",
                        icon = Icons.Outlined.CardMembership,
                        subtitle = "Kartu anggota digital",
                        tint = Secondary,
                        modifier = Modifier.weight(1f)
                    ) { onNavigate("card") }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    MenuCard(
                        title = "Pinjaman Saya",
                        icon = Icons.Outlined.Assignment,
                        subtitle = "Status & perpanjangan",
                        tint = StatusInfo,
                        modifier = Modifier.weight(1f)
                    ) { onNavigate("loans") }

                    MenuCard(
                        title = "Riwayat",
                        icon = Icons.Outlined.History,
                        subtitle = "Daftar peminjaman lalu",
                        tint = StatusSuccess,
                        modifier = Modifier.weight(1f)
                    ) { onNavigate("history") }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    MenuCard(
                        title = "Denda & Sanksi",
                        icon = Icons.Outlined.Payment,
                        subtitle = "Denda berjalan & dispute",
                        tint = StatusError,
                        modifier = Modifier.weight(1f)
                    ) { onNavigate("fines") }

                    MenuCard(
                        title = "Riwayat Kunjungan",
                        icon = Icons.Outlined.DirectionsWalk,
                        subtitle = "Log absensi perpustakaan",
                        tint = GradientCyan,
                        modifier = Modifier.weight(1f)
                    ) { onNavigate("visits") }
                }
            }
        }
    }
}

@Composable
private fun QuickStat(label: String, value: String, color: Color) {
    Column {
        Text(
            text = value,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = color
        )
        Text(
            text = label,
            fontSize = 11.sp,
            color = color.copy(0.8f)
        )
    }
}

@Composable
private fun MenuCard(
    title: String,
    icon: ImageVector,
    subtitle: String,
    tint: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier.clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerLow
        ),
        elevation = CardDefaults.cardElevation(1.dp)
    ) {
        Column(Modifier.padding(16.dp)) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(tint.copy(0.1f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(icon, null, tint = tint, modifier = Modifier.size(20.dp))
            }
            Spacer(Modifier.height(12.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = subtitle,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

// ===== Library Card with QR =====
@Composable
fun LibraryCardScreen(
    state: MhsUiState,
    onBack: () -> Unit
) {
    val context = LocalContext.current

    Scaffold(
        topBar = { SipusTopBar("Kartu Perpustakaan", onBack = onBack) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            // Card Header
            GradientCard(
                gradient = Brush.linearGradient(listOf(GradientStart, Color(0xFF7C3AED)))
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Outlined.LocalLibrary,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(32.dp)
                    )
                    Spacer(Modifier.width(12.dp))
                    Column {
                        Text(
                            text = "SIPUS",
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                            color = Color.White
                        )
                        Text(
                            text = "Kartu Anggota Digital",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.White.copy(0.8f)
                        )
                    }
                }

                Spacer(Modifier.height(24.dp))

                Text(
                    text = state.barcode?.nama ?: state.userName,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Text(
                    text = "NIM: ${state.barcode?.barcodeValue ?: "-"}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White.copy(0.8f)
                )
            }

            // QR Code
            state.barcode?.barcodeValue?.let { value ->
                Card(
                    shape = RoundedCornerShape(20.dp),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Scan QR Code ini di perpustakaan",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(Modifier.height(16.dp))

                        val bitmap = remember(value) { generateQR(value) }
                        bitmap?.let {
                            Image(
                                bitmap = it.asImageBitmap(),
                                contentDescription = "QR Code",
                                modifier = Modifier.size(200.dp)
                            )
                            Spacer(Modifier.height(16.dp))
                            Text(
                                text = value,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = Primary
                            )
                            Spacer(Modifier.height(12.dp))
                            Button(
                                onClick = { saveBitmapToGallery(context, it, "QR_SIPUS_$value") },
                                shape = RoundedCornerShape(10.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = Secondary)
                            ) {
                                Icon(Icons.Default.Download, null, Modifier.size(16.dp))
                                Spacer(Modifier.width(8.dp))
                                Text("Unduh QR Code")
                            }
                        }
                    }
                }
            } ?: LoadingScreen("Memuat barcode...")
        }
    }
}

private fun generateQR(text: String): Bitmap? {
    return try {
        val writer = QRCodeWriter()
        val matrix = writer.encode(text, BarcodeFormat.QR_CODE, 512, 512)
        val bmp = Bitmap.createBitmap(512, 512, Bitmap.Config.RGB_565)
        for (x in 0 until 512) {
            for (y in 0 until 512) {
                bmp.setPixel(
                    x, y,
                    if (matrix[x, y]) android.graphics.Color.BLACK
                    else android.graphics.Color.WHITE
                )
            }
        }
        bmp
    } catch (_: Exception) {
        null
    }
}

// ===== Book Catalog (Katalog Buku) =====
@Composable
fun BookCatalogScreen(
    state: MhsUiState,
    onSearch: (String) -> Unit,
    onBookClick: (Int) -> Unit,
    onBack: () -> Unit
) {
    var query by remember { mutableStateOf(state.searchQuery) }

    Scaffold(
        topBar = { SipusTopBar("Katalog Buku", onBack = onBack) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            OutlinedTextField(
                value = query,
                onValueChange = { query = it },
                placeholder = { Text("Cari judul buku, pengarang, jenis...") },
                leadingIcon = { Icon(Icons.Default.Search, null) },
                trailingIcon = {
                    if (query.isNotEmpty()) {
                        IconButton(onClick = { query = ""; onSearch("") }) {
                            Icon(Icons.Default.Clear, null)
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(14.dp),
                singleLine = true
            )

            LaunchedEffect(query) {
                if (query.length >= 2 || query.isEmpty()) onSearch(query)
            }

            when {
                state.isLoading -> LoadingScreen()
                state.books.isEmpty() -> EmptyScreen(
                    message = "Tidak ada buku ditemukan di katalog",
                    icon = Icons.Outlined.MenuBook
                )
                else -> LazyColumn(
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(state.books) { book ->
                        BookItem(book) { onBookClick(book.idBuku) }
                    }
                }
            }
        }
    }
}

@Composable
private fun BookItem(book: BookDto, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerLow
        ),
        elevation = CardDefaults.cardElevation(0.5.dp)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Book cover image with Coil AsyncImage
            if (!book.sampul.isNullOrBlank()) {
                AsyncImage(
                    model = book.sampul,
                    contentDescription = book.judul,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .width(60.dp)
                        .height(84.dp)
                        .clip(RoundedCornerShape(8.dp))
                )
            } else {
                // Stylish gradient placeholder for missing covers
                Box(
                    modifier = Modifier
                        .width(60.dp)
                        .height(84.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(
                            Brush.linearGradient(
                                listOf(Primary.copy(0.2f), Secondary.copy(0.2f))
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.MenuBook,
                            contentDescription = null,
                            tint = Primary,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(Modifier.height(4.dp))
                        Text(
                            text = book.judul.take(2).uppercase(),
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = Primary,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }

            Spacer(Modifier.width(14.dp))

            Column(Modifier.weight(1f)) {
                Text(
                    text = book.judul,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = book.pengarang ?: "-",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                book.jenisBuku?.let { jenis ->
                    if (jenis.isNotBlank()) {
                        Text(
                            text = jenis,
                            style = MaterialTheme.typography.labelSmall,
                            color = Secondary,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
                Spacer(Modifier.height(4.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = if (book.stok > 0) "Tersedia (${book.stok})" else "Stok Habis",
                        style = MaterialTheme.typography.labelSmall,
                        color = if (book.stok > 0) StatusSuccess else StatusError,
                        fontWeight = FontWeight.SemiBold
                    )
                    if (book.sedangDipinjam == true) {
                        Spacer(Modifier.width(8.dp))
                        StatusChip("Dipinjam")
                    }
                }
            }

            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

// ===== Book Detail =====
@Composable
fun BookDetailScreen(
    state: MhsUiState,
    onBack: () -> Unit,
    onBorrow: (Int) -> Unit
) {
    val book = state.bookDetail

    Scaffold(
        topBar = { SipusTopBar("Detail Buku", onBack = onBack) }
    ) { padding ->
        when {
            state.isLoading && book == null -> LoadingScreen()
            book == null -> ErrorScreen("Buku tidak ditemukan")
            else -> LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                // Elegant Cover & Header Section
                item {
                    Row(modifier = Modifier.fillMaxWidth()) {
                        if (!book.sampul.isNullOrBlank()) {
                            AsyncImage(
                                model = book.sampul,
                                contentDescription = book.judul,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .width(120.dp)
                                    .height(168.dp)
                                    .clip(RoundedCornerShape(12.dp))
                            )
                        } else {
                            Box(
                                modifier = Modifier
                                    .width(120.dp)
                                    .height(168.dp)
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(
                                        Brush.linearGradient(
                                            listOf(Primary.copy(0.2f), Secondary.copy(0.2f))
                                        )
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Outlined.MenuBook,
                                    contentDescription = null,
                                    tint = Primary,
                                    modifier = Modifier.size(48.dp)
                                )
                            }
                        }

                        Spacer(Modifier.width(16.dp))

                        Column(Modifier.weight(1f)) {
                            Text(
                                text = book.judul,
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold,
                                maxLines = 3,
                                overflow = TextOverflow.Ellipsis
                            )
                            Spacer(Modifier.height(4.dp))
                            Text(
                                text = "Oleh: ${book.pengarang ?: "-"}",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            book.jenisBuku?.let { jenis ->
                                if (jenis.isNotBlank()) {
                                    Spacer(Modifier.height(4.dp))
                                    Box(
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(6.dp))
                                            .background(Secondary.copy(0.15f))
                                            .padding(horizontal = 8.dp, vertical = 2.dp)
                                    ) {
                                        Text(
                                            text = jenis,
                                            style = MaterialTheme.typography.labelSmall,
                                            color = Secondary,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                // Specs Card
                item {
                    Card(
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceContainerLow
                        )
                    ) {
                        Column(Modifier.padding(16.dp)) {
                            Text(
                                text = "Informasi Buku",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold,
                                color = Primary
                            )
                            Spacer(Modifier.height(10.dp))
                            InfoRow("Penerbit", book.penerbit ?: "-")
                            InfoRow("Barcode", book.barcode ?: "-")
                            InfoRow("Sisa Stok", "${book.stok} Buku")

                            book.sinopsis?.let {
                                if (it.isNotBlank()) {
                                    Spacer(Modifier.height(12.dp))
                                    Text(
                                        text = "Sinopsis",
                                        style = MaterialTheme.typography.labelMedium,
                                        fontWeight = FontWeight.SemiBold,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                    Spacer(Modifier.height(4.dp))
                                    Text(
                                        text = it,
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = MaterialTheme.colorScheme.onSurface,
                                        lineHeight = 20.sp
                                    )
                                }
                            }
                        }
                    }
                }

                // Availability Status
                item {
                    book.availability?.let { avail ->
                        val statusColor = if (avail.tersedia) StatusSuccess else StatusError

                        Card(
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = statusColor.copy(0.1f)
                            )
                        ) {
                            Row(
                                modifier = Modifier.padding(16.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = if (avail.tersedia) Icons.Default.CheckCircle else Icons.Default.Cancel,
                                    contentDescription = null,
                                    tint = statusColor
                                )
                                Spacer(Modifier.width(12.dp))
                                Column {
                                    Text(
                                        text = if (avail.tersedia) "Tersedia untuk Dipinjam" else "Stok Habis / Tidak Tersedia",
                                        fontWeight = FontWeight.SemiBold,
                                        color = statusColor
                                    )
                                    Text(
                                        text = "${avail.stokTersedia} unit tersedia di rak, ${avail.sedangDipinjam} unit dipinjam",
                                        style = MaterialTheme.typography.bodySmall
                                    )
                                }
                            }
                        }
                    }
                }

                // Borrow Action Button
                item {
                    val canBorrow = book.availability?.tersedia == true && book.myLoan == null
                    Button(
                        onClick = { onBorrow(book.idBuku) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        enabled = canBorrow && !state.isLoading,
                        shape = RoundedCornerShape(14.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Primary)
                    ) {
                        Icon(Icons.Default.BookmarkAdd, null, Modifier.size(20.dp))
                        Spacer(Modifier.width(8.dp))
                        Text(
                            text = if (book.myLoan != null) "Sudah Dipinjam/Diajukan" else "Ajukan Peminjaman",
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun InfoRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.width(90.dp)
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium
        )
    }
}

// ===== Loans Screen =====
@Composable
fun LoansScreen(
    state: MhsUiState,
    onLoadLoans: (String?) -> Unit,
    onReturn: (Int, String) -> Unit,
    onExtend: (Int) -> Unit,
    onBack: () -> Unit
) {
    var selectedTab by remember { mutableIntStateOf(0) }
    val tabs = listOf(
        "Semua" to null,
        "Aktif" to "active",
        "Pengajuan" to "requests",
        "Pengembalian" to "returns"
    )

    LaunchedEffect(selectedTab) {
        onLoadLoans(tabs[selectedTab].second)
    }

    Scaffold(
        topBar = { SipusTopBar("Peminjaman Saya", onBack = onBack) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            ScrollableTabRow(
                selectedTabIndex = selectedTab,
                edgePadding = 16.dp
            ) {
                tabs.forEachIndexed { i, (title, _) ->
                    Tab(
                        selected = selectedTab == i,
                        onClick = { selectedTab = i },
                        text = { Text(title) }
                    )
                }
            }

            when {
                state.isLoading -> LoadingScreen()
                state.loans.isEmpty() -> EmptyScreen("Tidak ada peminjaman")
                else -> LazyColumn(
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(state.loans) { loan ->
                        LoanItem(
                            loan = loan,
                            onReturn = { onReturn(loan.idPeminjaman, "baik") },
                            onExtend = { onExtend(loan.idPeminjaman) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun LoanItem(
    loan: LoanDto,
    onReturn: () -> Unit,
    onExtend: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerLow
        )
    ) {
        Column(Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Column(Modifier.weight(1f)) {
                    Text(
                        text = loan.judul ?: "-",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = loan.pengarang ?: "-",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                StatusChip(loan.status ?: "Menunggu")
            }

            Spacer(Modifier.height(8.dp))

            Row {
                Text(
                    text = "Pinjam: ${loan.tglPinjam.formatDate()}",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(Modifier.width(16.dp))
                Text(
                    text = "Batas: ${loan.tglKembaliSeharusnya.formatDate()}",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            if ((loan.dendaBerjalan ?: 0) > 0) {
                Text(
                    text = "Denda berjalan: ${loan.dendaBerjalan?.toRupiah()}",
                    style = MaterialTheme.typography.labelSmall,
                    color = StatusError,
                    fontWeight = FontWeight.SemiBold
                )
            }

            if (loan.status == "Dipinjam") {
                Spacer(Modifier.height(8.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedButton(
                        onClick = onExtend,
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(10.dp),
                        enabled = loan.perpanjanganStatus != "requested"
                    ) {
                        Text("Perpanjang", style = MaterialTheme.typography.labelSmall)
                    }
                    Button(
                        onClick = onReturn,
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Primary)
                    ) {
                        Text("Kembalikan", style = MaterialTheme.typography.labelSmall)
                    }
                }
            }
        }
    }
}

// ===== History =====
@Composable
fun HistoryScreen(
    state: MhsUiState,
    onLoad: () -> Unit,
    onBack: () -> Unit
) {
    LaunchedEffect(Unit) { onLoad() }

    Scaffold(
        topBar = { SipusTopBar("Riwayat Peminjaman", onBack = onBack) }
    ) { padding ->
        when {
            state.isLoading -> LoadingScreen()
            state.history.isEmpty() -> EmptyScreen("Belum ada riwayat")
            else -> LazyColumn(
                modifier = Modifier.padding(padding),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(state.history) { loan ->
                    Card(
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceContainerLow
                        )
                    ) {
                        Row(
                            modifier = Modifier.padding(14.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(Modifier.weight(1f)) {
                                Text(
                                    text = loan.judul ?: "-",
                                    style = MaterialTheme.typography.titleSmall,
                                    fontWeight = FontWeight.SemiBold
                                )
                                Text(
                                    text = "${loan.tglPinjam.formatDate()} — ${loan.tglKembaliSeharusnya.formatDate()}",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                if ((loan.denda ?: 0) > 0) {
                                    Text(
                                        text = "Denda: ${loan.denda?.toRupiah()}",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = StatusError
                                    )
                                }
                            }
                            StatusChip(loan.status ?: "Kembali")
                        }
                    }
                }
            }
        }
    }
}

// ===== Fines Screen =====
@Composable
fun FinesScreen(
    state: MhsUiState,
    onLoad: () -> Unit,
    onPay: (Int, Double) -> Unit,
    onDispute: (Int, String) -> Unit,
    onBack: () -> Unit
) {
    LaunchedEffect(Unit) { onLoad() }

    Scaffold(
        topBar = { SipusTopBar("Denda & Sanksi", onBack = onBack) }
    ) { padding ->
        if (state.isLoading) {
            LoadingScreen()
        } else {
            val denda = state.finesData
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                // Balance Header
                item {
                    GradientCard(
                        gradient = Brush.linearGradient(listOf(StatusError, Color(0xFFC084FC)))
                    ) {
                        Text(
                            text = "Total Denda Belum Lunas",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.White.copy(0.9f)
                        )
                        Spacer(Modifier.height(4.dp))
                        Text(
                            text = (denda?.totalDenda ?: 0.0).toRupiah(),
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        if ((denda?.overdueBerjalan ?: 0.0) > 0.0) {
                            Spacer(Modifier.height(8.dp))
                            Text(
                                text = "Termasuk estimasi terlambat: ${denda?.overdueBerjalan?.toRupiah()}",
                                style = MaterialTheme.typography.labelSmall,
                                color = Color.White.copy(0.85f)
                            )
                        }
                    }
                }

                // Penalty List
                val penalties = denda?.penalties ?: emptyList()
                if (penalties.isEmpty()) {
                    item {
                        EmptyScreen("Bebas sanksi & denda", Icons.Outlined.CheckCircle)
                    }
                } else {
                    item {
                        Text(
                            text = "Rincian Denda",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                    items(penalties) { penalty ->
                        var showPayDialog by remember { mutableStateOf(false) }
                        var showDisputeDialog by remember { mutableStateOf(false) }
                        var payAmount by remember { mutableStateOf("${penalty.sisa.toInt()}") }
                        var disputeReason by remember { mutableStateOf("") }

                        Card(
                            shape = RoundedCornerShape(14.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.surfaceContainerLow
                            )
                        ) {
                            Column(Modifier.padding(14.dp)) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Column(Modifier.weight(1f)) {
                                        Text(
                                            text = penalty.buku ?: "Denda Layanan",
                                            style = MaterialTheme.typography.titleSmall,
                                            fontWeight = FontWeight.SemiBold
                                        )
                                        Text(
                                            text = penalty.catatan ?: "Sanksi administrasi",
                                            style = MaterialTheme.typography.bodySmall,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }

                                    val isPaid = penalty.status == "paid"
                                    Box(
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(8.dp))
                                            .background(
                                                if (isPaid) StatusSuccess.copy(0.15f)
                                                else StatusError.copy(0.15f)
                                            )
                                            .padding(horizontal = 8.dp, vertical = 4.dp)
                                    ) {
                                        Text(
                                            text = when (penalty.status) {
                                                "paid" -> "LUNAS"
                                                "dispute" -> "DISPUTE"
                                                else -> "BELUM LUNAS"
                                            },
                                            style = MaterialTheme.typography.labelSmall,
                                            fontWeight = FontWeight.Bold,
                                            color = if (isPaid) StatusSuccess else StatusError
                                        )
                                    }
                                }

                                Spacer(Modifier.height(8.dp))

                                Row {
                                    Column(Modifier.weight(1f)) {
                                        Text(
                                            text = "Jumlah",
                                            style = MaterialTheme.typography.labelSmall,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                        Text(
                                            text = penalty.jumlah.toRupiah(),
                                            style = MaterialTheme.typography.bodyMedium,
                                            fontWeight = FontWeight.SemiBold
                                        )
                                    }
                                    Column(Modifier.weight(1f)) {
                                        Text(
                                            text = "Sisa",
                                            style = MaterialTheme.typography.labelSmall,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                        Text(
                                            text = penalty.sisa.toRupiah(),
                                            style = MaterialTheme.typography.bodyMedium,
                                            fontWeight = FontWeight.SemiBold,
                                            color = StatusError
                                        )
                                    }
                                }

                                if (penalty.status != "paid" && penalty.status != "dispute") {
                                    Spacer(Modifier.height(12.dp))
                                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                        OutlinedButton(
                                            onClick = { showDisputeDialog = true },
                                            modifier = Modifier.weight(1f),
                                            shape = RoundedCornerShape(10.dp),
                                            colors = ButtonDefaults.outlinedButtonColors(contentColor = Primary)
                                        ) {
                                            Text(
                                                text = "Ajukan Sanggahan",
                                                style = MaterialTheme.typography.labelSmall
                                            )
                                        }

                                        Button(
                                            onClick = { showPayDialog = true },
                                            modifier = Modifier.weight(1f),
                                            shape = RoundedCornerShape(10.dp),
                                            colors = ButtonDefaults.buttonColors(containerColor = Primary)
                                        ) {
                                            Text(
                                                text = "Simulasi Bayar",
                                                style = MaterialTheme.typography.labelSmall
                                            )
                                        }
                                    }
                                }
                            }
                        }

                        // Pay Dialog
                        if (showPayDialog) {
                            AlertDialog(
                                onDismissRequest = { showPayDialog = false },
                                title = { Text("Simulasi Pembayaran") },
                                text = {
                                    Column {
                                        Text("Bayar denda ini untuk keperluan simulasi.", style = MaterialTheme.typography.bodyMedium)
                                        Spacer(Modifier.height(8.dp))
                                        OutlinedTextField(
                                            value = payAmount,
                                            onValueChange = { payAmount = it },
                                            label = { Text("Jumlah Pembayaran (Rp)") },
                                            shape = RoundedCornerShape(10.dp)
                                        )
                                    }
                                },
                                confirmButton = {
                                    Button(onClick = {
                                        val amt = payAmount.toDoubleOrNull() ?: penalty.sisa
                                        onPay(penalty.id, amt)
                                        showPayDialog = false
                                    }) { Text("Proses Bayar") }
                                },
                                dismissButton = {
                                    TextButton(onClick = { showPayDialog = false }) { Text("Batal") }
                                }
                            )
                        }

                        // Dispute Dialog
                        if (showDisputeDialog) {
                            AlertDialog(
                                onDismissRequest = { showDisputeDialog = false },
                                title = { Text("Ajukan Sanggahan") },
                                text = {
                                    Column {
                                        Text("Tulis alasan mengapa Anda menyanggah tagihan denda ini.", style = MaterialTheme.typography.bodyMedium)
                                        Spacer(Modifier.height(8.dp))
                                        OutlinedTextField(
                                            value = disputeReason,
                                            onValueChange = { disputeReason = it },
                                            label = { Text("Alasan Sanggahan") },
                                            modifier = Modifier.fillMaxWidth(),
                                            shape = RoundedCornerShape(10.dp)
                                        )
                                    }
                                },
                                confirmButton = {
                                    Button(onClick = {
                                        if (disputeReason.isNotBlank()) {
                                            onDispute(penalty.id, disputeReason)
                                            showDisputeDialog = false
                                        }
                                    }) { Text("Kirim Sanggahan") }
                                },
                                dismissButton = {
                                    TextButton(onClick = { showDisputeDialog = false }) { Text("Batal") }
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

// ===== Visit Screen =====
@Composable
fun VisitScreen(
    state: MhsUiState,
    onLoadStatus: () -> Unit,
    onLoadHistory: () -> Unit,
    onBack: () -> Unit
) {
    LaunchedEffect(Unit) {
        onLoadStatus()
        onLoadHistory()
    }

    Scaffold(
        topBar = { SipusTopBar("Riwayat Kunjungan", onBack = onBack) }
    ) { padding ->
        if (state.isLoading && state.visitHistory.isEmpty()) {
            LoadingScreen()
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                // Live Library Status
                item {
                    val inside = state.visitStatus?.isInside == true
                    val statusColor = if (inside) StatusSuccess else Primary

                    Card(
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = statusColor.copy(0.1f)
                        )
                    ) {
                        Row(
                            modifier = Modifier.padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = if (inside) Icons.Default.MeetingRoom else Icons.Default.DoorBack,
                                contentDescription = null,
                                tint = statusColor,
                                modifier = Modifier.size(36.dp)
                            )
                            Spacer(Modifier.width(16.dp))
                            Column {
                                Text(
                                    text = if (inside) "Status: Di Dalam Perpustakaan"
                                           else "Status: Di Luar Perpustakaan",
                                    fontWeight = FontWeight.Bold
                                )
                                if (inside) {
                                    Text(
                                        text = "Masuk pada: ${state.visitStatus?.lastCheckin?.formatDate() ?: "-"} (${state.visitStatus?.durationMinutes} Menit)",
                                        style = MaterialTheme.typography.bodySmall
                                    )
                                } else {
                                    Text(
                                        text = "Silakan scan QR kartu Anda di meja absensi petugas.",
                                        style = MaterialTheme.typography.bodySmall
                                    )
                                }
                            }
                        }
                    }
                }

                // Log History
                item {
                    Text(
                        text = "Log Absensi Kunjungan",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                if (state.visitHistory.isEmpty()) {
                    item { EmptyScreen("Belum ada riwayat kunjungan") }
                } else {
                    items(state.visitHistory) { log ->
                        val isCheckin = log.tipe == "checkin"

                        Card(
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.surfaceContainerLow
                            )
                        ) {
                            Row(
                                modifier = Modifier.padding(14.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = if (isCheckin) Icons.Default.Login else Icons.Default.Logout,
                                    contentDescription = null,
                                    tint = if (isCheckin) StatusSuccess else StatusInfo
                                )
                                Spacer(Modifier.width(12.dp))
                                Column(Modifier.weight(1f)) {
                                    Text(
                                        text = if (isCheckin) "Check-In Kunjungan" else "Check-Out Kunjungan",
                                        fontWeight = FontWeight.SemiBold
                                    )
                                    Text(
                                        text = log.waktu?.formatDate() ?: "-",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(6.dp))
                                        .background(MaterialTheme.colorScheme.secondaryContainer)
                                        .padding(horizontal = 8.dp, vertical = 2.dp)
                                ) {
                                    Text(
                                        text = log.metode?.uppercase() ?: "QR",
                                        style = MaterialTheme.typography.labelSmall,
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
}
