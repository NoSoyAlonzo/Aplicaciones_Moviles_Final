package com.example.proyectoapp_moviles

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.card.MaterialCardView
import com.google.android.material.datepicker.MaterialDatePicker

class Reservas : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reservas)

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


        val card1 = findViewById<MaterialCardView>(R.id.card1)
        val card2 = findViewById<MaterialCardView>(R.id.card2)
        val card3 = findViewById<MaterialCardView>(R.id.card3)

        fun seleccionar(card: MaterialCardView) {
            listOf(card1, card2, card3).forEach {
                it.strokeWidth = 1
                it.setStrokeColor(getColor(R.color.primary))
            }

            card.strokeWidth = 3
            card.setStrokeColor(getColor(R.color.black))
        }

        card1.setOnClickListener { seleccionar(card1) }
        card2.setOnClickListener { seleccionar(card2) }
        card3.setOnClickListener { seleccionar(card3) }


        val btnFecha = findViewById<Button>(R.id.btnSeleccionarFecha)
        val txtFechas = findViewById<TextView>(R.id.txtFechas)

        btnFecha.setOnClickListener {

            val picker = MaterialDatePicker.Builder.dateRangePicker()
                .setTitleText("Selecciona fechas")
                .build()

            picker.show(supportFragmentManager, "DATE_PICKER")

            picker.addOnPositiveButtonClickListener { selection ->

                val inicio = selection.first
                val fin = selection.second

                val formato =
                    java.text.SimpleDateFormat("dd/MM/yyyy", java.util.Locale.getDefault())

                val fechaInicio = formato.format(java.util.Date(inicio!!))
                val fechaFin = formato.format(java.util.Date(fin!!))

                txtFechas.text = "$fechaInicio - $fechaFin"
            }
        }
    }
}