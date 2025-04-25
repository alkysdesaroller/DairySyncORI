package controller;

import ModuloDariel.models.RegistroSalud;
import ModuloDariel.models.Vaca;
import ModuloDariel.usecase.RegistroSaludServicio;
import ModuloDariel.usecase.VacaServicio;

import javax.swing.*;
import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import java.util.function.Consumer;

/**
 * Controlador para la gestión de registros de salud de las vacas
 */
public class RegistroSaludController {
    // Servicios para acceder a los datos
    private final RegistroSaludServicio registroSaludServicio;
    private final VacaServicio vacaServicio;

    /**
     * Constructor del controlador
     * @param registroSaludServicio Servicio de registros de salud
     * @param vacaServicio Servicio de gestión de vacas
     */
    public RegistroSaludController(RegistroSaludServicio registroSaludServicio, VacaServicio vacaServicio) {
        this.registroSaludServicio = registroSaludServicio;
        this.vacaServicio = vacaServicio;
    }

    /**
     * Muestra diálogo para crear nuevo registro de salud
     * @param parent Componente padre para el diálogo
     * @param refrescarCallback Callback para actualizar la vista
     */
    public void crearNuevoRegistro(Component parent, Runnable refrescarCallback) {
        createStyledDialog(parent, "Nuevo Registro de Salud", 500, 400, dialog -> {
            JPanel centerPanel = buildFormPanel(6);
            
            // Campos del formulario
            JTextField txtVacaId = createStyledTextField(15);
            JTextField txtFecha = createStyledTextField(15);
            JComboBox<Vaca.EstadoSalud> comboEstadoSalud = createStyledComboBox(Vaca.EstadoSalud.values());
            JTextField txtDescripcion = createStyledTextField(20);
            JTextField txtTratamiento = createStyledTextField(20);

            // Configurar etiquetas y campos
            addFormField(centerPanel, "Vaca ID:", txtVacaId);
            addFormField(centerPanel, "Fecha (yyyy-MM-dd):", txtFecha);
            addFormField(centerPanel, "Estado de Salud:", comboEstadoSalud);
            addFormField(centerPanel, "Descripción:", txtDescripcion);
            addFormField(centerPanel, "Tratamiento:", txtTratamiento);

            // Botón de confirmación
            JButton btnGuardar = createStyledButton("Guardar");
            btnGuardar.addActionListener(e -> {
                try {
                    // Validar y formatear fecha
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    sdf.setLenient(false);
                    Date fecha = sdf.parse(txtFecha.getText());

                    // Validar ID de vaca
                    int vacaId = Integer.parseInt(txtVacaId.getText());
                    Optional<Vaca> vacaOptional = vacaServicio.obtenerPorId(vacaId);
                    if (vacaOptional.isEmpty()) {
                        showError(dialog, "No existe una vaca con ese ID.");
                        return;
                    }

                    // Obtener datos del formulario
                    Vaca.EstadoSalud estadoSalud = (Vaca.EstadoSalud) comboEstadoSalud.getSelectedItem();
                    String descripcion = txtDescripcion.getText();
                    String tratamiento = txtTratamiento.getText();

                    // Crear nuevo registro
                    registroSaludServicio.crearRegistroSalud(vacaId, estadoSalud, descripcion, tratamiento);
                    showSuccess(dialog, "Registro de salud creado con éxito.");
                    
                    dialog.dispose();
                    refrescarCallback.run();
                } catch (ParseException ex) {
                    showError(dialog, "La fecha debe estar en el formato yyyy-MM-dd.");
                } catch (NumberFormatException ex) {
                    showError(dialog, "El ID de vaca debe ser un número entero.");
                }
            });

            addBottomButtons(dialog, btnGuardar);
            dialog.add(centerPanel, BorderLayout.CENTER);
        });
    }

    /**
     * Muestra diálogo con detalles de un registro
     * @param parent Componente padre para el diálogo
     * @param registro Registro a mostrar
     */
    public void verDetallesRegistro(Component parent, RegistroSalud registro) {
        createStyledDialog(parent, "Detalles del Registro", 500, 400, dialog -> {
            JPanel centerPanel = buildFormPanel(6);
            
            // Mostrar campos no editables
            addField(centerPanel, "ID:", String.valueOf(registro.getId()));
            addField(centerPanel, "Fecha:", registro.getFecha().toString());
            addField(centerPanel, "Estado de Salud:", registro.getEstadoSalud().name());
            addField(centerPanel, "Descripción:", registro.getDescripcion());
            addField(centerPanel, "Tratamiento:", registro.getTratamiento());

            // Botón de cierre
            JButton btnCerrar = createStyledButton("Cerrar");
            btnCerrar.addActionListener(e -> dialog.dispose());

            addBottomButtons(dialog, btnCerrar);
            dialog.add(centerPanel, BorderLayout.CENTER);
        });
    }

    /**
     * Elimina un registro de salud con confirmación
     * @param parent Componente padre para diálogos
     * @param registroId ID del registro a eliminar
     * @param refrescarCallback Callback para actualizar la vista
     */
    public void eliminarRegistro(Component parent, int registroId, Runnable refrescarCallback) {
        int confirm = JOptionPane.showConfirmDialog(
            parent, 
            "¿Estás seguro de que deseas eliminar este registro?",
            "Confirmar eliminación", 
            JOptionPane.YES_NO_OPTION
        );
        
        if (confirm == JOptionPane.YES_OPTION) {
            registroSaludServicio.eliminarRegistroSalud(registroId);
            showSuccess(parent, "Registro de salud eliminado.");
            refrescarCallback.run();
        }
    }

    /**
     * Crea un diálogo estilizado
     * @param parent Componente padre
     * @param title Título del diálogo
     * @param width Ancho del diálogo
     * @param height Alto del diálogo
     * @param setupContent Consumer para configurar el contenido
     */
    private void createStyledDialog(Component parent, String title, int width, int height, Consumer<JDialog> setupContent) {
        Window windowParent = SwingUtilities.getWindowAncestor(parent);
        JDialog dialog = new JDialog(windowParent, title, Dialog.ModalityType.APPLICATION_MODAL);
        dialog.setSize(width, height);
        dialog.setResizable(false);
        dialog.setLayout(new BorderLayout());
        dialog.setLocationRelativeTo(parent);

        // Barra superior
        JPanel topBar = createStyledTopBar(title);
        dialog.add(topBar, BorderLayout.NORTH);

        setupContent.accept(dialog);
        dialog.setVisible(true);
    }

    /**
     * Crea la barra superior del diálogo
     * @param title Título a mostrar
     * @return Panel configurado
     */
    private JPanel createStyledTopBar(String title) {
        JPanel topBar = new JPanel();
        topBar.setBackground(new Color(108, 67, 37));
        topBar.setPreferredSize(new Dimension(500, 45));
        JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);
        topBar.add(titleLabel);
        return topBar;
    }

    /**
     * Construye un panel de formulario
     * @param rows Número de filas
     * @return Panel configurado
     */
    private JPanel buildFormPanel(int rows) {
        JPanel panel = new JPanel(new GridLayout(rows, 2, 10, 10));
        panel.setBackground(new Color(207, 184, 143));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 25, 15, 25));
        return panel;
    }

    /**
     * Añade botones inferiores al diálogo
     * @param dialog Diálogo al que añadir
     * @param confirmButton Botón principal
     */
    private void addBottomButtons(JDialog dialog, JButton confirmButton) {
        JButton cancelButton = createStyledButton("Cancelar");
        cancelButton.addActionListener(e -> dialog.dispose());

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        bottomPanel.setBackground(new Color(108, 67, 37));
        bottomPanel.add(confirmButton);
        bottomPanel.add(cancelButton);

        dialog.add(bottomPanel, BorderLayout.SOUTH);
    }

    /**
     * Crea un campo de texto estilizado
     * @param columns Ancho en columnasz
     * @return Campo de texto configurado
     */
    private JTextField createStyledTextField(int columns) {
        JTextField textField = new JTextField(columns);
        textField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        textField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.BLACK),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        return textField;
    }

    /**
     * Crea un combo box estilizado
     * @param items Elementos del combo box
     * @return Combo box configurado
     */
    private <T> JComboBox<T> createStyledComboBox(T[] items) {
        JComboBox<T> comboBox = new JComboBox<>(items);
        comboBox.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        comboBox.setBackground(Color.WHITE);
        comboBox.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.BLACK),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        return comboBox;
    }

    /**
     * Crea una etiqueta estilizada
     * @param text Texto a mostrar
     * @return Etiqueta configurada
     */
    private JLabel createStyledLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        label.setForeground(Color.BLACK);
        return label;
    }

    /**
     * Crea una etiqueta de valor estilizada
     * @param text Texto a mostrar
     * @return Etiqueta configurada
     */
    private JLabel createStyledValueLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        label.setForeground(Color.BLACK);
        label.setOpaque(true);
        label.setBackground(new Color(239, 231, 218));
        label.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.BLACK),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        return label;
    }

    /**
     * Añade un campo al formulario
     * @param panel Panel al que añadir
     * @param labelText Texto de la etiqueta
     * @param component Componente del campo
     */
    private void addFormField(JPanel panel, String labelText, JComponent component) {
        panel.add(createStyledLabel(labelText));
        panel.add(component);
    }

    /**
     * Añade un campo de solo lectura al formulario
     * @param panel Panel al que añadir
     * @param label Texto de la etiqueta
     * @param value Valor a mostrar
     */
    private void addField(JPanel panel, String label, String value) {
        panel.add(createStyledLabel(label));
        panel.add(createStyledValueLabel(value));
    }

    /**
     * Crea un botón estilizado
     * @param text Texto del botón
     * @return Botón configurado
     */
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 18));
        button.setForeground(Color.BLACK);
        button.setBackground(new Color(108, 67, 37));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createEtchedBorder(),
            BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));
        return button;
    }

    /**
     * Muestra mensaje de error
     * @param parent Componente padre
     * @param message Mensaje a mostrar
     */
    private void showError(Component parent, String message) {
        JOptionPane.showMessageDialog(parent, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Muestra mensaje de éxito
     * @param parent Componente padre
     * @param message Mensaje a mostrar
     */
    private void showSuccess(Component parent, String message) {
        JOptionPane.showMessageDialog(parent, message, "Éxito", JOptionPane.INFORMATION_MESSAGE);
    }
}