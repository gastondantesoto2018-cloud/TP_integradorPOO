package biblioteca;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Prestamo {

    private Calendar fechaRetiro;
    private Calendar fechaDevolucion;
    // Cardinalidad: cada préstamo tiene exactamente 1 socio y 1 libro
    private Socio socio;
    private Libro libro;

    public Prestamo(Calendar p_fechaRetiro, Socio p_socio, Libro p_libro) {
        this.fechaRetiro = p_fechaRetiro;
        this.fechaDevolucion = null; // se crea solo con fecha de retiro
        this.socio = p_socio;
        this.libro = p_libro;
    }

    // ----- Getters y setters -----
    public Calendar getFechaRetiro() {
        return fechaRetiro;
    }

    public void setFechaRetiro(Calendar p_fechaRetiro) {
        this.fechaRetiro = p_fechaRetiro;
    }

    public Calendar getFechaDevolucion() {
        return fechaDevolucion;
    }

    public void setFechaDevolucion(Calendar p_fechaDevolucion) {
        this.fechaDevolucion = p_fechaDevolucion;
    }

    public Socio getSocio() {
        return socio;
    }

    public void setSocio(Socio p_socio) {
        this.socio = p_socio;
    }

    public Libro getLibro() {
        return libro;
    }

    public void setLibro(Libro p_libro) {
        this.libro = p_libro;
    }

    // ----- Comportamiento -----
    public void registrarFechaDevolucion(Calendar p_fecha) {
        this.setFechaDevolucion(p_fecha);
    }

    /** true si la fecha pasada es mayor que la fecha de vencimiento (retiro + días del socio). */
    public boolean vencido(Calendar p_fecha) {
        Calendar vencimiento = soloFecha(this.getFechaRetiro());
        vencimiento.add(Calendar.DAY_OF_MONTH, this.getSocio().getDiasPrestamo());
        return soloFecha(p_fecha).after(vencimiento);
    }

    /** true si todavía no fue devuelto. */
    public boolean estaEnPoderDelSocio() {
        return this.getFechaDevolucion() == null;
    }

    private static Calendar soloFecha(Calendar c) {
        Calendar r = (Calendar) c.clone();
        r.set(Calendar.HOUR_OF_DAY, 0);
        r.set(Calendar.MINUTE, 0);
        r.set(Calendar.SECOND, 0);
        r.set(Calendar.MILLISECOND, 0);
        return r;
    }

    @Override
    public String toString() {
        SimpleDateFormat f = new SimpleDateFormat("yyyy/MM/dd");
        String retiro = f.format(this.getFechaRetiro().getTime());
        String devolucion = (this.getFechaDevolucion() == null)
                ? "-" : f.format(this.getFechaDevolucion().getTime());
        return "Retiro: " + retiro + " - Devolución: " + devolucion
                + "\nLibro: " + this.getLibro().getTitulo()
                + "\nSocio: " + this.getSocio().getNombre();
    }
}
