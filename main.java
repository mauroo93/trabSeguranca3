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
import java.util.Arrays;
import java.util.Base64;
import java.util.Scanner;

import javax.crypto.*;
import javax.crypto.spec.*;
import java.nio.file.*;

public class main {

    // assumo que a partir deste ponto o usuario ja foi autenticado
    static String pathFiles = System.getProperty("user.dir") + "/Files";
    static String pathKeys = System.getProperty("user.dir") + "/Keys";  
    //os paths abaixo teriam que ser pegos com user tbm acho 
    
    static String pathKeyUserPriv = pathKeys+"/user01-pkcs8-des.key";
    static String pathKeyUserPub = pathKeys+"/user01-x509.crt";
    static PrivateKey privKey;
    static PublicKey pubKey;
    static IndexElem[] elemInd= new IndexElem[50]; 
    String loginName="teste";//Aqui teria que inicializar o loginName do usuario
    String groupName="teste";
    String name="teste";
    int totAcessos=0;

    public static void main(String[] args)throws Exception{ 
        boolean exit=true;
        boolean escolhab=true;
        int escolha =0;
        //inicia indexElem com os arquivos na pasta indice, se validacao for feita
        Scanner teclado=new Scanner(System.in);
        //pega keys do user
        pubKey=Decrypt.geraPublicKey(Decrypt.fileByte(pathKeyUserPub));
        privKey=Decrypt.geraPrivateKey("user01",Decrypt.fileByte(pathKeyUserPriv));
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
                    atualizaArrayIndice(retornaIndiceArq());
                    //daqui e apenas fazer o mesmo processo para arquivo escolhido
                    //e gravar com o nome real no array de indices
                break;

            }
            
        }

    }
    static byte[] retornaIndiceArq(){
        //-----------------------------------
        try{
            byte[] sementeEnv=Decrypt.decryptEnv(pathFiles+"/index.env", privKey);
            byte[] indexByte=Decrypt.decryptEnc(pathFiles+"/index.enc", sementeEnv);
            boolean flag=Decrypt.validaArquivo(pathFiles+"/index.asd",pubKey,indexByte);
            if(flag)
                return indexByte;
            else
                return null;
        }catch(Exception e){
            //tratamento de exception
            return null;
        }
    }
    static void atualizaArrayIndice(byte[] index){
        String indString = new String(index);
        Scanner scanner = new Scanner(indString);
        int count=0;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] lineSplit = line.split("\\s+");
            elemInd[count]=new IndexElem(lineSplit[0],lineSplit[1],lineSplit[2], lineSplit[3]);
        }
        scanner.close();
    }
}
