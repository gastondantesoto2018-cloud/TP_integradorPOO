package biblioteca;

import java.util.ArrayList;

public class Libro {

    private String titulo;
    private int edicion;
    private String editorial;
    private int anio;
    // Cardinalidad: un libro tiene 0..* préstamos (su ficha de préstamos)
    private ArrayList<Prestamo> prestamos;

    public Libro(String p_titulo, int p_edicion, String p_editorial, int p_anio) {
        this.titulo = p_titulo;
        this.edicion = p_edicion;
        this.editorial = p_editorial;
        this.anio = p_anio;
        this.prestamos = new ArrayList<Prestamo>();
    }

    // ----- Getters y setters -----
    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String p_titulo) {
        this.titulo = p_titulo;
    }

    public int getEdicion() {
        return edicion;
    }

    public void setEdicion(int p_edicion) {
        this.edicion = p_edicion;
    }

    public String getEditorial() {
        return editorial;
    }

    public void setEditorial(String p_editorial) {
        this.editorial = p_editorial;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int p_anio) {
        this.anio = p_anio;
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

    /** Último préstamo del libro, o null si nunca se prestó. */
    public Prestamo ultimoPrestamo() {
        if (this.getPrestamos().isEmpty()) {
            return null;
        }
        return this.getPrestamos().get(this.getPrestamos().size() - 1);
    }

    /** true si el libro se encuentra prestado. */
    public boolean prestado() {
        Prestamo ultimo = this.ultimoPrestamo();
        return ultimo != null && ultimo.estaEnPoderDelSocio();
    }

    @Override
    public String toString() {
        return "Titulo: " + this.getTitulo();
    }
}
