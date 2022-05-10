package ittepic.edu.ladm_u3_p1_edgar_gerardo_rojas_medina_mapeo_empresas.ui.isertar_departamento

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.firestore.FirebaseFirestore
import ittepic.edu.ladm_u3_p1_edgar_gerardo_rojas_medina_mapeo_empresas.databinding.FragmentActualizarDeptoBinding
import java.util.ArrayList

class DepartamentoActualizarFragment : Fragment() {

    private var _binding: FragmentActualizarDeptoBinding? = null
    var listaIDs = ArrayList<String>()
    var listaIDss = ArrayList<String>()
    var idSub = ""

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
            val baseRemota = FirebaseFirestore.getInstance()
            baseRemota.collection("subdepartamento")
                .document(idSub)
                .update("descripcion",binding.desarea.text.toString(),
                    "division", binding.division.text.toString(),
                    "id_edificio", binding.edifcio.text.toString(),
                    "piso", binding.piso.text.toString())

                .addOnSuccessListener {
                    Toast.makeText(this.requireContext(),"Se actualizó con éxito", Toast.LENGTH_LONG)
                        .show()
                    binding.desarea.setText("")
                    binding.division.setText("")
                    binding.edifcio.setText("")
                    binding.piso.setText("")
                    binding.actualizar.isEnabled = false
                    mostrarSubEnListView()
                }
                .addOnFailureListener {
                    AlertDialog.Builder(this.requireContext())
                        .setMessage(it.message)
                        .show()
                }
        }


        return root
    }


    fun mostrarSubEnListView(){
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
                binding.listaSub.adapter = ArrayAdapter<String>(this.requireContext(),
                    R.layout.simple_list_item_1,arreglo)

                binding.listaSub.setOnItemClickListener { adapterView, view, pos, l ->
                    val idSeleccionado = listaIDs.get(pos)

                    AlertDialog.Builder(this.requireContext()).setTitle("ATENCIÓN")
                        .setMessage("Qué deseas hacer con: ${idSeleccionado}")
                        .setPositiveButton("ACTUALIZAR"){d, i->
                            idSub = idSeleccionado
                            actualizar(idSeleccionado)
                            binding.actualizar.isEnabled = true

                        }
                        .setNeutralButton("ELIMINAR"){d, i->
                            eliminar(idSeleccionado)
                        }
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
/*
    .setPositiveButton("Seleccionar"){d,i->
        binding.idarea.setText(area.idArea.toString())
        binding.descripcion.setText(area.descripcion)
    }*/

    fun mostrarDatosEnListView(){
        FirebaseFirestore.getInstance()
            .collection("area")
            .addSnapshotListener { querySnapshot, firebaseFirestoreException ->
                if(firebaseFirestoreException != null){
                    //Si hubo
                    AlertDialog.Builder(this.requireContext())
                        .setMessage(firebaseFirestoreException.message)
                        .show()
                    return@addSnapshotListener
                }
                var arreglo = ArrayList<String>()
                listaIDss.clear()
                for(documneto in querySnapshot!!){
                    var cadena = "${documneto.getString("descripcion")}\n" +
                            "${documneto.getString("division")}"
                    arreglo.add(cadena)
                    listaIDss.add(documneto.id)
                }
                binding.lista.adapter = ArrayAdapter<String>(this.requireContext(),
                    android.R.layout.simple_list_item_1,arreglo)

                binding.lista.setOnItemClickListener { adapterView, view, pos, l ->
                    val idSeleccionado = listaIDss.get(pos)

                    AlertDialog.Builder(this.requireContext()).setTitle("ATENCIÓN")
                        .setMessage("Qué deseas hacer con: ${idSeleccionado}")
                        .setPositiveButton("SELECCIONAR"){d, i->
                            obtenerDescrip(idSeleccionado)
                        }
                        .setNegativeButton("SALIR"){d, i->

                        }
                        .show()

                }
            }
    }

    fun obtenerDescrip(idSeleccionado : String){
        val baseRemota = FirebaseFirestore.getInstance()
        baseRemota.collection("area")
            .document(idSeleccionado)
            .get()
            .addOnSuccessListener {
                binding.desarea.setText(it.getString("descripcion"))
                binding.division.setText(it.getString("division"))
            }
            .addOnFailureListener {
                AlertDialog.Builder(this.requireContext())
                    .setMessage(it.message)
                    .show()
            }
    }

    fun actualizar(idSeleccionado: String){
        val baseRemota = FirebaseFirestore.getInstance()
        baseRemota.collection("subdepartamento")
            .document(idSeleccionado)
            .get()
            .addOnSuccessListener {
                binding.edifcio.setText(it.getString("id_edificio"))
                binding.piso.setText(it.getString("piso"))
                binding.desarea.setText(it.getString("descripcion"))
                binding.division.setText(it.getString("division"))
            }
            .addOnFailureListener {
                AlertDialog.Builder(this.requireContext())
                    .setMessage(it.message)
                    .show()
            }
    }

    private fun eliminar(idElegido: String) {
        val baseRemota = FirebaseFirestore.getInstance()
        baseRemota.collection("subdepartamento")
            .document(idElegido)
            .delete()
            .addOnSuccessListener {
                Toast.makeText(this.requireContext(),"Se elimino con éxito", Toast.LENGTH_LONG)
                mostrarSubEnListView()
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