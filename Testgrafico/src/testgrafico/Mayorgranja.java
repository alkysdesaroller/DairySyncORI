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

public class Mayorgranja {
    
    public static void main(String[] args) {
        mostrarReporte();
    }
    
    public static void mostrarReporte() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        
        try (Connection conn = Bdconection.conectar()) {
            String sql = """
                SELECT 
                    g.Id AS granjero_id,
                    g.Nombre AS granjero_nombre,
                    gr.Id AS granja_id,
                    gr.Direccion AS granja_direccion,
                    COUNT(v.Id) AS cantidad_vacas,
                    SUM(p.litros) AS total_produccion,
                    COUNT(r.Id) AS total_reproducciones
                FROM 
                    Granjero g
                JOIN 
                    Granja gr ON g.Id = gr.granjeroId
                LEFT JOIN 
                    Vaca v ON gr.Id = v.granjaId
                LEFT JOIN 
                    Produccion p ON v.Id = p.vacaId
                LEFT JOIN 
                    Reproduccion r ON v.Id = r.vacaId
                GROUP BY 
                    g.Id, gr.Id
                ORDER BY 
                    total_produccion DESC
                LIMIT 1;
                """;
            
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            
            while (rs.next()) {
                String granjaNombre = rs.getString("granja_direccion");
                double totalProduccion = rs.getDouble("total_produccion");
                
                // Agregar datos al dataset
                dataset.addValue(totalProduccion, "Producción Total", granjaNombre);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        // Crear el gráfico de barras
        JFreeChart barChart = ChartFactory.createBarChart(
            "Producción Total de Leche por Granja", // Título
            "Granja",                              // Etiqueta del eje X
            "Litros Totales",                      // Etiqueta del eje Y
            dataset,
            PlotOrientation.VERTICAL,
            true, true, false
        );
        
        // Establecer título y fuente personalizada
        barChart.setTitle(new TextTitle("Producción Total de Leche por Granja", new java.awt.Font("Serif", java.awt.Font.BOLD, 18)));
        
        // Personalizar el gráfico: cambiar color de las barras
        CategoryPlot plot = barChart.getCategoryPlot();
        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setSeriesPaint(0, Color.BLUE);  // Cambiar el color de las barras (a azul)

        // Personalizar el fondo del gráfico
        plot.setBackgroundPaint(Color.black);
        plot.setDomainGridlinePaint(Color.GRAY);
        plot.setRangeGridlinePaint(Color.GRAY);
        
        // Crear ventana para mostrar el gráfico
        JFrame frame = new JFrame("Reporte de Producción por Granja");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.add(new ChartPanel(barChart));
        frame.setLocationRelativeTo(null);  // Centrar la ventana
        frame.setVisible(true);
    }
}
