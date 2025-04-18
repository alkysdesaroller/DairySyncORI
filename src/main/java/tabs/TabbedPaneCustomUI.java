package tabs;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.Area;
import java.awt.geom.CubicCurve2D;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import javax.swing.plaf.basic.BasicTabbedPaneUI;


/**
 *
 * @author darie
 */
public class TabbedPaneCustomUI extends BasicTabbedPaneUI {

    private final TabbedPaneCustom tab;

    public TabbedPaneCustomUI(TabbedPaneCustom tab) {
        this.tab = tab;
    }

    @Override
    protected void installDefaults() {
        super.installDefaults();
        tabPane.setOpaque(false); // Añade esta línea
    }

    @Override
    protected Insets getTabInsets(int tabPlacement, int tabIndex) {
        return new Insets(5, 30, 5, 30);
    }

    @Override
    protected void paintTabBorder(Graphics g, int tabPlacement, int tabIndex, 
            int x, int y, int width, int height, boolean isSelected) {
        // Dejar vacío para eliminar bordes predeterminados
    }

    @Override
    protected void paintFocusIndicator(Graphics g, int tabPlacement, 
            Rectangle[] rects, int tabIndex, Rectangle iconRect, 
            Rectangle textRect, boolean isSelected) {
        // Dejar vacío para eliminar el indicador de foco
    }

    @Override
    protected void paintTabArea(Graphics g, int tabPlacement, int selectedIndex) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Fondo del área de tabs (transparente)
        g2.setColor(new Color(0, 0, 0, 0));
        g2.fillRect(0, 0, tabPane.getWidth(), calculateTabAreaHeight(tabPlacement, runCount, maxTabHeight));

        // Pintar tabs
        for (int i = 0; i < tabPane.getTabCount(); i++) {
            if (i != selectedIndex) {
                paintTab(g2, i, false);
            }
        }
        
        if (selectedIndex >= 0) {
            paintTab(g2, selectedIndex, true);
        }
        
        g2.dispose();
    }
    private void paintTab(Graphics2D g2, int index, boolean selected) {
        Rectangle rect = getTabBounds(tabPane, index);
        Color color = selected ? ((TabbedPaneCustom)tabPane).getSelectedColor() 
                             : ((TabbedPaneCustom)tabPane).getUnselectedColor();
        
        // Forma del tab (simplificada)
        g2.setColor(color);
        g2.fillRoundRect(rect.x, rect.y, rect.width, rect.height, 15, 15);
        
        // Texto del tab
        g2.setColor(selected ? Color.WHITE : Color.LIGHT_GRAY);
        String title = tabPane.getTitleAt(index);
        FontMetrics fm = g2.getFontMetrics();
        int x = rect.x + (rect.width - fm.stringWidth(title)) / 2;
        int y = rect.y + ((rect.height - fm.getHeight()) / 2) + fm.getAscent();
        g2.drawString(title, x, y);
    }

    @Override
    protected void paintContentBorder(Graphics g, int tabPlacement, int selectedIndex) {
        // NO pintar el borde de contenido predeterminado
    }

    private Shape createTabShape(Rectangle rec) {
        int arc = 10;
        int x = rec.x;
        int y = rec.y;
        int width = rec.width;
        int height = rec.height;
        
        Path2D path = new Path2D.Double();
        path.moveTo(x, y + height);
        path.lineTo(x, y + arc);
        path.quadTo(x, y, x + arc, y);
        path.lineTo(x + width - arc, y);
        path.quadTo(x + width, y, x + width, y + arc);
        path.lineTo(x + width, y + height);
        
        return path;
    }

    private Color getTabColor(boolean selected) {
        return selected ? tab.getSelectedColor() : tab.getUnselectedColor();
    }
}