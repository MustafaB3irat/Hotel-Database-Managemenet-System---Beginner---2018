package application;

import java.time.LocalDate;

import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

public class EmployeeRec {

	private SimpleStringProperty name, address, phone, log_inName, log_inPassword, email , job;
	private SimpleLongProperty id;
	private SimpleFloatProperty rating, salary;
	private LocalDate DOB, startDate, endDate;

	public EmployeeRec(String name, Long id, String email, String phone, float rating, String address, float salary,
			LocalDate startDate, LocalDate endDate , String job) {

		this.name = new SimpleStringProperty(name);
		this.id = new SimpleLongProperty(id);
		this.email = new SimpleStringProperty(email);
		this.phone = new SimpleStringProperty(phone);
		this.rating = new SimpleFloatProperty(rating);
		this.address = new SimpleStringProperty(address);
		this.salary = new SimpleFloatProperty(salary);
		this.startDate = startDate;
		this.endDate = endDate;
		this.job = new SimpleStringProperty(job);
	}

	public String getName() {
		return name.get();
	}
	
	public String getJob() {
		return job.get();
	}

	public String getAddress() {
		return address.get();
	}

	public String getPhone() {
		return phone.get();
	}

	public String getLog_inName() {
		return log_inName.get();
	}

	public String getLog_inPassword() {
		return log_inPassword.get();
	}

	public String getEmail() {
		return email.get();
	}

	public Long getId() {
		return id.get();
	}

	public float getRating() {
		return rating.get();
	}

	public float getSalary() {
		return salary.get();
	}

	public LocalDate getDOB() {
		return DOB;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

}
