package com.codepath.bitlife

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.codepath.bitlife.data.WaterIntakeEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface WaterIntakeDao {
    @Query("SELECT * FROM water_intake_table ORDER BY date DESC")
    fun getAllEntries(): Flow<List<WaterIntakeEntity>>

    @Insert
    fun insertAll(entries: List<WaterIntakeEntity>)

    @Query("DELETE FROM water_intake_table")
    fun deleteAllEntries()
}
