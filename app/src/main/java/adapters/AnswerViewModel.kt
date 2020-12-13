package adapters

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.diarypraksa.*
import com.example.diarypraksa.MyApplication.Companion.currentApp
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.IOException
import java.nio.charset.StandardCharsets

class AnswerViewModel: ViewModel() {

    private val _answer = MutableLiveData<List<Answer>>()
    public val answer: LiveData<List<Answer>>
        get() {
            return _answer
        }
    private val _question = MutableLiveData<Question>()
    public val question: LiveData<Question>
        get() {
            return _question
        }


    public class AnswerViewModelFactory : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(AnswerViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return AnswerViewModel() as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }

    }

    fun jsonToObject(){
        var listOfQuestions = ArrayList<Question>()
        var gson = Gson()



        val listType = object : TypeToken<ArrayList<Question>>() {}.type
        listOfQuestions =
            Gson().fromJson<ArrayList<Question>>(
                loadJSONFromAsset(
                    currentApp,
                    "proba.json"
                ), listType
            )
        _question.postValue(listOfQuestions[0])

    }




    fun loadJSONFromAsset(context: Context, path: String, language: String? = null): String? {
        val json: String
        try {
            val `is` = context.assets.open(path)
            val size = `is`.available()
            val buffer = ByteArray(size)
            `is`.read(buffer)
            `is`.close()
            json = String(buffer, StandardCharsets.UTF_8)

        } catch (ex: IOException) {
            ex.printStackTrace()
            return null
        }

        return json
    }
}