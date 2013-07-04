package modelPackage;

public class Inscription {
    private Integer idInscription, idActivite, idMembre, typeIns;
    private Boolean abandonne, certifie;
    private Float tarifSpecial, solde;
    private Membre membre;

	public Inscription(Integer idActivite, Integer idMembre, Float tarifSpecial, Boolean abandonne, Boolean certifie, Integer typeIns) {
        this.idActivite = idActivite;
        this.idMembre = idMembre;
        this.tarifSpecial = tarifSpecial;
        this.abandonne = abandonne;
        this.certifie = certifie;
        this.typeIns = typeIns;
    }
	
	public Inscription() {}
    
    public Integer getIdInscription() {return idInscription;}
    public Integer getIdActivite() {return idActivite;}
    public Integer getIdMembre() {return idMembre;}
    public Boolean getAbandonne() {return abandonne;}
    public Boolean getCertifie() {return certifie;}
    public Float getTarifSpecial() {return tarifSpecial;}
    public Membre getMembre() {return membre;}
    public Integer getTypeIns() {return typeIns;}
    public Float getSolde() {return solde;}
    
    public void setIdInscription(Integer idInscription) {this.idInscription = idInscription;}
    public void setIdActivite(Integer idActivite) {this.idActivite = idActivite;}
    public void setIdMembre(Integer idMembre) {this.idMembre = idMembre;}
    public void setAbandonne(Boolean abandonne) {this.abandonne = abandonne;}
    public void setCertifie(Boolean certifie) {this.certifie = certifie;}
    public void setTarifSpecial(Float tarifSpecial) {this.tarifSpecial = tarifSpecial;}    
    public void setMembre(Membre membre) {this.membre = membre;}
    public void setTypeIns(Integer typeIns) {this.typeIns = typeIns;}
    public void setSolde(Float solde) {this.solde = solde;}    

}