package net.ICFdemo.usermanagement.web;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.ICFdemo.usermanagement.dao.Userdao;
import net.ICFdemo.usermanagement.model.User;

/**
 * Servlet implementation class UserServlet
 */
@WebServlet("/")
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Userdao userdao;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public void init() {
    	this.userdao=new Userdao();
    }
    
    /**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doGet(request, response);
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String action= request.getServletPath();
		
		switch (action) {
		case "/new":
			showNewForm(request, response);
			break;
		case "/insert":
			try {
				insertUser(request, response);
			}
			catch(SQLException e){
				e.printStackTrace();
			}
			break;
		case "/delete":
			try {
			deleteUser(request, response);
			}
			catch(SQLException e) {
				e.printStackTrace();
			}
			break;
		case "/edit":
			try {
				showEditForm(request, response);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			break;
		case "/update":
			try {
				updateUser(request, response);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			break;
		default:
			try {
				listUser(request, response);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			break;
		}
	}
	private void showNewForm(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("adminlog.jsp");
		dispatcher.forward(request, response);
	}
	
	private void insertUser(HttpServletRequest request, HttpServletResponse response) 
			throws SQLException, IOException {
		String fname = request.getParameter("fname");
		String lname = request.getParameter("lname");
		String eid = request.getParameter("eid");
		String desig = request.getParameter("desig");
		String dob = request.getParameter("dob");
		String gender = request.getParameter("gender");
		String address = request.getParameter("address");
		User newUser = new User(fname,lname,eid,desig,dob,gender,address);
		userdao.insertUser(newUser);
		response.sendRedirect("list");
	}

	private void deleteUser(HttpServletRequest request, HttpServletResponse response) 
			throws SQLException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		userdao.deleteUser(id);
		response.sendRedirect("list");

	}
	
	private void showEditForm(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		User existingUser = userdao.selectUser(id);
		RequestDispatcher dispatcher = request.getRequestDispatcher("adminlog.jsp");
		request.setAttribute("user", existingUser);
		dispatcher.forward(request, response);

	}
	
	private void updateUser(HttpServletRequest request, HttpServletResponse response) 
			throws SQLException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		String fname = request.getParameter("fname");
		String lname = request.getParameter("lname");
		String eid = request.getParameter("eid");
		String desig = request.getParameter("desig");
		String dob = request.getParameter("dob");
		String gender = request.getParameter("gender");
		String address = request.getParameter("address");
		

		User user = new User(id,fname,lname,eid,desig,dob,gender,address);
		userdao.updateUser(user);
		response.sendRedirect("list");
	}
	
	private void listUser(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		List<User> listUser = userdao.selectAllUsers();
		request.setAttribute("listUser", listUser);
		RequestDispatcher dispatcher = request.getRequestDispatcher("user-list.jsp");
		dispatcher.forward(request, response);
	}

}
