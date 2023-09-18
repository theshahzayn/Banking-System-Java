import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import com.toedter.calendar.JDateChooser; //from external library JAR File

public class Signup extends JFrame implements ActionListener {


    JLabel name, dateBirth, gender, username, password, rePassword, invalid;
    JTextField nameField, usernameField, passwordField, rePassField;
    JButton signupButton;
    JDateChooser dateBirthField;
    JRadioButton male, female;

    double balance = 0;
    public Signup(){
        setTitle("Signup");

        setLayout(null); //for custom layout

        //For Bg Image
        JLabel imageBg = null;
        try{
            ImageIcon imageIcon = new ImageIcon(ClassLoader.getSystemResource("bg3.png"));
            Image image = imageIcon.getImage().getScaledInstance(800,600, Image.SCALE_DEFAULT);
            ImageIcon imageIcon1 = new ImageIcon(image);
            imageBg = new JLabel(imageIcon1);
            imageBg.setBounds(0,-30,800,600);
            add(imageBg);
        }catch (Exception e){
            System.out.println(e);
        }

        JLabel heading = new JLabel("Open the doors to financial freedom! ");
        heading.setFont(new Font("Helvetica",Font.BOLD,30));
        heading.setBounds(170,50,700,40);
        imageBg.add(heading);

        //Name
        name = new JLabel("Name:");
        name.setFont(new Font("Helvetica",Font.PLAIN,20));
        name.setBounds(100,120,200,30);
        imageBg.add(name);


        nameField = new JTextField();
        nameField.setBounds(320,120,200,30);
        imageBg.add(nameField);

        //DOB
        dateBirth = new JLabel("Date of Birth:");
        dateBirth.setFont(new Font("Helvetica",Font.PLAIN,20));
        dateBirth.setBounds(100,160,200,30);
        imageBg.add(dateBirth);

        dateBirthField = new JDateChooser(); //from .calender
        dateBirthField.setBounds(320,160,200,30);
        imageBg.add(dateBirthField);

        //Gender
        gender = new JLabel("Gender:");
        gender.setFont(new Font("Helvetica",Font.PLAIN,20));
        gender.setBounds(100,200,200,30);
        imageBg.add(gender);

        male = new JRadioButton("Male");
        female = new JRadioButton("Female");
        male.setBounds(320,200,70,30);
        male.setOpaque(false);
        female.setBounds(400,200,70,30);
        female.setOpaque(false);
        imageBg.add(male);
        imageBg.add(female);

        //username
        username = new JLabel("Username:");
        username.setFont(new Font("Helvetica",Font.PLAIN,20));
        username.setBounds(100,240,200,30);
        imageBg.add(username);

        usernameField = new JTextField();
        usernameField.setBounds(320,240,200,30);
        imageBg.add(usernameField);

        //password
        password = new JLabel("Password:");
        password.setFont(new Font("Helvetica",Font.PLAIN,20));
        password.setBounds(100,300,200,30);
        imageBg.add(password);

        passwordField = new JTextField();
        passwordField.setBounds(320,300,200,30);
        imageBg.add(passwordField);

        //Re enterpassword
        rePassword = new JLabel("Re-enter Password:");
        rePassword.setFont(new Font("Helvetica",Font.PLAIN,20));
        rePassword.setBounds(100,340,200,30);
        imageBg.add(rePassword);

        rePassField = new JTextField();
        rePassField.setBounds(320,340,200,30);
        imageBg.add(rePassField);

        //Sign Up Button
        signupButton = new JButton("SignUp");
        signupButton.setBounds(320,400,100,40);
        signupButton.setBackground(Color.decode("#0b0d0c"));
        signupButton.setForeground(Color.white);
        signupButton.addActionListener(this);
        imageBg.add(signupButton);

        signupButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                signupButton.setBackground(Color.decode("#1c8550")); //Hover
                signupButton.setForeground(Color.WHITE); //When Mouse exit
            }

            @Override
            public void mouseExited(MouseEvent e) {
                signupButton.setBackground(Color.decode("#0b0d0c"));
                signupButton.setForeground(Color.WHITE); //When Mouse exit
            }
        });

        //Invalid pass Statement
        invalid = new JLabel();
        invalid.setFont(new Font("Helvetica",Font.ITALIC,10));
        invalid.setForeground(Color.red);
        invalid.setBounds(300,450,200,30);
        invalid.setVisible(false);
        imageBg.add(invalid);

        setSize(800,600);
        setResizable(false);
        setVisible(true);
        setLocation(300,100);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource()==signupButton){
            String name1 = nameField.getText();
            String username1 = usernameField.getText();
            boolean nameFlag =false, passFlag =false, unameFlag = false;

            //name
            try{
                if(name1.equals("")){
                        JOptionPane.showMessageDialog(null,"Name is Required");
                }
                else{

                    try{
                        double value = Double.parseDouble(name1);
                        // If parsing is successful, then name1 contains a number
                        JOptionPane.showMessageDialog(null, "Name should not contain numbers");

                    }catch (NumberFormatException ae){
                        //catch block : if user enter string
                        nameFlag = true;
                    }
                }
            }catch (Exception ae){
                System.out.println(e);
            }

            //username
            try{
                if(usernameField.getText().equals("")){
                    JOptionPane.showMessageDialog(null,"Username is Required");
                }
                else {
                    unameFlag = true;
                }
            }catch (Exception ae){
                System.out.println(e);
            }


            //Password Mismatch
            if(!passwordField.getText().equals(rePassField.getText())){
                passwordField.setText(null);
                rePassField.setText(null);
                invalid.setText("Password Doesn't Match");
                invalid.setVisible(true);
            }
            else{
                invalid.setVisible(false);

                //Password Strength
                if(passwordField.getText().length() <8){
                    invalid.setText("At least 8 character");
                    invalid.setVisible(true);
                }
                else{
                    invalid.setVisible(false);
                    passFlag = true;
                }

            }

            File userFile = new File(username1.concat(".txt"));

            //Checking Username is saved or not
            try{
                Scanner fileReader = new Scanner(userFile);
                JOptionPane.showMessageDialog(null,"Username is already Taken");
                unameFlag = false;
            }catch (Exception ex){

            }

            //Creating File of a User
            if(nameFlag && unameFlag && passFlag){

                try{
                    userFile.createNewFile();
                }catch (IOException ex){
                    System.out.println("Unable to load");
                    ex.printStackTrace();
                }

                //Writing in User File
                try{
                    FileWriter fileWriter = new FileWriter(username1.concat(".txt"));
                    fileWriter.write(name1);
                    fileWriter.write("\n" + usernameField.getText());
                    fileWriter.write("\n" + passwordField.getText());
                    fileWriter.write("\nDate Of Birth: " + dateBirthField.getDate());
                    if(male.isSelected()){
                        fileWriter.write("\nGender: Male");
                    }
                    else if(female.isSelected()){
                        fileWriter.write("\nGender: Female");
                    }
                    fileWriter.write("\n" + balance);
                    fileWriter.close();
                }
                catch (IOException ex){
                    ex.printStackTrace();

                }


                setVisible(false);
                new Login().setVisible(true);
            }


        }

    }

    public static void main(String[] args) {
        new Signup();
    }
}
