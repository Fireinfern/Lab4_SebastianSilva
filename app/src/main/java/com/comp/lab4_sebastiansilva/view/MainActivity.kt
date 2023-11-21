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

class MainActivity : AppCompatActivity(), OnClickListener{

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        val db = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "medical-tests").build()
        viewModel.initDatabase(db)

        val nurse1 = Nurse(1, "Ginny", "Aransaenz", "Cardiology", "123456")
        val nurse2 = Nurse(2, "Renzo", "Pino", "Rheumatology", "123456")
        viewModel.insertData(nurse1)
        viewModel.insertData(nurse2)

        val nurseLoginButton = findViewById<Button>(R.id.btn_nurse_login)
        nurseLoginButton.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        val loginActivityIntent = Intent(this, NurseLoginActivity::class.java)
        startActivity(loginActivityIntent)
    }
}