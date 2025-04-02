package ModuloDariel.usecase;

import ModuloDariel.models.*;
import ModuloDariel.repo.*;
import java.util.List;
import java.util.Optional;

public class VacaServicio {
    private final VacaRepo vacaRepositorio;
    private final RegistroSaludServicio registroServicio;
    
    //Metodos para cada uno de las funciones con una Vaca
    public VacaServicio(RegistroSaludServicio registroServicio) {
        this.vacaRepositorio = new VacaRepo();
        this.registroServicio = registroServicio;
    }
    
    //Metodos para registrar una vaca
    
    public Vaca registrarVaca(String raza, int edad, String genealogia, int granjaId)
    {
    return registrarVaca(raza, edad, genealogia, granjaId, 0, 0);
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
        Vaca.EstadoReproductivo.NO_CONOCIDO,
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
    //Metodos para actualizar estado de sald  vaca
    public void actualizarEstadoSalud(int idVaca, Vaca.EstadoSalud nuevoEstado, String descripcion, String tratamiento) {
        Optional<Vaca> vaca = vacaRepositorio.obtenerPorId(idVaca);
        vaca.ifPresent(v -> {
            v.setEstadoSalud(nuevoEstado);
            vacaRepositorio.actualizar(v);
            registroServicio.crearRegistroSalud(v.getId(), nuevoEstado, descripcion, tratamiento);
        });
    }
    //Metodos para actualizar estado reprodc. una vaca
    public void actualizarEstadoReproductivo(int idVaca, Vaca.EstadoReproductivo nuevoEstado) {
        Optional<Vaca> vaca = vacaRepositorio.obtenerPorId(idVaca);
        vaca.ifPresent(v -> {
            v.setEstadoReproductivo(nuevoEstado);
            vacaRepositorio.actualizar(v);
        });
    }
    //Metodos para obtener de la ArrayList una vaca
    public List<Vaca> obtenerTodasLasVacas() {
        return vacaRepositorio.obtenerTodos();
    }
    public Optional<Vaca> obtenerPorId() {
        return vacaRepositorio.obtenerPorId(Integer.SIZE);
    }

    private int generarNuevoId() {
        return vacaRepositorio.obtenerTodos().stream()
                .mapToInt(Vaca::getId)
                .max().orElse(0) + 1;
    }
}