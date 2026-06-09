package com.sipus.feature.staff

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sipus.core.data.remote.dto.*
import com.sipus.core.data.repository.SipusRepository
import com.sipus.core.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class StaffUiState(
    val isLoading: Boolean = false, val error: String? = null, val successMsg: String? = null,
    val dashboard: StaffDashboardDto? = null, val approvals: List<ApprovalDto> = emptyList(),
    val checkInResult: CheckInResultDto? = null, val patrons: List<PatronDto> = emptyList(),
    val patronProfile: PatronProfileDto? = null, val visitorsData: VisitorsDataDto? = null,
    val profile: ProfileDto? = null
)

@HiltViewModel
class StaffViewModel @Inject constructor(private val repo: SipusRepository) : ViewModel() {
    private val _state = MutableStateFlow(StaffUiState())
    val state: StateFlow<StaffUiState> = _state.asStateFlow()

    init { loadDashboard() }

    fun loadDashboard() { viewModelScope.launch {
        _state.update { it.copy(isLoading = true) }
        when (val r = repo.getStaffDashboard()) {
            is Resource.Success -> _state.update { it.copy(isLoading = false, dashboard = r.data) }
            is Resource.Error -> _state.update { it.copy(isLoading = false, error = r.message) }
            else -> {}
        }
    }}

    fun checkInOut(barcode: String, tipe: String) { viewModelScope.launch {
        _state.update { it.copy(isLoading = true, checkInResult = null) }
        when (val r = repo.checkInOut(barcode, tipe)) {
            is Resource.Success -> _state.update { it.copy(isLoading = false, checkInResult = r.data, successMsg = r.data.message) }
            is Resource.Error -> _state.update { it.copy(isLoading = false, error = r.message) }
            else -> {}
        }
    }}

    fun loadApprovals(tipe: String? = null) { viewModelScope.launch {
        _state.update { it.copy(isLoading = true) }
        when (val r = repo.getApprovals(tipe)) {
            is Resource.Success -> _state.update { it.copy(isLoading = false, approvals = r.data) }
            is Resource.Error -> _state.update { it.copy(isLoading = false, error = r.message) }
            else -> {}
        }
    }}

    fun processApproval(id: Int, aksi: String, tipe: String) { viewModelScope.launch {
        _state.update { it.copy(isLoading = true) }
        when (val r = repo.processApproval(id, aksi, tipe)) {
            is Resource.Success -> { _state.update { it.copy(isLoading = false, successMsg = r.data.message) }; loadApprovals() }
            is Resource.Error -> _state.update { it.copy(isLoading = false, error = r.message) }
            else -> {}
        }
    }}

    fun searchPatrons(kw: String) { viewModelScope.launch {
        _state.update { it.copy(isLoading = true) }
        when (val r = repo.searchPatrons(kw)) {
            is Resource.Success -> _state.update { it.copy(isLoading = false, patrons = r.data) }
            is Resource.Error -> _state.update { it.copy(isLoading = false, error = r.message) }
            else -> {}
        }
    }}

    fun loadPatronProfile(id: Int) { viewModelScope.launch {
        _state.update { it.copy(isLoading = true, patronProfile = null) }
        when (val r = repo.getPatronProfile(id)) {
            is Resource.Success -> _state.update { it.copy(isLoading = false, patronProfile = r.data) }
            is Resource.Error -> _state.update { it.copy(isLoading = false, error = r.message) }
            else -> {}
        }
    }}

    fun loadVisitors() { viewModelScope.launch {
        _state.update { it.copy(isLoading = true) }
        when (val r = repo.getCurrentVisitors()) {
            is Resource.Success -> _state.update { it.copy(isLoading = false, visitorsData = r.data) }
            is Resource.Error -> _state.update { it.copy(isLoading = false, error = r.message) }
            else -> {}
        }
    }}

    fun loadProfile() { viewModelScope.launch {
        _state.update { it.copy(isLoading = true) }
        when (val r = repo.getProfile()) {
            is Resource.Success -> _state.update { it.copy(isLoading = false, profile = r.data) }
            is Resource.Error -> _state.update { it.copy(isLoading = false, error = r.message) }
            else -> {}
        }
    }}

    fun clearMessages() { _state.update { it.copy(error = null, successMsg = null, checkInResult = null) } }
}
