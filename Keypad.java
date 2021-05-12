import java.util.ArrayList;
import java.util.Random;

public class Keypad {

	public static ArrayList<String> createButtons() {
		String[] fonemas = new String[18];
		fonemas[0]="BA";
		fonemas[1]="BE";
		fonemas[2]="BO";
		fonemas[3]="CA";
		fonemas[4]="CE";
		fonemas[5]="CO";
		fonemas[6]="DA";
		fonemas[7]="DE";
		fonemas[8]="DO";
		fonemas[9]="FA";
		fonemas[10]="FE";
		fonemas[11]="FO";
		fonemas[12]="GA";
		fonemas[13]="GE";
		fonemas[14]="GO";
		fonemas[15]="HA";
		fonemas[16]="HE";
		fonemas[17]="HO";
		int cont = 0;
		String b = "";
		
		Random r = new Random();
		ArrayList<String> sorteados = new ArrayList<String>();
		ArrayList<String> botoes = new ArrayList<String>();
		
		while(sorteados.size()<18) {
			int i = r.nextInt(18);
			if(sorteados.indexOf(fonemas[i])==-1) {
				sorteados.add(fonemas[i]);
				if(cont<2) {
					b+=fonemas[i];
					b+="-";
					cont++;
				}
				else if(cont==2) {
					b+=fonemas[i];
					botoes.add(b);
					b = "";
					cont=0;					
				}
				
				//System.out.println(fonemas[i]);
			}
		}
		return botoes;
	
	}
}
