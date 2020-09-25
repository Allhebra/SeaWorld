package com.allhebra.seaworld.model

data class Cell(
    val cellType: CellType
)

enum class CellType(val value: String?) {
    PENGUIN("penguin"),
    ORCA("orca"),
    EMPTY(null)
}

fun createCell(name: String?): Cell {
    return when (name) {
        CellType.PENGUIN.value -> {
            Cell(CellType.PENGUIN)
        }
        CellType.ORCA.value -> {
            Cell(CellType.ORCA)
        }
        CellType.EMPTY.value -> {
            Cell(CellType.EMPTY)
        }
        else -> {
            Cell(CellType.EMPTY)
        }
    }
}
