package com.sipus.core.data.repository

import com.sipus.core.data.local.dao.BookDao
import com.sipus.core.data.local.dao.LoanDao
import com.sipus.core.data.local.entity.BookEntity
import com.sipus.core.data.local.entity.LoanEntity
import com.sipus.core.data.remote.SipusApiService
import com.sipus.core.data.remote.dto.*
import com.sipus.core.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SipusRepository @Inject constructor(
    private val api: SipusApiService,
    private val bookDao: BookDao,
    private val loanDao: LoanDao
) {
    private suspend fun <T> safeApiCall(call: suspend () -> Response<ApiResponse<T>>): Resource<T> =
        withContext(Dispatchers.IO) {
            try {
                val response = call()
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null && body.status == "ok" && body.data != null) {
                        Resource.Success(body.data)
                    } else {
                        Resource.Error(body?.message ?: "Terjadi kesalahan", response.code())
                    }
                } else {
                    val errorMsg = try {
                        val moshi = com.squareup.moshi.Moshi.Builder().build()
                        val adapter = moshi.adapter(ApiResponse::class.java)
                        adapter.fromJson(response.errorBody()?.string() ?: "")?.message
                    } catch (_: Exception) { null }
                    Resource.Error(errorMsg ?: "Error ${response.code()}", response.code())
                }
            } catch (e: Exception) {
                Resource.Error(e.localizedMessage ?: "Koneksi gagal")
            }
        }

    // Auth
    suspend fun login(username: String, password: String) = safeApiCall { api.login(LoginRequest(username, password)) }
    suspend fun register(username: String, nama: String, password: String, email: String, jenkel: String) =
        safeApiCall { api.register(RegisterRequest(username, nama, password, email, jenkel)) }
    suspend fun refreshToken() = safeApiCall { api.refreshToken() }

    // Profile
    suspend fun getProfile() = safeApiCall { api.getProfile() }
    suspend fun getBarcode() = safeApiCall { api.getBarcode() }

    // Dashboard
    suspend fun getDashboard() = safeApiCall { api.getDashboard() }

    // Books (Offline-First Cache)
    suspend fun getBooks(keyword: String? = null): Resource<List<BookDto>> {
        val result = safeApiCall { api.getBooks(keyword) }
        return if (result is Resource.Success) {
            val entities = result.data.map { BookEntity.fromBookDto(it) }
            bookDao.insertBooks(entities)
            result
        } else {
            try {
                val cached = if (keyword.isNullOrBlank()) {
                    bookDao.getAllBooks().firstOrNull() ?: emptyList()
                } else {
                    bookDao.searchBooks(keyword).firstOrNull() ?: emptyList()
                }
                if (cached.isNotEmpty()) {
                    Resource.Success(cached.map { it.toBookDto() })
                } else {
                    result
                }
            } catch (e: Exception) {
                result
            }
        }
    }

    suspend fun getBookDetail(id: Int): Resource<BookDto> {
        val result = safeApiCall { api.getBookDetail(id) }
        return if (result is Resource.Success) {
            bookDao.insertBooks(listOf(BookEntity.fromBookDto(result.data)))
            result
        } else {
            try {
                val cached = bookDao.getBookById(id)
                if (cached != null) {
                    Resource.Success(cached.toBookDto())
                } else {
                    result
                }
            } catch (e: Exception) {
                result
            }
        }
    }

    // Loans (Offline-First Cache)
    suspend fun getLoans(filter: String? = null): Resource<List<LoanDto>> {
        val result = safeApiCall { api.getLoans(filter) }
        return if (result is Resource.Success) {
            val entities = result.data.map { LoanEntity.fromLoanDto(it) }
            loanDao.insertLoans(entities)
            result
        } else {
            try {
                val cached = if (filter.isNullOrBlank()) {
                    loanDao.getAllLoans().firstOrNull() ?: emptyList()
                } else {
                    loanDao.getLoansByStatus(filter).firstOrNull() ?: emptyList()
                }
                if (cached.isNotEmpty()) {
                    Resource.Success(cached.map { it.toLoanDto() })
                } else {
                    result
                }
            } catch (e: Exception) {
                result
            }
        }
    }

    suspend fun getLoanStatus(id: Int) = safeApiCall { api.getLoanStatus(id = id) }
    suspend fun requestLoan(idBuku: Int) = safeApiCall { api.requestLoan(LoanRequest(idBuku)) }
    suspend fun requestReturn(idPeminjaman: Int, kondisi: String? = null) =
        safeApiCall { api.requestReturn(ReturnRequest(idPeminjaman, kondisi)) }
    suspend fun requestExtension(idPeminjaman: Int) = safeApiCall { api.requestExtension(ExtendRequest(idPeminjaman)) }

    // History
    suspend fun getHistory() = safeApiCall { api.getHistory() }

    // Returns
    suspend fun checkReturn(idPeminjaman: Int) = safeApiCall { api.checkReturn(idPeminjaman = idPeminjaman) }
    suspend fun getMyReturns() = safeApiCall { api.getMyReturns() }
    suspend fun processReturn(idPeminjaman: Int, kondisi: String, catatan: String? = null) =
        safeApiCall { api.processReturn(ProcessReturnRequest(idPeminjaman = idPeminjaman, kondisi = kondisi, catatan = catatan)) }

    // Fines
    suspend fun getMyFines() = safeApiCall { api.getMyFines() }
    suspend fun payFine(idPenalty: Int, jumlah: Double) =
        safeApiCall { api.fineAction(PaymentAction("pay", idPenalty, jumlah)) }
    suspend fun simulatePayment(idPenalty: Int, jumlah: Double) =
        safeApiCall { api.fineAction(PaymentAction("simulate_pay", idPenalty, jumlah)) }
    suspend fun disputeFine(idPenalty: Int, alasan: String) =
        safeApiCall { api.fineAction(PaymentAction("dispute", idPenalty, alasan = alasan)) }

    // Admin Fines
    suspend fun adminFineAction(body: AdminFineAction) = safeApiCall { api.adminFineAction(body) }

    // Visits
    suspend fun getVisitStatus() = safeApiCall { api.getVisitStatus() }
    suspend fun getVisitHistory() = safeApiCall { api.getVisitHistory() }
    suspend fun getCurrentVisitors() = safeApiCall { api.getCurrentVisitors() }

    // Attendance
    suspend fun checkInOut(barcode: String, tipe: String) = safeApiCall { api.checkInOut(CheckInRequest(barcode, tipe)) }

    // Approvals
    suspend fun getApprovals(tipe: String? = null) = safeApiCall { api.getApprovals(tipe) }
    suspend fun processApproval(idPeminjaman: Int, aksi: String, tipe: String, catatan: String? = null) =
        safeApiCall { api.processApproval(ApprovalAction(idPeminjaman, aksi, tipe, catatan)) }

    // Staff
    suspend fun searchPatrons(keyword: String) = safeApiCall { api.searchPatrons(keyword = keyword) }
    suspend fun getPatronProfile(id: Int) = safeApiCall { api.getPatronProfile(id = id) }
    suspend fun getStaffDashboard() = safeApiCall { api.getStaffDashboard() }
    suspend fun scanBook(barcode: String) = safeApiCall { api.scanBook(StaffScanBookRequest(barcode = barcode)) }
    suspend fun issueBook(username: String, idBuku: Int) = safeApiCall { api.issueBook(StaffIssueRequest(username = username, idBuku = idBuku)) }

    // Notifications
    suspend fun getNotifications() = safeApiCall { api.getNotifications() }
    suspend fun markAllRead() = safeApiCall { api.markNotificationRead(MarkReadRequest("mark_all")) }
    suspend fun markOneRead(id: Int) = safeApiCall { api.markNotificationRead(MarkReadRequest("mark_one", id)) }

    // Penalty Rules
    suspend fun getPenaltyRules() = safeApiCall { api.getPenaltyRules() }
}
