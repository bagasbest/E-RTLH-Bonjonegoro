package com.sounekatlogo.ertlhbojonegoro.history_data

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.sounekatlogo.ertlhbojonegoro.databinding.ActivityHistoryServerBinding

class HistoryServerActivity : AppCompatActivity() {

    private var _binding : ActivityHistoryServerBinding? = null
    private val binding get() = _binding!!
    private var adapter: HistoryAdapter? = null
    private var role = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityHistoryServerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        role = intent.getStringExtra(ROLE).toString()
        if(role == "user") {
            binding.textView8.text = "Riwayat Survey di Server"
        } else {
            binding.textView8.text = "Riwayat Seluruh Survey di Server"
        }

        binding.backButton.setOnClickListener {
            onBackPressed()
        }

        initRecyclerView()
        initViewModel()

    }

    private fun initRecyclerView() {
        binding.rvSurvery.layoutManager = LinearLayoutManager(this)
        adapter = HistoryAdapter()
        binding.rvSurvery.adapter = adapter
    }

    private fun initViewModel() {
        val viewModel = ViewModelProvider(this)[HistoryViewModel::class.java]

        binding.progressBar.visibility = View.VISIBLE
        when (role) {
            "user" -> {
                val prefs = getSharedPreferences("USER_DATA", Context.MODE_PRIVATE)
                val uid = prefs.getString("uid", "").toString()
                viewModel.setHistoryByUid(uid)
            }
            "admin" -> {
                viewModel.setHistory()
            }
        }
        viewModel.getHistory().observe(this) { historySurvey ->
            if (historySurvey.size > 0) {
                adapter?.setData(historySurvey)
                binding.noData.visibility = View.GONE
            } else {
                binding.noData.visibility = View.VISIBLE
            }
            binding.progressBar.visibility = View.GONE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        const val ROLE = "role"
    }
}