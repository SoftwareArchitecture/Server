package ac.at.tuwien.swazam.controller;

import java.io.IOException;

import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AccountController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String forward="";
        String action = request.getParameter("action");
        if (action.equalsIgnoreCase("listAccounts")){
        	
        	// return list of accounts
        	request.setAttribute("accounts", "");
        }
        else if(action.equalsIgnoreCase("accountid")) {
        	forward = "/account.jsp";
        	int userId = Integer.parseInt(request.getParameter("accountId"));
            //Get accountid from dao
        	//Account acc = AccountDao.getAccountById();
        	request.setAttribute("accounts", "");
        }
        
        RequestDispatcher view = request.getRequestDispatcher(forward);
        view.forward(request, response);
	}
	
	
	        
}
