import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.security.MessageDigest;
import java.security.*;
import java.security.NoSuchAlgorithmException;
import java.io.*;

public class DigestCalculator {
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
            	InputStream fis =  new FileInputStream(arqs[i]);
            	byte[] buffer = new byte[1024];
            	int numRead;
            	do {
                    numRead = fis.read(buffer);
                    if (numRead > 0) {
                        algoritm.update(buffer, 0, numRead);
                    }
                } while (numRead != -1);
            	fis.close();
            	byte[] digest = algoritm.digest();
            	String calculatedDigest = "";

                for (int i1=0; i1 < digest.length; i1++) {
                    calculatedDigest += Integer.toString( ( digest[i1] & 0xff ) + 0x100, 16).substring( 1 );
                }
                System.out.println("Arquivo:"+arqs[i].getName());
                System.out.println( calculatedDigest );
                                
            }
        }catch(Exception  algErr){
            //TODO tratamento de erro
            System.out.print("Problema do tipo \t"+algErr);
            throw algErr;
        }
    }
}

