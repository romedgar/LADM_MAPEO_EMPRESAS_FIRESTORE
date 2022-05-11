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
import ittepic.edu.ladm_u3_p1_edgar_gerardo_rojas_medina_mapeo_empresas.databinding.FragmentConsAreaBinding
import java.util.ArrayList

class AreaFragment : Fragment() {

    private var _binding: FragmentConsAreaBinding? = null
    var listaID = ArrayList<String>()

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
        val baseRemota = FirebaseFirestore.getInstance()
        baseRemota.collection("area")
            .get()
            .addOnSuccessListener {
                var arreglo = ArrayList<String>()
                listaID.clear()
                for(documneto in it){
                    var cadena = "Descripción: ${documneto.getString("descripcion")}\n" +
                            "División: ${documneto.getString("division")}\n"+
                            "Empleados: ${documneto.getString("cantidad_empleados")}"
                    arreglo.add(cadena)
                    listaID.add(documneto.id)
                }
                binding.lista.adapter = ArrayAdapter<String>(this.requireContext(),
                    R.layout.simple_list_item_1,arreglo)

                binding.lista.setOnItemClickListener { adapterView, view, pos, l ->
                    val idSeleccionado = listaID.get(pos)

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
            val baseRemota = FirebaseFirestore.getInstance()
            baseRemota.collection("area")
                .whereEqualTo("descripcion",descripcion).whereEqualTo("division",division)
                .get()
                .addOnSuccessListener {
                    var arreglo = ArrayList<String>()
                    listaID.clear()
                    for(documneto in it){
                        var cadena = "Descripción: ${documneto.getString("descripcion")}\n" +
                                "División: ${documneto.getString("division")}\n"+
                                "Empleados: ${documneto.getString("cantidad_empleados")}"
                        arreglo.add(cadena)
                        listaID.add(documneto.id)
                    }
                    binding.lista.adapter = ArrayAdapter<String>(this.requireContext(),
                        R.layout.simple_list_item_1,arreglo)

                    binding.lista.setOnItemClickListener { adapterView, view, pos, l ->
                        val idSeleccionado = listaID.get(pos)

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
        if (!descripcion.equals("") and division.equals("")){
            //Aquí se genera la consulta por descripcion
            val baseRemota = FirebaseFirestore.getInstance()
            baseRemota.collection("area")
                .whereEqualTo("descripcion",descripcion)
                .get()
                .addOnSuccessListener {
                    var arreglo = ArrayList<String>()
                    listaID.clear()
                    for(documneto in it){
                        var cadena = "Descripción: ${documneto.getString("descripcion")}\n" +
                                "División: ${documneto.getString("division")}\n"+
                                "Empleados: ${documneto.getString("cantidad_empleados")}"
                        arreglo.add(cadena)
                        listaID.add(documneto.id)
                    }
                    binding.lista.adapter = ArrayAdapter<String>(this.requireContext(),
                        R.layout.simple_list_item_1,arreglo)

                    binding.lista.setOnItemClickListener { adapterView, view, pos, l ->
                        val idSeleccionado = listaID.get(pos)

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
        if (descripcion.equals("") and !division.equals("")){
            //Aquí se genera la consulta por división
            val baseRemota = FirebaseFirestore.getInstance()
            baseRemota.collection("area")
                .whereEqualTo("division",division)
                .get()
                .addOnSuccessListener {
                    var arreglo = ArrayList<String>()
                    listaID.clear()
                    for(documneto in it){
                        var cadena = "Descripción: ${documneto.getString("descripcion")}\n" +
                                "División: ${documneto.getString("division")}\n"+
                                "Empleados: ${documneto.getString("cantidad_empleados")}"
                        arreglo.add(cadena)
                        listaID.add(documneto.id)
                    }
                    binding.lista.adapter = ArrayAdapter<String>(this.requireContext(),
                        R.layout.simple_list_item_1,arreglo)

                    binding.lista.setOnItemClickListener { adapterView, view, pos, l ->
                        val idSeleccionado = listaID.get(pos)

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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}