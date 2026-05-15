package com.example.proyectoapp_moviles

import android.widget.Button
import android.widget.TextView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectoapp_moviles.model.Mascota

class MascotaAdapter(
    private val lista: List<Mascota>,
    private val onEliminar: (Mascota) -> Unit,
    private val onEditar: (Mascota) -> Unit
) : RecyclerView.Adapter<MascotaAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val txtNombre =
            view.findViewById<TextView>(R.id.txtNombreMascota)

        val txtEspecie =
            view.findViewById<TextView>(R.id.txtEspecie)

        val txtRaza =
            view.findViewById<TextView>(R.id.txtRaza)

        val txtDieta =
            view.findViewById<TextView>(R.id.txtDieta)

        val txtHistorial =
            view.findViewById<TextView>(R.id.txtHistorial)

        val txtInstrucciones =
            view.findViewById<TextView>(R.id.txtInstrucciones)

        val btnEliminar =
            view.findViewById<Button>(R.id.btnEliminar)

        val btnEditar =
            view.findViewById<Button>(R.id.btnEditar)
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
        holder.txtDieta.text = mascota.dieta
        holder.txtHistorial.text = mascota.historial_medico
        holder.txtInstrucciones.text =
            mascota.instrucciones_salud

        holder.btnEliminar.setOnClickListener {
            onEliminar(mascota)
        }

        holder.btnEditar.setOnClickListener {
            onEditar(mascota)
        }
    }
}