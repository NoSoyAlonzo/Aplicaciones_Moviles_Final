package com.example.proyectoapp_moviles

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.proyectoapp_moviles.DAO.UsuarioDAO
import com.google.firebase.auth.FirebaseAuth

class PerfilActivity : AppCompatActivity() {

    private lateinit var txtNombre: TextView
    private lateinit var txtCorreo: TextView
    private lateinit var txtTelefono: TextView
    private lateinit var btnCerrarSesion: Button

    private val auth = FirebaseAuth.getInstance()
    private val usuarioDAO = UsuarioDAO()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.perfil)

        txtNombre = findViewById(R.id.txtNombre)
        txtCorreo = findViewById(R.id.txtCorreo)
        txtTelefono = findViewById(R.id.txtTelefono)
        btnCerrarSesion = findViewById(R.id.btnCerrarSesion)

        cargarUsuario()
        setupFooter()

        btnCerrarSesion.setOnClickListener {

            auth.signOut()

            startActivity(
                Intent(this, MainActivity::class.java)
            )

            finish()
        }
    }

    private fun cargarUsuario() {

        val uidAuth = auth.currentUser?.uid ?: return

        usuarioDAO.obtenerTodosUsuarios { lista ->

            val usuario =
                lista.find { it.uidAuth == uidAuth }

            runOnUiThread {

                if (usuario != null) {
                    txtNombre.text = usuario.nombre
                    txtCorreo.text = usuario.e_mail
                    txtTelefono.text = usuario.noCelular
                } else {
                    txtNombre.text = "Usuario"
                }
            }
        }
    }

    private fun setupFooter() {

        findViewById<LinearLayout>(R.id.explorar)
            .setOnClickListener {

                startActivity(
                    Intent(this, Explorar::class.java)
                )
            }

        findViewById<LinearLayout>(R.id.mis_mascotas)
            .setOnClickListener {

                startActivity(
                    Intent(this, ScreenHome::class.java)
                )
            }

        findViewById<LinearLayout>(R.id.reservas)
            .setOnClickListener {

                startActivity(
                    Intent(this, Reservas::class.java)
                )
            }
    }
}