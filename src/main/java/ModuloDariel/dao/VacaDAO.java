package ModuloDariel.dao;

import ModuloDariel.models.Vaca;
import java.util.List;
/**
 *
 * @author darie
 */
public interface VacaDAO {
    void agregarVaca(Vaca vaca);
    Vaca obtenerVacaPorId(int id);
    List<Vaca> obtenerTodasLasVacas();
    void actualizarVaca(Vaca vaca);
    void eliminarVaca(int id);
}
