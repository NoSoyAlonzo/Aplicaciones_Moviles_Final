package com.example.proyectoapp_moviles

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.proyectoapp_moviles.DAO.UsuarioDAO
import com.example.proyectoapp_moviles.model.Usuario


class registro_form : AppCompatActivity() {

    private val UsuarioD= UsuarioDAO()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.registro_form)


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

            val usuario = Usuario(

                nombre = txtNombre.text.toString(),
                e_mail = txtEmail.text.toString(),
                password = txtPassword.text.toString(),
                fechaN = txtFecha.text.toString(),
                noCelular = txtCelular.text.toString()
            )

            UsuarioD.agregar(usuario)
        }

    }

}