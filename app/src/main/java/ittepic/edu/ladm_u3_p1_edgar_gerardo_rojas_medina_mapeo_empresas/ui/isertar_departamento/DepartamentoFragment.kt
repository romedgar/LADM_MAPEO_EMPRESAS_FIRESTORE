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
import ittepic.edu.ladm_u3_p1_edgar_gerardo_rojas_medina_mapeo_empresas.Subdepartamento
import ittepic.edu.ladm_u3_p1_edgar_gerardo_rojas_medina_mapeo_empresas.databinding.FragmentDepartamentoBinding
import java.util.ArrayList

class DepartamentoFragment : Fragment() {

    private var _binding: FragmentDepartamentoBinding? = null
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

        _binding = FragmentDepartamentoBinding.inflate(inflater, container, false)
        val root: View = binding.root
        mostrarDatosEnListView()

        homeViewModel.text.observe(viewLifecycleOwner) {
        }

        binding.buscar.setOnClickListener {
            buscar(binding.descripcion.text.toString(),binding.division.text.toString(),binding.idedificio.text.toString())
        }

        return root
    }

    fun buscar(descripcion : String, division: String, edificio: String){
        if (descripcion.equals("") and division.equals("") and edificio.equals("")){
            AlertDialog.Builder(this.requireContext())
                .setTitle("Aviso")
                .setMessage("Necesitas Introducir una Descripción, División o Edificio")
                .setNeutralButton("Cerrar"){d,i->}
                .show()
        }
        if (descripcion.equals("") and division.equals("") and !edificio.equals("")){
            //Aquí se busca por edificio
            var listaSubDepto = Subdepartamento(this.requireContext()).mostrarEdi(edificio)
            var descripcionArea = ArrayList<String>()

            listaIDs.clear()
            (0..listaSubDepto.size-1).forEach{
                val dep = listaSubDepto.get(it)
                descripcionArea.add(dep.descripcion)
                listaIDs.add(dep.idSubdepto.toString())
            }

            binding.lista.adapter = ArrayAdapter<String>(this.requireContext(), R.layout.simple_list_item_1,descripcionArea)
            binding.lista.setOnItemClickListener { adapterView, view, indice, l ->
                val idDeptosLista = listaIDs.get(indice)
                val sub = Subdepartamento(this.requireContext()).mostrarUno(idDeptosLista.toInt())

                AlertDialog.Builder(this.requireContext())
                    .setTitle("Información")
                    .setMessage("Area: ${sub.descripcion},\nDivisión: ${sub.division},\n" +
                            "Edificio: ${sub.idEdificio},\n" +
                            "Piso: ${sub.piso}")
                    .setNeutralButton("Cerrar"){d,i->}
                    .show()
            }

        }
        if (descripcion.equals("") and !division.equals("") and edificio.equals("")){
         //Busqueda por division
            var listaSubDepto = Subdepartamento(this.requireContext()).mostrarDiv(division)
            var descripcionArea = ArrayList<String>()

            listaIDs.clear()
            (0..listaSubDepto.size-1).forEach{
                val dep = listaSubDepto.get(it)
                descripcionArea.add(dep.descripcion)
                listaIDs.add(dep.idSubdepto.toString())
            }

            binding.lista.adapter = ArrayAdapter<String>(this.requireContext(), R.layout.simple_list_item_1,descripcionArea)
            binding.lista.setOnItemClickListener { adapterView, view, indice, l ->
                val idDeptosLista = listaIDs.get(indice)
                val sub = Subdepartamento(this.requireContext()).mostrarUno(idDeptosLista.toInt())

                AlertDialog.Builder(this.requireContext())
                    .setTitle("Información")
                    .setMessage("Area: ${sub.descripcion},\nDivisión: ${sub.division},\n" +
                            "Edificio: ${sub.idEdificio},\n" +
                            "Piso: ${sub.piso}")
                    .setNeutralButton("Cerrar"){d,i->}
                    .show()
            }

        }
        if (!descripcion.equals("") and division.equals("") and edificio.equals("")){
            //Busqueda por descripcion
            var listaSubDepto = Subdepartamento(this.requireContext()).mostrarDescrip(descripcion)
            var descripcionArea = ArrayList<String>()

            listaIDs.clear()
            (0..listaSubDepto.size-1).forEach{
                val dep = listaSubDepto.get(it)
                descripcionArea.add(dep.descripcion)
                listaIDs.add(dep.idSubdepto.toString())
            }

            binding.lista.adapter = ArrayAdapter<String>(this.requireContext(), R.layout.simple_list_item_1,descripcionArea)
            binding.lista.setOnItemClickListener { adapterView, view, indice, l ->
                val idDeptosLista = listaIDs.get(indice)
                val sub = Subdepartamento(this.requireContext()).mostrarUno(idDeptosLista.toInt())

                AlertDialog.Builder(this.requireContext())
                    .setTitle("Información")
                    .setMessage("Area: ${sub.descripcion},\nDivisión: ${sub.division},\n" +
                            "Edificio: ${sub.idEdificio},\n" +
                            "Piso: ${sub.piso}")
                    .setNeutralButton("Cerrar"){d,i->}
                    .show()
            }

        }
        if (!descripcion.equals("") and !division.equals("") and edificio.equals("")){
            //Busqueda por descripcion y division
            var listaSubDepto = Subdepartamento(this.requireContext()).mostrarDesyDiv(descripcion,division)
            var descripcionArea = ArrayList<String>()

            listaIDs.clear()
            (0..listaSubDepto.size-1).forEach{
                val dep = listaSubDepto.get(it)
                descripcionArea.add(dep.descripcion)
                listaIDs.add(dep.idSubdepto.toString())
            }

            binding.lista.adapter = ArrayAdapter<String>(this.requireContext(), R.layout.simple_list_item_1,descripcionArea)
            binding.lista.setOnItemClickListener { adapterView, view, indice, l ->
                val idDeptosLista = listaIDs.get(indice)
                val sub = Subdepartamento(this.requireContext()).mostrarUno(idDeptosLista.toInt())

                AlertDialog.Builder(this.requireContext())
                    .setTitle("Información")
                    .setMessage("Area: ${sub.descripcion},\nDivisión: ${sub.division},\n" +
                            "Edificio: ${sub.idEdificio},\n" +
                            "Piso: ${sub.piso}")
                    .setNeutralButton("Cerrar"){d,i->}
                    .show()
            }

        }
        if (!descripcion.equals("") and division.equals("") and !edificio.equals("")){
            //Busqueda por descripcion y division
            var listaSubDepto = Subdepartamento(this.requireContext()).mostrarDescripEdi(descripcion,edificio)
            var descripcionArea = ArrayList<String>()

            listaIDs.clear()
            (0..listaSubDepto.size-1).forEach{
                val dep = listaSubDepto.get(it)
                descripcionArea.add(dep.descripcion)
                listaIDs.add(dep.idSubdepto.toString())
            }

            binding.lista.adapter = ArrayAdapter<String>(this.requireContext(), R.layout.simple_list_item_1,descripcionArea)
            binding.lista.setOnItemClickListener { adapterView, view, indice, l ->
                val idDeptosLista = listaIDs.get(indice)
                val sub = Subdepartamento(this.requireContext()).mostrarUno(idDeptosLista.toInt())

                AlertDialog.Builder(this.requireContext())
                    .setTitle("Información")
                    .setMessage("Area: ${sub.descripcion},\nDivisión: ${sub.division},\n" +
                            "Edificio: ${sub.idEdificio},\n" +
                            "Piso: ${sub.piso}")
                    .setNeutralButton("Cerrar"){d,i->}
                    .show()
            }

        }
        if (descripcion.equals("") and !division.equals("") and !edificio.equals("")){
            //Busqueda por descripcion y division
            var listaSubDepto = Subdepartamento(this.requireContext()).mostrarDivEdi(division,edificio)
            var descripcionArea = ArrayList<String>()

            listaIDs.clear()
            (0..listaSubDepto.size-1).forEach{
                val dep = listaSubDepto.get(it)
                descripcionArea.add(dep.descripcion)
                listaIDs.add(dep.idSubdepto.toString())
            }

            binding.lista.adapter = ArrayAdapter<String>(this.requireContext(), R.layout.simple_list_item_1,descripcionArea)
            binding.lista.setOnItemClickListener { adapterView, view, indice, l ->
                val idDeptosLista = listaIDs.get(indice)
                val sub = Subdepartamento(this.requireContext()).mostrarUno(idDeptosLista.toInt())

                AlertDialog.Builder(this.requireContext())
                    .setTitle("Información")
                    .setMessage("Area: ${sub.descripcion},\nDivisión: ${sub.division},\n" +
                            "Edificio: ${sub.idEdificio},\n" +
                            "Piso: ${sub.piso}")
                    .setNeutralButton("Cerrar"){d,i->}
                    .show()
            }

        }
        if (!descripcion.equals("") and !division.equals("") and !edificio.equals("")){
            //Busqueda por descripcion y division
            var listaSubDepto = Subdepartamento(this.requireContext()).mostrarCombinada(descripcion,division, edificio)
            var descripcionArea = ArrayList<String>()

            listaIDs.clear()
            (0..listaSubDepto.size-1).forEach{
                val dep = listaSubDepto.get(it)
                descripcionArea.add(dep.descripcion)
                listaIDs.add(dep.idSubdepto.toString())
            }

            binding.lista.adapter = ArrayAdapter<String>(this.requireContext(), R.layout.simple_list_item_1,descripcionArea)
            binding.lista.setOnItemClickListener { adapterView, view, indice, l ->
                val idDeptosLista = listaIDs.get(indice)
                val sub = Subdepartamento(this.requireContext()).mostrarUno(idDeptosLista.toInt())

                AlertDialog.Builder(this.requireContext())
                    .setTitle("Información")
                    .setMessage("Area: ${sub.descripcion},\nDivisión: ${sub.division},\n" +
                            "Edificio: ${sub.idEdificio},\n" +
                            "Piso: ${sub.piso}")
                    .setNeutralButton("Cerrar"){d,i->}
                    .show()
            }

        }

    }

    fun mostrarDatosEnListView(){
        var listaSubDepto = Subdepartamento(this.requireContext()).mostrarTodos()
        var descripcionArea = ArrayList<String>()

        listaIDs.clear()
        (0..listaSubDepto.size-1).forEach{
            val dep = listaSubDepto.get(it)
            descripcionArea.add(dep.descripcion)
            listaIDs.add(dep.idSubdepto.toString())
        }

        binding.lista.adapter = ArrayAdapter<String>(this.requireContext(), R.layout.simple_list_item_1,descripcionArea)
        binding.lista.setOnItemClickListener { adapterView, view, indice, l ->
            val idDeptosLista = listaIDs.get(indice)
            val sub = Subdepartamento(this.requireContext()).mostrarUno(idDeptosLista.toInt())

            AlertDialog.Builder(this.requireContext())
                .setTitle("Información")
                .setMessage("Area: ${sub.descripcion},\nDivisión: ${sub.division},\n" +
                        "Edificio: ${sub.idEdificio},\n" +
                        "Piso: ${sub.piso}")
                .setNeutralButton("Cerrar"){d,i->}
                .show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}