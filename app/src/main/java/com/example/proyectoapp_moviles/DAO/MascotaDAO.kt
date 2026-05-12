package com.example.proyectoapp_moviles.DAO;

import com.google.firebase.database.FirebaseDatabase;
import com.example.proyectoapp_moviles.model.Mascota;


class MascotaDAO {

    private val dbRef = FirebaseDatabase
        .getInstance()
        .getReference("mascota")


    fun agregar(mascota: Mascota) {
        val id = dbRef.push().key!!
        mascota.id = id
        dbRef.child(id).setValue(mascota)
    }

    fun actualizar(mascota: Mascota) {
        dbRef.child(mascota.id).setValue(mascota)
    }

    fun elminar(id: String){
        dbRef.child(id).removeValue()
    }

    fun encontrarPorID(id: String, callback: (Mascota?) -> Unit){
        dbRef.child(id).get().addOnSuccessListener { snapshot ->
            val mascota = snapshot.getValue(Mascota::class.java)
            callback(mascota)
            }.addOnFailureListener {
            callback(null)
        }
    }

    fun obtenerTodos(callback: (List<Mascota>) -> Unit) {
        dbRef.get().addOnSuccessListener { snapshot ->
            val list = mutableListOf<Mascota>()
            for (child in snapshot.children) {
                child.getValue(Mascota::class.java)?.let { list.add(it) }
            }
            callback(list)
            }
    }

}
