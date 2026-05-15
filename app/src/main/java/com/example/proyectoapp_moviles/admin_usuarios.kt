package com.example.proyectoapp_moviles

import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.proyectoapp_moviles.DAO.UsuarioDAO
import android.app.AlertDialog
import android.widget.EditText
import com.example.proyectoapp_moviles.model.Usuario


class admin_usuarios : AppCompatActivity() {

    private lateinit var recycler: RecyclerView
    private val usuarioDAO = UsuarioDAO()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_usuarios)

        recycler = findViewById(R.id.recyclerUsuarios)

        recycler.layoutManager = LinearLayoutManager(this)

        cargarUsuarios()

        val btnCuidadores = findViewById<Button>(R.id.btnCuidadores)

        btnCuidadores.setOnClickListener {

            startActivity(Intent(this, admin_cuidadores::class.java))
        }
    }

    private fun cargarUsuarios() {

        usuarioDAO.obtenerTodosUsuarios { lista ->

            val adapter = UsuarioAdapter(

                lista,

                { usuario ->

                    if (usuario.activo) {

                        usuarioDAO.desactivar(usuario.id)

                        Toast.makeText(
                            this,
                            "Usuario desactivado",
                            Toast.LENGTH_SHORT
                        ).show()

                    } else {

                        usuarioDAO.activar(usuario.id)

                        Toast.makeText(
                            this,
                            "Usuario activado",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    cargarUsuarios()
                },

                { usuario ->

                    mostrarDialogoEditar(usuario)
                }
            )

            recycler.adapter = adapter
        }
    }

    private fun mostrarDialogoEditar(usuario: Usuario) {

        val editText = EditText(this)

        editText.hint = "Nueva contraseña"

        AlertDialog.Builder(this)
            .setTitle("Cambiar contraseña")
            .setView(editText)

            .setPositiveButton("Guardar") { _, _ ->

                val nuevaPassword = editText.text.toString()

                usuario.password = nuevaPassword

                usuarioDAO.actualizar(usuario)

                Toast.makeText(
                    this,
                    "Contraseña actualizada",
                    Toast.LENGTH_SHORT
                ).show()

                cargarUsuarios()
            }

            .setNegativeButton("Cancelar", null)

            .show()
    }
}