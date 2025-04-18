package views;

import ModuloDariel.repo.RegistroSaludRepo;
import ModuloDariel.repo.VacaRepo;
import ModuloDariel.usecase.RegistroSaludServicio;
import ModuloDariel.usecase.VacaServicio;
import controller.GestorVacaController;
import controller.RegistroSaludController;
import tabs.TabbedPaneCustom;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.EtchedBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

/**
 * Interfaz gráfica para la gestión de vacas y sus registros de salud
 */
public class GestorVacas extends JFrame {
    // Controlador principal
    private final GestorVacaController controller;
    
    // Componentes de la tabla
    private JTable table;
    private DefaultTableModel tableModel;
    
    // Servicios para operaciones de negocio
    private final VacaServicio vacaServicio;
    private final RegistroSaludServicio registroSaludServicio;
    
    // Panel para registros de salud
    private PanelRegistrosSalud panelRegistroSalud;

    /**
     * Punto de entrada de la aplicación
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Inicializar repositorios y servicios
            VacaRepo vacaRepo = new VacaRepo();
            RegistroSaludRepo registroSaludRepo = new RegistroSaludRepo();
            RegistroSaludServicio registroSaludServicio = new RegistroSaludServicio(registroSaludRepo);
            VacaServicio vacaServicio = new VacaServicio(registroSaludServicio);

            // Crear controladores
            GestorVacaController gestorVacaController = new GestorVacaController(vacaServicio, registroSaludServicio);
            RegistroSaludController registroSaludController = new RegistroSaludController(registroSaludServicio, vacaServicio);

            // Iniciar interfaz gráfica
            new GestorVacas(gestorVacaController, vacaServicio, registroSaludServicio, registroSaludController);
        });
    }

    /**
     * Constructor principal
     * @param controller Controlador de vacas
     * @param vacaServicio Servicio de operaciones con vacas
     * @param registroSaludServicio Servicio de registros de salud
     * @param registroSaludController Controlador de registros de salud
     */
    public GestorVacas(GestorVacaController controller, VacaServicio vacaServicio, 
                      RegistroSaludServicio registroSaludServicio, RegistroSaludController registroSaludController) {
        this.controller = controller;
        this.vacaServicio = vacaServicio;
        this.registroSaludServicio = registroSaludServicio;

        // Configuración básica de la ventana
        setTitle("Gestión Vacas");
        setSize(900, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(900, 600));

        // Establecer look and feel
        try {
            UIManager.setLookAndFeel(new com.formdev.flatlaf.FlatDarculaLaf());
        } catch (Exception ex) {
            System.err.println("Error al inicializar FlatLaf");
        }

        // Panel principal
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(207, 184, 143));

        // Configurar pestañas
        TabbedPaneCustom tabbedPane = new TabbedPaneCustom();
        tabbedPane.setSelectedColor(new Color(108, 67, 37));
        tabbedPane.setUnselectedColor(new Color(108, 67, 37));

        // Panel de gestión de vacas
        JPanel gestionVacaPanel = createStyledPanel(new Color(207, 184, 143));

        // Barra superior
        JLabel topBar = new JLabel();
        topBar.setBackground(new Color(108, 67, 37));
        topBar.setOpaque(true);
        topBar.setLayout(new BorderLayout());

        // Configurar tabla de vacas
        String[] columnNames = {"ID", "Raza", "Edad", "Genealogía", "Granja Id", "Madre Id", "Padre Id", "Estado Salud", "Estado Reproductivo"};
        tableModel = new DefaultTableModel(new Object[][]{}, columnNames);
        
        // Personalizar tabla
        table = new JTable(tableModel) {
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component c = super.prepareRenderer(renderer, row, column);
                if (row == getRowCount() - 1) { // Última fila (total)
                    c.setBackground(new Color(0x3B170B));
                    c.setForeground(Color.WHITE);
                } else if (isRowSelected(row)) { // Fila seleccionada
                    c.setBackground(new Color(93, 110, 82));
                    c.setForeground(Color.WHITE);
                } else { // Filas normales
                    c.setBackground(new Color(150, 151, 119));
                    c.setForeground(Color.WHITE);
                }
                return c;
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Tabla no editable
            }

            @Override
            protected void paintComponent(Graphics g) {
                g.setColor(new Color(150, 151, 119));
                g.fillRect(0, 0, getWidth(), getHeight());
                super.paintComponent(g);
            }
        };

        // Estilo de la tabla
        table.setFont(new Font("Serif", Font.BOLD, 16));
        table.getTableHeader().setFont(new Font("Serif", Font.BOLD, 16));
        table.getTableHeader().setBackground(new Color(108, 67, 37));
        table.getTableHeader().setForeground(Color.WHITE);
        table.setGridColor(Color.BLACK);
        table.setShowGrid(true);
        table.setRowHeight(30);
        table.setFillsViewportHeight(true);

        // Scroll pane para la tabla
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.getViewport().setBackground(new Color(150, 151, 119));
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        // Barra inferior con botones
        JPanel bottomBar = new JPanel(new BorderLayout());
        bottomBar.setBackground(new Color(108, 67, 37));
        bottomBar.setPreferredSize(new Dimension(getWidth(), 80));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        
        // Botones de acción
        JButton addButton = new JButton("Agregar Vaca");
        JButton editButton = new JButton("Editar");
        JButton updateStatusButton = new JButton("Actualizar Estado");
        JButton deleteButton = new JButton("Eliminar");
        
        // Estilizar botones
        styleLargeButton(addButton);
        styleLargeButton(editButton);
        styleLargeButton(updateStatusButton);
        styleLargeButton(deleteButton);
        
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(updateStatusButton);
        buttonPanel.add(deleteButton);
        bottomBar.add(buttonPanel, BorderLayout.CENTER);

        // Diseño del panel de gestión
        gestionVacaPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.weighty = 0.0;
        gbc.insets = new Insets(0, 0, 10, 0);
        gestionVacaPanel.add(topBar, gbc);

        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(0, 0, 10, 0);
        gestionVacaPanel.add(scrollPane, gbc);

        gbc.gridy = 2;
        gbc.weightx = 1.0;
        gbc.weighty = 0.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 0, 0, 0);
        gestionVacaPanel.add(bottomBar, gbc);

        // Panel de registros de salud
        panelRegistroSalud = new PanelRegistrosSalud(registroSaludServicio, vacaServicio, this, registroSaludController);
        
        // Añadir pestañas
        tabbedPane.addTab("Gestión Vaca", gestionVacaPanel);
        tabbedPane.addTab("Registro Salud", panelRegistroSalud);

        // Contenedor de pestañas
        JPanel tabbedPaneWrapper = new JPanel(new BorderLayout());
        tabbedPaneWrapper.setBackground(new Color(207, 184, 143));
        tabbedPaneWrapper.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        tabbedPaneWrapper.add(tabbedPane, BorderLayout.CENTER);

        // Configurar ventana principal
        mainPanel.add(tabbedPaneWrapper, BorderLayout.CENTER);
        add(mainPanel);

        // Listeners para botones
        addButton.addActionListener(e -> {
            controller.agregarVaca(this);
            refrescarTabla();
        });
        
        editButton.addActionListener(e -> {
            controller.editarVaca(this);
            refrescarTabla();
        });
        
        updateStatusButton.addActionListener(e -> {
            controller.actualizarEstado(this);
            refrescarTabla();
        });
        
        deleteButton.addActionListener(e -> {
            controller.eliminarVaca(this);
            refrescarTabla();
        });

        // Mostrar datos iniciales
        refrescarTabla();
        setVisible(true);
    }

    /**
     * Crea un panel con estilo
     * @param backgroundColor Color de fondo
     * @return Panel configurado
     */
    private JPanel createStyledPanel(Color backgroundColor) {
        JPanel panel = new JPanel();
        panel.setBackground(backgroundColor);
        return panel;
    }

    /**
     * Estiliza un botón grande
     * @param button Botón a estilizar
     */
    private void styleLargeButton(JButton button) {
        button.setFont(new Font("Serif", Font.BOLD, 16));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(108, 67, 37));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEtchedBorder(EtchedBorder.LOWERED),
                BorderFactory.createEmptyBorder(15, 30, 15, 30)
        ));
    }

    /**
     * Actualiza los datos en la tabla de vacas
     */
    public void refrescarTabla() {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0); // Limpiar tabla
        
        // Obtener y mostrar todas las vacas
        var vacas = vacaServicio.obtenerTodasLasVacas();
        for (var vaca : vacas) {
            model.addRow(new Object[]{
                vaca.getId(),
                vaca.getRaza(),
                vaca.getEdad(),
                vaca.getGenealogia(),
                vaca.getGranjaId(),
                vaca.getMadreId(),
                vaca.getPadreId(),
                vaca.getEstadoSalud(),
                vaca.getEstadoReproductivo()
            });
        }

        // Añadir fila de total
        Object[] totalRow = new Object[9];
        totalRow[8] = "Total: " + vacas.size() + " Vacas";
        model.addRow(totalRow);
    }

    /**
     * Actualiza la tabla de registros de salud
     */
    public void refrescarTablaRegistrosSalud() {
        if (panelRegistroSalud != null) {
            panelRegistroSalud.refrescarTabla();
        }
    }
}