package ModuloDariel.repo;

import ModuloDariel.models.RegistroReproductivo;
import ModuloDariel.models.Vaca;
import model.repositorio.IBaseInterface;
import model.repositorio.IbaseRepo;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author darie
 */
public class RegistroReproductivoRepo implements IBaseInterface<RegistroReproductivo, Integer> {
    private final IbaseRepo<RegistroReproductivo> baseRepo;

    public RegistroReproductivoRepo() {
        String[] columnas = {"id", "fecha_inseminacion", "fecha_parto", "estado", "madre_id", "padre_id", "vaca_id"};
        this.baseRepo = new IbaseRepo<>("registro_reproductivo", columnas, new MapperRegistroReproductivo());
    }

    private static class MapperRegistroReproductivo implements IbaseRepo.Mapper<RegistroReproductivo> {
        @Override
        public RegistroReproductivo map(ResultSet rs) throws SQLException {
            return new RegistroReproductivo(
                rs.getInt("id"),
                rs.getDate("fecha_inseminacion").toLocalDate(),
                rs.getDate("fecha_parto") != null ? rs.getDate("fecha_parto").toLocalDate() : null,
                Vaca.EstadoReproductivo.valueOf(rs.getString("estado")),
                rs.getInt("madre_id"),
                rs.getInt("padre_id"),
                rs.getInt("vaca_id")
            );
        }
    }

    @Override
    public void guardar(RegistroReproductivo entidad) {
        String sql = String.format(
            "INSERT INTO registro_reproductivo (fecha_inseminacion, fecha_parto, estado, madre_id, padre_id, vaca_id) " +
            "VALUES ('%s', %s, '%s', %d, %d, %d)",
            entidad.getFechaInseminacion(),
            entidad.getFechaParto() != null ? "'" + entidad.getFechaParto() + "'" : "NULL",
            entidad.getEstado().name(),
            entidad.getMadreId(),
            entidad.getPadreId(),
            entidad.getVacaId()
        );
        baseRepo.guardar(entidad, sql);
    }

    @Override
    public Optional<RegistroReproductivo> obtenerPorId(Integer id) {
        return Optional.ofNullable(baseRepo.obtenerPorId(id));
    }

    @Override
    public List<RegistroReproductivo> obtenerTodos() {
        return baseRepo.obtenerTodos();
    }

    @Override
    public void actualizar(RegistroReproductivo entidad) {
        String sql = String.format(
            "UPDATE registro_reproductivo SET " +
            "fecha_inseminacion = '%s', " +
            "fecha_parto = %s, " +
            "estado = '%s', " +
            "madre_id = %d, " +
            "padre_id = %d, " +
            "vaca_id = %d " +
            "WHERE id = %d",
            entidad.getFechaInseminacion(),
            entidad.getFechaParto() != null ? "'" + entidad.getFechaParto() + "'" : "NULL",
            entidad.getEstado().name(),
            entidad.getMadreId(),
            entidad.getPadreId(),
            entidad.getVacaId(),
            entidad.getId()
        );
        baseRepo.actualizar(entidad, sql);
    }

    @Override
    public void eliminar(Integer id) {
        baseRepo.eliminar(id);
    }

    @Override
    public List<RegistroReproductivo> obtenerPorCondicion(String condicion) {
        String sql = "SELECT * FROM registro_reproductivo WHERE " + condicion;
        return baseRepo.obtenerTodos(); // Implementación básica
    }

    @Override
    public int contar() {
        return baseRepo.obtenerTodos().size();
    }

    // Metodo especifico para este repositorio
    public List<RegistroReproductivo> obtenerPorVacaId(int vacaId) {
        return obtenerPorCondicion("vaca_id = " + vacaId);
    }
}