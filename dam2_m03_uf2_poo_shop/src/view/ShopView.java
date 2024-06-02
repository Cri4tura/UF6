package view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import main.Shop;

public class ShopView extends JFrame implements ActionListener, KeyListener {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private Shop shop;

    public ShopView() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        
        JLabel lblNewLabel = new JLabel("Seleccione o pulse una opción: ");
        lblNewLabel.setBounds(119, 11, 200, 14);
        contentPane.add(lblNewLabel);
        
        shop = new Shop();
        shop.loadInventory();
        
        JButton btnNewButton = new JButton("1. Contar caja");
        btnNewButton.setBounds(119, 36, 200, 35);
        btnNewButton.addActionListener(this);
        contentPane.add(btnNewButton);
        
        JButton btnNewButton_1 = new JButton("2. Añadir producto");
        btnNewButton_1.setBounds(119, 82, 200, 35);
        btnNewButton_1.addActionListener(this);
        contentPane.add(btnNewButton_1);
        
        JButton btnNewButton_2 = new JButton("3. Añadir stock");
        btnNewButton_2.setBounds(119, 128, 200, 35);
        btnNewButton_2.addActionListener(this);
        contentPane.add(btnNewButton_2);
        
        JButton btnNewButton_3 = new JButton("9. Eliminar producto");
        btnNewButton_3.setBounds(119, 174, 200, 35);
        btnNewButton_3.addActionListener(this);
        contentPane.add(btnNewButton_3);

        addKeyListener(this);
        setFocusable(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        switch (command) {
            case "1. Contar caja":
                openCashView();
                break;
            case "2. Añadir producto":
            case "3. Añadir stock":
            case "9. Eliminar producto":
                openProductView(command.substring(0, 1));
                break;
            default:
                break;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();
        switch (keyCode) {
            case KeyEvent.VK_1:
                openCashView();
                break;
            case KeyEvent.VK_2:
            case KeyEvent.VK_3:
            case KeyEvent.VK_9:
                openProductView(String.valueOf(keyCode - KeyEvent.VK_0));
                break;
            default:
                break;
        }
    }

    private void openCashView() {
        CashView cashView = new CashView(this, shop);
        cashView.setVisible(true);
    }

    private void openProductView(String option) {
        ProductView productView = new ProductView(option, shop);
        productView.setVisible(true);
    }
}
