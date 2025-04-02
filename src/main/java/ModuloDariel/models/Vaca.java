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
    private Integer madreId;
    private Integer padreId;
    private EstadoSalud estadoSalud;
    private EstadoReproductivo estadoReproductivo;

    // Enums para definir estados no modificables
    public enum EstadoSalud {
        SANA, ENFERMA, EN_OBSERVACION;

        public static EstadoSalud fromString(String value) {
            if (value == null) return EN_OBSERVACION;
            try {
                return EstadoSalud.valueOf(value.toUpperCase().trim());
            } catch (IllegalArgumentException e) {
                return EN_OBSERVACION;
            }
        }
    }

    public enum EstadoReproductivo {
        NO_CONOCIDO, INSEMINADA, PREÑADA, PARIDA;

        public static EstadoReproductivo fromString(String value) {
            if (value == null) return NO_CONOCIDO;
            try {
                return EstadoReproductivo.valueOf(
                    value.toUpperCase()
                         .replace("Ñ", "N")
                         .replace("É", "E")
                         .trim()
                );
            } catch (IllegalArgumentException e) {
                return NO_CONOCIDO;
            }
        }
    }

    // Constructor
public Vaca(int id, String raza, int edad, String genealogia, int granjaId, EstadoSalud estadoSalud, EstadoReproductivo estadoReproductivo,Integer madreId, Integer padreId) {
    this.id = id;
    this.raza = raza;
    this.edad = edad;
    this.genealogia = genealogia;
    this.granjaId = granjaId;
    this.estadoSalud = estadoSalud;
    this.estadoReproductivo = estadoReproductivo;
    this.madreId = (madreId != null) ? madreId : 0; // o algún valor por defecto    
    this.padreId = (padreId != null) ? padreId : 0; // o algún valor por defecto 
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
    
     public Integer getMadreId() {return madreId;}
    public void setMadreId(Integer madreId) {this.madreId = madreId;}

    public Integer getPadreId() {return padreId;}
    public void setPadreId(Integer padreId) {this.padreId = padreId;}
    
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
