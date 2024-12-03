import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * This is the secondary window where teachers assign grades on students.
 */
public class VentanaNota extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField txtAlumno;
    private JLabel lblNota;
    private JTextField txtNota;
    private JButton btnAceptar;
    private Callback callback;

    public VentanaNota() {

        setTitle("Evaluación");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            SwingUtilities.updateComponentTreeUI(this);
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
                 UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        setBounds(100, 100, 272, 186);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblAlumno = new JLabel("Alumno");
        lblAlumno.setBounds(10, 10, 68, 13);
        contentPane.add(lblAlumno);

        txtAlumno = new JTextField();
        txtAlumno.setEditable(false);
        txtAlumno.setBounds(10, 33, 238, 19);
        contentPane.add(txtAlumno);
        txtAlumno.setColumns(10);

        lblNota = new JLabel("Nota");
        lblNota.setBounds(10, 62, 68, 13);
        contentPane.add(lblNota);

        txtNota = new JTextField();
        txtNota.setColumns(10);
        txtNota.setBounds(10, 85, 238, 19);
        contentPane.add(txtNota);

        btnAceptar = new JButton("Aceptar");
        btnAceptar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                devolverNota();
            }
        });
        btnAceptar.setBounds(10, 114, 85, 21);
        contentPane.add(btnAceptar);

        // Configuraciones propias.
        this.getRootPane().setDefaultButton(btnAceptar);
        setVisible(true);
        txtNota.requestFocus();

    }

    public void setAlumno(Alumno alumno) {
        txtAlumno.setText(alumno.getNombre().toUpperCase());
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    /**
     * This method is used to return the grade of a student. It retrieves the grade
     * from the txtNota text field, validates it, and if valid, passes it to the
     * callback interface and closes the window. If the grade is not valid, it shows
     * an error message.
     */
    public void devolverNota() {

        try {
            // Retrieve the grade from the txtNota text field.
            int nota = Integer.parseInt(txtNota.getText());

            // Validate the grade to ensure it's a number between 0 and 10.
            if (nota >= 0 && nota <= 10) {

                // If the grade is valid, pass it to the callback interface.
                callback.actualizarVentana(nota);

                // Close the window.
                dispose();

            } else {
                // If the grade is not valid, show an error message.
                JOptionPane.showMessageDialog(null, "Número de nota no válido.", "Boletín App", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            // If the grade is not an integer, show an error message.
            JOptionPane.showMessageDialog(null, "Número de nota debe ser un entero.", "Boletín App", JOptionPane.ERROR_MESSAGE);
        }

    }

}
