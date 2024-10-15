package com.personal.sample_motion_layout

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.personal.sample_motion_layout.databinding.ActivityEmuiTelBinding

class EMUITelActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val binding = ActivityEmuiTelBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.rvContacts.layoutManager = LinearLayoutManager(this)
        binding.rvContacts.adapter = TelAdapter()
        binding.rvContacts.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState != RecyclerView.SCROLL_STATE_IDLE && binding.root.currentState == binding.root.startState) {
                    binding.root.transitionToEnd()
                }
            }
        })
        binding.ivPhone.setOnClickListener {
            if (binding.root.currentState == binding.root.endState) {
                binding.root.transitionToStart()
            }
        }
    }


}