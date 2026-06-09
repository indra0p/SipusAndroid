package com.sipus.core.di

import android.content.Context
import androidx.room.Room
import com.sipus.core.data.local.SipusDatabase
import com.sipus.core.data.local.dao.BookDao
import com.sipus.core.data.local.dao.LoanDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): SipusDatabase {
        return Room.databaseBuilder(
            context,
            SipusDatabase::class.java,
            "sipus_db"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun provideBookDao(database: SipusDatabase): BookDao = database.bookDao()

    @Provides
    @Singleton
    fun provideLoanDao(database: SipusDatabase): LoanDao = database.loanDao()
}
