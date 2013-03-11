package modelPackage;

import static java.util.Calendar.DAY_OF_MONTH;
import static java.util.Calendar.MONTH;
import static java.util.Calendar.YEAR;
import java.util.GregorianCalendar;

public class Membre {
    private Integer idMembre, codePostal, idContact, provenance;
	private String nom, prenom, email, rue, numero, ville, pays, gsm, fixe, dayOfMonth, month;
	private GregorianCalendar dateNaiss;
    private Boolean assistant, animateur, supprime;

	public Membre(String nom, String prenom, String email, GregorianCalendar dateNaiss, String gsm, String fixe, String rue, String numero, Integer codePostal, String ville, String pays, Integer provenance, Integer idContact) {
		this.nom = nom;
		this.prenom = prenom;
        this.email = email;
		this.dateNaiss = dateNaiss;		
        this.gsm = gsm;
        this.fixe = fixe;
        this.rue = rue;
        this.numero = numero;
        this.codePostal = codePostal;
        this.ville = ville;
        this.pays = pays;
        this.provenance = provenance;
        this.idContact = idContact;
	}
	
	// Constructeur pour lister les clients lors d'une requete sql
	public Membre() {}

	// Getters
    public Integer getIdMembre() { return idMembre;}
	public String getNom() { return nom.toUpperCase();}
		public String getPrenom() { 
        if(prenom != null && prenom.isEmpty() == false && prenom.length() >= 2) {
            return prenom.substring(0, 1).toUpperCase() + prenom.substring(1, prenom.length()).toLowerCase();
        }
        else {            
            return prenom;
        }
    }  
	public String getEmail() { return email;}
    public GregorianCalendar getDateNaiss() { return dateNaiss;}
    public String getGsm() { return gsm;}
    public String getFixe() { return fixe;}
    public String getNumero() { return numero;}
    public String getRue() { return rue;}
    public Integer getCodePostal() { return codePostal;}
    public String getVille() { return ville;}
    public String getPays() {return pays;}
    public Integer getProvenance() { return provenance;}
    public Integer getIdContact() { return idContact;}
    public Boolean getAssistant() { return assistant;}
    public Boolean getAnimateur() { return animateur;}
    public Boolean getSupprime() { return supprime;}
    
    public String getFormatedDateNaiss() {    
        if(dateNaiss.get(DAY_OF_MONTH)<10) {
            dayOfMonth = 0+String.valueOf(dateNaiss.get(DAY_OF_MONTH));
        }
        else {
            dayOfMonth = String.valueOf(dateNaiss.get(DAY_OF_MONTH));
        }
        if(dateNaiss.get(MONTH)<9) {
            month = 0+String.valueOf(dateNaiss.get(MONTH)+1);
        }
        else {
            month = String.valueOf(dateNaiss.get(MONTH)+1);
        }
        return dayOfMonth+month+String.valueOf(dateNaiss.get(YEAR));
    }

	// Setters
    public void setIdMembre(Integer idMembre) { this.idMembre = idMembre;}
	public void setNom(String nom) { this.nom = nom;}
	public void setPrenom(String prenom) { this.prenom = prenom;}
    public void setEmail(String email) { this.email = email;}
	public void setDateNaiss(GregorianCalendar dateNaiss) { this.dateNaiss = dateNaiss;}
    public void setGsm(String gsm) { this.gsm = gsm;}
    public void setFixe(String fixe) { this.fixe = fixe;}
    public void setRue(String rue) { this.rue = rue;}
    public void setNumero(String numero) { this.numero = numero;}
    public void setCodePostal(Integer codePostal) { this.codePostal = codePostal;}
    public void setVille(String ville) { this.ville = ville;}
    public void setPays(String pays) {this.pays = pays;}
    public void setProvenance(Integer provenance) { this.provenance = provenance;}
    public void setIdContact(Integer idContact) { this.idContact = idContact;}
    public void setAssistant(Boolean assistant) { this.assistant = assistant;}
    public void setAnimateur(Boolean animateur) { this.animateur = animateur;}
    public void setSupprime(Boolean supprime) { this.supprime = supprime;}        
}
