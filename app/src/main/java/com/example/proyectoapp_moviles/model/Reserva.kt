package com.example.proyectoapp_moviles.model

data class Reserva(

    var id: String = "",

    var uidUsuario: String = "",
    var uidCuidador: String = "",

    var nombreCuidador: String = "",

    var idMascota: String = "",
    var nombreMascota: String = "",

    var fechaInicio: String = "",
    var fechaFin: String = "",

    var precio: String = "",

    var estado: String = "Pendiente"
)