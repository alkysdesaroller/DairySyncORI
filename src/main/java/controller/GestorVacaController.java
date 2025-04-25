package controller;

import ModuloDariel.models.Vaca;
import ModuloDariel.usecase.RegistroSaludServicio;
import ModuloDariel.usecase.VacaServicio;
import javax.swing.*;
import java.awt.*;
import java.util.Optional;
import java.util.function.Consumer;
import javax.swing.border.EtchedBorder;

/**
 * Controlador para la gestión de vacas
 * Maneja las operaciones CRUD y la interacción con la interfaz gráfica
 */
public class GestorVacaController {
    // Servicios para acceder a los datos
    private final VacaServicio vacaServicio;
    private final RegistroSaludServicio registroSaludServicio;

    /**
     * Constructor del controlador
     * @param vacaServicio Servicio de gestión de vacas
     * @param registroSaludServicio Servicio de registros de salud
     */
    public GestorVacaController(VacaServicio vacaServicio, RegistroSaludServicio registroSaludServicio) {
        this.vacaServicio = vacaServicio;
        this.registroSaludServicio = registroSaludServicio;
    }

    /**
     * Muestra diálogo para agregar nueva vaca
     * @param parent Ventana padre para el diálogo
     */
public void agregarVaca(JFrame parent) {
    createStyledDialog(parent, "Agregar Nueva Vaca", 500, 400, dialog -> {
        JPanel centerPanel = buildFormPanel(6);

        // Campos del formulario
        JTextField razaField = new JTextField(25);
        JTextField edadField = new JTextField(25);
        JTextField genealogiaField = new JTextField(25);
        JTextField granjaIdField = new JTextField(25);
        JTextField madreIdField = new JTextField(25);
        JTextField padreIdField = new JTextField(25);

        // Configurar etiquetas y campos
        JLabel razaLabel = new JLabel("Raza:");
        razaLabel.setForeground(Color.BLACK); // Texto en negro
        centerPanel.add(razaLabel);
        centerPanel.add(razaField);
        razaField.setForeground(Color.BLACK); // Texto en negro

        JLabel edadLabel = new JLabel("Edad:");
        edadLabel.setForeground(Color.BLACK); // Texto en negro
        centerPanel.add(edadLabel);
        centerPanel.add(edadField);
        edadField.setForeground(Color.BLACK); // Texto en negro

        JLabel genealogiaLabel = new JLabel("Genealogía:");
        genealogiaLabel.setForeground(Color.BLACK); // Texto en negro
        centerPanel.add(genealogiaLabel);
        centerPanel.add(genealogiaField);
        genealogiaField.setForeground(Color.BLACK); // Texto en negro

        JLabel granjaIdLabel = new JLabel("Granja ID:");
        granjaIdLabel.setForeground(Color.BLACK); // Texto en negro
        centerPanel.add(granjaIdLabel);
        centerPanel.add(granjaIdField);
        granjaIdField.setForeground(Color.BLACK); // Texto en negro

        JLabel madreIdLabel = new JLabel("Madre ID (opcional):");
        madreIdLabel.setForeground(Color.BLACK); // Texto en negro
        centerPanel.add(madreIdLabel);
        centerPanel.add(madreIdField);
        madreIdField.setForeground(Color.BLACK); // Texto en negro

        JLabel padreIdLabel = new JLabel("Padre ID (opcional):");
        padreIdLabel.setForeground(Color.BLACK); // Texto en negro
        centerPanel.add(padreIdLabel);
        centerPanel.add(padreIdField);
        padreIdField.setForeground(Color.BLACK); // Texto en negro

        // Botón de confirmación
        JButton confirmButton = new JButton("Guardar");
        styleLargeButton(confirmButton);
        confirmButton.setForeground(Color.BLACK); // Texto en negro
        confirmButton.addActionListener(e -> {
            try {
                // Validar y obtener datos
                String raza = razaField.getText();
                int edad = Integer.parseInt(edadField.getText());
                String genealogia = genealogiaField.getText();
                int granjaId = Integer.parseInt(granjaIdField.getText());
                Integer madreId = madreIdField.getText().isEmpty() ? null : Integer.parseInt(madreIdField.getText());
                Integer padreId = padreIdField.getText().isEmpty() ? null : Integer.parseInt(padreIdField.getText());

                // Registrar nueva vaca
                vacaServicio.registrarVaca(raza, edad, genealogia, granjaId, madreId, padreId);
                JOptionPane.showMessageDialog(dialog, "Vaca registrada exitosamente.");

                // Actualizar vista si es necesario
                if (parent instanceof views.GestorVacas) {
                    ((views.GestorVacas) parent).refrescarTabla();
                }
                dialog.dispose();
            } catch (NumberFormatException ex) {
                showError(dialog, "Por favor, ingrese valores válidos.");
            }
        });

        addBottomButtons(dialog, confirmButton);
        dialog.add(centerPanel, BorderLayout.CENTER);
    });
}

public void editarVaca(JFrame parent) {
    createStyledDialog(parent, "Editar Vaca", 500, 400, dialog -> {
        JPanel centerPanel = buildFormPanel(7);

        // Campos del formulario
        JTextField idField = new JTextField(25);
        JTextField razaField = new JTextField(25);
        JTextField edadField = new JTextField(25);
        JTextField genealogiaField = new JTextField(25);
        JTextField granjaIdField = new JTextField(25);
        JTextField madreIdField = new JTextField(25);
        JTextField padreIdField = new JTextField(25);

        // Configurar etiquetas y campos
        JLabel idLabel = new JLabel("ID de la vaca:");
        idLabel.setForeground(Color.BLACK); // Texto en negro
        centerPanel.add(idLabel);
        centerPanel.add(idField);
        idField.setForeground(Color.BLACK); // Texto en negro

        JLabel razaLabel = new JLabel("Raza:");
        razaLabel.setForeground(Color.BLACK); // Texto en negro
        centerPanel.add(razaLabel);
        centerPanel.add(razaField);
        razaField.setForeground(Color.BLACK); // Texto en negro

        JLabel edadLabel = new JLabel("Edad:");
        edadLabel.setForeground(Color.BLACK); // Texto en negro
        centerPanel.add(edadLabel);
        centerPanel.add(edadField);
        edadField.setForeground(Color.BLACK); // Texto en negro

        JLabel genealogiaLabel = new JLabel("Genealogía:");
        genealogiaLabel.setForeground(Color.BLACK); // Texto en negro
        centerPanel.add(genealogiaLabel);
        centerPanel.add(genealogiaField);
        genealogiaField.setForeground(Color.BLACK); // Texto en negro

        JLabel granjaIdLabel = new JLabel("Granja ID:");
        granjaIdLabel.setForeground(Color.BLACK); // Texto en negro
        centerPanel.add(granjaIdLabel);
        centerPanel.add(granjaIdField);
        granjaIdField.setForeground(Color.BLACK); // Texto en negro

        JLabel madreIdLabel = new JLabel("Madre ID (opcional):");
        madreIdLabel.setForeground(Color.BLACK); // Texto en negro
        centerPanel.add(madreIdLabel);
        centerPanel.add(madreIdField);
        madreIdField.setForeground(Color.BLACK); // Texto en negro

        JLabel padreIdLabel = new JLabel("Padre ID (opcional):");
        padreIdLabel.setForeground(Color.BLACK); // Texto en negro
        centerPanel.add(padreIdLabel);
        centerPanel.add(padreIdField);
        padreIdField.setForeground(Color.BLACK); // Texto en negro

        // Botón de confirmación
        JButton confirmButton = new JButton("Guardar Cambios");
        styleLargeButton(confirmButton);
        confirmButton.setForeground(Color.BLACK); // Texto en negro
        confirmButton.addActionListener(e -> {
            try {
                // Validar ID
                String idText = idField.getText().trim();
                if (idText.isEmpty()) {
                    showError(dialog, "Por favor, ingrese un ID válido.");
                    return;
                }

                // Obtener datos del formulario
                int id = Integer.parseInt(idText);
                String raza = razaField.getText();
                int edad = Integer.parseInt(edadField.getText());
                String genealogia = genealogiaField.getText();
                int granjaId = Integer.parseInt(granjaIdField.getText());
                Integer madreId = madreIdField.getText().isEmpty() ? null : Integer.parseInt(madreIdField.getText());
                Integer padreId = padreIdField.getText().isEmpty() ? null : Integer.parseInt(padreIdField.getText());

                // Buscar y actualizar vaca
                Optional<Vaca> vacaOptional = vacaServicio.obtenerPorId(id);
                if (vacaOptional.isPresent()) {
                    Vaca vaca = vacaOptional.get();
                    // Actualizar datos básicos
                    vaca.setRaza(raza);
                    vaca.setEdad(edad);
                    vaca.setGenealogia(genealogia);
                    vaca.setGranjaId(granjaId);
                    vaca.setMadreId(madreId);
                    vaca.setPadreId(padreId);

                    // Mantener estados actuales
                    vacaServicio.actualizarEstadoReproductivo(id, vaca.getEstadoReproductivo());
                    vacaServicio.actualizarEstadoSalud(id, vaca.getEstadoSalud(), "Actualización de datos", "Ninguno");

                    JOptionPane.showMessageDialog(dialog, "Vaca actualizada exitosamente.");
                } else {
                    showError(dialog, "No se encontró ninguna vaca con el ID proporcionado.");
                }
                dialog.dispose();
            } catch (NumberFormatException ex) {
                showError(dialog, "Por favor, ingrese valores válidos.");
            }
        });

        addBottomButtons(dialog, confirmButton);
        dialog.add(centerPanel, BorderLayout.CENTER);
    });
}

public void actualizarEstado(JFrame parent) {
    createStyledDialog(parent, "Actualizar Estado", 500, 400, dialog -> {
        JPanel centerPanel = buildFormPanel(5);

        // Campos del formulario
        JTextField idField = new JTextField(25);
        JComboBox<String> estadoSaludBox = new JComboBox<>(new String[]{"SANA", "ENFERMA", "EN_OBSERVACION"});
        JComboBox<String> estadoReproductivoBox = new JComboBox<>(new String[]{"NO_CONOCIDO", "INSEMINADA", "PREÑADA", "PARIDA"});
        JTextField descripcionField = new JTextField(25);
        JTextField tratamientoField = new JTextField(25);

        // Configurar etiquetas y campos
        JLabel idLabel = new JLabel("ID de la vaca:");
        idLabel.setForeground(Color.BLACK); // Texto en negro
        centerPanel.add(idLabel);
        centerPanel.add(idField);
        idField.setForeground(Color.BLACK); // Texto en negro

        JLabel estadoSaludLabel = new JLabel("Estado de Salud:");
        estadoSaludLabel.setForeground(Color.BLACK); // Texto en negro
        centerPanel.add(estadoSaludLabel);
        centerPanel.add(estadoSaludBox);

        JLabel estadoReproductivoLabel = new JLabel("Estado Reproductivo:");
        estadoReproductivoLabel.setForeground(Color.BLACK); // Texto en negro
        centerPanel.add(estadoReproductivoLabel);
        centerPanel.add(estadoReproductivoBox);

        JLabel descripcionLabel = new JLabel("Descripción:");
        descripcionLabel.setForeground(Color.BLACK); // Texto en negro
        centerPanel.add(descripcionLabel);
        centerPanel.add(descripcionField);
        descripcionField.setForeground(Color.BLACK); // Texto en negro

        JLabel tratamientoLabel = new JLabel("Tratamiento:");
        tratamientoLabel.setForeground(Color.BLACK); // Texto en negro
        centerPanel.add(tratamientoLabel);
        centerPanel.add(tratamientoField);
        tratamientoField.setForeground(Color.BLACK); // Texto en negro

        // Botón de confirmación
        JButton confirmButton = new JButton("Actualizar Estado");
        styleLargeButton(confirmButton);
        confirmButton.setForeground(Color.BLACK); // Texto en negro
        confirmButton.addActionListener(e -> {
            try {
                int id = Integer.parseInt(idField.getText());
                String estadoSaludStr = (String) estadoSaludBox.getSelectedItem();
                String estadoReproductivoStr = (String) estadoReproductivoBox.getSelectedItem();
                String descripcion = descripcionField.getText();
                String tratamiento = tratamientoField.getText();

                // Convertir estado de salud a Enum
                Vaca.EstadoSalud estadoSalud = Vaca.EstadoSalud.fromString(estadoSaludStr);
                
                // Usar directamente el string normalizado para el estado reproductivo
                String estadoReproductivo = Vaca.convertirEstadoReproductivo(estadoReproductivoStr);

                vacaServicio.actualizarEstadoSalud(id, estadoSalud, descripcion, tratamiento);
                vacaServicio.actualizarEstadoReproductivo(id, estadoReproductivo);

                JOptionPane.showMessageDialog(dialog, "Estado actualizado exitosamente.");
                dialog.dispose();
            } catch (NumberFormatException ex) {
                showError(dialog, "Por favor, ingrese un ID válido.");
            }
        });

        addBottomButtons(dialog, confirmButton);
        dialog.add(centerPanel, BorderLayout.CENTER);
    });
}

public void eliminarVaca(JFrame parent) {
    createStyledDialog(parent, "Eliminar Vaca", 400, 250, dialog -> {
        JPanel centerPanel = buildFormPanel(2);

        // Campos del formulario
        JTextField idField = new JTextField(25);
        JTextField motivoField = new JTextField(25);

        // Configurar etiquetas y campos
        JLabel idLabel = new JLabel("ID de la vaca:");
        idLabel.setForeground(Color.BLACK); // Texto en negro
        centerPanel.add(idLabel);
        centerPanel.add(idField);
        idField.setForeground(Color.BLACK); // Texto en negro

        JLabel motivoLabel = new JLabel("Motivo (opcional):");
        motivoLabel.setForeground(Color.BLACK); // Texto en negro
        centerPanel.add(motivoLabel);
        centerPanel.add(motivoField);
        motivoField.setForeground(Color.BLACK); // Texto en negro

        // Botón de confirmación
        JButton confirmButton = new JButton("Eliminar");
        styleLargeButton(confirmButton);
        confirmButton.setForeground(Color.BLACK); // Texto en negro
        confirmButton.addActionListener(e -> {
            try {
                // Eliminar vaca por ID
                int id = Integer.parseInt(idField.getText());
                vacaServicio.eliminar(id);
                JOptionPane.showMessageDialog(dialog, "Vaca eliminada exitosamente.");
                dialog.dispose();
            } catch (NumberFormatException ex) {
                showError(dialog, "Por favor, ingrese un ID válido.");
            }
        });

        addBottomButtons(dialog, confirmButton);
        dialog.add(centerPanel, BorderLayout.CENTER);
    });
}


    /**
     * Crea un diálogo estilizado
     * @param parent Ventana padre
     * @param title Título del diálogo
     * @param width Ancho del diálogo
     * @param height Alto del diálogo
     * @param setupContent Consumer para configurar el contenido
     */
    private void createStyledDialog(JFrame parent, String title, int width, int height, Consumer<JDialog> setupContent) {
        JDialog dialog = new JDialog(parent, title, true);
        dialog.setSize(width, height);
        dialog.setLayout(new BorderLayout());
        dialog.setLocationRelativeTo(parent);

        // Barra superior del diálogo
        JPanel topBar = new JPanel();
        topBar.setBackground(new Color(108, 67, 37)); // Color marrón oscuro
        topBar.setPreferredSize(new Dimension(dialog.getWidth(), 45));
        JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titleLabel.setForeground(Color.BLACK);
        topBar.add(titleLabel);

        dialog.add(topBar, BorderLayout.NORTH);

        // Configurar los componentes del contenido
        setupContent.accept(dialog);

        // Estilizar todos los textos dentro del diálogo con el mismo color que la barra superior
        Color labelColor = new Color(0x000000); // Color negro para los textos
        Component[] components = dialog.getComponents();
        for (Component component : components) {
            if (component instanceof JPanel) {
                JPanel panel = (JPanel) component;
                for (Component subComponent : panel.getComponents()) {
                    if (subComponent instanceof JLabel) {
                        JLabel label = (JLabel) subComponent;
                        label.setFont(new Font("Segoe UI", Font.BOLD, 14));  // Negritas
                        label.setForeground(Color.BLACK);  // Aplicar color al texto de las etiquetas
                    }
                    if (subComponent instanceof JTextField) {
                        JTextField textField = (JTextField) subComponent;
                        textField.setForeground(Color.BLACK);;  // Aplicar color al texto de los campos de texto
                    }
                    if (subComponent instanceof JButton) {
                        JButton button = (JButton) subComponent;
                        button.setForeground(Color.WHITE);  // Aplicar color al texto de los botones
                    }
                }
            }
        }

        dialog.setVisible(true);
    }
    /**
     * Construye un panel de formulario con grid layout
     * @param rows Número de filas del formulario
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
     * @param dialog Diálogo al que añadir los botones
     * @param confirmButton Botón de confirmación principal
     */
    private void addBottomButtons(JDialog dialog, JButton confirmButton) {
        JButton cancelButton = new JButton("Cancelar");
        styleLargeButton(cancelButton);
        cancelButton.addActionListener(e -> dialog.dispose());
        
        cancelButton.setForeground(Color.BLACK);
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        bottomPanel.setBackground(new Color(108, 67, 37));
        bottomPanel.add(confirmButton);
        bottomPanel.add(cancelButton);

        dialog.add(bottomPanel, BorderLayout.SOUTH);
    }

    /**
     * Muestra mensaje de error
     * 
     * @param message Mensaje a mostrar
     */
    private void showError(Component parent, String message) {
        JOptionPane.showMessageDialog(parent, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Estiliza un botón grande
     * @param button Botón a estilizar
     */
    private void styleLargeButton(JButton button) {
        button.setFont(new Font("Segoe UI", Font.BOLD, 18));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(108, 67, 37));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createEtchedBorder(EtchedBorder.LOWERED),
            BorderFactory.createEmptyBorder(15, 30, 15, 30)
        ));
    }
}