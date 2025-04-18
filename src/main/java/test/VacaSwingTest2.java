package test;

import ModuloDariel.models.*;
import ModuloDariel.usecase.*;
import ModuloDariel.repo.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class VacaSwingTest2 {
    private VacaServicio vacaServicio;
    private JFrame frame;
    private JTextArea outputArea;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                new VacaSwingTest2().initialize();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void initialize() {
        vacaServicio = new VacaServicio(null);
        
        frame = new JFrame("Gestión Completa de Vacas");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 500);
        frame.setLayout(new BorderLayout());

        // Panel de botones con scroll
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        
        String[] buttonLabels = {
            "Registrar Nueva Vaca",
            "Listar Todas las Vacas",
            "Actualizar Estado de Salud",
            "Registrar Inseminación",
            "Registrar Parto",
            "Registrar Tratamiento",
            "Actualizar Datos Genealógicos",
            "Buscar Vaca por ID"
        };
        
        for (String label : buttonLabels) {
            JButton button = new JButton(label);
            button.setAlignmentX(Component.CENTER_ALIGNMENT);
            button.setMaximumSize(new Dimension(200, 30));
            button.addActionListener(this::handleButtonAction);
            buttonPanel.add(button);
            buttonPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        }
        
        JScrollPane buttonScroll = new JScrollPane(buttonPanel);
        buttonScroll.setPreferredSize(new Dimension(220, 400));

        // Área de texto para salida
        outputArea = new JTextArea();
        outputArea.setEditable(false);
        JScrollPane outputScroll = new JScrollPane(outputArea);

        // Agregar componentes
        frame.add(buttonScroll, BorderLayout.WEST);
        frame.add(outputScroll, BorderLayout.CENTER);

        frame.setVisible(true);
    }

    private void handleButtonAction(ActionEvent e) {
        String command = ((JButton)e.getSource()).getText();
        
        switch(command) {
            case "Registrar Nueva Vaca":
                registrarVaca();
                break;
            case "Listar Todas las Vacas":
                listarVacas();
                break;
            case "Actualizar Estado de Salud":
                actualizarEstadoSalud();
                break;
            case "Registrar Inseminación":
                registrarInseminacion();
                break;
            case "Registrar Parto":
                registrarParto();
                break;
            case "Registrar Tratamiento":
                registrarTratamiento();
                break;
            case "Buscar Vaca por ID":
                buscarVacaPorId();
                break;
        }
    }

    private void registrarVaca() {
        JPanel panel = new JPanel(new GridLayout(0, 2, 5, 5));
        
        // Campos básicos
        JTextField razaField = new JTextField();
        JTextField edadField = new JTextField();
        JTextField genealogiaField = new JTextField();
        JTextField granjaIdField = new JTextField();
        JTextField madreIdField = new JTextField();
        JTextField padreIdField = new JTextField();
        
        panel.add(new JLabel("Raza:"));
        panel.add(razaField);
        panel.add(new JLabel("Edad: "));
        panel.add(edadField);
        panel.add(new JLabel("Genealogía:"));
        panel.add(genealogiaField);
        panel.add(new JLabel("ID Granja:"));
        panel.add(granjaIdField);
        panel.add(new JLabel("ID Madre (opcional):"));
        panel.add(madreIdField);
        panel.add(new JLabel("ID Padre (opcional):"));
        panel.add(padreIdField);

        int result = JOptionPane.showConfirmDialog(frame, panel, 
                "Registrar Nueva Vaca", JOptionPane.OK_CANCEL_OPTION);
        
        if (result == JOptionPane.OK_OPTION) {
            try {
                String raza = razaField.getText();
                int edad = Integer.parseInt(edadField.getText());
                String genealogia = genealogiaField.getText();
                int granjaId = Integer.parseInt(granjaIdField.getText());
                Integer madreId = madreIdField.getText().isEmpty() ? null : Integer.parseInt(madreIdField.getText());
                Integer padreId = padreIdField.getText().isEmpty() ? null : Integer.parseInt(padreIdField.getText());
                
                // Aquí deberías modificar tu VacaServicio para aceptar madreId y padreId
                Vaca nuevaVaca = vacaServicio.registrarVaca(raza, edad, genealogia, granjaId);
                outputArea.append("Vaca registrada: ID=" + nuevaVaca.getId() + "\n");
            } catch (NumberFormatException ex) {
                showError("Error en los datos numéricos");
            }
        }
    }

    private void registrarInseminacion() {
        List<Vaca> vacas = vacaServicio.obtenerTodasLasVacas();
        if (vacas.isEmpty()) {
            showError("No hay vacas registradas");
            return;
        }

        String[] ids = vacas.stream().map(v -> String.valueOf(v.getId())).toArray(String[]::new);
        String selectedId = (String) JOptionPane.showInputDialog(frame, 
                "Seleccione Vaca:", "Registrar Inseminación", 
                JOptionPane.QUESTION_MESSAGE, null, ids, ids[0]);

        if (selectedId != null) {
            JPanel panel = new JPanel(new GridLayout(0, 2, 5, 5));
            
            JTextField fechaField = new JTextField(dateFormat.format(new Date()));
            JTextField toroIdField = new JTextField();
            JTextField metodoField = new JTextField();
            JTextField observacionesField = new JTextField();
            
            panel.add(new JLabel("Fecha (dd/mm/aaaa):"));
            panel.add(fechaField);
            panel.add(new JLabel("ID Toro:"));
            panel.add(toroIdField);
            panel.add(new JLabel("Método:"));
            panel.add(metodoField);
            panel.add(new JLabel("Observaciones:"));
            panel.add(observacionesField);

            int result = JOptionPane.showConfirmDialog(frame, panel, 
                    "Datos de Inseminación", JOptionPane.OK_CANCEL_OPTION);

            if (result == JOptionPane.OK_OPTION) {
                try {
                    Date fechaInseminacion = dateFormat.parse(fechaField.getText());
                    int idToro = Integer.parseInt(toroIdField.getText());
                    String metodo = metodoField.getText();
                    String observaciones = observacionesField.getText();
                    
                    // Aquí deberías implementar la lógica para registrar la inseminación
                    outputArea.append(String.format(
                        "Inseminación registrada para vaca ID=%s\n" +
                        "Fecha: %s | Toro ID: %d | Método: %s\n" +
                        "Observaciones: %s\n",
                        selectedId, dateFormat.format(fechaInseminacion), 
                        idToro, metodo, observaciones
                    ));
                } catch (Exception ex) {
                    showError("Error en los datos ingresados");
                }
            }
        }
    }

    private void registrarParto() {
        List<Vaca> vacas = vacaServicio.obtenerTodasLasVacas();
        if (vacas.isEmpty()) {
            showError("No hay vacas registradas");
            return;
        }

        String[] ids = vacas.stream().map(v -> String.valueOf(v.getId())).toArray(String[]::new);
        String selectedId = (String) JOptionPane.showInputDialog(frame, 
                "Seleccione Vaca:", "Registrar Parto", 
                JOptionPane.QUESTION_MESSAGE, null, ids, ids[0]);

        if (selectedId != null) {
            JPanel panel = new JPanel(new GridLayout(0, 2, 5, 5));
            
            JTextField fechaField = new JTextField(dateFormat.format(new Date()));
            JTextField criasField = new JTextField("1");
            JTextField pesoCriaField = new JTextField();
            JTextField observacionesField = new JTextField();
            
            panel.add(new JLabel("Fecha de parto (dd/mm/aaaa):"));
            panel.add(fechaField);
            panel.add(new JLabel("Número de crías:"));
            panel.add(criasField);
            panel.add(new JLabel("Peso cría (kg):"));
            panel.add(pesoCriaField);
            panel.add(new JLabel("Observaciones:"));
            panel.add(observacionesField);

            int result = JOptionPane.showConfirmDialog(frame, panel, 
                    "Datos del Parto", JOptionPane.OK_CANCEL_OPTION);

            if (result == JOptionPane.OK_OPTION) {
                try {
                    Date fechaParto = dateFormat.parse(fechaField.getText());
                    int numCrias = Integer.parseInt(criasField.getText());
                    double pesoCria = pesoCriaField.getText().isEmpty() ? 0 : Double.parseDouble(pesoCriaField.getText());
                    String observaciones = observacionesField.getText();
                    
                    // Aquí deberías implementar la lógica para registrar el parto
                    outputArea.append(String.format(
                        "Parto registrado para vaca ID=%s\n" +
                        "Fecha: %s | Crías: %d | Peso: %.2f kg\n" +
                        "Observaciones: %s\n",
                        selectedId, dateFormat.format(fechaParto), 
                        numCrias, pesoCria, observaciones
                    ));
                } catch (Exception ex) {
                    showError("Error en los datos ingresados");
                }
            }
        }
    }

    private void registrarTratamiento() {
        List<Vaca> vacas = vacaServicio.obtenerTodasLasVacas();
        if (vacas.isEmpty()) {
            showError("No hay vacas registradas");
            return;
        }

        String[] ids = vacas.stream().map(v -> String.valueOf(v.getId())).toArray(String[]::new);
        String selectedId = (String) JOptionPane.showInputDialog(frame, 
                "Seleccione Vaca:", "Registrar Tratamiento", 
                JOptionPane.QUESTION_MESSAGE, null, ids, ids[0]);

        if (selectedId != null) {
            JPanel panel = new JPanel(new GridLayout(0, 2, 5, 5));
            
            JTextField fechaField = new JTextField(dateFormat.format(new Date()));
            JTextField tratamientoField = new JTextField();
            JTextField dosisField = new JTextField();
            JTextField veterinarioField = new JTextField();
            JTextField diagnosticoField = new JTextField();
            
            panel.add(new JLabel("Fecha (dd/mm/aaaa):"));
            panel.add(fechaField);
            panel.add(new JLabel("Tratamiento:"));
            panel.add(tratamientoField);
            panel.add(new JLabel("Dosis:"));
            panel.add(dosisField);
            panel.add(new JLabel("Veterinario:"));
            panel.add(veterinarioField);
            panel.add(new JLabel("Diagnóstico:"));
            panel.add(diagnosticoField);

            int result = JOptionPane.showConfirmDialog(frame, panel, 
                    "Datos del Tratamiento", JOptionPane.OK_CANCEL_OPTION);

            if (result == JOptionPane.OK_OPTION) {
                try {
                    Date fecha = dateFormat.parse(fechaField.getText());
                    String tratamiento = tratamientoField.getText();
                    String dosis = dosisField.getText();
                    String veterinario = veterinarioField.getText();
                    String diagnostico = diagnosticoField.getText();
                    
                    // Aquí deberías implementar la lógica para registrar el tratamiento
                    outputArea.append(String.format(
                        "Tratamiento registrado para vaca ID=%s\n" +
                        "Fecha: %s | Tratamiento: %s | Dosis: %s\n" +
                        "Veterinario: %s | Diagnóstico: %s\n",
                        selectedId, dateFormat.format(fecha), 
                        tratamiento, dosis, veterinario, diagnostico
                    ));
                } catch (Exception ex) {
                    showError("Error en los datos ingresados");
                }
            }
        }
    }

    private void listarVacas() {
        outputArea.setText("");
        List<Vaca> vacas = vacaServicio.obtenerTodasLasVacas();
        
        if (vacas.isEmpty()) {
            outputArea.append("No hay vacas registradas.\n");
        } else {
            outputArea.append(String.format("%-5s %-15s %-5s %-10s %-15s %-15s %-20s\n", 
                "ID", "Raza", "Edad", "Granja", "Salud", "Reprod.", "Genealogía"));
            outputArea.append("----------------------------------------------------------------------------\n");
            
            for (Vaca vaca : vacas) {
                outputArea.append(String.format("%-5d %-15s %-5d %-10d %-15s %-15s %-20s\n",
                    vaca.getId(),
                    vaca.getRaza(),
                    vaca.getEdad(),
                    vaca.getGranjaId(),
                    vaca.getEstadoSalud(),
                    vaca.getEstadoReproductivo(),
                    vaca.getGenealogia()
                ));
            }
        }
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(frame, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
private void actualizarEstadoSalud() {
    List<Vaca> vacas = vacaServicio.obtenerTodasLasVacas();
    if (vacas.isEmpty()) {
        showError("No hay vacas registradas");
        return;
    }

    // Seleccionar vaca
    String[] ids = vacas.stream().map(v -> String.valueOf(v.getId())).toArray(String[]::new);
    String selectedId = (String) JOptionPane.showInputDialog(frame, 
            "Seleccione Vaca:", "Actualizar Estado de Salud", 
            JOptionPane.QUESTION_MESSAGE, null, ids, ids[0]);

    if (selectedId != null) {
        // Seleccionar estado de salud
        Vaca.EstadoSalud[] estados = Vaca.EstadoSalud.values();
        Vaca.EstadoSalud selectedEstado = (Vaca.EstadoSalud) JOptionPane.showInputDialog(frame, 
                "Seleccione estado:", "Estado de Salud", 
                JOptionPane.QUESTION_MESSAGE, null, estados, estados[0]);

        if (selectedEstado != null) {
            // Pedir descripción y tratamiento
            JPanel panel = new JPanel(new GridLayout(3, 1));
            JTextField descripcionField = new JTextField();
            JTextField tratamientoField = new JTextField();

            panel.add(new JLabel("Descripción:"));
            panel.add(descripcionField);
            panel.add(new JLabel("Tratamiento recomendado:"));
            panel.add(tratamientoField);

            int result = JOptionPane.showConfirmDialog(frame, panel, 
                    "Detalles de Salud", JOptionPane.OK_CANCEL_OPTION);

            if (result == JOptionPane.OK_OPTION) {
                int idVaca = Integer.parseInt(selectedId);
                vacaServicio.actualizarEstadoSalud(
                        idVaca, 
                        selectedEstado, 
                        descripcionField.getText(), 
                        tratamientoField.getText());
                outputArea.append("Estado de salud actualizado para vaca ID=" + idVaca + "\n");
            }
        }
    }
}

private void buscarVacaPorId() {
    String idStr = JOptionPane.showInputDialog(frame, 
            "Ingrese ID de la vaca:", "Buscar Vaca", 
            JOptionPane.QUESTION_MESSAGE);

    if (idStr != null && !idStr.trim().isEmpty()) {
        try {
            int id = Integer.parseInt(idStr);
            Optional<Vaca> vacaOpt = vacaServicio.obtenerPorId(id);
            
            if (vacaOpt.isPresent()) {
                Vaca vaca = vacaOpt.get();
                outputArea.setText(""); // Limpiar área
                outputArea.append("=== Información Detallada ===\n");
                outputArea.append(String.format(
                    "ID: %d\nRaza: %s\nEdad: %d meses\nGranja ID: %d\n" +
                    "Estado de salud: %s\nEstado reproductivo: %s\n" +
                    "Genealogía: %s\n",
                    vaca.getId(), vaca.getRaza(), vaca.getEdad(), vaca.getGranjaId(),
                    vaca.getEstadoSalud(), vaca.getEstadoReproductivo(), vaca.getGenealogia()
                ));
                
                // Aquí puedes agregar más detalles si los tienes en tu clase Vaca
            } else {
                showError("No se encontró vaca con ID: " + id);
            }
        } catch (NumberFormatException ex) {
            showError("El ID debe ser un número válido");
        }
    }
}
}