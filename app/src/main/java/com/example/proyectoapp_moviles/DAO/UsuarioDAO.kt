package com.example.proyectoapp_moviles.DAO

import com.example.proyectoapp_moviles.model.Cuidador
import com.example.proyectoapp_moviles.model.Usuario
import com.google.firebase.database.FirebaseDatabase

class UsuarioDAO {


    private val dbRef = FirebaseDatabase
        .getInstance()
        .getReference("usuarios")


    fun agregar(usuario: Usuario, callback: (Boolean) -> Unit) {

        val id = dbRef.push().key!!

        usuario.id = id

        dbRef.child(id).setValue(usuario)
            .addOnSuccessListener {
                callback(true)
            }
            .addOnFailureListener {
                callback(false)
            }
    }


    fun actualizar(usuario: Usuario) {

        dbRef.child(usuario.id).setValue(usuario)
    }



    fun activar(id: String) {

        dbRef.child(id)
            .child("activo")
            .setValue(true)
    }

    fun desactivar(id: String) {

        dbRef.child(id)
            .child("activo")
            .setValue(false)
    }

    fun encontrarPorID(id: String, callback: (Usuario?) -> Unit){
        dbRef.child(id).get().addOnSuccessListener { snapshot ->
            val usuario = snapshot.getValue(Usuario::class.java)
            callback(usuario)
        }.addOnFailureListener {
            callback(null)
        }
    }


    fun obtenerTodosUsuarios(callback: (List<Usuario>) -> Unit) {

        dbRef.get().addOnSuccessListener { snapshot ->

            val lista = mutableListOf<Usuario>()

            for (child in snapshot.children) {

                val usuario = child.getValue(Usuario::class.java)

                if (usuario != null) {
                    lista.add(usuario)
                }
            }

            callback(lista)

        }.addOnFailureListener {

            callback(emptyList())
        }
    }
}