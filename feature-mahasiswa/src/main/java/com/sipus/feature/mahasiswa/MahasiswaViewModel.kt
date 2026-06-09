package com.sipus.feature.mahasiswa

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sipus.core.data.datastore.SessionManager
import com.sipus.core.data.remote.dto.*
import com.sipus.core.data.repository.SipusRepository
import com.sipus.core.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class MhsUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val successMsg: String? = null,
    val dashboard: DashboardDto? = null,
    val profile: ProfileDto? = null,
    val barcode: BarcodeDto? = null,
    val books: List<BookDto> = emptyList(),
    val bookDetail: BookDto? = null,
    val loans: List<LoanDto> = emptyList(),
    val history: List<LoanDto> = emptyList(),
    val finesData: FinesDataDto? = null,
    val visitStatus: VisitStatusDto? = null,
    val visitHistory: List<VisitLogDto> = emptyList(),
    val notifications: List<NotificationDto> = emptyList(),
    val unreadCount: Int = 0,
    val userName: String = "",
    val searchQuery: String = ""
)

@HiltViewModel
class MahasiswaViewModel @Inject constructor(
    private val repo: SipusRepository,
    private val session: SessionManager
) : ViewModel() {

    private val _state = MutableStateFlow(MhsUiState())
    val state: StateFlow<MhsUiState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            session.userName.collect { name -> _state.update { it.copy(userName = name ?: "") } }
        }
        loadDashboard()
    }

    fun loadDashboard() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            when (val r = repo.getDashboard()) {
                is Resource.Success -> _state.update { it.copy(isLoading = false, dashboard = r.data) }
                is Resource.Error -> _state.update { it.copy(isLoading = false, error = r.message) }
                else -> {}
            }
        }
    }

    fun loadProfile() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            when (val r = repo.getProfile()) {
                is Resource.Success -> _state.update { it.copy(isLoading = false, profile = r.data) }
                is Resource.Error -> _state.update { it.copy(isLoading = false, error = r.message) }
                else -> {}
            }
        }
    }

    fun loadBarcode() {
        viewModelScope.launch {
            when (val r = repo.getBarcode()) {
                is Resource.Success -> _state.update { it.copy(barcode = r.data) }
                is Resource.Error -> _state.update { it.copy(error = r.message) }
                else -> {}
            }
        }
    }

    fun searchBooks(query: String) {
        _state.update { it.copy(searchQuery = query) }
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            when (val r = repo.getBooks(query.ifBlank { null })) {
                is Resource.Success -> _state.update { it.copy(isLoading = false, books = r.data) }
                is Resource.Error -> _state.update { it.copy(isLoading = false, error = r.message) }
                else -> {}
            }
        }
    }

    fun loadBookDetail(id: Int) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, bookDetail = null) }
            when (val r = repo.getBookDetail(id)) {
                is Resource.Success -> _state.update { it.copy(isLoading = false, bookDetail = r.data) }
                is Resource.Error -> _state.update { it.copy(isLoading = false, error = r.message) }
                else -> {}
            }
        }
    }

    fun requestLoan(idBuku: Int) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            when (val r = repo.requestLoan(idBuku)) {
                is Resource.Success -> {
                    _state.update { it.copy(isLoading = false, successMsg = r.data.message ?: "Pengajuan berhasil") }
                    loadBookDetail(idBuku)
                }
                is Resource.Error -> _state.update { it.copy(isLoading = false, error = r.message) }
                else -> {}
            }
        }
    }

    fun loadLoans(filter: String? = null) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            when (val r = repo.getLoans(filter)) {
                is Resource.Success -> _state.update { it.copy(isLoading = false, loans = r.data) }
                is Resource.Error -> _state.update { it.copy(isLoading = false, error = r.message) }
                else -> {}
            }
        }
    }

    fun requestReturn(idPeminjaman: Int, kondisi: String = "baik") {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            when (val r = repo.requestReturn(idPeminjaman, kondisi)) {
                is Resource.Success -> {
                    _state.update { it.copy(isLoading = false, successMsg = r.data.message ?: "Pengajuan pengembalian berhasil") }
                    loadLoans("active")
                }
                is Resource.Error -> _state.update { it.copy(isLoading = false, error = r.message) }
                else -> {}
            }
        }
    }

    fun requestExtension(idPeminjaman: Int) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            when (val r = repo.requestExtension(idPeminjaman)) {
                is Resource.Success -> {
                    _state.update { it.copy(isLoading = false, successMsg = r.data.message ?: "Pengajuan perpanjangan berhasil") }
                    loadLoans("active")
                }
                is Resource.Error -> _state.update { it.copy(isLoading = false, error = r.message) }
                else -> {}
            }
        }
    }

    fun loadHistory() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            when (val r = repo.getHistory()) {
                is Resource.Success -> _state.update { it.copy(isLoading = false, history = r.data) }
                is Resource.Error -> _state.update { it.copy(isLoading = false, error = r.message) }
                else -> {}
            }
        }
    }

    fun loadFines() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            when (val r = repo.getMyFines()) {
                is Resource.Success -> _state.update { it.copy(isLoading = false, finesData = r.data) }
                is Resource.Error -> _state.update { it.copy(isLoading = false, error = r.message) }
                else -> {}
            }
        }
    }

    fun payFine(idPenalty: Int, jumlah: Double) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            when (val r = repo.payFine(idPenalty, jumlah)) {
                is Resource.Success -> {
                    _state.update { it.copy(isLoading = false, successMsg = r.data.message ?: "Pembayaran berhasil") }
                    loadFines()
                }
                is Resource.Error -> _state.update { it.copy(isLoading = false, error = r.message) }
                else -> {}
            }
        }
    }

    fun disputeFine(idPenalty: Int, alasan: String) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            when (val r = repo.disputeFine(idPenalty, alasan)) {
                is Resource.Success -> {
                    _state.update { it.copy(isLoading = false, successMsg = r.data.message ?: "Dispute berhasil diajukan") }
                    loadFines()
                }
                is Resource.Error -> _state.update { it.copy(isLoading = false, error = r.message) }
                else -> {}
            }
        }
    }

    fun loadVisitStatus() {
        viewModelScope.launch {
            when (val r = repo.getVisitStatus()) {
                is Resource.Success -> _state.update { it.copy(visitStatus = r.data) }
                is Resource.Error -> _state.update { it.copy(error = r.message) }
                else -> {}
            }
        }
    }

    fun loadVisitHistory() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            when (val r = repo.getVisitHistory()) {
                is Resource.Success -> _state.update { it.copy(isLoading = false, visitHistory = r.data) }
                is Resource.Error -> _state.update { it.copy(isLoading = false, error = r.message) }
                else -> {}
            }
        }
    }

    fun loadNotifications() {
        viewModelScope.launch {
            when (val r = repo.getNotifications()) {
                is Resource.Success -> _state.update { it.copy(notifications = r.data, unreadCount = r.data.count { !it.isRead }) }
                is Resource.Error -> _state.update { it.copy(error = r.message) }
                else -> {}
            }
        }
    }

    fun clearMessages() { _state.update { it.copy(error = null, successMsg = null) } }
}
