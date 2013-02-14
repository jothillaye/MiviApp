package modelPackage;

public class Inscription {
    private Integer idActivite, idMembre;
    private Boolean abandonne, certifie;
    private Float tarifSpecial;
    private Membre membre;

	public Inscription(Integer idActivite, Integer idMembre, Float tarifSpecial, Boolean abandonne, Boolean certifie) {
        this.idActivite = idActivite;
        this.idMembre = idMembre;
        this.tarifSpecial = tarifSpecial;
        this.abandonne = abandonne;
        this.certifie = certifie;
    }
	
	public Inscription() {}

    public Integer getIdActivite() {return idActivite;}
    public Integer getIdMembre() {return idMembre;}
    public Boolean getAbandonne() {return abandonne;}
    public Boolean getCertifie() {return certifie;}
    public Float getTarifSpecial() {return tarifSpecial;}
    public Membre getMembre() {return membre;}
    
    public void setIdActivite(Integer idActivite) {this.idActivite = idActivite;}
    public void setIdMembre(Integer idMembre) {this.idMembre = idMembre;}
    public void setAbandonne(Boolean abandonne) {this.abandonne = abandonne;}
    public void setCertifie(Boolean certifie) {this.certifie = certifie;}
    public void setTarifSpecial(Float tarifSpecial) {this.tarifSpecial = tarifSpecial;}    
    public void setMembre(Membre membre) {this.membre = membre;}
}