package com.jeet.service;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Time;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.jeet.api.Login;
import com.jeet.db.DAO;

/**
 * Servlet implementation class SecondServlet
 */
public class SecondServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SecondServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(final HttpServletRequest request,
			final HttpServletResponse response) throws ServletException, IOException {
		final String userName = request.getParameter("name");
		final String password = request.getParameter("pass");
		System.out.println("SecondServlet.doGet()" + userName + " pass:- "
				+ password);
		System.out.println("SecondServlet.doGet() th--------------"+Thread.currentThread().getName());
		try {
			Thread.currentThread().sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Thread t = new Thread(new Runnable() {
			
			public void run() {
				try {
					Login login = DAO.intsnace().getLoginDetail(userName);
					System.out.println(login.getUserName()+ " "+login.getPassword() +" already exist");
					getServletContext().setAttribute("msg", login.getUserName()+ " "+login.getPassword() +" already exist");
					String str = getServletConfig().getInitParameter("hello");
					System.out.println(str);
					handleSession(request);
					/*RequestDispatcher dis = getServletContext().getRequestDispatcher("/failure");
					dis.include(request, response);*/
					response.sendRedirect("failure");
				} catch (Exception e) {
					try {
						System.out
								.println("SecondServlet.doGet(...).new Runnable() {...}.run() 11111111"+request);
						request.getSession();
						System.out
								.println("SecondServlet.doGet(...).new Runnable() {...}.run() 22222222");
						//handleCookies(request, response);
						DAO.intsnace().updateLogin(new Login(userName, password));
						System.out
								.println("SecondServlet.doGet(...).new Runnable() {...}.run() 3333");
						getServletContext().setAttribute("msg", "Created a new user for "+userName);
						//generateResponse(response, "Sucess");
						//RequestDispatcher dis = getServletContext().getRequestDispatcher("/success");
						//dis.include(request, response);	
						Thread.currentThread().sleep(10000);
						System.out
								.println("SecondServlet.doGet(...).new Runnable() {...}.run()4444");
					} catch (Exception e1) {
					}
				}
			}
			}
		);
		t.start();
		try {
			t.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		generateResponse(response, "Sucess");
		System.out.println("SecondServlet.doGet()77777777777777777777777&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
	}
	
	private void generateResponse(HttpServletResponse response, String msg) throws IOException{
		PrintWriter out = response.getWriter();
		out.println("<html><body>");
		out.println("<h2>"+msg+"</h2>");
		out.println("</body></html>");
		out.flush();
	}

	private void handleSession(HttpServletRequest request) {
		HttpSession session = request.getSession(true);
		System.out.println("SecondServlet. getId() "+session.getId());
		System.out.println("SecondServlet getCreationTime "+ new Time(session.getCreationTime()));
		System.out.println("SecondServlet.doGet()"+ new Time(session.getLastAccessedTime()));
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}
	
	private void handleCookies(HttpServletRequest request,
			HttpServletResponse response){
		
		Cookie[] cookies = request.getCookies();
		if( cookies != null){
		System.out.println("SecondServlet.handleCookies()"+cookies.length);
		for(Cookie cookie : cookies){
			System.out.println("Cookie Name -> "+cookie.getName()+ " :: "+" Cookie Value -> "+cookie.getValue()+" Expires on :"+new Time(cookie.getMaxAge()) );
		}
		}else {
			System.out.println("SecondServlet.handleCookies() NULl");
		}
		
		String userName = request.getParameter("name");
		String password = request.getParameter("pass");
		
		Cookie ck = new Cookie(userName, password);
		System.out.println("max life of cookie is : 20 sec");
		ck.setMaxAge(2*10);
		response.addCookie(ck);
		
	}
	
	

}
