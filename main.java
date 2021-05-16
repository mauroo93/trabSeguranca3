import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.*;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.Scanner;

import javax.crypto.*;
import javax.crypto.spec.*;
import javax.swing.JFrame;
import javax.swing.JPanel;

import java.nio.file.*;


public class main {

    // assumo que a partir deste ponto o usuario ja foi autenticado
    static String pathFiles = System.getProperty("user.dir") + "/Files";
    static String pathKeys = System.getProperty("user.dir") + "/Keys";  
    //informacoes do user
    
    static String pathKeyUserPriv = pathKeys+"/user01-pkcs8-des.key";
    static String pathKeyUserPub = pathKeys+"/user01-x509.crt";
    
    static X509Certificate userCert ;
    static PrivateKey privKey;
    static PublicKey pubKey;
    static List<IndexElem> elemInd= new ArrayList<IndexElem>(); 
    static JFrame frame = new JFrame("Tela Principal");
    static JPanel mainP = new JPanel();
    //gui
    static GuiCadastro guiCadastro ;
    static GuiAuth1 guiAuth1;
    static GuiAuth2 guiAuth2;
    static GuiAuth3 guiAuth3;
    static GuiMain guiMain;
    static GuiAlterar guiAlterar;
    static GuiLista guiLista;
    //
    public static void main(String[] args)throws Exception{ 
        //user ini
        userCert = Decrypt.recCert(Decrypt.fileByte(pathKeyUserPub));
        //user ini
        System.out.println(pathKeyUserPub+"----"+pathKeyUserPriv);
        //ini bd
        BDconnection.connect();
        BDconnection.iniciaTableUser();
        BDconnection.countTables();
        //fim ini bd
        //ini gui
        frame.setSize(400, 500);
        guiCadastro=new GuiCadastro();
        guiLista=new GuiLista();
        guiAuth1 = new GuiAuth1();
        guiAuth2 = new GuiAuth2();
        guiAuth3 = new GuiAuth3();
        guiMain = new GuiMain();
        guiAlterar = new GuiAlterar();
        guiCadastro.setSize(400,500);
        guiAuth1.setSize(400,500);
        guiAuth2.setSize(400,500);
        guiAuth3.setSize(400,500);
        guiMain.setSize(400,500);
        guiAlterar.setSize(400,500);
        guiLista.setSize(400, 500);
        frame.getContentPane().add(guiCadastro);
        frame.getContentPane().add(guiAuth1);
        frame.getContentPane().add(guiAuth2);
        frame.getContentPane().add(guiAuth3);
        frame.getContentPane().add(guiMain);
        frame.getContentPane().add(guiAlterar);
        frame.getContentPane().add(guiLista);
        frame.setVisible(true);
        escolheTela(6);
        // fim ini gui
        boolean exit=true;
        boolean escolhab=true;
        int escolha =0;
        //inicia indexElem com os arquivos na pasta indice, se validacao for feita
        Scanner teclado=new Scanner(System.in);
        //pega keys do user
        pubKey=Decrypt.geraPublicKey(Decrypt.fileByte(pathKeyUserPub));
        privKey=Decrypt.geraPrivateKey("user01",pathKeyUserPriv);
        //key user
        while(exit){
            escolha=0;
            escolhab=true;
            //aqui teria que mostrar o menu
            System.out.print("Escolha uma opcao");
            while(escolhab){
                escolha=teclado.nextInt();
                if(escolha!=0){
                    escolhab=false;
                } 
            }
            switch(escolha){
                //opcao 1 :tela de cadastro->n sei
                //opcao 2:tbm n sei
                //opcao 3:acesso a pasta secreta
                case 3:
                    
                    
                    //depois disso temos array pronto
                    //isso recupera o email
                    //System.out.println(Decrypt.recuperaEmail(Decrypt.fileByte(pathKeyUserPub)));
                    atualizaArrayIndice(retornaIndiceArq());
                    //daqui e apenas fazer o mesmo processo para arquivo escolhido
                    //e gravar com o nome real no array de indices
                break;

            }
            
        }

    }
    static void escolheTela(int tela){
        switch(tela){
            case 1://auth 1 2 e 3
                guiAuth1.setVisible(true);
                guiAuth2.setVisible(false);
                guiAuth3.setVisible(false);
                guiCadastro.setVisible(false);
                guiMain.setVisible(false);
                guiAlterar.setVisible(false);
                guiLista.setVisible(false);
                break;
            case 2:
                guiAuth1.setVisible(false);
                guiAuth2.setVisible(true);
                guiAuth3.setVisible(false);
                guiCadastro.setVisible(false);
                guiMain.setVisible(false);
                guiAlterar.setVisible(false);
                guiLista.setVisible(false);
                break;
            case 3:
                guiAuth1.setVisible(false);
                guiAuth2.setVisible(false);
                guiAuth3.setVisible(true);
                guiCadastro.setVisible(false);
                guiMain.setVisible(false);
                guiAlterar.setVisible(false);
                guiLista.setVisible(false);
                break;
            case 4://main
                guiAuth1.setVisible(false);
                guiAuth2.setVisible(false);
                guiAuth3.setVisible(false);
                guiCadastro.setVisible(false);
                guiMain.setVisible(true);
                guiAlterar.setVisible(false);
                guiLista.setVisible(false);
                break;
            case 5://cadastro
                guiAuth1.setVisible(false);
                guiAuth2.setVisible(false);
                guiAuth3.setVisible(false);
                guiCadastro.setVisible(true);
                guiMain.setVisible(false);
                guiAlterar.setVisible(false);
                guiLista.setVisible(false);
                break;
            case 6://alterar
                guiAuth1.setVisible(false);
                guiAuth2.setVisible(false);
                guiAuth3.setVisible(false);
                guiCadastro.setVisible(false);
                guiMain.setVisible(false);
                guiAlterar.setVisible(true);
                guiLista.setVisible(false);
                break;
            case 7://gui lista
                guiAuth1.setVisible(false);
                guiAuth2.setVisible(false);
                guiAuth3.setVisible(false);
                guiCadastro.setVisible(false);
                guiMain.setVisible(false);
                guiAlterar.setVisible(false);
                guiLista.setVisible(true);
                break;    
        }       
    }
    
    static void atualizaArrayIndice(byte[] index){
        String indString = new String(index);
        Scanner scanner = new Scanner(indString);
        IndexElem element;
        int count=0;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] lineSplit = line.split("\\s+");
            element = new IndexElem(lineSplit[0],lineSplit[1],lineSplit[2], lineSplit[3]);
            elemInd.add(element);
        }
        scanner.close();
    }
}
