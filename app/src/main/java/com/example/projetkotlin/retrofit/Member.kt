package com.example.projetkotlin.retrofit

import java.util.Date

data class Member (
    val id: Int? = null,
    val numLicense: String,
    val name: String,
    val surname: String,
    val dateMedicalCertification: Date? = null,
    val pricing: String? = null,
    val status: Int? = null,
    val remainingDives: Int? = null,
    val password: String? = null
)