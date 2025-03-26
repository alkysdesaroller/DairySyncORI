package ModuloDariel.usecase;

import ModuloDariel.models.RegistroReproductivo;
import ModuloDariel.models.Vaca;
import ModuloDariel.repo.RegistroReproductivoRepo;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author darie
 */
public class RegistroReproductivoServicio {
    private final RegistroReproductivoRepo registroReprRepo;

    public RegistroReproductivoServicio(RegistroReproductivoRepo registroRepo) {
        this.registroReprRepo = registroRepo;
    }

    public void crearRegistroReproductivo(LocalDate fechaInseminacion,Vaca.EstadoReproductivo estado,int madreId, int padreId, int vacaId) {
        RegistroReproductivo registro = new RegistroReproductivo(
            0, // ID será generado por la BD
            fechaInseminacion,
            null, // fechaParto inicialmente null
            estado,
            madreId,
            padreId,
            vacaId
        );
        registroReprRepo.guardar(registro);
    }

    public void actualizarEstadoReproductivo(int id, Vaca.EstadoReproductivo nuevoEstado) {
        Optional<RegistroReproductivo> registroOpt = registroReprRepo.obtenerPorId(id);
        registroOpt.ifPresent(registro -> {
            registro.setEstado(nuevoEstado);
            registroReprRepo.actualizar(registro);
        });
    }

    public void actualizarFechaParto(int id, LocalDate nuevaFechaParto) {
        Optional<RegistroReproductivo> registroOpt = registroReprRepo.obtenerPorId(id);
        registroOpt.ifPresent(registro -> {
            registro.setFechaParto(nuevaFechaParto);
            registroReprRepo.actualizar(registro);
        });
    }

    public void eliminarRegistroReproductivo(int id) {
        registroReprRepo.eliminar(id);
    }

    public Optional<RegistroReproductivo> buscarRegistroPorId(int id) {
        return registroReprRepo.obtenerPorId(id);
    }

    public List<RegistroReproductivo> obtenerTodosLosRegistros() {
        return registroReprRepo.obtenerTodos();
    }

    public List<RegistroReproductivo> obtenerRegistrosPorVaca(int vacaId) {
        return registroReprRepo.obtenerPorVacaId(vacaId);
    }

    public void mostrarTodosLosRegistros() {
        List<RegistroReproductivo> registros = registroReprRepo.obtenerTodos();
        if (registros.isEmpty()) {
            System.out.println("No hay registros reproductivos.");
        } else {
            registros.forEach(registro -> 
                System.out.println(registro.infoReproduccion())
            );
        }
    }
}