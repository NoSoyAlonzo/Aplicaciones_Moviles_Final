package com.example.proyectoapp_moviles

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.proyectoapp_moviles.DAO.MascotaDAO
import com.example.proyectoapp_moviles.model.Mascota
import com.google.firebase.auth.FirebaseAuth

class AgregarMascota : AppCompatActivity() {

    private val mascotaDAO = MascotaDAO()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.agregar_mascota)

        // Botón regresar
        findViewById<ImageButton>(R.id.btnBack)
            .setOnClickListener {
                finish()
            }

        // Campos
        val eNombre =
            findViewById<EditText>(R.id.e_nombre_mascota)

        val eEspecie =
            findViewById<AutoCompleteTextView>(R.id.e_especie_mascota)

        val eRaza =
            findViewById<EditText>(R.id.txtRaza)

        val eHistorial =
            findViewById<EditText>(R.id.e_historial_medico)

        val eDieta =
            findViewById<EditText>(R.id.txtDieta)

        val eInstrucciones =
            findViewById<EditText>(R.id.e_instrucciones)

        // Radio buttons
        val r2Veces =
            findViewById<RadioButton>(R.id.r_2veces)

        val r3Veces =
            findViewById<RadioButton>(R.id.r_3veces)

        val rLibre =
            findViewById<RadioButton>(R.id.r_libre)

        // Lista de especies
        val especies = arrayOf(
            "Perro",
            "Gato",
            "Ave",
            "Conejo",
            "Hamster",
            "Tortuga",
            "Pez",
            "Otro"
        )

        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_dropdown_item_1line,
            especies
        )

        eEspecie.setAdapter(adapter)

        // Botón guardar
        val btnAgregarMascota =
            findViewById<Button>(R.id.btnRegistroMascota)

        btnAgregarMascota.setOnClickListener {

            val uid =
                FirebaseAuth.getInstance()
                    .currentUser?.uid ?: ""

            // Obtener frecuencia
            val frecuencia = when {
                r2Veces.isChecked -> "2 veces"
                r3Veces.isChecked -> "3 veces"
                rLibre.isChecked -> "Libre"
                else -> ""
            }

            // Validaciones
            if (eNombre.text.toString().isEmpty()) {

                Toast.makeText(
                    this,
                    "Ingresa el nombre",
                    Toast.LENGTH_SHORT
                ).show()

                return@setOnClickListener
            }

            if (eEspecie.text.toString().isEmpty()) {

                Toast.makeText(
                    this,
                    "Selecciona la especie",
                    Toast.LENGTH_SHORT
                ).show()

                return@setOnClickListener
            }

            // Crear mascota
            val mascota = Mascota(
                id = "",
                uidUsuario = uid,
                nombre = eNombre.text.toString(),
                especie = eEspecie.text.toString(),
                raza = eRaza.text.toString(),
                historial_medico = eHistorial.text.toString(),
                instrucciones_salud =
                    eInstrucciones.text.toString(),

                dieta =
                    eDieta.text.toString() +
                            " | Frecuencia: $frecuencia"
            )

            // Guardar
            mascotaDAO.agregar(mascota) { success ->

                if (success) {

                    Toast.makeText(
                        this,
                        "Mascota agregada correctamente",
                        Toast.LENGTH_SHORT
                    ).show()

                    finish()

                } else {

                    Toast.makeText(
                        this,
                        "Error al guardar mascota",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}