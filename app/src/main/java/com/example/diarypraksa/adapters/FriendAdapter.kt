package com.example.diarypraksa.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.diarypraksa.Friend
import com.example.diarypraksa.R
import kotlinx.android.synthetic.main.sticker_rv_item.view.*

class FriendAdapter : RecyclerView.Adapter<FriendAdapter.FriendHolder>() {

    val listaImena = ArrayList<String>().apply {
        add("Sanja")
        add("Sanja2")
        add("Sanja3")
    }

    inner open class FriendHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

        val ime = itemView.findViewById<TextView>(R.id.name_tv)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendAdapter.FriendHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.friends_rv_item, null)
        return FriendHolder(view)
    }

    override fun onBindViewHolder(holder: FriendHolder, position: Int) {
        holder.ime.setText(listaImena[position])

    }

    override fun getItemCount(): Int {
        return listaImena.size
    }


}
