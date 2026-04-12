package com.example.proyectoapp_moviles.viewModel

import androidx.lifecycle.ViewModel

class usuario_register: ViewModel() {

    private var fecha_nacimiento: Long=0L

    fun setFecha_Nacimiento(fecha:Long){
        fecha_nacimiento=fecha
    }

    fun registrar(nombre:String,e_mail:String,password: String,no_cel:Int){

        val usuario=Usuario(
            nombre=nombre,
            e_mail=e_mail,
            password=password,
            no_cel=no_cel,
            fecha_nacimiento=fecha_nacimiento
        )

        //llamar al repository
        print("Guardando Usuario: ${usuario}")
    }
}