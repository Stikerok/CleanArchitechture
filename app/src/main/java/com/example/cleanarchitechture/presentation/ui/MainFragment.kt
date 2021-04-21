package com.example.cleanarchitechture.presentation.ui

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cleanarchitechture.Dependencies
import com.example.cleanarchitechture.R
import com.example.cleanarchitechture.entity.Person
import com.example.cleanarchitechture.presentation.adapter.ItemClickListener
import com.example.cleanarchitechture.presentation.adapter.PersonAdapter
import com.example.cleanarchitechture.presentation.viewModel.MainViewModel
import com.example.cleanarchitechture.presentation.viewModel.MainViewModelFactory
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers


class MainFragment : Fragment(), ItemClickListener {

    companion object {
        fun newInstance() =
            MainFragment()
    }

    private lateinit var viewModel: MainViewModel
    private lateinit var firstInput: EditText
    private lateinit var secondInput: EditText
    private lateinit var addPersonBtn: Button
    private lateinit var persons: RecyclerView
    private lateinit var personsFilter: RecyclerView
    private var adapter = PersonAdapter(listOf())
    private var adapterFilter = PersonAdapter(listOf())
    private var compositeDisposable = CompositeDisposable()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val factory = MainViewModelFactory(Dependencies.getPersonUseCase(requireContext()))
        viewModel = ViewModelProvider(this, factory).get(MainViewModel::class.java)
        firstInput.doAfterTextChanged {
            viewModel.name = it.toString()
        }
        secondInput.doAfterTextChanged {
            viewModel.rating = it.toString()
        }
        val observable = Observable.create<Unit> {emitter ->
            addPersonBtn.setOnClickListener {
                emitter.onNext(Unit)
            }
        }

        val subscribe = observable
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                viewModel.addPerson()
            }
        compositeDisposable.add(subscribe)

        viewModel.getPersons().observe(viewLifecycleOwner, Observer {
            adapter.setData(it)
        })
        viewModel.getPersonsFilter().observe(viewLifecycleOwner, Observer {
            adapterFilter.setData(it)
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        firstInput = view.findViewById(R.id.input_first)
        secondInput = view.findViewById(R.id.input_second)
        addPersonBtn = view.findViewById(R.id.calculate_btn)
        persons = view.findViewById(R.id.persons_list)
        personsFilter = view.findViewById(R.id.persons_list_filter)

        persons.layoutManager = LinearLayoutManager(requireContext())
        persons.adapter = adapter
        personsFilter.layoutManager = LinearLayoutManager(requireContext())
        personsFilter.adapter = adapterFilter
        adapter.setListener(this)


    }

    override fun onDestroyView() {
        super.onDestroyView()
        adapter.setListener(null)
        compositeDisposable.dispose()
    }

    override fun onClick(person: Person) {
        viewModel.onPersonSelected(person)
    }


}