package view;

import java.awt.BorderLayout;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import main.Shop;

public class CashView extends JDialog {

    private static final long serialVersionUID = 1L;
    private final JPanel contentPanel = new JPanel();
    private JTextField textField;

    public CashView(JFrame parent, Shop shop) {
        super(parent, true);
        setTitle("Dinero en caja");
        setBounds(100, 100, 450, 300);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(null);

        JLabel lblNewLabel = new JLabel("Dinero en caja:");
        lblNewLabel.setBounds(121, 69, 144, 14);
        contentPanel.add(lblNewLabel);

        textField = new JTextField();
        textField.setEditable(false);
        textField.setBounds(179, 94, 128, 28);
        textField.setText(String.valueOf(shop.showCash()) + "€");
        contentPanel.add(textField);
        textField.setColumns(10);
    }
}
