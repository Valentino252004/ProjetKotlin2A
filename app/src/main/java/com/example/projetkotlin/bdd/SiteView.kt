package com.example.projetkotlin.bdd

import android.app.Application
import androidx.lifecycle.AndroidViewModel

class SiteView(application: Application): AndroidViewModel(application) {
    private val base = Base.getInstance(application)
    val dao = base.sitesDAO()
}