package com.sipus.core.`data`.local.dao

import androidx.room.EntityInsertAdapter
import androidx.room.RoomDatabase
import androidx.room.coroutines.createFlow
import androidx.room.util.getColumnIndexOrThrow
import androidx.room.util.performSuspending
import androidx.sqlite.SQLiteStatement
import com.sipus.core.`data`.local.entity.LoanEntity
import javax.`annotation`.processing.Generated
import kotlin.Int
import kotlin.String
import kotlin.Suppress
import kotlin.Unit
import kotlin.collections.List
import kotlin.collections.MutableList
import kotlin.collections.mutableListOf
import kotlin.reflect.KClass
import kotlinx.coroutines.flow.Flow

@Generated(value = ["androidx.room.RoomProcessor"])
@Suppress(names = ["UNCHECKED_CAST", "DEPRECATION", "REDUNDANT_PROJECTION", "REMOVAL"])
public class LoanDao_Impl(
  __db: RoomDatabase,
) : LoanDao {
  private val __db: RoomDatabase

  private val __insertAdapterOfLoanEntity: EntityInsertAdapter<LoanEntity>
  init {
    this.__db = __db
    this.__insertAdapterOfLoanEntity = object : EntityInsertAdapter<LoanEntity>() {
      protected override fun createQuery(): String =
          "INSERT OR REPLACE INTO `loans` (`idPeminjaman`,`idBuku`,`judul`,`pengarang`,`jenisBuku`,`sampul`,`tglPinjam`,`tglKembaliSeharusnya`,`tglKembaliAsli`,`status`,`statusLabel`,`perpanjanganStatus`,`kondisiKembali`,`sisaHari`,`dendaBerjalan`,`dendaTercatat`,`hariTerlambat`,`catatanAdmin`,`denda`,`message`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: LoanEntity) {
        statement.bindLong(1, entity.idPeminjaman.toLong())
        val _tmpIdBuku: Int? = entity.idBuku
        if (_tmpIdBuku == null) {
          statement.bindNull(2)
        } else {
          statement.bindLong(2, _tmpIdBuku.toLong())
        }
        val _tmpJudul: String? = entity.judul
        if (_tmpJudul == null) {
          statement.bindNull(3)
        } else {
          statement.bindText(3, _tmpJudul)
        }
        val _tmpPengarang: String? = entity.pengarang
        if (_tmpPengarang == null) {
          statement.bindNull(4)
        } else {
          statement.bindText(4, _tmpPengarang)
        }
        val _tmpJenisBuku: String? = entity.jenisBuku
        if (_tmpJenisBuku == null) {
          statement.bindNull(5)
        } else {
          statement.bindText(5, _tmpJenisBuku)
        }
        val _tmpSampul: String? = entity.sampul
        if (_tmpSampul == null) {
          statement.bindNull(6)
        } else {
          statement.bindText(6, _tmpSampul)
        }
        val _tmpTglPinjam: String? = entity.tglPinjam
        if (_tmpTglPinjam == null) {
          statement.bindNull(7)
        } else {
          statement.bindText(7, _tmpTglPinjam)
        }
        val _tmpTglKembaliSeharusnya: String? = entity.tglKembaliSeharusnya
        if (_tmpTglKembaliSeharusnya == null) {
          statement.bindNull(8)
        } else {
          statement.bindText(8, _tmpTglKembaliSeharusnya)
        }
        val _tmpTglKembaliAsli: String? = entity.tglKembaliAsli
        if (_tmpTglKembaliAsli == null) {
          statement.bindNull(9)
        } else {
          statement.bindText(9, _tmpTglKembaliAsli)
        }
        val _tmpStatus: String? = entity.status
        if (_tmpStatus == null) {
          statement.bindNull(10)
        } else {
          statement.bindText(10, _tmpStatus)
        }
        val _tmpStatusLabel: String? = entity.statusLabel
        if (_tmpStatusLabel == null) {
          statement.bindNull(11)
        } else {
          statement.bindText(11, _tmpStatusLabel)
        }
        val _tmpPerpanjanganStatus: String? = entity.perpanjanganStatus
        if (_tmpPerpanjanganStatus == null) {
          statement.bindNull(12)
        } else {
          statement.bindText(12, _tmpPerpanjanganStatus)
        }
        val _tmpKondisiKembali: String? = entity.kondisiKembali
        if (_tmpKondisiKembali == null) {
          statement.bindNull(13)
        } else {
          statement.bindText(13, _tmpKondisiKembali)
        }
        val _tmpSisaHari: Int? = entity.sisaHari
        if (_tmpSisaHari == null) {
          statement.bindNull(14)
        } else {
          statement.bindLong(14, _tmpSisaHari.toLong())
        }
        val _tmpDendaBerjalan: Int? = entity.dendaBerjalan
        if (_tmpDendaBerjalan == null) {
          statement.bindNull(15)
        } else {
          statement.bindLong(15, _tmpDendaBerjalan.toLong())
        }
        val _tmpDendaTercatat: Int? = entity.dendaTercatat
        if (_tmpDendaTercatat == null) {
          statement.bindNull(16)
        } else {
          statement.bindLong(16, _tmpDendaTercatat.toLong())
        }
        val _tmpHariTerlambat: Int? = entity.hariTerlambat
        if (_tmpHariTerlambat == null) {
          statement.bindNull(17)
        } else {
          statement.bindLong(17, _tmpHariTerlambat.toLong())
        }
        val _tmpCatatanAdmin: String? = entity.catatanAdmin
        if (_tmpCatatanAdmin == null) {
          statement.bindNull(18)
        } else {
          statement.bindText(18, _tmpCatatanAdmin)
        }
        val _tmpDenda: Int? = entity.denda
        if (_tmpDenda == null) {
          statement.bindNull(19)
        } else {
          statement.bindLong(19, _tmpDenda.toLong())
        }
        val _tmpMessage: String? = entity.message
        if (_tmpMessage == null) {
          statement.bindNull(20)
        } else {
          statement.bindText(20, _tmpMessage)
        }
      }
    }
  }

  public override suspend fun insertLoans(loans: List<LoanEntity>): Unit = performSuspending(__db,
      false, true) { _connection ->
    __insertAdapterOfLoanEntity.insert(_connection, loans)
  }

  public override fun getAllLoans(): Flow<List<LoanEntity>> {
    val _sql: String = "SELECT * FROM loans"
    return createFlow(__db, false, arrayOf("loans")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfIdPeminjaman: Int = getColumnIndexOrThrow(_stmt, "idPeminjaman")
        val _columnIndexOfIdBuku: Int = getColumnIndexOrThrow(_stmt, "idBuku")
        val _columnIndexOfJudul: Int = getColumnIndexOrThrow(_stmt, "judul")
        val _columnIndexOfPengarang: Int = getColumnIndexOrThrow(_stmt, "pengarang")
        val _columnIndexOfJenisBuku: Int = getColumnIndexOrThrow(_stmt, "jenisBuku")
        val _columnIndexOfSampul: Int = getColumnIndexOrThrow(_stmt, "sampul")
        val _columnIndexOfTglPinjam: Int = getColumnIndexOrThrow(_stmt, "tglPinjam")
        val _columnIndexOfTglKembaliSeharusnya: Int = getColumnIndexOrThrow(_stmt,
            "tglKembaliSeharusnya")
        val _columnIndexOfTglKembaliAsli: Int = getColumnIndexOrThrow(_stmt, "tglKembaliAsli")
        val _columnIndexOfStatus: Int = getColumnIndexOrThrow(_stmt, "status")
        val _columnIndexOfStatusLabel: Int = getColumnIndexOrThrow(_stmt, "statusLabel")
        val _columnIndexOfPerpanjanganStatus: Int = getColumnIndexOrThrow(_stmt,
            "perpanjanganStatus")
        val _columnIndexOfKondisiKembali: Int = getColumnIndexOrThrow(_stmt, "kondisiKembali")
        val _columnIndexOfSisaHari: Int = getColumnIndexOrThrow(_stmt, "sisaHari")
        val _columnIndexOfDendaBerjalan: Int = getColumnIndexOrThrow(_stmt, "dendaBerjalan")
        val _columnIndexOfDendaTercatat: Int = getColumnIndexOrThrow(_stmt, "dendaTercatat")
        val _columnIndexOfHariTerlambat: Int = getColumnIndexOrThrow(_stmt, "hariTerlambat")
        val _columnIndexOfCatatanAdmin: Int = getColumnIndexOrThrow(_stmt, "catatanAdmin")
        val _columnIndexOfDenda: Int = getColumnIndexOrThrow(_stmt, "denda")
        val _columnIndexOfMessage: Int = getColumnIndexOrThrow(_stmt, "message")
        val _result: MutableList<LoanEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: LoanEntity
          val _tmpIdPeminjaman: Int
          _tmpIdPeminjaman = _stmt.getLong(_columnIndexOfIdPeminjaman).toInt()
          val _tmpIdBuku: Int?
          if (_stmt.isNull(_columnIndexOfIdBuku)) {
            _tmpIdBuku = null
          } else {
            _tmpIdBuku = _stmt.getLong(_columnIndexOfIdBuku).toInt()
          }
          val _tmpJudul: String?
          if (_stmt.isNull(_columnIndexOfJudul)) {
            _tmpJudul = null
          } else {
            _tmpJudul = _stmt.getText(_columnIndexOfJudul)
          }
          val _tmpPengarang: String?
          if (_stmt.isNull(_columnIndexOfPengarang)) {
            _tmpPengarang = null
          } else {
            _tmpPengarang = _stmt.getText(_columnIndexOfPengarang)
          }
          val _tmpJenisBuku: String?
          if (_stmt.isNull(_columnIndexOfJenisBuku)) {
            _tmpJenisBuku = null
          } else {
            _tmpJenisBuku = _stmt.getText(_columnIndexOfJenisBuku)
          }
          val _tmpSampul: String?
          if (_stmt.isNull(_columnIndexOfSampul)) {
            _tmpSampul = null
          } else {
            _tmpSampul = _stmt.getText(_columnIndexOfSampul)
          }
          val _tmpTglPinjam: String?
          if (_stmt.isNull(_columnIndexOfTglPinjam)) {
            _tmpTglPinjam = null
          } else {
            _tmpTglPinjam = _stmt.getText(_columnIndexOfTglPinjam)
          }
          val _tmpTglKembaliSeharusnya: String?
          if (_stmt.isNull(_columnIndexOfTglKembaliSeharusnya)) {
            _tmpTglKembaliSeharusnya = null
          } else {
            _tmpTglKembaliSeharusnya = _stmt.getText(_columnIndexOfTglKembaliSeharusnya)
          }
          val _tmpTglKembaliAsli: String?
          if (_stmt.isNull(_columnIndexOfTglKembaliAsli)) {
            _tmpTglKembaliAsli = null
          } else {
            _tmpTglKembaliAsli = _stmt.getText(_columnIndexOfTglKembaliAsli)
          }
          val _tmpStatus: String?
          if (_stmt.isNull(_columnIndexOfStatus)) {
            _tmpStatus = null
          } else {
            _tmpStatus = _stmt.getText(_columnIndexOfStatus)
          }
          val _tmpStatusLabel: String?
          if (_stmt.isNull(_columnIndexOfStatusLabel)) {
            _tmpStatusLabel = null
          } else {
            _tmpStatusLabel = _stmt.getText(_columnIndexOfStatusLabel)
          }
          val _tmpPerpanjanganStatus: String?
          if (_stmt.isNull(_columnIndexOfPerpanjanganStatus)) {
            _tmpPerpanjanganStatus = null
          } else {
            _tmpPerpanjanganStatus = _stmt.getText(_columnIndexOfPerpanjanganStatus)
          }
          val _tmpKondisiKembali: String?
          if (_stmt.isNull(_columnIndexOfKondisiKembali)) {
            _tmpKondisiKembali = null
          } else {
            _tmpKondisiKembali = _stmt.getText(_columnIndexOfKondisiKembali)
          }
          val _tmpSisaHari: Int?
          if (_stmt.isNull(_columnIndexOfSisaHari)) {
            _tmpSisaHari = null
          } else {
            _tmpSisaHari = _stmt.getLong(_columnIndexOfSisaHari).toInt()
          }
          val _tmpDendaBerjalan: Int?
          if (_stmt.isNull(_columnIndexOfDendaBerjalan)) {
            _tmpDendaBerjalan = null
          } else {
            _tmpDendaBerjalan = _stmt.getLong(_columnIndexOfDendaBerjalan).toInt()
          }
          val _tmpDendaTercatat: Int?
          if (_stmt.isNull(_columnIndexOfDendaTercatat)) {
            _tmpDendaTercatat = null
          } else {
            _tmpDendaTercatat = _stmt.getLong(_columnIndexOfDendaTercatat).toInt()
          }
          val _tmpHariTerlambat: Int?
          if (_stmt.isNull(_columnIndexOfHariTerlambat)) {
            _tmpHariTerlambat = null
          } else {
            _tmpHariTerlambat = _stmt.getLong(_columnIndexOfHariTerlambat).toInt()
          }
          val _tmpCatatanAdmin: String?
          if (_stmt.isNull(_columnIndexOfCatatanAdmin)) {
            _tmpCatatanAdmin = null
          } else {
            _tmpCatatanAdmin = _stmt.getText(_columnIndexOfCatatanAdmin)
          }
          val _tmpDenda: Int?
          if (_stmt.isNull(_columnIndexOfDenda)) {
            _tmpDenda = null
          } else {
            _tmpDenda = _stmt.getLong(_columnIndexOfDenda).toInt()
          }
          val _tmpMessage: String?
          if (_stmt.isNull(_columnIndexOfMessage)) {
            _tmpMessage = null
          } else {
            _tmpMessage = _stmt.getText(_columnIndexOfMessage)
          }
          _item =
              LoanEntity(_tmpIdPeminjaman,_tmpIdBuku,_tmpJudul,_tmpPengarang,_tmpJenisBuku,_tmpSampul,_tmpTglPinjam,_tmpTglKembaliSeharusnya,_tmpTglKembaliAsli,_tmpStatus,_tmpStatusLabel,_tmpPerpanjanganStatus,_tmpKondisiKembali,_tmpSisaHari,_tmpDendaBerjalan,_tmpDendaTercatat,_tmpHariTerlambat,_tmpCatatanAdmin,_tmpDenda,_tmpMessage)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun getLoansByStatus(status: String): Flow<List<LoanEntity>> {
    val _sql: String = "SELECT * FROM loans WHERE status = ?"
    return createFlow(__db, false, arrayOf("loans")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, status)
        val _columnIndexOfIdPeminjaman: Int = getColumnIndexOrThrow(_stmt, "idPeminjaman")
        val _columnIndexOfIdBuku: Int = getColumnIndexOrThrow(_stmt, "idBuku")
        val _columnIndexOfJudul: Int = getColumnIndexOrThrow(_stmt, "judul")
        val _columnIndexOfPengarang: Int = getColumnIndexOrThrow(_stmt, "pengarang")
        val _columnIndexOfJenisBuku: Int = getColumnIndexOrThrow(_stmt, "jenisBuku")
        val _columnIndexOfSampul: Int = getColumnIndexOrThrow(_stmt, "sampul")
        val _columnIndexOfTglPinjam: Int = getColumnIndexOrThrow(_stmt, "tglPinjam")
        val _columnIndexOfTglKembaliSeharusnya: Int = getColumnIndexOrThrow(_stmt,
            "tglKembaliSeharusnya")
        val _columnIndexOfTglKembaliAsli: Int = getColumnIndexOrThrow(_stmt, "tglKembaliAsli")
        val _columnIndexOfStatus: Int = getColumnIndexOrThrow(_stmt, "status")
        val _columnIndexOfStatusLabel: Int = getColumnIndexOrThrow(_stmt, "statusLabel")
        val _columnIndexOfPerpanjanganStatus: Int = getColumnIndexOrThrow(_stmt,
            "perpanjanganStatus")
        val _columnIndexOfKondisiKembali: Int = getColumnIndexOrThrow(_stmt, "kondisiKembali")
        val _columnIndexOfSisaHari: Int = getColumnIndexOrThrow(_stmt, "sisaHari")
        val _columnIndexOfDendaBerjalan: Int = getColumnIndexOrThrow(_stmt, "dendaBerjalan")
        val _columnIndexOfDendaTercatat: Int = getColumnIndexOrThrow(_stmt, "dendaTercatat")
        val _columnIndexOfHariTerlambat: Int = getColumnIndexOrThrow(_stmt, "hariTerlambat")
        val _columnIndexOfCatatanAdmin: Int = getColumnIndexOrThrow(_stmt, "catatanAdmin")
        val _columnIndexOfDenda: Int = getColumnIndexOrThrow(_stmt, "denda")
        val _columnIndexOfMessage: Int = getColumnIndexOrThrow(_stmt, "message")
        val _result: MutableList<LoanEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: LoanEntity
          val _tmpIdPeminjaman: Int
          _tmpIdPeminjaman = _stmt.getLong(_columnIndexOfIdPeminjaman).toInt()
          val _tmpIdBuku: Int?
          if (_stmt.isNull(_columnIndexOfIdBuku)) {
            _tmpIdBuku = null
          } else {
            _tmpIdBuku = _stmt.getLong(_columnIndexOfIdBuku).toInt()
          }
          val _tmpJudul: String?
          if (_stmt.isNull(_columnIndexOfJudul)) {
            _tmpJudul = null
          } else {
            _tmpJudul = _stmt.getText(_columnIndexOfJudul)
          }
          val _tmpPengarang: String?
          if (_stmt.isNull(_columnIndexOfPengarang)) {
            _tmpPengarang = null
          } else {
            _tmpPengarang = _stmt.getText(_columnIndexOfPengarang)
          }
          val _tmpJenisBuku: String?
          if (_stmt.isNull(_columnIndexOfJenisBuku)) {
            _tmpJenisBuku = null
          } else {
            _tmpJenisBuku = _stmt.getText(_columnIndexOfJenisBuku)
          }
          val _tmpSampul: String?
          if (_stmt.isNull(_columnIndexOfSampul)) {
            _tmpSampul = null
          } else {
            _tmpSampul = _stmt.getText(_columnIndexOfSampul)
          }
          val _tmpTglPinjam: String?
          if (_stmt.isNull(_columnIndexOfTglPinjam)) {
            _tmpTglPinjam = null
          } else {
            _tmpTglPinjam = _stmt.getText(_columnIndexOfTglPinjam)
          }
          val _tmpTglKembaliSeharusnya: String?
          if (_stmt.isNull(_columnIndexOfTglKembaliSeharusnya)) {
            _tmpTglKembaliSeharusnya = null
          } else {
            _tmpTglKembaliSeharusnya = _stmt.getText(_columnIndexOfTglKembaliSeharusnya)
          }
          val _tmpTglKembaliAsli: String?
          if (_stmt.isNull(_columnIndexOfTglKembaliAsli)) {
            _tmpTglKembaliAsli = null
          } else {
            _tmpTglKembaliAsli = _stmt.getText(_columnIndexOfTglKembaliAsli)
          }
          val _tmpStatus: String?
          if (_stmt.isNull(_columnIndexOfStatus)) {
            _tmpStatus = null
          } else {
            _tmpStatus = _stmt.getText(_columnIndexOfStatus)
          }
          val _tmpStatusLabel: String?
          if (_stmt.isNull(_columnIndexOfStatusLabel)) {
            _tmpStatusLabel = null
          } else {
            _tmpStatusLabel = _stmt.getText(_columnIndexOfStatusLabel)
          }
          val _tmpPerpanjanganStatus: String?
          if (_stmt.isNull(_columnIndexOfPerpanjanganStatus)) {
            _tmpPerpanjanganStatus = null
          } else {
            _tmpPerpanjanganStatus = _stmt.getText(_columnIndexOfPerpanjanganStatus)
          }
          val _tmpKondisiKembali: String?
          if (_stmt.isNull(_columnIndexOfKondisiKembali)) {
            _tmpKondisiKembali = null
          } else {
            _tmpKondisiKembali = _stmt.getText(_columnIndexOfKondisiKembali)
          }
          val _tmpSisaHari: Int?
          if (_stmt.isNull(_columnIndexOfSisaHari)) {
            _tmpSisaHari = null
          } else {
            _tmpSisaHari = _stmt.getLong(_columnIndexOfSisaHari).toInt()
          }
          val _tmpDendaBerjalan: Int?
          if (_stmt.isNull(_columnIndexOfDendaBerjalan)) {
            _tmpDendaBerjalan = null
          } else {
            _tmpDendaBerjalan = _stmt.getLong(_columnIndexOfDendaBerjalan).toInt()
          }
          val _tmpDendaTercatat: Int?
          if (_stmt.isNull(_columnIndexOfDendaTercatat)) {
            _tmpDendaTercatat = null
          } else {
            _tmpDendaTercatat = _stmt.getLong(_columnIndexOfDendaTercatat).toInt()
          }
          val _tmpHariTerlambat: Int?
          if (_stmt.isNull(_columnIndexOfHariTerlambat)) {
            _tmpHariTerlambat = null
          } else {
            _tmpHariTerlambat = _stmt.getLong(_columnIndexOfHariTerlambat).toInt()
          }
          val _tmpCatatanAdmin: String?
          if (_stmt.isNull(_columnIndexOfCatatanAdmin)) {
            _tmpCatatanAdmin = null
          } else {
            _tmpCatatanAdmin = _stmt.getText(_columnIndexOfCatatanAdmin)
          }
          val _tmpDenda: Int?
          if (_stmt.isNull(_columnIndexOfDenda)) {
            _tmpDenda = null
          } else {
            _tmpDenda = _stmt.getLong(_columnIndexOfDenda).toInt()
          }
          val _tmpMessage: String?
          if (_stmt.isNull(_columnIndexOfMessage)) {
            _tmpMessage = null
          } else {
            _tmpMessage = _stmt.getText(_columnIndexOfMessage)
          }
          _item =
              LoanEntity(_tmpIdPeminjaman,_tmpIdBuku,_tmpJudul,_tmpPengarang,_tmpJenisBuku,_tmpSampul,_tmpTglPinjam,_tmpTglKembaliSeharusnya,_tmpTglKembaliAsli,_tmpStatus,_tmpStatusLabel,_tmpPerpanjanganStatus,_tmpKondisiKembali,_tmpSisaHari,_tmpDendaBerjalan,_tmpDendaTercatat,_tmpHariTerlambat,_tmpCatatanAdmin,_tmpDenda,_tmpMessage)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun deleteLoansByStatus(status: String) {
    val _sql: String = "DELETE FROM loans WHERE status = ?"
    return performSuspending(__db, false, true) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, status)
        _stmt.step()
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun deleteAllLoans() {
    val _sql: String = "DELETE FROM loans"
    return performSuspending(__db, false, true) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        _stmt.step()
      } finally {
        _stmt.close()
      }
    }
  }

  public companion object {
    public fun getRequiredConverters(): List<KClass<*>> = emptyList()
  }
}
