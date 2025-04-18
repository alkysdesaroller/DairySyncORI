package views;

import ModuloDariel.models.RegistroSalud;
import ModuloDariel.models.Vaca;
import ModuloDariel.usecase.RegistroSaludServicio;
import ModuloDariel.usecase.VacaServicio;
import controller.RegistroSaludController;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import javax.swing.table.TableCellRenderer;

/**
 * Panel para la gestión de registros de salud de las vacas
 */
public class PanelRegistrosSalud extends JPanel {
    // Servicios para acceder a los datos
    private final RegistroSaludServicio registroSaludServicio;
    private final VacaServicio vacaServicio;
    
    // Controlador para operaciones
    private final RegistroSaludController controlador;
    
    // Componentes de la interfaz
    private JTable tabla;
    private DefaultTableModel modeloTabla;

    /**
     * Constructor del panel
     * @param registroSaludServicio Servicio de registros de salud
     * @param vacaServicio Servicio de vacas
     * @param parent Ventana padre
     * @param controlador Controlador de registros de salud
     */
    public PanelRegistrosSalud(RegistroSaludServicio registroSaludServicio, VacaServicio vacaServicio, 
                              GestorVacas parent, RegistroSaludController controlador) {
        this.registroSaludServicio = registroSaludServicio;
        this.vacaServicio = vacaServicio;
        this.controlador = controlador;
        
        // Configuración básica del panel
        setLayout(new BorderLayout());
        setBackground(new Color(207, 184, 143));

        // Crear barra superior con título
        JLabel topLabel = crearBarraSuperior();
        add(topLabel, BorderLayout.NORTH);

        // Configurar tabla de registros
        configurarTablaRegistros();
        
        // Configurar controles inferiores
        configurarControlesInferiores(parent);
        
        // Cargar datos iniciales
        refrescarTabla();
    }

    /**
     * Crea la barra superior con el título
     */
    private JLabel crearBarraSuperior() {
        JLabel topLabel = new JLabel("Registros de Salud", JLabel.CENTER);
        topLabel.setFont(new Font("Serif", Font.BOLD, 20));
        topLabel.setOpaque(true);
        topLabel.setBackground(new Color(0x6C4325));
        topLabel.setForeground(Color.WHITE);
        topLabel.setPreferredSize(new Dimension(getWidth(), 40));
        return topLabel;
    }

    /**
     * Configura la tabla de registros de salud
     */
    private void configurarTablaRegistros() {
        // Columnas de la tabla
        String[] columnas = {"ID", "Vaca ID", "Fecha", "Estado Salud", "Descripción", "Tratamiento"};
        modeloTabla = new DefaultTableModel(null, columnas);
        
        // Personalizar tabla
        tabla = new JTable(modeloTabla) {
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component c = super.prepareRenderer(renderer, row, column);
                // Estilo para última fila (total)
                if (row == getRowCount() - 1) {
                    c.setBackground(new Color(0x3B170B));
                    c.setForeground(Color.WHITE);
                } 
                // Estilo para fila seleccionada
                else if (isRowSelected(row)) {
                    c.setBackground(new Color(93, 110, 82));
                    c.setForeground(Color.WHITE);
                } 
                // Estilo para filas normales
                else {
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
        tabla.setFont(new Font("Serif", Font.BOLD, 16));
        tabla.getTableHeader().setFont(new Font("Serif", Font.BOLD, 16));
        tabla.getTableHeader().setBackground(new Color(108, 67, 37));
        tabla.getTableHeader().setForeground(Color.WHITE);
        tabla.setGridColor(Color.BLACK);
        tabla.setRowHeight(30);
        tabla.setFillsViewportHeight(true);

        // Scroll pane para la tabla
        JScrollPane scrollPane = new JScrollPane(tabla);
        scrollPane.getViewport().setBackground(new Color(150, 151, 119));
        scrollPane.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        add(scrollPane, BorderLayout.CENTER);
    }

    /**
     * Configura los controles inferiores (filtros y botones)
     * @param parent Ventana padre para diálogos
     */
    private void configurarControlesInferiores(JFrame parent) {
        // Panel inferior
        JPanel bottomBar = new JPanel(new BorderLayout());
        bottomBar.setBackground(new Color(108, 67, 37));
        bottomBar.setPreferredSize(new Dimension(getWidth(), 80));

        // Panel de controles
        JPanel controlsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        controlsPanel.setOpaque(false);

        // Combo box para filtrar por vaca
        JComboBox<String> comboBoxVacas = crearComboVacas();
        
        // Combo box para filtrar por estado de salud
        JComboBox<String> comboBoxEstado = crearComboEstados();

        // Botones de acción
        JButton nuevoRegistroButton = new JButton("Nuevo Registro");
        JButton verDetallesButton = new JButton("Ver Detalles");
        JButton eliminarButton = new JButton("Eliminar");

        // Estilizar botones
        styleLargeButton(nuevoRegistroButton);
        styleLargeButton(verDetallesButton);
        styleLargeButton(eliminarButton);

        // Agregar componentes al panel
        controlsPanel.add(comboBoxVacas);
        controlsPanel.add(comboBoxEstado);
        controlsPanel.add(nuevoRegistroButton);
        controlsPanel.add(verDetallesButton);
        controlsPanel.add(eliminarButton);

        bottomBar.add(controlsPanel, BorderLayout.CENTER);
        add(bottomBar, BorderLayout.SOUTH);

        // Asignar acciones a los botones
        nuevoRegistroButton.addActionListener(e -> 
            controlador.crearNuevoRegistro(parent, this::refrescarTabla));
        verDetallesButton.addActionListener(e -> 
            verDetallesRegistro(parent));
        eliminarButton.addActionListener(e -> 
            eliminarRegistroSeleccionado(parent));
    }

    /**
     * Crea y configura el combo box de vacas
     */
    private JComboBox<String> crearComboVacas() {
        JComboBox<String> comboBoxVacas = new JComboBox<>();
        comboBoxVacas.setFont(new Font("Serif", Font.PLAIN, 14));
        comboBoxVacas.setPreferredSize(new Dimension(160, 35));
        cargarVacasEnComboBox(comboBoxVacas);
        
        // Listener para filtrar por vaca seleccionada
        comboBoxVacas.addActionListener(e -> {
            String seleccion = (String) comboBoxVacas.getSelectedItem();
            if (seleccion == null || seleccion.equals("Todas las vacas")) {
                refrescarTabla();
            } else {
                filtrarPorVaca(Integer.parseInt(seleccion));
            }
        });
        
        return comboBoxVacas;
    }

    /**
     * Crea y configura el combo box de estados de salud
     */
    private JComboBox<String> crearComboEstados() {
        JComboBox<String> comboBoxEstado = new JComboBox<>();
        comboBoxEstado.addItem("Todos los estados");
        for (Vaca.EstadoSalud estado : Vaca.EstadoSalud.values()) {
            comboBoxEstado.addItem(estado.name());
        }
        comboBoxEstado.setFont(new Font("Serif", Font.PLAIN, 14));
        comboBoxEstado.setPreferredSize(new Dimension(160, 35));
        
        // Listener para filtrar por estado seleccionado
        comboBoxEstado.addActionListener(e -> {
            String seleccion = (String) comboBoxEstado.getSelectedItem();
            if (seleccion == null || seleccion.equals("Todos los estados")) {
                refrescarTabla();
            } else {
                filtrarPorEstado(Vaca.EstadoSalud.valueOf(seleccion));
            }
        });
        
        return comboBoxEstado;
    }

    /**
     * Carga las vacas en el combo box de filtrado
     * @param comboBoxVacas Combo box a poblar
     */
    private void cargarVacasEnComboBox(JComboBox<String> comboBoxVacas) {
        comboBoxVacas.addItem("Todas las vacas"); // Opción por defecto
        vacaServicio.obtenerTodasLasVacas().forEach(vaca -> 
            comboBoxVacas.addItem(String.valueOf(vaca.getId())));
    }

    /**
     * Actualiza la tabla con todos los registros
     */
    void refrescarTabla() {
        modeloTabla.setRowCount(0);
        List<RegistroSalud> registros = registroSaludServicio.obtenerTodos();

        // Agregar cada registro a la tabla
        registros.forEach(registro -> modeloTabla.addRow(new Object[]{
            registro.getId(),
            registro.getVacaId(),
            registro.getFecha(),
            registro.getEstadoSalud(),
            registro.getDescripcion(),
            registro.getTratamiento()
        }));

        // Agregar fila de total
        Object[] filaTotal = new Object[6];
        filaTotal[5] = "Total: " + registros.size() + " registros";
        modeloTabla.addRow(filaTotal);
    }

    /**
     * Filtra los registros por ID de vaca
     * @param idVaca ID de la vaca a filtrar
     */
    private void filtrarPorVaca(int idVaca) {
        modeloTabla.setRowCount(0);
        
        registroSaludServicio.obtenerTodos().stream()
            .filter(registro -> registro.getVacaId() == idVaca)
            .forEach(registro -> modeloTabla.addRow(new Object[]{
                registro.getId(),
                registro.getVacaId(),
                registro.getFecha(),
                registro.getEstadoSalud(),
                registro.getDescripcion(),
                registro.getTratamiento()
            }));

        // Agregar fila de total
        long count = registroSaludServicio.obtenerTodos().stream()
                            .filter(r -> r.getVacaId() == idVaca).count();
        Object[] filaTotal = new Object[6];
        filaTotal[5] = "Total: " + count + " registros";
        modeloTabla.addRow(filaTotal);
    }

    /**
     * Filtra los registros por estado de salud
     * @param estado Estado de salud a filtrar
     */
    private void filtrarPorEstado(Vaca.EstadoSalud estado) {
        modeloTabla.setRowCount(0);
        
        registroSaludServicio.obtenerTodos().stream()
            .filter(registro -> registro.getEstadoSalud() == estado)
            .forEach(registro -> modeloTabla.addRow(new Object[]{
                registro.getId(),
                registro.getVacaId(),
                registro.getFecha(),
                registro.getEstadoSalud(),
                registro.getDescripcion(),
                registro.getTratamiento()
            }));

        // Agregar fila de total
        long count = registroSaludServicio.obtenerTodos().stream()
                            .filter(r -> r.getEstadoSalud() == estado).count();
        Object[] filaTotal = new Object[6];
        filaTotal[5] = "Total: " + count + " registros";
        modeloTabla.addRow(filaTotal);
    }

    /**
     * Muestra los detalles del registro seleccionado
     * @param parent Ventana padre para diálogos
     */
    private void verDetallesRegistro(JFrame parent) {
        int filaSeleccionada = tabla.getSelectedRow();
        // Validar selección
        if (filaSeleccionada == -1 || filaSeleccionada == modeloTabla.getRowCount() - 1) {
            JOptionPane.showMessageDialog(parent, "Selecciona un registro válido.");
            return;
        }

        int idRegistro = (int) modeloTabla.getValueAt(filaSeleccionada, 0);
        registroSaludServicio.obtenerPorId(idRegistro).ifPresentOrElse(
            registro -> controlador.verDetallesRegistro(parent, registro),
            () -> JOptionPane.showMessageDialog(parent, "No se encontró el registro seleccionado.")
        );
    }

    /**
     * Elimina el registro seleccionado
     * @param parent Ventana padre para diálogos
     */
    private void eliminarRegistroSeleccionado(JFrame parent) {
        int filaSeleccionada = tabla.getSelectedRow();
        // Validar selección
        if (filaSeleccionada == -1 || filaSeleccionada == modeloTabla.getRowCount() - 1) {
            JOptionPane.showMessageDialog(parent, "Selecciona un registro válido.");
            return;
        }

        int idRegistro = (int) modeloTabla.getValueAt(filaSeleccionada, 0);
        controlador.eliminarRegistro(parent, idRegistro, this::refrescarTabla);
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
}