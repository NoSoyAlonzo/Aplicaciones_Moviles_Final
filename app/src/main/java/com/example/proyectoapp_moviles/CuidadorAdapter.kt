package com.example.proyectoapp_moviles

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectoapp_moviles.model.Cuidador

class CuidadorAdapter(
    private val lista: List<Cuidador>
) : RecyclerView.Adapter<CuidadorAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val btnContratar = view.findViewById<Button>(R.id.btnContratar)

        val txtNombre = view.findViewById<TextView>(R.id.txtNombre)
        val txtDescripcion = view.findViewById<TextView>(R.id.txtDescripcion)
        val txtPrecio = view.findViewById<TextView>(R.id.txtPrecio)
        val txtExperiencia = view.findViewById<TextView>(R.id.txtExperiencia)
        val txtHospedaje = view.findViewById<TextView>(R.id.txtHospedaje)
        val txtPaseos = view.findViewById<TextView>(R.id.txtPaseos)

        val imgCuidador = view.findViewById<ImageView>(R.id.imgCuidador)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_cuidador, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int = lista.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val cuidador = lista[position]

        holder.txtNombre.text = cuidador.nCompleto
        holder.txtDescripcion.text = cuidador.descripcion
        holder.txtExperiencia.text = "Experiencia: ${cuidador.aniosExp}"
        holder.txtPrecio.text = "$${cuidador.precio} / noche"

        holder.txtHospedaje.visibility =
            if (cuidador.hospedaje) View.VISIBLE else View.GONE

        holder.txtPaseos.visibility =
            if (cuidador.paseos) View.VISIBLE else View.GONE

        holder.btnContratar.setOnClickListener {

            val context = holder.itemView.context

            val intent = Intent(context, Reservas::class.java)

            intent.putExtra("uidCuidador", cuidador.uidUsuario)

            intent.putExtra("nombreCuidador", cuidador.nCompleto)
            intent.putExtra("precioCuidador", cuidador.precio)

            context.startActivity(intent)
        }
    }
}