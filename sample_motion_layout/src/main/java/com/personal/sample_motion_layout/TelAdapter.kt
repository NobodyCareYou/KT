package com.personal.sample_motion_layout

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder

class TelAdapter : RecyclerView.Adapter<TelAdapter.TelViewHolder>() {


    class TelViewHolder(itemView: ViewGroup) : ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TelViewHolder {

       return TelViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_tel, parent, false) as ViewGroup)
    }

    override fun getItemCount(): Int {
       return 20
    }

    override fun onBindViewHolder(holder: TelViewHolder, position: Int) {

    }

}