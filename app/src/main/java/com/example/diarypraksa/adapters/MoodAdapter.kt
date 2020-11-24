package com.example.diarypraksa.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.diarypraksa.R

class MoodAdapter(val listener : INotify) : RecyclerView.Adapter<MoodAdapter.MoodHolder>() {
    var selectedPosition = -1
    val listaSlicica = ArrayList<Int>().apply {
        add(R.drawable.sticker_1)
        add(R.drawable.sticker_2)
        add(R.drawable.sticker_3)
        add(R.drawable.sticker_4)
//        add(R.drawable.sticker_1)
//        add(R.drawable.sticker_2)
//        add(R.drawable.sticker_3)
//        add(R.drawable.sticker_4)
//        add(R.drawable.sticker_1)
//        add(R.drawable.sticker_2)
//        add(R.drawable.sticker_3)
//        add(R.drawable.sticker_4)
//        add(R.drawable.sticker_1)
//        add(R.drawable.sticker_2)
//        add(R.drawable.sticker_3)
//        add(R.drawable.sticker_4)
//        add(R.drawable.sticker_1)
//        add(R.drawable.sticker_2)
//        add(R.drawable.sticker_3)
//        add(R.drawable.sticker_4)
//        add(R.drawable.sticker_1)
//        add(R.drawable.sticker_2)
//        add(R.drawable.sticker_3)
//        add(R.drawable.sticker_4)
//        add(R.drawable.sticker_1)
//        add(R.drawable.sticker_2)
//        add(R.drawable.sticker_3)
//        add(R.drawable.sticker_4)
//        add(R.drawable.sticker_1)
//        add(R.drawable.sticker_2)
//        add(R.drawable.sticker_3)
//        add(R.drawable.sticker_4)
//        add(R.drawable.sticker_1)
//        add(R.drawable.sticker_2)
//        add(R.drawable.sticker_3)
//        add(R.drawable.sticker_4)

    }

    inner open class MoodHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val stickerImage = itemView.findViewById<ImageView>(R.id.sticker_1)

        init {
            stickerImage.setOnClickListener {
                val position = stickerImage.tag as Int
                notifyDataSetChanged()
                selectedPosition = position
                it.setBackgroundColor(Color.RED)
                listener.onMoodClick(selectedPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoodHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.sticker_rv_item, null)
        return MoodHolder(view)
    }

    override fun onBindViewHolder(holder: MoodHolder, position: Int) {
        if (position == selectedPosition)
            holder.stickerImage.setBackgroundColor(Color.RED)
        else
            holder.stickerImage.setBackgroundColor(Color.TRANSPARENT)
        holder.stickerImage.setImageResource(listaSlicica[position])
        holder.stickerImage.tag  = position
    }

    override fun getItemCount(): Int {
        return listaSlicica.size
    }

    interface INotify {
        fun onMoodClick(index : Int)
    }
}