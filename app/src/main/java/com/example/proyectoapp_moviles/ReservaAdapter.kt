package com.example.proyectoapp_moviles

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectoapp_moviles.model.Reserva

class ReservaAdapter(
    private val lista: List<Reserva>
) : RecyclerView.Adapter<ReservaAdapter.ViewHolder>() {

    class ViewHolder(view: View)
        : RecyclerView.ViewHolder(view) {

        val txtCuidador =
            view.findViewById<TextView>(R.id.txtCuidador)

        val txtFechas =
            view.findViewById<TextView>(R.id.txtFechas)

        val txtTotal =
            view.findViewById<TextView>(R.id.txtTotal)

        val txtEstado =
            view.findViewById<TextView>(R.id.txtEstado)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {

        val view = LayoutInflater
            .from(parent.context)
            .inflate(
                R.layout.item_reserva,
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

        val reserva = lista[position]

        holder.txtCuidador.text =
            reserva.nombreCuidador

        holder.txtFechas.text =
            "${reserva.fechaInicio} - ${reserva.fechaFin}"

        holder.txtTotal.text =
            "Total: $${reserva.precio}"

        holder.txtEstado.text =
            "Estado: ${reserva.estado}"

    }

}