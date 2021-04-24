import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.security.MessageDigest;
import java.security.*;
import java.security.NoSuchAlgorithmException;
import java.io.*;

public class App {
    public static void main(String[] args) throws Exception {
        String tipoDgst = "";
        String pathListaDgst = "";
        String pathPastaArquivos="";
        for (int i=0;i<args.length;i++) {
            if (i==0){
                tipoDgst = args[i];
            }
            if (i==1){
                pathListaDgst = args[i];
            }
            if (i==2){
                pathPastaArquivos = args[i];
            } 
        }
        File arqListaDgst = new File(pathListaDgst);
        File arqListaArq = new File (pathPastaArquivos);
        try{
            MessageDigest algoritm = MessageDigest.getInstance(tipoDgst);
            File[] arqs = arqListaArq.listFiles();
            for (int i =0;i<arqs.length;i++){
                byte[] arqByte = new byte[(int)arqs[i].length()];
                
                
                DataInputStream input = new DataInputStream(new BufferedInputStream(new FileInputStream(arqs[i].getPath())));
                input.readFully(arqByte);
                input.close();
                System.out.println("Arquivo:"+arqs[i].getName());
                for(int j = 0;j<arqByte.length;j++){
                    System.out.println("byte "+j+"\t"+arqByte[j]);
                }
                byte[] digest = algoritm.digest(arqByte);
                for(int k = 0;k<digest.length;k++){
                    System.out.println("byte do digest "+k+"\t"+digest[k]);
                }
            }
        }catch(Exception  algErr){
            //TODO tratamento de erro
            System.out.print("Problema do tipo \t"+algErr);
            throw algErr;
        }
    }
}

