package es.mira.progesin.services;
public class Alerta {
    private long id;
    private String nombre;
    private String rut;
    private String edad;
 
    public Alerta(long id, String nombre, String rut, String edad) {
        super();
        this.id = id;
        this.nombre = nombre;
        this.rut = rut;
        this.edad = edad;
    }
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getRut() {
        return rut;
    }
    public void setRut(String rut) {
        this.rut = rut;
    }
    public String getEdad() {
        return edad;
    }
    public void setEdad(String edad) {
        this.edad = edad;
    }
 
}