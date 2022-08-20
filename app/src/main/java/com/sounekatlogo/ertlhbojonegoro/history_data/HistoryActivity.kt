package com.sounekatlogo.ertlhbojonegoro.history_data

import android.annotation.SuppressLint
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.sounekatlogo.ertlhbojonegoro.databinding.ActivityHistoryBinding
import com.sounekatlogo.ertlhbojonegoro.survey.SurveyAdapter
import com.sounekatlogo.ertlhbojonegoro.survey.SurveyModel
import com.sounekatlogo.ertlhbojonegoro.utils.DBHelper

class HistoryActivity : AppCompatActivity() {

    private var _binding : ActivityHistoryBinding? = null
    private val binding get() = _binding!!
    private var adapter : HistoryAdapter? = null
    private var surveyList = ArrayList<SurveyModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getData()
        initRecyclerView()

        binding.backButton.setOnClickListener {
            onBackPressed()
        }

    }

    @SuppressLint("Range")
    private fun getData() {
        surveyList.clear()
        val prefs = getSharedPreferences("USER_DATA", Context.MODE_PRIVATE)
        val myUid = prefs.getString("uid", "").toString()

        val db = DBHelper(this, null)
        val cursor = db.getAllSurvey(myUid)
        cursor!!.moveToFirst()

        val id = cursor.getString(cursor.getColumnIndex(DBHelper.ID_COL))
        val uid = cursor.getString(cursor.getColumnIndex(DBHelper.uid))
        val nama = cursor.getString(cursor.getColumnIndex(DBHelper.nama))
        val nik = cursor.getString(cursor.getColumnIndex(DBHelper.nik))
        val nokk = cursor.getString(cursor.getColumnIndex(DBHelper.noKK))
        val alamat = cursor.getString(cursor.getColumnIndex(DBHelper.alamat))
        val desa = cursor.getString(cursor.getColumnIndex(DBHelper.desa))
        val kecamatan = cursor.getString(cursor.getColumnIndex(DBHelper.kecamatan))
        val jumlahkk = cursor.getString(cursor.getColumnIndex(DBHelper.jumlahKK))
        val jumlahPenghuni = cursor.getString(cursor.getColumnIndex(DBHelper.jumlahPenghuni))
        val penghasilan = cursor.getString(cursor.getColumnIndex(DBHelper.penghasilanKK))
        val luas = cursor.getString(cursor.getColumnIndex(DBHelper.luasRumah))
        val pondasi = cursor.getString(cursor.getColumnIndex(DBHelper.fondasi))
        val sloof = cursor.getString(cursor.getColumnIndex(DBHelper.sloof))
        val kolom = cursor.getString(cursor.getColumnIndex(DBHelper.kolom))
        val ring = cursor.getString(cursor.getColumnIndex(DBHelper.ringBalok))
        val kuda = cursor.getString(cursor.getColumnIndex(DBHelper.kudaKuda))
        val dinding = cursor.getString(cursor.getColumnIndex(DBHelper.dinding))
        val lantai = cursor.getString(cursor.getColumnIndex(DBHelper.lantai))
        val penutup = cursor.getString(cursor.getColumnIndex(DBHelper.penutupAtap))
        val statusPenguasaanLahan = cursor.getString(cursor.getColumnIndex(DBHelper.statusPenguasaanLahan))
        val koordinat = cursor.getString(cursor.getColumnIndex(DBHelper.koordinat))
        val ktp = cursor.getString(cursor.getColumnIndex(DBHelper.ktp))
        val samping = cursor.getString(cursor.getColumnIndex(DBHelper.samping))
        val dalam = cursor.getString(cursor.getColumnIndex(DBHelper.dalamRumah))
        val status = cursor.getString(cursor.getColumnIndex(DBHelper.status))
        val date = cursor.getString(cursor.getColumnIndex(DBHelper.date))

        surveyList.add(
            SurveyModel(
                id1 = id.toInt(),
                uid1 = uid,
                nama1 = nama,
                nik1 = nik,
                noKK1 = nokk,
                alamat1 = alamat,
                desa1 = desa,
                kecamatan1 = kecamatan,
                jumlahKK1 = jumlahkk,
                jumlahPenghuni1 = jumlahPenghuni,
                penghasilan1 = penghasilan,
                luasRumah1 = luas,
                pondasi1 = pondasi,
                sloof1 = sloof,
                kolom1 = kolom,
                ringBalok1 = ring,
                kudaKuda1 = kuda,
                dinding1 = dinding,
                lantai1 = lantai,
                penutupAtap1 = penutup,
                statusPenguasaanLahan1 = statusPenguasaanLahan,
                koordinat1 = koordinat,
                ktp1 = ktp,
                samping1 = samping,
                dalamRumah1 = dalam,
                status1 = status,
                date1 = date,
            )
        )


        while (cursor.moveToNext()) {
            val id = cursor.getString(cursor.getColumnIndex(DBHelper.ID_COL))
            val uid = cursor.getString(cursor.getColumnIndex(DBHelper.uid))
            val nama = cursor.getString(cursor.getColumnIndex(DBHelper.nama))
            val nik = cursor.getString(cursor.getColumnIndex(DBHelper.nik))
            val nokk = cursor.getString(cursor.getColumnIndex(DBHelper.noKK))
            val alamat = cursor.getString(cursor.getColumnIndex(DBHelper.alamat))
            val desa = cursor.getString(cursor.getColumnIndex(DBHelper.desa))
            val kecamatan = cursor.getString(cursor.getColumnIndex(DBHelper.kecamatan))
            val jumlahkk = cursor.getString(cursor.getColumnIndex(DBHelper.jumlahKK))
            val jumlahPenghuni = cursor.getString(cursor.getColumnIndex(DBHelper.jumlahPenghuni))
            val penghasilan = cursor.getString(cursor.getColumnIndex(DBHelper.penghasilanKK))
            val luas = cursor.getString(cursor.getColumnIndex(DBHelper.luasRumah))
            val pondasi = cursor.getString(cursor.getColumnIndex(DBHelper.fondasi))
            val sloof = cursor.getString(cursor.getColumnIndex(DBHelper.sloof))
            val kolom = cursor.getString(cursor.getColumnIndex(DBHelper.kolom))
            val ring = cursor.getString(cursor.getColumnIndex(DBHelper.ringBalok))
            val kuda = cursor.getString(cursor.getColumnIndex(DBHelper.kudaKuda))
            val dinding = cursor.getString(cursor.getColumnIndex(DBHelper.dinding))
            val lantai = cursor.getString(cursor.getColumnIndex(DBHelper.lantai))
            val penutup = cursor.getString(cursor.getColumnIndex(DBHelper.penutupAtap))
            val statusPenguasaanLahan = cursor.getString(cursor.getColumnIndex(DBHelper.statusPenguasaanLahan))
            val koordinat = cursor.getString(cursor.getColumnIndex(DBHelper.koordinat))
            val ktp = cursor.getString(cursor.getColumnIndex(DBHelper.ktp))
            val samping = cursor.getString(cursor.getColumnIndex(DBHelper.samping))
            val dalam = cursor.getString(cursor.getColumnIndex(DBHelper.dalamRumah))
            val status = cursor.getString(cursor.getColumnIndex(DBHelper.status))
            val date = cursor.getString(cursor.getColumnIndex(DBHelper.date))

            surveyList.add(
                SurveyModel(
                    id1 = id.toInt(),
                    uid1 = uid,
                    nama1 = nama,
                    nik1 = nik,
                    noKK1 = nokk,
                    alamat1 = alamat,
                    desa1 = desa,
                    kecamatan1 = kecamatan,
                    jumlahKK1 = jumlahkk,
                    jumlahPenghuni1 = jumlahPenghuni,
                    penghasilan1 = penghasilan,
                    luasRumah1 = luas,
                    pondasi1 = pondasi,
                    sloof1 = sloof,
                    kolom1 = kolom,
                    ringBalok1 = ring,
                    kudaKuda1 = kuda,
                    dinding1 = dinding,
                    lantai1 = lantai,
                    penutupAtap1 = penutup,
                    statusPenguasaanLahan1 = statusPenguasaanLahan,
                    koordinat1 = koordinat,
                    ktp1 = ktp,
                    samping1 = samping,
                    dalamRumah1 = dalam,
                    status1 = status,
                    date1 = date,
                )
            )
        }


        adapter?.setData(surveyList)
        cursor.close()
    }

    private fun initRecyclerView() {
        val layoutManager = LinearLayoutManager(this)
        layoutManager.reverseLayout = true
        layoutManager.stackFromEnd = true
        binding.rvSurvery.layoutManager = layoutManager
        adapter = HistoryAdapter()
        binding.rvSurvery.adapter = adapter
        adapter!!.setData(surveyList)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}