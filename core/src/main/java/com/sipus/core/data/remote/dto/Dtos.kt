package com.sipus.core.data.remote.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/** Generic API response wrapper */
@JsonClass(generateAdapter = true)
data class ApiResponse<T>(
    @Json(name = "status") val status: String,
    @Json(name = "data") val data: T? = null,
    @Json(name = "message") val message: String? = null,
    @Json(name = "total") val total: Int? = null,
    @Json(name = "unread") val unread: Int? = null
)

// ===== Auth =====
@JsonClass(generateAdapter = true)
data class LoginRequest(val username: String, val password: String)

@JsonClass(generateAdapter = true)
data class RegisterRequest(val username: String, val nama: String, val password: String, val email: String, val jenkel: String)

@JsonClass(generateAdapter = true)
data class AuthData(
    val token: String,
    val user: UserDto
)

@JsonClass(generateAdapter = true)
data class UserDto(
    val id: Int,
    val username: String,
    val nama: String? = null,
    val email: String? = null,
    val jenkel: String? = null,
    val role: String,
    val status: String? = null,
    val foto: String? = null,
    @Json(name = "borrowing_limit") val borrowingLimit: Int? = null
)

// ===== Profile =====
@JsonClass(generateAdapter = true)
data class ProfileDto(
    val id: Int,
    val username: String,
    val nama: String? = null,
    val email: String? = null,
    val jenkel: String? = null,
    val role: String,
    val status: String? = null,
    @Json(name = "borrowing_limit") val borrowingLimit: Int? = null,
    val foto: String? = null,
    val stats: ProfileStatsDto? = null
)

@JsonClass(generateAdapter = true)
data class ProfileStatsDto(
    @Json(name = "total_pinjam") val totalPinjam: Int = 0,
    @Json(name = "pinjaman_aktif") val pinjamanAktif: Int = 0,
    val selesai: Int = 0,
    @Json(name = "denda_belum_lunas") val dendaBelumLunas: Double = 0.0
)

// ===== Barcode =====
@JsonClass(generateAdapter = true)
data class BarcodeDto(
    @Json(name = "barcode_value") val barcodeValue: String,
    val nama: String,
    val role: String
)

// ===== Dashboard =====
@JsonClass(generateAdapter = true)
data class DashboardDto(
    @Json(name = "total_buku") val totalBuku: Int = 0,
    @Json(name = "total_dipinjam") val totalDipinjam: Int = 0,
    @Json(name = "total_riwayat") val totalRiwayat: Int = 0,
    @Json(name = "total_denda") val totalDenda: Int = 0
)

// ===== Book =====
@JsonClass(generateAdapter = true)
data class BookDto(
    @Json(name = "id_buku") val idBuku: Int,
    val barcode: String? = null,
    val judul: String,
    val pengarang: String? = null,
    val penerbit: String? = null,
    @Json(name = "jenis_buku") val jenisBuku: String? = null,
    val sinopsis: String? = null,
    val stok: Int = 0,
    val sampul: String? = null,
    @Json(name = "sedang_dipinjam") val sedangDipinjam: Boolean? = null,
    val availability: BookAvailDto? = null,
    @Json(name = "my_loan") val myLoan: MyLoanDto? = null
)

@JsonClass(generateAdapter = true)
data class BookAvailDto(
    val tersedia: Boolean = false,
    @Json(name = "stok_tersedia") val stokTersedia: Int = 0,
    @Json(name = "sedang_dipinjam") val sedangDipinjam: Int = 0
)

@JsonClass(generateAdapter = true)
data class MyLoanDto(
    @Json(name = "id_peminjaman") val idPeminjaman: Int,
    val status: String
)

// ===== Loan / Peminjaman =====
@JsonClass(generateAdapter = true)
data class LoanDto(
    @Json(name = "id_peminjaman") val idPeminjaman: Int,
    @Json(name = "id_buku") val idBuku: Int? = null,
    val judul: String? = null,
    val pengarang: String? = null,
    @Json(name = "jenis_buku") val jenisBuku: String? = null,
    val sampul: String? = null,
    @Json(name = "tgl_pinjam") val tglPinjam: String? = null,
    @Json(name = "tgl_kembali_seharusnya") val tglKembaliSeharusnya: String? = null,
    @Json(name = "tgl_kembali_asli") val tglKembaliAsli: String? = null,
    val status: String? = null,
    @Json(name = "status_label") val statusLabel: String? = null,
    @Json(name = "perpanjangan_status") val perpanjanganStatus: String? = null,
    @Json(name = "kondisi_kembali") val kondisiKembali: String? = null,
    @Json(name = "sisa_hari") val sisaHari: Int? = null,
    @Json(name = "denda_berjalan") val dendaBerjalan: Int? = null,
    @Json(name = "denda_tercatat") val dendaTercatat: Int? = null,
    @Json(name = "hari_terlambat") val hariTerlambat: Int? = null,
    @Json(name = "catatan_admin") val catatanAdmin: String? = null,
    val denda: Int? = null,
    val message: String? = null
)

@JsonClass(generateAdapter = true)
data class LoanRequest(@Json(name = "id_buku") val idBuku: Int)

@JsonClass(generateAdapter = true)
data class ReturnRequest(
    @Json(name = "id_peminjaman") val idPeminjaman: Int,
    val kondisi: String? = null
)

@JsonClass(generateAdapter = true)
data class ExtendRequest(@Json(name = "id_peminjaman") val idPeminjaman: Int)

// ===== Approval =====
@JsonClass(generateAdapter = true)
data class ApprovalDto(
    @Json(name = "id_peminjaman") val idPeminjaman: Int,
    @Json(name = "id_buku") val idBuku: Int? = null,
    val user: ApprovalUserDto? = null,
    val buku: ApprovalBookDto? = null,
    @Json(name = "tgl_pinjam") val tglPinjam: String? = null,
    @Json(name = "tgl_kembali_seharusnya") val tglKembaliSeharusnya: String? = null,
    val status: String,
    @Json(name = "perpanjangan_status") val perpanjanganStatus: String? = null,
    @Json(name = "kondisi_kembali") val kondisiKembali: String? = null,
    @Json(name = "denda_estimasi") val dendaEstimasi: Int? = null
)

@JsonClass(generateAdapter = true)
data class ApprovalUserDto(val nama: String, val username: String, val role: String? = null)

@JsonClass(generateAdapter = true)
data class ApprovalBookDto(
    val judul: String, val pengarang: String? = null, val sampul: String? = null,
    @Json(name = "jenis_buku") val jenisBuku: String? = null, val stok: Int? = null
)

@JsonClass(generateAdapter = true)
data class ApprovalAction(
    @Json(name = "id_peminjaman") val idPeminjaman: Int,
    val aksi: String, val tipe: String, val catatan: String? = null
)

// ===== Fine / Denda =====
@JsonClass(generateAdapter = true)
data class FinesDataDto(
    @Json(name = "total_denda") val totalDenda: Double = 0.0,
    @Json(name = "overdue_berjalan") val overdueBerjalan: Double = 0.0,
    @Json(name = "denda_tercatat") val dendaTercatat: Double = 0.0,
    @Json(name = "overdue_detail") val overdueDetail: List<OverdueDto>? = null,
    val penalties: List<PenaltyDto>? = null
)

@JsonClass(generateAdapter = true)
data class OverdueDto(
    @Json(name = "id_peminjaman") val idPeminjaman: Int,
    val judul: String? = null,
    @Json(name = "hari_terlambat") val hariTerlambat: Int = 0,
    val denda: Int = 0
)

@JsonClass(generateAdapter = true)
data class PenaltyDto(
    val id: Int,
    @Json(name = "tipe_denda") val tipeDenda: String? = null,
    val jumlah: Double = 0.0,
    @Json(name = "jumlah_dibayar") val jumlahDibayar: Double = 0.0,
    val sisa: Double = 0.0,
    val status: String? = null,
    val buku: String? = null,
    val catatan: String? = null,
    @Json(name = "catatan_dispute") val catatanDispute: String? = null,
    @Json(name = "created_at") val createdAt: String? = null,
    @Json(name = "resolved_at") val resolvedAt: String? = null
)

@JsonClass(generateAdapter = true)
data class PaymentAction(val action: String, @Json(name = "id_penalty") val idPenalty: Int, val jumlah: Double? = null, val alasan: String? = null)

@JsonClass(generateAdapter = true)
data class PaymentResultDto(
    @Json(name = "id_penalty") val idPenalty: Int? = null,
    val status: String? = null,
    val sisa: Double? = null,
    val message: String? = null,
    @Json(name = "jumlah_dibayar") val jumlahDibayar: Double? = null,
    val simulasi: Boolean? = null,
    @Json(name = "jumlah_diminta") val jumlahDiminta: Double? = null,
    @Json(name = "jumlah_efektif") val jumlahEfektif: Double? = null,
    @Json(name = "sisa_sebelum") val sisaSebelum: Double? = null,
    @Json(name = "sisa_setelah") val sisaSetelah: Double? = null,
    @Json(name = "status_setelah") val statusSetelah: String? = null
)

// ===== Visit / Kunjungan =====
@JsonClass(generateAdapter = true)
data class VisitStatusDto(
    @Json(name = "is_inside") val isInside: Boolean = false,
    @Json(name = "last_checkin") val lastCheckin: String? = null,
    @Json(name = "last_checkout") val lastCheckout: String? = null,
    @Json(name = "duration_minutes") val durationMinutes: Int = 0
)

@JsonClass(generateAdapter = true)
data class VisitLogDto(val id: Int, val waktu: String? = null, val tipe: String? = null, val metode: String? = null)

// ===== Attendance / Absensi =====
@JsonClass(generateAdapter = true)
data class CheckInRequest(val barcode: String, val tipe: String)

@JsonClass(generateAdapter = true)
data class CheckInResultDto(
    val user: UserDto? = null,
    @Json(name = "waktu_checkin") val waktuCheckin: String? = null,
    val tipe: String? = null,
    @Json(name = "sudah_checkin_hari_ini") val sudahCheckinHariIni: Boolean = false,
    @Json(name = "pinjaman_aktif") val pinjamanAktif: Int = 0,
    val message: String? = null
)

// ===== Staff Dashboard =====
@JsonClass(generateAdapter = true)
data class StaffDashboardDto(
    @Json(name = "total_anggota") val totalAnggota: Int = 0,
    @Json(name = "total_buku") val totalBuku: Int = 0,
    @Json(name = "pinjaman_aktif") val pinjamanAktif: Int = 0,
    @Json(name = "pending_peminjaman") val pendingPeminjaman: Int = 0,
    @Json(name = "pending_pengembalian") val pendingPengembalian: Int = 0,
    @Json(name = "pending_perpanjangan") val pendingPerpanjangan: Int = 0,
    @Json(name = "peminjaman_terlambat") val peminjamanTerlambat: Int = 0,
    val occupancy: Int = 0,
    @Json(name = "pengunjung_hari_ini") val pengunjungHariIni: Int = 0,
    @Json(name = "dispute_pending") val disputePending: Int = 0,
    @Json(name = "total_denda_belum_lunas") val totalDendaBelumLunas: Double = 0.0,
    @Json(name = "buku_populer") val bukuPopuler: List<PopularBookDto>? = null,
    val timestamp: String? = null
)

@JsonClass(generateAdapter = true)
data class PopularBookDto(val judul: String, val total: Int = 0)

// ===== Notification =====
@JsonClass(generateAdapter = true)
data class NotificationDto(
    val id: Int, val judul: String? = null, val pesan: String? = null,
    val tipe: String? = null, @Json(name = "is_read") val isRead: Boolean = false,
    val link: String? = null, @Json(name = "created_at") val createdAt: String? = null
)

@JsonClass(generateAdapter = true)
data class MarkReadRequest(val action: String, val id: Int? = null)

// ===== Token Refresh =====
@JsonClass(generateAdapter = true)
data class TokenRefreshDto(
    val token: String, @Json(name = "expires_in") val expiresIn: Int? = null,
    val user: UserDto? = null
)

// ===== Staff Patron =====
@JsonClass(generateAdapter = true)
data class PatronDto(
    val id: Int, val username: String, val nama: String? = null,
    val email: String? = null, val role: String? = null,
    val jenkel: String? = null, val status: String? = null
)

@JsonClass(generateAdapter = true)
data class PatronProfileDto(
    val id: Int, val username: String, val nama: String? = null,
    val email: String? = null, val role: String? = null,
    val jenkel: String? = null, val foto: String? = null,
    val status: String? = null,
    @Json(name = "borrowing_limit") val borrowingLimit: Int = 2,
    val stats: PatronStatsDto? = null,
    @Json(name = "riwayat_terbaru") val riwayatTerbaru: List<PatronLoanDto>? = null
)

@JsonClass(generateAdapter = true)
data class PatronStatsDto(
    @Json(name = "pinjaman_aktif") val pinjamanAktif: Int = 0,
    @Json(name = "total_pinjam") val totalPinjam: Int = 0,
    @Json(name = "total_denda_belum_lunas") val totalDendaBelumLunas: Double = 0.0
)

@JsonClass(generateAdapter = true)
data class PatronLoanDto(
    @Json(name = "id_peminjaman") val idPeminjaman: Int,
    val judul: String? = null, val status: String? = null,
    @Json(name = "tgl_pinjam") val tglPinjam: String? = null,
    @Json(name = "tgl_kembali_seharusnya") val tglKembaliSeharusnya: String? = null
)

// ===== Visitors =====
@JsonClass(generateAdapter = true)
data class VisitorsDataDto(
    val occupancy: Int = 0,
    @Json(name = "total_checkin_today") val totalCheckinToday: Int = 0,
    @Json(name = "total_checkout_today") val totalCheckoutToday: Int = 0,
    val visitors: List<VisitorDto>? = null
)

@JsonClass(generateAdapter = true)
data class VisitorDto(
    @Json(name = "id_user") val idUser: Int, val nama: String? = null,
    val username: String? = null, val role: String? = null,
    @Json(name = "waktu_masuk") val waktuMasuk: String? = null,
    val durasi: String? = null
)

// ===== Return Check =====
@JsonClass(generateAdapter = true)
data class ReturnCheckDto(
    @Json(name = "id_peminjaman") val idPeminjaman: Int,
    val judul: String? = null, val pengarang: String? = null,
    val peminjam: PeminjamDto? = null,
    @Json(name = "tgl_pinjam") val tglPinjam: String? = null,
    @Json(name = "tgl_kembali_seharusnya") val tglKembaliSeharusnya: String? = null,
    val status: String? = null,
    @Json(name = "kondisi_kembali") val kondisiKembali: String? = null,
    @Json(name = "hari_terlambat") val hariTerlambat: Int = 0,
    @Json(name = "denda_terlambat") val dendaTerlambat: Double = 0.0,
    @Json(name = "estimasi_per_kondisi") val estimasiPerKondisi: List<EstimasiKondisiDto>? = null
)

@JsonClass(generateAdapter = true)
data class PeminjamDto(val nama: String? = null, val username: String? = null)

@JsonClass(generateAdapter = true)
data class EstimasiKondisiDto(
    val kondisi: String, @Json(name = "denda_kondisi") val dendaKondisi: Double = 0.0,
    @Json(name = "total_estimasi") val totalEstimasi: Double = 0.0
)

// ===== Process Return (Admin) =====
@JsonClass(generateAdapter = true)
data class ProcessReturnRequest(
    val action: String = "process",
    @Json(name = "id_peminjaman") val idPeminjaman: Int,
    val kondisi: String, val catatan: String? = null
)

@JsonClass(generateAdapter = true)
data class RequestReturnBody(
    val action: String = "request",
    @Json(name = "id_peminjaman") val idPeminjaman: Int,
    val kondisi: String = "baik"
)

// ===== Admin Fine =====
@JsonClass(generateAdapter = true)
data class AdminFineAction(
    val action: String,
    @Json(name = "id_peminjaman") val idPeminjaman: Int? = null,
    @Json(name = "id_penalty") val idPenalty: Int? = null,
    @Json(name = "id_user") val idUser: Int? = null,
    @Json(name = "id_buku") val idBuku: Int? = null,
    @Json(name = "tipe_denda") val tipeDenda: String? = null,
    val jumlah: Double? = null,
    val catatan: String? = null,
    val keputusan: String? = null
)

// ===== Staff Issue =====
@JsonClass(generateAdapter = true)
data class StaffIssueRequest(val action: String = "issue", val username: String, @Json(name = "id_buku") val idBuku: Int)

// ===== Staff Scan Book =====
@JsonClass(generateAdapter = true)
data class StaffScanBookRequest(val action: String = "scan_book", val barcode: String)

@JsonClass(generateAdapter = true)
data class ScannedBookDto(
    @Json(name = "id_buku") val idBuku: Int, val barcode: String? = null,
    val judul: String, val pengarang: String? = null, val penerbit: String? = null,
    @Json(name = "jenis_buku") val jenisBuku: String? = null, val stok: Int = 0,
    val sampul: String? = null,
    @Json(name = "sedang_dipinjam_oleh") val sedangDipinjamOleh: List<BorrowerDto>? = null
)

@JsonClass(generateAdapter = true)
data class BorrowerDto(
    @Json(name = "id_peminjaman") val idPeminjaman: Int,
    val nama: String? = null, val username: String? = null,
    @Json(name = "tgl_pinjam") val tglPinjam: String? = null,
    @Json(name = "tgl_kembali_seharusnya") val tglKembaliSeharusnya: String? = null
)

// ===== Penalty Rules =====
@JsonClass(generateAdapter = true)
data class PenaltyRuleDto(
    val id: Int, @Json(name = "tipe_denda") val tipeDenda: String? = null,
    @Json(name = "nama_aturan") val namaAturan: String? = null,
    val tarif: Double = 0.0, val satuan: String? = null, val deskripsi: String? = null
)
