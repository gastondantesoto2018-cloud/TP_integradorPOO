package biblioteca;

import java.util.Calendar;

public class Docente extends Socio {

    private String area;

    public Docente(int p_dniSocio, String p_nombre, String p_area) {
        super(p_dniSocio, p_nombre, 5); // inicialmente 5 días de préstamo
        this.area = p_area;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String p_area) {
        this.area = p_area;
    }

    /** true si nunca tuvo ni tiene un préstamo vencido. */
    public boolean esResponsable() {
        Calendar hoy = Calendar.getInstance();
        for (Prestamo p : this.getPrestamos()) {
            Calendar fecha = p.estaEnPoderDelSocio() ? hoy : p.getFechaDevolucion();
            if (p.vencido(fecha)) {
                return false;
            }
        }
        return true;
    }

    /** Premio a la responsabilidad: suma días de préstamo. */
    public void cambiarDiasDePrestamo(int p_dias) {
        this.setDiasPrestamo(this.getDiasPrestamo() + p_dias);
    }

    @Override
    public boolean puedePedir() {
        return super.puedePedir();
    }

    @Override
    public String soyDeLaClase() {
        return "Docente";
    }
}
