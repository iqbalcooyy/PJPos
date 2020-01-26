package com.iqbalproject.pj_pos.utils

class Terbilang {
    companion object {
        fun konversi(x: Int): String {
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
                in 1..19 -> hasil += "${konversi(x - 10)} Belas"
                in 1..99 -> hasil += "${konversi(x / 10)} Puluh ${konversi(x % 10)}"
                in 1..199 -> hasil += "Seratus ${konversi(x - 100)}"
                in 1..999 -> hasil += "${konversi(x / 100)} Ratus ${konversi(x % 100)}"
                in 1..1999 -> hasil += "Seribu ${konversi(x - 1000)}"
                in 1..999999 -> hasil += "${konversi(x / 1000)} Ribu ${konversi(x % 1000)}"
                in 1..999999999 -> hasil += "${konversi(x / 1000000)} Juta ${konversi(x % 1000000)}"
                in 1000000000..9999999999 -> hasil += "${konversi(x / 1000000000)} Milyar ${konversi(x % 1000000000)}"
                else -> hasil += "Angka Terlalu Besar !"
            }

            return hasil
        }
    }
}