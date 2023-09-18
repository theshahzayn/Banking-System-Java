import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class History extends JFrame{
    public History(){
        setTitle("History");

        JTextArea textArea = new JTextArea();
        textArea.setFont(new Font("Helvetica",Font.PLAIN,20));

        JLabel heading = new JLabel("TRANSACTION HISTORY");
        heading.setFont(new Font("Helvetica",Font.BOLD,30));
        heading.setBounds(170,50,700,40);
        textArea.add(heading);

        textArea.setEditable(false);
        textArea.setMargin(new Insets(100,100,20,20));
        textArea.setBounds(100,100,500,600);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBounds(50,50,800,600);

        File userfile = new File((User.userName.concat(".txt")));
        StringBuilder contents = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(userfile));
            String content;

            for (int i = 1 ; i<=7; i++){
                content = reader.readLine();
            }

            while ((content = reader.readLine()) !=null){
                contents.append(content).append("\n");
            }

        }catch (Exception e){
            System.out.println("Error");
        }

        textArea.setText(contents.toString());
        add(scrollPane);

        setSize(800,600);
        setResizable(false);
        setVisible(true);
        setLocation(300,100);
    }

    public static void main(String[] args) {
        new History();
    }
}
