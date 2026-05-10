package com.example.proyectoapp_moviles.DAO

import com.example.proyectoapp_moviles.model.Cuidador
import com.google.firebase.database.FirebaseDatabase

class CuidadorDAO {
    private val dbRef = FirebaseDatabase
        .getInstance()
        .getReference("cuidador")


    fun agregar(cuidador: Cuidador) {

        val id = dbRef.push().key!!

        cuidador.id=id

        dbRef.child(id).setValue(cuidador)
    }


    fun actualizar(cuidador: Cuidador) {

        dbRef.child(cuidador.id).setValue(cuidador)
    }



    fun eliminar(id: String) {

        dbRef.child(id).removeValue()
    }
}
