package ittepic.edu.ladm_u3_p1_edgar_gerardo_rojas_medina_mapeo_empresas

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteException

class Area(este: Context) {
    var idArea = 0
    var descripcion =""
    var division = ""
    var cantidadEmpleados = 0
    private var este = este
    private var err = ""

    fun insertar(): Boolean{
        var baseDatos = BaseDatos(este, "mapeo",null,1)
        err = ""
        try {
            val tabla = baseDatos.writableDatabase

            var datos = ContentValues()
            //datos.put("IDAREA",idArea)
            datos.put("DESCRIPCION",descripcion)
            datos.put("DIVISION",division)
            datos.put("CANTIDAD_EMPLEADOS",cantidadEmpleados)

            var resultado = tabla.insert("AREA","IDAREA",datos)

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

    fun mostrarTodos(): ArrayList<Area>{
        val baseDatos = BaseDatos(este, "mapeo",null,1)
        val arreglo = ArrayList<Area>()
        err = ""
        try {
            var tabla = baseDatos.readableDatabase
            var SQLSELECT = "SELECT * FROM AREA"


            var cursor = tabla.rawQuery(SQLSELECT,null)
            if(cursor.moveToFirst()){
                do {
                    val area = Area(este)
                    area.idArea = cursor.getInt(0)
                    area.descripcion = cursor.getString(1)
                    area.division = cursor.getString(2)
                    area.cantidadEmpleados = cursor.getInt(3)
                    arreglo.add(area)
                }while (cursor.moveToNext())
            }

        }catch (err: SQLiteException){
            this.err = err.message!!

        }finally {
            baseDatos.close()
        }
        return arreglo
    }

    fun mostrarUno(id: Int): Area{
        val baseDatos = BaseDatos(este, "mapeo",null,1)
        val area = Area(este)
        err = ""
        try {
            val tabla = baseDatos.readableDatabase
            val SQL_SELECT = "SELECT * FROM AREA WHERE IDAREA = ?"


            var cursor = tabla.rawQuery(SQL_SELECT, arrayOf(id.toString()))
            if(cursor.moveToFirst()){

                area.idArea = cursor.getInt(0)
                area.descripcion = cursor.getString(1)
                area.division = cursor.getString(2)
                area.cantidadEmpleados = cursor.getInt(3)
            }
        }catch (err: SQLiteException){
            this.err = err.message!!

        }finally {
            baseDatos.close()
        }
        return area
    }

    fun mostrarDescrip(descripcion : String): ArrayList<Area>{
        val baseDatos = BaseDatos(este, "mapeo",null,1)
        val arreglo = ArrayList<Area>()
        err = ""
        try {
            var tabla = baseDatos.readableDatabase
            var SQLSELECT = "SELECT * FROM AREA WHERE DESCRIPCION = ?"


            var cursor = tabla.rawQuery(SQLSELECT, arrayOf(descripcion))
            if(cursor.moveToFirst()){
                do {
                    val area = Area(este)
                    area.idArea = cursor.getInt(0)
                    area.descripcion = cursor.getString(1)
                    area.division = cursor.getString(2)
                    area.cantidadEmpleados = cursor.getInt(3)
                    arreglo.add(area)
                }while (cursor.moveToNext())
            }

        }catch (err: SQLiteException){
            this.err = err.message!!

        }finally {
            baseDatos.close()
        }
        return arreglo
    }

    fun mostrarDiv(division : String): ArrayList<Area>{
        val baseDatos = BaseDatos(este, "mapeo",null,1)
        val arreglo = ArrayList<Area>()
        err = ""
        try {
            var tabla = baseDatos.readableDatabase
            var SQLSELECT = "SELECT * FROM AREA WHERE DIVISION = ?"


            var cursor = tabla.rawQuery(SQLSELECT, arrayOf(division))
            if(cursor.moveToFirst()){
                do {
                    val area = Area(este)
                    area.idArea = cursor.getInt(0)
                    area.descripcion = cursor.getString(1)
                    area.division = cursor.getString(2)
                    area.cantidadEmpleados = cursor.getInt(3)
                    arreglo.add(area)
                }while (cursor.moveToNext())
            }

        }catch (err: SQLiteException){
            this.err = err.message!!

        }finally {
            baseDatos.close()
        }
        return arreglo
    }

    fun mostrarCombinada(descripcion: String,division : String): ArrayList<Area>{
        val baseDatos = BaseDatos(este, "mapeo",null,1)
        val arreglo = ArrayList<Area>()
        err = ""
        try {
            var tabla = baseDatos.readableDatabase
            var SQLSELECT = "SELECT * FROM AREA WHERE DESCRIPCION = ? AND DIVISION = ?"


            var cursor = tabla.rawQuery(SQLSELECT, arrayOf(descripcion, division))
            if(cursor.moveToFirst()){
                do {
                    val area = Area(este)
                    area.idArea = cursor.getInt(0)
                    area.descripcion = cursor.getString(1)
                    area.division = cursor.getString(2)
                    area.cantidadEmpleados = cursor.getInt(3)
                    arreglo.add(area)
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

            datosActualizados.put("DESCRIPCION",descripcion)
            datosActualizados.put("DIVISION",division)
            datosActualizados.put("CANTIDAD_EMPLEADOS",cantidadEmpleados)

            val respuesta = tabla.update("AREA", datosActualizados, "IDAREA=?", arrayOf(idArea.toString()))

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

    fun eliminar(idAreaEliminar: Int): Boolean {
        val baseDatos = BaseDatos(este,"mapeo",null,1)
        err = ""
        try {
            var tabla = baseDatos.writableDatabase
            val resultado = tabla.delete("AREA","IDAREA=?", arrayOf(idAreaEliminar.toString()))
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
            val resultado = tabla.delete("AREA","IDAREA=?", arrayOf(idArea.toString()))
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