package com.sipus.core.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sipus.core.data.local.dao.BookDao
import com.sipus.core.data.local.dao.LoanDao
import com.sipus.core.data.local.entity.BookEntity
import com.sipus.core.data.local.entity.LoanEntity

@Database(entities = [BookEntity::class, LoanEntity::class], version = 2, exportSchema = false)
abstract class SipusDatabase : RoomDatabase() {
    abstract fun bookDao(): BookDao
    abstract fun loanDao(): LoanDao
}
