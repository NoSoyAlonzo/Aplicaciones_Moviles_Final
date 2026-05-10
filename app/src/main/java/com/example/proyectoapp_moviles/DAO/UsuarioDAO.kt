package com.example.proyectoapp_moviles.DAO

import com.example.proyectoapp_moviles.model.Usuario
import com.google.firebase.database.FirebaseDatabase

class UsuarioDAO {


    private val dbRef = FirebaseDatabase
        .getInstance()
        .getReference("usuarios")


    fun agregar(usuario: Usuario) {

        val id = dbRef.push().key!!

        usuario.id = id

        dbRef.child(id).setValue(usuario)
    }


    fun actualizar(usuario: Usuario) {

        dbRef.child(usuario.id).setValue(usuario)
    }



    fun eliminar(id: String) {

        dbRef.child(id).removeValue()
    }
}