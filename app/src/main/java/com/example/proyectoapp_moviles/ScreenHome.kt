package com.example.proyectoapp_moviles

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ScreenHome : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.screen_home)

        val buttonRegistrar = findViewById<Button>(R.id.agregar_registro)

        buttonRegistrar.setOnClickListener {
            val intent = Intent(this, AgregarMascota::class.java)
            startActivity(intent)
        }


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

    }


}