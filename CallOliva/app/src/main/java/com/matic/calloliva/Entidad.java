package com.matic.calloliva;

/**
 * Created by matic on 11/05/16.
 */
public class Entidad {

    private int id;
    private String nombre;
    private String descripcion;
    private String telefono;
    private double lat;
    private double lon;
    private String calle;
    private int nroCalle;
    private String ciudad;
    private String provincia;
    private String pais;
    private int logo;
    private String email;

    public Entidad(int id,String nombre, String descripcion, String telefono, double lat, double lon, String calle,int nroCalle, String ciudad,String provincia,String pais, int logo, String email) {
        this.id=id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.telefono = telefono;
        this.lat = lat;
        this.lon = lon;
        this.calle = calle;
        this.nroCalle=nroCalle;
        this.ciudad = ciudad;
        this.provincia=provincia;
        this.logo = logo;
        this.pais = pais;
        this.email=email;

    }

    public int getId(){
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getTelefono() {
        return telefono;
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

    public String getCalle() {
        return calle;
    }

    public int getNroCalle() {
        return nroCalle;
    }

    public String getCiudad() {
        return ciudad;
    }

    public String getProvincia() {
        return provincia;
    }

    public String getPais() {
        return pais;
    }

    public int getLogo() {
        return logo;
    }

    public String getEmail(){return email;}


    public void setId(int id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public void setNroCalle(int nroCalle) {
        this.nroCalle = nroCalle;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public void setLogo(int logo) {
        this.logo = logo;
    }

    public void setEmail(String email) {
        this.email = email;
    }



    @Override
    public String toString() {
        return "Entidad{" +
                "id='" + id + '\'' +
                "nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", telefono='" + telefono + '\'' +
                ", lat=" + lat +
                ", lon=" + lon +
                ", calle='" + calle + '\'' +
                ", nroCalle=" + nroCalle +
                ", ciudad='" + ciudad + '\'' +
                ", provincia='" + provincia+ '\'' +
                ", pais='" + pais + '\'' +
                ", logo='" + logo + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
