import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Dashboard extends JFrame implements ActionListener {
    JButton transactionButton, exitButton, depositButton, historyButton, changePass;
    static JLabel balanceLabel1;
    public Dashboard() {
        setTitle("Signup");
        setLayout(null); // for custom layout
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); // get screen size
        setBounds(0, 0, screenSize.width, screenSize.height); // set JFrame size to screen size

        //Add Image
        JLabel imageBg = null;
        try{
            ImageIcon imageIcon = new ImageIcon(ClassLoader.getSystemResource("bgImage.jpg"));
            Image image = imageIcon.getImage().getScaledInstance(screenSize.width,screenSize.height, Image.SCALE_DEFAULT);
            ImageIcon imageIcon1 = new ImageIcon(image);
            imageBg = new JLabel(imageIcon1);
            imageBg.setBounds(0,0,screenSize.width,screenSize.height);
            add(imageBg);
        }catch (Exception e){
            System.out.println(e);
        }


        JLabel heading = new JLabel("Welcome to EasyMoney!");
        heading.setFont(new Font("Poppins",Font.PLAIN,40));
        heading.setBounds(50, (screenSize.height)/3 ,700,40);
        heading.setForeground(Color.white);
        imageBg.add(heading);

        JLabel heading1 = new JLabel(User.name);
        heading1.setFont(new Font("Poppins Black",Font.PLAIN,50));
        heading1.setBounds(50, ((screenSize.height)/3) + 50 ,700,60);
        heading1.setForeground(Color.white);
        imageBg.add(heading1);


        JLabel balanceLabel = new JLabel("Current Balance: ");
        balanceLabel.setFont(new Font("Poppins",Font.ITALIC,20));
        balanceLabel.setBounds(70, ((screenSize.height)/3) + 140 ,700,40);
        balanceLabel.setForeground(Color.white);
        imageBg.add(balanceLabel);


        balanceLabel1 = new JLabel("Rs " + User.balance );
        balanceLabel1.setFont(new Font("Poppins Bold",Font.BOLD,40));
        balanceLabel1.setBounds(70, ((screenSize.height)/3) + 180 ,700,40);
        balanceLabel1.setForeground(Color.white);
        imageBg.add(balanceLabel1);

        JLabel detail = new JLabel(User.dateOBirth  );
        detail.setFont(new Font("Poppins Bold",Font.BOLD,40));
        detail.setBounds(70, ((screenSize.height)/3) + 220 ,700,40);
        detail.setForeground(Color.white);
        imageBg.add(detail);

        //Buttons
        transactionButton = new JButton("Send Money");
        transactionButton.setBounds(1060,100,200,250);
        transactionButton.setBackground(Color.decode("#1c8550"));
        transactionButton.setForeground(Color.white);
        transactionButton.addActionListener(this);
        imageBg.add(transactionButton);

        transactionButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                transactionButton.setBackground(Color.decode("#f2e7e6"));
                transactionButton.setForeground(Color.BLACK);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                transactionButton.setBackground(Color.decode("#1c8550"));
                transactionButton.setForeground(Color.white);
            }
        });

        depositButton = new JButton("Deposit Money");
        depositButton.setBounds(800,100,200,125);
        depositButton.setBackground(Color.decode("#63067d"));
        depositButton.setForeground(Color.white);
        depositButton.addActionListener(this);
        imageBg.add(depositButton);

        depositButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                depositButton.setBackground(Color.decode("#f2e7e6"));
                depositButton.setForeground(Color.BLACK);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                depositButton.setBackground(Color.decode("#63067d"));
                depositButton.setForeground(Color.white);
            }
        });

        //Transaction History
        historyButton = new JButton("Transaction History");
        historyButton.setBounds(800,230,200,125);
        historyButton.setBackground(Color.decode("#004b7d"));
        historyButton.setForeground(Color.white);
        historyButton.addActionListener(this);
        imageBg.add(historyButton);

        //Change Password
        changePass = new JButton("Change Password");
        changePass.setBounds(800,screenSize.height-150,200,60);
        changePass.setBackground(Color.decode("#261616"));
        changePass.setForeground(Color.white);
        changePass.addActionListener(this);
        imageBg.add(changePass);

        //Exit Button
        exitButton = new JButton("Exit");
        exitButton.setBounds(screenSize.width-200,screenSize.height-150,100,60);
        exitButton.setBackground(Color.decode("#941304"));
        exitButton.setForeground(Color.white);
        exitButton.addActionListener(this);
        imageBg.add(exitButton);



        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(true);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e){
        if(e.getSource() == transactionButton){
            new Transactions();
        }else if (e.getSource() == depositButton) {
            new deposit();
        } else if (e.getSource()==historyButton) {
            new History();
        } else if (e.getSource() == exitButton) {
            System.exit(0);
        } else if (e.getSource() == changePass) {
            new changePass();
        }
    }

    public static void main(String[] args) {
        new Dashboard();
    }
}
