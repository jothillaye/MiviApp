package modelPackage;

import static java.util.Calendar.DAY_OF_MONTH;
import static java.util.Calendar.MONTH;
import static java.util.Calendar.YEAR;
import java.util.GregorianCalendar;

public class Paiement {
    private Integer idPaiement, idActivite, idMembre, typePaiement, typeIns;
    private Boolean accord;
    private Float montant;
    private GregorianCalendar datePaiement, oldDate;
    private String dayOfMonth, month;

	public Paiement(Integer idActivite, Integer idMembre, Float montant, GregorianCalendar datePaiement, Integer typePaiement, Boolean accord) {
        this.idActivite = idActivite;
        this.idMembre = idMembre;
        this.montant = montant;
        this.datePaiement = datePaiement;
        this.typePaiement = typePaiement;
        this.accord = accord;
    }
	
	public Paiement() {}

    public Integer getIdActivite() {return idActivite;}
    public Integer getIdMembre() {return idMembre;}
    public Float getMontant() {return montant;}
    public GregorianCalendar getDatePaiement() {return datePaiement;}
    public Boolean getAccord() {return accord;}
    public Integer getTypePaiement() {return typePaiement;}
    public GregorianCalendar getOldDate() {return oldDate;}
    public Integer getIdPaiement() {return idPaiement;}
    public Integer getTypeIns() {return typeIns;}
    
    public String getDatePaiementFormated() {    
        if(datePaiement.get(DAY_OF_MONTH)<10) {
            dayOfMonth = 0+String.valueOf(datePaiement.get(DAY_OF_MONTH));
        }
        else {
            dayOfMonth = String.valueOf(datePaiement.get(DAY_OF_MONTH));
        }
        if(datePaiement.get(MONTH)<9) {
            month = 0+String.valueOf(datePaiement.get(MONTH)+1);
        }
        else {
            month = String.valueOf(datePaiement.get(MONTH)+1);
        }
        return dayOfMonth+"/"+month+"/"+String.valueOf(datePaiement.get(YEAR));
    }
    
    public void setIdActivite(Integer idActivite) {this.idActivite = idActivite;}
    public void setIdMembre(Integer idMembre) {this.idMembre = idMembre;}
    public void setMontant(Float montant) {this.montant = montant;}
    public void setDatePaiement(GregorianCalendar datePaiement) {this.datePaiement = datePaiement;}
    public void setAccord(Boolean accord) {this.accord = accord;}
    public void setTypePaiement(Integer typePaiement) {this.typePaiement = typePaiement;}
    public void setOldDate(GregorianCalendar oldDate) {this.oldDate = oldDate;}
    public void setIdPaiement(Integer idPaiement) {this.idPaiement = idPaiement;}
    public void setTypeIns(Integer typeIns) {this.typeIns = typeIns;}

    
}