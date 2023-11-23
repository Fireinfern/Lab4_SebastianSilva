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
    private lateinit var arrayAdapter1: ArrayAdapter<Int>
    private lateinit var arrayAdapter2: ArrayAdapter<Int>
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

        arrayAdapter1 = ArrayAdapter<Int>(this, R.layout.id_item, R.id.id_text, nurseIds)
        arrayAdapter1.setNotifyOnChange(true)
        nurseIdSpinner.adapter = arrayAdapter1

        arrayAdapter2 = ArrayAdapter<Int>(this, R.layout.id_item, R.id.id_text, patientsIds)
        arrayAdapter2.setNotifyOnChange(true)
        patientsIdSpinner.adapter = arrayAdapter2
        viewModel.nurseList.observe(this){
            nurseIds.clear()
            nurseIds.addAll(it.map { nurse ->
                nurse.nurseId!!
            })
            allNursesIds.clear()
            allNursesIds.addAll(it)
            arrayAdapter1 = ArrayAdapter<Int>(this, R.layout.id_item, R.id.id_text, nurseIds)
            nurseIdSpinner.adapter = arrayAdapter1

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
            arrayAdapter2 = ArrayAdapter<Int>(this, R.layout.id_item, R.id.id_text, patientsIds)
            patientsIdSpinner.adapter = arrayAdapter2
        }
        patientsIdSpinner.onItemSelectedListener=this
        viewModel.getTestByPatientId(patientsIdSpinner.id)

        testListView=findViewById(R.id.test_recycler_view)
        testListView.layoutManager=LinearLayoutManager(this,RecyclerView.VERTICAL,false)
        testCustAdapter = TestAdapter()
        testListView.adapter=testCustAdapter
        viewModel.testList.observe(this){
            testCustAdapter.updateAdapter(it)
        }
        viewModel.getTestByPatientId( patientID )
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        updatingPatient = allPatientsIds[p2]
        updatingNurces = allNursesIds[p2]
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("Not yet implemented")
    }
}