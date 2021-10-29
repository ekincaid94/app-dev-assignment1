package ie.wit.assignment1.hikes.hikes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import ie.wit.assignment1.databinding.HikeMainBinding
import timber.log.Timber
import timber.log.Timber.i

private lateinit var binding: HikeMainBinding

class Hike() : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = HikeMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Timber.plant(Timber.DebugTree())
        i("Hikelife started..")

        binding.btnAdd.setOnClickListener() {
            val hikeTitle = binding.hikeTitle.text.toString()
            if (hikeTitle.isNotEmpty()) {
                i("add Button Pressed; $hikeTitle")
            } else {
                Snackbar
                    .make(it, "Please Enter a title", Snackbar.LENGTH_LONG)
                    .show()
            }
        }
    }
}


