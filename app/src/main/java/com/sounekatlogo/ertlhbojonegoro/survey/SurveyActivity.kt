package com.sounekatlogo.ertlhbojonegoro.survey

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.net.Uri
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.bumptech.glide.Glide
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.android.gms.location.*
import com.sounekatlogo.ertlhbojonegoro.R
import com.sounekatlogo.ertlhbojonegoro.databinding.ActivitySurveyBinding
import com.sounekatlogo.ertlhbojonegoro.utils.DBHelper
import com.sounekatlogo.ertlhbojonegoro.utils.GPSTracker
import java.text.SimpleDateFormat
import java.util.*


class SurveyActivity : AppCompatActivity() {

    private var _binding: ActivitySurveyBinding? = null
    private val binding get() = _binding!!

    /// variable untuk menampung gambar dari galeri handphone
    private var fondasi = ""
    private var sloof = ""
    private var kolom = ""
    private var ringBalok = ""
    private var kudaKuda = ""
    private var dinding = ""
    private var lantai = ""
    private var penutupAtap = ""
    private var statusPenguasaanLahan = ""
    private var uid = ""
    private var desa1 = ""
    private var kecamatan1 = ""

    /// variable untuk permission ke galeri handphone
    private var ktpp: String = ""
    private var samping: String = ""
    private var dalamRumah: String = ""
    private val REQUEST_KTP_GALLERY = 1001
    private val REQUEST_SAMPING_GALLERY = 1002
    private val REQUEST_DALAM_RUMAH_GALLERY = 1003
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private val permissionID = 101

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivitySurveyBinding.inflate(layoutInflater)
        setContentView(binding.root)
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        checkDistance()


        val prefs =  getSharedPreferences("USER_DATA", Context.MODE_PRIVATE)
        uid = prefs.getString("uid", "").toString()
        desa1 = prefs.getString("desa", "").toString()
        kecamatan1 = prefs.getString("kecamatan", "").toString()

        binding.apply {
            desa.setText(desa1)
            kecamatan.setText(kecamatan1)

            backButton.setOnClickListener {
                onBackPressed()
            }

            ktpHint.setOnClickListener {
                ImagePicker.with(this@SurveyActivity)
                    .cameraOnly()
                    .compress(1024)
                    .start(REQUEST_KTP_GALLERY)
            }

            fotoTampakSampingHint.setOnClickListener {
                ImagePicker.with(this@SurveyActivity)
                    .cameraOnly()
                    .compress(1024)
                    .start(REQUEST_SAMPING_GALLERY)
            }

            fotoDalamRumahHint.setOnClickListener {
                ImagePicker.with(this@SurveyActivity)
                    .cameraOnly()
                    .compress(1024)
                    .start(REQUEST_DALAM_RUMAH_GALLERY)
            }

            save.setOnClickListener {
                saveFormSurvey()
            }
        }


    }

    @SuppressLint("SetTextI18n", "MissingPermission")
    private fun checkDistance() {
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                fusedLocationProviderClient.lastLocation.addOnCompleteListener(this) { task ->
                    val location: Location? = task.result
                    if (location == null) {
                        requestNewLocationData()
                    } else {
                        binding.koordinat.setText("${location.latitude} - ${location.longitude}")

                    }
                }
            } else {
                Toast.makeText(this, "Turn on location", Toast.LENGTH_LONG).show()
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }
        } else {
            requestPermissions()
        }
    }

    @SuppressLint("MissingPermission")
    private fun requestNewLocationData() {
        val mLocationRequest = LocationRequest()
        mLocationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        mLocationRequest.interval = 0
        mLocationRequest.fastestInterval = 0
        mLocationRequest.numUpdates = 1

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        Looper.myLooper()?.let {
            fusedLocationProviderClient.requestLocationUpdates(
                mLocationRequest, mLocationCallback,
                it
            )
        }
    }

    private val mLocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            val mLastLocation: Location? = locationResult.lastLocation

            binding.koordinat.setText("${mLastLocation?.latitude} - ${mLastLocation?.longitude}")

        }
    }

    private fun isLocationEnabled(): Boolean {
        val locationManager: LocationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    private fun checkPermissions(): Boolean {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            return true
        }
        return false
    }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION),
            permissionID
        )
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == permissionID) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                checkDistance()
            }
        }
    }

    private fun saveFormSurvey() {
        binding.apply {
            val nama = nama.text.toString().trim()
            val nik = nik.text.toString().trim()
            val noKK = noKK.text.toString().trim()
            val alamat = alamat.text.toString().trim()
            val desa = desa.text.toString().trim()
            val kecamatan = kecamatan.text.toString().trim()
            val jumlahKK = jumlahKK.text.toString().trim()
            val jumlahPenghuni = jumlahPenghuni.text.toString().trim()
            val penghasilan = penghasilanKK.text.toString().trim()
            val luasRumah = luasRumah.text.toString().trim()
            val koordinat = koordinat.text.toString().trim()

            if(nama.isEmpty()) {
                Toast.makeText(this@SurveyActivity, "Nama tidak boleh kosong", Toast.LENGTH_SHORT).show()
            } else if (nik.isEmpty()) {
                Toast.makeText(this@SurveyActivity, "NIK tidak boleh kosong", Toast.LENGTH_SHORT).show()
            } else if (noKK.isEmpty()) {
                Toast.makeText(this@SurveyActivity, "NO KK tidak boleh kosong", Toast.LENGTH_SHORT).show()
            }else if (alamat.isEmpty()) {
                Toast.makeText(this@SurveyActivity, "ALAMAT tidak boleh kosong", Toast.LENGTH_SHORT).show()
            }else if (desa.isEmpty()) {
                Toast.makeText(this@SurveyActivity, "DESA tidak boleh kosong", Toast.LENGTH_SHORT).show()
            }else if (kecamatan.isEmpty()) {
                Toast.makeText(this@SurveyActivity, "KECAMATAN tidak boleh kosong", Toast.LENGTH_SHORT).show()
            }else if (jumlahKK.isEmpty()) {
                Toast.makeText(this@SurveyActivity, "Jumlah KK dalam 1 rumah tidak boleh kosong", Toast.LENGTH_SHORT).show()
            }else if (jumlahPenghuni.isEmpty()) {
                Toast.makeText(this@SurveyActivity, "Jumlah penghuni rumah tidak boleh kosong", Toast.LENGTH_SHORT).show()
            }else if (penghasilan.isEmpty()) {
                Toast.makeText(this@SurveyActivity, "Penghasilan per KK tidak boleh kosong", Toast.LENGTH_SHORT).show()
            }else if (luasRumah.isEmpty()) {
                Toast.makeText(this@SurveyActivity, "Luas Rumah tidak boleh kosong", Toast.LENGTH_SHORT).show()
            }else if (koordinat.isEmpty()) {
                Toast.makeText(this@SurveyActivity, "koordinat tidak boleh kosong", Toast.LENGTH_SHORT).show()
            }else if (ktpp == "") {
                Toast.makeText(this@SurveyActivity, "KTP tidak boleh kosong", Toast.LENGTH_SHORT).show()
            }else if (samping == "") {
                Toast.makeText(this@SurveyActivity, "Foto tampak samping tidak boleh kosong", Toast.LENGTH_SHORT).show()
            }else if (dalamRumah == "") {
                Toast.makeText(this@SurveyActivity, "Foto dalam rumah tidak boleh kosong", Toast.LENGTH_SHORT).show()
            }else if (fondasi == "") {
                Toast.makeText(this@SurveyActivity, "Fondasi tidak boleh kosong", Toast.LENGTH_SHORT).show()
            }else if (sloof == "") {
                Toast.makeText(this@SurveyActivity, "Sloof tidak boleh kosong", Toast.LENGTH_SHORT).show()
            }else if (kolom == "") {
                Toast.makeText(this@SurveyActivity, "Kolom tidak boleh kosong", Toast.LENGTH_SHORT).show()
            }else if (ringBalok == "") {
                Toast.makeText(this@SurveyActivity, "Ring Balok tidak boleh kosong", Toast.LENGTH_SHORT).show()
            }else if (kudaKuda == "") {
                Toast.makeText(this@SurveyActivity, "Kuda - Kuda tidak boleh kosong", Toast.LENGTH_SHORT).show()
            }else if (dinding == "") {
                Toast.makeText(this@SurveyActivity, "Dinding tidak boleh kosong", Toast.LENGTH_SHORT).show()
            }else if (lantai == "") {
                Toast.makeText(this@SurveyActivity, "Lantai tidak boleh kosong", Toast.LENGTH_SHORT).show()
            }else if (penutupAtap == "") {
                Toast.makeText(this@SurveyActivity, "Penutup Atap tidak boleh kosong", Toast.LENGTH_SHORT).show()
            }else if (statusPenguasaanLahan == "") {
                Toast.makeText(this@SurveyActivity, "Status Penguasaan Lahan tidak boleh kosong", Toast.LENGTH_SHORT).show()
            } else {
                val c = Calendar.getInstance()
                val df = SimpleDateFormat("dd MMMM yyyy, hh:mm", Locale.getDefault())
                val formattedDate = df.format(c.time)

                val db = DBHelper(this@SurveyActivity, null)

                db.addSurvey(
                    uid,
                    nama,
                    nik,
                    noKK,
                    alamat,
                    desa,
                    kecamatan,
                    jumlahKK,
                    jumlahPenghuni,
                    penghasilan,
                    luasRumah,
                    fondasi,
                    sloof,
                    kolom,
                    ringBalok,
                    kudaKuda,
                    dinding,
                    lantai,
                    penutupAtap,
                    statusPenguasaanLahan,
                    koordinat,
                    ktpp,
                    samping,
                    dalamRumah,
                    "Belum Diupload",
                    formattedDate
                )

                showSuccessDialog()
            }
        }
    }


    /// ini adalah program untuk menambahkan gambar kedalalam halaman ini
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            when (requestCode) {
                REQUEST_KTP_GALLERY -> {
                    ktpp = data?.data.toString()
                    Glide.with(this)
                        .load(data?.data)
                        .into(binding.ktp)
                }
                REQUEST_SAMPING_GALLERY -> {
                    samping = data?.data.toString()
                    Glide.with(this)
                        .load(data?.data)
                        .into(binding.fotoTampakSamping)
                }
                REQUEST_DALAM_RUMAH_GALLERY -> {
                    dalamRumah = data?.data.toString()
                    Glide.with(this)
                        .load(data?.data)
                        .into(binding.fotoDalamRumah)
                }
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    fun fondasi(view: View) {
        if (view is RadioButton) {
            val checked = view.isChecked
            binding.apply {
                when (view.getId()) {
                    R.id.a1 ->
                        if (checked) {
                            fondasi = a1.text.toString()
                        }
                    R.id.a2 ->
                        if (checked) {
                            fondasi = a2.text.toString()
                        }

                    R.id.a3 ->
                        if (checked) {
                            fondasi = a3.text.toString()
                        }
                }
            }
        }
    }

    fun sloof(view: View) {
        if (view is RadioButton) {
            val checked = view.isChecked
            binding.apply {
                when (view.getId()) {
                    R.id.b1 ->
                        if (checked) {
                            sloof = b1.text.toString()
                        }
                    R.id.b2 ->
                        if (checked) {
                            sloof = b2.text.toString()
                        }

                    R.id.b3 ->
                        if (checked) {
                            sloof = b3.text.toString()
                        }
                }
            }
        }
    }

    fun kolom(view: View) {
        if (view is RadioButton) {
            val checked = view.isChecked
            binding.apply {
                when (view.getId()) {
                    R.id.bb1 ->
                        if (checked) {
                            kolom = bb1.text.toString()
                        }
                    R.id.bb2 ->
                        if (checked) {
                            kolom = bb2.text.toString()
                        }

                    R.id.bb3 ->
                        if (checked) {
                            kolom = bb3.text.toString()
                        }
                }
            }
        }
    }

    fun ringBalok(view: View) {
        if (view is RadioButton) {
            val checked = view.isChecked
            binding.apply {
                when (view.getId()) {
                    R.id.c1 ->
                        if (checked) {
                            ringBalok = c1.text.toString()
                        }
                    R.id.c2 ->
                        if (checked) {
                            ringBalok = c2.text.toString()
                        }

                    R.id.c3 ->
                        if (checked) {
                            ringBalok = c3.text.toString()
                        }
                }
            }
        }
    }

    fun kudaKuda(view: View) {
        if (view is RadioButton) {
            val checked = view.isChecked
            binding.apply {
                when (view.getId()) {
                    R.id.d1 ->
                        if (checked) {
                            kudaKuda = d1.text.toString()
                        }
                    R.id.d2 ->
                        if (checked) {
                            kudaKuda = d2.text.toString()
                        }

                    R.id.d3 ->
                        if (checked) {
                            kudaKuda = d3.text.toString()
                        }
                }
            }
        }
    }

    fun dinding(view: View) {
        if (view is RadioButton) {
            val checked = view.isChecked
            binding.apply {
                when (view.getId()) {
                    R.id.e1 ->
                        if (checked) {
                            dinding = e1.text.toString()
                        }
                    R.id.e2 ->
                        if (checked) {
                            dinding = e2.text.toString()
                        }

                    R.id.e3 ->
                        if (checked) {
                            dinding = e3.text.toString()
                        }
                }
            }
        }
    }

    fun lantai(view: View) {
        if (view is RadioButton) {
            val checked = view.isChecked
            binding.apply {
                when (view.getId()) {
                    R.id.ee1 ->
                        if (checked) {
                            lantai = ee1.text.toString()
                        }
                    R.id.ee2 ->
                        if (checked) {
                            lantai = ee2.text.toString()
                        }

                    R.id.ee3 ->
                        if (checked) {
                            lantai = ee3.text.toString()
                        }
                }
            }
        }
    }

    fun penutupAtap(view: View) {
        if (view is RadioButton) {
            val checked = view.isChecked
            binding.apply {
                when (view.getId()) {
                    R.id.f1 ->
                        if (checked) {
                            penutupAtap = f1.text.toString()
                        }
                    R.id.f2 ->
                        if (checked) {
                            penutupAtap = f2.text.toString()
                        }

                    R.id.f3 ->
                        if (checked) {
                            penutupAtap = f3.text.toString()
                        }
                }
            }
        }
    }

    fun status(view: View) {
        if (view is RadioButton) {
            val checked = view.isChecked
            binding.apply {
                when (view.getId()) {
                    R.id.g1 ->
                        if (checked) {
                            statusPenguasaanLahan = g1.text.toString()
                        }
                    R.id.g2 ->
                        if (checked) {
                            statusPenguasaanLahan = g2.text.toString()
                        }

                    R.id.g3 ->
                        if (checked) {
                            statusPenguasaanLahan = g3.text.toString()
                        }
                }
            }
        }
    }


    private fun showSuccessDialog() {
        AlertDialog.Builder(this)
            .setTitle("Berhasil Menyimpan Survey")
            .setMessage("Anda dapat mengupload survey dengan menekan tombol Sync Data di halaman utama.")
            .setIcon(R.drawable.ic_baseline_check_circle_outline_24)
            .setPositiveButton("OKE") { dialogInterface, _ ->
                dialogInterface.dismiss()
                binding.apply {
                    nama.setText("")
                    nik.setText("")
                    noKK.setText("")
                    alamat.setText("")
                    jumlahKK.setText("")
                    jumlahPenghuni.setText("")
                    penghasilanKK.setText("")
                    luasRumah.setText("")
                    koordinat.setText("")
                    ktpp = ""
                    samping = ""
                    dalamRumah = ""
                    ktpp = ""
                    samping = ""
                    dalamRumah = ""
                    fondasi = ""
                    sloof = ""
                    kolom = ""
                    ringBalok = ""
                    kudaKuda = ""
                    dinding = ""
                    lantai = ""
                    penutupAtap = ""
                    uid = ""
                    statusPenguasaanLahan = ""
                }
            }
            .show()
    }
}