package com.example.proyectoapp_moviles

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btnInicio = findViewById<Button>(R.id.btnInicio)
        val btnRegistro = findViewById<Button>(R.id.btnRegistro)

        btnInicio.setOnClickListener {
            val intent = Intent(this, ScreenHome::class.java)
            startActivity(intent)
        }

        btnRegistro.setOnClickListener {
            val intent = Intent(this, registro_form::class.java)
            startActivity(intent)
        }
    }


}
