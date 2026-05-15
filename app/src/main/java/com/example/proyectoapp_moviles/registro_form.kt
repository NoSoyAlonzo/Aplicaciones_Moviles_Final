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
                            fechaN = txtFecha.text.toString(),
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