package com.sipus.core.`data`.local.dao

import androidx.room.EntityInsertAdapter
import androidx.room.RoomDatabase
import androidx.room.coroutines.createFlow
import androidx.room.util.getColumnIndexOrThrow
import androidx.room.util.performSuspending
import androidx.sqlite.SQLiteStatement
import com.sipus.core.`data`.local.entity.BookEntity
import javax.`annotation`.processing.Generated
import kotlin.Boolean
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
public class BookDao_Impl(
  __db: RoomDatabase,
) : BookDao {
  private val __db: RoomDatabase

  private val __insertAdapterOfBookEntity: EntityInsertAdapter<BookEntity>
  init {
    this.__db = __db
    this.__insertAdapterOfBookEntity = object : EntityInsertAdapter<BookEntity>() {
      protected override fun createQuery(): String =
          "INSERT OR REPLACE INTO `books` (`idBuku`,`barcode`,`judul`,`pengarang`,`penerbit`,`jenisBuku`,`sinopsis`,`stok`,`sampul`,`sedangDipinjam`) VALUES (?,?,?,?,?,?,?,?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: BookEntity) {
        statement.bindLong(1, entity.idBuku.toLong())
        val _tmpBarcode: String? = entity.barcode
        if (_tmpBarcode == null) {
          statement.bindNull(2)
        } else {
          statement.bindText(2, _tmpBarcode)
        }
        statement.bindText(3, entity.judul)
        val _tmpPengarang: String? = entity.pengarang
        if (_tmpPengarang == null) {
          statement.bindNull(4)
        } else {
          statement.bindText(4, _tmpPengarang)
        }
        val _tmpPenerbit: String? = entity.penerbit
        if (_tmpPenerbit == null) {
          statement.bindNull(5)
        } else {
          statement.bindText(5, _tmpPenerbit)
        }
        val _tmpJenisBuku: String? = entity.jenisBuku
        if (_tmpJenisBuku == null) {
          statement.bindNull(6)
        } else {
          statement.bindText(6, _tmpJenisBuku)
        }
        val _tmpSinopsis: String? = entity.sinopsis
        if (_tmpSinopsis == null) {
          statement.bindNull(7)
        } else {
          statement.bindText(7, _tmpSinopsis)
        }
        statement.bindLong(8, entity.stok.toLong())
        val _tmpSampul: String? = entity.sampul
        if (_tmpSampul == null) {
          statement.bindNull(9)
        } else {
          statement.bindText(9, _tmpSampul)
        }
        val _tmpSedangDipinjam: Boolean? = entity.sedangDipinjam
        val _tmp: Int? = _tmpSedangDipinjam?.let { if (it) 1 else 0 }
        if (_tmp == null) {
          statement.bindNull(10)
        } else {
          statement.bindLong(10, _tmp.toLong())
        }
      }
    }
  }

  public override suspend fun insertBooks(books: List<BookEntity>): Unit = performSuspending(__db,
      false, true) { _connection ->
    __insertAdapterOfBookEntity.insert(_connection, books)
  }

  public override fun getAllBooks(): Flow<List<BookEntity>> {
    val _sql: String = "SELECT * FROM books"
    return createFlow(__db, false, arrayOf("books")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfIdBuku: Int = getColumnIndexOrThrow(_stmt, "idBuku")
        val _columnIndexOfBarcode: Int = getColumnIndexOrThrow(_stmt, "barcode")
        val _columnIndexOfJudul: Int = getColumnIndexOrThrow(_stmt, "judul")
        val _columnIndexOfPengarang: Int = getColumnIndexOrThrow(_stmt, "pengarang")
        val _columnIndexOfPenerbit: Int = getColumnIndexOrThrow(_stmt, "penerbit")
        val _columnIndexOfJenisBuku: Int = getColumnIndexOrThrow(_stmt, "jenisBuku")
        val _columnIndexOfSinopsis: Int = getColumnIndexOrThrow(_stmt, "sinopsis")
        val _columnIndexOfStok: Int = getColumnIndexOrThrow(_stmt, "stok")
        val _columnIndexOfSampul: Int = getColumnIndexOrThrow(_stmt, "sampul")
        val _columnIndexOfSedangDipinjam: Int = getColumnIndexOrThrow(_stmt, "sedangDipinjam")
        val _result: MutableList<BookEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: BookEntity
          val _tmpIdBuku: Int
          _tmpIdBuku = _stmt.getLong(_columnIndexOfIdBuku).toInt()
          val _tmpBarcode: String?
          if (_stmt.isNull(_columnIndexOfBarcode)) {
            _tmpBarcode = null
          } else {
            _tmpBarcode = _stmt.getText(_columnIndexOfBarcode)
          }
          val _tmpJudul: String
          _tmpJudul = _stmt.getText(_columnIndexOfJudul)
          val _tmpPengarang: String?
          if (_stmt.isNull(_columnIndexOfPengarang)) {
            _tmpPengarang = null
          } else {
            _tmpPengarang = _stmt.getText(_columnIndexOfPengarang)
          }
          val _tmpPenerbit: String?
          if (_stmt.isNull(_columnIndexOfPenerbit)) {
            _tmpPenerbit = null
          } else {
            _tmpPenerbit = _stmt.getText(_columnIndexOfPenerbit)
          }
          val _tmpJenisBuku: String?
          if (_stmt.isNull(_columnIndexOfJenisBuku)) {
            _tmpJenisBuku = null
          } else {
            _tmpJenisBuku = _stmt.getText(_columnIndexOfJenisBuku)
          }
          val _tmpSinopsis: String?
          if (_stmt.isNull(_columnIndexOfSinopsis)) {
            _tmpSinopsis = null
          } else {
            _tmpSinopsis = _stmt.getText(_columnIndexOfSinopsis)
          }
          val _tmpStok: Int
          _tmpStok = _stmt.getLong(_columnIndexOfStok).toInt()
          val _tmpSampul: String?
          if (_stmt.isNull(_columnIndexOfSampul)) {
            _tmpSampul = null
          } else {
            _tmpSampul = _stmt.getText(_columnIndexOfSampul)
          }
          val _tmpSedangDipinjam: Boolean?
          val _tmp: Int?
          if (_stmt.isNull(_columnIndexOfSedangDipinjam)) {
            _tmp = null
          } else {
            _tmp = _stmt.getLong(_columnIndexOfSedangDipinjam).toInt()
          }
          _tmpSedangDipinjam = _tmp?.let { it != 0 }
          _item =
              BookEntity(_tmpIdBuku,_tmpBarcode,_tmpJudul,_tmpPengarang,_tmpPenerbit,_tmpJenisBuku,_tmpSinopsis,_tmpStok,_tmpSampul,_tmpSedangDipinjam)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun searchBooks(query: String): Flow<List<BookEntity>> {
    val _sql: String =
        "SELECT * FROM books WHERE judul LIKE '%' || ? || '%' OR pengarang LIKE '%' || ? || '%'"
    return createFlow(__db, false, arrayOf("books")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, query)
        _argIndex = 2
        _stmt.bindText(_argIndex, query)
        val _columnIndexOfIdBuku: Int = getColumnIndexOrThrow(_stmt, "idBuku")
        val _columnIndexOfBarcode: Int = getColumnIndexOrThrow(_stmt, "barcode")
        val _columnIndexOfJudul: Int = getColumnIndexOrThrow(_stmt, "judul")
        val _columnIndexOfPengarang: Int = getColumnIndexOrThrow(_stmt, "pengarang")
        val _columnIndexOfPenerbit: Int = getColumnIndexOrThrow(_stmt, "penerbit")
        val _columnIndexOfJenisBuku: Int = getColumnIndexOrThrow(_stmt, "jenisBuku")
        val _columnIndexOfSinopsis: Int = getColumnIndexOrThrow(_stmt, "sinopsis")
        val _columnIndexOfStok: Int = getColumnIndexOrThrow(_stmt, "stok")
        val _columnIndexOfSampul: Int = getColumnIndexOrThrow(_stmt, "sampul")
        val _columnIndexOfSedangDipinjam: Int = getColumnIndexOrThrow(_stmt, "sedangDipinjam")
        val _result: MutableList<BookEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: BookEntity
          val _tmpIdBuku: Int
          _tmpIdBuku = _stmt.getLong(_columnIndexOfIdBuku).toInt()
          val _tmpBarcode: String?
          if (_stmt.isNull(_columnIndexOfBarcode)) {
            _tmpBarcode = null
          } else {
            _tmpBarcode = _stmt.getText(_columnIndexOfBarcode)
          }
          val _tmpJudul: String
          _tmpJudul = _stmt.getText(_columnIndexOfJudul)
          val _tmpPengarang: String?
          if (_stmt.isNull(_columnIndexOfPengarang)) {
            _tmpPengarang = null
          } else {
            _tmpPengarang = _stmt.getText(_columnIndexOfPengarang)
          }
          val _tmpPenerbit: String?
          if (_stmt.isNull(_columnIndexOfPenerbit)) {
            _tmpPenerbit = null
          } else {
            _tmpPenerbit = _stmt.getText(_columnIndexOfPenerbit)
          }
          val _tmpJenisBuku: String?
          if (_stmt.isNull(_columnIndexOfJenisBuku)) {
            _tmpJenisBuku = null
          } else {
            _tmpJenisBuku = _stmt.getText(_columnIndexOfJenisBuku)
          }
          val _tmpSinopsis: String?
          if (_stmt.isNull(_columnIndexOfSinopsis)) {
            _tmpSinopsis = null
          } else {
            _tmpSinopsis = _stmt.getText(_columnIndexOfSinopsis)
          }
          val _tmpStok: Int
          _tmpStok = _stmt.getLong(_columnIndexOfStok).toInt()
          val _tmpSampul: String?
          if (_stmt.isNull(_columnIndexOfSampul)) {
            _tmpSampul = null
          } else {
            _tmpSampul = _stmt.getText(_columnIndexOfSampul)
          }
          val _tmpSedangDipinjam: Boolean?
          val _tmp: Int?
          if (_stmt.isNull(_columnIndexOfSedangDipinjam)) {
            _tmp = null
          } else {
            _tmp = _stmt.getLong(_columnIndexOfSedangDipinjam).toInt()
          }
          _tmpSedangDipinjam = _tmp?.let { it != 0 }
          _item =
              BookEntity(_tmpIdBuku,_tmpBarcode,_tmpJudul,_tmpPengarang,_tmpPenerbit,_tmpJenisBuku,_tmpSinopsis,_tmpStok,_tmpSampul,_tmpSedangDipinjam)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getBookById(id: Int): BookEntity? {
    val _sql: String = "SELECT * FROM books WHERE idBuku = ?"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, id.toLong())
        val _columnIndexOfIdBuku: Int = getColumnIndexOrThrow(_stmt, "idBuku")
        val _columnIndexOfBarcode: Int = getColumnIndexOrThrow(_stmt, "barcode")
        val _columnIndexOfJudul: Int = getColumnIndexOrThrow(_stmt, "judul")
        val _columnIndexOfPengarang: Int = getColumnIndexOrThrow(_stmt, "pengarang")
        val _columnIndexOfPenerbit: Int = getColumnIndexOrThrow(_stmt, "penerbit")
        val _columnIndexOfJenisBuku: Int = getColumnIndexOrThrow(_stmt, "jenisBuku")
        val _columnIndexOfSinopsis: Int = getColumnIndexOrThrow(_stmt, "sinopsis")
        val _columnIndexOfStok: Int = getColumnIndexOrThrow(_stmt, "stok")
        val _columnIndexOfSampul: Int = getColumnIndexOrThrow(_stmt, "sampul")
        val _columnIndexOfSedangDipinjam: Int = getColumnIndexOrThrow(_stmt, "sedangDipinjam")
        val _result: BookEntity?
        if (_stmt.step()) {
          val _tmpIdBuku: Int
          _tmpIdBuku = _stmt.getLong(_columnIndexOfIdBuku).toInt()
          val _tmpBarcode: String?
          if (_stmt.isNull(_columnIndexOfBarcode)) {
            _tmpBarcode = null
          } else {
            _tmpBarcode = _stmt.getText(_columnIndexOfBarcode)
          }
          val _tmpJudul: String
          _tmpJudul = _stmt.getText(_columnIndexOfJudul)
          val _tmpPengarang: String?
          if (_stmt.isNull(_columnIndexOfPengarang)) {
            _tmpPengarang = null
          } else {
            _tmpPengarang = _stmt.getText(_columnIndexOfPengarang)
          }
          val _tmpPenerbit: String?
          if (_stmt.isNull(_columnIndexOfPenerbit)) {
            _tmpPenerbit = null
          } else {
            _tmpPenerbit = _stmt.getText(_columnIndexOfPenerbit)
          }
          val _tmpJenisBuku: String?
          if (_stmt.isNull(_columnIndexOfJenisBuku)) {
            _tmpJenisBuku = null
          } else {
            _tmpJenisBuku = _stmt.getText(_columnIndexOfJenisBuku)
          }
          val _tmpSinopsis: String?
          if (_stmt.isNull(_columnIndexOfSinopsis)) {
            _tmpSinopsis = null
          } else {
            _tmpSinopsis = _stmt.getText(_columnIndexOfSinopsis)
          }
          val _tmpStok: Int
          _tmpStok = _stmt.getLong(_columnIndexOfStok).toInt()
          val _tmpSampul: String?
          if (_stmt.isNull(_columnIndexOfSampul)) {
            _tmpSampul = null
          } else {
            _tmpSampul = _stmt.getText(_columnIndexOfSampul)
          }
          val _tmpSedangDipinjam: Boolean?
          val _tmp: Int?
          if (_stmt.isNull(_columnIndexOfSedangDipinjam)) {
            _tmp = null
          } else {
            _tmp = _stmt.getLong(_columnIndexOfSedangDipinjam).toInt()
          }
          _tmpSedangDipinjam = _tmp?.let { it != 0 }
          _result =
              BookEntity(_tmpIdBuku,_tmpBarcode,_tmpJudul,_tmpPengarang,_tmpPenerbit,_tmpJenisBuku,_tmpSinopsis,_tmpStok,_tmpSampul,_tmpSedangDipinjam)
        } else {
          _result = null
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun deleteAllBooks() {
    val _sql: String = "DELETE FROM books"
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
