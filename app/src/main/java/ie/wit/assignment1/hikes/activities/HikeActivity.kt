package ie.wit.assignment1.hikes.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import ie.wit.assignment1.databinding.HikeMainBinding
import ie.wit.assignment1.hikes.models.HikeModel
import ie.wit.assignment1.main.MainApp
import timber.log.Timber
import timber.log.Timber.i

class Hike() : AppCompatActivity() {

    private lateinit var binding: HikeMainBinding
    var hike = HikeModel()
    var app : MainApp? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = HikeMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        app = application as MainApp
        i("Hikelife started..")
        binding.btnAdd.setOnClickListener() {
            hike.title = binding.hikeTitle.text.toString()
            hike.description = binding.description.text.toString()
            if (hike.title.isNotEmpty()) {
                app.hikes.add(hike.copy()){
                i("add Button Pressed; ${hike}")
                for (i in app.hikes.indices) {
                    i("Hike[$i]:${this.app!!.hikes[i]}")
            }
                    setResult(RESULT_OK)
                    finish()
                }
                else {
                Snackbar.make(it, "Please Enter a title", Snackbar.LENGTH_LONG)
                    .show()
            }
        }
    }
}



