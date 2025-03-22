package ModuloDariel.models;

import java.time.LocalDate;
/**
 *
 * @author darie
 */
public class RegistroReproductivo {
    private int id;
    private LocalDate fechaInseminacion;
    private LocalDate fechaParto;
    private Vaca.EstadoReproductivo estado; // Usamos el enum de la clase Vaca
    private int madreId;
    private int padreId;
    private int vacaId;

    // Constructor
    public RegistroReproductivo(int id, LocalDate fechaInseminacion, LocalDate fechaParto, Vaca.EstadoReproductivo estado, int madreId, int padreId, int vacaId) {
        this.id = id;
        this.fechaInseminacion = fechaInseminacion;
        this.fechaParto = fechaParto;
        this.estado = estado;
        this.madreId = madreId;
        this.padreId = padreId;
        this.vacaId = vacaId;
    }

    // Getters y Setters
    public int getId() {return id;}
    public void setId(int id) {this.id = id;}

    public LocalDate getFechaInseminacion() {return fechaInseminacion;}
    public void setFechaInseminacion(LocalDate fechaInseminacion) {this.fechaInseminacion = fechaInseminacion;}

    public LocalDate getFechaParto() {return fechaParto;}
    public void setFechaParto(LocalDate fechaParto) {this.fechaParto = fechaParto;}

    public Vaca.EstadoReproductivo getEstado() {return estado;}
    public void setEstado(Vaca.EstadoReproductivo estado) {this.estado = estado;}

    public int getMadreId() {return madreId;}
    public void setMadreId(int madreId) {this.madreId = madreId;}

    public int getPadreId() {return padreId;}
    public void setPadreId(int padreId) {this.padreId = padreId;}

    public int getVacaId() {return vacaId;}
    public void setVacaId(int vacaId) {this.vacaId = vacaId;}

    // Método para representación en texto
    public String infoReproduccion() {
        return "RegistroReproductivo{" +
                "id=" + id +
                ", fechaInseminacion=" + fechaInseminacion +
                ", fechaParto=" + fechaParto +
                ", estado=" + estado +
                ", madreId=" + madreId +
                ", padreId=" + padreId +
                ", vacaId=" + vacaId +
                '}';
    }
}
