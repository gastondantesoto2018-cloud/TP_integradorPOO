package biblioteca;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashSet;

public class Biblioteca {

    private String nombre;
    // Cardinalidad: la biblioteca tiene 0..* libros y 0..* socios
    private ArrayList<Libro> libros;
    private ArrayList<Socio> socios;

    public Biblioteca(String p_nombre) {
        this.nombre = p_nombre;
        this.libros = new ArrayList<Libro>();
        this.socios = new ArrayList<Socio>();
    }

    // ----- Getters y setters -----
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String p_nombre) {
        this.nombre = p_nombre;
    }

    /** Colección de libros (para recorrer con un for). */
    public ArrayList<Libro> getLibros() {
        return libros;
    }

    public void setLibros(ArrayList<Libro> p_libros) {
        this.libros = p_libros;
    }

    /** Colección de socios (para recorrer con un for). */
    public ArrayList<Socio> getSocios() {
        return socios;
    }

    public void setSocios(ArrayList<Socio> p_socios) {
        this.socios = p_socios;
    }

    // ----- Altas -----
    public void nuevoLibro(String p_titulo, int p_edicion, String p_editorial, int p_anio) {
        this.getLibros().add(new Libro(p_titulo, p_edicion, p_editorial, p_anio));
    }

    public void nuevoSocioEstudiante(int p_dniSocio, String p_nombre, String p_carrera) {
        this.getSocios().add(new Estudiante(p_dniSocio, p_nombre, p_carrera));
    }

    public void nuevoSocioDocente(int p_dniSocio, String p_nombre, String p_area) {
        this.getSocios().add(new Docente(p_dniSocio, p_nombre, p_area));
    }

    // ----- Préstamos -----
    /** Crea el préstamo y lo agrega en el libro y en el socio. false si no se pudo prestar. */
    public boolean prestarLibro(Calendar p_fechaRetiro, Socio p_socio, Libro p_libro) {
        if (p_libro.prestado() || !p_socio.puedePedir()) {
            return false;
        }
        Prestamo prestamo = new Prestamo(p_fechaRetiro, p_socio, p_libro);
        p_libro.agregarPrestamo(prestamo);
        p_socio.agregarPrestamo(prestamo);
        return true;
    }

    public void devolverLibro(Libro p_libro) throws LibroNoPrestadoException {
        if (!p_libro.prestado()) {
            throw new LibroNoPrestadoException("El libro " + p_libro.getTitulo()
                    + " no se puede devolver ya que se encuentra en la biblioteca");
        }
        p_libro.ultimoPrestamo().registrarFechaDevolucion(Calendar.getInstance());
    }

    // ----- Consultas -----
    public int cantidadDeSociosPorTipo(String p_objeto) {
        int cant = 0;
        for (Socio s : this.getSocios()) {
            if (s.soyDeLaClase().equals(p_objeto)) {
                cant++;
            }
        }
        return cant;
    }

    /** Préstamos sin devolver cuyo plazo está vencido a la fecha de hoy. */
    public ArrayList<Prestamo> prestamosVencidos() {
        ArrayList<Prestamo> vencidos = new ArrayList<Prestamo>();
        Calendar hoy = Calendar.getInstance();
        for (Libro l : this.getLibros()) {
            Prestamo p = l.ultimoPrestamo();
            if (l.prestado() && p.vencido(hoy)) {
                vencidos.add(p);
            }
        }
        return vencidos;
    }

    public ArrayList<Docente> docentesResponsables() {
        ArrayList<Docente> responsables = new ArrayList<Docente>();
        for (Socio s : this.getSocios()) {
            if (s instanceof Docente && ((Docente) s).esResponsable()) {
                responsables.add((Docente) s);
            }
        }
        return responsables;
    }

    public String quienTieneElLibro(Libro p_libro) throws LibroNoPrestadoException {
        if (!p_libro.prestado()) {
            throw new LibroNoPrestadoException("El libro se encuentra en la biblioteca");
        }
        return p_libro.ultimoPrestamo().getSocio().getNombre();
    }

    /** Devuelve el socio con ese DNI, o null si no existe. */
    public Socio buscarSocio(int p_dni) {
        for (Socio s : this.getSocios()) {
            if (s.getDniSocio() == p_dni) {
                return s;
            }
        }
        return null;
    }

    /** Devuelve el libro con ese título, o null si no existe. */
    public Libro buscarLibro(String p_titulo) {
        for (Libro l : this.getLibros()) {
            if (l.getTitulo().equals(p_titulo)) {
                return l;
            }
        }
        return null;
    }

    // ----- Listados -----
    public String listaDeSocios() {
        String lista = "Lista de Socios:\n\n";
        int i = 1;
        for (Socio s : this.getSocios()) {
            lista += i + ") " + s.toString() + "\n";
            i++;
        }
        String asteriscos = "**************************************\n";
        lista += asteriscos;
        lista += "Cantidad de Socios del tipo Estudiante: " + this.cantidadDeSociosPorTipo("Estudiante") + "\n";
        lista += "Cantidad de Socios del tipo Docente: " + this.cantidadDeSociosPorTipo("Docente") + "\n";
        lista += asteriscos;
        return lista;
    }

    public String listaDeLibros() {
        String lista = "Lista de Libros:\n\n";
        int i = 1;
        for (Libro l : this.getLibros()) {
            lista += i + ") " + l.toString() + " || Prestado: (" + (l.prestado() ? "Si" : "No") + ")\n";
            i++;
        }
        return lista;
    }

    /** Títulos sin repetir (si hay más de un ejemplar). */
    public String listaDeTitulos() {
        LinkedHashSet<String> titulos = new LinkedHashSet<String>();
        for (Libro l : this.getLibros()) {
            titulos.add(l.getTitulo());
        }
        String lista = "Lista de Títulos:\n\n";
        int i = 1;
        for (String t : titulos) {
            lista += i + ") " + t + "\n";
            i++;
        }
        return lista;
    }

    public String listaDeDocentesResponsables() {
        String lista = "Lista de Docentes Responsables:\n\n";
        for (Docente d : this.docentesResponsables()) {
            lista += "* " + d.toString() + "\n";
        }
        return lista;
    }
}
