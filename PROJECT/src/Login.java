import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Scanner;


public class Login extends JFrame implements ActionListener {
    JButton signupButton, loginButton; //Defining globally to access them in all function
    JLabel username, password;
    JTextField usernameField,passField;
    boolean unameFlag = false, passFlag = false;


    Login(){
        setTitle("Online Banking System!");

        setLayout(null); //for custom layout

        //For Bg Image
        JLabel imageBg = null;
        try{
            ImageIcon imageIcon = new ImageIcon(ClassLoader.getSystemResource("bg3.png"));
            Image image = imageIcon.getImage().getScaledInstance(800,400, Image.SCALE_DEFAULT);
            ImageIcon imageIcon1 = new ImageIcon(image);
            imageBg = new JLabel(imageIcon1);
            imageBg.setBounds(0,-30,800,400);
            add(imageBg);
        }catch (Exception e){
            System.out.println(e);
        }

        JLabel heading = new JLabel("Welcome to EasyMoney!!");
        heading.setFont(new Font("Luckiest Guy",Font.PLAIN,30));
        heading.setBounds(200,50,500,40);
        imageBg.add(heading);

        //Username
        username = new JLabel("Username:");
        username.setFont(new Font("Helvetica",Font.PLAIN,20));
        username.setBounds(100,120,200,30);
        imageBg.add(username);

        usernameField = new JTextField();
        usernameField.setBounds(320,120,200,30);
        imageBg.add(usernameField);

        //Password
        password = new JLabel("Password:");
        password.setFont(new Font("Helvetica",Font.PLAIN,20));
        password.setBounds(100,160,200,30);
        imageBg.add(password);

        passField = new JTextField();
        passField.setBounds(320,160,200,30);
        imageBg.add(passField);

        //Login Button
        loginButton = new JButton("Login");
        loginButton.setBounds(320,200,100,40);
        loginButton.setBackground(Color.darkGray);
        loginButton.setForeground(Color.white);
        loginButton.addActionListener(this); //To call Action Perform class
        imageBg.add(loginButton);

        loginButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                loginButton.setBackground(Color.decode("#1c8550"));
                loginButton.setForeground(Color.white);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                loginButton.setBackground(Color.darkGray);
                loginButton.setForeground(Color.white);
            }
        });

        //Sign Up Button
        signupButton = new JButton("SignUp");
        signupButton.setBounds(430,200,100,40);
        signupButton.setBackground(Color.decode("#941304"));
        signupButton.setForeground(Color.white);
        signupButton.addActionListener(this);
        imageBg.add(signupButton);

        signupButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                signupButton.setBackground(Color.decode("#f2e7e6"));
                signupButton.setForeground(Color.BLACK);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                signupButton.setBackground(Color.decode("#941304"));
                signupButton.setForeground(Color.white);
            }
        });



        setSize(800,400);
        setVisible(true);
        setLocation(350,200);

    }

    //ActionButton
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==signupButton){
            setVisible(false);
            new Signup().setVisible(true);
        }
        else if (e.getSource()==loginButton){

            try{
                if(usernameField.getText().equals("")){
                    JOptionPane.showMessageDialog(null,"Username is Required");
                }

            }catch (Exception ae){
                System.out.println(e);
            }

            File userFile = new File(usernameField.getText().concat(".txt"));  //FILE
            BufferedReader fileReader = null;

            //checking username match
            try {
                fileReader = new BufferedReader(new FileReader(userFile));
                unameFlag = true;
            }
            catch (IOException ex){
                JOptionPane.showMessageDialog(null,"Username not Found!");
            }

            //checking Password match
            String passCheck = null;
            try {
                    for(int i =1; i<=3 ; i++){   //pass is in 3rd line
                        passCheck = fileReader.readLine();
                    }
                fileReader.close();

                    if(!passCheck.equals(passField.getText())){
                        passFlag = false;
                        throw new IOException();
                    }
                    passFlag = true;
            }

            catch (IOException ex){
                JOptionPane.showMessageDialog(null,"Password doesn't Match!");
            }

            //Assigning user info
            if(passFlag == true && unameFlag == true){
                try {

                    fileReader = new BufferedReader(new FileReader(userFile));
                    User.name = fileReader.readLine();
                    User.userName = fileReader.readLine();
                    User.setPassword(fileReader.readLine());

                    //reading last word balance
                    String balanceString = fileReader.readLine();
                    String lastWord = null;

                    while (balanceString != null){
                        String[] words = balanceString.split("\\s+"); //\\s+ as the delimiter, which matches one or more whitespace characters.
                        if(words.length > 0){
                            lastWord = words[words.length-1];
                        }
                        balanceString = fileReader.readLine();
                    }
                    User.balance = Double.parseDouble(lastWord);

                    JOptionPane.showMessageDialog(null,User.name + User.balance);

                    fileReader.close();

                    setVisible(false);
                    new Dashboard().setVisible(true);
                }
                catch (Exception ex){
                    JOptionPane.showMessageDialog(null,"Error");
                }

            }

        }

    }

    public static void main(String[] args) {
        new Login();

    }
}
