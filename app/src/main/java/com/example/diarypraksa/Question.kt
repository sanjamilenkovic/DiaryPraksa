package com.example.diarypraksa

data class Question(
    var id : Int,
    var questionText : String,
    var possibleAnswers : ArrayList<Answer>
)
{

}