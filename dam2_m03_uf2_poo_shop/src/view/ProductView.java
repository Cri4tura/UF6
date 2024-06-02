package view;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JOptionPane;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import main.Shop;
import model.Product;

public class ProductView extends JDialog implements ActionListener {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField productNameField;
    private JTextField stockField;
    private JTextField priceField;
    private String option;
    private Shop shop;

    public ProductView(String option, Shop shop) {
        this.option = option;
        this.shop = shop;
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblProductName = new JLabel("Nombre producto:");
        lblProductName.setBounds(29, 60, 121, 20);
        contentPane.add(lblProductName);

        productNameField = new JTextField();
        productNameField.setBounds(150, 60, 200, 20);
        contentPane.add(productNameField);
        productNameField.setColumns(10);

        JLabel lblStock = new JLabel("Stock:");
        lblStock.setBounds(50, 100, 100, 20);
        contentPane.add(lblStock);

        stockField = new JTextField();
        stockField.setBounds(150, 100, 200, 20);
        contentPane.add(stockField);
        stockField.setColumns(10);

        JLabel lblPrice = new JLabel("Precio:");
        lblPrice.setBounds(50, 140, 100, 20);
        contentPane.add(lblPrice);

        priceField = new JTextField();
        priceField.setBounds(150, 140, 200, 20);
        contentPane.add(priceField);
        priceField.setColumns(10);

        JButton btnOk = new JButton("OK");
        btnOk.setBounds(150, 200, 89, 23);
        btnOk.addActionListener(this);
        contentPane.add(btnOk);

        JButton btnCancel = new JButton("Cancel");
        btnCancel.setBounds(250, 200, 89, 23);
        btnCancel.addActionListener(this);
        contentPane.add(btnCancel);

        if (option.equals("2")) { 
            lblPrice.setVisible(true);
            priceField.setVisible(true);
        } else {
            lblPrice.setVisible(false);
            priceField.setVisible(false);
        }
        
        if (option.equals("9")) { 
        	lblStock.setVisible(false);
        	stockField.setVisible(false);
        } 
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("OK")) {
            String productName = productNameField.getText();
            boolean result = false;

            switch (option) {
                case "2":
                	int stockToAdd = Integer.parseInt(stockField.getText());
                    double price = Double.parseDouble(priceField.getText());
                    result = shop.addProduct(productName, stockToAdd, price);
                    Product existingProduct = shop.findProduct(productName);
                    if (existingProduct != null) {
                        dispose();
                    } else {
                        result = shop.addProduct(productName, stockToAdd, price);
                    }
                    break;
                case "3":
                	int stock = Integer.parseInt(stockField.getText());
                    result = shop.addStock(productName, stock);
                    break;
                case "9":
//                    result = shop.deleteProduct(productName);
                    break;
                default:
                    break;
            }

            if (result) {
                JOptionPane.showMessageDialog(this, "La operación se ha realizado con éxito.");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Error en la operación. Por favor, vuelve a intentarlo.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else if (e.getActionCommand().equals("Cancel")) {
            dispose();
        }
    }
}
