package com.example.diarypraksa

import adapters.AnswerAdapter
import adapters.QuestionAdapter
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ChatBotFragment:Fragment(R.layout.chat_bot),AnswerAdapter.AnswerINotify,QuestionAdapter.QuestionINotify {

    lateinit var viewModel: AnswerViewModel
    //val questionAdapter=QuestionAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel =
            ViewModelProviders.of(this,
                AnswerViewModel.AnswerViewModelFactory()
            ).get(AnswerViewModel::class.java)

        viewModel.question.observe(
            viewLifecycleOwner, {
                val listOfAnswer:RecyclerView = view.findViewById<RecyclerView>(R.id.textOfQuestion)

                listOfAnswer.layoutManager =
                    LinearLayoutManager(context,  RecyclerView.VERTICAL, false)
                val adapter=AnswerAdapter(listener = this@ChatBotFragment)
                adapter.list.clear()
                adapter.list.addAll(it.listOfAnswers)
                listOfAnswer.adapter = adapter


                val listOfQuestion:RecyclerView=view.findViewById<RecyclerView>(R.id.chat)
                listOfQuestion.layoutManager =
                    LinearLayoutManager(context,  RecyclerView.VERTICAL, false)
                val adapter2=QuestionAdapter(listener= this@ChatBotFragment)
                adapter2.list.clear()
                adapter2.list.add(it)
                listOfQuestion.adapter=adapter2



            }
        )


        viewModel.jsonToObject()





    }

}