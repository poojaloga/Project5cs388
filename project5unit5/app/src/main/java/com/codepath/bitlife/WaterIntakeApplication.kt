package com.codepath.bitlife

import android.app.Application

class WaterIntakeApplication : Application() {
    val db by lazy { AppDatabase.getInstance(this) }
}
