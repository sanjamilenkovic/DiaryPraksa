package com.example.diarypraksa.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.diarypraksa.Friend
import com.example.diarypraksa.R


class FriendAdapter(val listener: INotify) : RecyclerView.Adapter<FriendAdapter.FriendHolder>() {

    val listaPrijatelja = ArrayList<Friend>()

    inner open class FriendHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val ime = itemView.findViewById<TextView>(R.id.nameTextView)
        val prezime = itemView.findViewById<TextView>(R.id.lastNameTextView)
        val telefon = itemView.findViewById<TextView>(R.id.telephoneTextView)
        val email = itemView.findViewById<TextView>(R.id.emailTextView)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendAdapter.FriendHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.friends_rv_item, null)

        val layoutParams = ViewGroup.LayoutParams.MATCH_PARENT
        layoutParams

        return FriendHolder(view)


    }

    override fun onBindViewHolder(holder: FriendHolder, position: Int) {

        holder.ime.setText(listaPrijatelja[position].name)
        holder.prezime.setText(listaPrijatelja[position].lastName)
        holder.telefon.setText(listaPrijatelja[position].number)
        holder.email.setText(listaPrijatelja[position].email)

        holder.itemView.setOnClickListener {
           listener.onFriendClicked(listaPrijatelja[position])
        }

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
