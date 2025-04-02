package test;  // Añade este paquete

import ModuloDariel.usecase.VacaServicio;
import ModuloDariel.repo.VacaRepo;
import ModuloDariel.models.Vaca;
import ModuloDariel.usecase.RegistroSaludServicio;
import ModuloDariel.repo.RegistroSaludRepo;

/**
 *
 * @author darie
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("Iniciando sistema de gestion ganadera");
        
        // Configuración inicial
        VacaRepo vacaRepo = new VacaRepo();
        RegistroSaludRepo saludRepo = new RegistroSaludRepo();
        RegistroSaludServicio saludServicio = new RegistroSaludServicio(saludRepo);
        VacaServicio vacaServicio = new VacaServicio(saludServicio);
        
        // Prueba de registro
        Vaca nuevaVaca = vacaServicio.registrarVaca("Jersey", 4, "Genealogia-456", 1);
        System.out.println("Vaca registrada - ID: " + nuevaVaca.getId());
        
        System.out.println("\n=== Datos registrados ===");
        System.out.println("Raza: " + nuevaVaca.getRaza());
        System.out.println("Estado salud: " + nuevaVaca.getEstadoSalud());
    }
}