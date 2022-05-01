package ittepic.edu.ladm_u3_p1_edgar_gerardo_rojas_medina_mapeo_empresas.ui.isertar_departamento

import android.R
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
import ittepic.edu.ladm_u3_p1_edgar_gerardo_rojas_medina_mapeo_empresas.Subdepartamento
import ittepic.edu.ladm_u3_p1_edgar_gerardo_rojas_medina_mapeo_empresas.databinding.FragmentActualizarDeptoBinding
import ittepic.edu.ladm_u3_p1_edgar_gerardo_rojas_medina_mapeo_empresas.databinding.FragmentDepartamentoBinding
import java.util.ArrayList

class DepartamentoActualizarFragment : Fragment() {

    private var _binding: FragmentActualizarDeptoBinding? = null
    var listaIDs = ArrayList<String>()
    var listaIDss = ArrayList<String>()
    var idSub = 0

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(AreaViewModel::class.java)

        _binding = FragmentActualizarDeptoBinding.inflate(inflater, container, false)
        val root: View = binding.root
        mostrarSubEnListView()
        mostrarDatosEnListView()

        homeViewModel.text.observe(viewLifecycleOwner) {
        }


        binding.actualizar.setOnClickListener {
            var sub = Subdepartamento(this.requireContext())
            sub.idSubdepto = idSub
            sub.idEdificio = binding.edifcio.text.toString()
            sub.piso = binding.piso.text.toString()
            sub.idArea = binding.idarea.text.toString().toInt()

            val respuesta = sub.actualizar()
            binding.actualizar.isEnabled = false

            if(respuesta){
                Toast.makeText(this.requireContext(),"Se Actualizo correctamente", Toast.LENGTH_LONG)
                    .show()
                binding.edifcio.setText("")
                binding.piso.setText("")
                binding.idarea.setText("")
                binding.desarea.setText("")
                idSub = 0

            } else {
                AlertDialog.Builder(this.requireContext())
                    .setTitle("Atencion")
                    .setMessage("ERROR NO SE ACTUALIZO")
            }
        }


        return root
    }


    fun mostrarSubEnListView(){
        var listaSubDepto = Subdepartamento(this.requireContext()).mostrarTodos()
        var descripcionArea = ArrayList<String>()

        listaIDs.clear()
        (0..listaSubDepto.size-1).forEach{
            val dep = listaSubDepto.get(it)
            descripcionArea.add(dep.descripcion)
            listaIDs.add(dep.idSubdepto.toString())
        }

        binding.listaSub.adapter = ArrayAdapter<String>(this.requireContext(), R.layout.simple_list_item_1,descripcionArea)
        binding.listaSub.setOnItemClickListener { adapterView, view, indice, l ->
            val idDeptosLista = listaIDs.get(indice)
            val sub = Subdepartamento(this.requireContext()).mostrarUno(idDeptosLista.toInt())

            AlertDialog.Builder(this.requireContext())
                .setTitle("Información")
                .setMessage("Area: ${sub.descripcion},\nDivisión: ${sub.division},\n" +
                        "Edificio: ${sub.idEdificio},\n" +
                        "Piso: ${sub.piso}")
                .setPositiveButton("Actualizar"){d,i->
                    binding.edifcio.setText(sub.idEdificio)
                    binding.piso.setText(sub.piso)
                    binding.idarea.setText(sub.idArea.toString())
                    binding.desarea.setText(sub.descripcion)
                    idSub = sub.idSubdepto
                    binding.actualizar.isEnabled = true
                }
                .setNegativeButton("Eliminar"){d,i->
                    sub.eliminar()
                    mostrarSubEnListView()
                }
                .setNeutralButton("Cerrar"){d,i->}
                .show()
        }
    }
/*
    .setPositiveButton("Seleccionar"){d,i->
        binding.idarea.setText(area.idArea.toString())
        binding.descripcion.setText(area.descripcion)
    }*/

    fun mostrarDatosEnListView(){
        var listaAreas = Area(this.requireContext()).mostrarTodos()
        var descripcionAreas = ArrayList<String>()

        listaIDss.clear()
        (0..listaAreas.size-1).forEach{
            val ar = listaAreas.get(it)
            descripcionAreas.add(ar.descripcion)
            listaIDss.add(ar.idArea.toString())
        }

        binding.lista.adapter = ArrayAdapter<String>(this.requireContext(), R.layout.simple_list_item_1,descripcionAreas)
        binding.lista.setOnItemClickListener { adapterView, view, indice, l ->
            val idAereaLista = listaIDss.get(indice)
            val area = Area(this.requireContext()).mostrarUno(idAereaLista.toInt())

            AlertDialog.Builder(this.requireContext())
                .setTitle("Atención")
                .setMessage("Qué deseas hacer con ${area.descripcion},\n División: ${area.division}?")
                .setPositiveButton("Seleccionar"){d,i->
                    binding.idarea.setText(area.idArea.toString())
                    binding.desarea.setText(area.descripcion)
                }
                .setNeutralButton("Cerrar"){d,i->}
                .show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}