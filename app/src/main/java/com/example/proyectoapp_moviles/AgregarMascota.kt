package com.example.proyectoapp_moviles

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.proyectoapp_moviles.model.Mascota

class AgregarMascota : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.agregar_mascota)

        findViewById<ImageButton>(R.id.btnBack).setOnClickListener {
            finish()
        }
        //campos de registro de la mascota
        val e_nombre_mascota= findViewById<EditText>(R.id.e_nombre_mascota)
        val e_especie_mascota=findViewById<EditText>(R.id.e_especie_mascota)
        val e_historial_medico=findViewById<EditText>(R.id.e_historial_medico)
        val e_dieta_mascota=findViewById<EditText>(R.id.e_dietaMascota)
        //variable del boton de registro
        val btnAgregarMascota=findViewById<Button>(R.id.btnRegistroMascota)

        btnAgregarMascota.setOnClickListener {

            val mascota= Mascota(

                nombre = e_nombre_mascota.text.toString(),
                raza=e_especie_mascota.text.toString(),
                historial=e_historial_medico.text.toString(),
                dieta=e_dieta_mascota.text.toString()
            )
        }
    }


}