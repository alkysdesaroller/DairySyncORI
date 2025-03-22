package ModuloDariel.usecase;

import ModuloDariel.models.Vaca;
import ModuloDariel.models.RegistroSalud;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author darie
 */
public class VacaServicio {
    //ArrayList para almacenar Vacas y registrosSalud
    private List<Vaca> vacas = new ArrayList<>();
    private List<RegistroSalud> registrosSalud = new ArrayList<>();
    private int registroSaludId = 1;
    private int vacaId = 1;
    private RegistroSaludServicio registroServicio;
    
    //Costructor que inyecta a RegistroSaludServicio en esta clase
    public VacaServicio(RegistroSaludServicio registroServicio) {
        this.registroServicio = registroServicio;
    }
    // Metodo registrar nueva vaca
    public Vaca registrarVaca(String raza, int edad, String genealogia, int granjaId) {
        Vaca.EstadoSalud estadoSalud = Vaca.EstadoSalud.EN_OBSERVACION;
        Vaca.EstadoReproductivo estadoReproductivo = Vaca.EstadoReproductivo.NO_CONOCIDO;
        Vaca vaca = new Vaca(vacaId++, raza, edad, genealogia, granjaId, estadoSalud, estadoReproductivo);
        vacas.add(vaca);
        System.out.println("Nueva vaca registrada: ID " + vaca.getId());
        return vaca;
    }

    // Metodo actualizar estado de salud
    public void actualizarEstadoSalud(Vaca vaca, Vaca.EstadoSalud nuevoEstado, String descripcion, String tratamiento) {
        if (nuevoEstado != null) {
            vaca.setEstadoSalud(nuevoEstado);

            RegistroSalud nuevoRegistro = new RegistroSalud(
                registroSaludId++,
                vaca.getId(),
                LocalDate.now(),
                nuevoEstado,
                descripcion,
                tratamiento
            );

            registrosSalud.add(nuevoRegistro);

            System.out.println("Se actualizo el estado de salud de la vaca ID " + vaca.getId() +
                    " a " + nuevoEstado);
        } else {
            System.out.println("Estado de salud no valido.");
        }
    }

    // Metodo actualizar estado reproductivo
    public void actualizarEstadoReproductivo(Vaca vaca, Vaca.EstadoReproductivo nuevoEstado) {
        if (nuevoEstado != null) {
            vaca.setEstadoReproductivo(nuevoEstado);
            System.out.println("Se actualizo el estado reproductivo de la vaca ID " + vaca.getId() +
                    " a " + nuevoEstado);
        } else {
            System.out.println("Estado reproductivo no valido.");
        }
    }

    // Mostrar todas las vacas
    public void mostrarTodasLasVacas() {
        for (Vaca vaca : vacas) {
            vaca.mostrarInfo();
        }
    }

    // Mostrar registros de salud por vaca
public void mostrarRegistrosSaludPorVaca(int idVaca) {
    System.out.println("Historial de salud de la vaca ID: " + idVaca);
    for (RegistroSalud registro : registrosSalud) {
        if (registro.getVacaId() == idVaca) {
            registroServicio.mostrarRegistro(registro);
            }
        }
    }
}
