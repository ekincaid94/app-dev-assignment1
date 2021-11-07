package ie.wit.assignment1.hikes.activities

import android.content.Intent
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import ie.wit.assignment1.databinding.ActivityHikeBinding
import ie.wit.assignment1.hikes.models.HikeModel
import ie.wit.assignment1.main.MainApp
import ie.wit.assignment1.R
//import timber.log.Timber
import timber.log.Timber.i

class HikeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHikeBinding

    //var location = Location(52.245696, -7.139102, 15f)
    private lateinit var imageIntentLauncher: ActivityResultLauncher
    private lateinit var mapIntentLauncher: ActivityResultLauncher<Intent>
    var hike = HikeModel()
    lateinit var app: MainApp


    private fun registerImagePickerCallback() {
        imageIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { result ->
                when (result.resultCode) {
                    RESULT_OK -> {
                        if (result.data != null) {
                            i("Got Result ${result.data!!.data}")
                            hike.image = result.data!!.data!!
                            Picasso.get()
                                .load(hike.image)
                                .into(binding.placemarkImage)
                        } // end of if
                    }
                    RESULT_CANCELED -> {
                    }
                    else -> {
                    }
                }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var edit = false
        binding = ActivityHikeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbarAdd.title = title
        setSupportActionBar(binding.toolbarAdd)
        app = application as MainApp

        if (intent.hasExtra("hike_edit")) {
            hike = intent.extras?.getParcelable("hike_edit")!!
            binding.hikeTitle.setText(hike.title)
            binding.description.setText(hike.description)
        }

        if (intent.hasExtra("hike_edit")) {
            edit = true
            hike = intent.extras?.getParcelable("hike_edit")!!
            binding.hikeTitle.setText(hike.title)
            binding.hikeLocation.setOnClickListener {
                i("Set Location Pressed")
            }
            binding.description.setText(hike.description)
            binding.placemarkLocation.setOnClickListener {
                val launcherIntent = Intent(this, HikesActivity::class.java)
                mapIntentLauncher.launch(launcherIntent)
            }
            binding.btnAdd.setText(R.string.save_hike)
            Picasso.get()
                .load(hike.image)
                .into(binding.hikeImage)
            if (hike.image != Uri.EMPTY) {
                binding.chooseImage.setText(R.string.change_hike_image)
            }
            if (result.data != null) {
                i("Got Result ${result.data!!.data}")
                hike.image = result.data!!.data!!
                Picasso.get()
                    .load(hike.image)
                    .into(binding.hikeImage)
                binding.chooseImage.setText(R.string.change_hike_image)
            }

            binding.btnAdd.setOnClickListener() {
                hike.title = binding.hikeTitle.text.toString()
                hike.description = binding.description.text.toString()
                if (hike.title.isEmpty()) {
                    Snackbar.make(it, R.string.enter_hike_title, Snackbar.LENGTH_LONG)
                        .show()
                } else {
                    if (edit) {
                        app.hikes.update(hike.copy())
                    } else {
                        app.hikes.create(hike.copy())
                    }
                }
                i("add Button Pressed: $hike")
                setResult(RESULT_OK)
                finish()
            }
            binding.chooseImage.setOnClickListener {
                i("Select image")
            }
        }

        private fun registerMapCallback() {
            mapIntentLauncher =
                registerForActivityResult(ActivityResultContracts.StartActivityForResult())
                { i("Map Loaded") }
        }

        override fun onCreateOptionsMenu(menu: Menu): Boolean {
            menuInflater.inflate(R.menu.menu_hike, menu)
            return super.onCreateOptionsMenu(menu)
        }

        override fun onOptionsItemSelected(item: MenuItem): Boolean {
            when (item.itemId) {
                R.id.item_cancel -> {
                    finish()
                }
            }
            return super.onOptionsItemSelected(item)
        }
    }



    binding.hikeLocation.setOnClickListener
    {
        val location = Location(52.245696, -7.139102, 15f)
        if (hike.zoom != 0f) {
            location.lat = hike.lat
            location.lng = hike.lng
            location.zoom = hike.zoom
        }
        val launcherIntent = Intent(this, MapActivity::class.java)
            .putExtra("location", location)
        mapIntentLauncher.launch(launcherIntent)
    }

    private fun registerMapCallback() {
        mapIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { result ->
                when (result.resultCode) {
                    RESULT_OK -> {
                        if (result.data != null) {
                            i("Got Location ${result.data.toString()}")
                            val location =
                                result.data!!.extras?.getParcelable<Location>("location")!!
                            i("Location == $location")
                            hike.lat = location.lat
                            hike.lng = location.lng
                            hike.zoom = location.zoom
                        } // end of if
                    }
                    RESULT_CANCELED -> {
                    }
                    else -> {
                    }
                }
            }
    }
}