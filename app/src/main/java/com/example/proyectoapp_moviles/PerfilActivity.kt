package com.example.proyectoapp_moviles

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth

class PerfilActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.perfil)


        val explorar = findViewById<LinearLayout>(R.id.explorar)
        val misMascotas = findViewById<LinearLayout>(R.id.mis_mascotas)
        val reservas = findViewById<LinearLayout>(R.id.reservas)
        val perfil = findViewById<LinearLayout>(R.id.mi_perfil)

        explorar.setOnClickListener {
            startActivity(Intent(this, Explorar::class.java))
        }

        misMascotas.setOnClickListener {
            startActivity(Intent(this, ScreenHome::class.java))
        }

        reservas.setOnClickListener {
            startActivity(Intent(this, Reservas::class.java))
        }

        perfil.setOnClickListener {
            startActivity(Intent(this, PerfilActivity::class.java))
        }

        val btnCerrarSesion =
            findViewById<Button>(R.id.btnCerrarSesion)

        btnCerrarSesion.setOnClickListener {

            // Cerrar sesión
            FirebaseAuth.getInstance().signOut()

            // Ir al login
            val intent = Intent(
                this,
                MainActivity::class.java
            )

            // Limpiar actividades anteriores
            intent.flags =
                Intent.FLAG_ACTIVITY_NEW_TASK or
                        Intent.FLAG_ACTIVITY_CLEAR_TASK

            startActivity(intent)

            finish()
        }
        }


}