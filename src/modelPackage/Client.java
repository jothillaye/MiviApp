package modelPackage;

import java.util.GregorianCalendar;

public class Client {
	private int idResp;
	private String nom, prenom, nomPreResp;
	private GregorianCalendar dateNaiss;

	public Client(int idResp, String nom, String prenom, GregorianCalendar dateNaiss) {
		this.idResp = idResp;
		this.nom = nom;
		this.prenom = prenom;
		this.dateNaiss = dateNaiss;		
	}
	
	// Constructeur pour lister les clients lors d'une requ�te sql
	public Client() {}

	// Getters
	public int getIdResp() { return idResp; }
	public String getNom() { return nom;}
	public String getPrenom() { return prenom;}
	public GregorianCalendar getDateNaiss() { return dateNaiss;}
	public String getNomPreResp() { return nomPreResp;}	

	// Setters
	public void setNom(String nom) { this.nom = nom;}
	public void setPrenom(String prenom) { this.prenom = prenom;}
	public void setDateNaiss(GregorianCalendar dateNaiss) { this.dateNaiss = dateNaiss;}
	public void setNomPreResp(String nomPreResp) { this.nomPreResp = nomPreResp;}
}
