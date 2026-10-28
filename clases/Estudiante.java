package biblioteca;

public class Estudiante extends Socio {

    private String carrera;

    public Estudiante(int p_dniSocio, String p_nombre, String p_carrera) {
        super(p_dniSocio, p_nombre, 20); // los estudiantes tienen 20 días
        this.carrera = p_carrera;
    }

    public String getCarrera() {
        return carrera;
    }

    public void setCarrera(String p_carrera) {
        this.carrera = p_carrera;
    }

    @Override
    public boolean puedePedir() {
        return super.puedePedir() && this.cantLibrosPrestados() <= 3;
    }

    @Override
    public String soyDeLaClase() {
        return "Estudiante";
    }
}
