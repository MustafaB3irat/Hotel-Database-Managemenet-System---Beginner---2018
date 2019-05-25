package application;

import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleStringProperty;

public class Salary {

	private SimpleStringProperty Ename;

	private SimpleFloatProperty salary;

	private SimpleStringProperty job;

	public Salary(String ename, float salary, String job) {
		super();
		Ename = new SimpleStringProperty(ename);
		this.salary = new SimpleFloatProperty(salary);
		this.job = new SimpleStringProperty(job);
	}

	public String getEname() {
		return Ename.get();
	}

	public float getSalary() {
		return salary.get();
	}

	public String getJob() {

		return job.get();
	}

}
