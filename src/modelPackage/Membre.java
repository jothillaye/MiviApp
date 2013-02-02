package modelPackage;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import static java.util.Calendar.DATE;
import static java.util.Calendar.DAY_OF_MONTH;
import static java.util.Calendar.MONTH;
import static java.util.Calendar.YEAR;

public class Membre {
    private Integer id, numero, codePostal, gsm, fixe, idContact, provenance;
	private String nom, prenom, email, rue, ville, dayOfMonth, month;
	private GregorianCalendar dateNaiss;
    private Boolean assistant, animateur, clientME, ecarte;
    private Float soldeCrediteur;

	public Membre(String nom, String prenom, GregorianCalendar dateNaiss) {
		this.nom = nom;
		this.prenom = prenom;
		this.dateNaiss = dateNaiss;		
	}
	
	// Constructeur pour lister les clients lors d'une requete sql
	public Membre() {}

	// Getters
    public Integer getId() { return id;}
	public String getNom() { return nom;}
	public String getPrenom() { return prenom;}
	public String getEmail() { return email;}
    public GregorianCalendar getDateNaiss() { return dateNaiss;}
    public Integer getGsm() { return gsm;}
    public Integer getFixe() { return fixe;}
    public Integer getNumero() { return numero;}
    public String getRue() { return rue;}
    public Integer getCodePostal() { return codePostal;}
    public String getVille() { return ville;}
    public Integer getProvenance() { return provenance;}
    public Integer getIdContact() { return idContact;}
    public Boolean getAssistant() { return assistant;}
    public Boolean getAnimateur() { return animateur;}
    public Boolean getClientME() { return clientME;}
    public Boolean getEcarte() { return ecarte;}
    public Float getSoldeCrediteur() { return soldeCrediteur;}
    
    public String getFormatedDateNaiss() {    
        if(dateNaiss.get(DAY_OF_MONTH)<10) {
            dayOfMonth = 0+String.valueOf(dateNaiss.get(DAY_OF_MONTH));
        }
        else {
            dayOfMonth = String.valueOf(dateNaiss.get(DAY_OF_MONTH));
        }
        if(dateNaiss.get(MONTH)<10) {
            month = 0+String.valueOf(dateNaiss.get(MONTH));
        }
        else {
            month = String.valueOf(dateNaiss.get(MONTH));
        }
        return dayOfMonth+month+String.valueOf(dateNaiss.get(YEAR));
    }

	// Setters
    public void setId(Integer id) { this.id = id;}
	public void setNom(String nom) { this.nom = nom;}
	public void setPrenom(String prenom) { this.prenom = prenom;}
    public void setEmail(String email) { this.email = email;}
	public void setDateNaiss(GregorianCalendar dateNaiss) { this.dateNaiss = dateNaiss;}
    public void setGsm(Integer gsm) { this.gsm = gsm;}
    public void setFixe(Integer fixe) { this.fixe = fixe;}
    public void setRue(String rue) { this.rue = rue;}
    public void setNumero(Integer numero) { this.numero = numero;}
    public void setCodePostal(Integer codePostal) { this.codePostal = codePostal;}
    public void setVille(String ville) { this.ville = ville;}
    public void setProvenance(Integer provenance) { this.provenance = provenance;}
    public void setIdContact(Integer idContact) { this.idContact = idContact;}
    public void setAssistant(Boolean assistant) { this.assistant = assistant;}
    public void setAnimateur(Boolean animateur) { this.animateur = animateur;}
    public void setClientME(Boolean clientME) { this.clientME = clientME;}
    public void setEcarte(Boolean ecarte) { this.ecarte = ecarte;}
    public void setSoldeCrediteur(Float soldeCrediteur) { this.soldeCrediteur = soldeCrediteur;}    
}
