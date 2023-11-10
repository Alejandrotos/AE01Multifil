package ae01;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Order extends JFrame {

    private JTextField textFieldTipus, TextFieldQuantitat, TextFieldNom;
    private JButton btnEjecutar;

    public Order() {
        // Configuración de la interfaz gráfica
        setTitle("Order Tetrominos");
        getContentPane().setLayout(new GridLayout(4, 2, 10, 10));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        getContentPane().add(new JLabel("Tipus:"));
        textFieldTipus = new JTextField();
        getContentPane().add(textFieldTipus);

        getContentPane().add(new JLabel("Quantitat:"));
        TextFieldQuantitat = new JTextField();
        getContentPane().add(TextFieldQuantitat);

        getContentPane().add(new JLabel("Nom de l'arxiu (opcional):"));
        TextFieldNom = new JTextField();
        getContentPane().add(TextFieldNom);

        btnEjecutar = new JButton("Ejecutar");
        getContentPane().add(btnEjecutar);

        btnEjecutar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String type = textFieldTipus.getText();
                int quantity = Integer.parseInt(TextFieldQuantitat.getText());
                String filename = TextFieldNom.getText();

                // Llamada a la clase Manufacture para fabricar las piezas
                Manufacture manufacture = new Manufacture(type, quantity, filename);
                manufacture.startManufacturing();
            }
        });

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Order());
    }
}
