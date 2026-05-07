package com.example.proyectoapp_moviles.DAO

import android.R.attr.id
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.proyectoapp_moviles.model.usuario
import com.google.firebase.Firebase
import com.google.firebase.database.FirebaseDatabase

import java.time.LocalDate
import kotlin.collections.emptyList

class UsuarioDAO {


    private val dbRef = FirebaseDatabase
        .getInstance()
        .getReference("usuarios")


    fun agregar(usuario: usuario) {

        val id = dbRef.push().key!!

        usuario.id = id

        dbRef.child(id).setValue(usuario)
    }


    fun actualizar(usuario: usuario) {

        dbRef.child(usuario.id).setValue(usuario)
    }



    fun eliminar(id: String) {

        dbRef.child(id).removeValue()
    }
}