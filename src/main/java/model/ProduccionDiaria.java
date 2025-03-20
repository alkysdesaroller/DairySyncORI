
package model;
import java.time.LocalDate;
/**
 *
 * @author alna7
 */

//Declaramos variables
public class ProduccionDiaria {
    private int id; 
    private LocalDate fecha; // Utilizamos esta libreria para capturar la fecha exacta.  
    private double litros; 
    private int vacaId;
    private int granjaId; 
    
   //Constructor para la ProduccionDiaria
    public ProduccionDiaria(int id, LocalDate fecha, double litros, int vacaId, int granjaId){
    this.id = id; 
    this.fecha = fecha;  
    this.litros = litros; 
    this.vacaId = vacaId; 
    this.granjaId = granjaId;
    }
    
        // Getters y Setters
    public int getId(int id){
    return id;
    }
    
    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public double getLitros() {
        return litros;
    }

    public void setLitros(double litros) {
        this.litros = litros;
    }

    public int getVacaId() {
        return vacaId;
    }

    public void setVacaId(int vacaId) {
        this.vacaId = vacaId;
    }

    public int getGranjaId() {
        return granjaId;
    }

    public void setGranjaId(int granjaId) {
        this.granjaId = granjaId;
    }

}
