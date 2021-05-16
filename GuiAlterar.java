import java.awt.FlowLayout;
import java.awt.Rectangle;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
//funcionalidade dessa tela ainda nao testada//se ta mudando de fato

public class GuiAlterar extends JPanel {
    private JLabel loginName=new JLabel(User.getEmail());
    private JLabel grup= new JLabel(User.getGrupo());
    private JLabel nome = new JLabel(User.getNome());
    private JSeparator sep1=new JSeparator();
    private JSeparator sep2=new JSeparator();
    private JLabel acessos= new JLabel("Total de Acessos:"+BDconnection.getAcessos(User.getEmail()));
    private JTextField pathCert=new JTextField("Path do certificado");
    private String path ="";
    private JPasswordField passwordField=new JPasswordField();
    private String pass="";
    private JPasswordField passwordFieldConf=new JPasswordField();
    private String passConf="";
    private JButton confirma = new JButton("confirma");
    private JButton voltar = new JButton("voltar");
    String pem;
    String sal;
    String hash;
    public GuiAlterar(){
        setLayout(null);
        loginName.setBounds(30,10,350,30);
        add(loginName);
        grup.setBounds(30,50,350,30);
        add(grup);
        nome.setBounds(30,90,350,30);
        add(nome);
        sep1.setBounds(0,120 , 350, 10);
        add(sep1);
        acessos.setBounds(30,150, 350,30);
        add(acessos);
        sep2.setBounds(0,190,350,10);
        add(sep2);
        pathCert.setBounds(0,220 , 350, 30);
        add(pathCert);
        passwordField.setBounds(0,260 , 350, 30);
        add(passwordField);
        passwordFieldConf.setBounds(0,300 , 350, 30);
        add(passwordFieldConf);
        voltar.setBounds(20,350, 120, 40);
        add(voltar);
        confirma.setBounds(160,350, 120, 40);
        add(confirma);
        Handlr handler = new Handlr();
        pathCert.addActionListener(handler);
        passwordField.addActionListener(handler);
        passwordFieldConf.addActionListener(handler);
        voltar.addActionListener(handler);
        confirma.addActionListener(handler);
        
    }
    private class Handlr implements ActionListener{
        public void actionPerformed(ActionEvent e){
            if (e.getSource()==pathCert){
                path=e.getActionCommand();
                System.out.println("path "+path);
            }
            if (e.getSource()==passwordField){
                pass=e.getActionCommand();
                System.out.println("pass "+pass);
            }
            if (e.getSource()==passwordFieldConf){
                passConf=e.getActionCommand();
                System.out.println("pass conf "+passConf);
            }
            if (e.getSource()==voltar){
                main.escolheTela(4);
            }
            if (e.getSource()==confirma){
                try {
                    if(!path.equals("")){
                        pem = Decrypt.getPemCert(path);
                        BDconnection.setCert(pem, User.getEmail());
                    }
                    if(!pass.equals("")){
                        sal=Decrypt.geraSal();
                        hash=Decrypt.geraHash(pass, sal);
                        BDconnection.setSalt(sal,User.getEmail());
                    }
                } catch (Exception ex) {
                    System.out.println(ex);
                }
            }
        }     
    }
}

