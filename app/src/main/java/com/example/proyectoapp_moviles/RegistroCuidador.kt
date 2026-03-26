package com.example.proyectoapp_moviles

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class RegistroCuidador : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.registro_cuidador)

        findViewById<ImageButton>(R.id.btnBack).setOnClickListener {
            finish()
        }

        val nombre = findViewById<EditText>(R.id.inputNombre)
        val desc = findViewById<EditText>(R.id.inputDesc)
        val spinner = findViewById<Spinner>(R.id.spinnerExp)
        val btnContinuar = findViewById<Button>(R.id.btnContinuar)

        val opciones = arrayOf("1-2 años", "3-5 años", "5+ años")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, opciones)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        btnContinuar.setOnClickListener {
            Toast.makeText(this, "Continuando registro...", Toast.LENGTH_SHORT).show()
        }

        }
}