
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
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

    static PublicKey geraPublicKey(byte[] cert) throws Exception{
        X509Certificate ret=recCert(cert);
        System.out.println(ret.getSubjectX500Principal().toString());
        return ret.getPublicKey();
    }
    
    static String recuperaEmail(byte[] bcert) throws Exception{
        byte[] b64cert=Base64.getDecoder().decode(bcert);
        X509Certificate cert=recCert(b64cert);
        String subject = cert.getSubjectX500Principal().toString();
        String[] split = subject.split(",");
        for(int i =0;i<split.length;i++){
            System.out.println(split[i]);
        } 
        String e =split[0];
        String[] email=e.split("=");
        return email[1];    
    }
    static String recuperaNome(byte[] bcert) throws Exception{
        byte[] b64cert=Base64.getDecoder().decode(bcert);
        X509Certificate cert=recCert(b64cert);
        String subject = cert.getSubjectX500Principal().toString();
        String[] split = subject.split(",");
        for(int i =0;i<split.length;i++){
            System.out.println(split[i]);
        } 
        String e =split[1];
        String[] nome=e.split("=");
        return nome[1];    
    }
    static byte[] decrypt(String pathEnv,String pathEnc,String pathAsd,PrivateKey privKey,PublicKey pubKey){
        //-----------------------------------
        try{
            byte[] sementeEnv=Decrypt.decryptEnv(pathEnv, privKey);
            byte[] indexByte=Decrypt.decryptEnc(pathEnc, sementeEnv);
            boolean flag=Decrypt.validaArquivo(pathAsd,pubKey,indexByte);
            System.out.println(flag);
            if(flag)
                return indexByte;
            else
                return null;
        }catch(Exception e){
            //tratamento de exception
            return null;
        }
    }
    static String getPemCert(String path)throws Exception{
        byte[] fileByt=  fileByte(path);
        X509Certificate cert=recCert(fileByt);
        return Base64.getEncoder().encodeToString(cert.getEncoded());
    }

    static X509Certificate loadCert(String encoded)throws Exception{
        byte[] b64cert=Base64.getDecoder().decode(encoded);
        X509Certificate cert=recCert(b64cert);
        return cert;
    }

    static X509Certificate recCert(byte[] cert) throws Exception{
        InputStream key = new ByteArrayInputStream(cert);
        CertificateFactory cf = CertificateFactory.getInstance("X.509");
        X509Certificate ret = (X509Certificate) cf.generateCertificate(key);
        return ret;
    }

    static byte[] fileByte(String path) throws IOException {

        return Files.readAllBytes(Paths.get(path));   
    }

    static PrivateKey geraPrivateKey(String pass,String path) throws Exception{

        try{
            System.out.println("gera private key recebeu"+pass+" path"+path);
            byte[] keyBytes = fileByte(path);
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

    static String geraSal()throws Exception{
        SecureRandom random= SecureRandom.getInstance("SHA1PRNG");
        byte[] rand=new byte[10];
        random.nextBytes(rand);
        return new String(rand);
    }

    static String geraHash(String pass,String sal) throws Exception{
        String res = sal+pass;
        byte []bytes = res.getBytes();
        MessageDigest digest = MessageDigest.getInstance("SHA-1");
        digest.reset();
        for(byte b:bytes){
            digest.update(b);
        }
        String sha1 = String.format("%040x", new BigInteger(1, digest.digest()));
        return sha1;
    }
    static boolean checkSig(PrivateKey privKey,PublicKey pubKey) throws Exception{
        byte[] arrayB = new byte[2048];
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
        random.nextBytes(arrayB);
        Signature sig = Signature.getInstance("SHA1WithRSA");
        sig.initSign(privKey);
        sig.update(arrayB);
        byte[] signatureBytes = sig.sign();
        sig.initVerify(pubKey);
        sig.update(arrayB);
        return sig.verify(signatureBytes);
    }
}
