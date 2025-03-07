import javafx.application.Application;  
import javafx.stage.Stage;  
import javafx.scene.Scene;  
import javafx.scene.layout.FlowPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;  
import javafx.geometry.Pos;  
import java.util.*;
import java.io.*;

public class Oppg3 extends Application {

    final static int MAX = 100;     // Max antall kontakter
    
    static int antallKontakter = 0; // Faktisk antall kontakter

    String[] tlfTab  = new String[MAX];
    String[] navnTab = new String[MAX];

    // GUI-komponenter som skal brukes av flere metoder 
    private Button btnOk, btnTlf, btnNavn, btnNy, btnSlett;
    private TextField txtTlf, txtNavn, txtStatus;
    private TextArea flate;

    public void start(Stage vindu) {
    // velger linjeorganisering av komponenter
    FlowPane panel = new FlowPane();
    panel.setHgap(0);
    panel.setVgap(5);
    panel.setAlignment(Pos.CENTER);
    // Oppg.figur viser sentrert komponenter på hver "linje"
    Scene scene = new Scene(panel, 250, 330);
    vindu.setTitle("kontakter");
    vindu.setScene(scene);
    vindu.setResizable(false);
    vindu.show();  

    // bygger opp GUI-et, komponent for komponent
    btnTlf = new Button(" Sorter på tlf.nr ");
    btnTlf.setOnAction(e -> behandleKlikk(e));
    panel.getChildren().add(btnTlf);

    // begge knappene har fått litt lengre topptekst
    // rett og slett fordi det er plass til det her 

    // legger inn litt ekstra avstand mellom knappene 
    panel.getChildren().add( new Label("          ") );

    btnNavn = new Button("Sorter på navn");
    btnNavn.setOnAction(e -> behandleKlikk(e));
    panel.getChildren().add(btnNavn);

    flate = new TextArea();
    flate.setPrefColumnCount(19);
    // Oppgaven sier 20 kolonner, men 20 blir for bredt så da tar jeg 19
    flate.setPrefRowCount(10);
    panel.getChildren().add(flate);

    panel.getChildren().add( new Label("Tlf: ") );
    txtTlf = new TextField();
    txtTlf.setPrefColumnCount(6);
    panel.getChildren().add(txtTlf);

    panel.getChildren().add( new Label("Navn: ") );
    txtNavn = new TextField();
    txtNavn.setPrefColumnCount(7);
    panel.getChildren().add(txtNavn);

    btnNy = new Button(" Ny kontakt");
    btnNy.setOnAction(e -> behandleKlikk(e));
    panel.getChildren().add(btnNy);
    panel.getChildren().add( new Label("            ") );
    btnSlett = new Button(" Slett kontakt ");
    btnSlett.setOnAction(e->behandleKlikk(e));
    panel.getChildren().add(btnSlett);

    txtStatus = new TextField();
    txtStatus.setEditable(false);
    txtStatus.setPrefColumnCount(19);
    // Oppgaven sier 20 kolonner, men 20 blir for bredt så da tar jeg 19
    panel.getChildren().add(txtStatus);

    // fyller GUI-et med kontakter fra fil
    hentKontakterTilGUI();
    // I oppgaveteksten heter denne metoden actionPerformed  
    // siden dette er en innebygd del av Swing‐grafikken.
    // Her byttes navnet som i læreboka, men har samme innhold.
    }
    public void behandleKlikk(ActionEvent e) {  
        if ( e.getSource() == btnTlf )
          sorterPåTlf();  // Sorterer på tlf.nr
   else if ( e.getSource() == btnNavn )             
          sorterPåNavn();  // Sorterer på navn         
   else if ( e.getSource() == btnNy )   
          nyKontakt();    
   else if ( e.getSource() == btnSlett )   
          slettKontakt();  
        }
    
    private void nyKontakt() {
        try {
            String tlf = txtTlf.getText();
            String navn = txtNavn.getText();
        if (tlf.length() < 1 || navn.length() < 1)
            txtStatus.setText("Fyll ut både feltet [Tlf] og [Navn]");
        else {
            tlfTab[antallKontakter] = tlf + ": " + navn;
            navnTab[antallKontakter] = navn + ": " + tlf;
            taBackUp();
            // Metoden sørger for at kontakt‐fila blir oppdatert med
            // endringen som nå er gjort. Endringen vises også i GUI‐et
            // ved å hente inn igjen kontaktinfo fra oppdatert fil.
            hentKontakterTilGUI();
            txtStatus.setText("En kontakt er lagt til");
            txtTlf.setText("");
            txtNavn.setText("");
            }                
        }
        catch(Exception e) {
            txtStatus.setText("Kunne ikke legge til kontakt!");
            System.out.println("Problem: " + e.toString());
        }
    }

        private void slettKontakt() {
            try {
                String tlf = txtTlf.getText();
                if ( tlf.length() > 0 ) {
                    int pos = 0;
                    boolean funnet = false;
                    while(!funnet && pos < antallKontakter) {
                        String[] dataTab = tlfTab[pos].split(": ");
                        if ( tlf.equals(dataTab[0]) )
                            funnet = true;
                        else
                            pos++;
                    }

                    if (funnet) {
                        for (int i=pos; i<antallKontakter; i++)
                            tlfTab[i] = tlfTab[i+1];
                            // går dette bra hvis du sletter siste kontakt?
                        antallKontakter--;
                        taBackUp(); // Se forklaring under nyKontakt()-metoden
                        hentKontakterTilGUI();
                        txtStatus.setText("En kontakt er slettet");
                        txtTlf.setText("");
                        txtNavn.setText("");
                    }
                    else 
                    txtStatus.setText("Ukjent tlf.nr!");
                    }
                }   
                catch(Exception e) {
                    txtStatus.setText("Kunne ikke slette!");
                }
            }
            private void hentKontakterTilGUI() {
                try {
                File fil = new File("Kontakter.txt");
                Scanner leser = new Scanner(fil);
                int nr=0;
                while ( leser.hasNextLine() ) {
                    String linje = leser.nextLine();
                    String[] dataTab = linje.split(": ");
                    String tlf = dataTab[0];
                    String navn = dataTab[1];
                    tlfTab[nr] = tlf + ": " + navn;
                    navnTab[nr] = navn + ": " + tlf;
                    nr++;
                }
                antallKontakter = nr;
                sorterPåTlf();
                txtStatus.setText(nr + " Kontakter er lastet inn");
                
            }
            catch (Exception e) {
                txtStatus.setText("Kunne ikke laste kontaker!");
            }
        }
           private void taBackUp() {
               try{
                   PrintWriter skriver = new PrintWriter("kontakter.txt");
                   for (int i=0; i<antallKontakter; i++) 
                       skriver.println(tlfTab[i]);
                       skriver.close();
                   
               }
               catch (Exception e) {
                   // gir ikke statusmelding på backup, uansett ok eller ikke
               }
           } 
           
           private void sorterPåTlf() {
               sorter(tlfTab);
           }

           private void sorterPåNavn() {
               sorter(navnTab);
           }

           private void sorter(String[] tab) {
               Arrays.sort(tab, 0, antallKontakter);
               flate.setText("");
               for ( int i=0; i<antallKontakter; i++) 
                flate.appendText(tab[i] + "\n");
               txtStatus.setText(antallKontakter + " kontakter");
           }

           public static void main(String[] args) {
               launch(args);
           }

    }

    


