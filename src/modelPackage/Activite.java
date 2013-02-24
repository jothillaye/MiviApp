package modelPackage;

import static java.util.Calendar.DAY_OF_MONTH;
import static java.util.Calendar.MONTH;
import static java.util.Calendar.YEAR;
import java.util.GregorianCalendar;

public class Activite {
    private Integer idActivite, idFormation, promotion;
    private GregorianCalendar dateDeb, dateFin;
    private Float prix, accompte, tva;
    private String dayOfMonth, month;

	public Activite(Integer idFormation, Integer promotion, GregorianCalendar dateDeb, GregorianCalendar dateFin, Float prix, Float accompte, Float tva) {
        this.idFormation = idFormation;
        this.promotion = promotion;
        this.dateDeb = dateDeb;
        this.dateFin = dateFin; 
        this.prix = prix;
        this.accompte = accompte;
        this.tva = tva;
    }
	
	public Activite() {}

    public Integer getIdActivite() {return idActivite;}
    public Integer getIdFormation() {return idFormation;}
    public Integer getPromotion() {return promotion;}
    public Float getAccompte() {return accompte;}
    public Float getTva() {return tva;}
    public GregorianCalendar getDateDeb() {return dateDeb;}
    public GregorianCalendar getDateFin() {return dateFin;}
    public Float getPrix() {return prix;}
    
    public String getFormatedDateDeb() {    
        if(dateDeb.get(DAY_OF_MONTH)<10) {
            dayOfMonth = 0+String.valueOf(dateDeb.get(DAY_OF_MONTH));
        }
        else {
            dayOfMonth = String.valueOf(dateDeb.get(DAY_OF_MONTH));
        }
        if(dateDeb.get(MONTH)<9) {
            month = 0+String.valueOf(dateDeb.get(MONTH)+1);
        }
        else {
            month = String.valueOf(dateDeb.get(MONTH)+1);
        }
        return dayOfMonth+month+String.valueOf(dateDeb.get(YEAR));
    }
    
    public String getFormatedDateFin() {    
        if(dateFin.get(DAY_OF_MONTH)<10) {
            dayOfMonth = 0+String.valueOf(dateFin.get(DAY_OF_MONTH));
        }
        else {
            dayOfMonth = String.valueOf(dateFin.get(DAY_OF_MONTH));
        }
        if(dateFin.get(MONTH)<9) {
            month = 0+String.valueOf(dateFin.get(MONTH)+1);
        }
        else {
            month = String.valueOf(dateFin.get(MONTH)+1);
        }
        return dayOfMonth+month+String.valueOf(dateFin.get(YEAR));
    }
    
    public void setIdActivite(Integer idActivite) {this.idActivite = idActivite;}
    public void setIdFormation(Integer idFormation) {this.idFormation = idFormation;}
    public void setPromotion(Integer promotion) {this.promotion = promotion;}
    public void setAccompte(Float accompte) {this.accompte = accompte;}
    public void setTva(Float tva) {this.tva = tva;}
    public void setDateDeb(GregorianCalendar dateDeb) {this.dateDeb = dateDeb;}
    public void setDateFin(GregorianCalendar dateFin) {this.dateFin = dateFin;}
    public void setPrix(Float prix) {this.prix = prix;}   
}