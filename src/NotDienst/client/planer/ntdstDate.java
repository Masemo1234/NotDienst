package NotDienst.client.planer;

public class ntdstDate {
	
	private String date;
	private String typ;
	private int erstellungsNr;
	private int punkte;
	private int aId;
	public ntdstDate(String date, String typ, int erstellungsNr, int punkte) {
		super();
		this.date = date;
		this.typ = typ;
		this.erstellungsNr = erstellungsNr;
		this.punkte = punkte;
	}
	public ntdstDate(String date, String typ, int erstellungsNr, int punkte,
			int aId) {
		super();
		this.date = date;
		this.typ = typ;
		this.erstellungsNr = erstellungsNr;
		this.punkte = punkte;
		this.aId = aId;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getTyp() {
		return typ;
	}
	public void setTyp(String typ) {
		this.typ = typ;
	}
	public int getErstellungsNr() {
		return erstellungsNr;
	}
	public void setErstellungsNr(int erstellungsNr) {
		this.erstellungsNr = erstellungsNr;
	}
	public int getPunkte() {
		return punkte;
	}
	public void setPunkte(int punkte) {
		this.punkte = punkte;
	}
	public int getaId() {
		return aId;
	}
	public void setaId(int aId) {
		this.aId = aId;
	}
	public String toString(){
		return ""+date+";"+typ+";"+punkte+";"+aId;
		
	}

}
