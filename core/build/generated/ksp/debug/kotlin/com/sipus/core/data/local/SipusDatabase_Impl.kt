package com.sipus.core.`data`.local

import androidx.room.InvalidationTracker
import androidx.room.RoomOpenDelegate
import androidx.room.migration.AutoMigrationSpec
import androidx.room.migration.Migration
import androidx.room.util.TableInfo
import androidx.room.util.TableInfo.Companion.read
import androidx.room.util.dropFtsSyncTriggers
import androidx.sqlite.SQLiteConnection
import androidx.sqlite.execSQL
import com.sipus.core.`data`.local.dao.BookDao
import com.sipus.core.`data`.local.dao.BookDao_Impl
import com.sipus.core.`data`.local.dao.LoanDao
import com.sipus.core.`data`.local.dao.LoanDao_Impl
import javax.`annotation`.processing.Generated
import kotlin.Lazy
import kotlin.String
import kotlin.Suppress
import kotlin.collections.List
import kotlin.collections.Map
import kotlin.collections.MutableList
import kotlin.collections.MutableMap
import kotlin.collections.MutableSet
import kotlin.collections.Set
import kotlin.collections.mutableListOf
import kotlin.collections.mutableMapOf
import kotlin.collections.mutableSetOf
import kotlin.reflect.KClass

@Generated(value = ["androidx.room.RoomProcessor"])
@Suppress(names = ["UNCHECKED_CAST", "DEPRECATION", "REDUNDANT_PROJECTION", "REMOVAL"])
public class SipusDatabase_Impl : SipusDatabase() {
  private val _bookDao: Lazy<BookDao> = lazy {
    BookDao_Impl(this)
  }

  private val _loanDao: Lazy<LoanDao> = lazy {
    LoanDao_Impl(this)
  }

  protected override fun createOpenDelegate(): RoomOpenDelegate {
    val _openDelegate: RoomOpenDelegate = object : RoomOpenDelegate(2,
        "070375bf3f4794ed4c3394f1e5bd3ad2", "5583a20c6241f13a87028ce49f020df6") {
      public override fun createAllTables(connection: SQLiteConnection) {
        connection.execSQL("CREATE TABLE IF NOT EXISTS `books` (`idBuku` INTEGER NOT NULL, `barcode` TEXT, `judul` TEXT NOT NULL, `pengarang` TEXT, `penerbit` TEXT, `jenisBuku` TEXT, `sinopsis` TEXT, `stok` INTEGER NOT NULL, `sampul` TEXT, `sedangDipinjam` INTEGER, PRIMARY KEY(`idBuku`))")
        connection.execSQL("CREATE TABLE IF NOT EXISTS `loans` (`idPeminjaman` INTEGER NOT NULL, `idBuku` INTEGER, `judul` TEXT, `pengarang` TEXT, `jenisBuku` TEXT, `sampul` TEXT, `tglPinjam` TEXT, `tglKembaliSeharusnya` TEXT, `tglKembaliAsli` TEXT, `status` TEXT, `statusLabel` TEXT, `perpanjanganStatus` TEXT, `kondisiKembali` TEXT, `sisaHari` INTEGER, `dendaBerjalan` INTEGER, `dendaTercatat` INTEGER, `hariTerlambat` INTEGER, `catatanAdmin` TEXT, `denda` INTEGER, `message` TEXT, PRIMARY KEY(`idPeminjaman`))")
        connection.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)")
        connection.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '070375bf3f4794ed4c3394f1e5bd3ad2')")
      }

      public override fun dropAllTables(connection: SQLiteConnection) {
        connection.execSQL("DROP TABLE IF EXISTS `books`")
        connection.execSQL("DROP TABLE IF EXISTS `loans`")
      }

      public override fun onCreate(connection: SQLiteConnection) {
      }

      public override fun onOpen(connection: SQLiteConnection) {
        internalInitInvalidationTracker(connection)
      }

      public override fun onPreMigrate(connection: SQLiteConnection) {
        dropFtsSyncTriggers(connection)
      }

      public override fun onPostMigrate(connection: SQLiteConnection) {
      }

      public override fun onValidateSchema(connection: SQLiteConnection):
          RoomOpenDelegate.ValidationResult {
        val _columnsBooks: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsBooks.put("idBuku", TableInfo.Column("idBuku", "INTEGER", true, 1, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsBooks.put("barcode", TableInfo.Column("barcode", "TEXT", false, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsBooks.put("judul", TableInfo.Column("judul", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsBooks.put("pengarang", TableInfo.Column("pengarang", "TEXT", false, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsBooks.put("penerbit", TableInfo.Column("penerbit", "TEXT", false, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsBooks.put("jenisBuku", TableInfo.Column("jenisBuku", "TEXT", false, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsBooks.put("sinopsis", TableInfo.Column("sinopsis", "TEXT", false, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsBooks.put("stok", TableInfo.Column("stok", "INTEGER", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsBooks.put("sampul", TableInfo.Column("sampul", "TEXT", false, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsBooks.put("sedangDipinjam", TableInfo.Column("sedangDipinjam", "INTEGER", false, 0,
            null, TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysBooks: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        val _indicesBooks: MutableSet<TableInfo.Index> = mutableSetOf()
        val _infoBooks: TableInfo = TableInfo("books", _columnsBooks, _foreignKeysBooks,
            _indicesBooks)
        val _existingBooks: TableInfo = read(connection, "books")
        if (!_infoBooks.equals(_existingBooks)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |books(com.sipus.core.data.local.entity.BookEntity).
              | Expected:
              |""".trimMargin() + _infoBooks + """
              |
              | Found:
              |""".trimMargin() + _existingBooks)
        }
        val _columnsLoans: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsLoans.put("idPeminjaman", TableInfo.Column("idPeminjaman", "INTEGER", true, 1, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsLoans.put("idBuku", TableInfo.Column("idBuku", "INTEGER", false, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsLoans.put("judul", TableInfo.Column("judul", "TEXT", false, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsLoans.put("pengarang", TableInfo.Column("pengarang", "TEXT", false, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsLoans.put("jenisBuku", TableInfo.Column("jenisBuku", "TEXT", false, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsLoans.put("sampul", TableInfo.Column("sampul", "TEXT", false, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsLoans.put("tglPinjam", TableInfo.Column("tglPinjam", "TEXT", false, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsLoans.put("tglKembaliSeharusnya", TableInfo.Column("tglKembaliSeharusnya", "TEXT",
            false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsLoans.put("tglKembaliAsli", TableInfo.Column("tglKembaliAsli", "TEXT", false, 0,
            null, TableInfo.CREATED_FROM_ENTITY))
        _columnsLoans.put("status", TableInfo.Column("status", "TEXT", false, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsLoans.put("statusLabel", TableInfo.Column("statusLabel", "TEXT", false, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsLoans.put("perpanjanganStatus", TableInfo.Column("perpanjanganStatus", "TEXT",
            false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsLoans.put("kondisiKembali", TableInfo.Column("kondisiKembali", "TEXT", false, 0,
            null, TableInfo.CREATED_FROM_ENTITY))
        _columnsLoans.put("sisaHari", TableInfo.Column("sisaHari", "INTEGER", false, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsLoans.put("dendaBerjalan", TableInfo.Column("dendaBerjalan", "INTEGER", false, 0,
            null, TableInfo.CREATED_FROM_ENTITY))
        _columnsLoans.put("dendaTercatat", TableInfo.Column("dendaTercatat", "INTEGER", false, 0,
            null, TableInfo.CREATED_FROM_ENTITY))
        _columnsLoans.put("hariTerlambat", TableInfo.Column("hariTerlambat", "INTEGER", false, 0,
            null, TableInfo.CREATED_FROM_ENTITY))
        _columnsLoans.put("catatanAdmin", TableInfo.Column("catatanAdmin", "TEXT", false, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsLoans.put("denda", TableInfo.Column("denda", "INTEGER", false, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsLoans.put("message", TableInfo.Column("message", "TEXT", false, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysLoans: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        val _indicesLoans: MutableSet<TableInfo.Index> = mutableSetOf()
        val _infoLoans: TableInfo = TableInfo("loans", _columnsLoans, _foreignKeysLoans,
            _indicesLoans)
        val _existingLoans: TableInfo = read(connection, "loans")
        if (!_infoLoans.equals(_existingLoans)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |loans(com.sipus.core.data.local.entity.LoanEntity).
              | Expected:
              |""".trimMargin() + _infoLoans + """
              |
              | Found:
              |""".trimMargin() + _existingLoans)
        }
        return RoomOpenDelegate.ValidationResult(true, null)
      }
    }
    return _openDelegate
  }

  protected override fun createInvalidationTracker(): InvalidationTracker {
    val _shadowTablesMap: MutableMap<String, String> = mutableMapOf()
    val _viewTables: MutableMap<String, Set<String>> = mutableMapOf()
    return InvalidationTracker(this, _shadowTablesMap, _viewTables, "books", "loans")
  }

  public override fun clearAllTables() {
    super.performClear(false, "books", "loans")
  }

  protected override fun getRequiredTypeConverterClasses(): Map<KClass<*>, List<KClass<*>>> {
    val _typeConvertersMap: MutableMap<KClass<*>, List<KClass<*>>> = mutableMapOf()
    _typeConvertersMap.put(BookDao::class, BookDao_Impl.getRequiredConverters())
    _typeConvertersMap.put(LoanDao::class, LoanDao_Impl.getRequiredConverters())
    return _typeConvertersMap
  }

  public override fun getRequiredAutoMigrationSpecClasses(): Set<KClass<out AutoMigrationSpec>> {
    val _autoMigrationSpecsSet: MutableSet<KClass<out AutoMigrationSpec>> = mutableSetOf()
    return _autoMigrationSpecsSet
  }

  public override
      fun createAutoMigrations(autoMigrationSpecs: Map<KClass<out AutoMigrationSpec>, AutoMigrationSpec>):
      List<Migration> {
    val _autoMigrations: MutableList<Migration> = mutableListOf()
    return _autoMigrations
  }

  public override fun bookDao(): BookDao = _bookDao.value

  public override fun loanDao(): LoanDao = _loanDao.value
}
