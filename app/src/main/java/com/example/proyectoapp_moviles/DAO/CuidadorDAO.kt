package com.example.proyectoapp_moviles.DAO

import com.example.proyectoapp_moviles.model.Cuidador
import com.example.proyectoapp_moviles.model.Usuario
import com.google.firebase.database.FirebaseDatabase
import android.util.Log

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
    // TODOS
    fun obtenerTodos(
        callback: (List<Cuidador>) -> Unit
    ) {

        dbRef.get()
            .addOnSuccessListener { snapshot ->

                val lista =
                    mutableListOf<Cuidador>()

                for (child in snapshot.children) {

                    val cuidador =
                        child.getValue(
                            Cuidador::class.java
                        )

                    if (cuidador != null) {

                        lista.add(cuidador)
                    }
                }

                callback(lista)
            }
    }

    // SOLO APROBADOS
    fun obtenerAprobados(
        callback: (List<Cuidador>) -> Unit
    ) {

        dbRef.get()

            .addOnSuccessListener { snapshot ->

                val lista =
                    mutableListOf<Cuidador>()

                for (child in snapshot.children) {

                    val cuidador =
                        child.getValue(
                            Cuidador::class.java
                        )

                    Log.d(
                        "FIREBASE_DEBUG",
                        child.value.toString()
                    )

                    if (
                        cuidador != null &&
                        cuidador.aceptado == true
                    ) {

                        lista.add(cuidador)
                    }
                }

                Log.d(
                    "FIREBASE_DEBUG",
                    "APROBADOS: ${lista.size}"
                )

                callback(lista)
            }

            .addOnFailureListener {

                Log.d(
                    "FIREBASE_DEBUG",
                    "ERROR FIREBASE"
                )
            }
    }

    // APROBAR
    fun aprobarCuidador(id: String) {

        dbRef.child(id)
            .child("aceptado")
            .setValue(true)
    }

    fun existeCuidador(
        uid: String,
        callback: (Boolean) -> Unit
    ) {

        dbRef.orderByChild("uidUsuario")
            .equalTo(uid)
            .get()
            .addOnSuccessListener { snapshot ->

                callback(snapshot.exists())
            }
    }

    fun verificarSiEsCuidador(
        uid: String,
        callback: (Boolean) -> Unit
    ) {

        dbRef.get()
            .addOnSuccessListener { snapshot ->

                var encontrado = false

                for (child in snapshot.children) {

                    val cuidador =
                        child.getValue(Cuidador::class.java)

                    if (
                        cuidador != null &&
                        cuidador.uidUsuario == uid
                    ) {

                        encontrado = true
                        break
                    }
                }

                callback(encontrado)
            }
            .addOnFailureListener {

                callback(false)
            }
    }


}
