package ModuloDariel.models;
import java.time.LocalDate;

/**
 *
 * @author Dariel Capellan
 */
public class RegistroSalud {
    private int id;
    private int vacaId;
    private LocalDate fecha;
    private Vaca.EstadoSalud estadoSalud;
    private String descripcion;
    private String tratamiento;
       
    //Constructor
public RegistroSalud(int id, int vacaId, LocalDate fecha, Vaca.EstadoSalud estadoSalud, String descripcion, String tratamiento){
    this.id = id;
    this.vacaId = vacaId;
    this.fecha = fecha;
    this.estadoSalud = estadoSalud;
    this.descripcion = descripcion;
    this.tratamiento = tratamiento;
    }

    //Getters y Setters
public int getId(){return id;}
public void setId(int Id){this.id = id;}

public int getVacaId(){return vacaId;}
public void setVacaId(int vacaId){this.vacaId = vacaId;}

public LocalDate getFecha() { return fecha; }
public void setFecha(LocalDate fecha) { this.fecha = fecha; }

public Vaca.EstadoSalud getEstadoSalud(){ return estadoSalud; }
public void setEstadoSalud(Vaca.EstadoSalud estadoSalud){ this.estadoSalud = estadoSalud; }

public String getDescripcion(){return descripcion;}
public void setDescripcion(String descripcion){this.descripcion = descripcion;}

public String getTratamiento(){return tratamiento;}
public void setTratamiento(String tratamiento){this.tratamiento = tratamiento;}

}

