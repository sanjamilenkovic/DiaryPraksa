package adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.diarypraksa.*
import java.util.ArrayList

class QuestionAdapter(val listener: QuestionAdapter.QuestionINotify) : RecyclerView.Adapter<QuestionAdapter.QuestionHolder>() {
    lateinit var viewModel: AnswerViewModel
    var list = ArrayList<ChatItem>()




     override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionAdapter.QuestionHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.questions_layout, null)
        return QuestionHolder(view)
    }


    inner open class QuestionHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val question = itemView.findViewById<TextView>(R.id.questions)
        init {

        }
    }

    override fun onBindViewHolder(holder: QuestionAdapter.QuestionHolder, position: Int) {


        val question=list[position]
        var tekst=(list[0] as Question).textOfQuestion
        holder.question.setText(tekst)
        holder.question.tag=position










    }

     override fun getItemCount(): Int {
        return list.size
    }
    interface QuestionINotify {

    }


}