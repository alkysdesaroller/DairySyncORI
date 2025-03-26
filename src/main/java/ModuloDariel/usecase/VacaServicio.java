package ModuloDariel.usecase;

import ModuloDariel.models.Vaca;
import ModuloDariel.repo.VacaRepo;
import java.util.List;
import java.util.Optional;

public class VacaServicio {
    private final VacaRepo vacaRepositorio;
    private final RegistroSaludServicio registroServicio;
    
    public VacaServicio(RegistroSaludServicio registroServicio) {
        this.vacaRepositorio = new VacaRepo();
        this.registroServicio = registroServicio;
    }

    public Vaca registrarVaca(String raza, int edad, String genealogia, int granjaId) {
        Vaca vaca = new Vaca(
            generarNuevoId(),
            raza,
            edad,
            genealogia,
            granjaId,
            Vaca.EstadoSalud.EN_OBSERVACION,
            Vaca.EstadoReproductivo.NO_CONOCIDO
        );
        vacaRepositorio.guardar(vaca);
        return vaca;
    }

    public void actualizarEstadoSalud(int idVaca, Vaca.EstadoSalud nuevoEstado, String descripcion, String tratamiento) {
        Optional<Vaca> vaca = vacaRepositorio.obtenerPorId(idVaca);
        vaca.ifPresent(v -> {
            v.setEstadoSalud(nuevoEstado);
            vacaRepositorio.actualizar(v);
            registroServicio.crearRegistroSalud(v.getId(), nuevoEstado, descripcion, tratamiento);
        });
    }

    public void actualizarEstadoReproductivo(int idVaca, Vaca.EstadoReproductivo nuevoEstado) {
        Optional<Vaca> vaca = vacaRepositorio.obtenerPorId(idVaca);
        vaca.ifPresent(v -> {
            v.setEstadoReproductivo(nuevoEstado);
            vacaRepositorio.actualizar(v);
        });
    }

    public List<Vaca> obtenerTodasLasVacas() {
        return vacaRepositorio.obtenerTodos();
    }

    private int generarNuevoId() {
        return vacaRepositorio.obtenerTodos().stream()
                .mapToInt(Vaca::getId)
                .max().orElse(0) + 1;
    }
}