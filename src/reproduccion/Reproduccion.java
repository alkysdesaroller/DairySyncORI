/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */

package reproduccion;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

/**
 * Clase que representa el proceso de reproducción de ganado.
 * Contiene información sobre la vaca, el toro, el estado de reproducción y el granjero responsable.
 */
public class Reproduccion {
    
    // Constantes para estados de reproducción
    public static final String ESTADO_INSEMINADA = "Inseminada";
    public static final String ESTADO_EN_PARTO = "En parto";
    public static final String ESTADO_PARIDA = "Parida";
    
    private int vacaId;
    private int toroId;
    private String razaVaca;
    private String descripcion;
    private String estado;  // Campo para el estado de la vaca (Inseminada, En parto, Parida)
    private LocalDate fechaInseminacion;
    private String nombreGranjero;
    private int idGranjero;
    
    /**
     * Constructor completo para la clase Reproduccion
     * 
     * @param vacaId ID único de la vaca
     * @param toroId ID único del toro
     * @param razaVaca Raza de la vaca
     * @param descripcion Descripción del proceso de reproducción
     * @param estado Estado actual de la reproducción (Inseminada, En parto, Parida)
     * @param fechaInseminacion Fecha en que se realizó la inseminación
     * @param nombreGranjero Nombre del granjero responsable
     * @param idGranjero ID único del granjero
     */
    public Reproduccion(int vacaId, int toroId, String razaVaca, String descripcion, 
                       String estado, LocalDate fechaInseminacion, 
                       String nombreGranjero, int idGranjero) {
        this.vacaId = vacaId;
        this.toroId = toroId;
        this.razaVaca = razaVaca;
        this.descripcion = descripcion;
        setEstado(estado); // Usar el setter para validar el estado
        this.fechaInseminacion = Objects.requireNonNull(fechaInseminacion, "La fecha de inseminación no puede ser nula");
        this.nombreGranjero = nombreGranjero;
        this.idGranjero = idGranjero;
    }
    
    /**
     * Constructor simplificado con valores predeterminados para algunos campos
     */
    public Reproduccion(int vacaId, int toroId, String razaVaca, LocalDate fechaInseminacion) {
        this(vacaId, toroId, razaVaca, "", ESTADO_INSEMINADA, fechaInseminacion, "", 0);
    }
    
    // Métodos getter y setter con validaciones
    public int getVacaId() {
        return vacaId;
    }
    
    public void setVacaId(int vacaId) {
        this.vacaId = vacaId;
    }
    
    public int getToroId() {
        return toroId;
    }
    
    public void setToroId(int toroId) {
        this.toroId = toroId;
    }
    
    public String getRazaVaca() {
        return razaVaca;
    }
    
    public void setRazaVaca(String razaVaca) {
        this.razaVaca = razaVaca;
    }
    
    public String getDescripcion() {
        return descripcion;
    }
    
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public String getEstado() {
        return estado;
    }
    
    /**
     * Establece el estado de reproducción validando que sea uno de los estados permitidos
     * 
     * @param estado Nuevo estado de reproducción
     * @throws IllegalArgumentException si el estado no es válido
     */
    public void setEstado(String estado) {
        if (estado == null) {
            throw new IllegalArgumentException("El estado no puede ser nulo");
        }
        
        switch (estado) {
            case ESTADO_INSEMINADA:
            case ESTADO_EN_PARTO:
            case ESTADO_PARIDA:
                this.estado = estado;
                break;
            default:
                throw new IllegalArgumentException("Estado no válido: " + estado);
        }
    }
    
    public LocalDate getFechaInseminacion() {
        return fechaInseminacion;
    }
    
    public void setFechaInseminacion(LocalDate fechaInseminacion) {
        this.fechaInseminacion = Objects.requireNonNull(fechaInseminacion, 
                                 "La fecha de inseminación no puede ser nula");
    }
    
    public String getNombreGranjero() {
        return nombreGranjero;
    }
    
    public void setNombreGranjero(String nombreGranjero) {
        this.nombreGranjero = nombreGranjero;
    }
    
    public int getIdGranjero() {
        return idGranjero;
    }
    
    public void setIdGranjero(int idGranjero) {
        this.idGranjero = idGranjero;
    }
    
    /**
     * Calcula los días transcurridos desde la inseminación
     * 
     * @return número de días desde la inseminación hasta hoy
     */
    public long diasDesdeInseminacion() {
        return ChronoUnit.DAYS.between(fechaInseminacion, LocalDate.now());
    }
    
    /**
     * Determina si una vaca está próxima a parir (270-290 días desde inseminación)
     * 
     * @return true si la vaca está próxima a parir
     */
    public boolean estaProximaAParto() {
        long dias = diasDesdeInseminacion();
        return dias >= 270 && dias <= 290;
    }
    
    /**
     * Actualiza el estado a "En parto" si han pasado suficientes días
     * 
     * @return true si se actualizó el estado, false en caso contrario
     */
    public boolean actualizarEstadoParto() {
        if (ESTADO_INSEMINADA.equals(estado) && estaProximaAParto()) {
            setEstado(ESTADO_EN_PARTO);
            return true;
        }
        return false;
    }
    
    @Override
    public String toString() {
        return "Reproduccion{" +
                "vacaId=" + vacaId +
                ", toroId=" + toroId +
                ", razaVaca='" + razaVaca + '\'' +
                ", estado='" + estado + '\'' +
                ", fechaInseminacion=" + fechaInseminacion +
                ", nombreGranjero='" + nombreGranjero + '\'' +
                '}';
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reproduccion that = (Reproduccion) o;
        return vacaId == that.vacaId && 
               toroId == that.toroId && 
               Objects.equals(fechaInseminacion, that.fechaInseminacion);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(vacaId, toroId, fechaInseminacion);
    }
}