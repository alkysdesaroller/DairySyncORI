package ModuloDariel.usecase;

import ModuloDariel.models.*;
import ModuloDariel.repo.*;
import java.util.List;
import java.util.Optional;

public class VacaServicio {
    private final VacaRepo vacaRepositorio;
    private final RegistroSaludServicio registroServicio;
    
    public VacaServicio(RegistroSaludServicio registroServicio) {
        this.vacaRepositorio = new VacaRepo();
        this.registroServicio = registroServicio;
    }
    
    // Métodos para registrar una vaca
    public Vaca registrarVaca(String raza, int edad, String genealogia, int granjaId) {
        return registrarVaca(raza, edad, genealogia, granjaId, null, null);
    }

    public Vaca registrarVaca(String raza, int edad, String genealogia, int granjaId, 
                            Integer madreId, Integer padreId) {
        Vaca vaca = new Vaca(
            generarNuevoId(),
            raza,
            edad,
            genealogia,
            granjaId,
            Vaca.EstadoSalud.EN_OBSERVACION,
            "NO_CONOCIDO", // Cambiado a String
            madreId,
            padreId
        );
        vacaRepositorio.guardar(vaca);
        registroServicio.crearRegistroSalud(
            vaca.getId(),
            vaca.getEstadoSalud(),
            "Examen inicial",
            "Ninguno"
        );
        
        return vaca;
    }

    // Métodos para actualizar estado de salud
    public void actualizarEstadoSalud(int idVaca, Vaca.EstadoSalud nuevoEstado, 
                                    String descripcion, String tratamiento) {
        Optional<Vaca> vaca = vacaRepositorio.obtenerPorId(idVaca);
        vaca.ifPresent(v -> {
            v.setEstadoSalud(nuevoEstado);
            vacaRepositorio.actualizar(v);
            registroServicio.crearRegistroSalud(v.getId(), nuevoEstado, descripcion, tratamiento);
        });
    }

    // Métodos para actualizar estado reproductivo
    public void actualizarEstadoReproductivo(int idVaca, String nuevoEstado) {
        Optional<Vaca> vaca = vacaRepositorio.obtenerPorId(idVaca);
        vaca.ifPresent(v -> {
            v.setEstadoReproductivo(nuevoEstado);
            vacaRepositorio.actualizar(v);
        });
    }
    //Metoo que actualiza para Strings ya que no es Enum
    public void actualizarEstadoReproductivo(int idVaca, String nuevoEstado, boolean fromLegacy) {
        String normalizedEstado = Vaca.convertirEstadoReproductivo(nuevoEstado);
        actualizarEstadoReproductivo(idVaca, normalizedEstado);
    }

    public List<Vaca> obtenerTodasLasVacas() {
        return vacaRepositorio.obtenerTodos();
    }

    public Optional<Vaca> obtenerPorId(int id) {
        return vacaRepositorio.obtenerPorId(id);
    }

    private int generarNuevoId() {
        return vacaRepositorio.obtenerTodos().stream()
                .mapToInt(Vaca::getId)
                .max().orElse(0) + 1;
    }

    public void eliminar(int id) {
        Optional<Vaca> vacaOptional = vacaRepositorio.obtenerPorId(id);
        if (vacaOptional.isPresent()) {
            vacaRepositorio.eliminar(id);
            System.out.println("Vaca con ID " + id + " eliminada exitosamente.");
        } else {
            System.out.println("No se encontró ninguna vaca con el ID " + id + ". No se puede eliminar.");
        }
    }
}