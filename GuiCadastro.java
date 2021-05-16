import java.awt.FlowLayout;
import java.awt.Rectangle;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GuiCadastro extends JPanel{
    private JLabel loginName=new JLabel(User.getEmail());
    private JLabel grup= new JLabel(User.getGrupo());
    private JLabel nome = new JLabel(User.getNome());
    private JSeparator sep1=new JSeparator();
    private JSeparator sep2=new JSeparator();
    private int totUser=BDconnection.countTables();
    private JLabel totUsr = new JLabel("Total de usuarios:"+totUser);
    private JLabel text= new JLabel("Formulario de Cadastro:");
    private JTextField pathCert=new JTextField("Path do certificado");
    private String path ="";
    private JTextField userGroup=new JTextField("Usuario/Administrador");
    private String group="";
    private JPasswordField passwordField=new JPasswordField();
    private String pass="";
    private JPasswordField passwordFieldConf=new JPasswordField();
    private String passConf="";
    private JButton voltar=new JButton("Voltar");
    private JButton cadastrar=new JButton("Cadastrar");
    private int id=0;
    private String sal;
    private String hash;
    private String pem;
    private String email;
    Handlr handler = new Handlr();
    public GuiCadastro(){
        setLayout(null);
        loginName.setBounds(30,10,350,30);
        add(loginName);
        grup.setBounds(30,50,350,30);
        add(grup);
        nome.setBounds(30,90,350,30);
        add(nome);
        sep1.setBounds(0,120 , 350, 10);
        add(sep1);
        totUsr.setBounds(30,140 , 350, 30);
        add(totUsr);
        text.setBounds(30,180 , 350, 30);
        add(text);
        sep2.setBounds(0,210 , 350, 10);
        add(sep2);
        pathCert.setBounds(0,220 , 350, 30);
        add(pathCert);
        userGroup.setBounds(0,250 , 350, 30);
        add(userGroup);
        passwordField.setBounds(0,280 , 350, 30);
        add(passwordField);
        passwordFieldConf.setBounds(0,310 , 350, 30);
        add(passwordFieldConf);
        voltar.setBounds(20, 350, 150, 100);
        add(voltar);
        cadastrar.setBounds(180, 350, 150, 100);
        add(cadastrar);
        voltar.addActionListener(handler);
        cadastrar.addActionListener(handler);
        pathCert.addActionListener(handler);
        userGroup.addActionListener(handler);
        passwordField.addActionListener(handler);
        passwordFieldConf.addActionListener(handler);
    }
    private class Handlr implements ActionListener{
        public void actionPerformed(ActionEvent e){
            if (e.getSource()==pathCert){
                path=e.getActionCommand();
                System.out.println("path "+path);
            }
            if (e.getSource()==userGroup){
                group=e.getActionCommand();
                System.out.println("Grupo "+group);
            }
            if (e.getSource()==passwordField){
                pass=e.getActionCommand();
                System.out.println("pass "+pass);
            }
            if (e.getSource()==passwordFieldConf){
                passConf=e.getActionCommand();
                System.out.println("pass conf "+passConf);
            }
            if (e.getSource()==voltar){//aprender como faz para transicionar entre telas
                passConf=e.getActionCommand();
            }
            if (e.getSource()==cadastrar){

                System.out.println(group+path);
                if(pass.equals(passConf)){
                    if(!path.equals("") && !pass.equals("") && (group.equals("Usuario") || group.equals("Administrador"))){
                        try {
                            sal=Decrypt.geraSal();
                            hash=Decrypt.geraHash(pass, sal);
                            pem = Decrypt.getPemCert(path);
                            email = Decrypt.recuperaEmail(pem.getBytes());
                            id= totUser+1;
                            BDconnection.insereUser(id, email, sal, hash, pem, group);
                        } catch (Exception e1) {
                            System.out.println(e1);
                        }    
                    }
                }else{
                    pass="";
                    passConf="";
                    passwordField.setText("");
                    passwordFieldConf.setText("");
                    JOptionPane.showMessageDialog(null,"As senhas estao diferentes");
                }
            }    
        }
    }
}

