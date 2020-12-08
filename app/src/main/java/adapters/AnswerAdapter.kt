package adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.diarypraksa.Answer
import com.example.diarypraksa.R
import java.util.*

 class AnswerAdapter(val listener: AnswerAdapter.AnswerINotify) : RecyclerView.Adapter<AnswerAdapter.AnswerHolder>()   {

    val list = ArrayList<Answer>()




    inner open class AnswerHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val answer = itemView.findViewById<TextView>(R.id.odgovori)
        init {

        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnswerAdapter.AnswerHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.answers_beyond, null)
        return AnswerHolder(view)
    }

     override fun onBindViewHolder(holder: AnswerAdapter.AnswerHolder, position: Int) {

        var answer = list[position]






    }

     override fun getItemCount(): Int {
         return list.size
     }
     interface AnswerINotify {

     }

 }