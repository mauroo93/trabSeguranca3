import java.security.PrivateKey;
import java.security.cert.X509Certificate;

public class User {
    
    static String email;
    static X509Certificate cert;
    static String grupo;
    static PrivateKey privKey;
    static String nome;
    public static String getNome() {
        return nome;
    }
    public static void setNome(String nome) {
        User.nome = nome;
    }
    public static PrivateKey getPrivKey() {
        return privKey;
    }
    public static void setPrivKey(PrivateKey privKey) {
        User.privKey = privKey;
    }
    public static String getEmail() {
        return email;
    }
    public static void setEmail(String email) {
        User.email = email;
    }
    public static X509Certificate getCert() {
        return cert;
    }
    public static void setCert(X509Certificate cert) {
        User.cert = cert;
    }
    public static String getGrupo() {
        return grupo;
    }
    public static void setGrupo(String grupo) {
        User.grupo = grupo;
    }    
}
