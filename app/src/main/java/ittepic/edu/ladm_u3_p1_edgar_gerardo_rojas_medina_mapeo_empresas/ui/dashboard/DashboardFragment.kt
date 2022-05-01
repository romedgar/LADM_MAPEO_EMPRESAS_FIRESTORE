package ittepic.edu.ladm_u3_p1_edgar_gerardo_rojas_medina_mapeo_empresas.ui.dashboard

import android.R
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import ittepic.edu.ladm_u3_p1_edgar_gerardo_rojas_medina_mapeo_empresas.Area
import ittepic.edu.ladm_u3_p1_edgar_gerardo_rojas_medina_mapeo_empresas.MainActivity2
import ittepic.edu.ladm_u3_p1_edgar_gerardo_rojas_medina_mapeo_empresas.databinding.FragmentDashboardBinding
import java.util.ArrayList

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    var listaIDs = ArrayList<String>()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    var idArea = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel =
            ViewModelProvider(this).get(DashboardViewModel::class.java)

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root
        mostrarDatosEnListView()


        dashboardViewModel.text.observe(viewLifecycleOwner) {
        }

        return root
    }

    fun mostrarDatosEnListView(){
        var listaAreas = Area(this.requireContext()).mostrarTodos()
        var descripcionAreas = ArrayList<String>()

        listaIDs.clear()
        (0..listaAreas.size-1).forEach{
            val ar = listaAreas.get(it)
            descripcionAreas.add(ar.descripcion)
            listaIDs.add(ar.idArea.toString())
        }

        binding.lista.adapter = ArrayAdapter<String>(this.requireContext(), R.layout.simple_list_item_1,descripcionAreas)
        binding.lista.setOnItemClickListener { adapterView, view, indice, l ->
            val idAereaLista = listaIDs.get(indice)
            val area = Area(this.requireContext()).mostrarUno(idAereaLista.toInt())

            AlertDialog.Builder(this.requireContext())
                .setTitle("Atención")
                .setMessage("Qué deseas hacer con ${area.descripcion},\n División: ${area.division}?")
                .setNegativeButton("Eliminar"){d,i->
                    area.eliminar()
                    mostrarDatosEnListView()
                }
                .setPositiveButton("Actualizar"){d,i->
                    binding.descripcion.setText(area.descripcion)
                    binding.division.setText(area.division)
                    binding.noPersonas.setText(area.cantidadEmpleados.toString())
                    binding.actualizar.isEnabled = true
                    idArea = area.idArea
                }
                .setNeutralButton("Cerrar"){d,i->}
                .show()
        }
        binding.actualizar.setOnClickListener {
            var area = Area(this.requireContext())
            area.idArea = idArea.toInt()
            area.descripcion = binding.descripcion.text.toString()
            area.division = binding.division.text.toString()
            area.cantidadEmpleados = binding.noPersonas.text.toString().toInt()

            val respuesta = area.actualizar()

            if(respuesta){
                Toast.makeText(this.requireContext(),"Se Actualizo correctamente", Toast.LENGTH_LONG)
                    .show()
                binding.descripcion.setText("")
                binding.division.setText("")
                binding.noPersonas.setText("")
                idArea = 0
                binding.actualizar.isEnabled = false

            } else {
                AlertDialog.Builder(this.requireContext())
                    .setTitle("Atencion")
                    .setMessage("ERROR NO SE ACTUALIZO")
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}