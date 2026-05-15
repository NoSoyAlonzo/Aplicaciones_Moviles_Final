package com.example.proyectoapp_moviles

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.proyectoapp_moviles.model.Usuario
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = FirebaseAuth.getInstance()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(
                systemBars.left,
                systemBars.top,
                systemBars.right,
                systemBars.bottom
            )
            insets
        }

        val etCorreo = findViewById<EditText>(R.id.etCorreo)
        val etPassword = findViewById<EditText>(R.id.etPassword)

        val btnInicio = findViewById<Button>(R.id.btnInicio)
        val btnRegistro = findViewById<Button>(R.id.btnRegistro)

        btnRegistro.setOnClickListener {

            val intent = Intent(this, registro_form::class.java)
            startActivity(intent)
        }

        btnInicio.setOnClickListener {

            val correo = etCorreo.text.toString().trim()
            val password = etPassword.text.toString().trim()

            if (correo.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            auth.signInWithEmailAndPassword(correo, password)
                .addOnCompleteListener(this) { task ->

                    if (task.isSuccessful) {

                        val uid = auth.currentUser?.uid ?: ""

                        val dbRef = FirebaseDatabase
                            .getInstance()
                            .getReference("usuarios")

                        dbRef.get().addOnSuccessListener { snapshot ->

                            var usuarioActivo = true

                            for (child in snapshot.children) {

                                val usuario = child.getValue(Usuario::class.java)

                                if (usuario?.uidAuth == uid) {

                                    usuarioActivo = usuario.activo
                                    break
                                }
                            }

                            if (!usuarioActivo) {

                                FirebaseAuth.getInstance().signOut()

                                Toast.makeText(
                                    this,
                                    "Cuenta desactivada",
                                    Toast.LENGTH_LONG
                                ).show()

                                return@addOnSuccessListener
                            }

                            // ADMIN
                            if (correo == "admin@gmail.com" &&
                                password == "admin123") {

                                startActivity(
                                    Intent(this, admin_usuarios::class.java)
                                )

                            } else {

                                startActivity(
                                    Intent(this, ScreenHome::class.java)
                                )
                            }

                            finish()
                        }

                    } else {

                        Toast.makeText(
                            this,
                            "Correo o contraseña incorrectos",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
        }
    }
}