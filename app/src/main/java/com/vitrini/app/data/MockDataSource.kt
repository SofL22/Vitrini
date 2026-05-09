package com.vitrini.app.data

import com.vitrini.app.model.Business
import com.vitrini.app.model.Product

object MockDataSource {
    val emprendimientos = listOf(
        Business(
            id = 1,
            name = "Café Bruma",
            description = "Café artesanal costarricense.",
            category = "Comida",
            location = "Heredia"
        ),
        Business(
            id = 2,
            name = "Arte Tico",
            description = "Productos hechos a mano por emprendedores locales.",
            category = "Artesanía",
            location = "San José"
        )
    )

    val productos = listOf(
        Product(
            id = 1,
            businessId = 1,
            name = "Café molido artesanal",
            descriptionProd = "Bolsa de café premium de origen costarricense.",
            price = 4500.0,
            category = "Comida"
        ),
        Product(
            id = 2,
            businessId = 2,
            name = "Pulsera artesanal",
            descriptionProd = "Pulsera hecha a mano con diseño local.",
            price = 3000.0,
            category = "Artesanía"
        )
    )
}