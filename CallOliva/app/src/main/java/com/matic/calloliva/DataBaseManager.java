package com.matic.calloliva;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.renderscript.Script;

/**
 * Created by matic on 11/05/16.
 */
public class DataBaseManager {

    public static final String TABLE_NAME="Entidad";

    public static final String CN_ID="_id";
    public static final String CN_NAME="nombre";
    public static final String CN_DESCRIPCION="descripcion";
    public static final String CN_TELEFONO="telefono";
    public static final String CN_LAT="latitud";
    public static final String CN_LON="longitud";
    public static final String CN_CALLE="calle";
    public static final String CN_NCALLE="nroCalle";
    public static final String CN_CIUDAD="ciudad";
    public static final String CN_PAIS="pais";
    public static final String CN_PROVINCIA="provincia";
    public static final String CN_LOGO="logo";
    public static final String CN_EMAIL="email";


    //sentencia sql para crear tabla}
    public static final String CREATE_TABLE="CREATE TABLE "+TABLE_NAME+" ("
            +CN_ID+" integer primary key autoincrement,"
            +CN_NAME+" text not null,"
            +CN_DESCRIPCION+" text ,"
            +CN_TELEFONO+" text ,"
            +CN_LAT+" double ,"
            +CN_LON+" double ,"
            +CN_CALLE+" text not null,"
            +CN_NCALLE+" text not null,"
            +CN_CIUDAD+" text ,"
            +CN_PROVINCIA+" text ,"
            +CN_PAIS+" text ,"
            +CN_LOGO+" integer ,"
            +CN_EMAIL+" text);";


    private DBHelper helper;
    private SQLiteDatabase db;

    public DataBaseManager(Context context) {
        helper = new DBHelper(context);
        db = helper.getWritableDatabase();
    }


    public ContentValues generarContentValues(String nombre, String descripcion, String telefono,double lat, double lon, String calle, int ncalle,String ciudad, String provincia, String pais, int logo,String email){


        ContentValues valores=new ContentValues();
        valores.put(CN_NAME,nombre);
        valores.put(CN_DESCRIPCION,descripcion);
        valores.put(CN_TELEFONO,telefono);
        valores.put(CN_LAT,lat);
        valores.put(CN_LON,lon);
        valores.put(CN_CALLE,calle);
        valores.put(CN_NCALLE,ncalle);
        valores.put(CN_CIUDAD,ciudad);
        valores.put(CN_PROVINCIA,provincia);
        valores.put(CN_PAIS,pais);
        valores.put(CN_LOGO,logo);
        valores.put(CN_EMAIL,email);

        return valores;

    }


    public void insertar(String nombre, String descripcion, String telefono,double lat, double lon, String calle, int ncalle,String ciudad, String provincia, String pais, int logo, String email){

        //db.insert(TABLA, NUllColumnHack, ContentValues)
        db.insert(TABLE_NAME,null,generarContentValues(nombre,descripcion,telefono,lat,lon,calle,ncalle,ciudad,provincia,pais,logo,email));

    }

    public void eliminar(String nombre)
    {
        //db.delete(TABLA, Clausula WHERE, Argumentos WHERE)
        db.delete(TABLE_NAME,CN_NAME+"=?",new String[]{nombre});
    }


    public void update(String nombre, String descripcion, String telefono,double lat, double lon, String calle, int ncalle,String ciudad, String provincia, String pais, int logo,String email){

        db.update(TABLE_NAME,generarContentValues(nombre,descripcion,telefono,lat,lon,calle,ncalle,ciudad,provincia,pais,logo,email),CN_NAME+"=?",new String[]{nombre});

    }

    public Cursor cargarCursorEntidades(){

        String[] columnas=new String[]{CN_ID,CN_NAME,CN_DESCRIPCION,CN_TELEFONO,CN_LAT,CN_LON,CN_CALLE,CN_NCALLE,CN_CIUDAD,CN_PROVINCIA,CN_PAIS,CN_LOGO,CN_EMAIL};

        return db.query(TABLE_NAME,columnas,null,null,null,null,null);

    }


    public Cursor buscarEntidad(CharSequence nombre){

        String[] columnas=new String[]{CN_ID,CN_NAME,CN_DESCRIPCION,CN_TELEFONO,CN_LAT,CN_LON,CN_CALLE,CN_NCALLE,CN_CIUDAD,CN_PROVINCIA,CN_PAIS,CN_LOGO,CN_EMAIL};

        //return db.query(TABLE_NAME,columnas,CN_NAME+"=?",new String[]{nombre},null,null,null);

       return db.query(true, TABLE_NAME,columnas, CN_NAME + " LIKE ?",
                new String[] { nombre+"%" }, null, null, null,
                null);

    }







}
