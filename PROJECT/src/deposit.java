import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

public class deposit extends JFrame implements ActionListener {

    JLabel amount;
    JTextField amountField;
    JButton sendButton;
    Date date = new Date();
    deposit(){
        setTitle("Deposit Money!");

        setLayout(null); //for custom layout
        JLabel heading = new JLabel("Deposit Money Now!!");
        heading.setFont(new Font("Luckiest Guy",Font.PLAIN,30));
        heading.setBounds(200,50,500,40);
        add(heading);

        //Deposit
        amount = new JLabel("Amount:");
        amount.setFont(new Font("Helvetica",Font.PLAIN,20));
        amount.setBounds(100,120,200,30);
        add(amount);

        amountField = new JTextField();
        amountField.setBounds(320,120,200,30);
        add(amountField);

        sendButton = new JButton("Deposit!");
        sendButton.setBounds(430,200,100,40);
        sendButton.setBackground(Color.decode("#941304"));
        sendButton.setForeground(Color.white);
        sendButton.addActionListener(this);
        add(sendButton);


        setSize(800,600);
        setResizable(false);
        setVisible(true);
        setLocation(300,100);
    }

    @Override
    public void actionPerformed(ActionEvent e){
        if(e.getSource()==sendButton){
            File userFile = new File(User.userName.concat(".txt"));
            double amountDouble = 0;
            boolean flag = false;

            try {
                amountDouble = Double.parseDouble(amountField.getText());
                flag= true;
            }catch (NumberFormatException ex){
                JOptionPane.showMessageDialog(null,"Enter Correct Number!");
            }

            if(flag){
                try{
                    //Appending in receiver File
                    FileWriter writer = new FileWriter(userFile,true);
                    BufferedWriter bufferedWriter = new BufferedWriter(writer);
                    bufferedWriter.newLine();
                    bufferedWriter.write("\nMoney Deposited" + "\nTime and Date: " + date);
                    bufferedWriter.newLine();
                    bufferedWriter.write("Amount: " + amountDouble);
                    bufferedWriter.newLine();
                    bufferedWriter.write("Current Balance: " + (amountDouble+User.balance));
                    bufferedWriter.close();
                    writer.close();
                    JOptionPane.showMessageDialog(null,"Money Deposited: " + amountField.getText());

                }
                catch(IOException ex) {
                    JOptionPane.showMessageDialog(null,"Error in appending!");
                }
            }

        }
    }

    public static void main(String[] args) {
        new deposit();
    }
}
