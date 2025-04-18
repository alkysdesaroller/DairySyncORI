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
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.io.InputStream;
import java.awt.Dimension;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Image;

/**
 * Interfaz gráfica para la gestión de reproducción de ganado
 * Sistema DairySync - Innovación y calidad en cada gota
 */
public class ReproduccionGui extends JFrame {
    // Constantes para los estados
    private static final String ESTADO_INSEMINADA = "Inseminada";
    private static final String ESTADO_EN_PARTO = "En parto";
    private static final String ESTADO_PARIDA = "Parida";
    
    // Paleta de colores DairySync
    private static final Color COLOR_BEIGE_CLARO = new Color(237, 224, 204); // Beige claro (fondo principal)
    private static final Color COLOR_BEIGE_MEDIO = new Color(211, 195, 169); // Beige medio (paneles)
    private static final Color COLOR_VERDE_OLIVA = new Color(153, 158, 122); // Verde oliva (acentos)
    private static final Color COLOR_DORADO = new Color(182, 155, 76);      // Dorado (botones principales)
    private static final Color COLOR_MARRON = new Color(92, 52, 22);        // Marrón oscuro (texto)
    private static final Color COLOR_TERRACOTA = new Color(166, 84, 53);    // Terracota (alertas)
    private static final Color COLOR_MARRON_SUAVE = new Color(135, 104, 74); // Marrón suave (botones secundarios)
    private static final Color COLOR_BLANCO_ROTO = new Color(250, 245, 235); // Blanco roto (campos de texto)
    
    // Constantes para estados visuales
    private static final Color COLOR_ESTADO_INSEMINADA = new Color(120, 190, 120); // Verde claro
    private static final Color COLOR_ESTADO_EN_PARTO = new Color(240, 200, 80);    // Amarillo
    private static final Color COLOR_ESTADO_PARIDA = new Color(180, 150, 220);     // Morado claro
    
    // Componentes de la interfaz
    private JTextField vacaIdField, toroIdField, fechaField, granjeroField, granjeroIdField;
    private JTextArea descripcionField;
    private JComboBox<String> razaVacaBox, estadoBox;
    private JLabel resultadoLabel, logoLabel, tituloLabel;
    private JTable vacasTable;
    private DefaultTableModel tableModel;
    private JPanel sidePanel;
    private Image logoImage;
    
    // Lista para almacenar vacas agregadas
    private List<Reproduccion> vacasRegistradas = new ArrayList<>();

    /**
     * Constructor de la interfaz gráfica
     */
    public ReproduccionGui() {
        super("DairySync - Gestión de Reproducción");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Configurar para pantalla completa
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        
        // Establecer colores base
        getContentPane().setBackground(COLOR_BEIGE_CLARO);
        
        // Configurar el layout principal como BorderLayout
        setLayout(new BorderLayout());
        
        // Crear panel lateral izquierdo para logo y navegación
        sidePanel = createSidePanel();
        add(sidePanel, BorderLayout.WEST);
        
        // Panel principal para el contenido
        JPanel mainPanel = new JPanel(new BorderLayout(20, 20));
        mainPanel.setBackground(COLOR_BEIGE_CLARO);
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        // Título principal
        tituloLabel = new JLabel("Sistema de Gestión de Reproducción");
        tituloLabel.setFont(createCustomTitleFont(28));
        tituloLabel.setForeground(COLOR_MARRON);
        tituloLabel.setHorizontalAlignment(SwingConstants.CENTER);
        tituloLabel.setBorder(new EmptyBorder(15, 0, 25, 0));
        mainPanel.add(tituloLabel, BorderLayout.NORTH);
        
        // Panel central con formulario y tabla en distribución vertical
        JPanel centerPanel = new JPanel(new BorderLayout(20, 20));
        centerPanel.setBackground(COLOR_BEIGE_CLARO);
        
        // Panel de contenido con formulario y botones
        JPanel contentPanel = new JPanel(new BorderLayout(20, 20));
        contentPanel.setBackground(COLOR_BEIGE_CLARO);
        
        // Panel para formulario de entrada
        JPanel formPanel = crearPanelFormulario();
        
        // Panel para botones de acción
        JPanel buttonPanel = crearPanelBotones();
        
        contentPanel.add(formPanel, BorderLayout.CENTER);
        contentPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        // Panel para tabla de resultados
        JPanel tablePanel = crearPanelTabla();
        
        centerPanel.add(contentPanel, BorderLayout.NORTH);
        centerPanel.add(tablePanel, BorderLayout.CENTER);
        
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        
        // Panel inferior para mensajes
        JPanel statusPanel = new JPanel(new BorderLayout());
        statusPanel.setBackground(COLOR_BEIGE_MEDIO);
        statusPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        
        resultadoLabel = new JLabel(" ");
        resultadoLabel.setForeground(COLOR_DORADO);
        resultadoLabel.setFont(createCustomTextFont(14, Font.BOLD));
        
        statusPanel.add(resultadoLabel, BorderLayout.CENTER);
        
        // Eslogan
        JLabel sloganLabel = new JLabel("Innovación y calidad en cada gota.");
        sloganLabel.setForeground(COLOR_MARRON_SUAVE);
        sloganLabel.setFont(createCustomTextFont(12, Font.ITALIC));
        sloganLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        
        statusPanel.add(sloganLabel, BorderLayout.EAST);
        
        mainPanel.add(statusPanel, BorderLayout.SOUTH);
        
        // Añadir el panel principal
        add(mainPanel, BorderLayout.CENTER);
        
        // Establecer visible
        pack(); // Ajustar tamaño al contenido
        setLocationRelativeTo(null); // Centrar en pantalla
        setVisible(true);
    }
    
    /**
     * Crea un panel lateral con el logo y opciones de navegación
     */
    private JPanel createSidePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(COLOR_BEIGE_MEDIO);
        panel.setPreferredSize(new Dimension(240, 0));
        panel.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, COLOR_MARRON_SUAVE));
        
        // Panel para el logo
        JPanel logoPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        logoPanel.setBackground(COLOR_BEIGE_MEDIO);
        logoPanel.setBorder(BorderFactory.createEmptyBorder(30, 10, 20, 10));
        
        // Cargar y mostrar el logo
        logoLabel = new JLabel();
        try {
            URL logoURL = getClass().getResource("/reproduccion/imagenes/vaca.png");
            if (logoURL != null) {
                ImageIcon logoIcon = new ImageIcon(logoURL);
                Image logoImg = logoIcon.getImage().getScaledInstance(160, 160, Image.SCALE_SMOOTH);
                logoLabel.setIcon(new ImageIcon(logoImg));
            } else {
                // Intentar rutas alternativas
                logoURL = getClass().getResource("/imagenes/vaca.png");
                if (logoURL != null) {
                    ImageIcon logoIcon = new ImageIcon(logoURL);
                    Image logoImg = logoIcon.getImage().getScaledInstance(160, 160, Image.SCALE_SMOOTH);
                    logoLabel.setIcon(new ImageIcon(logoImg));
                } else {
                    System.out.println("No se pudo encontrar el logo");
                    logoLabel.setText("DairySync");
                    logoLabel.setFont(createCustomTitleFont(24));
                    logoLabel.setForeground(COLOR_MARRON);
                }
            }
        } catch (Exception e) {
            System.out.println("Error al cargar el logo: " + e.getMessage());
            logoLabel.setText("DairySync");
            logoLabel.setFont(createCustomTitleFont(24));
            logoLabel.setForeground(COLOR_MARRON);
        }
        
        logoPanel.add(logoLabel);
        panel.add(logoPanel, BorderLayout.NORTH);
        
        // Panel para opciones de menú
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
        menuPanel.setBackground(COLOR_BEIGE_MEDIO);
        menuPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        
        // Estilo común para botones de menú
        Font menuFont = createCustomTextFont(16, Font.BOLD);
        
        // Información de menú con iconos correspondientes
        String[] menuOptions = {
            "Reproducción", "Gestión de Vacas", "Gestión de Toros", 
            "Reportes", "Configuración", "Ayuda"
        };
        
        String[] menuIcons = {
            "vaca.png", "1.png", "2.png", 
            "3.png", "4.png", "5.png"
        };
        
        for (int i = 0; i < menuOptions.length; i++) {
            String option = menuOptions[i];
            JButton menuButton = new JButton(option);
            menuButton.setFont(menuFont);
            menuButton.setForeground(COLOR_MARRON);
            menuButton.setBackground(COLOR_BEIGE_MEDIO);
            menuButton.setBorderPainted(false);
            menuButton.setFocusPainted(false);
            menuButton.setContentAreaFilled(false);
            menuButton.setHorizontalAlignment(SwingConstants.LEFT);
            menuButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
            
            // Añadir ícono al botón
            try {
                String iconName = menuIcons[i];
                URL iconURL = getClass().getResource("/reproduccion/imagenes/" + iconName);
                if (iconURL == null) {
                    iconURL = getClass().getResource("/imagenes/" + iconName);
                }
                
                if (iconURL != null) {
                    ImageIcon originalIcon = new ImageIcon(iconURL);
                    Image scaledImage = originalIcon.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);
                    menuButton.setIcon(new ImageIcon(scaledImage));
                    menuButton.setIconTextGap(10);
                    menuButton.setHorizontalTextPosition(SwingConstants.RIGHT);
                }
            } catch (Exception e) {
                System.out.println("Error cargando icono para " + option + ": " + e.getMessage());
            }
            
            // Resaltar el botón "Reproducción" como activo
            if (option.equals("Reproducción")) {
                menuButton.setForeground(COLOR_DORADO);
                menuButton.setBorder(BorderFactory.createMatteBorder(0, 3, 0, 0, COLOR_DORADO));
            }
            
            // Añadir efecto hover
            menuButton.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    if (!option.equals("Reproducción")) {
                        menuButton.setForeground(COLOR_DORADO);
                    }
                }
                
                public void mouseExited(java.awt.event.MouseEvent evt) {
                    if (!option.equals("Reproducción")) {
                        menuButton.setForeground(COLOR_MARRON);
                    }
                }
            });
            
            menuPanel.add(menuButton);
            menuPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        }
        
        panel.add(menuPanel, BorderLayout.CENTER);
        
        // Panel inferior con versión
        JPanel versionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        versionPanel.setBackground(COLOR_BEIGE_MEDIO);
        JLabel versionLabel = new JLabel("DairySync v1.0.2");
        versionLabel.setFont(createCustomTextFont(12, Font.ITALIC));
        versionLabel.setForeground(COLOR_MARRON_SUAVE);
        versionPanel.add(versionLabel);
        
        panel.add(versionPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    /**
     * Crea el panel con el formulario de entrada de datos
     */
    private JPanel crearPanelFormulario() {
        JPanel panel = new JPanel(new BorderLayout(15, 15));
        panel.setBackground(COLOR_BEIGE_MEDIO);
        
        // Crear un borde con título estilizado
        javax.swing.border.TitledBorder titledBorder = BorderFactory.createTitledBorder("");
        titledBorder.setTitleColor(COLOR_MARRON);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createEmptyBorder(10, 10, 10, 10),
            titledBorder
        ));
        
        // Título del panel
        JLabel formTitle = new JLabel("Registro de Reproducción");
        formTitle.setFont(createCustomTitleFont(20));
        formTitle.setForeground(COLOR_MARRON);
        formTitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        panel.add(formTitle, BorderLayout.NORTH);
        
        // Panel de campos en dos columnas
        JPanel fieldsPanel = new JPanel(new GridBagLayout());
        fieldsPanel.setBackground(COLOR_BEIGE_MEDIO);
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);
        
        // Estilo para etiquetas
        Font labelFont = createCustomTextFont(14, Font.BOLD);
        
        // Crear campos y etiquetas
        // Columna izquierda
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.weightx = 0.0;
        JLabel label1 = new JLabel("ID Vaca:");
        label1.setFont(labelFont);
        label1.setForeground(COLOR_MARRON);
        fieldsPanel.add(label1, gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 0.5;
        vacaIdField = createStyledTextField(10);
        fieldsPanel.add(vacaIdField, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.0;
        JLabel label2 = new JLabel("ID Toro:");
        label2.setFont(labelFont);
        label2.setForeground(COLOR_MARRON);
        fieldsPanel.add(label2, gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 0.5;
        toroIdField = createStyledTextField(10);
        fieldsPanel.add(toroIdField, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0.0;
        JLabel label3 = new JLabel("Raza de la Vaca:");
        label3.setFont(labelFont);
        label3.setForeground(COLOR_MARRON);
        fieldsPanel.add(label3, gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 0.5;
        String[] razas = {"Holstein", "Jersey", "Brahman", "Gyr", "Angus", "Hereford", "Limousin", "Charolais"};
        razaVacaBox = createStyledComboBox(razas, false);
        fieldsPanel.add(razaVacaBox, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 0.0;
        JLabel label4 = new JLabel("Estado:");
        label4.setFont(labelFont);
        label4.setForeground(COLOR_MARRON);
        fieldsPanel.add(label4, gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 0.5;
        String[] estados = {ESTADO_INSEMINADA, ESTADO_EN_PARTO, ESTADO_PARIDA};
        estadoBox = createStyledComboBox(estados, true);
        fieldsPanel.add(estadoBox, gbc);
        
        // Columna derecha
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.weightx = 0.0;
        JLabel label5 = new JLabel("Fecha Inseminación:");
        label5.setFont(labelFont);
        label5.setForeground(COLOR_MARRON);
        fieldsPanel.add(label5, gbc);
        
        gbc.gridx = 3;
        gbc.weightx = 0.5;
        fechaField = createStyledTextField(10);
        fechaField.setToolTipText("Formato: YYYY-MM-DD");
        fieldsPanel.add(fechaField, gbc);
        
        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.weightx = 0.0;
        JLabel label6 = new JLabel("Nombre del Granjero:");
        label6.setFont(labelFont);
        label6.setForeground(COLOR_MARRON);
        fieldsPanel.add(label6, gbc);
        
        gbc.gridx = 3;
        gbc.weightx = 0.5;
        granjeroField = createStyledTextField(15);
        fieldsPanel.add(granjeroField, gbc);
        
        gbc.gridx = 2;
        gbc.gridy = 2;
        gbc.weightx = 0.0;
        JLabel label7 = new JLabel("ID del Granjero:");
        label7.setFont(labelFont);
        label7.setForeground(COLOR_MARRON);
        fieldsPanel.add(label7, gbc);
        
        gbc.gridx = 3;
        gbc.weightx = 0.5;
        granjeroIdField = createStyledTextField(10);
        fieldsPanel.add(granjeroIdField, gbc);
        
        // Panel de descripción (ocupando ambas columnas)
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.weightx = 0.0;
        JLabel label8 = new JLabel("Descripción:");
        label8.setFont(labelFont);
        label8.setForeground(COLOR_MARRON);
        fieldsPanel.add(label8, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.gridwidth = 3;
        gbc.weightx = 1.0;
        
        descripcionField = new JTextArea(3, 20);
        descripcionField.setFont(createCustomTextFont(14, Font.PLAIN));
        descripcionField.setLineWrap(true);
        descripcionField.setWrapStyleWord(true);
        descripcionField.setBackground(COLOR_BLANCO_ROTO);
        descripcionField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(COLOR_MARRON_SUAVE),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        
        JScrollPane scrollPane = new JScrollPane(descripcionField);
        scrollPane.setBorder(BorderFactory.createLineBorder(COLOR_MARRON_SUAVE));
        fieldsPanel.add(scrollPane, gbc);
        
        panel.add(fieldsPanel, BorderLayout.CENTER);
        
        return panel;
    }
    
    /**
     * Crea un JTextField con el estilo personalizado
     */
    private JTextField createStyledTextField(int columns) {
        JTextField field = new JTextField(columns);
        field.setFont(createCustomTextFont(14, Font.PLAIN));
        field.setBackground(COLOR_BLANCO_ROTO);
        field.setForeground(COLOR_MARRON);
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(COLOR_MARRON_SUAVE),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        return field;
    }
    
    /**
     * Crea un JComboBox con el estilo personalizado
     */
    private JComboBox<String> createStyledComboBox(String[] items, boolean isEstadoBox) {
        JComboBox<String> comboBox = new JComboBox<>(items);
        comboBox.setFont(createCustomTextFont(14, Font.PLAIN));
        comboBox.setBackground(COLOR_BLANCO_ROTO);
        comboBox.setForeground(COLOR_MARRON);
        comboBox.setBorder(BorderFactory.createLineBorder(COLOR_MARRON_SUAVE));
        
        // Personalizar el renderizador para mejorar la apariencia
        if (isEstadoBox) {
            // Renderizador especial para ComboBox de estados con iconos de color
            comboBox.setRenderer(new DefaultListCellRenderer() {
                @Override
                public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                             boolean isSelected, boolean cellHasFocus) {
                    JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                    label.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
                    
                    if (value != null) {
                        String estado = value.toString();
                        label.setIconTextGap(10);
                        
                        if (estado.equals(ESTADO_INSEMINADA)) {
                            label.setIcon(createCircleIcon(COLOR_ESTADO_INSEMINADA, 12));
                        } else if (estado.equals(ESTADO_EN_PARTO)) {
                            label.setIcon(createCircleIcon(COLOR_ESTADO_EN_PARTO, 12));
                        } else if (estado.equals(ESTADO_PARIDA)) {
                            label.setIcon(createCircleIcon(COLOR_ESTADO_PARIDA, 12));
                        }
                    }
                    
                    if (isSelected) {
                        label.setBackground(COLOR_DORADO);
                        label.setForeground(COLOR_BLANCO_ROTO);
                    } else {
                        label.setBackground(COLOR_BLANCO_ROTO);
                        label.setForeground(COLOR_MARRON);
                    }
                    
                    return label;
                }
            });
        } else {
            // Renderizador estándar para otros ComboBox
            DefaultListCellRenderer renderer = new DefaultListCellRenderer() {
                @Override
                public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                             boolean isSelected, boolean cellHasFocus) {
                    super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                    setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
                    
                    if (isSelected) {
                        setBackground(COLOR_DORADO);
                        setForeground(COLOR_BLANCO_ROTO);
                    } else {
                        setBackground(COLOR_BLANCO_ROTO);
                        setForeground(COLOR_MARRON);
                    }
                    
                    return this;
                }
            };
            
            comboBox.setRenderer(renderer);
        }
        
        return comboBox;
    }
    
    /**
     * Crea un icono circular del color especificado
     */
    private Icon createCircleIcon(Color color, int size) {
        BufferedImage img = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = img.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(color);
        g2d.fillOval(0, 0, size, size);
        g2d.setColor(color.darker());
        g2d.drawOval(0, 0, size - 1, size - 1);
        g2d.dispose();
        return new ImageIcon(img);
    }
    
    /**
     * Crea el panel con los botones de acción
     */
    private JPanel crearPanelBotones() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        panel.setBackground(COLOR_BEIGE_MEDIO);
        
        // Estilo común para botones
        Font buttonFont = createCustomTextFont(14, Font.BOLD);
        Dimension buttonSize = new Dimension(180, 40);
        
        // Botón Agregar con ícono
        JButton agregarBtn = crearBotonEstilizado("Agregar Registro", buttonFont, buttonSize, COLOR_DORADO);
        try {
            URL iconURL = getClass().getResource("/reproduccion/imagenes/vaca.png");
            if (iconURL == null) {
                iconURL = getClass().getResource("/imagenes/vaca.png");
            }
            
            if (iconURL != null) {
                ImageIcon originalIcon = new ImageIcon(iconURL);
                Image scaledImage = originalIcon.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);
                agregarBtn.setIcon(new ImageIcon(scaledImage));
                agregarBtn.setIconTextGap(10);
            }
        } catch (Exception e) {
            System.out.println("Error cargando icono para botón agregar: " + e.getMessage());
        }
        agregarBtn.addActionListener(e -> agregarRegistro());
        
        // Botón Limpiar con ícono
        JButton limpiarBtn = crearBotonEstilizado("Limpiar Formulario", buttonFont, buttonSize, COLOR_MARRON_SUAVE);
        try {
            URL iconURL = getClass().getResource("/reproduccion/imagenes/3.png");
            if (iconURL == null) {
                iconURL = getClass().getResource("/imagenes/3.png");
            }
            
            if (iconURL != null) {
                ImageIcon originalIcon = new ImageIcon(iconURL);
                Image scaledImage = originalIcon.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);
                limpiarBtn.setIcon(new ImageIcon(scaledImage));
                limpiarBtn.setIconTextGap(10);
            }
        } catch (Exception e) {
            System.out.println("Error cargando icono para botón limpiar: " + e.getMessage());
        }
        limpiarBtn.addActionListener(e -> limpiarFormulario());
        
        // Botón Eliminar con ícono
        JButton eliminarBtn = crearBotonEstilizado("Eliminar Registro", buttonFont, buttonSize, COLOR_TERRACOTA);
        try {
            URL iconURL = getClass().getResource("/reproduccion/imagenes/4.png");
            if (iconURL == null) {
                iconURL = getClass().getResource("/imagenes/4.png");
            }
            
            if (iconURL != null) {
                ImageIcon originalIcon = new ImageIcon(iconURL);
                Image scaledImage = originalIcon.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);
                eliminarBtn.setIcon(new ImageIcon(scaledImage));
                eliminarBtn.setIconTextGap(10);
            }
        } catch (Exception e) {
            System.out.println("Error cargando icono para botón eliminar: " + e.getMessage());
        }
        eliminarBtn.addActionListener(e -> eliminarRegistroSeleccionado());
        
        // Botón Verificar con ícono
        JButton actualizarBtn = crearBotonEstilizado("Verificar Estados", buttonFont, buttonSize, COLOR_VERDE_OLIVA);
        try {
            URL iconURL = getClass().getResource("/reproduccion/imagenes/5.png");
            if (iconURL == null) {
                iconURL = getClass().getResource("/imagenes/5.png");
            }
            
            if (iconURL != null) {
                ImageIcon originalIcon = new ImageIcon(iconURL);
                Image scaledImage = originalIcon.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);
                actualizarBtn.setIcon(new ImageIcon(scaledImage));
                actualizarBtn.setIconTextGap(10);
            }
        } catch (Exception e) {
            System.out.println("Error cargando icono para botón actualizar: " + e.getMessage());
        }
        actualizarBtn.addActionListener(e -> actualizarEstados());
        
        panel.add(agregarBtn);
        panel.add(limpiarBtn);
        panel.add(eliminarBtn);
        panel.add(actualizarBtn);
        
        return panel;
    }
    
    /**
     * Crea un botón con el estilo personalizado
     */
    private JButton crearBotonEstilizado(String texto, Font font, Dimension size, Color bgColor) {
        JButton boton = new JButton(texto);
        boton.setFont(font);
        boton.setPreferredSize(size);
        boton.setBackground(bgColor);
        boton.setForeground(COLOR_BLANCO_ROTO);
        boton.setFocusPainted(false);
        boton.setBorderPainted(false);
        boton.setOpaque(true);
        
        // Añadir efecto hover
        boton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                boton.setBackground(brighten(bgColor, 0.1));
            }
            
            public void mouseExited(java.awt.event.MouseEvent evt) {
                boton.setBackground(bgColor);
            }
        });
        
        return boton;
    }
    
    /**
     * Crea el panel con la tabla de registros
     */
    private JPanel crearPanelTabla() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(COLOR_BEIGE_CLARO);
        
        // Título del panel con icono
        JLabel tableTitle = new JLabel("Registros de Reproducción");
        tableTitle.setFont(createCustomTitleFont(20));
        tableTitle.setForeground(COLOR_MARRON);
        tableTitle.setBorder(BorderFactory.createEmptyBorder(10, 10, 15, 10));
        
        // Añadir icono al título de la tabla
        try {
            URL iconURL = getClass().getResource("/reproduccion/imagenes/2.png");
            if (iconURL == null) {
                iconURL = getClass().getResource("/imagenes/2.png");
            }
            
            if (iconURL != null) {
                ImageIcon originalIcon = new ImageIcon(iconURL);
                Image scaledImage = originalIcon.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);
                tableTitle.setIcon(new ImageIcon(scaledImage));
                tableTitle.setIconTextGap(10);
            }
        } catch (Exception e) {
            System.out.println("Error cargando icono para título de tabla: " + e.getMessage());
        }
        
        panel.add(tableTitle, BorderLayout.NORTH);
        
        // Crear modelo de tabla con columnas
        String[] columnas = {"ID Vaca", "Raza", "Estado", "Fecha Inseminación", "Días", "ID Toro", "Granjero", "ID Granjero"};
        tableModel = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Hacer la tabla no editable
            }
            
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                // Para ordenación correcta, definir el tipo de cada columna
                if (columnIndex == 0 || columnIndex == 5 || columnIndex == 7) {
                    return Integer.class; // Columnas de ID son enteros
                } else if (columnIndex == 4) {
                    return Long.class; // Columna de días es entero largo
                }
                return String.class; // El resto son strings
            }
        };
        
        vacasTable = new JTable(tableModel);
        vacasTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        vacasTable.setAutoCreateRowSorter(true);
        vacasTable.setFont(createCustomTextFont(14, Font.PLAIN));
        vacasTable.setRowHeight(30);
        vacasTable.setGridColor(COLOR_BEIGE_MEDIO);
        vacasTable.setShowGrid(true);
        vacasTable.setIntercellSpacing(new Dimension(5, 5));
        
        // Personalizar encabezados de tabla
        JTableHeader header = vacasTable.getTableHeader();
        header.setBackground(COLOR_DORADO);
        header.setForeground(COLOR_BLANCO_ROTO);
        header.setFont(createCustomTextFont(14, Font.BOLD));
        header.setPreferredSize(new Dimension(header.getWidth(), 35));
        
        // Personalizar selección
        vacasTable.setSelectionBackground(COLOR_DORADO);
        vacasTable.setSelectionForeground(COLOR_BLANCO_ROTO);
        
        // Personalizar el renderizado de las celdas, incluye indicadores visuales para estado
        DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                          boolean isSelected, boolean hasFocus,
                                                          int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                
                // Si no está seleccionada, aplicar un fondo alternado para las filas
                if (!isSelected) {
                    c.setBackground(row % 2 == 0 ? COLOR_BLANCO_ROTO : new Color(240, 235, 225));
                    c.setForeground(COLOR_MARRON);
                }
                
                // Centrar contenido para ciertas columnas
                setHorizontalAlignment(column == 0 || column == 4 || column == 5 || column == 7 
                                      ? SwingConstants.CENTER : SwingConstants.LEFT);
                
                // Destacar el estado según su valor con íconos de color
                if (column == 2) { // Columna Estado
                    String estadoStr = value.toString();
                    
                    if (this instanceof JLabel) {
                        JLabel label = (JLabel) this;
                        label.setIconTextGap(10);
                        
                        if (estadoStr.equals(ESTADO_INSEMINADA)) {
                            label.setIcon(createCircleIcon(COLOR_ESTADO_INSEMINADA, 12));
                            if (!isSelected) {
                                c.setBackground(new Color(220, 235, 220)); // Verde claro
                            }
                        } else if (estadoStr.equals(ESTADO_EN_PARTO)) {
                            label.setIcon(createCircleIcon(COLOR_ESTADO_EN_PARTO, 12));
                            if (!isSelected) {
                                c.setBackground(new Color(240, 230, 180)); // Amarillo claro
                            }
                            setFont(getFont().deriveFont(Font.BOLD));
                        } else if (estadoStr.equals(ESTADO_PARIDA)) {
                            label.setIcon(createCircleIcon(COLOR_ESTADO_PARIDA, 12));
                            if (!isSelected) {
                                c.setBackground(new Color(230, 220, 240)); // Morado claro
                            }
                        }
                    }
                }
                
                // Destacar los días dependiendo de su valor
                if (column == 4) { // Columna Días
                    long dias = (long) value;
                    if (dias >= 270 && dias <= 290) {
                        if (!isSelected) {
                            c.setBackground(new Color(240, 230, 180)); // Amarillo claro
                        }
                        setFont(getFont().deriveFont(Font.BOLD));
                    } else if (dias > 290) {
                        if (!isSelected) {
                            c.setBackground(new Color(230, 220, 240)); // Morado claro
                        }
                        setFont(getFont().deriveFont(Font.BOLD));
                    }
                }
                
                setBorder(BorderFactory.createEmptyBorder(2, 5, 2, 5));
                return c;
            }
        };
        
        // Aplicar el renderizador a todas las columnas
        for (int i = 0; i < vacasTable.getColumnCount(); i++) {
            vacasTable.getColumnModel().getColumn(i).setCellRenderer(cellRenderer);
        }
        
        // Ajustar anchos de columnas
        vacasTable.getColumnModel().getColumn(0).setPreferredWidth(80);  // ID Vaca
        vacasTable.getColumnModel().getColumn(1).setPreferredWidth(120); // Raza
        vacasTable.getColumnModel().getColumn(2).setPreferredWidth(100); // Estado
        vacasTable.getColumnModel().getColumn(3).setPreferredWidth(150); // Fecha
        vacasTable.getColumnModel().getColumn(4).setPreferredWidth(80);  // Días
        vacasTable.getColumnModel().getColumn(5).setPreferredWidth(80);  // ID Toro
        vacasTable.getColumnModel().getColumn(6).setPreferredWidth(150); // Granjero
        vacasTable.getColumnModel().getColumn(7).setPreferredWidth(80);  // ID Granjero
        
        JScrollPane scrollPane = new JScrollPane(vacasTable);
        scrollPane.setBorder(BorderFactory.createLineBorder(COLOR_MARRON_SUAVE));
        scrollPane.getViewport().setBackground(COLOR_BLANCO_ROTO);
        
        // Ajustar altura preferida del panel de tabla
        scrollPane.setPreferredSize(new Dimension(0, 400));
        
        panel.add(scrollPane, BorderLayout.CENTER);
        
        // Panel de búsqueda y estadísticas
        JPanel statsPanel = new JPanel(new BorderLayout(10, 0));
        statsPanel.setBackground(COLOR_BEIGE_MEDIO);
        statsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Panel izquierdo para búsqueda
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        searchPanel.setBackground(COLOR_BEIGE_MEDIO);
        
        JLabel searchLabel = new JLabel("Buscar:");
        searchLabel.setFont(createCustomTextFont(14, Font.BOLD));
        searchLabel.setForeground(COLOR_MARRON);
        
        JTextField searchField = createStyledTextField(15);
        
        JButton searchButton = crearBotonEstilizado("Buscar", createCustomTextFont(12, Font.BOLD), 
                                                 new Dimension(100, 30), COLOR_MARRON_SUAVE);
        
        // Añadir icono a botón de búsqueda
        try {
            URL iconURL = getClass().getResource("/reproduccion/imagenes/3.png");
            if (iconURL == null) {
                iconURL = getClass().getResource("/imagenes/3.png");
            }
            
            if (iconURL != null) {
                ImageIcon originalIcon = new ImageIcon(iconURL);
                Image scaledImage = originalIcon.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH);
                searchButton.setIcon(new ImageIcon(scaledImage));
                searchButton.setIconTextGap(5);
            }
        } catch (Exception e) {
            System.out.println("Error cargando icono para botón de búsqueda: " + e.getMessage());
        }
        
        searchPanel.add(searchLabel);
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        
        // Panel derecho para estadísticas
        JPanel countPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        countPanel.setBackground(COLOR_BEIGE_MEDIO);
        
        JLabel totalLabel = new JLabel("Total registros: 0");
        totalLabel.setFont(createCustomTextFont(14, Font.BOLD));
        totalLabel.setForeground(COLOR_MARRON);
        
        JLabel inseminadasLabel = new JLabel("Inseminadas: 0");
        inseminadasLabel.setFont(createCustomTextFont(14, Font.PLAIN));
        inseminadasLabel.setForeground(COLOR_MARRON);
        
        JLabel enPartoLabel = new JLabel("En parto: 0");
        enPartoLabel.setFont(createCustomTextFont(14, Font.PLAIN));
        enPartoLabel.setForeground(COLOR_MARRON);
        
        JLabel paridasLabel = new JLabel("Paridas: 0");
        paridasLabel.setFont(createCustomTextFont(14, Font.PLAIN));
        paridasLabel.setForeground(COLOR_MARRON);
        
        countPanel.add(totalLabel);
        countPanel.add(inseminadasLabel);
        countPanel.add(enPartoLabel);
        countPanel.add(paridasLabel);
        
        statsPanel.add(searchPanel, BorderLayout.WEST);
        statsPanel.add(countPanel, BorderLayout.EAST);
        
        panel.add(statsPanel, BorderLayout.SOUTH);
        
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
            
            // Mostrar diálogo de confirmación
            int result = JOptionPane.showConfirmDialog(
                this,
                "¿Está seguro de eliminar el registro de la vaca ID " + vacaId + "?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
            );
            
            if (result == JOptionPane.YES_OPTION) {
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
        
        // Contadores para estadísticas
        int inseminadas = 0;
        int enParto = 0;
        int paridas = 0;
        
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
            
            // Conteo por estado
            switch (r.getEstado()) {
                case ESTADO_INSEMINADA:
                    inseminadas++;
                    break;
                case ESTADO_EN_PARTO:
                    enParto++;
                    break;
                case ESTADO_PARIDA:
                    paridas++;
                    break;
            }
            
            tableModel.addRow(fila);
        }
        
        // Actualizar etiquetas de estadísticas si existen
        Component[] components = ((Container) vacasTable.getParent().getParent()).getComponents();
        for (Component c : components) {
            if (c instanceof JPanel && ((JPanel) c).getComponentCount() > 0) {
                Component[] subcomps = ((JPanel) c).getComponents();
                for (Component sc : subcomps) {
                    if (sc instanceof JPanel) {
                        Component[] labels = ((JPanel) sc).getComponents();
                        for (Component label : labels) {
                            if (label instanceof JLabel) {
                                String text = ((JLabel) label).getText();
                                if (text.startsWith("Total registros:")) {
                                    ((JLabel) label).setText("Total registros: " + vacasRegistradas.size());
                                } else if (text.startsWith("Inseminadas:")) {
                                    ((JLabel) label).setText("Inseminadas: " + inseminadas);
                                } else if (text.startsWith("En parto:")) {
                                    ((JLabel) label).setText("En parto: " + enParto);
                                } else if (text.startsWith("Paridas:")) {
                                    ((JLabel) label).setText("Paridas: " + paridas);
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    
    /**
     * Muestra un mensaje en el label de resultado
     */
    private void mostrarMensaje(String mensaje, boolean esError) {
        resultadoLabel.setText(mensaje);
        resultadoLabel.setForeground(esError ? COLOR_TERRACOTA : COLOR_DORADO);
        
        // Hacer que el mensaje desaparezca después de 5 segundos
        Timer timer = new Timer(5000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resultadoLabel.setText(" ");
            }
        });
        timer.setRepeats(false);
        timer.start();
    }
    
    /**
     * Crea una fuente personalizada para títulos (intentando usar Rockstone)
     */
    private Font createCustomTitleFont(int size) {
    try {
        // Intenta cargar la fuente Rockstone si está disponible
        InputStream is = getClass().getResourceAsStream("/reproduccion/fonts/Rockstone Regular.ttf");
        if (is == null) {
            // Intenta una ruta alternativa
            is = getClass().getResourceAsStream("/fonts/Rockstone Regular.ttf");
        }

        if (is != null) {
            Font customFont = Font.createFont(Font.TRUETYPE_FONT, is);
            return customFont.deriveFont(Font.BOLD, size);
        } else {
            System.err.println("No se pudo encontrar el archivo de fuente");
            return new Font("SansSerif", Font.BOLD, size);
        }
    } catch (Exception e) {
        // Si no se puede cargar, usa una fuente sans-serif similar
        System.err.println("Error al cargar la fuente: " + e.getMessage());
        return new Font("SansSerif", Font.BOLD, size);
    }
}
    
    /**
     * Crea una fuente personalizada para texto (intentando usar Ambiguity Rounded)
     */
  
private Font createCustomTextFont(int size, int style) {
    try {
        // Intenta cargar directamente desde el nombre del archivo
        InputStream is = getClass().getResourceAsStream("ambiguity rounded.ttf");

        // Si no lo encuentra, prueba en la carpeta fonts
        if (is == null) {
            is = getClass().getResourceAsStream("/fonts/ambiguity rounded.ttf");
        }

        // Si sigue sin encontrarlo, prueba otra ruta
        if (is == null) {
            is = getClass().getResourceAsStream("/reproduccion/fonts/ambiguity rounded.ttf");
        }

        if (is != null) {
            Font customFont = Font.createFont(Font.TRUETYPE_FONT, is);
            return customFont.deriveFont(style, size);
        } else {
            System.err.println("No se pudo encontrar el archivo de fuente ambiguity rounded.ttf");
            return new Font("SansSerif", style, size);
        }
    } catch (Exception e) {
        System.err.println("Error al cargar la fuente: " + e.getMessage());
        return new Font("SansSerif", style, size);
    }
}
    /**
     * Aclara un color por un factor dado
     */
    private Color brighten(Color color, double factor) {
        int r = Math.min(255, (int)(color.getRed() * (1 + factor)));
        int g = Math.min(255, (int)(color.getGreen() * (1 + factor)));
        int b = Math.min(255, (int)(color.getBlue() * (1 + factor)));
        return new Color(r, g, b);
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
    SwingUtilities.invokeLater(() -> {
        ReproduccionGui app = new ReproduccionGui();
    });
}

}
