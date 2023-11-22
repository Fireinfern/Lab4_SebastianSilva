package com.comp.lab4_sebastiansilva.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.Button
import android.widget.EditText
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import androidx.room.Room
import com.comp.lab4_sebastiansilva.AppDatabase
import com.comp.lab4_sebastiansilva.R
import com.comp.lab4_sebastiansilva.models.Patient
import com.comp.lab4_sebastiansilva.viewmodel.PatientActivityViewModel

class PatientActivity : AppCompatActivity(), OnClickListener {

    private val viewModel: PatientActivityViewModel by viewModels()

    private var nurseId: Int = 0

    private lateinit var patientsListView: RecyclerView

    private lateinit var patientFirstName: EditText

    private lateinit var patientLastName: EditText

    private lateinit var patientDepartment: EditText

    private lateinit var patientRoom: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_patient)

        val sharedPreferences = getSharedPreferences("user_preferences", MODE_PRIVATE)
        nurseId = sharedPreferences.getInt("id", 0)

        val db = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "medical-tests").build()
        viewModel.initDatabase(db, nurseId)

        patientsListView = findViewById<RecyclerView>(R.id.patients_list)
        patientsListView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        viewModel.patientsList.observe(this) {
            var customAdapter = PatientAdapter(it)
            patientsListView.adapter = customAdapter
        }
        viewModel.getPatientsByNurseId(nurseId)

        val addPatientButton = findViewById<Button>(R.id.add_patient)
        addPatientButton.setOnClickListener(this)

        patientFirstName = findViewById(R.id.patient_name_input)
        patientLastName = findViewById(R.id.patient_lastname_input)
        patientDepartment = findViewById(R.id.department_input)
        patientRoom = findViewById(R.id.room_inpu)

    }

    override fun onClick(p0: View?) {
        var firstName = patientFirstName.text.toString()
        var lastName = patientLastName.text.toString()
        var room = patientRoom.text.toString().toIntOrNull()
        var department = patientDepartment.text.toString()
        if (firstName.isEmpty() || lastName.isEmpty() || room == null || department.isEmpty()) {
            return
        }
        var newPatient = Patient(null, firstName, lastName, department, nurseId, room)
        patientFirstName.text.clear()
        patientLastName.text.clear()
        patientRoom.text.clear()
        patientDepartment.text.clear()
        viewModel.insertData(newPatient)
    }
}