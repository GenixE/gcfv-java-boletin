import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;

/**
 * This is a program where teachers import students from a .txt file, to assign
 * grades on students on a given subject. After that, teachers can save(export)
 * the assignations on a .txt file.
 *
 * @author gcfv
 * @version 1.0
 * @since May 26, 2024
 */
public class Main extends JFrame {

    private List<Alumno> alumnos;

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField txtAsignatura;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Main frame = new Main();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public Main() {

        setTitle("Boletín App");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 546, 441);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);
        setLocationRelativeTo(null);

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            SwingUtilities.updateComponentTreeUI(this);
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
                 UnsupportedLookAndFeelException e) {
            JOptionPane.showMessageDialog(this, "Error al cambiar el look and feel de la aplicación.", "Boletín App", JOptionPane.ERROR_MESSAGE);
        }

        JLabel lblAsignatura = new JLabel("Asignatura");
        lblAsignatura.setBounds(10, 10, 100, 13);
        contentPane.add(lblAsignatura);

        txtAsignatura = new JTextField();
        txtAsignatura.setBounds(10, 33, 164, 19);
        contentPane.add(txtAsignatura);
        txtAsignatura.setColumns(10);

        JLabel lblAlumnos = new JLabel("Alumnos");
        lblAlumnos.setBounds(10, 62, 60, 13);
        contentPane.add(lblAlumnos);

        JScrollPane panelAlumnos = new JScrollPane();
        panelAlumnos.setBounds(10, 85, 508, 278);
        contentPane.add(panelAlumnos);

        DefaultListModel<Alumno> modelo = new DefaultListModel<>();
        JList<Alumno> lstAlumnos = new JList<>(modelo);
        lstAlumnos.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    abrirFichaAlumno(lstAlumnos);
                }
            }
        });
        panelAlumnos.setViewportView(lstAlumnos);

        alumnos = new ArrayList<>();

        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                guardarBoletin("ficheros/boletin.txt");
            }
        });
        btnGuardar.setBounds(142, 373, 115, 21);
        contentPane.add(btnGuardar);

        JButton btnImportarAlumnos = new JButton("Importar alumnos");
        btnImportarAlumnos.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                importarAlumnos("ficheros/alumnos.txt");
                mostrarAlumnos(modelo);
            }
        });
        btnImportarAlumnos.setBounds(10, 373, 115, 21);
        contentPane.add(btnImportarAlumnos);

    }

    /**
     * This method is used to import students from a file.
     *
     * @param rutaArchivo The path of the file to import students from.
     */
    private void importarAlumnos(String rutaArchivo) {
        // Create a File object with the given file path
        File fileAlumnos = new File(rutaArchivo);
        // Check if the file exists
        if (fileAlumnos.exists()) {
            // Try to read the file
            try (BufferedReader br = new BufferedReader(new FileReader(fileAlumnos))) {
                String linea;
                // Read the course name from the file
                String curso = br.readLine();
                // Show a message dialog with the course name
                JOptionPane.showMessageDialog(this, "Se van a importar los alumnos de " + curso + ".", "Boletín App", JOptionPane.INFORMATION_MESSAGE);
                // Skip the 2nd line
                linea = br.readLine();
                // Clear the ArrayList of students
                alumnos.clear();
                // Iterate through the file and add each student to the ArrayList
                while ((linea = br.readLine()) != null) {
                    String[] partes = linea.split("\n");
                    String apellidosNombre = partes[0];
                    Alumno alumno = new Alumno(apellidosNombre, -1);
                    alumnos.add(alumno);
                }
                // Print each student to the console
                for (Alumno alumnoShow : alumnos) {
                    System.out.println(alumnoShow);
                }
            } catch (IOException ex) {
                // Show an error message if there was a problem reading the file
                JOptionPane.showMessageDialog(this, "Error al leer el fichero de alumnos.", "Boletín App", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            // Show an error message if the file was not found
            JOptionPane.showMessageDialog(this, "No se ha encontrado el fichero de alumnos.", "Boletín App", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * This method is used to display students in the UI.
     *
     * @param modelo The DefaultListModel to add students to.
     */
    private void mostrarAlumnos(DefaultListModel<Alumno> modelo) {
        // Clear the DefaultListModel
        modelo.clear();
        // Add each student to the DefaultListModel
        for (Alumno alumnoShow : alumnos) {
            modelo.addElement(alumnoShow);
        }
    }

    private VentanaNota ventanaNota;

    /**
     * This method is used to open the student's record.
     *
     * @param lstAlumnos The JList of students.
     */
    private void abrirFichaAlumno(JList<Alumno> lstAlumnos) {
        // Check if ventanaNota is null or not visible
        if (ventanaNota == null || !ventanaNota.isVisible()) {
            // Create a new VentanaNota
            ventanaNota = new VentanaNota();
            // Center the VentanaNota to the main window
            ventanaNota.setLocationRelativeTo(this);
            // Get the selected student from the JList
            Alumno alumno = lstAlumnos.getSelectedValue();
            // Set the student in the VentanaNota
            ventanaNota.setAlumno(alumno);
            // Set the callback in the VentanaNota
            ventanaNota.setCallback(new Callback() {
                // Implement the actualizarVentana method from the Callback interface
                @Override
                public void actualizarVentana(int nota) {
                    // Set the student's grade
                    alumno.setNota(nota);
                }
            });
        } else {
            // Bring the existing VentanaNota to the front
            ventanaNota.toFront();
        }
    }

    /**
     * This method is used to save the bulletin to a file.
     *
     * @param rutaArchivo The path of the file to save the bulletin to.
     */
    private void guardarBoletin(String rutaArchivo) {
        // Create a File object with the given file path
        File saveFile = new File(rutaArchivo);
        // Get the subject from the txtAsignatura text field
        String asignatura = txtAsignatura.getText();
        // Get the current date
        LocalDate fecha = LocalDate.now();
        // Check if the subject is not blank and the ArrayList of students is not empty
        if (!asignatura.isBlank() && !alumnos.isEmpty()) {
            // Try to write to the file
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(saveFile))) {
                // Write the subject and date to the file
                bw.write("asignatura=" + asignatura + "\n");
                bw.write("fecha=" + fecha + "\n\n");
                // Write each student's name and grade to the file
                for (Alumno alumno : alumnos) {
                    bw.write("nombre=" + alumno.getNombre() + "\n");
                    bw.write("nota=" + alumno.getNota() + "\n");
                }
                // Show a message dialog that the grades have been saved
                JOptionPane.showMessageDialog(this, "Se han guardado las notas de " + asignatura + ".", "Boletín App", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException e) {
                // Show an error message if there was a problem writing to the file
                JOptionPane.showMessageDialog(this, "Error al escibir el fichero de boletín.", "Boletín App", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            // Show an error message if the subject is blank or the ArrayList of students is
            // empty
            JOptionPane.showMessageDialog(this, "Faltan información del boletín. Por favor, rellénenselo.", "Boletín App", JOptionPane.ERROR_MESSAGE);
        }
    }

}
