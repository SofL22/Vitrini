package com.vitrini.app.viewmodel

import androidx.lifecycle.ViewModel
import com.vitrini.app.repository.VitriniRepository

class MainViewModel : ViewModel(){
    private val repository = VitriniRepository()

    fun cargarProductos() = repository.obtenerProductos()

    fun cargarEmprendimientos() = repository.obtenerEmprendimientos()
}