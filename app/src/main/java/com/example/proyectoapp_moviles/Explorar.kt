package com.example.proyectoapp_moviles

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectoapp_moviles.DAO.CuidadorDAO
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth

class Explorar : AppCompatActivity() {

    private lateinit var recyclerCuidadores: RecyclerView

    private val cuidadorDAO =
        CuidadorDAO()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.explorar)

        Log.d("EXPLORAR_DEBUG", "Pantalla explorar iniciada")

        recyclerCuidadores =
            findViewById(R.id.recyclerCuidadores)

        recyclerCuidadores.layoutManager =
            LinearLayoutManager(this)

        cargarCuidadores()

        val explorar =
            findViewById<LinearLayout>(R.id.explorar)

        val misMascotas =
            findViewById<LinearLayout>(R.id.mis_mascotas)

        val reservas =
            findViewById<LinearLayout>(R.id.reservas)

        val perfil =
            findViewById<LinearLayout>(R.id.mi_perfil)
        val btnAgregar =
            findViewById<FloatingActionButton>(
                R.id.btnAgregarCuidador
            )

        explorar.setOnClickListener {
            startActivity(
                Intent(this, Explorar::class.java)
            )
        }

        misMascotas.setOnClickListener {
            startActivity(
                Intent(this, ScreenHome::class.java)
            )
        }

        reservas.setOnClickListener {
            startActivity(
                Intent(this, Reservas::class.java)
            )
        }

        perfil.setOnClickListener {
            startActivity(
                Intent(this, PerfilActivity::class.java)
            )
        }

        val uid =
            FirebaseAuth.getInstance()
                .currentUser?.uid ?: ""

        cuidadorDAO.verificarSiEsCuidador(uid) { esCuidador ->

            if (esCuidador) {

                btnAgregar.hide()

            } else {

                btnAgregar.show()

                btnAgregar.setOnClickListener {

                    startActivity(
                        Intent(
                            this,
                            RegistroCuidador::class.java
                        )
                    )
                }
            }
        }
    }

    private fun cargarCuidadores() {

        val uidActual =
            FirebaseAuth.getInstance()
                .currentUser?.uid

        cuidadorDAO.obtenerAprobados { lista ->

            val filtrados =
                lista.filter {

                    it.uidUsuario != uidActual
                }

            val adapter =
                CuidadorAdapter(filtrados)

            recyclerCuidadores.adapter =
                adapter
        }
    }


}