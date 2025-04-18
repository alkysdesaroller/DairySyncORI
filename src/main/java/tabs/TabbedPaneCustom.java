/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tabs;
        
import java.awt.Color;
import java.awt.Insets;
import javax.swing.JTabbedPane;

/**
 *
 * @author darie
 */
public class TabbedPaneCustom extends JTabbedPane {

    public Color getSelectedColor() {
        return selectedColor;
    }

    public void setSelectedColor(Color selectedColor) {
        this.selectedColor = selectedColor;
        repaint();
    }

    public Color getUnselectedColor() {
        return unselectedColor;
    }

    public void setUnselectedColor(Color unselectedColor) {
        this.unselectedColor = unselectedColor;
        repaint();
    }

    private Color selectedColor = new Color(248, 91, 50);
    private Color unselectedColor = new Color(60, 60, 60);
    
    public TabbedPaneCustom() {
        setUI(new TabbedPaneCustomUI(this));
        setBackground(new Color(0, 0, 0, 0)); // Fondo transparente
        setOpaque(false);
        
        // Configuración especial para FlatLaf
        putClientProperty("JTabbedPane.tabType", "card");
        putClientProperty("JTabbedPane.tabInsets", new Insets(5, 10, 5, 10));
        putClientProperty("JTabbedPane.showTabSeparators", true);
        putClientProperty("JTabbedPane.tabSeparatorsFullHeight", true);
    }
}