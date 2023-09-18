import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Date;
import java.util.Scanner;

public class Transactions extends JFrame implements ActionListener {
    JLabel amount, username, bankLabel;
    JTextField amountField , usernameField;
    JButton sendButton;
    JComboBox banks;
    double amount1, senderBalance, receiverBalance , transferFee, totalTransfer;
    String uname;
    String[] bankList;
    boolean transferFlag = false, unameFlag = false;
    Date date = new Date();
    Transactions(){
        setTitle("Send Money");

        setLayout(null); //for custom layout

        JLabel heading = new JLabel("Send Money! ");
        heading.setFont(new Font("Helvetica",Font.BOLD,30));
        heading.setBounds(200,50,500,40);
        add(heading);

        //Amount
        amount = new JLabel("Enter Amount:");
        amount.setFont(new Font("Helvetica",Font.PLAIN,20));
        amount.setBounds(100,120,200,30);
        add(amount);


        amountField = new JTextField();
        amountField.setBounds(320,120,200,30);
        add(amountField);

        //username
        username = new JLabel("Enter Username:");
        username.setFont(new Font("Helvetica",Font.PLAIN,20));
        username.setBounds(100,160,200,30);
        add(username);


        usernameField = new JTextField();
        usernameField.setBounds(320,160,200,30);
        add(usernameField);

        //Bank lists
        bankLabel = new JLabel("Select Bank:");
        bankLabel.setFont(new Font("Helvetica",Font.PLAIN,20));
        bankLabel.setBounds(100,200,200,30);
        add(bankLabel);

        bankList = new String[] {"EasyMoney", "Bank Al-Habib", "HBL", "Meezan Bank"};
        banks= new JComboBox<>(bankList);  //For Bank List
        banks.setBounds(320,200,200,30);
        banks.setBackground(Color.getHSBColor(38, 70, 95));
        add(banks);


        //Send Button
        sendButton = new JButton("Send Money");
        sendButton.setBounds(430,240,100,40);
        sendButton.setBackground(Color.blue);
        sendButton.setForeground(Color.white);
        sendButton.addActionListener(this);
        add(sendButton);

        setSize(800,600);
        setVisible(true);
        setLocation(300,100);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource() == sendButton){

            String amountString = amountField.getText();
            uname = usernameField.getText();

            try{
                //Amount arguments
                if(amountString.equals("")){
                    transferFlag = false;
                    throw new IllegalArgumentException("Amount is Required!");
                }else {
                    transferFlag = true;
                }

                amount1 = Double.parseDouble(amountString);  //convert String into double

                //username arguments
                if(uname.equals("")){
                    unameFlag = false;
                    throw new IllegalArgumentException("Username is Required!");
                }else{
                    unameFlag = true;
                }

            } catch (NumberFormatException ex){
                transferFlag = false;
                JOptionPane.showMessageDialog(null,"Enter correct numbers");

            } catch (IllegalArgumentException ex){
                JOptionPane.showMessageDialog(null,ex.getMessage());

            }

            File recieverFile = new File(usernameField.getText().concat(".txt"));
            File senderFile = new File(User.userName.concat(".txt"));


            //For easyMoney user
            if(banks.getSelectedItem() == bankList[0] && transferFlag && unameFlag){

                //checking username exist
                try{
                    Scanner fileReader = new Scanner(recieverFile);
                    unameFlag = true;
                }
                catch (Exception ex){
                    unameFlag = false;
                    JOptionPane.showMessageDialog(null,"No User Found!");
                }

                //getting current balance and checking sufficient amount
                try{

                    senderBalance = User.balance;

                    transferFee = amount1 * (0.02);
                    totalTransfer = transferFee + amount1;

                    if(totalTransfer>senderBalance){
                        throw new IllegalArgumentException();
                    }else {
                        transferFlag = true;
                    }

                } catch (IllegalArgumentException ex){
                    transferFlag = false;
                    JOptionPane.showMessageDialog(null,"Not Sufficient Balance in your Account!");
                }
                catch (Exception ex){
                    JOptionPane.showMessageDialog(null,"Error in appending!");
                }

                if(unameFlag){
                    try{
                        //Finding Receiver Balance
                        BufferedReader fileReader = new BufferedReader(new FileReader(recieverFile));
                        String balanceString = fileReader.readLine();
                        String lastWord = null;

                        while (balanceString != null){
                            String[] words = balanceString.split("\\s+"); //\\s+ as the delimiter, which matches one or more whitespace characters.
                            if(words.length > 0){
                                lastWord = words[words.length-1];
                            }
                            balanceString = fileReader.readLine();
                        }
                        receiverBalance = Double.parseDouble(lastWord);
                        fileReader.close();

                    }catch (Exception ex){
                        JOptionPane.showMessageDialog(null,"Error in finding receiver balance");
                    }




                }


                if(unameFlag && transferFlag){
                    // for appending balance
                    try{

                        //Appending in receiver File
                        FileWriter writer = new FileWriter(recieverFile,true);
                        BufferedWriter bufferedWriter = new BufferedWriter(writer);
                        bufferedWriter.newLine();
                        bufferedWriter.write("\nTransfer by: " + User.userName + " - " + User.name + "\nTime and Date: " + date);
                        bufferedWriter.newLine();
                        bufferedWriter.write("Amount Transfer: " + amountField.getText());
                        bufferedWriter.newLine();
                        bufferedWriter.write("Current Balance: " + (amount1+receiverBalance));
                        bufferedWriter.close();
                        writer.close();

                        //Appending in sender File
                        FileWriter writer1 = new FileWriter(senderFile, true);
                        BufferedWriter bufferedWriter1 = new BufferedWriter(writer1);
                        bufferedWriter1.newLine();
                        bufferedWriter1.write("\nTransfer To: " + usernameField.getText() + "\nTime and Date: " + date);
                        bufferedWriter1.newLine();
                        bufferedWriter1.write("Amount Transfer: " + amountField.getText() + "\nService Fee" + transferFee);
                        bufferedWriter1.newLine();
                        User.balance = senderBalance - totalTransfer;
                        bufferedWriter1.write("Current Balance: " + User.balance);
                        bufferedWriter1.close();
                        writer1.close();

                        new TransactionReceipt();


                    }
                    catch(IOException ex) {
                        JOptionPane.showMessageDialog(null,"Error in appending!");
                    }
                }

            }

        }

    }

    public class TransactionReceipt extends JFrame{
            public TransactionReceipt(){
            setTitle("Receipt");
            setLayout(null); //for custom layout

                JLabel imageBg = null;
                try{
                    ImageIcon imageIcon = new ImageIcon(ClassLoader.getSystemResource("bg.jpg"));
                    Image image = imageIcon.getImage().getScaledInstance(400,500, Image.SCALE_DEFAULT);
                    ImageIcon imageIcon1 = new ImageIcon(image);
                    imageBg = new JLabel(imageIcon1);
                    imageBg.setBounds(0,0,400,500);
                    add(imageBg);
                }catch (Exception e){
                    System.out.println(e);
                }

            JLabel transaction = new JLabel("Transaction Successful ");
            transaction.setFont(new Font("Helvetica Bold",Font.BOLD,25));
            transaction.setBounds(60,50,350,30);
            transaction.setForeground(Color.decode("#135743"));
            imageBg.add(transaction);

            JLabel transferInfo = new JLabel("Received by");
            transferInfo.setFont(new Font("Helvetica",Font.BOLD,20));
            transferInfo.setForeground(Color.darkGray);
            transferInfo.setBounds(50,100,200,20);
            imageBg.add(transferInfo);

                JLabel transferInfo2 = new JLabel(usernameField.getText());
                transferInfo2.setFont(new Font("Helvetica",Font.PLAIN,17));
                transferInfo2.setForeground(Color.darkGray);
                transferInfo2.setBounds(50,120,200,20);
                imageBg.add(transferInfo2);

            JLabel transferInfo3 = new JLabel("Sent by");
            transferInfo3.setFont(new Font("Helvetica",Font.BOLD,20));
            transferInfo3.setForeground(Color.darkGray);
            transferInfo3.setBounds(50,150,200,20);
            imageBg.add(transferInfo3);

                JLabel transferInfo4 = new JLabel(User.name);
                transferInfo4.setFont(new Font("Helvetica",Font.PLAIN,17));
                transferInfo4.setForeground(Color.darkGray);
                transferInfo4.setBounds(50,170,200,20);
                imageBg.add(transferInfo4);

            JLabel transferInfo5 = new JLabel(date.toString());
            transferInfo5.setFont(new Font("Helvetica",Font.PLAIN,15));
            transferInfo5.setForeground(Color.darkGray);
            transferInfo5.setBounds(50,200,400,20);
            imageBg.add(transferInfo5);

                JLabel transferInfo6 = new JLabel(String.format("Current Balance: %.2f", User.balance));
                transferInfo6.setFont(new Font("Helvetica",Font.PLAIN,15));
                transferInfo6.setForeground(Color.darkGray);
                transferInfo6.setBounds(50,230,400,20);
                imageBg.add(transferInfo6);

                JLabel transferInfo7 = new JLabel("Service Fee: " + transferFee);
                transferInfo7.setFont(new Font("Helvetica",Font.PLAIN,15));
                transferInfo7.setForeground(Color.darkGray);
                transferInfo7.setBounds(50,250,400,20);
                imageBg.add(transferInfo7);

                JLabel transferInfo8 = new JLabel("Total Amount ");
                transferInfo8.setFont(new Font("Helvetica",Font.PLAIN,15));
                transferInfo8.setForeground(Color.darkGray);
                transferInfo8.setBounds(130,290,400,20);
                imageBg.add(transferInfo8);

                JLabel transferInfo9 = new JLabel("Rs."+ amountField.getText());
                transferInfo9.setFont(new Font("Luckiest Guy",Font.BOLD,30));
                transferInfo9.setForeground(Color.decode("#1e5713"));
                transferInfo9.setBounds(130,310,400,30);
                imageBg.add(transferInfo9);

            setSize(400,500);
            setResizable(false);
            setVisible(true);
            setLocation(500,100);

        }
    }

    public static void main(String[] args) {
        new Transactions();
    }

}


