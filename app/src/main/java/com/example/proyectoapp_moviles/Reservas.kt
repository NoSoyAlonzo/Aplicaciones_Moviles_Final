package com.example.proyectoapp_moviles

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectoapp_moviles.DAO.MascotaDAO
import com.example.proyectoapp_moviles.DAO.ReservaDAO
import com.example.proyectoapp_moviles.model.Reserva
import com.google.firebase.auth.FirebaseAuth
import java.util.Calendar
import com.example.proyectoapp_moviles.model.Mascota
import java.text.SimpleDateFormat
import java.util.*

class Reservas : AppCompatActivity() {

    private val mascotaDAO = MascotaDAO()

    private var listaMascotas = mutableListOf<Mascota>()
    private var mascotaSeleccionada: Mascota? = null

    private var recycler: RecyclerView? = null
    private val reservaDAO = ReservaDAO()

    private var fechaInicio: String = ""
    private var fechaFin: String = ""

    private var uidCuidador: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val nombre = intent.getStringExtra("nombreCuidador")
        val precio = intent.getStringExtra("precioCuidador")
        uidCuidador = intent.getStringExtra("uidCuidador") ?: ""

        val modoCrear = nombre != null && precio != null && uidCuidador.isNotEmpty()

        if (modoCrear) {
            setContentView(R.layout.activity_reservas)
            iniciarModoCrear(nombre!!, precio!!)
        } else {
            setContentView(R.layout.activity_mis_reservas)
            iniciarModoLista()
        }

        setupFooter()
    }

    // ---------------- FOOTER ----------------
    private fun setupFooter() {
        val explorar = findViewById<LinearLayout?>(R.id.explorar)
        val misMascotas = findViewById<LinearLayout?>(R.id.mis_mascotas)
        val reservasBtn = findViewById<LinearLayout?>(R.id.reservas)
        val perfil = findViewById<LinearLayout?>(R.id.mi_perfil)

        explorar?.setOnClickListener {
            startActivity(Intent(this, Explorar::class.java))
        }

        misMascotas?.setOnClickListener {
            startActivity(Intent(this, ScreenHome::class.java))
        }

        reservasBtn?.setOnClickListener {
            // ya estás aquí
        }

        perfil?.setOnClickListener {
            startActivity(Intent(this, PerfilActivity::class.java))
        }
    }

    // ---------------- MODO CREAR ----------------
    private fun iniciarModoCrear(nombre: String, precio: String) {

        val spinnerMascotas = findViewById<Spinner>(R.id.spinnerMascotas)

        cargarMascotas(spinnerMascotas)
        val txtNombre = findViewById<TextView>(R.id.txtNombreCuidador)
        val txtPrecio = findViewById<TextView>(R.id.txtPrecioCuidador)
        val btnSeleccionar = findViewById<Button>(R.id.btnSeleccionarFecha)
        val btnConfirmar = findViewById<Button>(R.id.btnConfirmar)
        val txtFechas = findViewById<TextView>(R.id.txtFechas)

        txtNombre.text = nombre
        txtPrecio.text = "$$precio / noche"

        btnSeleccionar.setOnClickListener {
            seleccionarFechas { inicio, fin ->
                fechaInicio = inicio
                fechaFin = fin
                txtFechas.text = "$inicio - $fin"
            }
        }

        btnConfirmar.setOnClickListener {
            guardarReserva(nombre, precio)
        }
    }

    // ---------------- MODO LISTA ----------------
    private fun iniciarModoLista() {
        recycler = findViewById(R.id.recyclerReservas)
        recycler?.layoutManager = LinearLayoutManager(this)
        cargarMisReservas()
    }

    private fun cargarMisReservas() {
        val uid = FirebaseAuth.getInstance().currentUser?.uid ?: return

        reservaDAO.obtenerReservasUsuario(uid) { lista ->
            runOnUiThread {
                recycler?.adapter = ReservaAdapter(lista)
            }
        }
    }

    // ---------------- GUARDAR ----------------
    private fun guardarReserva(nombre: String, precio: String) {

        val uidUsuario = FirebaseAuth.getInstance().currentUser?.uid ?: return

        if (fechaInicio.isEmpty() || fechaFin.isEmpty()) {
            Toast.makeText(this, "Selecciona fechas primero", Toast.LENGTH_SHORT).show()
            return
        }

        val mascota = mascotaSeleccionada
        if (mascota == null) {
            Toast.makeText(this, "Selecciona una mascota", Toast.LENGTH_SHORT).show()
            return
        }

        if (uidCuidador.isEmpty()) {
            Toast.makeText(this, "Error: cuidador no válido", Toast.LENGTH_SHORT).show()
            return
        }

        val dias = calcularDias()
        val precioNoche = precio.toDouble()
        val total = precioNoche * dias

        val reserva = Reserva(
            uidUsuario = uidUsuario,
            uidCuidador = uidCuidador,
            nombreCuidador = nombre,

            idMascota = mascota.id,
            nombreMascota = mascota.nombre,

            fechaInicio = fechaInicio,
            fechaFin = fechaFin,

            precio = total.toString(),
            estado = "Pendiente"
        )

        reservaDAO.agregar(reserva)

        Toast.makeText(
            this,
            "Reserva creada por $$total",
            Toast.LENGTH_LONG
        ).show()

        finish()
    }

    // ---------------- DATE PICKER ----------------
    private fun seleccionarFechas(callback: (String, String) -> Unit) {

        val calendar = Calendar.getInstance()

        DatePickerDialog(this,
            { _, y1, m1, d1 ->

                val inicio = "$d1/${m1 + 1}/$y1"

                DatePickerDialog(this,
                    { _, y2, m2, d2 ->
                        val fin = "$d2/${m2 + 1}/$y2"
                        callback(inicio, fin)
                    },
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
                ).show()

            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    private fun cargarMascotas(spinner: Spinner) {

        val uid = FirebaseAuth.getInstance().currentUser?.uid ?: return

        mascotaDAO.obtenerMascotasUsuario(uid) { lista ->

            listaMascotas = lista.toMutableList()

            val nombres = lista.map { it.nombre }

            val adapter = ArrayAdapter(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                nombres
            )

            spinner.adapter = adapter

            spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: android.view.View?,
                    position: Int,
                    id: Long
                ) {
                    mascotaSeleccionada = lista[position]
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
        }
    }

    private fun calcularDias(): Int {

        val format = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

        val inicio = format.parse(fechaInicio)
        val fin = format.parse(fechaFin)

        val diff = fin.time - inicio.time

        return (diff / (1000 * 60 * 60 * 24)).toInt() + 1
    }
}