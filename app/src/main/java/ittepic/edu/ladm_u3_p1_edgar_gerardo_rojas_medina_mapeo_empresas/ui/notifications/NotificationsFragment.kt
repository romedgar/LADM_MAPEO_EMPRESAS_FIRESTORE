package ittepic.edu.ladm_u3_p1_edgar_gerardo_rojas_medina_mapeo_empresas.ui.notifications

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
import ittepic.edu.ladm_u3_p1_edgar_gerardo_rojas_medina_mapeo_empresas.databinding.FragmentNotificationsBinding
import java.util.ArrayList

class NotificationsFragment : Fragment() {

    private var _binding: FragmentNotificationsBinding? = null
    var listaID = ArrayList<String>()
    var idAr = ""
    var descrip = ""
    var divi = ""

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val notificationsViewModel =
            ViewModelProvider(this).get(NotificationsViewModel::class.java)

        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        notificationsViewModel.text.observe(viewLifecycleOwner) {
        }

        binding.buscar.setOnClickListener {
            buscar(binding.descripcion.text.toString(),binding.division.text.toString())
        }
        binding.insertar.setOnClickListener {
            val baseRemota = FirebaseFirestore.getInstance()

            val datos = hashMapOf(
                "id_edificio" to binding.edifcio.text.toString(),
                "piso" to binding.piso.text.toString(),
                "descripcion" to descrip,
                "division" to divi
               // "id_area" to binding.idarea.text.toString()
            )

            baseRemota.collection("subdepartamento")
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
            binding.edifcio.setText("")
            binding.piso.setText("")
            binding.idarea.setText("")
            binding.descripcion.setText("'")
            binding.division.setText("")
            binding.insertar.isEnabled = false
        }
        return root
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
                        var cadena = "${documneto.getString("descripcion")}\n" +
                                "${documneto.getString("division")}"
                        arreglo.add(cadena)
                        listaID.add(documneto.id)
                    }
                    binding.lista.adapter = ArrayAdapter<String>(this.requireContext(),
                        R.layout.simple_list_item_1,arreglo)

                    binding.lista.setOnItemClickListener { adapterView, view, pos, l ->
                        val idSeleccionado = listaID.get(pos)

                        AlertDialog.Builder(this.requireContext()).setTitle("ATENCIÓN")
                            .setMessage("Qué deseas hacer con: ${idSeleccionado}")
                            .setPositiveButton("SELECCIONAR"){d, i->
                                binding.idarea.setText(idSeleccionado)
                                getDatos(idSeleccionado)
                                binding.insertar.isEnabled = true
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
                        var cadena = "${documneto.getString("descripcion")}\n" +
                                "${documneto.getString("division")}"
                        arreglo.add(cadena)
                        listaID.add(documneto.id)
                    }
                    binding.lista.adapter = ArrayAdapter<String>(this.requireContext(),
                        R.layout.simple_list_item_1,arreglo)

                    binding.lista.setOnItemClickListener { adapterView, view, pos, l ->
                        val idSeleccionado = listaID.get(pos)

                        AlertDialog.Builder(this.requireContext()).setTitle("ATENCIÓN")
                            .setMessage("Qué deseas hacer con: ${idSeleccionado}")
                            .setPositiveButton("SELECCIONAR"){d, i->
                                binding.idarea.setText(idSeleccionado)
                                getDatos(idSeleccionado)
                                binding.insertar.isEnabled = true
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
                        var cadena = "${documneto.getString("descripcion")}\n" +
                                "${documneto.getString("division")}"
                        arreglo.add(cadena)
                        listaID.add(documneto.id)
                    }
                    binding.lista.adapter = ArrayAdapter<String>(this.requireContext(),
                        R.layout.simple_list_item_1,arreglo)

                    binding.lista.setOnItemClickListener { adapterView, view, pos, l ->
                        val idSeleccionado = listaID.get(pos)

                        AlertDialog.Builder(this.requireContext()).setTitle("ATENCIÓN")
                            .setMessage("Qué deseas hacer con: ${idSeleccionado}")
                            .setPositiveButton("SELECCIONAR"){d, i->
                                binding.idarea.setText(idSeleccionado)
                                getDatos(idSeleccionado)
                                idAr = idSeleccionado
                                binding.insertar.isEnabled = true
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
    }

    private fun getDatos(idSeleccionado: String) {
        val baseRemota = FirebaseFirestore.getInstance()
        baseRemota.collection("area")
            .document(idSeleccionado)
            .get()
            .addOnSuccessListener {
                descrip = it.getString("descripcion").toString()
                divi = it.getString("division").toString()
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