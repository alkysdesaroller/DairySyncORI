package testgrafico;

import java.sql.*;
import javax.swing.JFrame;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.chart.renderer.category.BarRenderer;
import java.awt.Color;
import org.jfree.chart.title.TextTitle;

public class GraficoProduccion {
    
    public static void main(String[] args) {
        mostrarGrafico();
    }
    
    public static void mostrarGrafico() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        
        try (Connection conn = Bdconection.conectar()) {
            String sql = """
                SELECT V.id AS VacaID, V.raza, ROUND(AVG(P.litros), 2) AS Promedio_Litros
                FROM Produccion P
                JOIN Vaca V ON P.vacaId = V.Id
                GROUP BY V.id, V.raza
                HAVING AVG(P.litros) > 30
                ORDER BY Promedio_Litros DESC;
                """;
            
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            
            while (rs.next()) {
                String raza = rs.getString("raza");
                double litros = rs.getDouble("Promedio_Litros");
                dataset.addValue(litros, "Producción", raza);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        // Crear el gráfico de barras
        JFreeChart barChart = ChartFactory.createBarChart(
            "Producción de Leche por Raza",  // Título
            "Raza de la Vaca",               // Etiqueta del eje X
            "Litros Promedio",               // Etiqueta del eje Y
            dataset,
            PlotOrientation.VERTICAL,
            true, true, false
        );
        
        // Establecer título y estilo
        barChart.setTitle(new TextTitle("Producción de Leche por Raza", new java.awt.Font("Serif", java.awt.Font.BOLD, 18)));
        
        // Personalizar el gráfico: cambiar color de las barras
        CategoryPlot plot = barChart.getCategoryPlot();
        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setSeriesPaint(0, Color.GREEN);  // Cambiar el color de las barras (a verde)

        // Personalizar el fondo del gráfico
        plot.setBackgroundPaint(Color.white);
        plot.setDomainGridlinePaint(Color.GRAY);
        plot.setRangeGridlinePaint(Color.GRAY);
        
        // Crear ventana para mostrar el gráfico
        JFrame frame = new JFrame("Gráfico de Producción");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.add(new ChartPanel(barChart));
        frame.setLocationRelativeTo(null);  // Centrar la ventana
        frame.setVisible(true);
    }
}
