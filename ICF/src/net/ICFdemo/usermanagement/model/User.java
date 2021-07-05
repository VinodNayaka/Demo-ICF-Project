package net.ICFdemo.usermanagement.model;

public class User {
	
	protected int id;
	protected String fname;
	protected String lname;
	protected String eid;
	protected String desig;
	protected String dob;
	protected String gender;
	protected String address;
	
	
	
	public User(String fname, String lname, String eid, String desig, String dob, String gender, String address) {
		super();
		this.fname = fname;
		this.lname = lname;
		this.eid = eid;
		this.desig = desig;
		this.dob = dob;
		this.gender = gender;
		this.address = address;
	}
	public User(int id, String fname, String lname, String eid, String desig, String dob, String gender,
			String address) {
		super();
		this.id = id;
		this.fname = fname;
		this.lname = lname;
		this.eid = eid;
		this.desig = desig;
		this.dob = dob;
		this.gender = gender;
		this.address = address;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFname() {
		return fname;
	}
	public void setFname(String fname) {
		this.fname = fname;
	}
	public String getLname() {
		return lname;
	}
	public void setLname(String lname) {
		this.lname = lname;
	}
	public String getEid() {
		return eid;
	}
	public void setEid(String eid) {
		this.eid = eid;
	}
	public String getDesig() {
		return desig;
	}
	public void setDesig(String desig) {
		this.desig = desig;
	}
	public String getDob() {
		return dob;
	}
	public void setDob(String dob) {
		this.dob = dob;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	

}
