package com.sipus.core.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sipus.core.data.remote.dto.BookDto

@Entity(tableName = "books")
data class BookEntity(
    @PrimaryKey val idBuku: Int,
    val barcode: String?,
    val judul: String,
    val pengarang: String?,
    val penerbit: String?,
    val jenisBuku: String?,
    val sinopsis: String?,
    val stok: Int,
    val sampul: String?,
    val sedangDipinjam: Boolean?
) {
    fun toBookDto(): BookDto = BookDto(
        idBuku = idBuku,
        barcode = barcode,
        judul = judul,
        pengarang = pengarang,
        penerbit = penerbit,
        jenisBuku = jenisBuku,
        sinopsis = sinopsis,
        stok = stok,
        sampul = sampul,
        sedangDipinjam = sedangDipinjam
    )

    companion object {
        fun fromBookDto(dto: BookDto): BookEntity = BookEntity(
            idBuku = dto.idBuku,
            barcode = dto.barcode,
            judul = dto.judul,
            pengarang = dto.pengarang,
            penerbit = dto.penerbit,
            jenisBuku = dto.jenisBuku,
            sinopsis = dto.sinopsis,
            stok = dto.stok,
            sampul = dto.sampul,
            sedangDipinjam = dto.sedangDipinjam
        )
    }
}
