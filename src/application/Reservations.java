package application;

import java.time.LocalDate;
import javafx.beans.property.SimpleStringProperty;

public class Reservations {

	private SimpleStringProperty CName;
	private SimpleStringProperty CEmail;
	private SimpleStringProperty CPhone;
	private SimpleStringProperty CDocumentType;
	private SimpleStringProperty CDocumentNo;
	private SimpleStringProperty RoomNum;
	private SimpleStringProperty RecName;
	private LocalDate FromDate, ToDate;

	public Reservations(String CName, String CEmail, String CPhone, String CDocumentType, String CDocumentNo,
			String RoomNum, String RecName, LocalDate FromDate, LocalDate ToDate) {

		this.CName = new SimpleStringProperty(CName);
		this.CEmail = new SimpleStringProperty(CEmail);
		this.CPhone = new SimpleStringProperty(CPhone);
		this.CDocumentType = new SimpleStringProperty(CDocumentType);
		this.CDocumentNo = new SimpleStringProperty(CDocumentNo);
		this.RoomNum = new SimpleStringProperty(RoomNum);
		this.RecName = new SimpleStringProperty(RecName);
		this.FromDate = FromDate;
		this.ToDate = ToDate;

	}

	public String getCName() {
		return CName.get();
	}

	public String getCEmail() {
		return CEmail.get();
	}

	public String getCPhone() {
		return CPhone.get();
	}

	public String getCDocumentType() {
		return CDocumentType.get();
	}

	public String getCDocumentNo() {
		return CDocumentNo.get();
	}

	public String getRoomNum() {
		return RoomNum.get();
	}

	public String getRecName() {
		return RecName.get();
	}

	public LocalDate getFromDate() {
		return FromDate;
	}

	public LocalDate getToDate() {
		return ToDate;
	}

}
