import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.sql.*;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class my_fans implements ActionListener{
	static Frame frame;
	static JPanel panel;
	static JTextArea title;
	static JTextField name,email;
	static JPasswordField password;
	static JButton btn;

	public my_fans() {
		// TODO Auto-generated constructor stub
	}
	


	public static void main(String[] args) {
		frame = new JFrame("Database sample");
		frame.setSize(600, 300);
		((JFrame) frame).setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		panel = new JPanel();
		frame.add(panel);
		placeComponents(panel);
		frame.setVisible(true);
		btn.requestFocusInWindow();

	}
	
	private static void placeComponents(JPanel panel){
		my_fans CS = new my_fans();
		panel.setLayout(null);
		
		title = new JTextArea("Database Sample project");
		title.setBounds(160,0,300,30);
		Font labelFont = title.getFont();
        title.setEditable(false);
		title.setFont(new Font(labelFont.getName(), Font.PLAIN, 20));
		
		name = new JTextField("Enter your name here");
		name.setBounds(160,40,300,30);
		name.setForeground(Color.GRAY); //placeholder
		name.addFocusListener(new FocusListener() {
			public void focusGained(FocusEvent e) {
				if (name.getText().equals("Enter your name here")) {
			            name.setText("");
			            name.setForeground(Color.BLACK); // set the color of the regular text
			        }
			 }
			 public void focusLost(FocusEvent e) {
			     if (name.getText().isEmpty()) {
			            name.setForeground(Color.GRAY);
			            name.setText("Enter your name here");
			        }
			    }
			});


		
		 
		email = new JTextField("Enter your email");
		email.setBounds(160,70,300,30);
		email.setForeground(Color.GRAY); //placeholder
		email.addFocusListener(new FocusListener() {
			public void focusGained(FocusEvent e) {
				if (email.getText().equals("Enter your email")) {
						email.setText("");
						email.setForeground(Color.BLACK); // set the color of the regular text
			        }
			 }
			 public void focusLost(FocusEvent e) {
			     if (email.getText().isEmpty()) {
			    	 	email.setForeground(Color.GRAY);
			    	 	email.setText("Enter your email");
			        }
			    }
			});
		
		
		password = new JPasswordField("password");
		password.setBounds(160,100,300,30);
		password.addFocusListener(new FocusListener() {
			public void focusGained(FocusEvent e) {
						password.setText("");
			}
			public void focusLost(FocusEvent e) {}//noting
			});

		
		btn = new JButton("Sign up");
		btn.addActionListener(CS);
		btn.setBounds(150,240,300,25);


		panel.add(title);
		panel.add(btn);
		panel.add(name);
		panel.add(password);
		panel.add(email);
	}
	

	public void actionPerformed(ActionEvent evt){
		Connection con=null;
		if(evt.getSource().equals(btn)) {
			try {
				//fill your database credentials here.
	            Class.forName("com.mysql.cj.jdbc.Driver");
	            con = DriverManager.getConnection(
	            	"[HOST]",
	            	"[USERNAME]",
	            	"[PASSWORD]"); 
	            insertData(con);
	            showMyFans(con);
			}
			catch(Exception e) {
	            e.printStackTrace();
	        }
			finally {
		        // Close the statement and connection
		        try {
		        	if (con != null) con.close();
		        }
		        catch (SQLException e) {
		            e.printStackTrace();
		        }
			}
		}
	}
	
	private void insertData(Connection c) {
		// Execute the INSERT statement
		try {
        String sql = "INSERT INTO my_fans (name,email,password) VALUES(?,?,?)";
        PreparedStatement preparedStmt = c.prepareStatement(sql);
        preparedStmt.setString (1, name.getText());
        preparedStmt.setString (2, email.getText());
        preparedStmt.setString(3, password.getText());
        preparedStmt.execute();
        System.out.println("Executed");
		} 
		catch (Exception e) {
			e.printStackTrace();
    	} 
	}
	
	public void showMyFans(Connection c) {
		String sql = "SELECT * FROM my_fans";
		try {
        PreparedStatement preparedStmt = c.prepareStatement(sql);
		ResultSet rs = preparedStmt.executeQuery();

        // Loop through the result set
		String str="";
        while (rs.next()) {
           str+="Name: "+rs.getString("name") + "  , Email: " + rs.getString("email")+"\n";
           
        }
     // Clear the frame and present all the fans on the screen.
        panel.removeAll();
        panel.revalidate();
        panel.repaint();
        title.setBounds(0, 0, 590, 400);
        title.setText(str);
        title.setEditable(false);
        panel.add(title);
		}
		catch (Exception e) {
	        e.printStackTrace();

		}
	}
}
