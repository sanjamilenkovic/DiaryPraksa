package adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.liveData
import androidx.recyclerview.widget.RecyclerView
import com.example.diarypraksa.Feeling
import com.example.diarypraksa.R
import org.w3c.dom.Text
import java.time.Month
import java.util.*
import kotlin.collections.ArrayList

class CalendarAdapter(val listener:CalendarINotify) : RecyclerView.Adapter<CalendarAdapter.DayHolder>() {

    var currentDate: Date=Calendar.getInstance().time
    var proba: Date=Calendar.getInstance().time
    var c = Calendar.getInstance()
    val list = ArrayList<Int>()


    //    init {
//        currentDate = Calendar.getInstance().time
//
//        for (i in 0 until 42
//        )
//            list.add(i)
//
//    }


    //    val year = c.get(Calendar.YEAR)
//    val month = c.get(Calendar.MONTH)
//    val day = c.get(Calendar.DAY_OF_MONTH)
//    val dayInWeek=c.get(Calendar.DAY_OF_WEEK)
////2020.11.23 Monday
//    Calendar calendar = Calendar.getInstance();
//    calendar.clear();
//    calendar.setTimeInMillis(timestamp);
//    while (calendar.get(Calendar.DATE) > 1) {
//        calendar.add(Calendar.DATE, -1); // Substract 1 day until first day of month.
//    }
//    long firstDayOfMonthTimestamp = calendar.getTimeInMillis();
//
//
    var selectedPosition:Int=-1
    init {

        c.time //23.11.2020
        c.set(Calendar.DAY_OF_MONTH, 1) //1.11.2020
        val dayOfTheWeek = c.get(Calendar.DAY_OF_WEEK)
        for (i in 0 until dayOfTheWeek - 1) {
            list.add(-1)
        }
        val daysInMonth: Int = c.getActualMaximum(Calendar.DAY_OF_MONTH)
        for (i in 0 until daysInMonth)
            list.add(i + 1)

        for (i in list.size until 42)
            list.add(-1)
    }


    inner open class DayHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val day = itemView.findViewById<TextView>(R.id.dan)
        init {
            day.setOnClickListener {
                val position = day.tag as Int
                notifyDataSetChanged()
                selectedPosition = position
                if (!day.text.isBlank()) {
                    val c=Calendar.getInstance()
                    c.time=currentDate
                    c.get(Calendar.MONTH)
                    val dayoFMonth = day.text.toString().toInt()
                    listener.onDayClick(dayoFMonth,c.get(Calendar.MONTH))
                    //itemView.setBackgroundColor(Color.GREEN)

                }
            }
        }


    }
    fun onRefresh(proba:Calendar)
    {
        list.clear()
            proba.set(Calendar.DAY_OF_MONTH, 1) //1.11.2020

            val dayOfTheWeek = proba.get(Calendar.DAY_OF_WEEK)
            for (i in 0 until dayOfTheWeek - 1) {
                list.add(-1)
            }
            val daysInMonth: Int = proba.getActualMaximum(Calendar.DAY_OF_MONTH)
            for (i in 0 until daysInMonth)
                list.add(i + 1)
            for (i in list.size until 42)
                list.add(-1)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_day, null)
        return DayHolder(view)
    }

    override fun onBindViewHolder(holder: DayHolder, position: Int) {
        val day = list[position]
        if (day != -1)
            holder.day.setText(day.toString())
        else {
            holder.day.text = ""
        }
        if (position == selectedPosition)
            holder.day.setBackgroundColor(Color.BLUE)
        else
            holder.day.setBackgroundColor(Color.TRANSPARENT)

        holder.day.tag = position



    }

    override fun getItemCount(): Int {
        return 42
    }
    interface CalendarINotify {
        fun onDayClick(index : Int, mesec:Int)
    }


}

