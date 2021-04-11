package com.example.cleanarchitechture.presentation.ui

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cleanarchitechture.R
import com.example.cleanarchitechture.domain.Operation
import com.example.cleanarchitechture.presentation.adapter.ItemClickListener
import com.example.cleanarchitechture.presentation.adapter.OperationAdapter
import com.example.cleanarchitechture.presentation.viewModel.CalculationState
import com.example.cleanarchitechture.presentation.viewModel.MainViewModel


class MainFragment : Fragment(), ItemClickListener {

    companion object {
        fun newInstance() =
            MainFragment()
    }

    private lateinit var viewModel: MainViewModel
    private lateinit var firstInput: EditText
    private lateinit var secondInput: EditText
    private lateinit var calculateBtn: Button
    private lateinit var operations: RecyclerView
    private lateinit var textState: TextView
    private var adapter = OperationAdapter(listOf())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        firstInput.doAfterTextChanged {
            viewModel.first = it.toString()
        }
        secondInput.doAfterTextChanged {
            viewModel.second = it.toString()
        }
        calculateBtn.setOnClickListener {

            val toast =
                Toast.makeText(requireContext(), "${viewModel.calculate()}", Toast.LENGTH_SHORT)
            toast.show()

        }

        viewModel.getOperations().observe(viewLifecycleOwner, Observer {
            adapter.setData(it)
        })
        viewModel.calculationState.observe(viewLifecycleOwner, Observer {
            textState.text = getString(
                when (it) {
                    CalculationState.Free -> R.string.state_free
                    CalculationState.Loading -> R.string.state_loading
                    CalculationState.Result -> R.string.state_result
                }
            )
            when (it){
                CalculationState.Free -> calculateBtn.isEnabled = true
                CalculationState.Loading -> calculateBtn.isEnabled = false
                CalculationState.Result -> calculateBtn.isEnabled = false
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        firstInput = view.findViewById(R.id.input_first)
        secondInput = view.findViewById(R.id.input_second)
        calculateBtn = view.findViewById(R.id.calculate_btn)
        operations = view.findViewById(R.id.operations_list)
        textState = view.findViewById(R.id.text_out)

        operations.layoutManager = LinearLayoutManager(requireContext())
        operations.adapter = adapter
        adapter.setListener(this)


    }

    override fun onDestroyView() {
        super.onDestroyView()
        adapter.setListener(null)
    }

    override fun onClick(operation: Operation) {
       viewModel.onOperationSelected(operation)
    }

}