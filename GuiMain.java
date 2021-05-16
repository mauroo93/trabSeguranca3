import java.awt.FlowLayout;
import java.awt.Rectangle;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GuiMain extends JPanel{
    private JLabel loginName=new JLabel(User.getEmail());
    private JLabel grup= new JLabel(User.getGrupo());
    private JLabel nome = new JLabel(User.getNome());
    private JSeparator sep1=new JSeparator();
    private JSeparator sep2=new JSeparator();
    private JLabel acessos= new JLabel(""+BDconnection.getAcessos(User.getEmail()));
    private JLabel text= new JLabel("Menu Principal:");
    private JButton cadastro=new JButton("1-Cadastrar um novo usuario");
    private JButton alterar=new JButton("2-Alterar senha pessoal e certificado");
    private JButton consulta=new JButton("3-Consultar pasta de arquivos secretos");
    private JButton sair=new JButton("4-Sair do Sistema");
    public GuiMain(){
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
        text.setBounds(30, 180, 350, 30);
        add(text);
        sep2.setBounds(0,210 , 350, 10);
        cadastro.setBounds(30,220, 350, 30);
        add(cadastro);
        alterar.setBounds(30,260, 350, 30);
        add(alterar);
        consulta.setBounds(30,300, 350, 30);
        add(consulta);
        sair.setBounds(30,340, 350, 30);
        add(sair);
        Handlr handler = new Handlr();
        cadastro.addActionListener(handler);
        alterar.addActionListener(handler);
        consulta.addActionListener(handler);
        sair.addActionListener(handler);
    }
    private class Handlr implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            if (e.getSource()==cadastro){
                main.escolheTela(4);
            }
            if (e.getSource()==alterar){
               //chamar main mudar tela
            }
            if (e.getSource()==consulta){
                //chamar main mudar tela
            }
            if (e.getSource()==sair){
                //encerrar
            }
        }
    }
}
