package com.comp.lab4_sebastiansilva.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.comp.lab4_sebastiansilva.AppDatabase
import com.comp.lab4_sebastiansilva.R
import com.comp.lab4_sebastiansilva.models.Nurse
import com.comp.lab4_sebastiansilva.models.Patient
import com.comp.lab4_sebastiansilva.viewmodel.TestActivityViewModel

class TestInfoActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    private val viewModel: TestActivityViewModel by viewModels()
    private var patientID: Int=0;
    private lateinit var patientsIdSpinner: Spinner
    private lateinit var nurseIdSpinner: Spinner
    private lateinit var testListView: RecyclerView
    private lateinit var testCustAdapter: TestAdapter
    private lateinit var arrayAdapterNurse: ArrayAdapter<Int>
    private lateinit var arrayAdapterPatient: ArrayAdapter<Int>
    private val patientsIds: ArrayList<Int> = arrayListOf()
    private val nurseIds: ArrayList<Int> = arrayListOf()
    private val allNursesIds: ArrayList<Nurse> = arrayListOf()
    private val allPatientsIds: ArrayList<Patient> = arrayListOf()
    private var updatingPatient: Patient? = null
    private var updatingNurces: Nurse? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_info)

        nurseIdSpinner=findViewById(R.id.NurseSpinner)
        patientsIdSpinner=findViewById(R.id.ClientSpinner)

        arrayAdapterNurse = ArrayAdapter<Int>(this, R.layout.id_item, R.id.id_text, nurseIds)
        arrayAdapterNurse.setNotifyOnChange(true)
        nurseIdSpinner.adapter = arrayAdapterNurse

        arrayAdapterPatient = ArrayAdapter<Int>(this, R.layout.id_item, R.id.id_text, patientsIds)
        arrayAdapterPatient.setNotifyOnChange(true)
        patientsIdSpinner.adapter = arrayAdapterPatient

        val db = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "medical-tests").build()
        viewModel.initDatabase(db, nurseIdSpinner.id)

        viewModel.nurseList.observe(this){
            nurseIds.clear()
            nurseIds.addAll(it.map { nurse ->
                nurse.nurseId!!
            })
            allNursesIds.clear()
            allNursesIds.addAll(it)
            arrayAdapterNurse = ArrayAdapter<Int>(this, R.layout.id_item, R.id.id_text, nurseIds)
            nurseIdSpinner.adapter = arrayAdapterNurse

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
            arrayAdapterPatient = ArrayAdapter<Int>(this, R.layout.id_item, R.id.id_text, patientsIds)
            patientsIdSpinner.adapter = arrayAdapterPatient
        }
        patientsIdSpinner.onItemSelectedListener=this
        viewModel.getTestByPatientId(patientsIdSpinner.id)
        viewModel.getNurses()
        viewModel.getPatient()

        testListView=findViewById(R.id.test_recycler_view)
        testListView.layoutManager=LinearLayoutManager(this,RecyclerView.VERTICAL,false)
        testCustAdapter = TestAdapter()
        testListView.adapter=testCustAdapter
        viewModel.testList.observe(this){
            testCustAdapter.updateAdapter(it)
            testCustAdapter.notifyDataSetChanged()

        }
        viewModel.getTestByPatientId( patientID )
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        if (p2 >= allPatientsIds.size || p2 >= allNursesIds.size) {
            testCustAdapter.updateAdapter(emptyList())
            return
        }

        updatingPatient = allPatientsIds[p2]
        updatingNurces = allNursesIds[p2]
        testCustAdapter.notifyDataSetChanged()
        viewModel.getTestByPatientId(updatingPatient?.patientId!!)
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("Not yet implemented")
    }
}