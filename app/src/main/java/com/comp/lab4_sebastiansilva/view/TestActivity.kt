package com.comp.lab4_sebastiansilva.view

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.activity.viewModels
import androidx.room.Room
import com.comp.lab4_sebastiansilva.R
import com.comp.lab4_sebastiansilva.AppDatabase
import com.comp.lab4_sebastiansilva.models.Nurse
import com.comp.lab4_sebastiansilva.models.Patient
import com.comp.lab4_sebastiansilva.models.Test
import com.comp.lab4_sebastiansilva.viewmodel.TestActivityViewModel

class TestActivity : AppCompatActivity(), OnItemSelectedListener {
    private val viewModel: TestActivityViewModel by viewModels()
    private lateinit var patientsIdSpinner: Spinner
    private lateinit var nurseIdSpinner: Spinner
    private lateinit var testBPL: EditText
    private lateinit var testBPH: EditText
    private lateinit var testTemperature: EditText
    private lateinit var arrayNuseAdapter: ArrayAdapter<Int>
    private val patientsIds: ArrayList<Int> = arrayListOf()
    private val nurseIds: ArrayList<Int> = arrayListOf()
    private lateinit var arrayPatientAdapter: ArrayAdapter<Int>
    private var nurseId: Int = 0
    private var patientId: Int = 0
    private val allNursesIds: ArrayList<Nurse> = arrayListOf()
    private val allPatientsIds: ArrayList<Patient> = arrayListOf()
    private var updatingPatient: Patient? = null
    private var updatingNurces: Nurse? = null

    //private var updatingPatient: Patient? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        val sharedPreferences = getSharedPreferences("user_preferences", MODE_PRIVATE)
        nurseId = sharedPreferences.getInt("id", 0)

        val db = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "medical-tests").build()
        viewModel.initDatabase(db, nurseId)
        //testNumber=findViewById(R.id.bphTestInput)
        testBPH=findViewById(R.id.bplTestInput)
        testBPL=findViewById(R.id.bplTestInput)
        testTemperature=findViewById(R.id.tempTestInput)

        nurseIdSpinner=findViewById(R.id.NurseIDspinner)
        patientsIdSpinner=findViewById(R.id.patientIDspinner)

        arrayNuseAdapter = ArrayAdapter<Int>(this, R.layout.id_item, R.id.id_text, nurseIds)
        arrayNuseAdapter.setNotifyOnChange(true)
        nurseIdSpinner.adapter = arrayNuseAdapter

        arrayPatientAdapter = ArrayAdapter<Int>(this, R.layout.id_item, R.id.id_text, patientsIds)
        arrayPatientAdapter.setNotifyOnChange(true)
        patientsIdSpinner.adapter = arrayPatientAdapter

        viewModel.nurseList.observe(this){
            nurseIds.clear()
            nurseIds.addAll(it.map { nurse ->
                nurse.nurseId!!
            })
            allNursesIds.clear()
            allNursesIds.addAll(it)
            arrayNuseAdapter = ArrayAdapter<Int>(this, R.layout.id_item, R.id.id_text, nurseIds)
            nurseIdSpinner.adapter = arrayNuseAdapter

        }
        viewModel.getTestByNurseId(nurseIdSpinner.id)
        nurseIdSpinner.onItemSelectedListener=this
        viewModel.patientsList.observe(this) {

            patientsIds.clear()
            patientsIds.addAll(it.map { patient ->
                patient.patientId!!
            })
            allPatientsIds.clear()
            allPatientsIds.addAll(it)
            arrayPatientAdapter = ArrayAdapter<Int>(this, R.layout.id_item, R.id.id_text, patientsIds)
            patientsIdSpinner.adapter = arrayPatientAdapter
        }
        patientsIdSpinner.onItemSelectedListener=this
        viewModel.getTestByPatientId(patientsIdSpinner.id)

        viewModel.getNurses()
        viewModel.getPatient()

        val saveButton = findViewById<Button>(R.id.save_test_button)
        saveButton.setOnClickListener {
            var bpl = testBPL.text.toString().toIntOrNull()
            var bph = testBPH.text.toString().toIntOrNull()
            var temperature=testTemperature.text.toString().toIntOrNull()
            var nurseID=nurseIdSpinner.selectedItem as? Int? ?: null
            var clientId = patientsIdSpinner.selectedItem as? Int ?: null
            if (bpl==null || bph==null || nurseID == null || clientId==null|| temperature==null) {
                return@setOnClickListener
            }
            var newTest = Test(null, clientId, nurseID, bpl.toFloat(), bph.toFloat(), temperature.toFloat())
            viewModel.insertTest(newTest)

            val mainMenuActivity = Intent(this, MainActivity::class.java)
            startActivity(mainMenuActivity)
        }

        val backButton = findViewById<Button>(R.id.back_button)
        backButton.setOnClickListener {
            val mainMenuActivity = Intent(this, MainActivity::class.java)
            startActivity(mainMenuActivity)
        }
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {


            if (p2 >= allPatientsIds.size || p2 >= allNursesIds.size) {
                return
            }
            updatingPatient = allPatientsIds[p2]
            updatingNurces = allNursesIds[p2]

    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("Not yet implemented")
    }


}