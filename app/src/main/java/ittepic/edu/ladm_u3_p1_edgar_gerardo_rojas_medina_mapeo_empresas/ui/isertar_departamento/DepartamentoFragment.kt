package ittepic.edu.ladm_u3_p1_edgar_gerardo_rojas_medina_mapeo_empresas.ui.isertar_departamento

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.firestore.FirebaseFirestore
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
            val baseRemota = FirebaseFirestore.getInstance()
            baseRemota.collection("subdepartamento")
                .whereEqualTo("id_edificio",edificio)
                .get()
                .addOnSuccessListener {
                    var arreglo = ArrayList<String>()
                    listaIDs.clear()
                    for(documneto in it){
                        var cadena =
                            "${documneto.getString("descripcion")}\n" +
                                    "${documneto.getString("division")}\n" +
                                    "${documneto.getString("id_edificio")}\n" +
                                    "Piso: ${documneto.getString("piso")}"
                        arreglo.add(cadena)
                        listaIDs.add(documneto.id)
                    }
                    binding.lista.adapter = ArrayAdapter<String>(this.requireContext(),
                        R.layout.simple_list_item_1,arreglo)

                    binding.lista.setOnItemClickListener { adapterView, view, pos, l ->
                        val idSeleccionado = listaIDs.get(pos)

                        AlertDialog.Builder(this.requireContext()).setTitle("ATENCIÓN")
                            .setMessage("Qué deseas hacer con: ${idSeleccionado}")
                            .setNegativeButton("SALIR"){d, i->

                            }
                            .show()

                    }
                }
                .addOnFailureListener {
                    AlertDialog.Builder(this.requireContext())
                        .setMessage(it.message)
                        .show()
                }

        }
        if (descripcion.equals("") and !division.equals("") and edificio.equals("")){
         //Busqueda por division
            val baseRemota = FirebaseFirestore.getInstance()
            baseRemota.collection("subdepartamento")
                .whereEqualTo("division",division)
                .get()
                .addOnSuccessListener {
                    var arreglo = ArrayList<String>()
                    listaIDs.clear()
                    for(documneto in it){
                        var cadena =
                            "${documneto.getString("descripcion")}\n" +
                                    "${documneto.getString("division")}\n" +
                                    "${documneto.getString("id_edificio")}\n" +
                                    "Piso: ${documneto.getString("piso")}"
                        arreglo.add(cadena)
                        listaIDs.add(documneto.id)
                    }
                    binding.lista.adapter = ArrayAdapter<String>(this.requireContext(),
                        R.layout.simple_list_item_1,arreglo)

                    binding.lista.setOnItemClickListener { adapterView, view, pos, l ->
                        val idSeleccionado = listaIDs.get(pos)

                        AlertDialog.Builder(this.requireContext()).setTitle("ATENCIÓN")
                            .setMessage("Qué deseas hacer con: ${idSeleccionado}")
                            .setNegativeButton("SALIR"){d, i->

                            }
                            .show()

                    }
                }
                .addOnFailureListener {
                    AlertDialog.Builder(this.requireContext())
                        .setMessage(it.message)
                        .show()
                }

        }
        if (!descripcion.equals("") and division.equals("") and edificio.equals("")){
            //Busqueda por descripcion
            val baseRemota = FirebaseFirestore.getInstance()
            baseRemota.collection("subdepartamento")
                .whereEqualTo("descripcion",descripcion)
                .get()
                .addOnSuccessListener {
                    var arreglo = ArrayList<String>()
                    listaIDs.clear()
                    for(documneto in it){
                        var cadena =
                            "${documneto.getString("descripcion")}\n" +
                                    "${documneto.getString("division")}\n" +
                                    "${documneto.getString("id_edificio")}\n" +
                                    "Piso: ${documneto.getString("piso")}"
                        arreglo.add(cadena)
                        listaIDs.add(documneto.id)
                    }
                    binding.lista.adapter = ArrayAdapter<String>(this.requireContext(),
                        R.layout.simple_list_item_1,arreglo)

                    binding.lista.setOnItemClickListener { adapterView, view, pos, l ->
                        val idSeleccionado = listaIDs.get(pos)

                        AlertDialog.Builder(this.requireContext()).setTitle("ATENCIÓN")
                            .setMessage("Qué deseas hacer con: ${idSeleccionado}")
                            .setNegativeButton("SALIR"){d, i->

                            }
                            .show()

                    }
                }
                .addOnFailureListener {
                    AlertDialog.Builder(this.requireContext())
                        .setMessage(it.message)
                        .show()
                }

        }
        if (!descripcion.equals("") and !division.equals("") and edificio.equals("")){
            //Busqueda por descripcion y division
            val baseRemota = FirebaseFirestore.getInstance()
            baseRemota.collection("subdepartamento")
                .whereEqualTo("descripcion",descripcion).whereEqualTo("division",division)
                .get()
                .addOnSuccessListener {
                    var arreglo = ArrayList<String>()
                    listaIDs.clear()
                    for(documneto in it){
                        var cadena =
                            "${documneto.getString("descripcion")}\n" +
                                    "${documneto.getString("division")}\n" +
                                    "${documneto.getString("id_edificio")}\n" +
                                    "Piso: ${documneto.getString("piso")}"
                        arreglo.add(cadena)
                        listaIDs.add(documneto.id)
                    }
                    binding.lista.adapter = ArrayAdapter<String>(this.requireContext(),
                        R.layout.simple_list_item_1,arreglo)

                    binding.lista.setOnItemClickListener { adapterView, view, pos, l ->
                        val idSeleccionado = listaIDs.get(pos)

                        AlertDialog.Builder(this.requireContext()).setTitle("ATENCIÓN")
                            .setMessage("Qué deseas hacer con: ${idSeleccionado}")
                            .setNegativeButton("SALIR"){d, i->

                            }
                            .show()

                    }
                }
                .addOnFailureListener {
                    AlertDialog.Builder(this.requireContext())
                        .setMessage(it.message)
                        .show()
                }

        }
        if (!descripcion.equals("") and division.equals("") and !edificio.equals("")){
            //Busqueda por descripcion y edificio
            //Busqueda por descripcion y division
            val baseRemota = FirebaseFirestore.getInstance()
            baseRemota.collection("subdepartamento")
                .whereEqualTo("descripcion",descripcion).whereEqualTo("id_edificio",edificio)
                .get()
                .addOnSuccessListener {
                    var arreglo = ArrayList<String>()
                    listaIDs.clear()
                    for(documneto in it){
                        var cadena =
                            "${documneto.getString("descripcion")}\n" +
                                    "${documneto.getString("division")}\n" +
                                    "${documneto.getString("id_edificio")}\n" +
                                    "Piso: ${documneto.getString("piso")}"
                        arreglo.add(cadena)
                        listaIDs.add(documneto.id)
                    }
                    binding.lista.adapter = ArrayAdapter<String>(this.requireContext(),
                        R.layout.simple_list_item_1,arreglo)

                    binding.lista.setOnItemClickListener { adapterView, view, pos, l ->
                        val idSeleccionado = listaIDs.get(pos)

                        AlertDialog.Builder(this.requireContext()).setTitle("ATENCIÓN")
                            .setMessage("Qué deseas hacer con: ${idSeleccionado}")
                            .setNegativeButton("SALIR"){d, i->

                            }
                            .show()

                    }
                }
                .addOnFailureListener {
                    AlertDialog.Builder(this.requireContext())
                        .setMessage(it.message)
                        .show()
                }

        }
        if (descripcion.equals("") and !division.equals("") and !edificio.equals("")){
            //Busqueda por edificio y division
            //Busqueda por descripcion y division
            val baseRemota = FirebaseFirestore.getInstance()
            baseRemota.collection("subdepartamento")
                .whereEqualTo("id_edificio",edificio).whereEqualTo("division",division)
                .get()
                .addOnSuccessListener {
                    var arreglo = ArrayList<String>()
                    listaIDs.clear()
                    for(documneto in it){
                        var cadena =
                            "${documneto.getString("descripcion")}\n" +
                                    "${documneto.getString("division")}\n" +
                                    "${documneto.getString("id_edificio")}\n" +
                                    "Piso: ${documneto.getString("piso")}"
                        arreglo.add(cadena)
                        listaIDs.add(documneto.id)
                    }
                    binding.lista.adapter = ArrayAdapter<String>(this.requireContext(),
                        R.layout.simple_list_item_1,arreglo)

                    binding.lista.setOnItemClickListener { adapterView, view, pos, l ->
                        val idSeleccionado = listaIDs.get(pos)

                        AlertDialog.Builder(this.requireContext()).setTitle("ATENCIÓN")
                            .setMessage("Qué deseas hacer con: ${idSeleccionado}")
                            .setNegativeButton("SALIR"){d, i->

                            }
                            .show()

                    }
                }
                .addOnFailureListener {
                    AlertDialog.Builder(this.requireContext())
                        .setMessage(it.message)
                        .show()
                }

        }
        if (!descripcion.equals("") and !division.equals("") and !edificio.equals("")){
            //Busqueda por descripcion, division y edificio
            //Busqueda por descripcion y division
            val baseRemota = FirebaseFirestore.getInstance()
            baseRemota.collection("subdepartamento")
                .whereEqualTo("descripcion",descripcion).whereEqualTo("division",division).whereEqualTo("id_edificio",edificio)
                .get()
                .addOnSuccessListener {
                    var arreglo = ArrayList<String>()
                    listaIDs.clear()
                    for(documneto in it){
                        var cadena =
                            "${documneto.getString("descripcion")}\n" +
                                    "${documneto.getString("division")}\n" +
                                    "${documneto.getString("id_edificio")}\n" +
                                    "Piso: ${documneto.getString("piso")}"
                        arreglo.add(cadena)
                        listaIDs.add(documneto.id)
                    }
                    binding.lista.adapter = ArrayAdapter<String>(this.requireContext(),
                        R.layout.simple_list_item_1,arreglo)

                    binding.lista.setOnItemClickListener { adapterView, view, pos, l ->
                        val idSeleccionado = listaIDs.get(pos)

                        AlertDialog.Builder(this.requireContext()).setTitle("ATENCIÓN")
                            .setMessage("Qué deseas hacer con: ${idSeleccionado}")
                            .setNegativeButton("SALIR"){d, i->

                            }
                            .show()

                    }
                }
                .addOnFailureListener {
                    AlertDialog.Builder(this.requireContext())
                        .setMessage(it.message)
                        .show()
                }

        }

    }

    fun mostrarDatosEnListView(){
        val baseRemota = FirebaseFirestore.getInstance()
        baseRemota.collection("subdepartamento")
            .get()
            .addOnSuccessListener {
                var arreglo = ArrayList<String>()
                listaIDs.clear()
                for(documneto in it){
                    var cadena =
                        "${documneto.getString("descripcion")}\n" +
                                "${documneto.getString("division")}\n" +
                                "${documneto.getString("id_edificio")}\n" +
                                "Piso: ${documneto.getString("piso")}"
                    arreglo.add(cadena)
                    listaIDs.add(documneto.id)
                }
                binding.lista.adapter = ArrayAdapter<String>(this.requireContext(),
                    R.layout.simple_list_item_1,arreglo)

                binding.lista.setOnItemClickListener { adapterView, view, pos, l ->
                    val idSeleccionado = listaIDs.get(pos)

                    AlertDialog.Builder(this.requireContext()).setTitle("ATENCIÓN")
                        .setMessage("Qué deseas hacer con: ${idSeleccionado}")
                        .setNegativeButton("SALIR"){d, i->

                        }
                        .show()

                }
            }
            .addOnFailureListener {
                AlertDialog.Builder(this.requireContext())
                    .setMessage(it.message)
                    .show()
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}