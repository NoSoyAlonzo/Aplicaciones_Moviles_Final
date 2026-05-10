package com.example.proyectoapp_moviles.viewModel

import androidx.lifecycle.ViewModel
import com.example.proyectoapp_moviles.model.Usuario

class usuario_register: ViewModel() {

    private var fecha_nacimiento: Long=0L

    fun setFecha_Nacimiento(fecha:Long){
        fecha_nacimiento=fecha
    }

    fun registrar(nombre:String, e_mail:String, password: String, fechaNacimiento: String, no_cel:String){

        val usuario= Usuario(
            nombre = nombre,
            e_mail = e_mail,
            password = password,
            fechaN = fechaNacimiento,
            noCelular = no_cel,


        )

        //llamar al repository
        print("Guardando Usuario: ${usuario}")
    }
}