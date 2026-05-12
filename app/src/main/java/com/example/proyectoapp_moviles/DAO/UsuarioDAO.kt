package com.example.proyectoapp_moviles.DAO

import com.example.proyectoapp_moviles.model.Cuidador
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

    fun encontrarPorID(id: String, callback: (Usuario?) -> Unit){
        dbRef.child(id).get().addOnSuccessListener { snapshot ->
            val usuario = snapshot.getValue(Usuario::class.java)
            callback(usuario)
        }.addOnFailureListener {
            callback(null)
        }
    }
    fun obtenerTodos(callback: (List<Cuidador>) -> Unit) {
        dbRef.get().addOnSuccessListener { snapshot ->
            val list = mutableListOf<Cuidador>()
            for (child in snapshot.children) {
                child.getValue(Cuidador::class.java)?.let { list.add(it) }
            }
            callback(list)
        }

    }
}