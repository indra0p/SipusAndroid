package com.sipus.core.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sipus.core.data.remote.dto.LoanDto

@Entity(tableName = "loans")
data class LoanEntity(
    @PrimaryKey val idPeminjaman: Int,
    val idBuku: Int?,
    val judul: String?,
    val pengarang: String?,
    val jenisBuku: String?,
    val sampul: String?,
    val tglPinjam: String?,
    val tglKembaliSeharusnya: String?,
    val tglKembaliAsli: String?,
    val status: String?,
    val statusLabel: String?,
    val perpanjanganStatus: String?,
    val kondisiKembali: String?,
    val sisaHari: Int?,
    val dendaBerjalan: Int?,
    val dendaTercatat: Int?,
    val hariTerlambat: Int?,
    val catatanAdmin: String?,
    val denda: Int?,
    val message: String?
) {
    fun toLoanDto(): LoanDto = LoanDto(
        idPeminjaman = idPeminjaman,
        idBuku = idBuku,
        judul = judul,
        pengarang = pengarang,
        jenisBuku = jenisBuku,
        sampul = sampul,
        tglPinjam = tglPinjam,
        tglKembaliSeharusnya = tglKembaliSeharusnya,
        tglKembaliAsli = tglKembaliAsli,
        status = status,
        statusLabel = statusLabel,
        perpanjanganStatus = perpanjanganStatus,
        kondisiKembali = kondisiKembali,
        sisaHari = sisaHari,
        dendaBerjalan = dendaBerjalan,
        dendaTercatat = dendaTercatat,
        hariTerlambat = hariTerlambat,
        catatanAdmin = catatanAdmin,
        denda = denda,
        message = message
    )

    companion object {
        fun fromLoanDto(dto: LoanDto): LoanEntity = LoanEntity(
            idPeminjaman = dto.idPeminjaman,
            idBuku = dto.idBuku,
            judul = dto.judul,
            pengarang = dto.pengarang,
            jenisBuku = dto.jenisBuku,
            sampul = dto.sampul,
            tglPinjam = dto.tglPinjam,
            tglKembaliSeharusnya = dto.tglKembaliSeharusnya,
            tglKembaliAsli = dto.tglKembaliAsli,
            status = dto.status,
            statusLabel = dto.statusLabel,
            perpanjanganStatus = dto.perpanjanganStatus,
            kondisiKembali = dto.kondisiKembali,
            sisaHari = dto.sisaHari,
            dendaBerjalan = dto.dendaBerjalan,
            dendaTercatat = dto.dendaTercatat,
            hariTerlambat = dto.hariTerlambat,
            catatanAdmin = dto.catatanAdmin,
            denda = dto.denda,
            message = dto.message
        )
    }
}
