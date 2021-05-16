import java.awt.FlowLayout;
import java.awt.Rectangle;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GuiAuth1 extends JPanel{
    private JLabel text=new JLabel("Email do Usuario");
    private JTextField email=new JTextField();
    Handlr handler = new Handlr();
    public GuiAuth1(){
        setLayout(null);
        text.setBounds(0,10,350,30);
        add(text);
        email.setBounds(0,50,350,30);
        add(email);
        email.addActionListener(handler);
    }
    private class Handlr implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            String emailS;
            if (e.getSource()==email){
                emailS=e.getActionCommand();
                if(BDconnection.checkUser(emailS)){
                    User.setEmail(emailS);
                    System.out.println(User.getEmail());
                    main.escolheTela(2);
                    //passou auth1
                }else{
                    email.setText("");
                    emailS="";
                    JOptionPane.showMessageDialog(null,"email nao existe no DB");
                }
            }  
        }
    
    }
}
