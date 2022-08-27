package com.sounekatlogo.ertlhbojonegoro.survey

import android.Manifest
import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.net.Uri
import android.os.Bundle
import android.os.Looper
import android.os.PersistableBundle
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
    private var mProgressDialog: ProgressDialog? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivitySurveyBinding.inflate(layoutInflater)
        setContentView(binding.root)
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        checkDistance()
        mProgressDialog = ProgressDialog(this)


        val prefs = getSharedPreferences("USER_DATA", Context.MODE_PRIVATE)
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
                showProgressBar()
                ImagePicker.with(this@SurveyActivity)
                    .cameraOnly()
                    .compress(1024)
                    .start(REQUEST_KTP_GALLERY)
            }

            fotoTampakSampingHint.setOnClickListener {
                showProgressBar()
                ImagePicker.with(this@SurveyActivity)
                    .cameraOnly()
                    .compress(1024)
                    .start(REQUEST_SAMPING_GALLERY)
            }

            fotoDalamRumahHint.setOnClickListener {
                showProgressBar()
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

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("fondasi", fondasi)
        outState.putString("sloof", sloof)
        outState.putString("ringBalok", ringBalok)
        outState.putString("kudaKuda", kudaKuda)
        outState.putString("dinding", dinding)
        outState.putString("lantai", lantai)
        outState.putString("penutupAtap", penutupAtap)
        outState.putString("statusPenguasaanLahan", statusPenguasaanLahan)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        fondasi = savedInstanceState.getString("fondasi").toString()
        sloof = savedInstanceState.getString("sloof").toString()
        ringBalok = savedInstanceState.getString("ringBalok").toString()
        kudaKuda = savedInstanceState.getString("kudaKuda").toString()
        dinding = savedInstanceState.getString("dinding").toString()
        lantai = savedInstanceState.getString("lantai").toString()
        penutupAtap = savedInstanceState.getString("penutupAtap").toString()
        statusPenguasaanLahan = savedInstanceState.getString("statusPenguasaanLahan").toString()
    }

    private fun showProgressBar() {
        mProgressDialog?.setMessage("Mohon tunggu hingga proses selesai...")
        mProgressDialog?.setCanceledOnTouchOutside(false)
        mProgressDialog?.show()
    }

    private fun unchecked() {
        binding.rga.clearCheck()
        binding.rgb.clearCheck()
        binding.rgbb.clearCheck()
        binding.rgc.clearCheck()
        binding.rgd.clearCheck()
        binding.rge.clearCheck()
        binding.rgee.clearCheck()
        binding.rgf.clearCheck()
        binding.rgg.clearCheck()
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
        val locationManager: LocationManager =
            getSystemService(Context.LOCATION_SERVICE) as LocationManager
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
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            ),
            permissionID
        )
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == permissionID) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                checkDistance()
            }
        }
    }

    private fun saveFormSurvey() {
        binding.apply {
            penghasilanKK.setLocale(Locale.US)
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


            if (nama.isEmpty()) {
                Toast.makeText(this@SurveyActivity, "Nama tidak boleh kosong", Toast.LENGTH_SHORT)
                    .show()
            } else if (nik.isEmpty()) {
                Toast.makeText(this@SurveyActivity, "NIK tidak boleh kosong", Toast.LENGTH_SHORT)
                    .show()
            } else if (nik.length != 16) {
                Toast.makeText(this@SurveyActivity, "NIK harus 16 digit", Toast.LENGTH_SHORT).show()
            } else if (noKK.isEmpty()) {
                Toast.makeText(this@SurveyActivity, "NO KK tidak boleh kosong", Toast.LENGTH_SHORT)
                    .show()
            } else if (noKK.length != 16) {
                Toast.makeText(this@SurveyActivity, "NO KK harus 16 digit", Toast.LENGTH_SHORT)
                    .show()
            } else if (alamat.isEmpty()) {
                Toast.makeText(this@SurveyActivity, "ALAMAT tidak boleh kosong", Toast.LENGTH_SHORT)
                    .show()
            } else if (desa.isEmpty()) {
                Toast.makeText(this@SurveyActivity, "DESA tidak boleh kosong", Toast.LENGTH_SHORT)
                    .show()
            } else if (kecamatan.isEmpty()) {
                Toast.makeText(
                    this@SurveyActivity,
                    "KECAMATAN tidak boleh kosong",
                    Toast.LENGTH_SHORT
                ).show()
            } else if (jumlahKK.isEmpty()) {
                Toast.makeText(
                    this@SurveyActivity,
                    "Jumlah KK dalam 1 rumah tidak boleh kosong",
                    Toast.LENGTH_SHORT
                ).show()
            } else if (jumlahPenghuni.isEmpty()) {
                Toast.makeText(
                    this@SurveyActivity,
                    "Jumlah penghuni rumah tidak boleh kosong",
                    Toast.LENGTH_SHORT
                ).show()
            } else if (penghasilan.isEmpty()) {
                Toast.makeText(
                    this@SurveyActivity,
                    "Penghasilan per KK tidak boleh kosong",
                    Toast.LENGTH_SHORT
                ).show()
            } else if (luasRumah.isEmpty()) {
                Toast.makeText(
                    this@SurveyActivity,
                    "Luas Rumah tidak boleh kosong",
                    Toast.LENGTH_SHORT
                ).show()
            } else if (koordinat.isEmpty()) {
                Toast.makeText(
                    this@SurveyActivity,
                    "koordinat tidak boleh kosong",
                    Toast.LENGTH_SHORT
                ).show()
            } else if (ktpp == "") {
                Toast.makeText(this@SurveyActivity, "KTP tidak boleh kosong", Toast.LENGTH_SHORT)
                    .show()
            } else if (samping == "") {
                Toast.makeText(
                    this@SurveyActivity,
                    "Foto tampak samping tidak boleh kosong",
                    Toast.LENGTH_SHORT
                ).show()
            } else if (dalamRumah == "") {
                Toast.makeText(
                    this@SurveyActivity,
                    "Foto dalam rumah tidak boleh kosong",
                    Toast.LENGTH_SHORT
                ).show()
            } else if (fondasi == "") {
                Toast.makeText(
                    this@SurveyActivity,
                    "Fondasi tidak boleh kosong",
                    Toast.LENGTH_SHORT
                ).show()
            } else if (sloof == "") {
                Toast.makeText(this@SurveyActivity, "Sloof tidak boleh kosong", Toast.LENGTH_SHORT)
                    .show()
            } else if (kolom == "") {
                Toast.makeText(this@SurveyActivity, "Kolom tidak boleh kosong", Toast.LENGTH_SHORT)
                    .show()
            } else if (ringBalok == "") {
                Toast.makeText(
                    this@SurveyActivity,
                    "Ring Balok tidak boleh kosong",
                    Toast.LENGTH_SHORT
                ).show()
            } else if (kudaKuda == "") {
                Toast.makeText(
                    this@SurveyActivity,
                    "Kuda - Kuda tidak boleh kosong",
                    Toast.LENGTH_SHORT
                ).show()
            } else if (dinding == "") {
                Toast.makeText(
                    this@SurveyActivity,
                    "Dinding tidak boleh kosong",
                    Toast.LENGTH_SHORT
                ).show()
            } else if (lantai == "") {
                Toast.makeText(this@SurveyActivity, "Lantai tidak boleh kosong", Toast.LENGTH_SHORT)
                    .show()
            } else if (penutupAtap == "") {
                Toast.makeText(
                    this@SurveyActivity,
                    "Penutup Atap tidak boleh kosong",
                    Toast.LENGTH_SHORT
                ).show()
            } else if (statusPenguasaanLahan == "") {
                Toast.makeText(
                    this@SurveyActivity,
                    "Status Penguasaan Lahan tidak boleh kosong",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                val c = Calendar.getInstance()
                val df = SimpleDateFormat("dd MMMM yyyy, hh:mm", Locale.getDefault())
                val formattedDate = df.format(c.time)

                val db = DBHelper(this@SurveyActivity, null)

                val serverUid = System.currentTimeMillis().toString()
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
                    serverUid,
                    formattedDate
                )

                showSuccessDialog()
            }
        }
    }


    /// ini adalah program untuk menambahkan gambar kedalalam halaman ini
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        mProgressDialog?.dismiss()
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
                            fondasi = "0"
                        }
                    R.id.a2 ->
                        if (checked) {
                            fondasi = "1"
                        }

                    R.id.a3 ->
                        if (checked) {
                            fondasi = "2"
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
                            sloof = "0"
                        }
                    R.id.b2 ->
                        if (checked) {
                            sloof = "1"
                        }

                    R.id.b3 ->
                        if (checked) {
                            sloof = "2"
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
                            kolom = "0"
                        }
                    R.id.bb2 ->
                        if (checked) {
                            kolom = "1"
                        }

                    R.id.bb3 ->
                        if (checked) {
                            kolom = "2"
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
                            ringBalok = "0"
                        }
                    R.id.c2 ->
                        if (checked) {
                            ringBalok = "1"
                        }

                    R.id.c3 ->
                        if (checked) {
                            ringBalok = "2"
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
                            kudaKuda = "0"
                        }
                    R.id.d2 ->
                        if (checked) {
                            kudaKuda = "1"
                        }

                    R.id.d3 ->
                        if (checked) {
                            kudaKuda = "2"
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
                            dinding = "0"
                        }
                    R.id.e2 ->
                        if (checked) {
                            dinding = "1"
                        }

                    R.id.e3 ->
                        if (checked) {
                            dinding = "2"
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
                            lantai = "0"
                        }
                    R.id.ee2 ->
                        if (checked) {
                            lantai = "1"
                        }

                    R.id.ee3 ->
                        if (checked) {
                            lantai = "2"
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
                            penutupAtap = "0"
                        }
                    R.id.f2 ->
                        if (checked) {
                            penutupAtap = "1"
                        }

                    R.id.f3 ->
                        if (checked) {
                            penutupAtap = "2"
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
                            statusPenguasaanLahan = "0"
                        }
                    R.id.g2 ->
                        if (checked) {
                            statusPenguasaanLahan = "1"
                        }

                    R.id.g3 ->
                        if (checked) {
                            statusPenguasaanLahan = "2"
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
                    unchecked()
                    ktp.setImageResource(0)
                    fotoTampakSamping.setImageResource(0)
                    fotoDalamRumah.setImageResource(0)
                }
            }
            .show()
    }
}