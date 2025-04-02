package test;

import ModuloDariel.models.*;
import ModuloDariel.repo.*;
import ModuloDariel.usecase.*;
import java.time.LocalDate;

public class LocalTest {
    public static void main(String[] args) {
        System.out.println("=== INICIO DE PRUEBAS DEL SISTEMA GANADERO ===");
        
        // 1. Inicialización de componentes
        VacaRepo vacaRepo = new VacaRepo();
        RegistroSaludRepo saludRepo = new RegistroSaludRepo();
        RegistroReproductivoRepo reprodRepo = new RegistroReproductivoRepo();
        
        RegistroSaludServicio saludServicio = new RegistroSaludServicio(saludRepo);
        RegistroReproductivoServicio reprodServicio = new RegistroReproductivoServicio(reprodRepo);
        VacaServicio vacaServicio = new VacaServicio(saludServicio);

        // 2. Registro de vacas iniciales 
        System.out.println("Registrando vacas iniciales...");
        Vaca vacaMadre = vacaServicio.registrarVaca("Holstein", 5, "GenealogiaMadre", 1, null, null);
        Vaca vacaPadre = vacaServicio.registrarVaca("Holstein", 6, "GenealogiaPadre", 1, null, null);
        
        // 3. Registro de nueva vaca con parentesco
        System.out.println("\n--- Registrando nueva vaca ---");
        Vaca nuevaVaca = vacaServicio.registrarVaca("Holstein", 2, "GEN-003", 1, vacaMadre.getId(), vacaPadre.getId());
        System.out.println("Nueva vaca registrada ID: " + nuevaVaca.getId());

        // 4. Actualización de estados
        System.out.println("\n--- Actualizando estados ---");
        vacaServicio.actualizarEstadoSalud(nuevaVaca.getId(), Vaca.EstadoSalud.ENFERMA, "Tos persistente", "Antibióticos cada 8 horas");
        
        vacaServicio.actualizarEstadoReproductivo(nuevaVaca.getId(), Vaca.EstadoReproductivo.PREÑADA);

        // 5. Registro reproductivo
        System.out.println("\n--- Creando registro reproductivo ---");
        reprodServicio.crearRegistroReproductivo(
            LocalDate.now(), 
            Vaca.EstadoReproductivo.PREÑADA,
            vacaMadre.getId(),
            vacaPadre.getId(),
            nuevaVaca.getId()
        );

        // 6. Consulta y muestra de resultados
        System.out.println("\n=== RESUMEN FINAL ===");
        System.out.println("Vaca ID: " + nuevaVaca.getId());
        System.out.println("Raza: " + nuevaVaca.getRaza());
        System.out.println("Madre ID: " + nuevaVaca.getMadreId());
        System.out.println("Padre ID: " + nuevaVaca.getPadreId());
        System.out.println("Estado Salud: " + nuevaVaca.getEstadoSalud());
        System.out.println("Estado Reproductivo: " + nuevaVaca.getEstadoReproductivo());

        System.out.println("\nPruebas completadas exitosamente!");
    }
}