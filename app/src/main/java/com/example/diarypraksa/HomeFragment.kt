package com.example.diarypraksa

import android.app.Activity
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.diarypraksa.MyApplication.Companion.currentApp
import com.example.diarypraksa.adapters.FriendAdapter
import com.example.diarypraksa.adapters.MoodAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER


/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment(R.layout.fragment_home) {

    private var bestFriend: Int = 3
    private val BEST_FRIEND_KEY = "bestFriend"

    fun returnBestFriend() : Int {
        return bestFriend
    }

    private var mPreferences: SharedPreferences? = null
    private val sharedPrefFile = "com.example.diarypraksa"

    lateinit var viewModel: HomeViewModel
    lateinit var dialog: DialogFriend

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel =
            ViewModelProviders.of(this, HomeViewModel.HomeViewModelFactory((currentApp.repository)))
                .get(HomeViewModel::class.java)

        mPreferences = activity?.getSharedPreferences(sharedPrefFile, MODE_PRIVATE)

        bestFriend = mPreferences!!.getInt(BEST_FRIEND_KEY, 0)
        //Toast.makeText(context, bestFriend.toString(), Toast.LENGTH_LONG).show()

        val recyclerViewSticker: RecyclerView = view.findViewById(R.id.sticker_rv)
        val recyclerViewFriend: RecyclerView = view.findViewById(R.id.list_of_friends_rv)

        val today: Date = Calendar.getInstance().time

        val calendar: Calendar = Calendar.getInstance()
        calendar.time = today
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        val startOfDay: Date = calendar.time

        val end: Calendar = Calendar.getInstance()
        end.time = today
        end.set(Calendar.HOUR_OF_DAY, 23)
        end.set(Calendar.MINUTE, 59)
        end.set(Calendar.SECOND, 59)
        end.set(Calendar.MILLISECOND, 999)
        val endOfDay: Date = end.time

        viewModel.getFeelingByDate(startOfDay, endOfDay)

        val listenerMood = object : MoodAdapter.INotify {

            override fun onMoodClick(index: Int) {
                viewModel.updateMyLiveData(index)
            }
        }

        val listenerFeeling = object : DialogFeeling.INotifyFeeling {

            override fun onMoodDescriptionAdded(opis: String) {
                viewModel.updateMyLiveData(opis)
            }
        }

        val listenerFriend = object : DialogFriend.INotifyFriend {

            override fun onNewFriendAddedOrUpdated(newFriend: Friend) {
                viewModel.update(newFriend)
            }

            override fun onBestFriendAddedOrUpdated(newFriend: Friend) {
                viewModel.updateBestFriend(newFriend)
            }

//            override fun onNewFriendAdded(newFriend: Friend) {
//                Toast.makeText(context, "Added new", Toast.LENGTH_SHORT).show()
//                //viewModel.insert(newFriend)
//                viewModel.update(newFriend)
//            }

//            override fun onNewBestFriendAdded(newFriend: Friend) {
//                viewModel.updateBestFriend(newFriend)
//               // viewModel.insertBestFriend(newFriend)
//            }

//            override fun onFriendUpdated(newFriend: Friend) {
////              Toast.makeText(context, "Edit starog", Toast.LENGTH_SHORT).show()
//                viewModel.update(newFriend)
//            }

//            override fun onBestFriendUpdated(newFriend: Friend) {
//                viewModel.updateBestFriend(newFriend)
//            }
        }

        val listenerFriendClicked = object : FriendAdapter.INotify {

            override fun onFriendClicked(friend: Friend) {
                activity?.let {
                    val editFiendDialog = DialogFriend(it, listenerFriend, this@HomeFragment)
                    dialog = editFiendDialog
                    Toast.makeText(context, "edit poziv", Toast.LENGTH_SHORT)
                    dialog.editFriend(friend)
                }
            }
        }


        with(viewModel) {
            recyclerViewSticker.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            recyclerViewSticker.adapter = MoodAdapter(listenerMood)


            val friendAdapter = FriendAdapter(listenerFriendClicked)
            recyclerViewFriend.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            recyclerViewFriend.adapter = friendAdapter

            feelingLD.observe(viewLifecycleOwner, {
//                prima dogadjaj da su se podaci koji se nalaze u feelingLD promenili, a to je Feeling koji vraca fja getFeelingByDate
//                u it se nalazi podatak
                viewModel.update(it)
            })

            friendLD.observe(viewLifecycleOwner, {
//                Toast.makeText(context, "update prijatelja", Toast.LENGTH_SHORT).show()
                viewModel.update(it)
            })

            probaPrijatelji.observe(viewLifecycleOwner, {
                friendAdapter.updateListuPrijatelja(it)
                friendAdapter.notifyDataSetChanged()
            })

            bestFriendLD.observe(viewLifecycleOwner, {
                bestFriend = it
                saveSharedPref(bestFriend)
                Toast.makeText(context, bestFriend.toString(), Toast.LENGTH_LONG).show()
            })

        }

        val editFeel: TextView = view.findViewById(R.id.i_feel_et)
        editFeel.setOnClickListener {
            activity?.let {
                val d = DialogFeeling(it, listenerFeeling)
                d.show()
            }
        }

        val editFriendButton: FloatingActionButton = view.findViewById(R.id.edit_friend)
        editFriendButton.setOnClickListener {
            activity?.let {
                val newFriendDialog = DialogFriend(it, listenerFriend, this)
                dialog = newFriendDialog
                newFriendDialog.show()
            }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            if (data?.dataString != null) {
                val dataString = data.dataString //image path
                if (dialog.isShowing) {
                    dataString?.let { dialog.getDataString(it) }
                }

            }

        }
    }


    fun saveSharedPref(id: Int) {
        val preferencesEditor = mPreferences!!.edit()
        preferencesEditor.clear()
        preferencesEditor.apply()
        preferencesEditor.putInt(BEST_FRIEND_KEY, bestFriend)
        preferencesEditor.apply()
    }

    override fun onPause() {
        super.onPause()
        val preferencesEditor = mPreferences!!.edit()
        preferencesEditor.putInt(BEST_FRIEND_KEY, bestFriend)
        preferencesEditor.apply()
    }

}

