// Paquete donde se encuentra esta clase
package reportegraf;

/**
 * Autor: Leury Brand
 */

// Importaciones necesarias para la conexión a base de datos y para crear el gráfico
import com.mycompany.reportegrafico.datos.Conexion; // Clase personalizada para conectarse a la base de datos
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JFrame;

import org.jfree.chart.ChartFactory; // Para crear el gráfico
import org.jfree.chart.ChartPanel;   // Para mostrar el gráfico dentro de una ventana
import org.jfree.chart.JFreeChart;   // Representa el gráfico en sí
import org.jfree.chart.plot.CategoryPlot; // Parte del gráfico que se puede personalizar
import org.jfree.chart.plot.PlotOrientation; // Orientación del gráfico (vertical o horizontal)
import org.jfree.chart.renderer.category.BarRenderer; // Permite cambiar el color de las barras
import org.jfree.data.category.DefaultCategoryDataset; // Conjunto de datos para el gráfico de barras
import org.jfree.chart.title.TextTitle; // Título del gráfico
import java.awt.Color;
import java.awt.Font;

/**
 * Clase principal que genera un gráfico de barras con la producción de leche por granja,
 * filtrando por el correo del granjero autenticado.
 */
public class ReporteGraficoMain {

    // Método principal que se ejecuta al iniciar el programa
    public static void main(String[] args) {
        // Correo del granjero (esto usualmente vendría de una sesión o autenticación)
        String correoUsuario = "juan@gmail.com"; // Puedes cambiarlo por otro correo
        mostrarReporte(correoUsuario); // Llamamos al método para generar y mostrar el gráfico
    }

    /**
     * Este método se encarga de obtener los datos desde la base de datos
     * y crear un gráfico de barras mostrando la producción total por granja.
     * 
     * @param correoUsuario El correo del granjero para filtrar los datos
     */
    public static void mostrarReporte(String correoUsuario) {
        // Creamos un dataset vacío donde se van a guardar los datos
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        // Creamos una instancia de la clase que maneja la conexión a la base de datos
        Conexion conexion = new Conexion();

        try (Connection conn = conexion.conectar()) {
            // Consulta SQL: suma la producción por granja del granjero con el correo indicado
            String sql = """
                SELECT 
                    GR.nombreGranja AS granja_nombre,
                    SUM(P.litros) AS total_produccion
                FROM Produccion P
                JOIN Granja GR ON P.granjaId = GR.Id
                JOIN Granjero G ON GR.granjeroId = G.Id
                WHERE G.Correo = ?
                GROUP BY GR.nombreGranja
                ORDER BY total_produccion DESC;
            """;

            // Preparamos la consulta evitando inyección SQL
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, correoUsuario); // Colocamos el correo en la consulta

                // Ejecutamos la consulta y guardamos los resultados
                ResultSet rs = stmt.executeQuery();

                // Recorremos cada resultado y lo agregamos al dataset
                while (rs.next()) {
                    String nombreGranja = rs.getString("granja_nombre");
                    double totalProduccion = rs.getDouble("total_produccion");
                    dataset.addValue(totalProduccion, "Producción Total", nombreGranja);
                }
            }

        } catch (SQLException e) {
            // Si hay un error al conectarse o consultar, se muestra en consola
            System.err.println("Error al conectar o consultar datos: " + e.getMessage());
            e.printStackTrace();
            return;
        }

        // Creamos el gráfico de barras con el dataset que ya llenamos
        JFreeChart barChart = ChartFactory.createBarChart(
            "Producción Total de Leche en la Granja ", // Título del gráfico
            "Granja", // Etiqueta del eje X
            "Litros Totales", // Etiqueta del eje Y
            dataset, // Datos
            PlotOrientation.VERTICAL, // Orientación de las barras
            true, true, false // Mostrar leyenda, tooltips, URLs
        );

        // Cambiamos el título principal con fuente más grande
        barChart.setTitle(new TextTitle("Producción Total de Leche por Granja", new Font("Serif", Font.BOLD, 18)));

        // Personalizamos el fondo y los colores del gráfico
        CategoryPlot plot = barChart.getCategoryPlot();
        plot.setBackgroundPaint(new Color(212, 187, 141)); // Color de fondo del gráfico
        plot.setDomainGridlinePaint(Color.GRAY); // Líneas verticales
        plot.setRangeGridlinePaint(Color.GRAY);  // Líneas horizontales

        // Cambiamos el color de las barras
        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setSeriesPaint(0, new Color(126, 143, 74)); // Verde oliva

        // Creamos una ventana (JFrame) para mostrar el gráfico
        JFrame frame = new JFrame("Reporte de Producción");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Cerrar la app al cerrar la ventana
        frame.setSize(800, 600); // Tamaño de la ventana
        frame.setLocationRelativeTo(null); // Centrar la ventana
        frame.add(new ChartPanel(barChart)); // Agregamos el gráfico a la ventana
        frame.setVisible(true); // Mostramos la ventana
    }
}
