package com.example.diarypraksa

import adapters.AnswerAdapter
import adapters.AnswerViewModel
import adapters.CalendarAdapter
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ChatBotFragment:Fragment(),AnswerAdapter.AnswerINotify {

    lateinit var viewModel: AnswerViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel =
            ViewModelProviders.of(this,
                AnswerViewModel.AnswerViewModelFactory()
            ).get(AnswerViewModel::class.java)

        viewModel.answer.observe(
            viewLifecycleOwner, {
                val listOfAnswer:RecyclerView = view.findViewById<RecyclerView>(R.id.odgovori)

                //with(viewModel) {
                //            recyclerViewSticker.layoutManager =
                //                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                //            recyclerViewSticker.adapter = MoodAdapter(listenerMood)
                listOfAnswer.layoutManager =
                    LinearLayoutManager(context,  RecyclerView.VERTICAL, false)
                val adapter=AnswerAdapter(listener = this@ChatBotFragment)
                adapter.list.clear()
                adapter.list.addAll(it)
                listOfAnswer.adapter = adapter

            }
        )
        viewModel.jsonToObject()



    }

}