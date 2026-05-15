package com.example.proyectoapp_moviles

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class admin_usuarios : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_usuarios)

        val btnCaregivers = findViewById<Button>(R.id.btnCuidadores)

        btnCaregivers.setOnClickListener {

            val intent = Intent(this, admin_cuidadores::class.java)
            startActivity(intent)

        }
    }
}