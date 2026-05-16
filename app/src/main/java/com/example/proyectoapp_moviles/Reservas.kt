package com.example.proyectoapp_moviles

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectoapp_moviles.DAO.MascotaDAO
import com.example.proyectoapp_moviles.DAO.ReservaDAO
import com.example.proyectoapp_moviles.model.Mascota
import com.example.proyectoapp_moviles.model.Reserva
import com.google.firebase.auth.FirebaseAuth
import java.text.SimpleDateFormat
import java.util.*

class Reservas : AppCompatActivity() {

    private val mascotaDAO = MascotaDAO()
    private val reservaDAO = ReservaDAO()

    private var listaMascotas = mutableListOf<Mascota>()
    private var mascotaSeleccionada: Mascota? = null

    private var recycler: RecyclerView? = null

    private var fechaInicio: String = ""
    private var fechaFin: String = ""

    private var uidCuidador: String = ""

    private var txtFechasResumen: TextView? = null
    private var txtMascotaResumen: TextView? = null
    private var txtTotal: TextView? = null

    private var precioCuidador: Double = 0.0

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
        findViewById<LinearLayout?>(R.id.explorar)?.setOnClickListener {
            startActivity(Intent(this, Explorar::class.java))
        }

        findViewById<LinearLayout?>(R.id.mis_mascotas)?.setOnClickListener {
            startActivity(Intent(this, ScreenHome::class.java))
        }

        findViewById<LinearLayout?>(R.id.mi_perfil)?.setOnClickListener {
            startActivity(Intent(this, PerfilActivity::class.java))
        }
    }

    // ---------------- MODO CREAR ----------------
    private fun iniciarModoCrear(nombre: String, precio: String) {

        txtFechasResumen = findViewById(R.id.txtFechasResumen)
        txtMascotaResumen = findViewById(R.id.txtMascotaResumen)
        txtTotal = findViewById(R.id.txtTotal)

        val txtNombre = findViewById<TextView>(R.id.txtNombreCuidador)
        val txtPrecio = findViewById<TextView>(R.id.txtPrecioCuidador)

        val btnSeleccionar = findViewById<Button>(R.id.btnSeleccionarFecha)
        val btnConfirmar = findViewById<Button>(R.id.btnConfirmar)

        val txtFechasSeleccion = findViewById<TextView>(R.id.txtFechasSeleccion)
        val spinnerMascotas = findViewById<Spinner>(R.id.spinnerMascotas)

        precioCuidador = precio.toDouble()

        txtNombre.text = nombre
        txtPrecio.text = "$$precio / noche"

        cargarMascotas(spinnerMascotas)

        btnSeleccionar.setOnClickListener {
            seleccionarFechas { inicio, fin ->
                fechaInicio = inicio
                fechaFin = fin

                txtFechasSeleccion.text = "$inicio - $fin"
                txtFechasResumen?.text = "$inicio - $fin"

                actualizarResumen()
            }
        }
        iniciarModoLista()

        btnConfirmar.setOnClickListener {
            guardarReserva(nombre, precio)
        }
    }

    private fun iniciarModoLista() {

        recycler = findViewById(R.id.recyclerReservas)
        recycler?.layoutManager = LinearLayoutManager(this)

        val uid = FirebaseAuth.getInstance().currentUser?.uid ?: return

        reservaDAO.obtenerReservasPorUsuarioOCuidador(uid) { lista ->

            runOnUiThread {

                recycler?.adapter =
                    ReservaAdapter(lista.toMutableList()) { id, estado ->

                        reservaDAO.actualizarEstado(id, estado) {

                            Toast.makeText(
                                this,
                                "Reserva $estado",
                                Toast.LENGTH_SHORT
                            ).show()

                            iniciarModoLista()
                        }
                    }
            }
        }
    }

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
            Toast.makeText(this, "Cuidador inválido", Toast.LENGTH_SHORT).show()
            return
        }

        val dias = calcularDias()
        val total = dias * precioCuidador

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

        Toast.makeText(this, "Reserva creada por $$total", Toast.LENGTH_LONG).show()

        finish()
    }

    private fun seleccionarFechas(callback: (String, String) -> Unit) {

        val calendar = Calendar.getInstance()

        DatePickerDialog(this, { _, y1, m1, d1 ->

            val inicio = "$d1/${m1 + 1}/$y1"

            DatePickerDialog(this, { _, y2, m2, d2 ->

                val fin = "$d2/${m2 + 1}/$y2"
                callback(inicio, fin)

            }, calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()

        }, calendar.get(Calendar.YEAR),
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
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    mascotaSeleccionada = lista[position]

                    txtMascotaResumen?.text =
                        "Mascota: ${mascotaSeleccionada?.nombre}"

                    actualizarResumen()
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
        }
    }

    private fun calcularDias(): Int {

        val format = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

        val inicio = format.parse(fechaInicio) ?: return 0
        val fin = format.parse(fechaFin) ?: return 0

        val diff = fin.time - inicio.time

        return (diff / (1000 * 60 * 60 * 24)).toInt() + 1
    }

    private fun actualizarResumen() {

        if (fechaInicio.isEmpty() || fechaFin.isEmpty()) {
            txtTotal?.text = "Total: $0"
            return
        }

        val dias = calcularDias()

        if (dias <= 0) {
            txtTotal?.text = "Total: $0"
            return
        }

        val total = dias * precioCuidador

        txtTotal?.text = "Total: $${"%.2f".format(total)}"
    }


}