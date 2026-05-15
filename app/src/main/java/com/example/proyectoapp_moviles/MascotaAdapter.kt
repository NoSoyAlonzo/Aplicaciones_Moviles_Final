package com.example.proyectoapp_moviles

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectoapp_moviles.model.Mascota

class MascotaAdapter(
    private val lista: List<Mascota>
) : RecyclerView.Adapter<MascotaAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val txtNombre =
            view.findViewById<TextView>(R.id.txtNombreMascota)

        val txtEspecie =
            view.findViewById<TextView>(R.id.txtEspecie)

        val txtRaza =
            view.findViewById<TextView>(R.id.txtRaza)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_mascota, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int = lista.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val mascota = lista[position]

        holder.txtNombre.text = mascota.nombre
        holder.txtEspecie.text = mascota.especie
        holder.txtRaza.text = mascota.raza
    }
}