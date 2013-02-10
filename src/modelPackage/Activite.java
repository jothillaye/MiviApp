package modelPackage;

import java.util.GregorianCalendar;

public class Activite {
    private Integer idActivite, idFormation, promotion, accompte, tva;
    private GregorianCalendar dateDeb, dateFin;
    private Float prix;

	public Activite(Integer idFormation, Integer promotion, GregorianCalendar dateDeb, GregorianCalendar dateFin, Float prix, Integer accompte, Integer tva) {
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
    public Integer getAccompte() {return accompte;}
    public Integer getTva() {return tva;}
    public GregorianCalendar getDateDeb() {return dateDeb;}
    public GregorianCalendar getDateFin() {return dateFin;}
    public Float getPrix() {return prix;}
    
    public void setIdActivite(Integer idActivite) {this.idActivite = idActivite;}
    public void setIdFormation(Integer idFormation) {this.idFormation = idFormation;}
    public void setPromotion(Integer promotion) {this.promotion = promotion;}
    public void setAccompte(Integer accompte) {this.accompte = accompte;}
    public void setTva(Integer tva) {this.tva = tva;}
    public void setDateDeb(GregorianCalendar dateDeb) {this.dateDeb = dateDeb;}
    public void setDateFin(GregorianCalendar dateFin) {this.dateFin = dateFin;}
    public void setPrix(Float prix) {this.prix = prix;}   
}