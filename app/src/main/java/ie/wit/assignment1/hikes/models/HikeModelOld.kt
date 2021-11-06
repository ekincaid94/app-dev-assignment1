package ie.wit.assignment1.hikes.models

data class HikeModel(var title: String = "",
                     var description: String = "")

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import ie.wit.assignment1.databinding.HikeMainBinding
import ie.wit.assignment1.hikes.models.HikeModel
import timber.log.Timber
import timber.log.Timber.i

class Hikes : AppCompatActivity() {

    private lateinit var binding: HikeMainBinding
    var hikes = HikeModel()
    val hikes = ArrayList<HikeModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = HikeMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Timber.plant(Timber.DebugTree())

        i("HikeLife started...")

        binding.btnAdd.setOnClickListener() {
            hikes.title = binding.hikeTitle.text.toString()
            if (hikes.title.isNotEmpty()) {
                i("add Button Pressed: $hikes.title")
                for (i in hikes.indeces)
                {i("Hike[$i]:${this.hikes[i]}")}
            }
            else {
                Snackbar.make(it,"Please Enter a title", Snackbar.LENGTH_LONG)
                    .show()
            }
        }
    }
}


