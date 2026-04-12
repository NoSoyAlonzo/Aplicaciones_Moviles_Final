package com.example.proyectoapp_moviles.ui_components

import android.app.DatePickerDialog
import android.content.Context
import android.icu.util.Calendar
import android.widget.EditText

class date_picker (  private val context: Context){

    fun datep_pop(editText: EditText, onDate_selected:(Long)-> Unit){

        editText.setOnClickListener {

            val calendarioN= Calendar.getInstance()

            val dialog= DatePickerDialog(
                context,
                {_,year,month,day ->

                    val fechaTextoFormato="$day/${month+1}/$year"
                    val calendar = Calendar.getInstance().apply {
                        set(year,month,day)
                    }
                    val timestamp=calendar.timeInMillis
                    editText.setText(fechaTextoFormato)
                    onDate_selected(timestamp)
                },
                calendarioN.get(Calendar.YEAR),
                calendarioN.get(Calendar.MONTH),
                calendarioN.get(Calendar.DAY_OF_MONTH)
            )

            dialog.show()
        }
    }

}