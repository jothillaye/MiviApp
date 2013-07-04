package controllerPackage;

import exceptionPackage.DBException;
import exceptionPackage.NotIdentified;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import javax.swing.JTable;
import javax.swing.table.TableModel;
import modelPackage.Activite;
import modelPackage.Inscription;
import modelPackage.Membre;
/**
 *
 * @author Joachim
 */
public class ExportToExcel {
    private ApplicationController app = new ApplicationController();
    
    public void ExportTableToExcel(JTable table, String fileName, String formInt, Integer idInscription) throws IOException, NotIdentified, DBException {        
        TableModel model = table.getModel();
        File file = File.createTempFile(fileName, ".xls"); 
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file),"iso-8859-1"));
        
        if(table.getName().equals("tablePaiement") == true) {
            out.write("Paiements de la formation : " + formInt + "\n");
        }
        else {
            out.write("Accords de Paiement de la formation : " + formInt + "\n");
        }
        
        Inscription ins = app.getInscription(idInscription);
        Membre me = app.getMembre(ins.getIdMembre());
        out.write(me.getPrenom().toUpperCase() + " " + me.getNom().toUpperCase() + "\n");
            
        for(int i=0; i < model.getColumnCount(); i++) {
            out.write(model.getColumnName(i) + "\t");
        }
        out.write("\n");
 
        for(int i=0; i< model.getRowCount(); i++) {
            for(int j=0; j < model.getColumnCount(); j++) {
                out.write(model.getValueAt(i,j).toString() + "\t");
			}
			out.write("\n");
		} 
		out.close();
        
        Runtime runtime = Runtime.getRuntime();
        runtime.exec("cmd /C start excel " + file);
    }
    
    public void ExportListToExcel(String formInt, Integer idActivite, Integer typeExport) throws IOException, NotIdentified, DBException {        
        File file = File.createTempFile("ListIns", ".xls");        
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file),"windows-1252"));        
        
        ArrayList<Inscription> arrayIns = app.listInscription(idActivite, 0);
        
        if(typeExport == 0){
            out.write(formInt + "\n");

            Activite act = app.getActivite(idActivite);
            if(act.getPromotion() != null) {            
                out.write("Promotion : " + act.getPromotion());
            }
            if(act.getDateDeb() != null) {
                out.write("\nDate : " + act.getFormatedDateDeb());
            }
            out.write("\t\t\t\tPrix de la formation: " + act.getPrix() + "€");

            for(Inscription ins : arrayIns) {
                Membre me = ins.getMembre();
                if(typeExport == 0){            
                    out.write("\n\n" + me.getPrenom() + " " + me.getNom() + "\n");            

                    if(me.getGsm() != null && me.getGsm().isEmpty() == false) {
                        out.write("GSM : " + me.getGsm());
                    }

                    Float solde = app.getSolde(ins.getIdInscription());
                    Float prixSpecial = app.getInscription(ins.getIdInscription()).getTarifSpecial();
                    if(prixSpecial != null && prixSpecial != 0) {
                        solde -= prixSpecial;
                    }
                    else {
                        solde -= act.getPrix();
                    }
                    String soldeExcel;            
                    if(solde > 0){
                        soldeExcel = "Trop payé de : " + solde.toString() + "€";
                    }
                    else if(solde < 0){
                        solde *= -1;
                        soldeExcel = "A Payer : " + solde.toString() + "€";
                        solde *= -1;
                    }
                    else {                
                        soldeExcel = "Payé";
                    }
                    out.write("\t\t\t\t" + soldeExcel);
                }
            }
        }
        else {
            for(Inscription ins : arrayIns) {
                Membre me = ins.getMembre();
                out.write("\n" + me.getPrenom() + " " + me.getNom());
                if(me.getEmail() != null){
                    out.write("\t\t" + me.getEmail());
                }
            }            
        }
        
        out.close();
        
        Runtime runtime = Runtime.getRuntime();
        runtime.exec("cmd /C start excel " + file);
    }
}
