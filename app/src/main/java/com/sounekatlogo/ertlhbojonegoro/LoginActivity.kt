package com.sounekatlogo.ertlhbojonegoro

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
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
            register()
            login()
        }

    }

    private fun login() {
        binding.apply {
            val email = email.text.toString().trim()
            val password = password.text.toString().trim()

            progressBar.visibility = View.VISIBLE
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener {
                    progressBar.visibility = View.GONE
                    if (it.isSuccessful) {
                        autoLogin()
                    } else {
                        Toast.makeText(this@LoginActivity, "Gagal melakukan login, silahkan periksa kembali akun anda, dan internet anda!", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }

    private fun register() {
        binding.apply {
           val email = email.text.toString().trim()
           val password = password.text.toString().trim()

            progressBar.visibility = View.VISIBLE
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener {
                    if(it.isSuccessful) {

                        val uid = FirebaseAuth.getInstance().currentUser?.uid
                        val data = mapOf(
                            "uid" to uid,
                            "email" to email,
                            "password" to password,
                            "role" to "admin",
                        )

                        FirebaseFirestore
                            .getInstance()
                            .collection("users")
                            .document(uid!!)
                            .set(data)
                            .addOnCompleteListener { task ->
                                progressBar.visibility = View.GONE
                                if(task.isSuccessful) {
                                    autoLogin()
                                }
                            }

                    }
                }
        }
    }

    private fun autoLogin() {
        if(FirebaseAuth.getInstance().currentUser != null) {
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}