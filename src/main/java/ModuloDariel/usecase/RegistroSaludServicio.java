package ModuloDariel.usecase;

import ModuloDariel.models.RegistroSalud;
import ModuloDariel.models.Vaca;
import ModuloDariel.repo.RegistroSaludRepo;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author Dariel
 */
public class RegistroSaludServicio {
    private final RegistroSaludRepo registroSaludRepositorio;

    public RegistroSaludServicio(RegistroSaludRepo registroRepositorio) {
        this.registroSaludRepositorio = registroRepositorio;
    }

    // Metodo para crear un nuevo registro de salud
    public void crearRegistroSalud(int vacaId, Vaca.EstadoSalud estado,String descripcion, String tratamiento) {
        RegistroSalud nuevoRegistro = new RegistroSalud(
            0, // El ID se generará automáticamente
            vacaId,
            LocalDate.now(),
            estado,
            descripcion,
            tratamiento
        );
        registroSaludRepositorio.guardar(nuevoRegistro);
    }

    // Metodo para actualizar un registro existente
    public void actualizarRegistroSalud(RegistroSalud registro, Vaca.EstadoSalud nuevoEstado, String nuevaDescripcion, String nuevoTratamiento) {
        if (registro == null) {
            System.out.println("Registro de salud nulo. No se puede actualizar.");
            return;
        }

        registro.setEstadoSalud(nuevoEstado);
        registro.setDescripcion(nuevaDescripcion);
        registro.setTratamiento(nuevoTratamiento);
        registro.setFecha(LocalDate.now());

        registroSaludRepositorio.actualizar(registro);
        System.out.println("Registro de salud #" + registro.getId() + " actualizado correctamente.");
    }

    // Metodo para mostrar un registro
    public void mostrarRegistro(RegistroSalud registro) {
        if (registro == null) {
            System.out.println("Registro de salud nulo. No se puede mostrar.");
            return;
        }
        System.out.println(
            "Registro #" + registro.getId() +
            "\nVaca ID: " + registro.getVacaId() +
            "\nFecha: " + registro.getFecha() +
            "\nEstado: " + registro.getEstadoSalud() +
            "\nDescripción: " + registro.getDescripcion() +
            "\nTratamiento: " + registro.getTratamiento()
        );
    }

    // Metodo para obtener todos los registros de una vaca
    public List<RegistroSalud> obtenerRegistrosPorVaca(int vacaId) {
        return registroSaludRepositorio.obtenerPorCondicion("vacaId = " + vacaId);
    }
}