package com.comp.lab4_sebastiansilva.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.comp.lab4_sebastiansilva.R
import com.comp.lab4_sebastiansilva.models.Test

class TestAdapter : RecyclerView.Adapter<TestAdapter.ViewHolder>() {

    private var test: ArrayList<Test> = arrayListOf()

    class ViewHolder(view: View):RecyclerView.ViewHolder(view){
        val testIDView: TextView
        val test_bpl: TextView
        val test_bph: TextView
        val test_temp: TextView

        init{
            testIDView=view.findViewById(R.id.testIDtext)
            test_bpl=view.findViewById(R.id.test_bpltext)
            test_bph=view.findViewById(R.id.test_bphtext)
            test_temp=view.findViewById(R.id.test_temperature_text)
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TestAdapter.ViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.test_item, parent, false)

        return ViewHolder(view)
    }
    fun updateAdapter(newTest: List<Test>){
        test.clear()
        test.addAll(newTest)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val test = test[position]
        holder.testIDView.text = test.testId.toString()
        holder.test_bpl.text = test.bpl.toString()
        holder.test_bph.text= test.bph.toString()
        holder.test_temp.text=test.temperature.toString()
    }
    override fun getItemCount(): Int {
        return test.size
    }
}