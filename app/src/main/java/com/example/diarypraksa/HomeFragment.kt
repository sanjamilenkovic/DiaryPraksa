package com.example.diarypraksa

import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
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
        val recyclerViewFriend: RecyclerView = view.findViewById(R.id.list_of_friends_rv)

        val today: Date = Calendar.getInstance().time

        val calendar : Calendar = Calendar.getInstance()
        calendar.time = today
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0);
        val startOfDay : Date = calendar.time


        val end : Calendar = Calendar.getInstance()
        end.time = today
        end.set(Calendar.HOUR_OF_DAY, 23)
        end.set(Calendar.MINUTE, 59)
        end.set(Calendar.SECOND, 59)
        end.set(Calendar.MILLISECOND, 999);
        val endOfDay : Date = end.time

        viewModel.getFeelingByDate(startOfDay, endOfDay)


        val listener = object : MoodAdapter.INotify {

            override fun onMoodClick(index: Int) {
                viewModel.deleteAll()
                val today: Date = Calendar.getInstance().time
                val sticker = (index) % 4
                val f = Feeling(today, sticker, "")
                viewModel.insert(f)

            }
        }

        recyclerViewSticker.layoutManager =
            LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)
        recyclerViewSticker.adapter = MoodAdapter(listener)


        recyclerViewFriend.layoutManager =
            LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)
        recyclerViewFriend.adapter = FriendAdapter()


        with(viewModel) {

            recyclerViewSticker.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            recyclerViewSticker.adapter = MoodAdapter(listener)


            recyclerViewFriend.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            recyclerViewFriend.adapter = FriendAdapter()


            feelingLD.observe(viewLifecycleOwner, {
                //prima dogadjaj da su se podaci koji se nalaze u feelingLD promenili, a to je Feeling koji vraca fja getFeelingByDate
    //            val editFeel: TextView = view.findViewById(R.id.i_feel_et)
    //            editFeel.setText(it.description)
                //u it se nalazi podatak
                //viewModel.update(it, )

                //Toast.makeText(context, it.sticker_id.toString(), Toast.LENGTH_SHORT).show()

                viewModel.update(it)
            })
        }

        val listenerFeeling = object : DialogFeeling.INotifyFeeling {

            override fun onMoodDescriptionAdded(opis: String) {
                Toast.makeText(context, "dodato", Toast.LENGTH_SHORT).show()

                viewModel.updateMyLiveData(opis)


                //val newF = Feeling(today, 15, opis)
                //viewModel.update(opis, 38)
                //Toast.makeText(context, startOfDay.toString(), Toast.LENGTH_LONG).show()
                //Toast.makeText(context, endOfDay.toString(), Toast.LENGTH_LONG).show()
//                public Date atEndOfDay(Date date) {
//                    Calendar calendar = Calendar.getInstance();
//                    calendar.setTime(date);
//                    calendar.set(Calendar.HOUR_OF_DAY, 23);
//                    calendar.set(Calendar.MINUTE, 59);
//                    calendar.set(Calendar.SECOND, 59);
//                    calendar.set(Calendar.MILLISECOND, 999);
//                    return calendar.getTime();
//                }

                // za konvertovanje milisekundi u dan
//                val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy")
//                val dateString = simpleDateFormat.format(today)
//                Toast.makeText(context, (String.format("Date: %s", dateString)), Toast.LENGTH_SHORT).show()
//
//                val startOfDay : LocalDateTime? = LocalDateTime.MIN
//                val endOfDay : LocalDateTime? = LocalDateTime.MAX
//
//                Toast.makeText(context, startOfDay.toString() + endOfDay.toString(), Toast.LENGTH_SHORT).show()


                //viewModel.getFeelingByDate(today, today)
                //Toast.makeText(context, today.toString(), Toast.LENGTH_SHORT).show()

            }
        }

        val editFeel: TextView = view.findViewById(R.id.i_feel_et)

        editFeel.setOnClickListener {

            activity?.let {
                val d = DialogFeeling(it, listenerFeeling)
                d.show()
            }
        }

    }

}