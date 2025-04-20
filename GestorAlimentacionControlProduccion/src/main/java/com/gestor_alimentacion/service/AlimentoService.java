/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gestor_alimentacion.service;

import com.gestor_alimentacion.model.Alimento;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author adm
 */
public class AlimentoService {
    
   private List<Alimento> alimentos = new ArrayList<>();
    private int nextId = 1; // Simulación de generación de IDs

    public void crearAlimento(Alimento alimento) {
        alimento.setId(nextId++);
        alimentos.add(alimento);
    }

    public Alimento obtenerAlimentoPorId(int id) {
        for (Alimento alimento : alimentos) {
            if (alimento.getId() == id) {
                return alimento;
            }
        }
        return null;
    }

    public List<Alimento> obtenerTodosLosAlimentos() {
        return new ArrayList<>(alimentos); // Devolvemos una copia para evitar modificaciones externas
    }

    public void actualizarAlimento(Alimento alimento) {
        for (int i = 0; i < alimentos.size(); i++) {
            if (alimentos.get(i).getId() == alimento.getId()) {
                alimentos.set(i, alimento);
                return; // Indica que se encontró y actualizó el alimento
            }
        }
        // Si no se encontró el alimento, podrías lanzar una excepción o simplemente no hacer nada
    }

    public void eliminarAlimento(int id) {
        alimentos.removeIf(alimento -> alimento.getId() == id);
    }
}
