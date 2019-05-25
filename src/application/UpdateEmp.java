package application;

import java.time.LocalDate;

import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

public class UpdateEmp {

	private SimpleStringProperty Name, Email, Phone, Address;
	private SimpleFloatProperty Salary, Rating;
	private SimpleLongProperty Id;
	private LocalDate startDate, endDate;
	private String Password, JobField, Username;

	public UpdateEmp(String Name, Long Id, String Email, String Phone, Float Rating, String Address, Float Salary,
			LocalDate startDate, LocalDate endDate) {

		this.Name = new SimpleStringProperty(Name);
		this.Id = new SimpleLongProperty(Id);
		this.Email = new SimpleStringProperty(Email);
		this.Phone = new SimpleStringProperty(Phone);
		this.Rating = new SimpleFloatProperty(Rating);
		this.Address = new SimpleStringProperty(Address);
		this.Salary = new SimpleFloatProperty(Salary);
		this.startDate = startDate;
		this.endDate = endDate;

	}

	public UpdateEmp(String Username, String Password, String Email, String Phone, Float Rating, String Address,
			Float Salary, LocalDate startDate, LocalDate endDate, String JobField) {

		this.Username = Username;
		this.Password = Password;
		this.Email = new SimpleStringProperty(Email);
		this.Phone = new SimpleStringProperty(Phone);
		this.Rating = new SimpleFloatProperty(Rating);
		this.Address = new SimpleStringProperty(Address);
		this.Salary = new SimpleFloatProperty(Salary);
		this.startDate = startDate;
		this.endDate = endDate;
		this.JobField = JobField;

	}

	public UpdateEmp(String Username, String Password, String Email, String Phone, Float Rating, String Address,
			Float Salary, LocalDate startDate, String JobField) {

		this.Username = Username;
		this.Password = Password;
		this.Email = new SimpleStringProperty(Email);
		this.Phone = new SimpleStringProperty(Phone);
		this.Rating = new SimpleFloatProperty(Rating);
		this.Address = new SimpleStringProperty(Address);
		this.Salary = new SimpleFloatProperty(Salary);
		this.startDate = startDate;
		this.JobField = JobField;

	}

	public UpdateEmp(String Name, Long Id, String Email, String Phone, Float Rating, String Address, Float Salary,
			LocalDate startDate) {

		this.Name = new SimpleStringProperty(Name);
		this.Id = new SimpleLongProperty(Id);
		this.Email = new SimpleStringProperty(Email);
		this.Phone = new SimpleStringProperty(Phone);
		this.Rating = new SimpleFloatProperty(Rating);
		this.Address = new SimpleStringProperty(Address);
		this.Salary = new SimpleFloatProperty(Salary);
		this.startDate = startDate;

	}

	public String getPassword() {
		return Password;
	}

	public String getJobField() {
		return JobField;
	}

	public String getUsername() {
		return Username;
	}

	public String getName() {
		return Name.get();
	}

	public String getEmail() {
		return Email.get();
	}

	public String getPhone() {
		return Phone.get();
	}

	public String getAddress() {
		return Address.get();
	}

	public float getSalary() {
		return Salary.get();
	}

	public Float getRating() {
		return Rating.get();
	}

	public long getId() {
		return Id.get();
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

}
