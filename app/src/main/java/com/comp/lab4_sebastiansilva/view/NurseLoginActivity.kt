package com.comp.lab4_sebastiansilva.view

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.room.Room
import com.comp.lab4_sebastiansilva.AppDatabase
import com.comp.lab4_sebastiansilva.R
import com.comp.lab4_sebastiansilva.models.Nurse
import com.comp.lab4_sebastiansilva.viewmodel.MainActivityViewModel
import com.google.android.material.textfield.TextInputEditText

class NurseLoginActivity : AppCompatActivity() , OnClickListener{

    private val viewModel: MainActivityViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nurse_login)

        val db = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "medical-tests").build()
        viewModel.initDatabase(db)

        val nurse1 = Nurse(1, "Ginny", "Aransaenz", "Cardiology", "123456")
        val nurse2 = Nurse(2, "Renzo", "Pino", "Rheumatology", "123456")
        viewModel.insertData(nurse1)
        viewModel.insertData(nurse2)

        val nurseLoginButton = findViewById<Button>(R.id.btn_login)
        nurseLoginButton.setOnClickListener(this)

        viewModel.validatedNurse.observe(this) {
            val sharedPreferences = getSharedPreferences("user_preferences", MODE_PRIVATE)
            with(sharedPreferences.edit()) {
                putInt("id", it.nurseId)
            }.apply()
            val mainActivityIntent = Intent(this, MainActivity::class.java)
            startActivity(mainActivityIntent)
        }
    }

    override fun onClick(p0: View?) {
        val usernameText = findViewById<EditText>(R.id.nurse_id_input)
        val passwordText = findViewById<EditText>(R.id.password_input)
        val username = usernameText.text.toString().toInt()
        val password = passwordText.text.toString()
        viewModel.validate(username, password)
    }
}