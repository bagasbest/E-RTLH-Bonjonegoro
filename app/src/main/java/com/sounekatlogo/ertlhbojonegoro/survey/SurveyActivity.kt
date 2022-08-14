package com.sounekatlogo.ertlhbojonegoro.survey

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.github.dhaval2404.imagepicker.ImagePicker
import com.sounekatlogo.ertlhbojonegoro.R
import com.sounekatlogo.ertlhbojonegoro.databinding.ActivitySurveyBinding
import com.sounekatlogo.ertlhbojonegoro.utils.DBHelper
import java.text.SimpleDateFormat
import java.util.*

class SurveyActivity : AppCompatActivity() {

    private var _binding: ActivitySurveyBinding? = null
    private val binding get() = _binding!!

    /// variable untuk menampung gambar dari galeri handphone
    private var ktpp: String = ""
    private var samping: String = ""
    private var dalamRumah: String = ""
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
    private val REQUEST_KTP_GALLERY = 1001
    private val REQUEST_SAMPING_GALLERY = 1002
    private val REQUEST_DALAM_RUMAH_GALLERY = 1003

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivitySurveyBinding.inflate(layoutInflater)
        setContentView(binding.root)

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


    /// ini adalah program untuk menambahkan gambar kedalalam halaman ini
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            when (requestCode) {
                REQUEST_KTP_GALLERY -> {
                    uploadImageToDatabase(data?.data, "ktp")
                    ktpp = data?.data.toString()
                    Glide.with(this)
                        .load(data?.data)
                        .into(binding.ktp)
                }
                REQUEST_SAMPING_GALLERY -> {
                    uploadImageToDatabase(data?.data, "samping_rumah")
                    samping = data?.data.toString()
                    Glide.with(this)
                        .load(data?.data)
                        .into(binding.fotoTampakSamping)
                }
                REQUEST_DALAM_RUMAH_GALLERY -> {
                    uploadImageToDatabase(data?.data, "dalam_rumah")
                    dalamRumah = data?.data.toString()
                    Glide.with(this)
                        .load(data?.data)
                        .into(binding.fotoDalamRumah)
                }
            }
        }
    }


    /// fungsi untuk mengupload foto kedalam cloud storage
    private fun uploadImageToDatabase(data: Uri?, dir: String) {

        // val mStorageRef = FirebaseStorage.getInstance().reference

//        val mProgressDialog = ProgressDialog(this)
//        mProgressDialog.setMessage("Mohon tunggu hingga proses selesai...")
//        mProgressDialog.setCanceledOnTouchOutside(false)
//        mProgressDialog.show()
//
//
//        val imageFileName = "$dir/image_" + System.currentTimeMillis() + ".png"
//        /// proses upload gambar ke databsae
//        mStorageRef.child(imageFileName).putFile(data!!)
//            .addOnSuccessListener {
//                mStorageRef.child(imageFileName).downloadUrl
//                    .addOnSuccessListener { uri: Uri ->
//
//                        /// proses upload selesai, berhasil
//                        mProgressDialog.dismiss()
//                        image = uri.toString()
//                        Glide.with(this)
//                            .load(image)
//                            .into(binding!!.image)
//                    }
//
//                    /// proses upload selesai, gagal
//                    .addOnFailureListener { e: Exception ->
//                        mProgressDialog.dismiss()
//                        Toast.makeText(
//                            this,
//                            "Gagal mengunggah gambar",
//                            Toast.LENGTH_SHORT
//                        ).show()
//                        Log.d("imageDp: ", e.toString())
//                    }
//            }
//            /// proses upload selesai, gagal
//            .addOnFailureListener { e: Exception ->
//                mProgressDialog.dismiss()
//                Toast.makeText(
//                    this,
//                    "Gagal mengunggah gambar",
//                    Toast.LENGTH_SHORT
//                )
//                    .show()
//                Log.d("imageDp: ", e.toString())
//            }
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
                    desa.setText("")
                    kecamatan.setText("")
                    jumlahKK.setText("")
                    jumlahPenghuni.setText("")
                    penghasilanKK.setText("")
                    luasRumah.setText("")
                    koordinat.setText("")
                    ktpp = ""
                    samping = ""
                    dalamRumah = ""
                }
            }
            .show()
    }
}