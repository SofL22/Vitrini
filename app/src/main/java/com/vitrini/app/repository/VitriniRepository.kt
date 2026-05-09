package com.vitrini.app.repository
import com.vitrini.app.data.MockDataSource
import com.vitrini.app.model.Business
import com.vitrini.app.model.Product

class VitriniRepository {
    fun obtenerEmprendimientos(): List<Business> {
        return MockDataSource.emprendimientos
    }

    fun obtenerProductos(): List<Product> {
        return MockDataSource.productos
    }
}