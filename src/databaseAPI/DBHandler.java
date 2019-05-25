package databaseAPI;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.media.AudioClip;
import javafx.stage.Modality;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.JRException;

public class DBHandler {

	private Connection connection;
	private static final String DBname = "hotel";
	private static final String user = "root";
	private static final String password = "mus%^&4545";
	private Statement statement;
	private Map<String, Object> map;

	public DBHandler() {
		InitializeDB();
	}

	public void printReport() throws JRException, FileNotFoundException {

		InputStream s = DBHandler.class.getResourceAsStream("/databaseAPI/bill.jasper");

		map = new HashMap<String, Object>();
		Report.creatReport(connection, map, s);
		Report.showReport();
	}

	private void InitializeDB() {

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + DBname
					+ "?useLegacyDatetimeCode=true&serverTimezone=UTC&useSSL=false", user, password);

			statement = connection.createStatement();

		} catch (SQLException | ClassNotFoundException e) {

			AudioClip Audio = new AudioClip("file:src/application/Error.mp3");
			Audio.play();

			Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText("Couldn't load Database!");
			alert.setTitle("Error!");
			alert.setHeaderText(null);
			alert.setResizable(false);

			alert.initModality(Modality.APPLICATION_MODAL);

			((Stage) alert.getDialogPane().getScene().getWindow()).getIcons().add(new Image("/application/Error.png"));
			alert.showAndWait();
		}
	}

	private void delete(String table, String attribute, String val) throws SQLException {
		if (table == null || attribute == null || val == null || table.isEmpty() || attribute.isEmpty()
				|| val.isEmpty())
			throw new IllegalArgumentException();

		statement.execute("delete from " + table + " where " + attribute + " = '" + val + "';");
	}

	public Boolean confirmLogin(String username, String password) throws SQLException {

		ResultSet s = statement.executeQuery("select login_type from employee where log_inName = '" + username
				+ "' and log_inPassword = '" + password + "'");

		if (s.next()) {
			if (s.getString(1).toLowerCase().equals("manager"))
				return true;
		}

		return false;

	}

	/****************************************************************************************************************/

	public void updateEmployee(String pid, String rating, String address, String base_salary, String log_inName,
			String log_inPassward, LocalDate startDate, LocalDate endDate) throws SQLException {

		int i = 0;
		String s = "UPDATE employee set ";

		if (rating != null && !rating.equals("")) {
			if (i != 0)
				s += ",";
			s = s + "rating = " + rating;
			i++;
		}

		if (address != null && !address.equals("")) {
			if (i != 0)
				s += ",";
			s = s + "address = '" + address + "'";
			i++;
		}

		if (base_salary != null && !base_salary.equals("")) {
			if (i != 0)
				s += ",";
			s = s + "base_salary = " + base_salary;
			i++;
		}

		if (log_inName != null && log_inName.length() > 0) {
			if (i != 0)
				s += ",";
			s = s + "log_inName = '" + log_inName + "'";
			i++;
		}

		if (log_inPassward != null && log_inPassward.length() > 0) {
			if (i != 0)
				s += ",";
			s = s + "log_inPassword = '" + log_inPassward + "'";
			i++;
		}

		if (startDate != null) {
			if (i != 0)
				s += ",";

			s = s + "startDate = '" + startDate.toString() + "'";
			i++;
		}

		if (endDate != null) {
			if (i != 0)
				s += ",";

			s = s + "endDate = '" + endDate.toString() + "'";
			i++;
		}

		// if (type != null) {
		// if (i != 0)
		// s += ",";
		// s = s + "login_type = '" + type + "'";
		// i++;
		// }

		if (i == 0)
			return;

		s += " where pid = " + pid;
		statement.execute(s);
	}

	public void insertEmployee(String pid, String rating, String address, String base_salary, String log_inName,
			String log_inPassword, LocalDate startDate, login_type type) throws SQLException {

		PreparedStatement insertStatement = connection.prepareStatement(
				"insert into hotel.employee (pid, rating, address, base_salary, log_inName, log_inPassword, startDate, login_type) value (?, ?, ?, ?, ?, ?, ?, ?);");

		insertStatement.setString(1, pid);
		insertStatement.setString(2, rating);
		insertStatement.setString(3, address);
		insertStatement.setString(4, base_salary);
		insertStatement.setString(5, log_inName);
		insertStatement.setString(6, log_inPassword);
		insertStatement.setString(7, startDate.toString());
		insertStatement.setString(8, type.toString());

		insertStatement.executeUpdate();
	}

	public ResultSet findEmployee(String eid, String pid) throws SQLException {
		if ((pid == null || pid.isEmpty()) && (eid == null || eid.isEmpty()))
			return null;
		else {
			if (eid == null || eid.isEmpty())
				return statement.executeQuery("select * from employee where pid = " + pid + ";");
			else if (pid == null || pid.isEmpty())
				return statement.executeQuery("select * from employee where eid = " + eid + ";");
			else
				return statement.executeQuery("select * from employee where eid = " + eid + "and pid = " + pid + ";");
		}
	}

	public ResultSet getEmployee(String pname) throws SQLException {

		return statement.executeQuery(
				"select * from person as p , employee as e where p.pid = e.pid and p.pname like '" + pname + "%';");
	}

	public ResultSet getEmpById(String id) throws SQLException {

		return statement
				.executeQuery("select * from person as p , employee as e where p.pid = e.pid and e.eid =" + id + ";");

	}

	/*************************************************************************************************/

	public void deletePerson(String pid) throws SQLException {

		statement.execute("delete from person where pid =" + pid + ";");
	}

	public void updatePerson(String email, String pphone, String username, String rating, String address,
			String base_salary, String log_inPassword, LocalDate startDate, LocalDate endDate, String Type,
			String newUsername) throws SQLException {

		int i = 0;
		String s = "update person as p , employee as e set ";

		if (email != null && !email.equals("")) {

			s = s + "p.email = '" + email + "'";
			i++;
		}

		if (newUsername != null && !newUsername.equals("")) {

			if (i != 0)
				s += ",";

			s = s + "e.log_inName = '" + newUsername + "'";
			i++;
		}

		if (pphone != null && !pphone.equals("")) {
			if (i != 0)
				s += ",";
			s = s + "p.pphone = '" + pphone + "'";
			i++;
		}

		if (rating != null && !rating.equals("")) {
			if (i != 0)
				s += ",";
			s = s + "e.rating = " + rating;
			i++;
		}

		if (address != null && !address.equals("")) {
			if (i != 0)
				s += ",";
			s = s + "e.address = '" + address + "'";
			i++;
		}

		if (base_salary != null && !base_salary.equals("")) {
			if (i != 0)
				s += ",";
			s = s + "e.base_salary = " + base_salary;
			i++;
		}

		if (log_inPassword != null && log_inPassword.length() > 0) {
			if (i != 0)
				s += ",";
			s = s + "e.log_inPassword = '" + log_inPassword + "'";
			i++;
		}

		if (startDate != null) {
			if (i != 0)
				s += ",";

			s = s + "e.startDate = '" + startDate.toString() + "'";
			i++;
		}

		if (endDate != null) {
			if (i != 0)
				s += ",";

			s = s + "e.endDate = '" + endDate.toString() + "'";
			i++;
		}

		if (Type != null) {
			if (i != 0)
				s += ",";

			s = s + "e.login_type = '" + Type + "'";
			i++;
		}

		if (i == 0)
			return;

		s += " where p.pid = e.pid and e.log_inName = '" + username + "';";
		statement.execute(s);
	}

	public void insertPerson(String email, String pname, LocalDate DOB, String pphone) throws SQLException {

		PreparedStatement insertStatement = connection
				.prepareStatement("insert into hotel.person (email, pname, DOB , pphone) value (?, ?, ? , ?);");

		insertStatement.setString(1, email);
		insertStatement.setString(2, pname);
		insertStatement.setString(3, DOB.toString());
		insertStatement.setString(4, pphone);
		insertStatement.executeUpdate();
	}

	public ResultSet findPerson(String email) throws SQLException {
		if (email == null || email.isEmpty())
			return null;
		else
			return statement.executeQuery("select * from person where email = '" + email + "';");
	}

	public ResultSet findIdPerson(String email) throws SQLException {

		return statement.executeQuery("select pid from person where email = '" + email + "' ;");
	}

	/*************************************************************************************************/

	public void deleteClient(String cid) throws SQLException {

		statement.execute("delete from client where cid = " + cid + ";");
	}

	public void updateClient(String cid, String pid, String doc_type, String doc_no) throws SQLException {

		int i = 0;
		String s = "UPDATE client set ";

		if (pid != null && !pid.equals("")) {
			if (i != 0)
				s += ",";
			s = s + "pid = " + pid;
			i++;
		}

		if (doc_type != null && !doc_type.equals("")) {
			if (i != 0)
				s += ",";
			s = s + "doc_type = '" + doc_type + "'";
			i++;
		}

		if (doc_no != null && !doc_no.equals("")) {
			if (i != 0)
				s += ",";
			s = s + "doc_no = '" + doc_no + "'";
			i++;
		}

		if (i == 0)
			return;

		s += " where cid = " + cid;
		statement.execute(s);
	}

	public void insertClient(String pid, String doc_type, String doc_no) throws SQLException {

		PreparedStatement insertStatement = connection
				.prepareStatement("insert into hotel.client (pid, doc_type, doc_no) value (?, ?, ?);");

		insertStatement.setString(1, pid);
		insertStatement.setString(2, doc_type);
		insertStatement.setString(3, doc_no);

		insertStatement.executeUpdate();
	}

	public ResultSet getReservationsByClients(String Name) throws SQLException {

		return statement.executeQuery(
				"select * from person as p , client as c , reservation as r where p.pid = c.pid and c.cid = r.cid and p.pname like '"
						+ Name + "%';");
	}

	public void updateReservation(LocalDate FromDate, LocalDate ToDate, String rNum, String bNum) throws SQLException {

		statement.execute("update reservation set fromDate = '" + FromDate.toString() + "' , toDate = '"
				+ ToDate.toString() + "' , rNum ='" + rNum + "' where bNum = '" + bNum + "';");
	}

	public ResultSet getPidEmail(String email) throws SQLException {
		return statement.executeQuery("select pid from person where email = '" + email + "';");
	}

	public ResultSet findClient(String cid) throws SQLException {
		return statement.executeQuery("select * from client where cid = " + cid + ";");
	}

	public ResultSet findClient_Pid(String pid) throws SQLException {
		return statement.executeQuery("select * from client where pid = " + pid + ";");
	}

	public ResultSet getClientId(String docNo) throws SQLException {
		return statement.executeQuery("select cid from client where doc_no = " + docNo + ";");
	}

	/*************************************************************************************************/

	public void deleteRoom(String rNum) throws SQLException {
		delete("room", "rNum", rNum);
	}

	public void updateRoom(String noOfBeds, String cost, String size, String floor, String _class, String status,
			String roomNum) throws SQLException {

		int i = 0;
		String s = "UPDATE room set ";

		if (noOfBeds != null && !noOfBeds.equals("")) {
			if (i != 0)
				s += ",";
			s = s + "noOfBeds = " + noOfBeds;
			i++;
		}

		if (noOfBeds != null && !noOfBeds.equals("")) {
			if (i != 0)
				s += ",";
			s = s + "rNum = '" + roomNum + "'";
			i++;
		}

		if (cost != null && !cost.equals("")) {
			if (i != 0)
				s += ",";
			s = s + "cost = " + cost;
			i++;
		}

		if (size != null && !size.equals("")) {
			if (i != 0)
				s += ",";
			s = s + "size = " + size;
			i++;
		}

		if (floor != null && !floor.equals("")) {
			if (i != 0)
				s += ",";
			s = s + "floor = " + floor;
			i++;
		}

		if (_class != null && !_class.equals("")) {
			if (i != 0)
				s += ",";
			s = s + "class = " + _class;
			i++;
		}

		if (status != null && !status.equals("")) {
			if (i != 0)
				s += ",";
			s = s + "status = '" + status + "'";
			i++;
		}

		if (i == 0)
			return;

		s += " where rNum = '" + roomNum + "';";
		statement.execute(s);
	}

	public ResultSet getRooms() throws SQLException {

		return statement.executeQuery("select rNum from room;");
	}

	public void chngeStateToReserved() throws SQLException {

		statement.execute("update room set status = 'Reserved' where rNum in (select rNum from reservation);");
	}

	public ResultSet getRoom() throws SQLException {

		return statement.executeQuery("select * from room;");
	}

	public void insertRoom(String noOfBeds, String cost, String size, String floor, String _class, String status,
			String rNum) throws SQLException {

		PreparedStatement insertStatement = connection.prepareStatement(
				"insert into hotel.room (noOfBeds, cost, size, floor, class, status , rNum) value ( ?, ?, ?, ?, ?, ? , ?);");

		insertStatement.setString(1, noOfBeds);
		insertStatement.setString(2, cost);
		insertStatement.setString(3, size);
		insertStatement.setString(4, floor);
		insertStatement.setString(5, _class);
		insertStatement.setString(6, status);
		insertStatement.setString(7, rNum);

		insertStatement.executeUpdate();
	}

	public ResultSet findRoom(String rNum) throws SQLException {
		if (rNum == null || rNum.isEmpty())
			return null;
		else
			return statement.executeQuery("select * from room where rNum = '" + rNum + "';");
	}

	/**
	 * @throws SQLException
	 ***********************************************************************************/

	public void insertCleanHistory(String eid, String rNum, LocalDate date) throws SQLException {

		String Date = date.toString();

		PreparedStatement insertStatement = connection
				.prepareStatement("insert into hotel.clean_history (eid, rNum, date) value (?, ?, ?);");

		insertStatement.setString(1, eid);
		insertStatement.setString(2, rNum);
		insertStatement.setString(3, Date);

		insertStatement.executeUpdate();

	}

	public void deleteCleanHistory(String eid, String rNum, LocalDate date) throws SQLException {

		String Date = date.toString();

		statement.execute("delete from clean_history  where eid =" + eid + " and rNum = '" + rNum + "' and date = '"
				+ Date + "';");

	}

	public ResultSet getCleanHistory(LocalDate date) throws SQLException {

		return statement.executeQuery("select * from clean_history where date = '" + date.toString() + "';");
	}

	public ResultSet getCleanByRoomNum(String rNum, LocalDate date) throws SQLException {

		return statement.executeQuery(
				"select * from clean_history where rNum = '" + rNum + "' and date = '" + date.toString() + "';");
	}

	public ResultSet getCleanByeid(String eid) throws SQLException {

		return statement.executeQuery("select * from clean_history where eid = " + eid + ";");
	}

	public ResultSet getEidOfName(String Name) throws SQLException {

		return statement.executeQuery(
				"select e.eid from person as p , employee as e where p.pid = e.pid and p.pname = '" + Name + "';");
	}

	public ResultSet getCleanHistoryByRoomNumber(String rNum) throws SQLException {

		return statement.executeQuery("select * from clean_history where rNum = '" + rNum + "';");
	}

	public ResultSet nameOfeid(String eid) throws SQLException {

		return statement.executeQuery("select p.pname from person as p , employee as e where p.pid = e.pid and e.eid = "
				+ eid + " and e.login_type = 'cleaner' ;");
	}

	public ResultSet getCleanHistoryFromName(String Name) throws SQLException {

		return statement.executeQuery(
				"select p.pname , c.rNum , c.date from person as p , employee as e , clean_history as c where p.pid = e.pid and e.eid = c.eid  and p.pname = '"
						+ Name + "';");
	}

	public ResultSet getCleanHistoryFromRoom(String rNum) throws SQLException {

		return statement.executeQuery(
				"select p.pname , c.rNum , c.date from person as p , employee as e , clean_history as c where p.pid = e.pid and e.eid = c.eid  and c.rNum = '"
						+ rNum + "';");
	}

	public ResultSet getCleanerNames() throws SQLException {

		return statement.executeQuery(
				"select p.pname from person as p , employee as e where p.pid = e.pid and e.login_type = 'cleaner';");
	}

	/***********************************************************************************************/

	public void deleteReservation(String rNum, LocalDate fromD, LocalDate toD) throws SQLException {

		statement.execute("delete from reservation where rNum = '" + rNum + "' and fromDate = '" + fromD.toString()
				+ "' and toDate = '" + toD.toString() + "';");
	}

	public void insertReservation(String cid, String eid, String rNum, LocalDate fromD, LocalDate toD)
			throws SQLException {

		PreparedStatement insertStatement = connection.prepareStatement(
				"insert into hotel.reservation (cid, eid, rNum, fromDate, toDate) value (?, ?, ?, ?, ?);");

		insertStatement.setString(1, cid);
		insertStatement.setString(2, eid);
		insertStatement.setString(3, rNum);
		insertStatement.setString(4, fromD.toString());
		insertStatement.setString(5, toD.toString());

		insertStatement.executeUpdate();
	}

	public void updateClientPerson(String CName, String CEmail, String CPhone, String newEmail) throws SQLException {

		statement.execute("update person set pname = '" + CName + "' , email = '" + newEmail + "' , pphone = '" + CPhone
				+ "' where email = '" + CEmail + "';");
	}

	public void updateClient(String DocType, String DocNo, String newDocNo) throws SQLException {

		statement.execute("update client set doc_type = '" + DocType + "' , doc_no = '" + newDocNo
				+ "' where doc_no = '" + DocNo + "';");
	}

	public void UpdateRoomStatus(String rNum) throws SQLException {

		statement.execute("update room set status ='Reserved' where rNum = '" + rNum + "';");
	}

	/**
	 * @throws SQLException
	 **********************************************************************************************************/

	public void insertCleanerEmployee(String eid, String pid, String job_type) throws SQLException {
		statement.execute("insert into other_employees value(" + eid + ", " + pid + ", '" + job_type + "');");
	}

	public void updateCleanerEmployee(String eid, String pid, String job_type) throws SQLException {
		int i = 0;
		String s = "UPDATE other_employee set ";

		if (job_type != null && !job_type.equals("")) {
			if (i != 0)
				s += ",";
			s = s + "jobType = '" + job_type + "'";
			i++;
		}

		if (i == 0)
			return;

		s += " where eid = " + eid + " and pid = " + pid + ";";
		statement.execute(s);
	}

	/************************************************************************************************************/

	public ResultSet searchClient(String cid) throws SQLException {
		return statement.executeQuery("select pid from client where client.cid=" + cid);
	}

	public ResultSet findAvailableRooms(LocalDate fromD, LocalDate toD) throws SQLException {

		return statement.executeQuery(
				"select * from room where rNum not in (select rNum from reservation where fromDate between '" + fromD
						+ "' and '" + toD + "' or toDate between '" + fromD + "' and '" + toD + "');");

	}

	public ResultSet findTotalIncome(LocalDate from, LocalDate to) throws SQLException {
		String t = to.toString();
		String f = from.toString();

		return statement.executeQuery("select * from bill where (date_issued is not null) and (date_issued between '"
				+ f + "' and '" + t + "');");
	}

	public void ChangeRoomStatus(LocalDate fromD, LocalDate toD) throws SQLException {

		statement.execute("update room set status = 'Available' where rNum not in (select rNum from reservation where '"
				+ fromD.toString() + "' between fromDate and toDate or '" + toD.toString()
				+ "' between fromDate and toDate) and status != 'Under Maintenance';");
	}

	/**
	 * @throws SQLException
	 **********************************************************************************************************************/

	public void IssueBill(String amount, String cid, String bNum) throws SQLException {
		LocalDate d = new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		statement.execute("insert into bill (date_issued , cid , amount , bNum) values ( '" + d.toString() + "' , "
				+ cid + " , " + amount + " , " + bNum + ");");
	}

	public ResultSet getpaymentByName(String name) throws SQLException {

		return statement.executeQuery(
				"select p.pname , c.doc_no , p.pphone , r.fromDate , r.toDate , ro.cost , ro.rNum from person as p , client as c , reservation as r , room as ro where p.pid = c.pid and c.cid = r.cid and r.rNum = ro.rNum and p.pname like '"
						+ name + "%' and r.bNum not in (select bNum from bill);");
	}

	public ResultSet getpaidbills(String name) throws SQLException {

		return statement.executeQuery(
				"select p.pname , c.doc_no , p.pphone , r.fromDate , r.toDate , ro.cost , ro.rNum from person as p , client as c , reservation as r , room as ro where p.pid = c.pid and c.cid = r.cid and r.rNum = ro.rNum and p.pname like '"
						+ name + "%' and r.bNum in (select bNum from bill);");
	}

	public ResultSet getpaidbills1(String name) throws SQLException {

		return statement.executeQuery(
				"select b.bid , b.date_issued , b.amount from person as p , client as c , reservation as r , bill as b where p.pid = c.pid and c.cid = r.cid and r.bNum = b.bNum and p.pname like '"
						+ name + "%'");
	}

	public ResultSet getBillsByClientName(String name) throws SQLException {

		return statement.executeQuery(
				"select * from person as p , client as c , reservation as r , room as ro where p.pid = c.pid and c.cid = r.cid and r.rNum = ro.rNum and p.pname like '"
						+ name + "%' and r.bNum not in (select bNum from bill);");
	}

	public ResultSet getbNumByDate(LocalDate date) throws SQLException {

		return statement.executeQuery("select bNum from bill where date_issued = '" + date.toString() + "';");
	}

	public ResultSet getBillsByDate(String bNum) throws SQLException {

		return statement.executeQuery(
				"select p.pname , c.doc_no , p.pphone , r.fromDate , r.toDate , ro.cost , ro.rNum from person as p , client as c , reservation as r , room as ro where p.pid = c.pid and c.cid = r.cid and r.rNum = ro.rNum and r.bNum = "
						+ bNum + " and r.bNum in (select bNum from bill);");

	}

	public ResultSet getBillsByDate1(LocalDate date) throws SQLException {

		return statement.executeQuery(
				"select b.bid , b.date_issued , b.amount from person as p , client as c , reservation as r , bill as b where p.pid = c.pid and c.cid = r.cid and r.bNum = b.bNum and b.date_issued = '"
						+ date.toString() + "';");

	}

	public void deleteBill(String bid) throws SQLException {

		statement.execute("delete from bill where bid = " + bid + ";");
	}

	// --------------------------------------------

	public ResultSet getCid(String doc_no) throws SQLException {
		return statement.executeQuery("select cid from client where doc_no = " + doc_no + ";");
	}

	public ResultSet getbNum(String rNum, LocalDate from, LocalDate to, String cid) throws SQLException {

		return statement.executeQuery("select bNum from reservation where rNum ='" + rNum + "' and fromDate = '" + from
				+ "' and toDate  ='" + to + "' and cid =" + cid + ";");
	}

	public void incrementClients(String eid) throws SQLException {
		statement.execute("update receptionist set noOfClients = noOfClients + 1 where eid = " + eid + ";");
	}

	public ResultSet findSalaries() throws SQLException {
		return statement.executeQuery(
				"select pname, base_salary , login_type from person, employee where employee.pid=person.pid");
	}

	public ResultSet getReservations() throws SQLException {
		return statement.executeQuery("select * from reservation;");
	}

	public ResultSet getReservationsByDocNo(String DocNo) throws SQLException {

		return statement.executeQuery(
				"select * from person as p , client as c , reservation as r where p.pid = c.pid and c.cid = r.cid and c.doc_no = '"
						+ DocNo + "';");

	}

	public boolean isReserved(String rid, LocalDate fromD, LocalDate toD) throws SQLException {

		ResultSet r = findAvailableRooms(fromD, toD);
		while (r.next())
			if (r.getString(1).equals(rid))
				return false;

		return true;
	}

	public void payBill(String bid, LocalDate date_issued, String amount) throws SQLException {
		String payDate = new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().toString();
		statement.execute("update bill set date_paid = '" + payDate + "' where bid = " + bid);
	}

	public ResultSet findBill(String bid) throws SQLException {
		return statement.executeQuery("select * from bill where bid = " + bid);
	}

	public void updateBill(String bid, LocalDate date_issued, String amount) throws SQLException {

		statement.execute("update bill set date_issued = '" + date_issued + "' , amount = " + amount + " where bid = "
				+ bid + ";");
	}

	public ResultSet getServices() throws SQLException {
		return statement.executeQuery("select * from services;");
	}

	public void insertReceptionist(String eid, String pid, String clients) throws SQLException {
		statement.execute("insert into receptionist value (" + eid + ", " + pid + ", " + clients + ");");
	}

	public void updateReceptionist(String eid, String pid, String clients) throws SQLException {
		statement.execute(
				"update receptionist set noOfClients = " + clients + " where eid = " + eid + " and pid = " + pid + ";");
	}

	public login_type getLoginType(String username, String password) throws SQLException {

		ResultSet s = statement.executeQuery("select login_type from employee where log_inName = '" + username
				+ "' and log_inPassword = '" + password + "'");

		s.first();
		String ss = s.getString(1);

		switch (ss.toLowerCase()) {
		case "receptionist":
			return login_type.RECEPTIONIST;
		case "manager":
			return login_type.MANAGER;
		case "cleaner":
			return login_type.Cleaner;
		default:
			throw new SQLException();
		}
	}

	public ResultSet getEidFromUserName(String UserName) throws SQLException {

		return statement
				.executeQuery("select e.eid from person as p , employee as e where p.pid = e.pid and e.log_inName = '"
						+ UserName + "';");
	}

	public enum login_type {
		MANAGER, RECEPTIONIST, CLIENT, Cleaner
	}
}
