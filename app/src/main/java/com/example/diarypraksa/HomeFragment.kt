package com.example.diarypraksa

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.diarypraksa.MyApplication.Companion.currentApp
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
        val recyclerView: RecyclerView = view.findViewById(R.id.sticker_rv)
        val listener = object : MoodAdapter.INotify {

            override fun onMoodClick(index: Int) {

                val today: Date = Calendar.getInstance().time

                val f = Feeling(1, today, index, "opis1")
                viewModel.insert(f)


            }
        }

        recyclerView.layoutManager =
            LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.adapter = MoodAdapter(listener)
    }


}