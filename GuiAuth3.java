import java.awt.FlowLayout;
import java.awt.HeadlessException;
import java.awt.Rectangle;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.PrivateKey;
import java.util.Base64;


public class GuiAuth3 extends JPanel {
    private JLabel text=new JLabel("Path do arquivo da chave privada");
    private JTextField path = new JTextField();
    private JLabel text2=new JLabel("senha secreta");
    private JPasswordField senha=new JPasswordField();
    private JButton confirma=new JButton("Confirma");
    private PrivateKey privKey;
    String pathS="";
    String senhaS="";
    Handlr handler = new Handlr();
    public GuiAuth3(){
        setLayout(null);
        text.setBounds(0,10,350,30);
        add(text);
        path.setBounds(0,50,350,30);
        add(path);
        text2.setBounds(0,90,350,30);
        add(text2);
        senha.setBounds(0,140,350,30);
        add(senha);
        confirma.setBounds(200, 350, 150, 100);
        add(confirma);
        confirma.addActionListener(handler);
        path.addActionListener(handler);
        senha.addActionListener(handler);
    }
    private class Handlr implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            if (e.getSource()==path){
                pathS=e.getActionCommand();
                System.out.println(pathS);
            }
            if (e.getSource()==senha){
                senhaS=e.getActionCommand();
                System.out.println(senhaS);
            }
            if (e.getSource()==confirma){
                try {
                    privKey=Decrypt.geraPrivateKey(senhaS,pathS);
                    if(Decrypt.checkSig(privKey, Decrypt.loadCert(BDconnection.getCert(User.getEmail())).getPublicKey())){
                        User.setCert(Decrypt.loadCert(BDconnection.getCert(User.getEmail())));
                        User.setPrivKey(privKey);
                        User.setGrupo(BDconnection.getGrupo(User.getEmail()));
                        User.setNome(Decrypt.recuperaNome(Base64.getEncoder().encode(User.getCert().getEncoded())));
                        BDconnection.atualizaAcessos(User.getEmail());
                        main.escolheTela(4);
                        //passa etapa 3
                    }else{
                        JOptionPane.showMessageDialog(null,"Falha na assinatura da chave Privada");
                        senha.setText("");
                        senhaS="";
                        path.setText("");
                        pathS="";
                    }
                }
                catch(Exception ex){
                    JOptionPane.showMessageDialog(null,"Falha na assinatura da chave Privada");
                    System.out.println(ex);
                }
            }
        }
    }
}
