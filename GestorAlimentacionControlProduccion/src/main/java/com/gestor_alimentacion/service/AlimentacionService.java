/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gestor_alimentacion.service;

import com.gestor_alimentacion.model.Alimentacion;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author adm
 */
public class AlimentacionService {
    
  private List<Alimentacion> alimentaciones = new ArrayList<>();
    private int nextId = 1; // Simulación de generación de IDs

    public void registrarAlimentacion(Alimentacion alimentacion) {
        alimentacion.setId(nextId++);
        alimentaciones.add(alimentacion);
    }

    public Alimentacion obtenerAlimentacionPorId(int id) {
        for (Alimentacion alimentacion : alimentaciones) {
            if (alimentacion.getId() == id) {
                return alimentacion;
            }
        }
        return null;
    }

    public List<Alimentacion> obtenerTodasLasAlimentaciones() {
        return new ArrayList<>(alimentaciones);
    }

    public void actualizarAlimentacion(Alimentacion alimentacion) {
        for (int i = 0; i < alimentaciones.size(); i++) {
            if (alimentaciones.get(i).getId() == alimentacion.getId()) {
                alimentaciones.set(i, alimentacion);
                return;
            }
        }
        // Manejo si no se encuentra la alimentación (opcional)
    }

    public void eliminarAlimentacion(int id) {
        alimentaciones.removeIf(alimentacion -> alimentacion.getId() == id);
    }

    // Métodos adicionales (ejemplos):

    public List<Alimentacion> obtenerAlimentacionesPorVaca(int vacaId) {
        List<Alimentacion> alimentacionesVaca = new ArrayList<>();
        for (Alimentacion alimentacion : alimentaciones) {
            if (alimentacion.getVacaId() == vacaId) {
                alimentacionesVaca.add(alimentacion);
            }
        }
        return alimentacionesVaca;
    }

  
}
