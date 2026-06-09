package com.sipus.core.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sipus.core.data.local.entity.LoanEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface LoanDao {
    @Query("SELECT * FROM loans")
    fun getAllLoans(): Flow<List<LoanEntity>>

    @Query("SELECT * FROM loans WHERE status = :status")
    fun getLoansByStatus(status: String): Flow<List<LoanEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLoans(loans: List<LoanEntity>)

    @Query("DELETE FROM loans WHERE status = :status")
    suspend fun deleteLoansByStatus(status: String)

    @Query("DELETE FROM loans")
    suspend fun deleteAllLoans()
}
