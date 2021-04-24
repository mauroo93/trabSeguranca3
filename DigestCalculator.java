import java.security.MessageDigest;
import java.security.*;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.io.*;



public class DigestCalculator {
	public static Map<String, ArrayList<Calculated>> dgstMap = new HashMap<String,ArrayList<Calculated>>();
	
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
        
        fileToMap(arqListaDgst);
        calcDigest(arqListaArq, tipoDgst);
        
    }
    
    public static void fileToMap(File f) throws FileNotFoundException, IOException {
    	try (BufferedReader br = new BufferedReader(new FileReader(f))) {
    	    String line;
    	    while ((line = br.readLine()) != null) {
    	    	ArrayList<Calculated> lc = new ArrayList<>();
    	    	String[] bline = line.split(" ");
    	    	String fileName = bline[0];
    	    	for(int i=1; i<bline.length;i+=2) {
    	    		Calculated c = new Calculated(bline[i],bline[i+1]);
    	    		lc.add(c);
    	    	}
    	    	dgstMap.put(fileName, lc);
       	    }
    	}	
    }
    
    public static void calcDigest(File arqListaArq, String tipoDgst) throws Exception {
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
                String fileName = arqs[i].getName();
                Calculated c = new Calculated(tipoDgst, calculatedDigest);
                
                
                /*FileWriter myWriter = new FileWriter(pathListaDgst, true);
                myWriter.write(arqs[i].getName()+" "+tipoDgst+" "+calculatedDigest +"\n");
                myWriter.close();*/
               
            }
            status("a", null);
        }catch(Exception  algErr){
            //TODO tratamento de erro
            System.out.print("Problema do tipo \t"+algErr);
            throw algErr;
        }
    }
    
    public static void status(String name, Calculated c) {
    	for (String i : dgstMap.keySet()) {
    		//dgstMap.get(i).clear();
    		  System.out.println(i);
    		  for(Calculated i1 : dgstMap.get(i)) {
    			  System.out.println(i1.getHex());
    		  }
    		}
    }
    
}

