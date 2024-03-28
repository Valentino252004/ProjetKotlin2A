package com.example.projetkotlin.retrofit

import java.util.Date

data class Dive(
    val id: Int? = null,
    val idBoat: Int? = null,
    val idMemberSecurity: Int? = null,
    val idMemberPilot: Int? = null,
    val idMemberLead: Int? = null,
    val idSite: Int? = null,
    val idPrerogative: Int? = null,
    val date: Date? = null,
    val minMembers: Int? = null,
    val maxMembers: Int? = null,
    val observation: String? = null,
)