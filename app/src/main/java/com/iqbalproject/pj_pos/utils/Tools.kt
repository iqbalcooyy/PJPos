package com.iqbalproject.pj_pos.utils

import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

class Tools {
    companion object {

        fun terbilang(x: Int): String {
            val satuan = arrayOf(
                " ",
                "Satu",
                "Dua",
                "Tiga",
                "Empat",
                "Lima",
                "Enam",
                "Tujuh",
                "Delapan",
                "Sembilan",
                "Sepuluh",
                "Sebelas"
            )

            var hasil = " "

            when (x) {
                in 0..11 -> hasil += satuan[x]
                in 1..19 -> hasil += "${terbilang(x - 10)} Belas"
                in 1..99 -> hasil += "${terbilang(x / 10)} Puluh ${terbilang(x % 10)}"
                in 1..199 -> hasil += "Seratus ${terbilang(x - 100)}"
                in 1..999 -> hasil += "${terbilang(x / 100)} Ratus ${terbilang(x % 100)}"
                in 1..1999 -> hasil += "Seribu ${terbilang(x - 1000)}"
                in 1..999999 -> hasil += "${terbilang(x / 1000)} Ribu ${terbilang(x % 1000)}"
                in 1..999999999 -> hasil += "${terbilang(x / 1000000)} Juta ${terbilang(x % 1000000)}"
                in 1000000000..9999999999 -> hasil += "${terbilang(x / 1000000000)} Milyar ${terbilang(x % 1000000000)}"
                else -> hasil += "Angka Terlalu Besar !"
            }

            return hasil
        }

        fun getCurrentDate(): String {
            val dateFormat = SimpleDateFormat("dd MMMM yyyy")
            dateFormat.timeZone = TimeZone.getTimeZone("UTC")
            val today: Date = Calendar.getInstance().time
            return dateFormat.format(today)
        }

        fun convertRupiahsFormat(number: Double): String {
            val localeID: Locale = Locale("in", "ID")
            val rupiahsFormat: NumberFormat = NumberFormat.getCurrencyInstance(localeID)

            return rupiahsFormat.format(number)
        }
    }
}