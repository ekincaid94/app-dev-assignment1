package ie.wit.assignment1.hikes.activities

import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
//import android.view.LayoutInflater
import android.view.Menu
//import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
//import androidx.recyclerview.widget.RecyclerView
import ie.wit.assignment1.R
import ie.wit.assignment1.adapters.hikeAdapter
import ie.wit.assignment1.adapters.HikeListener
import ie.wit.assignment1.main.MainApp
import ie.wit.assignment1.databinding.ActivityHikeListBinding
//import ie.wit.assignment1.databinding.CardHikeBinding
import ie.wit.assignment1.hikes.models.HikeModel

class HikeListActivity : AppCompatActivity(), HikeListener { {

    lateinit var app: MainApp
    private latinit var binding: ActivityHikeListBinding
    private lateinit var refreshIntentLauncher : ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHikeListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.title = title
        setSupportActionBar(binding.toolbar)

        app = application as MainApp

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        loadHikes()

        registerRefreshCallback()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }
}

override fun onOptionsItemSelected(item: MenuItem): Boolean {
    when (item.itemId) {
        R.id.item_add -> {
            val launcherIntent = Intent(this, HikeActivity::class.java)
            refreshIntentLauncher.launch(launcherIntent)
        }
        }
    }
    return super.onOptionsItemSelected(item)
}

    override fun onHikeClick(hike: HikeModel) {
        val launcherIntent = Intent(this, HikeActivity::class.java)
        launcherIntent.putExtra("hike_edit", hike)
        refreshIntentLauncher.launch(launcherIntent)
    }

    private fun registerRefreshCallback() {
        refreshIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { loadPlacemarks() }
    }

   private fun loadHikes() {
    showHikes(app.hikes.findAll())
     }

    fun showHikes (placemarks: List<HikeModel>) {
    binding.recyclerView.adapter = hikeAdapter(hikes, this)
    binding.recyclerView.adapter?.notifyDataSetChanged()
}


    }
}