package com.example.diarypraksa

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.diarypraksa.MyApplication.Companion.currentApp
import com.example.diarypraksa.adapters.FriendAdapter
import com.example.diarypraksa.adapters.MoodAdapter
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER


/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment(R.layout.fragment_home) {

    lateinit var viewModel: HomeViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel =
            ViewModelProviders.of(this, HomeViewModel.HomeViewModelFactory((currentApp.repository)))
                .get(HomeViewModel::class.java)


        val recyclerViewSticker: RecyclerView = view.findViewById(R.id.sticker_rv)
        val recyclerViewFriend : RecyclerView = view.findViewById(R.id.list_of_friends_rv)


        val listener = object : MoodAdapter.INotify {

            override fun onMoodClick(index: Int) {

                val today: Date = Calendar.getInstance().time

                val sticker = (index) % 4

                val f = Feeling(today, sticker, "opis1")
                viewModel.insert(f)

            }
        }

        recyclerViewSticker.layoutManager =
            LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)
        recyclerViewSticker.adapter = MoodAdapter(listener)


        recyclerViewFriend.layoutManager =
            LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)
        recyclerViewFriend.adapter = FriendAdapter()

        val editFeel : TextView = view.findViewById(R.id.i_feel_et)

        editFeel.setOnClickListener{
//
//            val d = activity?.let { it1 -> DialogFeeling(it1) }
//            if (d != null) {
//                d.show()
//            }

            activity?.let {
                val d = DialogFeeling(it)
                d.show()
            }
        }


    }


}