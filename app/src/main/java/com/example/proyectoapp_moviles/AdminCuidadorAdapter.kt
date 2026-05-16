package com.example.proyectoapp_moviles

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectoapp_moviles.model.Cuidador

class AdminCuidadorAdapter(
    private var lista: List<Cuidador>,
    private val onAceptar: (Cuidador) -> Unit,
    private val onRechazar: (Cuidador) -> Unit
) : RecyclerView.Adapter<AdminCuidadorAdapter.ViewHolder>() {

    class ViewHolder(view: View)
        : RecyclerView.ViewHolder(view) {

        val txtNombre =
            view.findViewById<TextView>(R.id.txtNombre)

        val txtExperiencia =
            view.findViewById<TextView>(R.id.txtExperiencia)

        val txtDescripcion =
            view.findViewById<TextView>(R.id.txtDescripcion)

        val txtServicios =
            view.findViewById<TextView>(R.id.txtServicios)

        val txtPrecio =
            view.findViewById<TextView>(R.id.txtPrecio)

        val btnAceptar =
            view.findViewById<Button>(R.id.btnAceptar)

        val btnRechazar =
            view.findViewById<Button>(R.id.btnRechazar)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {

        val view = LayoutInflater
            .from(parent.context)
            .inflate(
                R.layout.item_admin_cuidador,
                parent,
                false
            )

        return ViewHolder(view)
    }

    override fun getItemCount(): Int =
        lista.size

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {

        val cuidador = lista[position]

        holder.txtNombre.text =
            cuidador.nCompleto

        holder.txtExperiencia.text =
            "Experiencia: ${cuidador.aniosExp}"

        holder.txtDescripcion.text =
            cuidador.descripcion

        val servicios =
            mutableListOf<String>()

        if (cuidador.paseos)
            servicios.add("Paseos")

        if (cuidador.hospedaje)
            servicios.add("Hospedaje")

        if (cuidador.ciudado_diario)
            servicios.add("Cuidado diario")

        holder.txtServicios.text =
            servicios.joinToString(" • ")

        holder.txtPrecio.text =
            "$${cuidador.precio} MXN"

        holder.btnAceptar.setOnClickListener {

            onAceptar(cuidador)
        }

        holder.btnRechazar.setOnClickListener {

            onRechazar(cuidador)
        }
    }
}