package net.ICFdemo.usermanagement.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import net.ICFdemo.usermanagement.model.User;

public class Userdao {
	private String dburl="jdbc:mysql://localhost:3306/demo";
	private String dbuname="root";
	private String dbpass="1234";
	
	private static final String INSERT = "INSERT INTO emp" + " (fname,lname,eid,desig,dob,gender,address) VALUES " + " (?,?,?,?,?,?,?);";

	private static final String SELECT_BY_ID = "select id,fname,lname,eid,desig,dob,gender,address from emp where id =?";
	private static final String SELECT = "select * from emp;";
	private static final String DELETE = "delete from emp where id = ?;";
	private static final String UPDATE = "update emp set fname = ?,lname= ?,eid = ?, desig= ?,dob= ?,gender= ?,address= ? where id = ?;";
	
	protected Connection getConnection() {
		Connection connection=null;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(dburl, dbuname, dbpass);
		}
		catch(SQLException e) {
			e.printStackTrace();
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return connection;
	}
	public void insertUser(User user) throws SQLException {
		System.out.println(INSERT);
		// try-with-resource statement will auto close the connection.
		try (Connection con = getConnection();
				PreparedStatement preparedStatement = con.prepareStatement(INSERT)) {
			preparedStatement.setString(1, user.getFname());
			preparedStatement.setString(2, user.getLname());
			preparedStatement.setString(3, user.getEid());
			preparedStatement.setString(4, user.getDesig());
			preparedStatement.setString(5, user.getDob());
			preparedStatement.setString(6, user.getGender());
			preparedStatement.setString(7, user.getAddress());
			System.out.println(preparedStatement);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			printSQLException(e);
		}
	}
	
	public User selectUser(int id) {
		User user = null;
		// Step 1: Establishing a Connection
		try (Connection connection = getConnection();
				// Step 2:Create a statement using connection object
				PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_ID);) {
			preparedStatement.setInt(1, id);
			System.out.println(preparedStatement);
			// Step 3: Execute the query or update query
			ResultSet rs = preparedStatement.executeQuery();

			// Step 4: Process the ResultSet object.
			while (rs.next()) {
				
				String fname = rs.getString("fname");
				String lname = rs.getString("lname");
				String eid = rs.getString("eid");
				String desig = rs.getString("desig");
				String dob = rs.getString("dob");
				String gender = rs.getString("gender");
				String address = rs.getString("address");
				user = new User(id,fname,lname,eid,desig,dob,gender,address);
			}
		} catch (SQLException e) {
			printSQLException(e);
		}
		return user;
	}
	
	public List<User> selectAllUsers() {

		// using try-with-resources to avoid closing resources (boiler plate code)
		List<User> users = new ArrayList<>();
		// Step 1: Establishing a Connection
		try (Connection connection = getConnection();

				// Step 2:Create a statement using connection object
			PreparedStatement preparedStatement = connection.prepareStatement(SELECT);) {
			System.out.println(preparedStatement);
			// Step 3: Execute the query or update query
			ResultSet rs = preparedStatement.executeQuery();

			// Step 4: Process the ResultSet object.
			while (rs.next()) {
				int id = rs.getInt("id");
				String fname = rs.getString("fname");
				String lname = rs.getString("lname");
				String eid = rs.getString("eid");
				String desig = rs.getString("desig");
				String dob = rs.getString("dob");
				String gender = rs.getString("gender");
				String address = rs.getString("address");
				users.add(new User(id, fname, lname, eid, desig, dob, gender, address));
			}
		} catch (SQLException e) {
			printSQLException(e);
		}
		return users;
	}
	
	public boolean deleteUser(int id) throws SQLException {
		boolean rowDeleted;
		try (Connection con = getConnection();
				PreparedStatement statement = con.prepareStatement(DELETE);) {
			statement.setInt(1, id);
			rowDeleted = statement.executeUpdate() > 0;
		}
		return rowDeleted;
	}
	
	public boolean updateUser(User user) throws SQLException {
		boolean rowUpdated;
		try (Connection connection = getConnection();
				PreparedStatement statement = connection.prepareStatement(UPDATE);) {
			statement.setString(1, user.getFname());
			statement.setString(2, user.getLname());
			statement.setString(3, user.getEid());
			statement.setString(4, user.getDesig());
			statement.setString(5, user.getDob());
			statement.setString(6, user.getGender());
			statement.setString(7, user.getAddress());
			statement.setInt(8, user.getId());

			rowUpdated = statement.executeUpdate() > 0;
		}
		return rowUpdated;
	}

	private void printSQLException(SQLException ex) {
		for (Throwable e : ex) {
			if (e instanceof SQLException) {
				e.printStackTrace(System.err);
				System.err.println("SQLState: " + ((SQLException) e).getSQLState());
				System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
				System.err.println("Message: " + e.getMessage());
				Throwable t = ex.getCause();
				while (t != null) {
					System.out.println("Cause: " + t);
					t = t.getCause();
				}
			}
		}
	}

}
