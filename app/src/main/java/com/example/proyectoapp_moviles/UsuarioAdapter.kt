package com.example.proyectoapp_moviles

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectoapp_moviles.R
import com.example.proyectoapp_moviles.model.Usuario
import org.w3c.dom.Text

class UsuarioAdapter(
    private val lista: List<Usuario>,
    private val onEstadoClick: (Usuario) -> Unit,
    private val onEditarClick: (Usuario) -> Unit
) : RecyclerView.Adapter<UsuarioAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val btnEditar: ImageButton = view.findViewById(R.id.btnEditar)
        val txtNombre: TextView = view.findViewById(R.id.txtNombre)
        val txtCorreo: TextView = view.findViewById(R.id.txtCorreo)
        val txtTelefono: TextView = view.findViewById(R.id.txtTelefono)
        val txtPassword: TextView = view.findViewById<TextView>(R.id.txtPassword)

        val btnEstado: Button = view.findViewById(R.id.btnEstado)

        val card: CardView = view.findViewById(R.id.cardUsuario)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_usuario, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int = lista.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val usuario = lista[position]

        holder.txtNombre.text = usuario.nombre
        holder.txtCorreo.text = usuario.e_mail
        holder.txtTelefono.text = usuario.noCelular
        holder.txtPassword.text = usuario.password

        if (usuario.activo) {

            holder.btnEstado.text = "Desactivar"

            holder.btnEstado.setBackgroundColor(
                Color.parseColor("#BA1A1A")
            )

            holder.card.setCardBackgroundColor(
                Color.parseColor("#F2F4F4")
            )

        } else {

            holder.btnEstado.text = "Activar"

            holder.btnEstado.setBackgroundColor(
                Color.parseColor("#2E7D32")
            )

            holder.card.setCardBackgroundColor(
                Color.parseColor("#D7D7D7")
            )
        }

        holder.btnEstado.setOnClickListener {

            onEstadoClick(usuario)
        }

        holder.btnEditar.setOnClickListener {

            onEditarClick(usuario)
        }
    }
}