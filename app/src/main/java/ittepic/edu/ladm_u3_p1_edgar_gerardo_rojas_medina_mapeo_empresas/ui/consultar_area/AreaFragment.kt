package ittepic.edu.ladm_u3_p1_edgar_gerardo_rojas_medina_mapeo_empresas.ui.isertar_departamento

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import ittepic.edu.ladm_u3_p1_edgar_gerardo_rojas_medina_mapeo_empresas.Area
import ittepic.edu.ladm_u3_p1_edgar_gerardo_rojas_medina_mapeo_empresas.databinding.FragmentConsAreaBinding
import ittepic.edu.ladm_u3_p1_edgar_gerardo_rojas_medina_mapeo_empresas.databinding.FragmentDepartamentoBinding
import java.util.ArrayList

class AreaFragment : Fragment() {

    private var _binding: FragmentConsAreaBinding? = null
    var listaIDs = ArrayList<String>()

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

        _binding = FragmentConsAreaBinding.inflate(inflater, container, false)
        val root: View = binding.root
        mostrarDatosEnListView()


        homeViewModel.text.observe(viewLifecycleOwner) {
        }

        binding.buscar.setOnClickListener {
            buscar(binding.descripcion.text.toString(),binding.division.text.toString())
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
                .setTitle("Información")
                .setMessage("Area: ${area.descripcion},\nDivisión: ${area.division}")
                .setNeutralButton("Cerrar"){d,i->}
                .show()
        }
    }

    fun buscar(descripcion : String, division: String){
        var descripcion = descripcion
        var division = division

        if (descripcion.equals("") and division.equals("")){
            AlertDialog.Builder(this.requireContext())
                .setTitle("Aviso")
                .setMessage("Necesitas Introducir una Descripción o División")
                .setNeutralButton("Cerrar"){d,i->}
                .show()
        }
        if (!descripcion.equals("") and !division.equals("")){
            //Aquí se genera la consulta doble
            var listaAreas = Area(this.requireContext()).mostrarCombinada(descripcion,division)
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
                    .setTitle("Información")
                    .setMessage("Area: ${area.descripcion},\nDivisión: ${area.division}")
                    .setNeutralButton("Cerrar"){d,i->}
                    .show()
            }
        }
        if (!descripcion.equals("") and division.equals("")){
            //Aquí se genera la consulta por descripcion
            var listaAreas = Area(this.requireContext()).mostrarDescrip(descripcion)
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
                    .setTitle("Información")
                    .setMessage("Area: ${area.descripcion},\nDivisión: ${area.division}")
                    .setNeutralButton("Cerrar"){d,i->}
                    .show()
            }
        }
        if (descripcion.equals("") and !division.equals("")){
            //Aquí se genera la consulta por división
            var listaAreas = Area(this.requireContext()).mostrarDiv(division)
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
                    .setTitle("Información")
                    .setMessage("Area: ${area.descripcion},\nDivisión: ${area.division}")
                    .setNeutralButton("Cerrar"){d,i->}
                    .show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}