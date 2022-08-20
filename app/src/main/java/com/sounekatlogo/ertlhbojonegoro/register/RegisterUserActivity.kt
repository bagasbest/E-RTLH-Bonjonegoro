package com.sounekatlogo.ertlhbojonegoro.register

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.sounekatlogo.ertlhbojonegoro.R
import com.sounekatlogo.ertlhbojonegoro.databinding.ActivityRegisterUserBinding
import com.sounekatlogo.ertlhbojonegoro.utils.Common

class RegisterUserActivity : AppCompatActivity() {
    private var _binding: ActivityRegisterUserBinding? = null
    private val binding get() = _binding!!
    private var desa = ""
    private var kecamatans = ""
    private var desaList = ArrayList<DesaModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityRegisterUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getDesaFromAPI()
        showDropdownWilayahKecamatan()

        binding.backButton.setOnClickListener {
            onBackPressed()
        }

        binding.register.setOnClickListener {
            register()
        }

    }

    private fun showDropdownWilayahKecamatan() {
        Handler(Looper.getMainLooper()).postDelayed({
            val kecamatan = ArrayList<String>()
            desaList.forEach {
                kecamatan.add(it.kecamatan.toString())
            }

            val distinct = kecamatan.toSet().toList().reversed()

            val adapter = ArrayAdapter(
                this,
                android.R.layout.simple_expandable_list_item_1,
                distinct
            )
            binding.searchableSpinnerKecamatan.adapter = adapter
            binding.searchableSpinnerKecamatan.onItemSelectedListener =
                (object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        p0: AdapterView<*>?,
                        p1: View?,
                        p2: Int,
                        p3: Long
                    ) {
                        val customer = distinct[p2]
                        showDropdownWilayah(customer)
                    }

                    override fun onNothingSelected(p0: AdapterView<*>?) {
                    }
                })


        }, 1000)
    }

    private fun getDesaFromAPI() {
        Desa.jsonToList(this, desaList)
    }

    private fun showDropdownWilayah(kecamatan: String) {
        val desaName = ArrayList<String>()
        desaList.forEach {
            if (it.kecamatan == kecamatan) {
                desaName.add(it.desa.toString())
            }
        }

        desaName.reversed()


        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_expandable_list_item_1,
            desaName
        )
        binding.searchableSpinner.adapter = adapter
        binding.searchableSpinner.onItemSelectedListener =
            (object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    p0: AdapterView<*>?,
                    p1: View?,
                    p2: Int,
                    p3: Long
                ) {
                    desa = binding.searchableSpinner.selectedItem.toString()
                    kecamatans = binding.searchableSpinnerKecamatan.selectedItem.toString()
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                }
            })
    }

    private fun register() {
        binding.apply {
            val username = username.text.toString().trim()
            val email = email.text.toString().trim()
            val password = password.text.toString().trim()

            if (username.isEmpty()) {
                Toast.makeText(
                    this@RegisterUserActivity,
                    "Username tidak boleh kosong",
                    Toast.LENGTH_SHORT
                ).show()
            } else if (email.isEmpty()) {
                Toast.makeText(
                    this@RegisterUserActivity,
                    "Email tidak boleh kosong",
                    Toast.LENGTH_SHORT
                ).show()
            } else if (password.isEmpty() || password.length < 6) {
                Toast.makeText(
                    this@RegisterUserActivity,
                    "Password minimal 6 karakter",
                    Toast.LENGTH_SHORT
                ).show()
            } else if (desa == "") {
                Toast.makeText(
                    this@RegisterUserActivity,
                    "Desa/kelurahan harus diisi",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                progressBar.visibility = View.VISIBLE

                FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            saveUserData(email, username, password)
                        }
                    }

            }
        }
    }

    private fun saveUserData(email: String, username: String, password: String) {
        val uid = FirebaseAuth.getInstance().currentUser!!.uid
        val data = mapOf(
            "uid" to uid,
            "username" to username,
            "email" to email,
            "password" to password,
            "desa" to desa,
            "kecamatan" to kecamatans,
        )

        FirebaseFirestore
            .getInstance()
            .collection("users")
            .document(uid)
            .set(data)
            .addOnCompleteListener {
                if (it.isSuccessful) {

                    FirebaseAuth.getInstance().signOut()
                    FirebaseAuth.getInstance()
                        .signInWithEmailAndPassword(Common.email, Common.password)
                        .addOnCompleteListener { task ->
                            binding.progressBar.visibility = View.GONE
                            if (task.isSuccessful) {
                                showSuccessDialog()
                            } else {
                                showFailureDialog()
                            }
                        }

                }
            }
    }


    /// munculkan dialog ketika gagal registrasi
    private fun showFailureDialog() {
        AlertDialog.Builder(this)
            .setTitle("Gagal melakukan registrasi")
            .setMessage("Silahkan periksa kembali data yang anda daftarkan, email tidak boleh dipakai mendaftar lebih dari 1 akun, dan cek koneksi internet anda")
            .setIcon(R.drawable.ic_baseline_clear_24)
            .setPositiveButton("OKE") { dialogInterface, _ ->
                dialogInterface.dismiss()
            }
            .show()
    }

    /// munculkan dialog ketika sukses registrasi
    private fun showSuccessDialog() {
        AlertDialog.Builder(this)
            .setTitle("Berhasil melakukan registrasi")
            .setMessage("Silahkan berikan data registrasi kepada user yang bersangkutan!")
            .setIcon(R.drawable.ic_baseline_check_circle_outline_24)
            .setPositiveButton("OKE") { dialogInterface, _ ->
                dialogInterface.dismiss()
                binding.apply {
                    username.setText("")
                    email.setText("")
                    password.setText("")
                    desa = ""
                }
            }
            .show()
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}