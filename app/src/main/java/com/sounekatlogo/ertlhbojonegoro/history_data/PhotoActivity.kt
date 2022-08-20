package com.sounekatlogo.ertlhbojonegoro.history_data

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.sounekatlogo.ertlhbojonegoro.R
import com.sounekatlogo.ertlhbojonegoro.databinding.ActivityPhotoBinding

class PhotoActivity : AppCompatActivity() {

    private var binding: ActivityPhotoBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPhotoBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        val option = intent.getStringExtra(option)
        val name = intent.getStringExtra(name)
        val image = intent.getStringExtra(image)
        binding?.textView8?.text = "$option ($name)"
        Glide.with(this)
            .load(image)
            .error(R.drawable.ic_baseline_insert_photo_24)
            .into(binding!!.image)

        binding?.backButton?.setOnClickListener {
            onBackPressed()
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    companion object {
        const val option = "option"
        const val name = "name"
        const val image = "image"
    }
}