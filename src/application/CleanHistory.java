package application;

import java.time.LocalDate;

import javafx.beans.property.SimpleStringProperty;

public class CleanHistory {

	private SimpleStringProperty Name;
	private SimpleStringProperty Room;
	private LocalDate date;

	public CleanHistory(String Name, String Room, LocalDate date) {

		this.Name = new SimpleStringProperty(Name);
		this.Room = new SimpleStringProperty(Room);
		this.date = date;
	}

	public LocalDate getDate() {
		return date;
	}

	public String getName() {
		return Name.get();
	}

	public String getRoom() {
		return Room.get();
	}

}
