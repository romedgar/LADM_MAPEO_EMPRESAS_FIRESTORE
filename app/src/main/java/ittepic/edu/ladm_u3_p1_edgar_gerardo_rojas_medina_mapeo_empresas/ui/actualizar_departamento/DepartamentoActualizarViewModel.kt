package ittepic.edu.ladm_u3_p1_edgar_gerardo_rojas_medina_mapeo_empresas.ui.isertar_departamento

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DepartamentoActualizarViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Departamento"
    }
    val text: LiveData<String> = _text
}