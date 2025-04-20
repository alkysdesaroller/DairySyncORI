/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gestor_alimentacion.model;

public class Alimento {

    private int id;   
    private String nombre;
    private String descripcion;
    private String tipo;
    private String unidadMedida;
    private double precio;

    // Constructor vacío (opcional)
    public Alimento() {
    }

    // Constructor con todos los atributos (opcional)
    public Alimento(int id, String nombre, String descripcion, String tipo, String unidadMedida, double precio) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.tipo = tipo;
        this.unidadMedida = unidadMedida;
        this.precio = precio;
    }

    // Getters
    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public String getDescripcion() { return descripcion; }
    public String getTipo() { return tipo; }
    public String getUnidadMedida() { return unidadMedida; }
    public double getPrecio() { return precio; }

    // Setters
    public void setId(int id) { this.id = id; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public void setUnidadMedida(String unidadMedida) { this.unidadMedida = unidadMedida; }
    public void setPrecio(double precio) { this.precio = precio; }
}