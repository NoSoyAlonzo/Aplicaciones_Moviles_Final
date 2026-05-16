package com.example.proyectoapp_moviles

import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.proyectoapp_moviles.DAO.CuidadorDAO

class admin_cuidadores : AppCompatActivity() {

    private lateinit var recycler: RecyclerView

    private val dao = CuidadorDAO()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_cuidadores)

        recycler =
            findViewById(R.id.recyclerCuidadores)

        recycler.layoutManager =
            LinearLayoutManager(this)

        cargarSolicitudes()
    }

    private fun cargarSolicitudes() {

        dao.obtenerTodos { lista ->

            // SOLO LOS PENDIENTES
            val pendientes =
                lista.filter { !it.aceptado }

            recycler.adapter =
                AdminCuidadorAdapter(

                    pendientes,

                    // ACEPTAR
                    { cuidador ->

                        cuidador.aceptado = true

                        dao.actualizar(cuidador)

                        Toast.makeText(
                            this,
                            "Cuidador aceptado",
                            Toast.LENGTH_SHORT
                        ).show()

                        // RECARGAR LISTA
                        cargarSolicitudes()
                    },

                    // RECHAZAR
                    { cuidador ->

                        dao.eliminar(cuidador.id)

                        Toast.makeText(
                            this,
                            "Solicitud rechazada",
                            Toast.LENGTH_SHORT
                        ).show()

                        // RECARGAR LISTA
                        cargarSolicitudes()
                    }
                )
        }
    }
}