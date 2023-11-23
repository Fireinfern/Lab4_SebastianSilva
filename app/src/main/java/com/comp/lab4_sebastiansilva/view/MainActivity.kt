package com.comp.lab4_sebastiansilva.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.Button
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ui.AppBarConfiguration
import androidx.room.Room
import com.comp.lab4_sebastiansilva.databinding.ActivityMainBinding
import com.comp.lab4_sebastiansilva.AppDatabase
import com.comp.lab4_sebastiansilva.R
import com.comp.lab4_sebastiansilva.models.Nurse
import com.comp.lab4_sebastiansilva.viewmodel.MainActivityViewModel

class MainActivity : AppCompatActivity(){

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        val db = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "medical-tests").build()
        viewModel.initDatabase(db)

        val patientButton = findViewById<Button>(R.id.patient_btn)
        patientButton.setOnClickListener {
            val patientActivityIntent = Intent(this, PatientActivity::class.java)
            startActivity(patientActivityIntent)
        }

        val testButton = findViewById<Button>(R.id.test_btn)
        testButton.setOnClickListener {
            val intent = Intent(this, TestActivity::class.java)
            startActivity(intent)
        }

        val testInfoButton = findViewById<Button>(R.id.test_info_btn)
        testInfoButton.setOnClickListener {
            val intent = Intent(this, TestInfoActivity::class.java)
            startActivity(intent)
        }

        val updateInfoButton = findViewById<Button>(R.id.update_btn)
        updateInfoButton.setOnClickListener {
            val intent = Intent(this, UpdateInfoActivity::class.java)
            startActivity(intent)
        }
    }
}