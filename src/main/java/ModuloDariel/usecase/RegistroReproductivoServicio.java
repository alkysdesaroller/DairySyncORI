package ModuloDariel.usecase;

import ModuloDariel.models.RegistroReproductivo;
import ModuloDariel.models.Vaca;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author darie
 */
public class RegistroReproductivoServicio {
    // Lista para almacenar los registros
    private List<RegistroReproductivo> registrosReproductivos; 

    // Constructor
    public RegistroReproductivoServicio() {
        this.registrosReproductivos = new ArrayList<>();
    }
    
    // Metodo para crear un nuevo registro reproductivo
    public void crearRegistroReproductivo(int id, LocalDate fechaInseminacion, LocalDate fechaParto, Vaca.EstadoReproductivo estado, int madreId, int padreId, int vacaId) {
        RegistroReproductivo registro = new RegistroReproductivo(id, fechaInseminacion, fechaParto, estado, madreId, padreId, vacaId);
        registrosReproductivos.add(registro);
        System.out.println("Registro reproductivo creado: " + registro);
    }
    
    // Metodo para actualizar el estado de un registro reproductivo
    public void actualizarEstadoReproductivo(int id, Vaca.EstadoReproductivo nuevoEstado) {
        RegistroReproductivo registro = buscarRegistroPorId(id);
        if (registro != null) {
            registro.setEstado(nuevoEstado);
            System.out.println("Estado del registro reproductivo actualizado: " + registro);
        } else {
            System.out.println("Registro reproductivo no encontrado con ID: " + id);
        }
    }
      // Metodo para actualizar la fecha de parto de un registro reproductivo
    public void actualizarFechaParto(int id, LocalDate nuevaFechaParto) {
        RegistroReproductivo registro = buscarRegistroPorId(id);
        if (registro != null) {
            registro.setFechaParto(nuevaFechaParto);
            System.out.println("Fecha de parto actualizada: " + registro);
        } else {
            System.out.println("Registro reproductivo no encontrado con ID: " + id);
        }
    }

    // Metod para eliminar un registro reproductivo
    public void eliminarRegistroReproductivo(int id) {
        RegistroReproductivo registro = buscarRegistroPorId(id);
        if (registro != null) {
            registrosReproductivos.remove(registro);
            System.out.println("Registro reproductivo eliminado: " + registro);
        } else {
            System.out.println("Registro reproductivo no encontrado con ID: " + id);
        }
    }

    // Metodo para buscar un registro reproductivo por su ID
    public RegistroReproductivo buscarRegistroPorId(int id) {
        for (RegistroReproductivo registro : registrosReproductivos) {
            if (registro.getId() == id) {
                return registro;
            }
        }
        return null; // Retorna null si no se encuentra el registro
    }

    // Mtodo para obtener todos los registros reproductivos
    public List<RegistroReproductivo> obtenerTodosLosRegistros() {
        return registrosReproductivos;
    }

    // Metodo para mostrar todos los registros reproductivos
    public void mostrarTodosLosRegistros() {
        if (registrosReproductivos.isEmpty()) {
            System.out.println("No hay registros reproductivos.");
        } else {
            System.out.println("Lista de registros reproductivos:");
            for (RegistroReproductivo registro : registrosReproductivos) {
                System.out.println(registro);
            }
        }
    }
}
