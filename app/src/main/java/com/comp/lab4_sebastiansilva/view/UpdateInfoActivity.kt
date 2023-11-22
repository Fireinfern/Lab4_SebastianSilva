package com.comp.lab4_sebastiansilva.view

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.SpinnerAdapter
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.room.Room
import com.comp.lab4_sebastiansilva.AppDatabase
import com.comp.lab4_sebastiansilva.R
import com.comp.lab4_sebastiansilva.models.Patient
import com.comp.lab4_sebastiansilva.viewmodel.UpdatePatientActivityViewModel

class UpdateInfoActivity : AppCompatActivity() , OnItemSelectedListener, OnClickListener{

    private val viewModel: UpdatePatientActivityViewModel by viewModels()

    private val patientsIds: ArrayList<Int> = arrayListOf()

    private lateinit var arrayAdapter: ArrayAdapter<Int>

    private lateinit var patientsSpinner: Spinner

    private lateinit var patientFirstName: EditText

    private lateinit var patientLastName: EditText

    private lateinit var patientRoom: EditText

    private lateinit var patientDepartment: EditText

    private val allPatients: ArrayList<Patient> = arrayListOf()

    private var updatingPatient: Patient? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_info)

        val db = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "medical-tests").build()
        viewModel.initDatabase(db)

        patientFirstName = findViewById(R.id.update_firstname)
        patientLastName = findViewById(R.id.update_lastname)
        patientRoom = findViewById(R.id.update_room)
        patientDepartment = findViewById(R.id.update_department)

        patientsSpinner = findViewById(R.id.patients_spinner)
        arrayAdapter = ArrayAdapter<Int>(this, R.layout.id_item, R.id.id_text, patientsIds)
        arrayAdapter.setNotifyOnChange(true)
        patientsSpinner.adapter = arrayAdapter

        viewModel.patientsList.observe(this) {
            patientsIds.clear()
            patientsIds.addAll(it.map { patient ->
                patient.patientId!!
            })
            allPatients.clear()
            allPatients.addAll(it)
            arrayAdapter = ArrayAdapter<Int>(this, R.layout.id_item, R.id.id_text, patientsIds)
            patientsSpinner.adapter = arrayAdapter
        }

        patientsSpinner.onItemSelectedListener = this

        viewModel.getPatients()

        val updatePatientButton = findViewById<Button>(R.id.update_patient_btn)
        updatePatientButton.setOnClickListener(this)
    }

    override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, index: Int, p3: Long) {
        updatingPatient = allPatients[index]
        patientFirstName.setText(updatingPatient?.firstName)
        patientLastName.setText(updatingPatient?.lastName)
        patientRoom.setText(updatingPatient?.room.toString())
        patientDepartment.setText(updatingPatient?.department)
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("Not yet implemented")
    }

    override fun onClick(view: View?) {
        if (updatingPatient == null) {
            return
        }
        var firstName = patientFirstName.text.toString()
        var lastName = patientLastName.text.toString()
        var room = patientRoom.text.toString().toIntOrNull()
        var department = patientDepartment.text.toString()
        if (firstName.isEmpty() || lastName.isEmpty() || room == null || department.isEmpty()) {
            return
        }

        val inputMethodManager: InputMethodManager =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager;
        inputMethodManager.hideSoftInputFromWindow(currentFocus?.windowToken, 0)


        val newPatient = Patient(updatingPatient?.patientId, firstName, lastName, department, updatingPatient?.nurseId!!, room)
        viewModel.update(newPatient)
    }
}