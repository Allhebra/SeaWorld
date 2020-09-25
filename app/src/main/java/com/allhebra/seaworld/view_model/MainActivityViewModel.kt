package com.allhebra.seaworld.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.allhebra.seaworld.data.WorldRepository
import com.allhebra.seaworld.model.Cell
import com.allhebra.seaworld.model.createCell

class MainActivityViewModel(
    private val worldRepository: WorldRepository
) : ViewModel() {
    var cells: MutableLiveData<MutableList<Cell>> = MutableLiveData()
    var tableSize: MutableLiveData<Pair<Int, Int>> = MutableLiveData()
    var progress: MutableLiveData<Boolean> = MutableLiveData()

    init {
        progress.postValue(true)
        worldRepository.setCallback { c -> convert(c) }
    }

    fun initConnection() {
        progress.postValue(true)
        worldRepository.initConnection()
    }

    fun reset() {
        cells.postValue(mutableListOf())
        progress.postValue(true)
        worldRepository.reset()
    }

    fun nextTurn() {
        progress.postValue(true)
        worldRepository.nextTurn()
    }

    fun disconnect() = worldRepository.disconnect()

    private fun convert(cells: Array<Array<String?>>) {
        val list = mutableListOf<Cell>()
        tableSize.postValue(Pair(cells.size, cells[0].size))
        cells.forEach { row -> row.forEach { list.add(createCell(it)) } }
        progress.postValue(false)
        this.cells.postValue(list)
    }
}