package com.iqbalproject.pj_pos.utils

class Constants {
    companion object {
        //const val BASE_URL = "https://pj-pos.000webhostapp.com/"
        const val BASE_URL = "http://192.168.43.155/pjpos-restapi/api/"
        const val API_KEY = "putrajaya2020"

        const val REQ_TIMEOUT_DURATION = 60
        const val RES_ON_TIMEOUT = "Request timeout to server, please check your connection!"

        //Session Manager
        const val PREF_LOGIN: String = "LoginUser"
        const val IS_LOGIN: String = "isLoggedIn"
        const val KEY_ID: String = "userId"
        const val KEY_NAME: String = "name"
        const val KEY_POSITION: String = "position"

        const val STAT_HUTANG = "Terhutang"
        const val STAT_LUNAS = "Lunas"
        const val STAT_HUTANG_1 =  "OPEN"
        const val STAT_HUTANG_2 =  "CLOSED"

        const val SALES = "SALES"
        const val PURCHASE = "PURCHASE"

    }
}