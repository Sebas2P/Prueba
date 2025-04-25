package Views;

import Models.Jugador;
import Models.Sistema;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random; // Import necesario para generar números aleatorios
import java.util.ArrayList;
import java.util.List;
import javax.swing.JTabbedPane; // Import necesario para pestañas

public class Interfaz extends JFrame {
    private Sistema sistema;
    private DefaultTableModel tableModel;
    private DefaultTableModel emparejamientosTableModel;
    private DefaultTableModel historialTableModel; // Modelo para la tabla del historial
    private int numeroTorneo = 1; // Contador para el número del torneo
    private List<String[][]> historialTorneos = new ArrayList<>(); // Lista para almacenar los emparejamientos de cada torneo

    public Interfaz() {
        sistema = new Sistema();
        setTitle("Gestión de Jugadores");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel superior con botón y campo de texto
        JPanel panelSuperior = new JPanel();
        panelSuperior.setLayout(new FlowLayout());

        JTextField nombreField = new JTextField(20);
        JButton agregarButton = new JButton("Agregar Jugador");

        panelSuperior.add(new JLabel("Nombre:"));
        panelSuperior.add(nombreField);
        panelSuperior.add(agregarButton);

        // Tabla para mostrar jugadores
        tableModel = new DefaultTableModel(new String[]{"Jugadores"}, 0);
        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        // Panel inferior con botones para generar emparejamientos y ver historial
        JPanel panelInferior = new JPanel();
        panelInferior.setLayout(new FlowLayout());

        JButton emparejarButton = new JButton("Generar Emparejamientos");
        panelInferior.add(emparejarButton);

        // Crear un panel de pestañas
        JTabbedPane tabbedPane = new JTabbedPane();

        // Panel principal (gestión de jugadores)
        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.add(panelSuperior, BorderLayout.NORTH);
        panelPrincipal.add(scrollPane, BorderLayout.CENTER);
        panelPrincipal.add(panelInferior, BorderLayout.SOUTH);
        tabbedPane.addTab("Gestión de Jugadores", panelPrincipal);

        // Panel de historial de torneos
        JPanel panelHistorial = new JPanel(new BorderLayout());
        historialTableModel = new DefaultTableModel(new String[]{"Torneo", "Jugador 1", "Jugador 2"}, 0);
        JTable historialTable = new JTable(historialTableModel);
        JScrollPane historialScrollPane = new JScrollPane(historialTable);
        panelHistorial.add(historialScrollPane, BorderLayout.CENTER);
        tabbedPane.addTab("Historial de Torneos", panelHistorial);

        // Agregar las pestañas al marco principal
        add(tabbedPane, BorderLayout.CENTER);

        // Tabla para mostrar emparejamientos
        emparejamientosTableModel = new DefaultTableModel(new String[]{"Jugador 1", "Jugador 2"}, 0);
        JTable emparejamientosTable = new JTable(emparejamientosTableModel);
        JScrollPane emparejamientosScrollPane = new JScrollPane(emparejamientosTable);

        add(emparejamientosScrollPane, BorderLayout.EAST);

        // Acción del botón
        agregarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombre = nombreField.getText().trim();
                if (!nombre.isEmpty()) {
                    Jugador nuevoJugador = sistema.agregarJugador(nombre);
                    if (nuevoJugador != null) {
                        actualizarTabla();
                        nombreField.setText("");
                        JOptionPane.showMessageDialog(null, "Jugador agregado: " + nuevoJugador.getNombre(), "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, "No se pudo agregar el jugador.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "El nombre no puede estar vacío.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Acción del botón para generar emparejamientos
        emparejarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generarEmparejamientos();
                mostrarEmparejamientosEnCuadro();
                actualizarHistorialTabla(); // Actualizar la tabla del historial
            }
        });

        setVisible(true);
    }

    private void actualizarTabla() {
        tableModel.setRowCount(0); // Limpiar la tabla
        String[] jugadores = sistema.mostrarJugadores();
        for (String jugador : jugadores) {
            if (jugador != null) {
                tableModel.addRow(new Object[]{jugador});
            }
        }
    }

    private void generarEmparejamientos() {
        emparejamientosTableModel.setRowCount(0); // Limpiar la tabla de emparejamientos
        String[][] emparejamientos = sistema.emparejarJugadores();
        if (emparejamientos.length > 0) {
            historialTorneos.add(emparejamientos); // Guardar los emparejamientos en el historial
        }
        for (String[] pareja : emparejamientos) {
            if (pareja[0] != null && pareja[1] != null) {
                emparejamientosTableModel.addRow(pareja);
            }
        }
        actualizarTabla(); // Actualizar la tabla de jugadores para reflejar los jugadores restantes
    }

    private void mostrarEmparejamientosEnCuadro() {
        if (historialTorneos.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay suficientes jugadores para emparejar.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String[][] emparejamientos = historialTorneos.get(historialTorneos.size() - 1); // Último torneo
        StringBuilder mensaje = new StringBuilder("Torneo #" + numeroTorneo + "\n\nEmparejamientos:\n");
        for (String[] pareja : emparejamientos) {
            if (pareja[0] != null && pareja[1] != null) {
                mensaje.append(pareja[0]).append(" vs ").append(pareja[1]).append("\n");
            }
        }

        JOptionPane.showMessageDialog(null, mensaje.toString(), "Emparejamientos del Torneo", JOptionPane.INFORMATION_MESSAGE);
        numeroTorneo++; // Incrementar el número del torneo
    }

    private void actualizarHistorialTabla() {
        historialTableModel.setRowCount(0); // Limpiar la tabla del historial
        for (int i = 0; i < historialTorneos.size(); i++) {
            String[][] emparejamientos = historialTorneos.get(i);
            for (String[] pareja : emparejamientos) {
                if (pareja[0] != null && pareja[1] != null) {
                    historialTableModel.addRow(new Object[]{"Torneo #" + (i + 1), pareja[0], pareja[1]});
                }
            }
        }
    }

    public static void main(String[] args) {
        new Interfaz();
    }
}

