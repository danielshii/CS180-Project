import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 *
 * @author samrothschild
 */
public class loginClient extends JComponent implements Runnable {
    public ArrayList<User> userDatabase = new ArrayList<User>();  // current user ID
    public ArrayList<String> usernameDatabase = new ArrayList<String>();  // username database
    JTextField username;             // username field
    JTextField password;             // password
    JButton login;                   // login button
    JButton createAcct;              // button to create account
    
    
    
    
    public loginClient() {

    }
    ActionListener actionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            
            // logging in
            if (e.getSource() == login) {
                User checkUser = new User(username.getText(), password.getText());
                // valid user
                if (userDatabase.contains(checkUser)) {
                    JOptionPane.showMessageDialog(null, "welcome back. \nlogging in...", "please wait",
                        JOptionPane.ERROR_MESSAGE);
                // invalid user
                } else {
                    JOptionPane.showMessageDialog(null, "user not found! make sure credentials are correct", "Error",
                        JOptionPane.ERROR_MESSAGE);
                }
                login();
            }
            
            // creating account
            if (e.getSource() == createAcct) {
                // account exists
                int yesNo;
                String newUsername;  // new username
                String newPassword;  // new password
                
                // making username
                do {
                    do {
                        do {
                            newUsername = JOptionPane.showInputDialog(null, "Choose a username:",
                                "New Account", JOptionPane.PLAIN_MESSAGE);
                            if (newUsername.equals("")) {
                                JOptionPane.showMessageDialog(null, "Please enter a username."
                                , "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        } while (newUsername.equals(""));
                            yesNo = JOptionPane.showConfirmDialog(null, "Your username: " + newUsername 
                                + ".\nSelect yes to confirm. \nSelect no to create new username.", "New Account",
                                JOptionPane.YES_NO_OPTION);
                    } while (yesNo == JOptionPane.NO_OPTION);
                    if (usernameDatabase.contains(newUsername)) { 
                        JOptionPane.showMessageDialog(null, "This username already exists! Try a new username"
                            , "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } while (usernameDatabase.contains(newUsername));
                                
                // making password
                do {
                    do {
                        newPassword = JOptionPane.showInputDialog(null, "Create a password:",
                        "New Account", JOptionPane.QUESTION_MESSAGE);
                        if (newPassword.equals("")) {
                            JOptionPane.showMessageDialog(null, "Please enter a password."
                                , "Error", JOptionPane.ERROR_MESSAGE);                        
                        }
                    } while (newPassword.equals(""));
                    yesNo = JOptionPane.showConfirmDialog(null, "Your password: " + newPassword 
                        + ". \nYes to confirm. \nNo to choose new password", "New Account",
                        JOptionPane.YES_NO_OPTION);
                } while (yesNo == JOptionPane.NO_OPTION);                               
                userDatabase.add(new User(newUsername, newPassword));
                JOptionPane.showMessageDialog(null, newUsername + ", welcome to The Cool People Network.", "New Account",
                            JOptionPane.PLAIN_MESSAGE);                               
                login();
            }  
        }
    };
                
    public void login() {
        
    }
    
    @Override
    public void run() {
        JFrame frame = new JFrame("The Cool People Network");               
        Container content = frame.getContentPane();
        content.setLayout(new BorderLayout());        
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        
        createAcct = new JButton("Create Account");
        createAcct.addActionListener(actionListener);
        login = new JButton("Sign In");
        login.addActionListener(actionListener);
        username = new JTextField(35);
        password = new JTextField(35);
        
        JPanel panel0 = new JPanel(new GridLayout(3, 1, 0, 0));
        panel0.add(new JLabel(""));
        panel0.add(new JLabel(""));
        panel0.add(new JLabel("                                "
            + "                              Cool People Only"));
        
        JPanel panel = new JPanel(new GridLayout(5, 5, 0, 20));            
        panel.add(new JLabel(""));
        panel.add(new JLabel(""));
        panel.add(new JLabel(""));
        panel.add(new JLabel(""));
        panel.add(new JLabel(""));     
        panel.add(new JLabel(""));   
        panel.add(new JLabel("      username:"));  
        panel.add(username);
        panel.add(new JLabel(""));
        panel.add(new JLabel(""));  
        panel.add(new JLabel(""));  
        panel.add(new JLabel("      password:"));
        panel.add(password);        
        panel.add(new JLabel("")); 
        panel.add(new JLabel(""));
        panel.add(new JLabel(""));
        panel.add(new JLabel("")); 
        panel.add(login);
        panel.add(new JLabel(""));
        panel.add(new JLabel(""));
        panel.add(new JLabel(""));
        panel.add(new JLabel(""));
        panel.add(createAcct); 
        
        JPanel panel1 = new JPanel(new GridLayout(3, 1, 0, 50));
        panel1.add(new JLabel(""));
        panel1.add(new JLabel(""));
        
        frame.add(panel0, BorderLayout.NORTH);
        frame.add(panel, BorderLayout.CENTER);
        frame.add(panel1, BorderLayout.SOUTH);          
    }
    
    
    public static void main(String[] args) throws IOException {
        
        SwingUtilities.invokeLater(new loginClient());
    }
}
