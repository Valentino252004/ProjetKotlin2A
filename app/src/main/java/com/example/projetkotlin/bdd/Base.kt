package com.example.projetkotlin.bdd

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(
    entities = [Site::class],
    version = 1
)
abstract class Base: RoomDatabase() {
    abstract fun sitesDAO(): SitesDAO

    companion object {
        private var instance: Base? = null
        fun getInstance(context: Context): Base {
            Log.d("tag", "creation de l'instance")
            if (instance == null) {
                Log.d("tag", "instance null")
                instance = Room.databaseBuilder(
                    context,
                    Base::class.java,
                    "modules.sqlite"
                ).build()
                Log.d("tag", "instance crée : ${instance.toString()}")
            }
            Log.d("tag", "instance retournée !( ${instance.toString()})")
            return instance!!
        }
    }
}