import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.Buffer;

public class changePass extends JFrame implements ActionListener {
    JLabel oldpass , newPass;
    JTextField oldPassField , newPassField;
    JButton changeButton;
    public changePass(){
        setTitle("Signup");

        setLayout(null); //for custom layout
        JLabel heading = new JLabel("Change your Password!!");
        heading.setFont(new Font("Luckiest Guy",Font.PLAIN,30));
        heading.setBounds(200,50,500,40);
        add(heading);

        //old
        oldpass = new JLabel("Old Password:");
        oldpass.setFont(new Font("Helvetica",Font.PLAIN,20));
        oldpass.setBounds(100,120,200,30);
        add(oldpass);

        oldPassField= new JTextField();
        oldPassField.setBounds(320,120,200,30);
        add(oldPassField);

        //new Pass

        newPass = new JLabel("New Password:");
        newPass.setFont(new Font("Helvetica",Font.PLAIN,20));
        newPass.setBounds(100,160,200,30);
        add(newPass);

        newPassField = new JTextField();
        newPassField.setBounds(320,160,200,30);
        add(newPassField);

        changeButton = new JButton("Change");
        changeButton.setBounds(430,200,100,40);
        changeButton.setBackground(Color.decode("#941304"));
        changeButton.setForeground(Color.white);
        changeButton.addActionListener(this);
        add(changeButton);


        setSize(800,600);
        setResizable(false);
        setVisible(true);
        setLocation(300,100);

    }

    @Override
    public void actionPerformed(ActionEvent e){
        if(e.getSource()==changeButton) {

            File userFile = new File(User.userName.concat(".txt"));
            File tempFile = new File("temp.txt");

            BufferedReader reader = null;
            String passCheck = null;
            boolean passCheckFlag = false;

            try {
                reader = new BufferedReader(new FileReader(userFile));

                for (int i = 1; i <= 3; i++) {
                    passCheck = reader.readLine();
                }
                reader.close();

                if (passCheck.equals(oldPassField.getText())) {
                    JOptionPane.showMessageDialog(null, "Password Match!");
                    passCheckFlag = true;
                } else {
                    JOptionPane.showMessageDialog(null, "Password Doesn't Match!");
                    passCheckFlag = false;
                }

                if(newPassField.getText().equals("")){
                    passCheckFlag=false;
                    JOptionPane.showMessageDialog(null, "New Password Required!");

                }else {
                    passCheckFlag=true;
                }
            } catch (Exception ex) {
                System.out.println("Error!");
            }

            //CHANGING PASSWORD
            while (passCheckFlag == true) {
                try {
                    BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
                    BufferedReader reader1 = new BufferedReader(new FileReader(userFile));
                    String content;
                    int lineNumber = 1;

                    while ((content = reader1.readLine()) != null) {
                        if (lineNumber == 3) {
                            writer.write(newPassField.getText()); // modify line 3
                        } else {
                            writer.write(content); // write other lines as is
                        }
                        writer.newLine();
                        lineNumber++;
                    }
                    writer.close();
                    reader1.close();

                    //Delete the original File
                    if (userFile.delete()) {
                        //Rename Temp to username File
                        if (tempFile.renameTo(userFile)) {
                            System.out.println("Success");
                        }
                    } else {
                        throw new Exception();
                    }
                    passCheckFlag = false;

                } catch (Exception ex) {
                    System.out.println("Error!");
                    passCheckFlag = false;
                }
            }
        }

    }


    public static void main(String[] args) {
        new changePass();
    }
}
