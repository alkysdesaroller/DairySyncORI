/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


/**
 *
 * @author josea
 */
package reproduccion;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Interfaz gráfica para la gestión de reproducción de ganado
 */
public class ReproduccionGui extends JFrame {
    // Constantes para los estados
    private static final String ESTADO_INSEMINADA = "Inseminada";
    private static final String ESTADO_EN_PARTO = "En parto";
    private static final String ESTADO_PARIDA = "Parida";
    
    // Componentes de la interfaz
    private JTextField vacaIdField, toroIdField, fechaField, granjeroField, granjeroIdField, descripcionField;
    private JComboBox<String> razaVacaBox, estadoBox;
    private JLabel resultadoLabel;
    private JTable vacasTable;
    private DefaultTableModel tableModel;
    
    // Lista para almacenar vacas agregadas
    private List<Reproduccion> vacasRegistradas = new ArrayList<>();

    /**
     * Constructor de la interfaz gráfica
     */
    public ReproduccionGui() {
        super("Gestión de Reproducción de Ganado");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        
        // Usar layout manager para mejor organización
        setLayout(new BorderLayout(10, 10));
        
        // Panel para formulario de entrada
        JPanel formPanel = crearPanelFormulario();
        add(formPanel, BorderLayout.NORTH);
        
        // Panel para botones
        JPanel buttonPanel = crearPanelBotones();
        add(buttonPanel, BorderLayout.CENTER);
        
        // Panel para tabla de resultados
        JPanel tablePanel = crearPanelTabla();
        add(tablePanel, BorderLayout.SOUTH);
        
        // Panel para mostrar resultados/mensajes
        resultadoLabel = new JLabel(" ");
        resultadoLabel.setForeground(Color.BLUE);
        resultadoLabel.setBorder(new EmptyBorder(5, 10, 5, 10));
        add(resultadoLabel, BorderLayout.PAGE_END);
        
        pack(); // Ajustar tamaño automáticamente
        setLocationRelativeTo(null); // Centrar en pantalla
        setVisible(true);
    }
    
    /**
     * Crea el panel con el formulario de entrada de datos
     */
    private JPanel crearPanelFormulario() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Datos de Reproducción"));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        
        // Primera columna (etiquetas)
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("ID Vaca:"), gbc);
        
        gbc.gridy = 1;
        panel.add(new JLabel("ID Toro:"), gbc);
        
        gbc.gridy = 2;
        panel.add(new JLabel("Raza de la Vaca:"), gbc);
        
        gbc.gridy = 3;
        panel.add(new JLabel("Estado:"), gbc);
        
        gbc.gridy = 4;
        panel.add(new JLabel("Fecha de Inseminación (YYYY-MM-DD):"), gbc);
        
        gbc.gridy = 5;
        panel.add(new JLabel("Nombre del Granjero:"), gbc);
        
        gbc.gridy = 6;
        panel.add(new JLabel("ID del Granjero:"), gbc);
        
        gbc.gridy = 7;
        panel.add(new JLabel("Descripción:"), gbc);
        
        // Segunda columna (campos)
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        vacaIdField = new JTextField(10);
        panel.add(vacaIdField, gbc);
        
        gbc.gridy = 1;
        toroIdField = new JTextField(10);
        panel.add(toroIdField, gbc);
        
        gbc.gridy = 2;
        String[] razas = {"Holstein", "Jersey", "Brahman", "Gyr", "Angus", "Hereford", "Limousin", "Charolais"};
        razaVacaBox = new JComboBox<>(razas);
        panel.add(razaVacaBox, gbc);
        
        gbc.gridy = 3;
        String[] estados = {ESTADO_INSEMINADA, ESTADO_EN_PARTO, ESTADO_PARIDA};
        estadoBox = new JComboBox<>(estados);
        panel.add(estadoBox, gbc);
        
        gbc.gridy = 4;
        fechaField = new JTextField(10);
        fechaField.setToolTipText("Formato: YYYY-MM-DD");
        panel.add(fechaField, gbc);
        
        gbc.gridy = 5;
        granjeroField = new JTextField(15);
        panel.add(granjeroField, gbc);
        
        gbc.gridy = 6;
        granjeroIdField = new JTextField(10);
        panel.add(granjeroIdField, gbc);
        
        gbc.gridy = 7;
        descripcionField = new JTextField(20);
        panel.add(descripcionField, gbc);
        
        return panel;
    }
    
    /**
     * Crea el panel con los botones de acción
     */
    private JPanel crearPanelBotones() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        
        JButton agregarBtn = new JButton("Agregar Registro");
        agregarBtn.setIcon(UIManager.getIcon("FileView.fileIcon"));
        agregarBtn.addActionListener(e -> agregarRegistro());
        
        JButton limpiarBtn = new JButton("Limpiar Formulario");
        limpiarBtn.addActionListener(e -> limpiarFormulario());
        
        JButton eliminarBtn = new JButton("Eliminar Seleccionado");
        eliminarBtn.addActionListener(e -> eliminarRegistroSeleccionado());
        
        JButton actualizarBtn = new JButton("Verificar Estados");
        actualizarBtn.addActionListener(e -> actualizarEstados());
        
        panel.add(agregarBtn);
        panel.add(limpiarBtn);
        panel.add(eliminarBtn);
        panel.add(actualizarBtn);
        
        return panel;
    }
    
    /**
     * Crea el panel con la tabla de registros
     */
    private JPanel crearPanelTabla() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Registros de Reproducción"));
        
        // Crear modelo de tabla con columnas
        String[] columnas = {"ID Vaca", "Raza", "Estado", "Fecha Inseminación", "Días", "ID Toro", "Granjero", "ID Granjero"};
        tableModel = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Hacer la tabla no editable
            }
        };
        
        vacasTable = new JTable(tableModel);
        vacasTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        vacasTable.setAutoCreateRowSorter(true);  // Permitir ordenar por columnas
        
        JScrollPane scrollPane = new JScrollPane(vacasTable);
        scrollPane.setPreferredSize(new Dimension(750, 200));
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    /**

* Limpia todos los campos del formulario
     */
    private void limpiarFormulario() {
        vacaIdField.setText("");
        toroIdField.setText("");
        fechaField.setText("");
        granjeroField.setText("");
        granjeroIdField.setText("");
        descripcionField.setText("");
        razaVacaBox.setSelectedIndex(0);
        estadoBox.setSelectedIndex(0);
        resultadoLabel.setText(" ");
        vacaIdField.requestFocus();
    }
    
    /**
     * Agrega un nuevo registro de reproducción
     */
    private void agregarRegistro() {
        try {
            // Validar y obtener valores
            if (vacaIdField.getText().trim().isEmpty() || toroIdField.getText().trim().isEmpty() || 
                fechaField.getText().trim().isEmpty()) {
                mostrarMensaje("Error: Los campos ID Vaca, ID Toro y Fecha son obligatorios", true);
                return;
            }
            
            int vacaId = Integer.parseInt(vacaIdField.getText().trim());
            int toroId = Integer.parseInt(toroIdField.getText().trim());
            String razaVaca = (String) razaVacaBox.getSelectedItem();
            String estado = (String) estadoBox.getSelectedItem();
            LocalDate fechaInseminacion = LocalDate.parse(fechaField.getText().trim());
            String descripcion = descripcionField.getText().trim();
            
            String nombreGranjero = granjeroField.getText().trim();
            int idGranjero = 0;
            if (!granjeroIdField.getText().trim().isEmpty()) {
                idGranjero = Integer.parseInt(granjeroIdField.getText().trim());
            }
            
            // Verificar si la vaca ya existe
            for (Reproduccion r : vacasRegistradas) {
                if (r.getVacaId() == vacaId) {
                    mostrarMensaje("Error: La vaca con ID " + vacaId + " ya existe en los registros", true);
                    return;
                }
            }
            
            // Crear objeto Reproducción
            Reproduccion registro = new Reproduccion(vacaId, toroId, razaVaca, descripcion, 
                                                    estado, fechaInseminacion, nombreGranjero, idGranjero);
            
            // Agregar a la lista y actualizar tabla
            vacasRegistradas.add(registro);
            actualizarTabla();
            limpiarFormulario();
            
            mostrarMensaje("Registro agregado con éxito", false);
            
        } catch (NumberFormatException ex) {
            mostrarMensaje("Error: Los campos ID deben ser números enteros", true);
        } catch (DateTimeParseException ex) {
            mostrarMensaje("Error: Formato de fecha incorrecto. Use YYYY-MM-DD (ej: 2023-05-15)", true);
        } catch (Exception ex) {
            mostrarMensaje("Error desconocido: " + ex.getMessage(), true);
        }
    }
    
    /**
     * Elimina el registro seleccionado en la tabla
     */
    private void eliminarRegistroSeleccionado() {
        int selectedRow = vacasTable.getSelectedRow();
        if (selectedRow >= 0) {
            int modelRow = vacasTable.convertRowIndexToModel(selectedRow);
            int vacaId = (int) tableModel.getValueAt(modelRow, 0);
            
            // Encontrar y eliminar el registro
            Reproduccion aEliminar = null;
            for (Reproduccion r : vacasRegistradas) {
                if (r.getVacaId() == vacaId) {
                    aEliminar = r;
                    break;
                }
            }
            
            if (aEliminar != null) {
                vacasRegistradas.remove(aEliminar);
                actualizarTabla();
                mostrarMensaje("Registro eliminado correctamente", false);
            }
        } else {
            mostrarMensaje("Por favor, seleccione un registro para eliminar", true);
        }
    }
    
    /**
     * Actualiza los estados de las vacas según los días transcurridos
     */
    private void actualizarEstados() {
        boolean cambios = false;
        
        for (Reproduccion r : vacasRegistradas) {
            long dias = diasDesdeInseminacion(r.getFechaInseminacion());
            
            // Actualizar a "En parto" si han pasado entre 270-290 días y está inseminada
            if (ESTADO_INSEMINADA.equals(r.getEstado()) && dias >= 270 && dias <= 290) {
                r.setEstado(ESTADO_EN_PARTO);
                cambios = true;
            }
            // Actualizar a "Parida" si han pasado más de 290 días y está en parto
            else if (ESTADO_EN_PARTO.equals(r.getEstado()) && dias > 290) {
                r.setEstado(ESTADO_PARIDA);
                cambios = true;
            }
        }
        
        actualizarTabla();
        
        if (cambios) {
            mostrarMensaje("Estados actualizados según los días transcurridos", false);
        } else {
            mostrarMensaje("No se encontraron cambios de estado necesarios", false);
        }
    }
    
    /**
     * Calcula los días transcurridos desde la fecha de inseminación hasta hoy
     */
    private long diasDesdeInseminacion(LocalDate fechaInseminacion) {
        return java.time.temporal.ChronoUnit.DAYS.between(fechaInseminacion, LocalDate.now());
    }
    
    /**
     * Actualiza la tabla con los datos actuales
     */
    private void actualizarTabla() {
        // Limpiar tabla
        tableModel.setRowCount(0);
        
        // Rellenar con datos actualizados
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        
        for (Reproduccion r : vacasRegistradas) {
            long dias = diasDesdeInseminacion(r.getFechaInseminacion());
            
            Object[] fila = {
                r.getVacaId(),
                r.getRazaVaca(),
                r.getEstado(),
                r.getFechaInseminacion().format(formatter),
                dias,
                r.getToroId(),
                r.getNombreGranjero(),
                r.getIdGranjero()
            };
            
            tableModel.addRow(fila);
        }
    }
    
    /**
     * Muestra un mensaje en el label de resultado
     */
    private void mostrarMensaje(String mensaje, boolean esError) {
        resultadoLabel.setText(mensaje);
        resultadoLabel.setForeground(esError ? Color.RED : new Color(0, 100, 0));
    }
    
    /**
     * Método principal para iniciar la aplicación
     */
    public static void main(String[] args) {
        // Usar look and feel del sistema operativo
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // Iniciar la interfaz en el hilo de eventos de Swing
        SwingUtilities.invokeLater(() -> new ReproduccionGui());
    }
}


