package com.example.diarypraksa

import adapters.CalendarAdapter
import android.bluetooth.le.AdvertiseData
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.get
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.asLiveData
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_calendar.view.*
import kotlinx.android.synthetic.main.friends_rv_item.view.*
import kotlinx.android.synthetic.main.sticker_rv_item.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.nio.file.Files.size
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.Month
import java.util.*
import java.util.Calendar.*
import kotlin.collections.ArrayList


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CalendarFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CalendarFragment : Fragment(), CalendarAdapter.CalendarINotify {

    val format = SimpleDateFormat("MMMM yyyy ")
    lateinit var textView3: TextView
    lateinit var textOpis: TextView
    lateinit var viewModel: CalendarViewModel
    lateinit var stickerView: ImageView
    lateinit var textPrijatelj: TextView







    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel =
            ViewModelProviders.of(
                this,
                CalendarViewModelFactory((MyApplication.currentApp.repository))
            )
                .get(CalendarViewModel::class.java)
        viewModel.readFeeling.observe(viewLifecycleOwner, {
            if (it != null) {
                textOpis.text = it.description
                if (it.sticker_id == 1) {
                    stickerView.setImageResource(R.drawable.sticker_1)
                } else if (it.sticker_id == 2) {
                    stickerView.setImageResource(R.drawable.sticker_2)
                } else if (it.sticker_id == 3) {
                    stickerView.setImageResource(R.drawable.sticker_3)
                } else if (it.sticker_id == 4) {
                    stickerView.setImageResource(R.drawable.sticker_4)
                } else {
                    stickerView.setImageResource(0)

                }
            } else {
                textOpis.text = " "
                stickerView.setImageResource(0)

            }

        })

        viewModel.readFriends.observe(viewLifecycleOwner, {

            var text:String=""
            var coma:String=""


            var list=it
            if (list.isEmpty()) {

                textPrijatelj.text = ""

            } else {
                for (i in 0 until list.size) {
                    text = text + coma + list[i].name
                    coma=", "
                }
                textPrijatelj.text = text
            }

            list= emptyList()




        })


        //viewModel.insertTest()
        //viewModel.readFeelingTest()
        //viewModel.readFeelingByDate()
        // viewModel.insertTest2()


        val c = Calendar.getInstance()
        val calendar = view.findViewById<RecyclerView>(R.id.calendar_list)
        calendar.layoutManager = GridLayoutManager(context, 7, RecyclerView.VERTICAL, false)
        calendar.adapter = CalendarAdapter(listener = this)




        textView3 = view.findViewById<TextView>(R.id.textView3)
        textOpis = view.findViewById<TextView>(R.id.description)
        stickerView = view.findViewById<ImageView>(R.id.slicica)
        textPrijatelj = view.findViewById<TextView>(R.id.textView17)
        textView3.text = format.format(c.time)

        val prevMonthImage = view.findViewById<ImageView>(R.id.prevMonthImage)
        prevMonthImage.setOnClickListener {
            val cal = Calendar.getInstance()
            cal.time = (calendar.adapter as CalendarAdapter).currentDate
            cal.add(Calendar.MONTH, -1)
            val dateMinusOneMonth: Date = cal.time
            textView3.text = format.format(cal.time)
            (calendar.adapter as CalendarAdapter).currentDate = dateMinusOneMonth
            (calendar.adapter as CalendarAdapter).onRefresh(cal)
            (calendar.adapter as CalendarAdapter).notifyDataSetChanged()

        }
        val nextMonthImage = view.findViewById<ImageView>(R.id.nextMonth)
        nextMonthImage.setOnClickListener {
            val cal = Calendar.getInstance()
            cal.time = (calendar.adapter as CalendarAdapter).currentDate
            cal.add(Calendar.MONTH, 1)
            val datePlusOneMonth: Date = cal.time
            textView3.text = format.format(cal.time)
            (calendar.adapter as CalendarAdapter).currentDate = datePlusOneMonth
            (calendar.adapter as CalendarAdapter).onRefresh(cal)
            (calendar.adapter as CalendarAdapter).notifyDataSetChanged()


        }


    }

    override fun onDayClick(index: Int, mesec: Int) {
        val cal = Calendar.getInstance()
        cal.set(DAY_OF_MONTH, index)
        cal.set(MONTH, mesec)
        val fDate: Date = cal.time
        viewModel.readFeelingByDate(fDate)
        viewModel.readFriendsByDate(fDate)

    }
}

