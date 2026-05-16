package com.example.proyectoapp_moviles

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectoapp_moviles.model.Reserva
import com.google.firebase.auth.FirebaseAuth

class ReservaAdapter(
    private val lista: List<Reserva>,
    private val onActualizarEstado: (String, String) -> Unit
) : RecyclerView.Adapter<ReservaAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val txtCuidador: TextView = view.findViewById(R.id.txtCuidador)
        val txtMascota: TextView = view.findViewById(R.id.txtMascota)
        val txtFechas: TextView = view.findViewById(R.id.txtFechas)
        val txtTotal: TextView = view.findViewById(R.id.txtTotal)
        val txtEstado: TextView = view.findViewById(R.id.txtEstado)

        val layoutAcciones: View = view.findViewById(R.id.layoutAcciones)
        val btnAceptar: View = view.findViewById(R.id.btnAceptar)
        val btnRechazar: View = view.findViewById(R.id.btnRechazar)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_reserva, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = lista.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val reserva = lista[position]
        val uidActual = FirebaseAuth.getInstance().currentUser?.uid

        val esCuidador = reserva.uidCuidador == uidActual
        val esPendiente = reserva.estado == "Pendiente"

        holder.txtCuidador.text = reserva.nombreCuidador
        holder.txtMascota.text = "Mascota: ${reserva.nombreMascota}"
        holder.txtFechas.text = "${reserva.fechaInicio} - ${reserva.fechaFin}"
        holder.txtTotal.text = "Total: $${reserva.precio}"
        holder.txtEstado.text = "Estado: ${reserva.estado}"

        // 🔥 SOLO CUIDADOR VE BOTONES Y SOLO SI ESTÁ PENDIENTE
        if (esCuidador && esPendiente) {
            holder.layoutAcciones.visibility = View.VISIBLE
        } else {
            holder.layoutAcciones.visibility = View.GONE
        }

        holder.btnAceptar.setOnClickListener {
            onActualizarEstado(reserva.id, "Aceptada")
        }

        holder.btnRechazar.setOnClickListener {
            onActualizarEstado(reserva.id, "Rechazada")
        }
    }
}