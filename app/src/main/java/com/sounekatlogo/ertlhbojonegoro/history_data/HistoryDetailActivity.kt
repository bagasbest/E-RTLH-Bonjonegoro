package com.sounekatlogo.ertlhbojonegoro.history_data

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.github.dhaval2404.imagepicker.ImagePicker
import com.sounekatlogo.ertlhbojonegoro.HomeActivity
import com.sounekatlogo.ertlhbojonegoro.LoginActivity
import com.sounekatlogo.ertlhbojonegoro.R
import com.sounekatlogo.ertlhbojonegoro.databinding.ActivityHistoryDetailBinding
import com.sounekatlogo.ertlhbojonegoro.survey.SurveyModel
import com.sounekatlogo.ertlhbojonegoro.utils.DBHelper
import java.text.SimpleDateFormat
import java.util.*

class HistoryDetailActivity : AppCompatActivity() {

    private var _binding: ActivityHistoryDetailBinding? = null
    private val binding get() = _binding!!
    private var fondasi = ""
    private var sloof = ""
    private var kolom = ""
    private var ringBalok = ""
    private var kudaKuda = ""
    private var dinding = ""
    private var lantai = ""
    private var penutupAtap = ""
    private var uid = ""
    private var statusPenguasaanLahan = ""
    private var model: SurveyModel? = null

    /// variable untuk permission ke galeri handphone
    private var ktpp: String = ""
    private var samping: String = ""
    private var dalamRumah: String = ""
    private val REQUEST_KTP_GALLERY = 1001
    private val REQUEST_SAMPING_GALLERY = 1002
    private val REQUEST_DALAM_RUMAH_GALLERY = 1003

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityHistoryDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        model = intent.getParcelableExtra(EXTRA_DATA)
        binding.apply {
            if (model?.status1 == "Belum Diupload") {
                save.visibility = View.VISIBLE
            }
            nama.setText(model?.nama1)
            nik.setText(model?.nik1)
            noKK.setText(model?.noKK1)
            alamat.setText(model?.alamat1)
            desa.setText(model?.desa1)
            kecamatan.setText(model?.kecamatan1)
            jumlahKK.setText(model?.jumlahKK1)
            jumlahPenghuni.setText(model?.jumlahKK1)
            penghasilanKK.setText(model?.penghasilan1)
            luasRumah.setText(model?.luasRumah1)
            koordinat.setText(model?.koordinat1)
            fondasi = model?.pondasi1.toString()
            sloof = model?.sloof1.toString()
            kolom = model?.kolom1.toString()
            ringBalok = model?.ringBalok1.toString()
            kudaKuda = model?.kudaKuda1.toString()
            dinding = model?.dinding1.toString()
            lantai = model?.lantai1.toString()
            penutupAtap = model?.penutupAtap1.toString()
            statusPenguasaanLahan = model?.statusPenguasaanLahan1.toString()
            ktpp = model?.ktp1.toString()
            samping = model?.samping1.toString()
            dalamRumah = model?.dalamRumah1.toString()


            when (fondasi) {
                a1.text.toString() -> {
                    a1.isChecked = true
                }
                a2.text.toString() -> {
                    a2.isChecked = true
                }
                else -> {
                    a3.isChecked = true
                }
            }

            when (sloof) {
                b1.text.toString() -> {
                    b1.isChecked = true
                }
                b2.text.toString() -> {
                    b2.isChecked = true
                }
                else -> {
                    b1.isChecked = true
                }
            }

            when (kolom) {
                bb1.text.toString() -> {
                    bb1.isChecked = true
                }
                bb2.text.toString() -> {
                    bb2.isChecked = true
                }
                else -> {
                    bb3.isChecked = true
                }
            }

            when (ringBalok) {
                c1.text.toString() -> {
                    c1.isChecked = true
                }
                c2.text.toString() -> {
                    c2.isChecked = true
                }
                else -> {
                    c3.isChecked = true
                }
            }

            when (kudaKuda) {
                d1.text.toString() -> {
                    d1.isChecked = true
                }
                d2.text.toString() -> {
                    d2.isChecked = true
                }
                else -> {
                    d3.isChecked = true
                }
            }


            when (dinding) {
                e1.text.toString() -> {
                    e1.isChecked = true
                }
                e2.text.toString() -> {
                    e2.isChecked = true
                }
                else -> {
                    e3.isChecked = true
                }
            }

            when (lantai) {
                ee1.text.toString() -> {
                    ee1.isChecked = true
                }
                ee2.text.toString() -> {
                    ee2.isChecked = true
                }
                else -> {
                    ee3.isChecked = true
                }
            }


            when (penutupAtap) {
                f1.text.toString() -> {
                    f1.isChecked = true
                }
                f2.text.toString() -> {
                    f2.isChecked = true
                }
                else -> {
                    f3.isChecked = true
                }
            }

            when (statusPenguasaanLahan) {
                g1.text.toString() -> {
                    g1.isChecked = true
                }
                g2.text.toString() -> {
                    g2.isChecked = true
                }
                else -> {
                    g3.isChecked = true
                }
            }


            if (ktpp != "") {
                Glide.with(this@HistoryDetailActivity)
                    .load(ktpp)
                    .into(ktp)
            }

            if (samping != "") {
                Glide.with(this@HistoryDetailActivity)
                    .load(samping)
                    .into(fotoTampakSamping)
            }


            if (dalamRumah != "") {
                Glide.with(this@HistoryDetailActivity)
                    .load(dalamRumah)
                    .into(fotoDalamRumah)
            }

            backButton.setOnClickListener {
                onBackPressed()
            }

            ktpHint.setOnClickListener {
                ImagePicker.with(this@HistoryDetailActivity)
                    .cameraOnly()
                    .compress(1024)
                    .start(REQUEST_KTP_GALLERY)
            }

            fotoTampakSampingHint.setOnClickListener {
                ImagePicker.with(this@HistoryDetailActivity)
                    .cameraOnly()
                    .compress(1024)
                    .start(REQUEST_SAMPING_GALLERY)
            }

            fotoDalamRumahHint.setOnClickListener {
                ImagePicker.with(this@HistoryDetailActivity)
                    .cameraOnly()
                    .compress(1024)
                    .start(REQUEST_DALAM_RUMAH_GALLERY)
            }

            save.setOnClickListener {
                updateData()
            }

            delete.setOnClickListener {
                deleteConformation()
            }
        }

    }

    private fun deleteConformation() {
        AlertDialog.Builder(this)
            .setTitle("Konfirmasi Menghapus Survey")
            .setMessage("Apakah anda yakin ingin menghapus survey ini ?")
            .setIcon(R.drawable.ic_baseline_warning_24)
            .setPositiveButton("YA") { dialogInterface, _ ->
                dialogInterface.dismiss()
                val db = DBHelper(this@HistoryDetailActivity, null)

                db.delete(model?.id1)
                Toast.makeText(this, "Berhasil menghapus survey", Toast.LENGTH_SHORT).show()

                val intent = Intent(this, HomeActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
                finish()
            }
            .show()
    }

    private fun updateData() {
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

            val db = DBHelper(this@HistoryDetailActivity, null)

            db.editSurvey(
                model!!.id1,
                model!!.uid1,
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
            .setTitle("Berhasil Memperbarui Data Survey")
            .setMessage("Anda sudah memperbarui survey")
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

                val intent = Intent(this, HomeActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
                finish()
            }
            .show()
    }

    companion object {
        const val EXTRA_DATA = "data"
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}