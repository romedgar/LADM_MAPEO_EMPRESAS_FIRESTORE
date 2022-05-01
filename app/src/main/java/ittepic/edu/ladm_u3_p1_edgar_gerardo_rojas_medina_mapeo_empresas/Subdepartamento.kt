package ittepic.edu.ladm_u3_p1_edgar_gerardo_rojas_medina_mapeo_empresas

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteException

class Subdepartamento(este: Context) {
    var idArea = 0
    var piso =""
    var idEdificio = ""
    var idSubdepto = 0
    var descripcion = ""
    var division = ""

    private var este = este
    private var err = ""

    fun insertar(): Boolean{
        var baseDatos = BaseDatos(este, "mapeo",null,1)
        err = ""
        try {
            val tabla = baseDatos.writableDatabase

            var datos = ContentValues()
            //datos.put("IDAREA",idArea)
            datos.put("IDEDIFICIO",idEdificio)
            datos.put("PISO",piso)
            datos.put("IDAREA",idArea)

            var resultado = tabla.insert("SUBDEPARTAMENTO","IDSUBDEPTO",datos)

            if (resultado == -1L){
                return false
            }

        }catch (err: SQLiteException){
            this.err = err.message!!
            return false
        }finally {
            baseDatos.close()
        }
        return true
    }

    fun mostrarTodos(): ArrayList<Subdepartamento>{
        val baseDatos = BaseDatos(este, "mapeo",null,1)
        val arreglo = ArrayList<Subdepartamento>()
        err = ""
        try {
            var tabla = baseDatos.readableDatabase
            var SQLSELECT = "SELECT * FROM SUBDEPARTAMENTO"


            var cursor = tabla.rawQuery(SQLSELECT,null)
            if(cursor.moveToFirst()){
                do {
                    val subdepto = Subdepartamento(este)
                    subdepto.idSubdepto = cursor.getInt(0)
                    subdepto.idEdificio = cursor.getString(1)
                    subdepto.piso = cursor.getString(2)
                    subdepto.idArea = cursor.getInt(3)
                    subdepto.descripcion = ObtenerDescripArea(subdepto.idArea)
                    subdepto.division = ObtenerDivArea(subdepto.idArea)
                    arreglo.add(subdepto)
                }while (cursor.moveToNext())
            }

        }catch (err: SQLiteException){
            this.err = err.message!!

        }finally {
            baseDatos.close()
        }
        return arreglo
    }

    fun mostrarUno(id: Int): Subdepartamento{
        val baseDatos = BaseDatos(este, "mapeo",null,1)
        val subdepto = Subdepartamento(este)
        err = ""
        try {
            val tabla = baseDatos.readableDatabase
            val SQL_SELECT = "SELECT * FROM SUBDEPARTAMENTO WHERE IDSUBDEPTO = ?"


            var cursor = tabla.rawQuery(SQL_SELECT, arrayOf(id.toString()))
            if(cursor.moveToFirst()){

                subdepto.idSubdepto = cursor.getInt(0)
                subdepto.idEdificio = cursor.getString(1)
                subdepto.piso = cursor.getString(2)
                subdepto.idArea = cursor.getInt(3)
                subdepto.descripcion = ObtenerDescripArea(subdepto.idArea)
                subdepto.division = ObtenerDivArea(subdepto.idArea)
            }
        }catch (err: SQLiteException){
            this.err = err.message!!

        }finally {
            baseDatos.close()
        }
        return subdepto
    }

    fun ObtenerDescripArea(id : Int) : String{
        val baseDatos = BaseDatos(este, "mapeo",null,1)
        var descripObtenida = ""
        err = ""
        try {
            val tabla = baseDatos.readableDatabase
            val SQL_SELECT = "SELECT * FROM AREA WHERE IDAREA = ?"


            var cursor = tabla.rawQuery(SQL_SELECT, arrayOf(id.toString()))
            if(cursor.moveToFirst()){

                descripObtenida = cursor.getString(1)
            }
        }catch (err: SQLiteException){
            this.err = err.message!!

        }finally {
            baseDatos.close()
        }
        return descripObtenida

    }

    fun ObtenerDivArea(id : Int) : String{
        val baseDatos = BaseDatos(este, "mapeo",null,1)
        var divObtenida = ""
        err = ""
        try {
            val tabla = baseDatos.readableDatabase
            val SQL_SELECT = "SELECT * FROM AREA WHERE IDAREA = ?"


            var cursor = tabla.rawQuery(SQL_SELECT, arrayOf(id.toString()))
            if(cursor.moveToFirst()){

                divObtenida = cursor.getString(2)
            }
        }catch (err: SQLiteException){
            this.err = err.message!!

        }finally {
            baseDatos.close()
        }
        return divObtenida

    }

    fun ObtenerIdDescrip(descripcion: String) : Int{
        val baseDatos = BaseDatos(este, "mapeo",null,1)
        var id = 0
        err = ""
        try {
            val tabla = baseDatos.readableDatabase
            val SQL_SELECT = "SELECT * FROM AREA WHERE DESCRIPCION = ?"


            var cursor = tabla.rawQuery(SQL_SELECT, arrayOf(descripcion))
            if(cursor.moveToFirst()){

                id = cursor.getInt(0)
            }
        }catch (err: SQLiteException){
            this.err = err.message!!

        }finally {
            baseDatos.close()
        }
        return id
    }

    fun ObtenerIdDivision(division: String) : ArrayList<Int>{
        val baseDatos = BaseDatos(este, "mapeo",null,1)
        val arreglo = ArrayList<Int>()
        err = ""
        try {
            val tabla = baseDatos.readableDatabase
            val SQL_SELECT = "SELECT * FROM AREA WHERE DIVISION = ?"


            var cursor = tabla.rawQuery(SQL_SELECT, arrayOf(division))
            if(cursor.moveToFirst()){
                do {
                    arreglo.add(cursor.getInt(0))
                }while (cursor.moveToNext())
            }
        }catch (err: SQLiteException){
            this.err = err.message!!

        }finally {
            baseDatos.close()
        }
        return arreglo
    }

    fun ObtenerIdDescripyDiv(descripcion: String, division: String) : Int{
        val baseDatos = BaseDatos(este, "mapeo",null,1)
        var id = 0

        err = ""
        try {
            val tabla = baseDatos.readableDatabase
            val SQL_SELECT = "SELECT * FROM AREA WHERE DESCRIPCION = ? AND DIVISION = ?"


            var cursor = tabla.rawQuery(SQL_SELECT, arrayOf(descripcion,division))
            if(cursor.moveToFirst()){

                id = cursor.getInt(0)
            }
        }catch (err: SQLiteException){
            this.err = err.message!!

        }finally {
            baseDatos.close()
        }
        return id
    }

    fun mostrarDescrip(descripcion : String): ArrayList<Subdepartamento>{
        val baseDatos = BaseDatos(este, "mapeo",null,1)
        val arreglo = ArrayList<Subdepartamento>()
        var id = ObtenerIdDescrip(descripcion)
        err = ""
        try {
            var tabla = baseDatos.readableDatabase
            var SQLSELECT = "SELECT * FROM SUBDEPARTAMENTO WHERE IDAREA = ?"


            var cursor = tabla.rawQuery(SQLSELECT, arrayOf(id.toString()))
            if(cursor.moveToFirst()){
                do {
                    val subdepto = Subdepartamento(este)
                    subdepto.idSubdepto = cursor.getInt(0)
                    subdepto.idEdificio = cursor.getString(1)
                    subdepto.piso = cursor.getString(2)
                    subdepto.idArea = cursor.getInt(3)
                    subdepto.descripcion = ObtenerDescripArea(subdepto.idArea)
                    subdepto.division = ObtenerDivArea(subdepto.idArea)
                    arreglo.add(subdepto)
                }while (cursor.moveToNext())
            }

        }catch (err: SQLiteException){
            this.err = err.message!!

        }finally {
            baseDatos.close()
        }
        return arreglo
    }

    fun mostrarDiv(division : String): ArrayList<Subdepartamento>{
        val baseDatos = BaseDatos(este, "mapeo",null,1)
        val arreglo = ArrayList<Subdepartamento>()
        var id = ObtenerIdDivision(division)
        var cadena = "("
        err = ""

        (0..id.size-2).forEach {
            cadena+= id[it].toString()+","
        }
        cadena+= id[id.size-1].toString()+")"

        try {
            var tabla = baseDatos.readableDatabase
            var SQLSELECT = "SELECT * FROM SUBDEPARTAMENTO WHERE IDAREA IN "+cadena


            var cursor = tabla.rawQuery(SQLSELECT,null)
            if(cursor.moveToFirst()){
                do {
                    val subdepto = Subdepartamento(este)
                    subdepto.idSubdepto = cursor.getInt(0)
                    subdepto.idEdificio = cursor.getString(1)
                    subdepto.piso = cursor.getString(2)
                    subdepto.idArea = cursor.getInt(3)
                    subdepto.descripcion = ObtenerDescripArea(subdepto.idArea)
                    subdepto.division = ObtenerDivArea(subdepto.idArea)
                    arreglo.add(subdepto)
                }while (cursor.moveToNext())
            }

        }catch (err: SQLiteException){
            this.err = err.message!!

        }finally {
            baseDatos.close()
        }
        return arreglo
    }

    fun mostrarEdi(edificio : String): ArrayList<Subdepartamento>{
        val baseDatos = BaseDatos(este, "mapeo",null,1)
        val arreglo = ArrayList<Subdepartamento>()
        err = ""
        try {
            var tabla = baseDatos.readableDatabase
            var SQLSELECT = "SELECT * FROM SUBDEPARTAMENTO WHERE IDEDIFICIO = ?"


            var cursor = tabla.rawQuery(SQLSELECT, arrayOf(edificio))
            if(cursor.moveToFirst()){
                do {
                    val subdepto = Subdepartamento(este)
                    subdepto.idSubdepto = cursor.getInt(0)
                    subdepto.idEdificio = cursor.getString(1)
                    subdepto.piso = cursor.getString(2)
                    subdepto.idArea = cursor.getInt(3)
                    subdepto.descripcion = ObtenerDescripArea(subdepto.idArea)
                    subdepto.division = ObtenerDivArea(subdepto.idArea)
                    arreglo.add(subdepto)
                }while (cursor.moveToNext())
            }

        }catch (err: SQLiteException){
            this.err = err.message!!

        }finally {
            baseDatos.close()
        }
        return arreglo
    }


    fun mostrarCombinada(descripcion: String,division : String, edificio: String): ArrayList<Subdepartamento>{
        val baseDatos = BaseDatos(este, "mapeo",null,1)
        val arreglo = ArrayList<Subdepartamento>()
        var id = ObtenerIdDescripyDiv(descripcion,division)
        err = ""
        try {
            var tabla = baseDatos.readableDatabase
            var SQLSELECT = "SELECT * FROM SUBDEPARTAMENTO WHERE IDAREA = ? AND IDEDIFICIO = ?"


            var cursor = tabla.rawQuery(SQLSELECT, arrayOf(id.toString(),edificio))
            if(cursor.moveToFirst()){
                do {
                    val subdepto = Subdepartamento(este)
                    subdepto.idSubdepto = cursor.getInt(0)
                    subdepto.idEdificio = cursor.getString(1)
                    subdepto.piso = cursor.getString(2)
                    subdepto.idArea = cursor.getInt(3)
                    subdepto.descripcion = ObtenerDescripArea(subdepto.idArea)
                    subdepto.division = ObtenerDivArea(subdepto.idArea)
                    arreglo.add(subdepto)
                }while (cursor.moveToNext())
            }

        }catch (err: SQLiteException){
            this.err = err.message!!

        }finally {
            baseDatos.close()
        }
        return arreglo
    }

    fun mostrarDesyDiv(descripcion: String,division : String): ArrayList<Subdepartamento>{
        val baseDatos = BaseDatos(este, "mapeo",null,1)
        val arreglo = ArrayList<Subdepartamento>()
        var id = ObtenerIdDescripyDiv(descripcion,division)
        err = ""
        try {
            var tabla = baseDatos.readableDatabase
            var SQLSELECT = "SELECT * FROM SUBDEPARTAMENTO WHERE IDAREA = ? "


            var cursor = tabla.rawQuery(SQLSELECT, arrayOf(id.toString()))
            if(cursor.moveToFirst()){
                do {
                    val subdepto = Subdepartamento(este)
                    subdepto.idSubdepto = cursor.getInt(0)
                    subdepto.idEdificio = cursor.getString(1)
                    subdepto.piso = cursor.getString(2)
                    subdepto.idArea = cursor.getInt(3)
                    subdepto.descripcion = ObtenerDescripArea(subdepto.idArea)
                    subdepto.division = ObtenerDivArea(subdepto.idArea)
                    arreglo.add(subdepto)
                }while (cursor.moveToNext())
            }

        }catch (err: SQLiteException){
            this.err = err.message!!

        }finally {
            baseDatos.close()
        }
        return arreglo
    }

    fun mostrarDescripEdi(descripcion: String, edificio: String): ArrayList<Subdepartamento>{
        val baseDatos = BaseDatos(este, "mapeo",null,1)
        val arreglo = ArrayList<Subdepartamento>()
        var id = ObtenerIdDescrip(descripcion)
        err = ""
        try {
            var tabla = baseDatos.readableDatabase
            var SQLSELECT = "SELECT * FROM SUBDEPARTAMENTO WHERE IDAREA = ? AND IDEDIFICIO = ?"


            var cursor = tabla.rawQuery(SQLSELECT, arrayOf(id.toString(),edificio))
            if(cursor.moveToFirst()){
                do {
                    val subdepto = Subdepartamento(este)
                    subdepto.idSubdepto = cursor.getInt(0)
                    subdepto.idEdificio = cursor.getString(1)
                    subdepto.piso = cursor.getString(2)
                    subdepto.idArea = cursor.getInt(3)
                    subdepto.descripcion = ObtenerDescripArea(subdepto.idArea)
                    subdepto.division = ObtenerDivArea(subdepto.idArea)
                    arreglo.add(subdepto)
                }while (cursor.moveToNext())
            }

        }catch (err: SQLiteException){
            this.err = err.message!!

        }finally {
            baseDatos.close()
        }
        return arreglo
    }

    fun mostrarDivEdi(division: String, edificio: String): ArrayList<Subdepartamento>{
        val baseDatos = BaseDatos(este, "mapeo",null,1)
        val arreglo = ArrayList<Subdepartamento>()
        var id = ObtenerIdDivision(division)
        var cadena = "("
        err = ""

        (0..id.size-2).forEach {
            cadena+= id[it].toString()+","
        }
        cadena+= id[id.size-1].toString()+")"
        try {
            var tabla = baseDatos.readableDatabase
            var SQLSELECT = "SELECT * FROM SUBDEPARTAMENTO WHERE IDAREA IN" + cadena +" AND IDEDIFICIO = ?"


            var cursor = tabla.rawQuery(SQLSELECT, arrayOf(edificio))
            if(cursor.moveToFirst()){
                do {
                    val subdepto = Subdepartamento(este)
                    subdepto.idSubdepto = cursor.getInt(0)
                    subdepto.idEdificio = cursor.getString(1)
                    subdepto.piso = cursor.getString(2)
                    subdepto.idArea = cursor.getInt(3)
                    subdepto.descripcion = ObtenerDescripArea(subdepto.idArea)
                    subdepto.division = ObtenerDivArea(subdepto.idArea)
                    arreglo.add(subdepto)
                }while (cursor.moveToNext())
            }

        }catch (err: SQLiteException){
            this.err = err.message!!

        }finally {
            baseDatos.close()
        }
        return arreglo
    }

    fun actualizar(): Boolean {
        val basedatos = BaseDatos(este, "mapeo",null,1)
        err = ""
        try{
            var tabla = basedatos.writableDatabase
            val datosActualizados = ContentValues()

            datosActualizados.put("IDEDIFICIO",idEdificio)
            datosActualizados.put("PISO",piso)
            datosActualizados.put("IDAREA",idArea)

            val respuesta = tabla.update("SUBDEPARTAMENTO", datosActualizados, "IDSUBDEPTO=?", arrayOf(idSubdepto.toString()))

            if(respuesta==0){
                return false
            }
        }catch (err: SQLiteException){
            this.err = err.message!!
            return false
        }finally {
            basedatos.close()
        }
        return true
    }

    fun eliminar(idDeptoEliminar: Int): Boolean {
        val baseDatos = BaseDatos(este,"mapeo",null,1)
        err = ""
        try {
            var tabla = baseDatos.writableDatabase
            val resultado = tabla.delete("SUBDEPARTAMENTO","IDSUBDEPTO=?", arrayOf(idDeptoEliminar.toString()))
            if(resultado == 0){
                return false
            }
        } catch (err: SQLiteException){
            this.err = err.message!!
            return false
        }finally {
            baseDatos.close()
        }
        return true
    }

    fun eliminar(): Boolean {
        val baseDatos = BaseDatos(este,"mapeo",null,1)
        err = ""
        try {
            var tabla = baseDatos.writableDatabase
            val resultado = tabla.delete("SUBDEPARTAMENTO","IDSUBDEPTO=?", arrayOf(idSubdepto.toString()))
            if(resultado == 0){
                return false
            }
        } catch (err: SQLiteException){
            this.err = err.message!!
            return false
        }finally {
            baseDatos.close()
        }
        return true
    }
}