package com.sounekatlogo.ertlhbojonegoro

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.sounekatlogo.ertlhbojonegoro.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private var _binding : ActivityLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        autoLogin()

        binding.login.setOnClickListener {
           login()
        }

    }

    private fun login() {
        binding.apply {
            val email = email.text.toString().trim()
            val password = password.text.toString().trim()

            if(email.isEmpty()) {
                Toast.makeText(this@LoginActivity, "Email tidak boleh kosong", Toast.LENGTH_SHORT).show()
            } else if(password.isEmpty() || password.length < 6) {
                Toast.makeText(this@LoginActivity, "Kata sandi minimal 6 karakter", Toast.LENGTH_SHORT).show()
            } else {
                progressBar.visibility = View.VISIBLE
                FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            val uid = FirebaseAuth.getInstance().currentUser!!.uid
                            FirebaseFirestore
                                .getInstance()
                                .collection("users")
                                .document(uid)
                                .get()
                                .addOnSuccessListener { response ->
                                    val desa = "" + response.data!!["desa"]
                                    val kecamatan = "" + response.data!!["kecamatan"]

                                    val sharedPreference =  getSharedPreferences("USER_DATA",
                                        Context.MODE_PRIVATE)
                                    val editor = sharedPreference.edit()
                                    editor.putString("desa",desa)
                                    editor.putString("kecamatan", kecamatan)
                                    editor.putString("uid",uid)
                                    editor.apply()

                                    autoLogin()

                                }

                        } else {
                            binding.progressBar.visibility = View.GONE
                            Toast.makeText(this@LoginActivity, "Gagal melakukan login, silahkan periksa kembali akun anda, dan internet anda!", Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }
    }

    private fun autoLogin() {
        if(FirebaseAuth.getInstance().currentUser != null) {
            binding.progressBar.visibility = View.GONE
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}