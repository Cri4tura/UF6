package view;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import model.Employee;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JOptionPane;

public class LoginView extends JFrame implements ActionListener{

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private int attemptCount = 0;
	private static final int MAX_ATTEMPTS = 3;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginView login = new LoginView();
					login.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public LoginView() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnNewButton = new JButton("Acceder");
		btnNewButton.setBounds(319, 227, 89, 23);
		contentPane.add(btnNewButton);
		
		JLabel lblNewLabel = new JLabel("Número de empleado:");
		lblNewLabel.setBounds(75, 34, 147, 14);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Password:");
		lblNewLabel_1.setBounds(75, 123, 130, 14);
		contentPane.add(lblNewLabel_1);
		
		textField = new JTextField();
		textField.setBounds(75, 59, 210, 23);
		contentPane.add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setBounds(75, 148, 210, 23);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
		
		btnNewButton.addActionListener(this);
}
	
	@Override
    public void actionPerformed(ActionEvent e) {
        String employeeID = textField.getText();
        String password = textField_1.getText();

        try {
            int ID = Integer.parseInt(employeeID);
            Employee employee = new Employee("Martina");

            boolean logged = employee.login(ID, password);

            if (logged) {
                this.setVisible(false);
                ShopView shop = new ShopView();
                shop.setVisible(true);
            } else {
                attemptCount++;
                if (attemptCount >= MAX_ATTEMPTS) {
                    JOptionPane.showMessageDialog(contentPane, "Se ha alcanzado el número máximo de intentos", "Error", JOptionPane.ERROR_MESSAGE);
                    System.exit(0);
                } else {
                    JOptionPane.showMessageDialog(contentPane, "Usuario o password incorrectos", "Error", JOptionPane.ERROR_MESSAGE);
                    textField.setText("");
                    textField_1.setText("");
                }
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(contentPane, "Por favor, ingrese un número de empleado válido", "Error", JOptionPane.ERROR_MESSAGE);
            textField.setText("");
        }
    }
}