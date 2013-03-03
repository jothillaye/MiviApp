package modelPackage;

import static java.util.Calendar.DAY_OF_MONTH;
import static java.util.Calendar.MONTH;
import static java.util.Calendar.YEAR;
import java.util.GregorianCalendar;

public class Membre {
    private Integer idMembre, codePostal, gsm, fixe, idContact, provenance;
	private String nom, prenom, email, rue, numero, ville, dayOfMonth, month;
	private GregorianCalendar dateNaiss;
    private Boolean assistant, animateur, clientME, ecarte, supprime;
    private Float solde;

	public Membre(String nom, String prenom, String email, GregorianCalendar dateNaiss, Integer gsm, Integer fixe, String rue, String numero, Integer codePostal, String ville, Integer provenance, Integer idContact, Boolean assistant, Boolean animateur, Boolean clientME, Boolean ecarte, Float solde) {
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
        this.provenance = provenance;
        this.idContact = idContact;
        this.assistant = assistant;
        this.animateur = animateur;
        this.clientME = clientME;
        this.ecarte = ecarte;
        this.solde = solde;
	}
	
	// Constructeur pour lister les clients lors d'une requete sql
	public Membre() {}

	// Getters
    public Integer getIdMembre() { return idMembre;}
	public String getNom() { return nom.toUpperCase();}
	public String getPrenom() { return prenom.substring(0, 1).toUpperCase() + prenom.substring(1, prenom.length()).toLowerCase();}
	public String getEmail() { return email;}
    public GregorianCalendar getDateNaiss() { return dateNaiss;}
    public Integer getGsm() { return gsm;}
    public Integer getFixe() { return fixe;}
    public String getNumero() { return numero;}
    public String getRue() { return rue;}
    public Integer getCodePostal() { return codePostal;}
    public String getVille() { return ville;}
    public Integer getProvenance() { return provenance;}
    public Integer getIdContact() { return idContact;}
    public Boolean getAssistant() { return assistant;}
    public Boolean getAnimateur() { return animateur;}
    public Boolean getClientME() { return clientME;}
    public Boolean getEcarte() { return ecarte;}
    public Float getSolde() { return solde;}
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
    public void setGsm(Integer gsm) { this.gsm = gsm;}
    public void setFixe(Integer fixe) { this.fixe = fixe;}
    public void setRue(String rue) { this.rue = rue;}
    public void setNumero(String numero) { this.numero = numero;}
    public void setCodePostal(Integer codePostal) { this.codePostal = codePostal;}
    public void setVille(String ville) { this.ville = ville;}
    public void setProvenance(Integer provenance) { this.provenance = provenance;}
    public void setIdContact(Integer idContact) { this.idContact = idContact;}
    public void setAssistant(Boolean assistant) { this.assistant = assistant;}
    public void setAnimateur(Boolean animateur) { this.animateur = animateur;}
    public void setClientME(Boolean clientME) { this.clientME = clientME;}
    public void setEcarte(Boolean ecarte) { this.ecarte = ecarte;}
    public void setSolde(Float solde) { this.solde = solde;}    
    public void setSupprime(Boolean supprime) { this.supprime = supprime;}
}
