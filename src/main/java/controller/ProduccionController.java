
package controller;
import java.sql.SQLException;
import java.time.LocalDate;
import model.ProduccionDiaria;
import model.services.ProduccionServicios;
import java.util.Date;
/**
 *
 * @author alna7
 */
public class ProduccionController {
    private final ProduccionServicios produccionServicios;
    
    public ProduccionController(){
    this.produccionServicios = new ProduccionServicios(); 
    }
    
    public void registrarProduccionDiaria() throws SQLException{
    ProduccionDiaria nuevaProduccion = new ProduccionDiaria();
      nuevaProduccion.setFecha(LocalDate.EPOCH);
      nuevaProduccion.setLitros(25.5f);
      nuevaProduccion.setVacaId(1);
      nuevaProduccion.setGranjaId(1);
    
   // Usar la instancia de ProduccionServicios
        this.produccionServicios.registrarProduccion(nuevaProduccion);
    }

      public void mostrarPromedioProduccion() {
        int vacaId = 1;
        // Usar la instancia de ProduccionServicios
        float promedio = this.produccionServicios.obtenerPromedioProduccion(vacaId);
        System.out.println("Promedio de producción: " + promedio + " litros");
    }
}

