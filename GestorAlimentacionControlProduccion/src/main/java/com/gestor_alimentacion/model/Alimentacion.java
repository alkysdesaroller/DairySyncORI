/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gestor_alimentacion.model;

/**
 *
 * @author adm
 */
import java.util.Date;

public class Alimentacion {
    
private int id;
    private int alimentoId;
    private float cantidad;
    private Date fecha;
    private int vacaId;
    private int granjaId; 

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAlimentoId() {
        return alimentoId;
    }

    public void setAlimentoId(int alimentoId) {
        this.alimentoId = alimentoId;
    }

    public float getCantidad() {
        return cantidad;
    }

    public void setCantidad(float cantidad) {
        this.cantidad = cantidad;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
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

    // Constructor vacío (opcional)
    public Alimentacion() { 
    }  

    // Constructor con todos los atributos (opcional)
    public Alimentacion(int id, int alimentoId, float cantidad, Date fecha, int vacaId, int granjaId) {
        this.id = id;
        this.alimentoId = alimentoId;
        this.cantidad = cantidad;
        this.fecha = fecha;
        this.vacaId = vacaId;
        this.granjaId = granjaId;  
    }

    // Getters y setters (los generaremos después)
}
