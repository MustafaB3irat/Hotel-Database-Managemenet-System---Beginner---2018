package application;

import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleLongProperty;

public class FinancialData {

	private SimpleLongProperty no;
	private SimpleFloatProperty amount;

	public FinancialData(Long no, float amount) {
		super();
		this.no = new SimpleLongProperty(no);
		this.amount = new SimpleFloatProperty(amount);
	}

	public Long getNo() {
		return no.get();
	}

	public float getAmount() {
		return amount.get();
	}

}
