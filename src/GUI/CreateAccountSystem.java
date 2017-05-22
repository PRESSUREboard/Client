package GUI;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SpringLayout;


import Database.Insert;
import GUIElements.CancelButton;
import Main.Main;
import Main.SystemAccount;

@SuppressWarnings("serial")
public class CreateAccountSystem extends JPanel{
	
	private JLabel idLabel;
	private JLabel passwordLabel1;
	private JLabel passwordLabel2;
	
	private JTextField idField;
	private JPasswordField passwordField1;
	private JPasswordField passwordField2;
	
	private JButton connexion;
	
	private MenuGUI frame;
	
	// variable verifiant la correspondance des mots de passe
		boolean psswdMatch;
	
	public CreateAccountSystem (final MenuGUI f){
		this.frame = f;
		setBackground(Color.DARK_GRAY);
		SpringLayout layout = f.layout;
		setLayout(layout);
		
		idLabel = new JLabel ("Id :");
		idLabel.setForeground(Color.WHITE);
		passwordLabel1 = new JLabel ("Password");
		passwordLabel1.setForeground(Color.WHITE);
		passwordLabel2 = new JLabel ("Password");
		passwordLabel2.setForeground(Color.WHITE);
		
		this.add(idLabel);
		this.add(passwordLabel1);
		this.add(passwordLabel2);
		
		idField = new JTextField();
		passwordField1 = new JPasswordField("", 15);
		passwordField2 = new JPasswordField("", 15);
		
		this.add(idField);
		this.add(passwordField1);
		this.add(passwordField2);
		
		connexion = new JButton ("Créer");
		connexion.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				psswdMatch = Main.passwordMatch(passwordField1.getPassword(), passwordField2.getPassword())	;
				String pswd = new String(passwordField1.getPassword());   
				if (psswdMatch == true && (pswd.equals("")==false)){   //on verifie que les mdp sont les memes et qu'ils sont non nuls
					java.sql.Connection conn = null;
					try {
						conn = DriverManager.getConnection("jdbc:mysql://5.196.123.198:3306/" + "P2I", "G222_B", "G222_B");
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					Insert.addCompteSystem(idField.getText(), pswd, conn);
					Main.currentSystemAccount = new SystemAccount(idField.getText());
					frame.hideCreateAccountSystem();
					frame.showMenuPane();
				}else{
					JOptionPane.showMessageDialog(null, "Your passwords are diferent, try again");
					passwordField1.setText("");
					passwordField2.setText("");
					
				}
			}
		});
		
		this.add(connexion);
		
		CancelButton cancel = new CancelButton(frame.getFirstPanel(),this);
		this.add(cancel);
		
		//on place les elements dans la fenetre
		layout.putConstraint(SpringLayout.NORTH, idLabel, 10, SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.WEST, idLabel, 10, SpringLayout.WEST, this);
		
		layout.putConstraint(SpringLayout.NORTH, passwordLabel1, 15, SpringLayout.SOUTH, idLabel);
		layout.putConstraint(SpringLayout.WEST, passwordLabel1, 10, SpringLayout.WEST, this);
		
		layout.putConstraint(SpringLayout.NORTH, passwordLabel2, 15, SpringLayout.SOUTH, passwordLabel1);
		layout.putConstraint(SpringLayout.WEST, passwordLabel2, 10, SpringLayout.WEST, this);
		
		layout.putConstraint(SpringLayout.NORTH, idField, 10, SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.EAST, idField, 10, SpringLayout.EAST, this);
		layout.putConstraint(SpringLayout.WEST, idField, 10, SpringLayout.EAST, passwordLabel1);
		
		layout.putConstraint(SpringLayout.NORTH, passwordField1, 10, SpringLayout.SOUTH, idField);
		layout.putConstraint(SpringLayout.EAST, passwordField1, 10, SpringLayout.EAST, this);
		layout.putConstraint(SpringLayout.WEST, passwordField1, 0, SpringLayout.WEST, idField);
		
		layout.putConstraint(SpringLayout.NORTH, passwordField2, 10, SpringLayout.SOUTH, passwordField1);
		layout.putConstraint(SpringLayout.WEST, passwordField2, 0, SpringLayout.WEST, passwordField1);
		layout.putConstraint(SpringLayout.EAST, passwordField2, 10, SpringLayout.EAST, this);
		
		layout.putConstraint(SpringLayout.NORTH, connexion, 10, SpringLayout.SOUTH, passwordField2);
		layout.putConstraint(SpringLayout.SOUTH, connexion, -10, SpringLayout.SOUTH, this);
		layout.putConstraint(SpringLayout.EAST, connexion, -20, SpringLayout.HORIZONTAL_CENTER, this);
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, connexion, -frame.getWidth()/4, SpringLayout.HORIZONTAL_CENTER, this);

		layout.putConstraint(SpringLayout.NORTH, cancel, 10, SpringLayout.SOUTH, passwordField2);
		layout.putConstraint(SpringLayout.SOUTH, cancel, -10, SpringLayout.SOUTH, this);
		layout.putConstraint(SpringLayout.WEST, cancel, 20, SpringLayout.HORIZONTAL_CENTER, this);
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, cancel, frame.getWidth()/4, SpringLayout.HORIZONTAL_CENTER, this);

		
		
		
		setVisible(false);
		
	}
	

}