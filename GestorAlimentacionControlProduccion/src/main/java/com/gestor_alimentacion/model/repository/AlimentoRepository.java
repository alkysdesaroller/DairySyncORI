/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.gestor_alimentacion.model.repository;

/**
 *
 * @author adm
 */
import com.gestor_alimentacion.model.Alimento;
import java.util.List;

public interface AlimentoRepository {
    Alimento guardar(Alimento alimento);
    Alimento obtenerPorId(int id);
    List<Alimento> obtenerTodos();
    void actualizar(Alimento alimento);
    void eliminar(int id);
}
