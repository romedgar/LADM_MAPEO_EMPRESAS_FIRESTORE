package ittepic.edu.ladm_u3_p1_edgar_gerardo_rojas_medina_mapeo_empresas.ui.home

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
import ittepic.edu.ladm_u3_p1_edgar_gerardo_rojas_medina_mapeo_empresas.databinding.FragmentHomeBinding
import java.util.ArrayList

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    var listaID = ArrayList<String>()
    var idSel = ""


    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    var idArea = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        //EVENTO (SE DISPARA SOLO)
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
                listaID.clear()
                for(documneto in querySnapshot!!){
                    var cadena = "${documneto.getString("descripcion")}\n" +
                            "${documneto.getString("division")}"
                    arreglo.add(cadena)
                    listaID.add(documneto.id)
                }
                binding.lista.adapter = ArrayAdapter<String>(this.requireContext(),
                    android.R.layout.simple_list_item_1,arreglo)

                binding.lista.setOnItemClickListener { adapterView, view, pos, l ->
                    val idSeleccionado = listaID.get(pos)

                    AlertDialog.Builder(this.requireContext()).setTitle("ATENCIÓN")
                        .setMessage("Qué deseas hacer con: ${idSeleccionado}")
                        .setNeutralButton("ELIMINAR"){d, i->
                            eliminar(idSeleccionado)
                        }
                        .setPositiveButton("ACTUALIZAR"){d, i->
                            actualizar(idSeleccionado)
                            binding.actualizar.isEnabled = true
                            binding.insertar.isEnabled = false
                        }
                        .setNegativeButton("SALIR"){d, i->

                        }
                        .show()

                }
            }


        _binding!!.insertar.setOnClickListener {
            val baseRemota = FirebaseFirestore.getInstance()

            val datos = hashMapOf(
                "descripcion" to binding.descripcion.text.toString(),
                "division" to binding.division.text.toString(),
                "cantidad_empleados" to binding.noPersonas.text.toString()
            )

            baseRemota.collection("area")
                .add(datos)
                .addOnSuccessListener {
                    //Si se pudo
                    Toast.makeText(this.requireContext(),"Exito! Se inserto", Toast.LENGTH_LONG)
                        .show()
                }
                .addOnFailureListener {
                    //No se pudo
                    AlertDialog.Builder(this.requireContext())
                        .setMessage(it.message)
                        .show()
                }
            binding.descripcion.setText("")
            binding.division.setText("")
            binding.noPersonas.setText("")
        }

        binding.actualizar.setOnClickListener {
            val baseRemota = FirebaseFirestore.getInstance()
            baseRemota.collection("area")
                .document(idSel)
                .update("descripcion",binding.descripcion.text.toString(),
                    "division", binding.division.text.toString(),
                    "cantidad_empleados", binding.noPersonas.text.toString())
                .addOnSuccessListener {
                    Toast.makeText(this.requireContext(),"Se actualizó con éxito", Toast.LENGTH_LONG)
                        .show()
                    binding.descripcion.setText("")
                    binding.division.setText("")
                    binding.noPersonas.setText("")
                    binding.actualizar.isEnabled = false
                    binding.insertar.isEnabled = true
                }
                .addOnFailureListener {
                    AlertDialog.Builder(this.requireContext())
                        .setMessage(it.message)
                        .show()
                }
        }

        return root

    }

    private fun actualizar(idSeleccionado: String) {
        val baseRemota = FirebaseFirestore.getInstance()
        baseRemota.collection("area")
            .document(idSeleccionado)
            .get()
            .addOnSuccessListener {
                binding.descripcion.setText(it.getString("descripcion"))
                binding.division.setText(it.getString("division"))
                binding.noPersonas.setText(it.getString("cantidad_empleados"))
                idSel = idSeleccionado
            }
            .addOnFailureListener {
                AlertDialog.Builder(this.requireContext())
                    .setMessage(it.message)
                    .show()
            }

    }


    private fun eliminar(idElegido: String) {
        val baseRemota = FirebaseFirestore.getInstance()
        baseRemota.collection("area")
            .document(idElegido)
            .delete()
            .addOnSuccessListener {
                Toast.makeText(this.requireContext(),"Se elimino con éxito", Toast.LENGTH_LONG)
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