package com.example.projetkotlin.bdd

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(
    entities = [Site::class],
    version = 1
)
abstract class Base: RoomDatabase() {
    abstract fun SitesDAO(): SitesDAO

    companion object {
        private var instance: Base? = null
        fun getInstance(context: Context): Base {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context,
                    Base::class.java,
                    "module.sqlite"
                ).build()
            }
            return instance!!
        }
    }
}