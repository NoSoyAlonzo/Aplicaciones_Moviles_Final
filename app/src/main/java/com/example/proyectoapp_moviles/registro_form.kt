package com.example.proyectoapp_moviles

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.proyectoapp_moviles.DAO.UsuarioDAO
import com.example.proyectoapp_moviles.model.Usuario
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import java.util.Calendar

class registro_form : AppCompatActivity() {

    private val UsuarioD= UsuarioDAO()
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.registro_form)
        auth = FirebaseAuth.getInstance()



        //campoos de registro Usuario
        val txtNombre = findViewById<EditText>(R.id.nombre_registro)
        val txtEmail = findViewById<EditText>(R.id.correo_electronico)
        val txtPassword = findViewById<EditText>(R.id.contrasena)

        val fechaNacimiento =
            findViewById<TextInputEditText>(R.id.fecha_nacimiento)

        fechaNacimiento.setOnClickListener {

            val calendar = Calendar.getInstance()

            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePicker = DatePickerDialog(
                this,
                { _, selectedYear, selectedMonth, selectedDay ->

                    val fecha =
                        String.format(
                            "%02d/%02d/%04d",
                            selectedDay,
                            selectedMonth + 1,
                            selectedYear
                        )

                    fechaNacimiento.setText(fecha)

                },
                year,
                month,
                day
            )

            datePicker.show()
        }

        val txtCelular = findViewById<EditText>(R.id.numero_celular)

        val btn = findViewById<Button?>(R.id.Registrar)
        //Radio button para la
        btn?.setOnClickListener {
            startActivity(Intent(this, ScreenHome::class.java))
        }

        btn.setOnClickListener {

            val correo = txtEmail.text.toString().trim()
            val password = txtPassword.text.toString().trim()

            auth.createUserWithEmailAndPassword(correo, password)
                .addOnCompleteListener(this) { task ->

                    if (task.isSuccessful) {

                        val uid = auth.currentUser?.uid ?: ""

                        val usuario = Usuario(
                            uidAuth = uid,
                            nombre = txtNombre.text.toString(),
                            e_mail = correo,
                            password = password,
                            fechaN = fechaNacimiento.text.toString(),
                            noCelular = txtCelular.text.toString()
                        )

                        UsuarioD.agregar(usuario) { success ->

                            if (success) {

                                Toast.makeText(
                                    this,
                                    "Usuario registrado",
                                    Toast.LENGTH_SHORT
                                ).show()

                                startActivity(
                                    Intent(this, ScreenHome::class.java)
                                )

                                finish()

                            } else {

                                Toast.makeText(
                                    this,
                                    "Error guardando usuario",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }

                    } else {

                        Toast.makeText(
                            this,
                            "Error: ${task.exception?.message}",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
        }

    }

}