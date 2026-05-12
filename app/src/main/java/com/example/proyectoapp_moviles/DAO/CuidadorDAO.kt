package com.example.proyectoapp_moviles.DAO

import com.example.proyectoapp_moviles.model.Cuidador
import com.example.proyectoapp_moviles.model.Usuario
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

    fun encontrarPorID(id: String, callback: (Cuidador?) -> Unit){
        dbRef.child(id).get().addOnSuccessListener { snapshot ->
            val cuidador = snapshot.getValue(Cuidador::class.java)
             callback(cuidador)
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
