package com.example.projetkotlin.bdd

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface SitesDAO {
    @Insert
    fun insert(vararg insert: Site)

    @Insert
    fun insertOne(insert: Site)

    @Query("SELECT * FROM Site WHERE id=:id")
    fun getSite(id: Int): LiveData<Site>

    @Query("SELECT * FROM Site")
    fun getAllSite(): LiveData<List<Site>>
}