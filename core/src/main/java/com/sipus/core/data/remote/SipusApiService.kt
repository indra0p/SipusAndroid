package com.sipus.core.data.remote

import com.sipus.core.data.remote.dto.*
import retrofit2.Response
import retrofit2.http.*

interface SipusApiService {

    // === Auth (no token) ===
    @POST("masuk.php")
    suspend fun login(@Body body: LoginRequest): Response<ApiResponse<AuthData>>

    @POST("daftar.php")
    suspend fun register(@Body body: RegisterRequest): Response<ApiResponse<AuthData>>

    @POST("perbarui_token.php")
    suspend fun refreshToken(): Response<ApiResponse<TokenRefreshDto>>

    // === Profile ===
    @GET("profil.php")
    suspend fun getProfile(): Response<ApiResponse<ProfileDto>>

    @GET("barcode.php")
    suspend fun getBarcode(): Response<ApiResponse<BarcodeDto>>

    // === Dashboard ===
    @GET("dasbor.php")
    suspend fun getDashboard(): Response<ApiResponse<DashboardDto>>

    // === Books ===
    @GET("buku.php")
    suspend fun getBooks(@Query("keyword") keyword: String? = null): Response<ApiResponse<List<BookDto>>>

    @GET("buku.php")
    suspend fun getBookDetail(@Query("id") id: Int): Response<ApiResponse<BookDto>>

    // === Loans ===
    @GET("peminjaman.php")
    suspend fun getLoans(@Query("filter") filter: String? = null): Response<ApiResponse<List<LoanDto>>>

    @GET("peminjaman.php")
    suspend fun getLoanStatus(@Query("action") action: String = "status", @Query("id") id: Int): Response<ApiResponse<LoanDto>>

    @POST("peminjaman.php")
    suspend fun requestLoan(@Body body: LoanRequest): Response<ApiResponse<LoanDto>>

    @PUT("peminjaman.php")
    suspend fun requestReturn(@Body body: ReturnRequest): Response<ApiResponse<LoanDto>>

    @PATCH("peminjaman.php")
    suspend fun requestExtension(@Body body: ExtendRequest): Response<ApiResponse<LoanDto>>

    // === History ===
    @GET("riwayat.php")
    suspend fun getHistory(): Response<ApiResponse<List<LoanDto>>>

    // === Returns ===
    @GET("pengembalian.php")
    suspend fun checkReturn(
        @Query("action") action: String = "check",
        @Query("id_peminjaman") idPeminjaman: Int
    ): Response<ApiResponse<ReturnCheckDto>>

    @GET("pengembalian.php")
    suspend fun getMyReturns(@Query("action") action: String = "my_returns"): Response<ApiResponse<List<LoanDto>>>

    @POST("pengembalian.php")
    suspend fun processReturn(@Body body: ProcessReturnRequest): Response<ApiResponse<LoanDto>>

    @POST("pengembalian.php")
    suspend fun requestReturnV2(@Body body: RequestReturnBody): Response<ApiResponse<LoanDto>>

    // === Fines (Patron) ===
    @GET("denda.php")
    suspend fun getMyFines(@Query("action") action: String = "my_fines"): Response<ApiResponse<FinesDataDto>>

    @GET("denda.php")
    suspend fun getPaymentHistory(@Query("action") action: String = "history"): Response<ApiResponse<List<PaymentResultDto>>>

    @GET("denda.php")
    suspend fun simulateFine(@Query("action") action: String = "simulate", @Query("id_penalty") idPenalty: Int): Response<ApiResponse<PaymentResultDto>>

    @POST("denda.php")
    suspend fun fineAction(@Body body: PaymentAction): Response<ApiResponse<PaymentResultDto>>

    // === Fines (Admin) ===
    @POST("denda_admin.php")
    suspend fun adminFineAction(@Body body: AdminFineAction): Response<ApiResponse<PaymentResultDto>>

    // === Visits ===
    @GET("kunjungan.php")
    suspend fun getVisitStatus(@Query("action") action: String = "current"): Response<ApiResponse<VisitStatusDto>>

    @GET("kunjungan.php")
    suspend fun getVisitHistory(@Query("action") action: String = "history"): Response<ApiResponse<List<VisitLogDto>>>

    @GET("kunjungan.php")
    suspend fun getCurrentVisitors(@Query("action") action: String = "current_visitors"): Response<ApiResponse<VisitorsDataDto>>

    // === Attendance (Staff) ===
    @POST("absensi.php")
    suspend fun checkInOut(@Body body: CheckInRequest): Response<ApiResponse<CheckInResultDto>>

    // === Approval (Staff) ===
    @GET("persetujuan.php")
    suspend fun getApprovals(@Query("tipe") tipe: String? = null): Response<ApiResponse<List<ApprovalDto>>>

    @POST("persetujuan.php")
    suspend fun processApproval(@Body body: ApprovalAction): Response<ApiResponse<LoanDto>>

    // === Staff / Petugas ===
    @GET("petugas.php")
    suspend fun searchPatrons(@Query("action") action: String = "search_patrons", @Query("keyword") keyword: String): Response<ApiResponse<List<PatronDto>>>

    @GET("petugas.php")
    suspend fun getPatronProfile(@Query("action") action: String = "patron_profile", @Query("id") id: Int): Response<ApiResponse<PatronProfileDto>>

    @GET("petugas.php")
    suspend fun getStaffDashboard(@Query("action") action: String = "dashboard"): Response<ApiResponse<StaffDashboardDto>>

    @POST("petugas.php")
    suspend fun scanBook(@Body body: StaffScanBookRequest): Response<ApiResponse<ScannedBookDto>>

    @POST("petugas.php")
    suspend fun issueBook(@Body body: StaffIssueRequest): Response<ApiResponse<LoanDto>>

    // === Notifications ===
    @GET("notifikasi.php")
    suspend fun getNotifications(@Query("limit") limit: Int = 50): Response<ApiResponse<List<NotificationDto>>>

    @PUT("notifikasi.php")
    suspend fun markNotificationRead(@Body body: MarkReadRequest): Response<ApiResponse<Any>>

    // === Penalty Rules ===
    @GET("aturan_denda.php")
    suspend fun getPenaltyRules(): Response<ApiResponse<List<PenaltyRuleDto>>>
}
