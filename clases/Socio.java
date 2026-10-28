package biblioteca;

import java.util.ArrayList;
import java.util.Calendar;

public abstract class Socio {

    private int dniSocio;
    private String nombre;
    private int diasPrestamo;
    // Cardinalidad: un socio tiene 0..* préstamos
    private ArrayList<Prestamo> prestamos;

    public Socio(int p_dniSocio, String p_nombre, int p_diasPrestamo) {
        this.dniSocio = p_dniSocio;
        this.nombre = p_nombre;
        this.diasPrestamo = p_diasPrestamo;
        this.prestamos = new ArrayList<Prestamo>();
    }

    // ----- Getters y setters -----
    public int getDniSocio() {
        return dniSocio;
    }

    public void setDniSocio(int p_dniSocio) {
        this.dniSocio = p_dniSocio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String p_nombre) {
        this.nombre = p_nombre;
    }

    public int getDiasPrestamo() {
        return diasPrestamo;
    }

    public void setDiasPrestamo(int p_diasPrestamo) {
        this.diasPrestamo = p_diasPrestamo;
    }

    /** Colección de préstamos (para recorrer con un for). */
    public ArrayList<Prestamo> getPrestamos() {
        return prestamos;
    }

    public void setPrestamos(ArrayList<Prestamo> p_prestamos) {
        this.prestamos = p_prestamos;
    }

    // ----- Comportamiento -----
    public void agregarPrestamo(Prestamo p_prestamo) {
        this.getPrestamos().add(p_prestamo);
    }

    /** Cantidad de libros en poder del socio (préstamos sin devolver). */
    public int cantLibrosPrestados() {
        int cant = 0;
        for (Prestamo p : this.getPrestamos()) {
            if (p.estaEnPoderDelSocio()) {
                cant++;
            }
        }
        return cant;
    }

    /** true si el socio tiene algún préstamo sin devolver y vencido a hoy. */
    protected boolean tienePrestamoVencido() {
        Calendar hoy = Calendar.getInstance();
        for (Prestamo p : this.getPrestamos()) {
            if (p.estaEnPoderDelSocio() && p.vencido(hoy)) {
                return true;
            }
        }
        return false;
    }

    /** true si el socio no tiene ningún préstamo vencido. */
    public boolean puedePedir() {
        return !this.tienePrestamoVencido();
    }

    public abstract String soyDeLaClase();

    @Override
    public String toString() {
        return "D.N.I.: " + this.getDniSocio() + " || " + this.getNombre()
                + " (" + this.soyDeLaClase() + ") || Libros Prestados: "
                + this.cantLibrosPrestados();
    }
}
