package ittepic.edu.ladm_u3_p1_edgar_gerardo_rojas_medina_mapeo_empresas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import ittepic.edu.ladm_u3_p1_edgar_gerardo_rojas_medina_mapeo_empresas.databinding.ActivityMain2Binding

class MainActivity2 : AppCompatActivity() {
    lateinit var binding: ActivityMain2Binding
    var idArea = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        idArea = this.intent.extras!!.getString("idArea")!!
        val area = Area(this).mostrarUno(idArea.toInt())

        binding.descripcion.setText(area.descripcion)
        binding.division.setText(area.division)
        binding.numeroEmpleados.setText(area.cantidadEmpleados)

        binding.actualizar.setOnClickListener {
            var area = Area(this)
            area.idArea = idArea.toInt()
            area.descripcion = binding.descripcion.text.toString()
            area.division = binding.division.text.toString()
            area.cantidadEmpleados = binding.numeroEmpleados.text.toString().toInt()

            val respuesta = area.actualizar()

            if(respuesta){
                Toast.makeText(this,"Se Actualizo correctamente", Toast.LENGTH_LONG)
                    .show()
                binding.descripcion.setText("")
                binding.division.setText("")
                binding.numeroEmpleados.setText("")
            } else {
                AlertDialog.Builder(this)
                    .setTitle("Atencion")
                    .setMessage("ERROR NO SE ACTUALIZO")
            }
        }
        binding.regresar.setOnClickListener {
            finish()
        }
    }
}