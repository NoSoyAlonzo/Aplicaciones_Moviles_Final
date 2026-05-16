package com.example.proyectoapp_moviles

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.proyectoapp_moviles.DAO.CuidadorDAO
import com.example.proyectoapp_moviles.model.Cuidador
import com.google.firebase.auth.FirebaseAuth

class RegistroCuidador : AppCompatActivity() {

    private val cuidadorDAO = CuidadorDAO()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.registro_cuidador)

        // BOTON REGRESAR
        findViewById<ImageButton>(R.id.btnBack)
            .setOnClickListener {
                finish()
            }

        // CAMPOS
        val nombre =
            findViewById<EditText>(R.id.inputNombre)

        val desc =
            findViewById<EditText>(R.id.inputDesc)

        val spinner =
            findViewById<Spinner>(R.id.spinnerExp)

        val checkPaseo =
            findViewById<CheckBox>(R.id.checkPaseo)

        val checkHospedaje =
            findViewById<CheckBox>(R.id.checkHospedaje)

        val checkCuidado =
            findViewById<CheckBox>(R.id.checkCuidado)

        val btnContinuar =
            findViewById<Button>(R.id.btnContinuar)

        val precio =
            findViewById<EditText>(R.id.inputPrecio)

        // OPCIONES SPINNER
        val opciones = arrayOf(
            "1-2 años",
            "3-5 años",
            "5+ años"
        )

        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            opciones
        )

        adapter.setDropDownViewResource(
            android.R.layout.simple_spinner_dropdown_item
        )

        spinner.adapter = adapter

        // BOTON REGISTRAR
        btnContinuar.setOnClickListener {

            // VALIDACIONES
            if (nombre.text.toString().trim().isEmpty()) {

                Toast.makeText(
                    this,
                    "Ingresa tu nombre",
                    Toast.LENGTH_SHORT
                ).show()

                return@setOnClickListener
            }


            if (precio.text.toString().trim().isEmpty()) {

                Toast.makeText(
                    this,
                    "Ingresa un precio",
                    Toast.LENGTH_SHORT
                ).show()

                return@setOnClickListener
            }

            if (desc.text.toString().trim().isEmpty()) {

                Toast.makeText(
                    this,
                    "Ingresa una descripción",
                    Toast.LENGTH_SHORT
                ).show()

                return@setOnClickListener
            }

            val paseos = checkPaseo.isChecked

            val hospedaje = checkHospedaje.isChecked

            val cuidado = checkCuidado.isChecked

            val uid =
                FirebaseAuth.getInstance()
                    .currentUser?.uid ?: ""

            val cuidador = Cuidador(
                id = "",
                uidUsuario = uid,
                nCompleto = nombre.text.toString(),
                aniosExp = spinner.selectedItem.toString(),
                descripcion = desc.text.toString(),

                paseos = paseos,
                hospedaje = hospedaje,
                ciudado_diario = cuidado,

                precio = precio.text.toString(),

                aceptado = false
            )

            cuidadorDAO.agregar(cuidador)

            AlertDialog.Builder(this)
                .setTitle("Solicitud enviada")
                .setMessage(
                    "Tu solicitud para ser cuidador fue enviada correctamente.\n\n" +
                            "Un administrador revisará tu perfil antes de aprobarlo."
                )
                .setCancelable(false)
                .setPositiveButton("Aceptar") { _, _ ->

                    val intent =
                        Intent(
                            this,
                            Explorar::class.java
                        )

                    startActivity(intent)
                    finish()
                }
                .show()
        }
    }
}