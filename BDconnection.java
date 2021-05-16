import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.SecureRandom;
import java.sql.*;
public class BDconnection {
	
	static Connection conn = null;
	public static void connect() {
        try {
            // db parameters
            String url = "jdbc:sqlite:db/digitalvault.db";
            // create a connection to the database
            conn = DriverManager.getConnection(url);
            
            System.out.println("Connection to SQLite has been established.");
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } 
    }
    public static void iniciaTableUser(){
        String sql="CREATE TABLE IF NOT EXISTS user(\n"+
        "id integer PRIMARY KEY,\n"+
        "email text NOT NULL UNIQUE,\n"+
        "salt text NOT NULL,\n"+
        "hexPass text NOT NULL,\n"+
        "pemCert text NOT NULL,\n"+
        "grupo text NOT NULL,\n"+
        "ctAcessos integer DEFAULT 0,\n"+
        "blockTime text\n"+
        ");";
        try{
            Statement statement = conn.createStatement();
            statement.execute(sql); 
         }
        catch (SQLException e) {
            throw new RuntimeException("Error executing sql:\n" + sql, e);
        }
    }
    
    public static void insereUser(int id,String email,String salt,String hexPass,
    String pemCert,String grupo ){
        String sql="Insert into user (id,email,salt,hexPass,pemCert,grupo) values("+id+","+"'"+email+"'"+","+"'"+salt+"'"+","+"'"+hexPass+"'"+","+"'"+pemCert+"'"+","+"'"+grupo+"'"+")";
        try{
            Statement statement = conn.createStatement();
            statement.execute(sql); 
            System.out.println("inseri user");
         }
        catch (SQLException e) {
            throw new RuntimeException("Error executing sql:\n" + sql, e);
        }
    }


    public static int countTables(){
        String sql="select count(*) as cont from user";
        try{
            int count=0;
            Statement statement = conn.createStatement();
            statement.execute(sql); 
            ResultSet rs=statement.getResultSet();
            while(rs.next()){
                count=rs.getInt("cont");
            }
            System.out.println(count);
            return count;
         }
        catch (SQLException e) {
            throw new RuntimeException("Error executing sql:\n" + sql, e);
        }
    }

    public static boolean checkSenha(String pass,String email) throws Exception {
        String hash = getHash(email);
        String salt = getSalt(email);
        System.out.println(hash+" hash "+salt+" salt ");
        if(hash.equals(Decrypt.geraHash(pass, salt))){
            return true;
        }else{
            return false;
        }
    }
    public static boolean checkUser(String email){
        String sql="select count(*) as cont from user where email='"+email+"';";
        try{
            int count=0;
            Statement statement = conn.createStatement();
            statement.execute(sql); 
            ResultSet rs=statement.getResultSet();
            while(rs.next()){
                count=rs.getInt("cont");
            }if(count ==1){
                return true;
            }else
                return false;
         }
        catch (SQLException e) {
            throw new RuntimeException("Error executing sql:\n" + sql, e);
        }
    }
    public static String getGrupo(String email){
        String sql="select grupo as grupo from user where email='"+email+"';";
        try{
            String grupo="";
            Statement statement = conn.createStatement();
            statement.execute(sql); 
            ResultSet rs=statement.getResultSet();
            while(rs.next()){
                grupo=rs.getString("grupo");
            }
            return grupo;
        }
        catch (SQLException e) {
            throw new RuntimeException("Error executing sql:\n" + sql, e);
        }
    }
    public static String getHash(String email){
        String sql="select hexPass as hexPass from user where email='"+email+"';";
        try{
            String hash="";
            Statement statement = conn.createStatement();
            statement.execute(sql); 
            ResultSet rs=statement.getResultSet();
            while(rs.next()){
                hash=rs.getString("hexPass");
            }
            return hash;
        }
        catch (SQLException e) {
            throw new RuntimeException("Error executing sql:\n" + sql, e);
        }
    }
    public static String getSalt(String email){
        String sql="select salt as salt from user where email='"+email+"';";
        try{
            String salt="";
            Statement statement = conn.createStatement();
            statement.execute(sql); 
            ResultSet rs=statement.getResultSet();
            while(rs.next()){
                salt=rs.getString("salt");
            }
            return salt;
        }
        catch (SQLException e) {
            throw new RuntimeException("Error executing sql:\n" + sql, e);
        }
    }
    public static int getAcessos(String email){
        String sql="select ctAcessos as ctAcessos from user where email='"+email+"';";
        try{
            int ctAcessos=0;
            Statement statement = conn.createStatement();
            statement.execute(sql); 
            ResultSet rs=statement.getResultSet();
            while(rs.next()){
                ctAcessos=rs.getInt("ctAcessos");
            }
            return ctAcessos;
        }
        catch (SQLException e) {
            throw new RuntimeException("Error executing sql:\n" + sql, e);
        }
    }
    public static void setCert(String pem,String email){
        String sql="update user set pemCert = "+"'"+pem+"'"+"where email='"+email+"';";
        try{
            Statement statement = conn.createStatement();
            statement.execute(sql); 
        }
        catch (SQLException e) {
            throw new RuntimeException("Error executing sql:\n" + sql, e);
        }
    }
    public static void setSalt(String salt,String email){
        String sql="update user set salt = "+"'"+salt+"'"+"where email='"+email+"';";
        try{
            Statement statement = conn.createStatement();
            statement.execute(sql); 
        }
        catch (SQLException e) {
            throw new RuntimeException("Error executing sql:\n" + sql, e);
        }
    }
    public static void setHash(String hash,String email){
        String sql="update user set hexPass = "+"'"+hash+"'"+"where email='"+email+"';";
        try{
            Statement statement = conn.createStatement();
            statement.execute(sql); 
        }
        catch (SQLException e) {
            throw new RuntimeException("Error executing sql:\n" + sql, e);
        }
    }
    public static String getCert(String email){
        String sql="select pemCert as pemCert from user where email='"+email+"';";
        try{
            String cert="";
            Statement statement = conn.createStatement();
            statement.execute(sql); 
            ResultSet rs=statement.getResultSet();
            while(rs.next()){
                cert=rs.getString("pemCert");
            }
            return cert;
        }
        catch (SQLException e) {
            throw new RuntimeException("Error executing sql:\n" + sql, e);
        }
    }
    public static void atualizaAcessos(String email){
        String sql="update user set ctAcessos = ctAcessos+1 where email='"+email+"';";
        try{
            Statement statement = conn.createStatement();
            statement.execute(sql); 

        }
        catch (SQLException e) {
            throw new RuntimeException("Error executing sql:\n" + sql, e);
        }
    }
    
    public static void fechaBd(){
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
