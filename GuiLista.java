import java.awt.FlowLayout;
import java.awt.Rectangle;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener; 

public class GuiLista extends JPanel {
    static IndexElem selecionado;
    private JLabel loginName=new JLabel(User.getEmail());
    private JLabel grup= new JLabel(User.getGrupo());
    private JLabel nome = new JLabel(User.getNome());
    private JSeparator sep1=new JSeparator();
    private JSeparator sep2=new JSeparator();
    private JLabel acessos= new JLabel("Total de Acessos:"+BDconnection.getAcessos(User.getEmail()));
    private JTextField pathPasta=new JTextField("Path do certificado");
    private String path ="";
    private String pathEnc="";
    private String pathEnv="";
    private String pathAsd="";
    JButton listar = new JButton();
    JButton voltar = new JButton();
    Handlr handler = new Handlr();
    public GuiLista(){
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
        pathPasta.setBounds(0,220 , 350, 30);
        add(pathPasta);   
        listar.setBounds(20,350, 120, 40);
        add(listar);
        voltar.setBounds(160,350, 120, 40);
        add(voltar);
        pathPasta.addActionListener(handler);
        listar.addActionListener(handler);
        voltar.addActionListener(handler);
    }
    private class Handlr implements ActionListener{
        public void actionPerformed(ActionEvent e){
            if (e.getSource()==pathPasta){
                path=e.getActionCommand();
            }
            if (e.getSource()==listar){
                if(!path.equals("")){
                    pathAsd=path+"/index.asd";
                    pathEnc=path+"/index.enc";
                    pathEnv=path+"/index.env";
                    // a lista esta na main 
                    main.atualizaArrayIndice(Decrypt.decrypt(pathEnv,pathEnc,pathAsd
                    ,User.privKey,User.getCert().getPublicKey()));
                }
            }
            if (e.getSource()==voltar){
                main.escolheTela(4);
            }
        }
    }
    private void mostraLista(List<IndexElem> elemList,
}
