package com.sounekatlogo.ertlhbojonegoro.history_data

import android.app.ProgressDialog
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
import com.google.firebase.firestore.FirebaseFirestore
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
    private var option = ""
    private var mProgressDialog: ProgressDialog? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityHistoryDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mProgressDialog = ProgressDialog(this)

        model = intent.getParcelableExtra(EXTRA_DATA)
        option = intent.getStringExtra(OPTION).toString()
//        if(option == "local") {
//            binding.delete.visibility = View.VISIBLE
//        }
        binding.apply {
            if (model?.status1 == "Belum Diupload") {
                save.visibility = View.VISIBLE
                ktpHint.visibility = View.VISIBLE
                fotoTampakSampingHint.visibility = View.VISIBLE
                fotoDalamRumahHint.visibility = View.VISIBLE
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
                "0" -> {
                    a1.isChecked = true
                }
                "1" -> {
                    a2.isChecked = true
                }
                "2" -> {
                    a3.isChecked = true
                }
            }

            when (sloof) {
                "0" -> {
                    b1.isChecked = true
                }
                "1" -> {
                    b2.isChecked = true
                }
                "2" -> {
                    b1.isChecked = true
                }
            }

            when (kolom) {
               "0" -> {
                    bb1.isChecked = true
                }
               "1" -> {
                    bb2.isChecked = true
                }
                "2" -> {
                    bb3.isChecked = true
                }
            }

            when (ringBalok) {
                "0" -> {
                    c1.isChecked = true
                }
                "1" -> {
                    c2.isChecked = true
                }
                "2" -> {
                    c3.isChecked = true
                }
            }

            when (kudaKuda) {
                "0" -> {
                    d1.isChecked = true
                }
                "1" -> {
                    d2.isChecked = true
                }
                "2" -> {
                    d3.isChecked = true
                }
            }


            when (dinding) {
                "0" -> {
                    e1.isChecked = true
                }
                "1" -> {
                    e2.isChecked = true
                }
                "2" -> {
                    e3.isChecked = true
                }
            }

            when (lantai) {
                "0" -> {
                    ee1.isChecked = true
                }
                "1" -> {
                    ee2.isChecked = true
                }
                "2" -> {
                    ee3.isChecked = true
                }
            }


            when (penutupAtap) {
                "0" -> {
                    f1.isChecked = true
                }
                "1" -> {
                    f2.isChecked = true
                }
                "2" -> {
                    f3.isChecked = true
                }
            }

            when (statusPenguasaanLahan) {
               "0" -> {
                    g1.isChecked = true
                }
                "1" -> {
                    g2.isChecked = true
                }
                "2" -> {
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
                showProgressBar()
                ImagePicker.with(this@HistoryDetailActivity)
                    .cameraOnly()
                    .compress(1024)
                    .start(REQUEST_KTP_GALLERY)
            }

            fotoTampakSampingHint.setOnClickListener {
                showProgressBar()
                ImagePicker.with(this@HistoryDetailActivity)
                    .cameraOnly()
                    .compress(1024)
                    .start(REQUEST_SAMPING_GALLERY)
            }

            fotoDalamRumahHint.setOnClickListener {
                showProgressBar()
                ImagePicker.with(this@HistoryDetailActivity)
                    .cameraOnly()
                    .compress(1024)
                    .start(REQUEST_DALAM_RUMAH_GALLERY)
            }

            save.setOnClickListener {
                updateData()
            }

            delete.setOnClickListener {
                if(option == "local")
                {
                    deleteConformation()
                }
                else {
                    deleteFromFirebase()
                }
            }

            ktp.setOnClickListener {
                val intent = Intent(this@HistoryDetailActivity, PhotoActivity::class.java)
                intent.putExtra(PhotoActivity.option, "KTP")
                intent.putExtra(PhotoActivity.name, model?.nama1)
                intent.putExtra(PhotoActivity.image, model?.ktp1)
                startActivity(intent)
            }

            fotoTampakSamping.setOnClickListener {
                val intent = Intent(this@HistoryDetailActivity, PhotoActivity::class.java)
                intent.putExtra(PhotoActivity.option, "Tampak Samping")
                intent.putExtra(PhotoActivity.name, model?.nama1)
                intent.putExtra(PhotoActivity.image, model?.samping1)
                startActivity(intent)
            }

            fotoDalamRumah.setOnClickListener {
                val intent = Intent(this@HistoryDetailActivity, PhotoActivity::class.java)
                intent.putExtra(PhotoActivity.option, "Dalam Rumah")
                intent.putExtra(PhotoActivity.name, model?.nama1)
                intent.putExtra(PhotoActivity.image, model?.dalamRumah1)
                startActivity(intent)
            }
        }

    }

    private fun showProgressBar() {
        mProgressDialog?.setMessage("Mohon tunggu hingga proses selesai...")
        mProgressDialog?.setCanceledOnTouchOutside(false)
        mProgressDialog?.show()
    }

    private fun deleteFromFirebase() {
        AlertDialog.Builder(this)
            .setTitle("Konfirmasi Menghapus Survey")
            .setMessage("Apakah anda yakin ingin menghapus survey ini ?")
            .setIcon(R.drawable.ic_baseline_warning_24)
            .setPositiveButton("YA") { dialogInterface, _ ->
                dialogInterface.dismiss()

                Log.e("dsada", model?.serverUid1.toString())

                FirebaseFirestore
                    .getInstance()
                    .collection("survey")
                    .document(model?.serverUid1.toString())
                    .delete()
                    .addOnCompleteListener {
                        if(it.isSuccessful) {
                            binding.delete.visibility = View.GONE
                            Toast.makeText(this, "Berhasil menghapus survey", Toast.LENGTH_SHORT).show()
                        }
                    }
            }
            .setNegativeButton("TIDAK", null)
            .show()
    }

    private fun deleteConformation() {
        AlertDialog.Builder(this)
            .setTitle("Konfirmasi Menghapus Survey")
            .setMessage("Apakah anda yakin ingin menghapus survey ini ?")
            .setIcon(R.drawable.ic_baseline_warning_24)
            .setPositiveButton("YA") { dialogInterface, _ ->
                dialogInterface.dismiss()
                binding.delete.visibility = View.GONE
                val db = DBHelper(this@HistoryDetailActivity, null)
                db.delete(model?.id1)
                Toast.makeText(this, "Berhasil menghapus survey", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("TIDAK", null)
            .show()
    }

    private fun updateData() {
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

            if(nama.isEmpty()) {
                Toast.makeText(this@HistoryDetailActivity, "Nama tidak boleh kosong", Toast.LENGTH_SHORT).show()
            } else if (nik.isEmpty()) {
                Toast.makeText(this@HistoryDetailActivity, "NIK tidak boleh kosong", Toast.LENGTH_SHORT).show()
            }else if (nik.length != 16) {
                Toast.makeText(this@HistoryDetailActivity, "NIK harus 16 digit", Toast.LENGTH_SHORT).show()
            }
            else if (noKK.isEmpty()) {
                Toast.makeText(this@HistoryDetailActivity, "NO KK tidak boleh kosong", Toast.LENGTH_SHORT).show()
            } else if (noKK.length != 16) {
                Toast.makeText(this@HistoryDetailActivity, "NO KK harus 16 digit", Toast.LENGTH_SHORT).show()
            }

            else if (alamat.isEmpty()) {
                Toast.makeText(this@HistoryDetailActivity, "ALAMAT tidak boleh kosong", Toast.LENGTH_SHORT).show()
            }else if (desa.isEmpty()) {
                Toast.makeText(this@HistoryDetailActivity, "DESA tidak boleh kosong", Toast.LENGTH_SHORT).show()
            }else if (kecamatan.isEmpty()) {
                Toast.makeText(this@HistoryDetailActivity, "KECAMATAN tidak boleh kosong", Toast.LENGTH_SHORT).show()
            }else if (jumlahKK.isEmpty()) {
                Toast.makeText(this@HistoryDetailActivity, "Jumlah KK dalam 1 rumah tidak boleh kosong", Toast.LENGTH_SHORT).show()
            }else if (jumlahPenghuni.isEmpty()) {
                Toast.makeText(this@HistoryDetailActivity, "Jumlah penghuni rumah tidak boleh kosong", Toast.LENGTH_SHORT).show()
            }else if (penghasilan.isEmpty()) {
                Toast.makeText(this@HistoryDetailActivity, "Penghasilan per KK tidak boleh kosong", Toast.LENGTH_SHORT).show()
            }else if (luasRumah.isEmpty()) {
                Toast.makeText(this@HistoryDetailActivity, "Luas Rumah tidak boleh kosong", Toast.LENGTH_SHORT).show()
            }else if (koordinat.isEmpty()) {
                Toast.makeText(this@HistoryDetailActivity, "koordinat tidak boleh kosong", Toast.LENGTH_SHORT).show()
            }else if (ktpp == "") {
                Toast.makeText(this@HistoryDetailActivity, "KTP tidak boleh kosong", Toast.LENGTH_SHORT).show()
            }else if (samping == "") {
                Toast.makeText(this@HistoryDetailActivity, "Foto tampak samping tidak boleh kosong", Toast.LENGTH_SHORT).show()
            }else if (dalamRumah == "") {
                Toast.makeText(this@HistoryDetailActivity, "Foto dalam rumah tidak boleh kosong", Toast.LENGTH_SHORT).show()
            }else if (fondasi == "") {
                Toast.makeText(this@HistoryDetailActivity, "Fondasi tidak boleh kosong", Toast.LENGTH_SHORT).show()
            }else if (sloof == "") {
                Toast.makeText(this@HistoryDetailActivity, "Sloof tidak boleh kosong", Toast.LENGTH_SHORT).show()
            }else if (kolom == "") {
                Toast.makeText(this@HistoryDetailActivity, "Kolom tidak boleh kosong", Toast.LENGTH_SHORT).show()
            }else if (ringBalok == "") {
                Toast.makeText(this@HistoryDetailActivity, "Ring Balok tidak boleh kosong", Toast.LENGTH_SHORT).show()
            }else if (kudaKuda == "") {
                Toast.makeText(this@HistoryDetailActivity, "Kuda - Kuda tidak boleh kosong", Toast.LENGTH_SHORT).show()
            }else if (dinding == "") {
                Toast.makeText(this@HistoryDetailActivity, "Dinding tidak boleh kosong", Toast.LENGTH_SHORT).show()
            }else if (lantai == "") {
                Toast.makeText(this@HistoryDetailActivity, "Lantai tidak boleh kosong", Toast.LENGTH_SHORT).show()
            }else if (penutupAtap == "") {
                Toast.makeText(this@HistoryDetailActivity, "Penutup Atap tidak boleh kosong", Toast.LENGTH_SHORT).show()
            }else if (statusPenguasaanLahan == "") {
                Toast.makeText(this@HistoryDetailActivity, "Status Penguasaan Lahan tidak boleh kosong", Toast.LENGTH_SHORT).show()
            } else {

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
                    model?.serverUid1!!,
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
        const val OPTION = "option"
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}