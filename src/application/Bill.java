package application;

import java.time.LocalDate;

import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

public class Bill {

	private SimpleStringProperty name, phone, docNo, status, room;
	private SimpleIntegerProperty nights;
	private SimpleLongProperty billId;
	private SimpleFloatProperty totalCost;
	private LocalDate dateIssued, fromD, toD;

	public Bill(String name, String phone, String docNo, Long billId, int nights, float totalCost, String status,
			LocalDate dateIssued) {

		this.name = new SimpleStringProperty(name);
		this.phone = new SimpleStringProperty(phone);
		this.docNo = new SimpleStringProperty(docNo);
		this.billId = new SimpleLongProperty(billId);
		this.nights = new SimpleIntegerProperty(nights);
		this.totalCost = new SimpleFloatProperty(totalCost);
		this.status = new SimpleStringProperty(status);
		this.dateIssued = dateIssued;
	}

	public Bill(String name, String phone, String docNo, int nights, float totalCost, String status, LocalDate fromD,
			LocalDate toD, String room) {

		this.name = new SimpleStringProperty(name);
		this.phone = new SimpleStringProperty(phone);
		this.docNo = new SimpleStringProperty(docNo);
		this.nights = new SimpleIntegerProperty(nights);
		this.totalCost = new SimpleFloatProperty(totalCost);
		this.status = new SimpleStringProperty(status);
		this.fromD = fromD;
		this.toD = toD;
		this.room = new SimpleStringProperty(room);

	}

	public String getRoom() {

		return room.get();
	}

	public LocalDate getFromD() {

		return fromD;
	}

	public LocalDate getToD() {

		return toD;
	}

	public String getStatus() {

		return status.get();
	}

	public String getName() {
		return name.get();
	}

	public LocalDate getDateIssued() {
		return dateIssued;
	}

	public String getPhone() {
		return phone.get();
	}

	public String getDocNo() {
		return docNo.get();
	}

	public Long getBillId() {
		return billId.get();
	}

	public int getNights() {
		return nights.get();
	}

	public float getTotalCost() {
		return totalCost.get();
	}

}
