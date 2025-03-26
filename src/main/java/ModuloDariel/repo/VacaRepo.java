package ModuloDariel.repo;

import ModuloDariel.models.Vaca;
import model.repositorio.IBaseInterface;
import model.repositorio.IbaseRepo;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
/**
 *
 * @author darie
 */
public class VacaRepo implements IBaseInterface<Vaca, Integer> {
    private final IbaseRepo<Vaca> baseRepo;

    public VacaRepo() {
        String[] columnas = {"id", "raza", "edad", "genealogia", "granjaId", "estadoSalud", "estadoReproductivo"};
        this.baseRepo = new IbaseRepo<>("Vaca", columnas, new MapperVaca());
    }

    private static class MapperVaca implements IbaseRepo.Mapper<Vaca> {
        @Override
        public Vaca map(ResultSet rs) throws SQLException {
            return new Vaca(
                rs.getInt("id"),
                rs.getString("raza"),
                rs.getInt("edad"),
                rs.getString("genealogia"),
                rs.getInt("granjaId"),
                Vaca.EstadoSalud.valueOf(rs.getString("estadoSalud")),
                Vaca.EstadoReproductivo.valueOf(rs.getString("estadoReproductivo"))
            );
        }
    }

    @Override
    public void guardar(Vaca entidad) {
        String sql = String.format(
            "INSERT INTO Vaca (raza, edad, genealogia, granjaId, estadoSalud, estadoReproductivo) " +
            "VALUES ('%s', %d, '%s', %d, '%s', '%s')",
            entidad.getRaza(),
            entidad.getEdad(),
            entidad.getGenealogia(),
            entidad.getGranjaId(),
            entidad.getEstadoSalud().name(),
            entidad.getEstadoReproductivo().name()
        );
        baseRepo.guardar(entidad, sql);
    }

    @Override
    public Optional<Vaca> obtenerPorId(Integer id) {
        return Optional.ofNullable(baseRepo.obtenerPorId(id));
    }

    @Override
    public List<Vaca> obtenerTodos() {
        return baseRepo.obtenerTodos();
    }

    @Override
    public void actualizar(Vaca entidad) {
        String sql = String.format(
            "UPDATE Vaca SET raza = '%s', edad = %d, genealogia = '%s', " +
            "granjaId = %d, estadoSalud = '%s', estadoReproductivo = '%s' " +
            "WHERE id = %d",
            entidad.getRaza(),
            entidad.getEdad(),
            entidad.getGenealogia(),
            entidad.getGranjaId(),
            entidad.getEstadoSalud().name(),
            entidad.getEstadoReproductivo().name(),
            entidad.getId()
        );
        baseRepo.actualizar(entidad, sql);
    }

    @Override
    public void eliminar(Integer id) {
        baseRepo.eliminar(id);
    }

    @Override
    public List<Vaca> obtenerPorCondicion(String condicion) {
        
        return null;
    }

    @Override
    public int contar() {
        return 0;
    }
}
