import java.awt.FlowLayout;
import java.awt.HeadlessException;
import java.awt.Rectangle;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GuiAuth2 extends JPanel{
    private JLabel text=new JLabel("Senha do Usuario");
    private JPasswordField senha=new JPasswordField();
    Handlr handler = new Handlr();
    public GuiAuth2(){
        setLayout(null);
        text.setBounds(0,10,350,30);
        add(text);
        senha.setBounds(0,50,350,30);
        add(senha);
        senha.addActionListener(handler);
    }
    private class Handlr implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            String senhaS;
            if (e.getSource()==senha){
                senhaS=e.getActionCommand();
                try {
                    if(BDconnection.checkSenha(senhaS,User.email)){
                        main.escolheTela(3);
                        //passou auth2
                    }else{
                        senha.setText("");
                        senhaS="";
                        JOptionPane.showMessageDialog(null,"senha errada para o usuario");
                    }
                } catch (Exception e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }  
        }
    
    }
}
