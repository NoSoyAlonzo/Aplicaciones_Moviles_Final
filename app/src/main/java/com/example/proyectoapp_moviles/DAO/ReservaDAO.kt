package com.example.proyectoapp_moviles.DAO

import android.util.Log
import com.example.proyectoapp_moviles.model.Reserva
import com.google.firebase.database.FirebaseDatabase
import kotlin.collections.getValue
import kotlin.text.get

class ReservaDAO {

    private val dbRef =
        FirebaseDatabase
            .getInstance()
            .getReference("reservas")

    fun agregar(reserva: Reserva) {

        val id = dbRef.push().key!!

        reserva.id = id

        dbRef.child(id)
            .setValue(reserva)
    }

    fun obtenerReservasCuidador(uid: String, callback: (List<Reserva>) -> Unit) {

        dbRef.orderByChild("uidCuidador")
            .equalTo(uid)
            .get()
            .addOnSuccessListener { snapshot ->

                val lista = mutableListOf<Reserva>()

                for (child in snapshot.children) {
                    child.getValue(Reserva::class.java)?.let {
                        lista.add(it)
                    }
                }

                callback(lista)
            }
    }

    fun obtenerReservasUsuario(
        uid: String,
        callback: (List<Reserva>) -> Unit
    ) {

        Log.d("RESERVAS_DEBUG", "UID recibido: $uid")

        dbRef.orderByChild("uidUsuario")
            .equalTo(uid)
            .get()
            .addOnSuccessListener { snapshot ->

                Log.d("RESERVAS_DEBUG", "RESULTADOS: ${snapshot.childrenCount}")

                val lista = mutableListOf<Reserva>()

                for (child in snapshot.children) {

                    val reserva = child.getValue(Reserva::class.java)

                    if (reserva != null) {
                        lista.add(reserva)
                    }
                }

                Log.d("RESERVAS_DEBUG", "FINAL LISTA: ${lista.size}")

                callback(lista)
            }
            .addOnFailureListener {
                Log.e("RESERVAS_DEBUG", "ERROR FIREBASE", it)
            }
    }

    fun actualizarEstado(
        id: String,
        estado: String,
        onDone: () -> Unit = {}
    ) {
        dbRef.child(id)
            .child("estado")
            .setValue(estado)
            .addOnSuccessListener {
                onDone()
            }
    }

    fun obtenerReservasPorUsuarioOCuidador(
        uid: String,
        callback: (List<Reserva>) -> Unit
    ) {

        dbRef.get().addOnSuccessListener { snapshot ->

            val lista = mutableListOf<Reserva>()

            for (child in snapshot.children) {

                val reserva = child.getValue(Reserva::class.java)

                if (
                    reserva != null &&
                    (reserva.uidUsuario == uid ||
                            reserva.uidCuidador == uid)
                ) {
                    lista.add(reserva)
                }
            }

            callback(lista)
        }
    }
}