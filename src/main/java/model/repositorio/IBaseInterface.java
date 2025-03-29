
package model.repositorio;
 import java.util.List;
import java.util.Optional;
import java.util.List; /**
 *
 * @author alna7
 */
public interface IBaseInterface<T, ID> {

    // Métodos CRUD básicos
    void guardar(T entidad);
    Optional<T> obtenerPorId(ID id);
    List<T> obtenerTodos();
    void actualizar(T entidad);
    void eliminar(ID id);

    // Métodos adicionales (opcionales)
    List<T> obtenerPorCondicion(String condicion);
    int contar();
    }

