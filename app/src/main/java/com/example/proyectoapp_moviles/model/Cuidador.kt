package com.example.proyectoapp_moviles.model

data class Cuidador(

    var id: String = "",

    var uidUsuario: String = "",

    var nCompleto: String = "",
    var aniosExp: String = "",
    var descripcion: String = "",

    var paseos: Boolean = false,
    var hospedaje: Boolean = false,
    var ciudado_diario: Boolean = false,

    var aceptado: Boolean = false,

    var precio: String = ""
)
