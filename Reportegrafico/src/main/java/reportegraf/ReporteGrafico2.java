package reportegraf;



/**
 * Autor: Leury Brand
 */

// Importaciones necesarias para conectarse a la base de datos y generar el gráfico

// Conexion: Permite establecer la conexión con la base de datos y ejecutar consultas SQL.
import com.mycompany.reportegrafico.datos.Conexion;

// Necesarias para trabajar con la base de datos (Conexión, ejecución de consultas y manejo de resultados).
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

// Importaciones de Swing para la creación de interfaces gráficas, como la ventana (JFrame) donde se mostrará el gráfico.
import javax.swing.JFrame;

// Librerías de JFreeChart necesarias para crear y personalizar gráficos. Esta librería es muy popular para crear gráficos en Java.
import org.jfree.chart.ChartFactory; // Para crear gráficos a partir de datasets.
import org.jfree.chart.ChartPanel;   // Para agregar el gráfico a un panel y mostrarlo en la interfaz gráfica.
import org.jfree.chart.JFreeChart;   // Representa el gráfico como tal.
import org.jfree.chart.plot.CategoryPlot; // Se usa para personalizar el diseño del gráfico de barras (por ejemplo, colores, fondo).
import org.jfree.chart.plot.PlotOrientation; // Define la orientación del gráfico (horizontal o vertical).
import org.jfree.chart.renderer.category.BarRenderer; // Se utiliza para definir el estilo de las barras del gráfico.
import org.jfree.data.category.DefaultCategoryDataset; // Representa los datos que se mostrarán en el gráfico.

import org.jfree.chart.title.TextTitle; // Permite personalizar el título del gráfico, cambiando fuentes y estilos.

import java.awt.Color;  // Permite manipular colores en la interfaz gráfica (como el color de fondo o de las barras).
import java.awt.Font;   // Para personalizar la fuente y estilo del texto, como el título del gráfico.



/**
 * Clase principal para generar un gráfico de barras que muestra
 * el total de vacas que hay en cada granja asociada a un granjero específico.
 * 
 * Este reporte se construye a partir de los datos obtenidos desde una base de datos.
 */
public class ReporteGrafico2 {

    public static void main(String[] args) {
        // Correo del granjero (usualmente se obtiene al iniciar sesión)
        String correoUsuario = "juan@gmail.com"; // Este correo se usa para filtrar los datos de la consulta

        // Llamada al método que genera el gráfico
        mostrarReporte(correoUsuario);
    }

    /**
     * Método que consulta la base de datos para obtener la cantidad de vacas por granja
     * asociada al granjero cuyo correo se proporciona. Luego muestra un gráfico con esa información.
     *
     * @param correoUsuario Correo del granjero autenticado (filtra las granjas correspondientes).
     */
    public static void mostrarReporte(String correoUsuario) {
        // Crear un dataset vacío donde se almacenarán los datos para el gráfico
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        Conexion conexion = new Conexion(); // Objeto que maneja la conexión con la base de datos

        try (Connection conn = conexion.conectar()) {
            // Consulta SQL para contar el número de vacas por granja filtrando por el correo del granjero
            String sql = """
                SELECT 
                    G.nombreGranja AS nombre_granja,
                    COUNT(V.Id) AS total_vacas
                FROM 
                    Granja G
                LEFT JOIN 
                    Vaca V ON G.Id = V.granjaId
                JOIN 
                    Granjero GR ON G.granjeroId = GR.Id
                WHERE 
                    GR.Correo = ?
                GROUP BY 
                    G.nombreGranja;
            """;

            // PreparedStatement permite ejecutar la consulta evitando inyecciones SQL
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, correoUsuario); // Se establece el correo como parámetro

                // Ejecutar la consulta
                ResultSet rs = stmt.executeQuery();

                // Recorrer los resultados y agregarlos al dataset
                while (rs.next()) {
                    String nombreGranja = rs.getString("nombre_granja");
                    int totalVacas = rs.getInt("total_vacas");
                    dataset.addValue(totalVacas, "Total de Vacas", nombreGranja);
                }
            }

        } catch (SQLException e) {
            // Mostrar mensaje de error en caso de falla en la conexión o consulta
            System.err.println("Error al conectar o consultar datos: " + e.getMessage());
            e.printStackTrace();
            return;
        }

        // Crear un gráfico de barras a partir del dataset
        JFreeChart barChart = ChartFactory.createBarChart(
            "Total de Vacas que hay en la Granja", // Título del gráfico
            "Granja",                              // Etiqueta del eje X
            "Número de Vacas",                     // Etiqueta del eje Y
            dataset,                               // Conjunto de datos
            PlotOrientation.VERTICAL,              // Orientación del gráfico
            true, true, false                      // Leyenda, tooltips, URLs
        );

        // Personalizar el título del gráfico
        barChart.setTitle(new TextTitle("Total de Vacas que hay en la Granja", new Font("Serif", Font.BOLD, 18)));

        // Configuración del diseño del gráfico
        CategoryPlot plot = barChart.getCategoryPlot();
        plot.setBackgroundPaint(new Color(212, 187, 141));   // Color de fondo del área de gráfico
        plot.setDomainGridlinePaint(Color.GRAY);             // Líneas del eje X
        plot.setRangeGridlinePaint(Color.GRAY);              // Líneas del eje Y

        // Cambiar color de las barras
        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setSeriesPaint(0, new Color(126, 143, 74)); // Color de las barras

        // Crear una ventana para mostrar el gráfico
        JFrame frame = new JFrame("Reporte de Vacas");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);                  // Tamaño de la ventana
        frame.setLocationRelativeTo(null);        // Centrar la ventana
        frame.add(new ChartPanel(barChart));      // Agregar el gráfico a la ventana
        frame.setVisible(true);                   // Hacer visible la ventana
    }
}