package ModuloDariel.repo;

import ModuloDariel.models.RegistroSalud;
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
public class RegistroSaludRepo implements IBaseInterface<RegistroSalud, Integer> {
    private final IbaseRepo<RegistroSalud> baseRepo;

    public RegistroSaludRepo() {
        String[] columnas = {"id", "vacaId", "fecha", "estadoSalud", "descripcion", "tratamiento"};
        this.baseRepo = new IbaseRepo<>("registros_salud", columnas, new MapperRegistroSalud());
    }

    private static class MapperRegistroSalud implements IbaseRepo.Mapper<RegistroSalud> {
        @Override
        public RegistroSalud map(ResultSet rs) throws SQLException {
            return new RegistroSalud(
                rs.getInt("id"),
                rs.getInt("vacaId"),
                rs.getDate("fecha").toLocalDate(),
                Vaca.EstadoSalud.valueOf(rs.getString("estadoSalud")),
                rs.getString("descripcion"),
                rs.getString("tratamiento")
            );
        }
    }

    @Override
    public void guardar(RegistroSalud entidad) {
        String sql = String.format(
            "INSERT INTO registros_salud (vacaId, fecha, estadoSalud, descripcion, tratamiento) " +
            "VALUES (%d, '%s', '%s', '%s', '%s')",
            entidad.getVacaId(),
            entidad.getFecha(),
            entidad.getEstadoSalud().name(),
            entidad.getDescripcion(),
            entidad.getTratamiento()
        );
        baseRepo.guardar(entidad, sql);
    }

    @Override
    public Optional<RegistroSalud> obtenerPorId(Integer id) {
        return Optional.ofNullable(baseRepo.obtenerPorId(id));
    }

    @Override
    public List<RegistroSalud> obtenerTodos() {
        return baseRepo.obtenerTodos();
    }

    @Override
    public void actualizar(RegistroSalud entidad) {
        String sql = String.format(
            "UPDATE registros_salud SET " +
            "vacaId = %d, fecha = '%s', estadoSalud = '%s', " +
            "descripcion = '%s', tratamiento = '%s' " +
            "WHERE id = %d",
            entidad.getVacaId(),
            entidad.getFecha(),
            entidad.getEstadoSalud().name(),
            entidad.getDescripcion(),
            entidad.getTratamiento(),
            entidad.getId()
        );
        baseRepo.actualizar(entidad, sql);
    }

    @Override
    public void eliminar(Integer id) {
        baseRepo.eliminar(id);
    }

    @Override
    public List<RegistroSalud> obtenerPorCondicion(String condicion) {
        String sql = "SELECT * FROM registros_salud WHERE " + condicion;
        return baseRepo.obtenerTodos(); // Implementación básica - deberías modificar IbaseRepo para soportar condiciones
    }

    @Override
    public int contar() {
        return this.obtenerTodos().size();
    }
}