package appbox.room.vasili.simpletodolist

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import appbox.room.vasili.simpletodolist.databinding.ActivityMainBinding
import appbox.room.vasili.simpletodolist.ui.addEditTask.AddEditTask
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView
        navView.menu.getItem(2).isEnabled=false
        navView.background=null

        val navController = findNavController(R.id.nav_host_fragment_activity_main)

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_calendar, R.id.navigation_folders, R.id.navigation_settings
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        val intent:Intent = Intent( this, AddEditTask::class.java)
        binding.flbtnGotoAddEditTask.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                intent.putExtra("Request", "add")
                startActivity(intent)
            }
        })
    }
}