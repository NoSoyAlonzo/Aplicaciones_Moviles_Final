package com.example.proyectoapp_moviles

import android.content.Intent
import android.os.Bundle
import android.widget.Button
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


    }


}