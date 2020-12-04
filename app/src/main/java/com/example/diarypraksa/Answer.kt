package com.example.diarypraksa

data class Answer(
    var id: Int,
    var answerText : String,
//    var previousQuestion : Question,
    var nextQuestion: Int
)
{
}