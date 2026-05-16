package com.example.proyectoapp_moviles

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectoapp_moviles.DAO.CuidadorDAO
import com.google.firebase.auth.FirebaseAuth

class admin_cuidadores : AppCompatActivity() {

    private lateinit var recycler: RecyclerView
    private lateinit var adapter: AdminCuidadorAdapter

    private val dao = CuidadorDAO()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_cuidadores)

        recycler = findViewById(R.id.recyclerCuidadores)
        recycler.layoutManager = LinearLayoutManager(this)

        adapter = AdminCuidadorAdapter(
            emptyList(),
            {},
            {}
        )
        recycler.adapter = adapter

        val btnUsuarios = findViewById<Button>(R.id.btnUsuarios)

        btnUsuarios.setOnClickListener {

            startActivity(Intent(this, admin_usuarios::class.java))
        }

        val btnCerrarSesion =
            findViewById<ImageButton>(R.id.btnCerrarSesion)


        btnCerrarSesion.setOnClickListener {

            FirebaseAuth.getInstance().signOut()

            val intent = Intent(this, MainActivity::class.java)
            intent.flags =
                Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

            startActivity(intent)
            finish()
        }

        cargarSolicitudes()
    }

    private fun cargarSolicitudes() {
        dao.obtenerTodos { lista ->

            val pendientes = lista.filter { !it.aceptado }

            adapter = AdminCuidadorAdapter(

                pendientes,

                { cuidador ->
                    cuidador.aceptado = true
                    dao.actualizar(cuidador)

                    Toast.makeText(this, "Cuidador aceptado", Toast.LENGTH_SHORT).show()
                    cargarSolicitudes()
                },

                { cuidador ->
                    dao.eliminar(cuidador.id)

                    Toast.makeText(this, "Solicitud rechazada", Toast.LENGTH_SHORT).show()
                    cargarSolicitudes()
                }
            )

            recycler.adapter = adapter
        }
    }
}