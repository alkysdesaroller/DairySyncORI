package ModuloDariel.models;
/**
 *
 * @author Dariel Capellan
 */
public class Vaca {
    //Atributos 
    private int id;
    private String raza;
    private int edad;
    private String genealogia;
    private int granjaId;
    private EstadoSalud estadoSalud;
    private EstadoReproductivo estadoReproductivo;

    // Enums para definir estados no modificables
    public enum EstadoSalud {
        SANA,
        ENFERMA,
        EN_OBSERVACION
    }

    public enum EstadoReproductivo {
        INSEMINADA,
        PREÑADA,
        PARIDA,
        NO_CONOCIDO
    }

    // Constructor
    public Vaca(int id, String raza, int edad, String genealogia, int granjaId,EstadoSalud estadoSalud,EstadoReproductivo estadoReproductivo) {
        this.id = id;
        this.raza = raza;
        this.edad = edad;
        this.genealogia = genealogia;
        this.granjaId = granjaId;
        this.estadoSalud = EstadoSalud.EN_OBSERVACION;
        this.estadoReproductivo = EstadoReproductivo.NO_CONOCIDO;
    }

    // Getters y setters
    public int getId() { return id; }

    public String getRaza() { return raza; }
    public void setRaza(String raza) { this.raza = raza; }
    
    public int getEdad() { return edad; }
    public void setEdad(int edad) {
        if (edad >= 0) {
            this.edad = edad;
        } else {
            System.out.println("La edad no puede ser negativa.");
        }
    }

    public String getGenealogia() { return genealogia; }
    public void setGenealogia(String genealogia) { this.genealogia = genealogia; }

    public int getGranjaId() { return granjaId; }
    public void setGranjaId(int granjaId) { this.granjaId = granjaId; }

    public EstadoSalud getEstadoSalud() { return estadoSalud; }
    public void setEstadoSalud(EstadoSalud estadoSalud) {
        this.estadoSalud = estadoSalud;
    }

    public EstadoReproductivo getEstadoReproductivo() { return estadoReproductivo; }
    public void setEstadoReproductivo(EstadoReproductivo estadoReproductivo) {
        this.estadoReproductivo = estadoReproductivo;
    }
    
    //Metodo para mostrar la info
    public void mostrarInfo() {
        System.out.println("ID: " + id +
                ", Raza: " + raza +
                ", Edad: " + edad +
                ", Genealogia: " + genealogia +
                ", Granja ID: " + granjaId +
                ", Estado de salud: " + estadoSalud +
                ", Estado reproductivo: " + estadoReproductivo);
    }

}
