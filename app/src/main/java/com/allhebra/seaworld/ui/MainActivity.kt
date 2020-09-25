package com.allhebra.seaworld.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.allhebra.seaworld.R
import com.allhebra.seaworld.data.WorldRepository
import com.allhebra.seaworld.model.Cell
import com.allhebra.seaworld.view_model.MainActivityViewModel
import com.allhebra.seaworld.view_model.MainActivityViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val worldRepository = WorldRepository()
    private val viewModelFactory = MainActivityViewModelFactory(worldRepository)
    private lateinit var viewModel: MainActivityViewModel
    private var cells: ArrayList<Cell> = ArrayList()
    private var adapter: TableAdapter? = null
    private lateinit var layoutManager: GridLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainActivityViewModel::class.java)
        initViews()
        bindUI()
    }

    private fun initViews() {
        val rv = findViewById<RecyclerView>(R.id.cells)
        layoutManager = GridLayoutManager(this, 1)
        rv.layoutManager = layoutManager
        adapter = TableAdapter(cells, this)
        rv.adapter = adapter
        restart.setOnClickListener { viewModel.reset() }
        next.setOnClickListener { viewModel.nextTurn() }
    }

    private fun bindUI() {
        viewModel.tableSize.observe(this, Observer { values ->
            Log.d("table size: ", "${values.second}")
            layoutManager.spanCount = values.second
            adapter?.setSpanCount(values.second)
        })
        viewModel.cells.observe(this, Observer { entries ->
            showCells(entries)
        })
        viewModel.progress.observe(this, Observer { visible ->
            progress_bar.isVisible = visible
        })
    }

    private fun showCells(cells: MutableList<Cell>) {
        this.cells.clear()
        this.cells.addAll(cells)
        adapter?.notifyDataSetChanged()
        Log.d("tag", "showCells:   ${cells.size}")
    }

    override fun onStart() {
        super.onStart()
        viewModel.initConnection()
    }

    override fun onStop() {
        super.onStop()
        viewModel.disconnect()
    }
}
