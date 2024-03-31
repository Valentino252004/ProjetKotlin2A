package com.example.projetkotlin.bdd

import androidx.room.PrimaryKey
import androidx.room.Entity

@Entity
data class Site(
    @PrimaryKey
    var id: Int = 0,
    var name: String
)