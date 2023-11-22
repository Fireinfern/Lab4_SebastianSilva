package com.comp.lab4_sebastiansilva.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import com.comp.lab4_sebastiansilva.R

class TestActivity : AppCompatActivity() {

    private lateinit var testNumber: EditText
    private lateinit var patientsIdSpinner: Spinner
    private lateinit var nurseIdSpinner: Spinner
    private lateinit var testBPL: EditText

    private lateinit var testBPH: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
        //testNumber=findViewById(R.id.bphTestInput)
        testBPH=findViewById(R.id.bplTestInput)
        testBPL=findViewById(R.id.bplTestInput)
        nurseIdSpinner=findViewById(R.id.NurseIDspinner)
        patientsIdSpinner=findViewById(R.id.patientIDspinner)

        val SaveButton = findViewById<Button>(R.id.save_test_button)
        SaveButton.setOnClickListener {
            var bpl = testBPL.text.toString().toIntOrNull()
            var bph = testBPH.text.toString().toIntOrNull()
            var nurseID=nurseIdSpinner.toString().toIntOrNull()
            var clientid=patientsIdSpinner.toString().toIntOrNull()
            val mainMenuActivity = Intent(this, MainActivity::class.java)
            startActivity(mainMenuActivity)
        }

        val backButton = findViewById<Button>(R.id.back_button)
        backButton.setOnClickListener {
            val mainMenuActivity = Intent(this, MainActivity::class.java)
            startActivity(mainMenuActivity)
        }
    }

}