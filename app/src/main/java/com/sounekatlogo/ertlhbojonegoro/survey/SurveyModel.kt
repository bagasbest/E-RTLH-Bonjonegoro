package com.sounekatlogo.ertlhbojonegoro.survey

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SurveyModel(
    var uid1: String,
    var nama1: String,
    var nik1: String,
    var noKK1: String,
    var alamat1: String,
    var desa1: String,
    var kecamatan1: String,
    var jumlahKK1: String,
    var jumlahPenghuni1: String,
    var penghasilan1: String,
    var luasRumah1: String,
    var pondasi1: String,
    var sloof1: String,
    var kolom1: String,
    var ringBalok1: String,
    var kudaKuda1: String,
    var dinding1: String,
    var lantai1: String,
    var penutupAtap1: String,
    var statusPenguasaanLahan1: String,
    var koordinat1: String,
    var ktp1: String,
    var samping1: String,
    var dalamRumah1: String,
    var status1: String,
    var date1: String,
) : Parcelable