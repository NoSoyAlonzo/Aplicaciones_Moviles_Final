package com.example.proyectoapp_moviles

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.proyectoapp_moviles.DAO.UsuarioDAO
import com.example.proyectoapp_moviles.model.Usuario
import com.google.firebase.auth.FirebaseAuth


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
        val txtFecha = findViewById<EditText>(R.id.fecha_nacimiento)
        val txtCelular = findViewById<EditText>(R.id.numero_celular)

        val btn = findViewById<Button?>(R.id.Registrar)
        //Radio button para la
        btn?.setOnClickListener {
            startActivity(Intent(this, ScreenHome::class.java))
        }

        btn.setOnClickListener {

            val nombre = txtNombre.text.toString().trim()
            val email = txtEmail.text.toString().trim()
            val password = txtPassword.text.toString().trim()
            val fecha = txtFecha.text.toString().trim()
            val celular = txtCelular.text.toString().trim()

            if (
                nombre.isEmpty() ||
                email.isEmpty() ||
                password.isEmpty() ||
                fecha.isEmpty() ||
                celular.isEmpty()
            ) {

                Toast.makeText(
                    this,
                    "Completa todos los campos",
                    Toast.LENGTH_SHORT
                ).show()

                return@setOnClickListener
            }

            // CREAR USUARIO EN AUTHENTICATION
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->

                    if (task.isSuccessful) {

                        val usuario = Usuario(
                            nombre = nombre,
                            e_mail = email,
                            password = password,
                            fechaN = fecha,
                            noCelular = celular
                        )

                        // GUARDAR EN REALTIME DATABASE
                        UsuarioD.agregar(usuario) { exitoso ->

                            if (exitoso) {

                                Toast.makeText(
                                    this,
                                    "Usuario registrado",
                                    Toast.LENGTH_SHORT
                                ).show()

                                val intent = Intent(this, ScreenHome::class.java)
                                startActivity(intent)
                                finish()

                            } else {

                                Toast.makeText(
                                    this,
                                    "Error guardando datos",
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