package NotDienst.client.nc;

import java.util.Vector;

import NotDienst.client.planer.ntdstDate;

public class Arzt {
	public int aid;
	public String nachname;
	public String vorname;
	public int punkte;
	public Vector<ntdstDate> dates;
	public String StrHausnr;
	public String PLZStadt;
	public String Titel;
	public String TelNr;
	public String anrede;
	public String AnsageID;
	public Arzt(int aid, String nachname, String vorname, int punkte) {
		super();
		this.aid = aid;
		this.nachname = nachname;
		this.vorname = vorname;
		this.punkte = punkte;
		
		dates = new Vector<ntdstDate>();
	}
	public Arzt(int aid, String nachname, String vorname, int punkte, String StrHausnr, String PLZStadt, String Titel, String TelNr, String anrede, String AnsageID) {
		super();
		this.aid = aid;
		this.nachname = nachname;
		this.vorname = vorname;
		this.punkte = punkte;
		this.StrHausnr = StrHausnr;
		this.PLZStadt = PLZStadt;
		this.Titel = Titel;
		this.TelNr = TelNr;
		this.anrede = anrede;
		this.AnsageID = AnsageID;
		
		dates = new Vector<ntdstDate>();
	}
	public String getStrHausnr() {
		return StrHausnr;
	}
	public String getPLZStadt() {
		return PLZStadt;
	}
	public String getTitel() {
		return Titel;
	}
	public String getTelNr() {
		return TelNr;
	}
	public String getAnrede() {
		return anrede;
	}
	public String getAnsageID() {
		return AnsageID;
	}
	public int getPunkte() {
		return punkte;
	}
	public void setPunkte(int punkte) {
		this.punkte = punkte;
	}
	public int getAid() {
		return aid;
	}
	public String getNachname() {
		return nachname;
	}
	public String getVorname() {
		return vorname;
	}
	public Vector<ntdstDate> getDates(){
		return dates;
	}
	public void addDate(ntdstDate nd){
		dates.addElement(nd);
	}
	public String toStringA() {
		String value= "ID: "+aid+"  Nachname: "+Titel+" "+nachname+"  Vorname: "+vorname+"\n TelNr: "+TelNr+"  AnsageID: "+AnsageID;
		return value;
	}
	public String toStringAdresse(){
		String ergebnis = anrede+" "+Titel+"\n"+vorname+" "+nachname+"\n"+StrHausnr+"\n"+PLZStadt;
		return ergebnis;
	}

}
