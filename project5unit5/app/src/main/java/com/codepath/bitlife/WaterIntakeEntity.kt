package com.codepath.bitlife.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "water_intake_table")
data class WaterIntakeEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val date: String,
    val amount: Int
)
