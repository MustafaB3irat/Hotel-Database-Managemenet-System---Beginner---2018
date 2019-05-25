package application;

import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class AvailableRooms {

	private SimpleIntegerProperty numOfBeds;
	private SimpleStringProperty roomNum;
	private SimpleFloatProperty cost;
	private SimpleIntegerProperty floor;
	private SimpleStringProperty status;
	private SimpleFloatProperty size;
	private SimpleFloatProperty Cl;

	public AvailableRooms(String roomNum, int numOfBeds, float cost, float size, int floor, float Cl,
			String status) {
		super();
		this.roomNum = new SimpleStringProperty(roomNum);
		this.numOfBeds = new SimpleIntegerProperty(numOfBeds);
		this.cost = new SimpleFloatProperty(cost);
		this.size = new SimpleFloatProperty(size);
		this.floor = new SimpleIntegerProperty(floor);
		this.Cl = new SimpleFloatProperty(Cl);
		this.status = new SimpleStringProperty(status);
	}

	public int getNumOfBeds() {
		return numOfBeds.get();
	}

	public String getRoomNum() {
		return roomNum.get();
	}

	public float getCost() {
		return cost.get();
	}

	public int getFloor() {
		return floor.get();
	}

	public String getStatus() {
		return status.get();
	}

	public float getSize() {
		return size.get();
	}

	public float getCl() {
		return Cl.get();
	}

}