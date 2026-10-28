package biblioteca;

import java.util.Calendar;

public class GestionBiblioteca {

    /** Devuelve una fecha desplazada la cantidad de días indicada respecto de hoy. */
    private static Calendar hace(int dias) {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_MONTH, -dias);
        return c;
    }

    public static void main(String[] args) {
        Biblioteca biblioteca = new Biblioteca("Biblioteca Central");

        // Libros
        biblioteca.nuevoLibro("JAVA. Como Programar", 5, "Pearson", 2014);
        biblioteca.nuevoLibro("Longman. Diccionario Pocket", 1, "Longman", 2010);
        biblioteca.nuevoLibro("Vivir para contarla", 2, "Sudamericana", 2002);
        biblioteca.nuevoLibro("Programando con JAVA", 3, "Anaya", 2012);
        biblioteca.nuevoLibro("Programando con JAVA", 3, "Anaya", 2012); // 2do ejemplar

        // Socios
        biblioteca.nuevoSocioDocente(14524782, "Juan Perez", "Programación");
        biblioteca.nuevoSocioDocente(17982110, "Juan Fernández", "Matemática");
        biblioteca.nuevoSocioDocente(10912002, "María Alegre", "Lengua");
        biblioteca.nuevoSocioEstudiante(28987498, "Francisco Paenza", "Informática");
        biblioteca.nuevoSocioEstudiante(31987123, "Cesar Milstein", "Biología");

        Libro java = biblioteca.getLibros().get(0);
        Libro longman = biblioteca.getLibros().get(1);
        Libro vivir = biblioteca.getLibros().get(2);
        Libro programando = biblioteca.getLibros().get(3);

        Socio perez = biblioteca.buscarSocio(14524782);
        Socio fernandez = biblioteca.buscarSocio(17982110);
        Socio paenza = biblioteca.buscarSocio(28987498);

        // Préstamos
        biblioteca.prestarLibro(hace(10), perez, longman);     // vencido (5 días)
        biblioteca.prestarLibro(hace(1), fernandez, java);      // en término
        biblioteca.prestarLibro(hace(2), paenza, programando);  // en término

        // Devolución
        try {
            biblioteca.devolverLibro(java);
        } catch (LibroNoPrestadoException e) {
            System.out.println(e.getMessage());
        }

        // Devolver un libro que no está prestado -> excepción
        try {
            biblioteca.devolverLibro(vivir);
        } catch (LibroNoPrestadoException e) {
            System.out.println(e.getMessage());
        }

        // Premio a docentes responsables
        for (Docente d : biblioteca.docentesResponsables()) {
            d.cambiarDiasDePrestamo(2);
        }

        // ¿Qué cantidad de socios de tipo Estudiante hay?
        System.out.println("Cantidad de Estudiantes: " + biblioteca.cantidadDeSociosPorTipo("Estudiante"));
        System.out.println();

        // ¿Cuál es la lista de docentes que nunca han adeudado ni adeudan libros?
        System.out.println(biblioteca.listaDeDocentesResponsables());

        // ¿Cuál es la lista de libros? ¿Y la de socios?
        System.out.println(biblioteca.listaDeLibros());
        System.out.println(biblioteca.listaDeSocios());

        // Títulos sin repetir
        System.out.println(biblioteca.listaDeTitulos());

        // Préstamos vencidos
        System.out.println("Préstamos vencidos:");
        for (Prestamo p : biblioteca.prestamosVencidos()) {
            System.out.println(p);
        }
        System.out.println();

        // ¿Qué socio tiene prestado el libro "Programando con JAVA"?
        try {
            System.out.println("Tiene el libro 'Programando con JAVA': "
                    + biblioteca.quienTieneElLibro(programando));
        } catch (LibroNoPrestadoException e) {
            System.out.println(e.getMessage());
        }
    }
}
