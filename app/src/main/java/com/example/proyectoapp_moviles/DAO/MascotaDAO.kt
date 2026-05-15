package com.example.proyectoapp_moviles.DAO;

import com.google.firebase.database.FirebaseDatabase;
import com.example.proyectoapp_moviles.model.Mascota;
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener


class MascotaDAO {

    private val dbRef =
        FirebaseDatabase.getInstance()
            .getReference("mascotas")

    fun agregar(
        mascota: Mascota,
        callback: (Boolean) -> Unit
    ) {

        val id = dbRef.push().key ?: ""

        mascota.id = id

        dbRef.child(id)
            .setValue(mascota)
            .addOnSuccessListener {

                callback(true)
            }
            .addOnFailureListener {

                callback(false)
            }
    }

    fun obtenerMascotasUsuario(
        uid: String,
        callback: (List<Mascota>) -> Unit
    ) {

        dbRef
            .orderByChild("uidUsuario")
            .equalTo(uid)
            .addListenerForSingleValueEvent(
                object : ValueEventListener {

                    override fun onDataChange(snapshot: DataSnapshot) {

                        val lista =
                            mutableListOf<Mascota>()

                        for (dato in snapshot.children) {

                            val mascota =
                                dato.getValue(Mascota::class.java)

                            mascota?.let {
                                lista.add(it)
                            }
                        }

                        callback(lista)
                    }

                    override fun onCancelled(error: DatabaseError) {

                        callback(emptyList())
                    }
                }
            )
    }

    fun actualizar(mascota: Mascota) {

        dbRef.child(mascota.id)
            .setValue(mascota)
    }

    fun eliminar(id: String) {

        dbRef.child(id)
            .removeValue()
    }

    fun encontrarPorID(
        id: String,
        callback: (Mascota?) -> Unit
    ) {

        dbRef.child(id)
            .get()
            .addOnSuccessListener { snapshot ->

                val mascota =
                    snapshot.getValue(Mascota::class.java)

                callback(mascota)
            }
            .addOnFailureListener {

                callback(null)
            }
    }
}