package com.sipus.core.ui.component

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.sipus.core.ui.theme.*

@Composable
fun SipusTopBar(
    title: String,
    onBack: (() -> Unit)? = null,
    actions: @Composable RowScope.() -> Unit = {}
) {
    @OptIn(ExperimentalMaterial3Api::class)
    TopAppBar(
        title = { Text(title, fontWeight = FontWeight.SemiBold, maxLines = 1, overflow = TextOverflow.Ellipsis) },
        navigationIcon = {
            if (onBack != null) {
                IconButton(onClick = onBack) { Icon(Icons.Default.ArrowBack, "Kembali") }
            }
        },
        actions = actions,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surface,
            titleContentColor = MaterialTheme.colorScheme.onSurface
        )
    )
}

@Composable
fun GradientCard(
    modifier: Modifier = Modifier,
    gradient: Brush = Brush.linearGradient(listOf(GradientStart, GradientEnd)),
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        modifier = modifier.clip(RoundedCornerShape(20.dp)),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        Column(modifier = Modifier.background(gradient).padding(20.dp).fillMaxWidth(), content = content)
    }
}

@Composable
fun StatCard(
    title: String,
    value: String,
    icon: ImageVector,
    color: Color = Primary,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerLow),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp).animateContentSize(), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Box(modifier = Modifier.size(40.dp).clip(RoundedCornerShape(12.dp)).background(color.copy(alpha = 0.12f)), contentAlignment = Alignment.Center) {
                Icon(icon, null, tint = color, modifier = Modifier.size(22.dp))
            }
            Text(value, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
            Text(title, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}

@Composable
fun StatusChip(status: String, modifier: Modifier = Modifier) {
    val (bgColor, textColor, label) = when (status) {
        "Menunggu" -> Triple(StatusWarning.copy(0.15f), StatusWarning, "Menunggu")
        "Dipinjam" -> Triple(StatusInfo.copy(0.15f), StatusInfo, "Dipinjam")
        "Kembali" -> Triple(StatusSuccess.copy(0.15f), StatusSuccess, "Dikembalikan")
        "Ditolak" -> Triple(StatusError.copy(0.15f), StatusError, "Ditolak")
        "Pengajuan_Kembali" -> Triple(Secondary.copy(0.15f), Secondary, "Pengajuan Kembali")
        "requested" -> Triple(StatusWarning.copy(0.15f), StatusWarning, "Diminta")
        "approved" -> Triple(StatusSuccess.copy(0.15f), StatusSuccess, "Disetujui")
        "rejected" -> Triple(StatusError.copy(0.15f), StatusError, "Ditolak")
        "unpaid" -> Triple(StatusError.copy(0.15f), StatusError, "Belum Bayar")
        "partial" -> Triple(StatusWarning.copy(0.15f), StatusWarning, "Sebagian")
        "paid" -> Triple(StatusSuccess.copy(0.15f), StatusSuccess, "Lunas")
        "disputed" -> Triple(Tertiary.copy(0.15f), Tertiary, "Dispute")
        "waived" -> Triple(StatusInfo.copy(0.15f), StatusInfo, "Dihapuskan")
        else -> Triple(Color.Gray.copy(0.15f), Color.Gray, status)
    }
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(8.dp),
        color = bgColor
    ) {
        Text(label, modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
            style = MaterialTheme.typography.labelSmall, color = textColor, fontWeight = FontWeight.SemiBold)
    }
}

@Composable
fun LoadingScreen(message: String = "Memuat...") {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(12.dp)) {
            CircularProgressIndicator(color = Primary)
            Text(message, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}

@Composable
fun ErrorScreen(message: String, onRetry: (() -> Unit)? = null) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Icon(Icons.Default.ErrorOutline, null, tint = StatusError, modifier = Modifier.size(48.dp))
            Text(message, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
            if (onRetry != null) {
                Button(onClick = onRetry, colors = ButtonDefaults.buttonColors(containerColor = Primary)) {
                    Text("Coba Lagi")
                }
            }
        }
    }
}

@Composable
fun EmptyScreen(message: String = "Tidak ada data", icon: ImageVector = Icons.Default.Inbox) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Icon(icon, null, tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(0.5f), modifier = Modifier.size(56.dp))
            Text(message, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}

@Composable
fun SipusButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    isLoading: Boolean = false,
    containerColor: Color = Primary,
    icon: ImageVector? = null
) {
    Button(
        onClick = onClick,
        modifier = modifier.height(50.dp),
        enabled = enabled && !isLoading,
        shape = RoundedCornerShape(14.dp),
        colors = ButtonDefaults.buttonColors(containerColor = containerColor)
    ) {
        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.size(20.dp), color = Color.White, strokeWidth = 2.dp)
        } else {
            if (icon != null) Icon(icon, null, modifier = Modifier.size(18.dp))
            if (icon != null) Spacer(Modifier.width(8.dp))
            Text(text, fontWeight = FontWeight.SemiBold)
        }
    }
}
