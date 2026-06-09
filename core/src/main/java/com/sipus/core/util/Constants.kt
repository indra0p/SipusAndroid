package com.sipus.core.util

object Constants {
    const val BASE_URL = "https://sipusweb3-production.up.railway.app/api/"
    const val CONNECT_TIMEOUT = 30L
    const val READ_TIMEOUT = 60L
    const val WRITE_TIMEOUT = 60L

    const val ROLE_MAHASISWA = "mahasiswa"
    const val ROLE_KARYAWAN = "karyawan"
    const val ROLE_DOSEN = "dosen"
    const val ROLE_ADMIN = "admin"

    fun isStaff(role: String?): Boolean = role in listOf(ROLE_ADMIN, ROLE_KARYAWAN)
}
