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
        calcDigest(arqListaDgst, arqListaArq, tipoDgst);
        
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
    
    public static void mapToFile(File f) throws FileNotFoundException, IOException {
    	FileWriter fw = new FileWriter(f);
    	
    	for (String i : dgstMap.keySet()) {
    		String line="";
    		line+=i;
    		line+=" ";
  		  for(Calculated i1 : dgstMap.get(i)) {
  			  line+=i1.getType();
  			  line+=" ";
  			  line+=i1.getHex();
  			  line+=" ";
  		  }
  		  	line+="\n";
  			fw.write(line);
  		}
    	fw.close();
    }
    
    public static void calcDigest(File pathListaDgst, File arqListaArq, String tipoDgst) throws Exception {
    	try{
            MessageDigest algoritm = MessageDigest.getInstance(tipoDgst);
            File[] arqs = arqListaArq.listFiles();
            boolean flag = false;
            
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
                String s = status(fileName, c);
                System.out.println(fileName + " " + tipoDgst + " " + calculatedDigest + " " + s);
                if(s.compareTo("NOT FOUND")==0) {
                	ArrayList<Calculated> lc = new ArrayList<>();
                	lc.add(c);
                	dgstMap.put(fileName, lc);
                	flag = true;
                }
                
                /*FileWriter myWriter = new FileWriter(pathListaDgst, true);
                myWriter.write(arqs[i].getName()+" "+tipoDgst+" "+calculatedDigest +"\n");
                myWriter.close();*/
               
            }
            if(flag) {
            	mapToFile(pathListaDgst);
            }
        }catch(Exception  algErr){
            //TODO tratamento de erro
            System.out.print("Problema do tipo \t"+algErr);
            throw algErr;
        }
    }
    
    public static String status(String name, Calculated c) {
    	//testa colisão
    	for (String i : dgstMap.keySet()) {
    		  for(Calculated i1 : dgstMap.get(i)) {
    			  if(i1.getHex().compareTo(c.getHex())==0) {
    				  if(i.compareTo(name)!=0) {
    					  //System.out.println("OK" + i1.getHex()+c.getHex());
    					  return "COLISION";
    				  }
    			  }
    		  }
    		}
    	//testa OK
    	for (String i : dgstMap.keySet()) {
    		  for(Calculated i1 : dgstMap.get(i)) {
    			  if(i1.getHex().compareTo(c.getHex())==0) {
    				  if(i.compareTo(name)==0) {
    					  //System.out.println("OK" + i1.getHex()+c.getHex());
    					  return "OK";
    				  }
    			  }
    		  }
    		}
    	//testa não OK
    	for (String i : dgstMap.keySet()) {
    		if(i.compareTo(name)==0) {
    			for(Calculated i1 : dgstMap.get(i)) {
    				if(i1.getType().compareTo(c.getType())==0 && i1.getHex().compareTo(c.getHex())!=0) {
    					//System.out.println("NOT OK" + i1.getHex()+c.getHex());
    					return "NOT OK";
    				}
    			}
    		}
    	}
    	//System.out.println("NOT FOUND"+name);
    	return "NOT FOUND";
    	
    }
    
}

