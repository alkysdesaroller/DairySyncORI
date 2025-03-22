package ModuloDariel.usecase;

import ModuloDariel.models.RegistroSalud;
import ModuloDariel.models.Vaca;
import java.time.LocalDate;

/**
 *
 * @author Dariel
 */
public class RegistroSaludServicio {

    // Metodo actualizar un registro de salud existente
    public void actualizarRegistroSalud(RegistroSalud registro, Vaca.EstadoSalud nuevoEstado, String nuevaDescripcion, String nuevoTratamiento, LocalDate nuevaFecha) {
        if (registro == null) {
            System.out.println("Registro de salud nulo. No se puede actualizar.");
            return;
        }

        if (nuevoEstado == null || nuevaDescripcion == null || nuevoTratamiento == null || nuevaFecha == null) {
            System.out.println("Datos incompletos. No se puede actualizar el registro de salud.");
            return;
        }
        registro.setFecha(nuevaFecha);
        registro.setEstadoSalud(nuevoEstado);
        registro.setDescripcion(nuevaDescripcion);
        registro.setTratamiento(nuevoTratamiento);

        System.out.println("Registro de salud # " + registro.getId() + " actualizado correctamente.");
    }

    // Muestra un registro de salud
    public void mostrarRegistro(RegistroSalud registro) {
        if (registro == null) {
            System.out.println("Registro de salud nulo. No se puede mostrar.");
            return;
        }

        System.out.println("Registro #" + registro.getId() +
            ": Fecha: " + registro.getFecha() +
            ", Estado de salud: " + registro.getEstadoSalud() +
            ", Descripción: " + registro.getDescripcion() +
            ", Tratamiento: " + registro.getTratamiento());
    }
}

