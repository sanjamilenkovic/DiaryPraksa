package com.example.diarypraksa.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.diarypraksa.Friend
import com.example.diarypraksa.R


class FriendAdapter(val listener: INotify) : RecyclerView.Adapter<FriendAdapter.FriendHolder>() {

    val listaPrijatelja = ArrayList<Friend>()

    inner open class FriendHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val ime = itemView.findViewById<TextView>(R.id.nameTextView)
        val prezime = itemView.findViewById<TextView>(R.id.lastNameTextView)
        val telefon = itemView.findViewById<TextView>(R.id.telephoneTextView)
        val email = itemView.findViewById<TextView>(R.id.emailTextView)
        val image = itemView.findViewById<ImageView>(R.id.imageFriend)

        fun setData(position: Int) {
            ime.setText(listaPrijatelja[position].name)
            prezime.setText(listaPrijatelja[position].lastName)
            telefon.setText(listaPrijatelja[position].number)
            email.setText(listaPrijatelja[position].email)
//          image.setImageURI(listaPrijatelja[position].image.toUri())
            Glide.with(image.context).load(listaPrijatelja[position].image.toUri()).into(image)

            itemView.setOnClickListener {
                listener.onFriendClicked(listaPrijatelja[position])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendAdapter.FriendHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.friends_rv_item, null)

        return FriendHolder(view)

    }

    override fun onBindViewHolder(holder: FriendHolder, position: Int) {

        holder.setData(position)

    }

    override fun getItemCount(): Int {
        return listaPrijatelja.size
    }

    fun updateListuPrijatelja(novaLista: List<Friend>) {
        listaPrijatelja.clear()
        listaPrijatelja.addAll(novaLista)
    }


    interface INotify {
        fun onFriendClicked(friend: Friend)
    }

}
