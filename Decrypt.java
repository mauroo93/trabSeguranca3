
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
import javax.crypto.*;
import javax.crypto.spec.*;
import java.nio.file.*;

public class Decrypt {
    Decrypt(){}
    /*main reserva caso de merda
    public static void main(String[] args) throws Exception {

        String pathFiles = System.getProperty("user.dir") + "/Files";
        String pathKeys = System.getProperty("user.dir") + "/Keys";     
        String pathKeyUser1 = pathKeys+"/user01-pkcs8-des.key";
        byte[] byteArq=fileByte(pathKeyUser1);
        PrivateKey keyUserPriv=geraPrivateKey("user01",byteArq);
        PublicKey keyUserPub=geraPublicKey(fileByte(pathKeys+"/user01-x509.crt"));
        byte[] sementeEnv=decryptEnv(fileByte(pathFiles+"/index.env"), keyUserPriv);
        byte[] indexByte=decryptEnc(fileByte(pathFiles+"/index.enc"), sementeEnv);
        boolean flag=validaArquivo(fileByte(pathFiles+"/index.asd"),keyUserPub,indexByte);
        gravaArq(indexByte,"index.doc");
        System.out.print(flag);
       
    }
    */
    static void gravaArq(byte[] data,String nomeArq)throws Exception{

        File output=new File(nomeArq);
        try (FileOutputStream outputStream = new FileOutputStream(output)) {
            outputStream.write(data);
        }
    }
    static boolean validaArquivo(String path, PublicKey keyUserPub,byte[] arq) throws Exception {
        byte[] userAsd=fileByte(path);
        Signature sign = Signature.getInstance("SHA1WithRSA");
        sign.initVerify(keyUserPub);
        sign.update(arq);
        return sign.verify(userAsd);
    }
    static PublicKey geraPublicKey(byte[] fileByte) throws Exception{
        
        InputStream key = new ByteArrayInputStream(fileByte);
        CertificateFactory cf = CertificateFactory.getInstance("X.509");
        X509Certificate cert = (X509Certificate) cf.generateCertificate(key);
        return cert.getPublicKey();
    }
    static byte[] fileByte(String path) throws IOException {

        return Files.readAllBytes(Paths.get(path));   
    }
    static PrivateKey geraPrivateKey(String pass,byte[] keyBytes) throws Exception{

        try{

            byte[] plainText = pass.getBytes("UTF8");
            SecureRandom random =  	SecureRandom.getInstance("SHA1PRNG");
            random.setSeed(plainText);
            //byte iV[] = new byte[8];
            //random.nextBytes(iV);
           // IvParameterSpec ivSpec = new IvParameterSpec(iV);
            KeyGenerator keyGen = KeyGenerator.getInstance("DES");
            keyGen.init(56,random);
            Key key = keyGen.generateKey();
            Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] cipherText = cipher.doFinal(keyBytes);
            String keyS= new String(cipherText);
           // System.out.println(keyS);
            String privateKeyPEM = keyS.replace("-----BEGIN PRIVATE KEY-----", "").replaceAll(System.lineSeparator(), "").replace("-----END PRIVATE KEY-----", "");
            //uso de base64
            byte[] decoded = Base64.getDecoder().decode(privateKeyPEM);
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(decoded);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PrivateKey retorno  = keyFactory.generatePrivate(keySpec);
            //System.out.println(retorno.toString());
            return retorno;
            //ISSO TRANSFORMA O CYPHER EM HEX 
            //StringBuffer buf = new StringBuffer();
            //for(int i = 0; i < cipherText.length; i++) {
            //    String hex = Integer.toHexString(0x0100 + (cipherText[i] & 0x00FF)).substring(1);
            //    buf.append((hex.length() < 2 ? "0" : "") + hex);
            //}
            // String cypherHex = buf.toString();
            //System.out.println(cypherHex);
            
        }catch(Exception e){

            System.out.println("Exception "+e);
            return null;
        }
    }
    static byte[] decryptEnv(String path,PrivateKey key) throws Exception{
        byte[] byteEnv=fileByte(path);
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.DECRYPT_MODE, key);
        return  cipher.doFinal(byteEnv);
        
    }
    static byte[] decryptEnc(String path,byte[] semente)throws Exception{
        byte[] byteEnc=fileByte(path);
        SecureRandom random =  	SecureRandom.getInstance("SHA1PRNG");
        random.setSeed(semente);
        KeyGenerator keyGen = KeyGenerator.getInstance("DES");
        keyGen.init(56,random);
        Key key = keyGen.generateKey();
        Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] cipherText = cipher.doFinal(byteEnc);
        String keyS= new String(cipherText);
        //System.out.println(keyS);
        return cipherText;
    }
}
