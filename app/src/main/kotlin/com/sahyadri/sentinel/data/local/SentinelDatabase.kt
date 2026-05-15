package com.sahyadri.sentinel.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sahyadri.sentinel.data.local.dao.ReportDao
import com.sahyadri.sentinel.data.local.entity.ReportEntity

@Database(entities = [ReportEntity::class], version = 1, exportSchema = false)
abstract class SentinelDatabase : RoomDatabase() {
    abstract val reportDao: ReportDao

    companion object {
        const val DATABASE_NAME = "sentinel_db"
    }
}
