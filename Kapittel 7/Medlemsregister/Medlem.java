
    /* Kapittel 5, oppgave
   Tekst om programmet
   C.W.R Jan 2022
*/
import static java.lang.System.*;
import static javax.swing.JOptionPane.*;
import static java.lang.Integer.*;
import static java.lang.Double.*;
import static java.lang.Math.*;
import java.util.*;

public class Medlem {
	//Objektvariabler-attrubutter-instans
	//Bruker innkapsling - begrenset tilgang til objektvariablene

	private int nr;
	private int tlf;
	private String adresse;
	private String fornavn;
	private String etternavn;


	//Konstruktør
	public Medlem(int inNr, int inTlf, String inFornavn, String inEnavn, String inAdresse) {
		nr = inNr;
        tlf = inTlf;
		adresse = inAdresse;
		fornavn = pent(inFornavn);
		etternavn = pent(inEnavn);
	}

	//Objektmetoder
	//get metoder for innkapsling
	public int getNr(){return nr;}
	public String getFornavn() {return pent(fornavn);}
	public String getEtternavn() {return pent(etternavn);}
	public String getAdresse() {return adresse;}
	public int getTlf() {return tlf;}
	//Set metoder
	public void setNr(int nyttNr){nr = nyttNr;}
	public void setEtternavn(String nyttEtternavn){etternavn = nyttEtternavn;}
	public void setFornavn(String nyttFornavn){fornavn = nyttFornavn;}
	public void setAdresse(String nyttAdresse){adresse = nyttAdresse;}
	public void setTlf(int nyttTlf){tlf = nyttTlf;}

	public String toString(){
		return nr + " ; " + fornavn + " ; " + etternavn + " ; " + adresse + " ; " + tlf;
	}
	public String skrivEtternavn(){
		return etternavn + " ; " + fornavn + " ; " + tlf + " ; " + adresse + " ; " + nr;
	}
	public boolean equals(Medlem annen){
		if(annen == null)
			return false;
		return nr == annen.nr;
	}
	public String navn(){
		return pent(etternavn) + ", " + pent(fornavn);
	}
	private String pent(String navn){
		char i = navn.toUpperCase().charAt(0);
		String n = navn.toLowerCase();
		return i+n.substring(1);
	}


}

