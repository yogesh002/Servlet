package com.parishram.login;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.parishram.common.AvoidBrowserCache;
import com.parishram.common.Constants;
import com.parishram.common.InvalidInputException;

/**
 * This class retrieves the input parameters - username and password - from the
 * user. It calls {@link LoginAppDelegate#handleInput(HttpServletRequest)} to
 * validate the input parameters.<br>
 * Once the username and password is validated, they are stored in a session
 * object {@code model}. <br>
 * Success page is rendered if the login attempt is successful. Otherwise, the
 * error message is displayed along with the login page.
 * 
 * @author Yogesh
 *
 */
public class LoginAppController extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * In case the user selected the link or when form submit includes
	 * {@code get} method
	 * 
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		if (req.getSession(false) != null) {
			req.getSession(false).invalidate();
		}
		resp.sendRedirect("HTML/signup.html");
	}

	/**
	 * In the form, {@code doPost} method is mentioned. Therefore, we override
	 * this method.
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		RequestDispatcher rd = null;
		PrintWriter out = resp.getWriter();

		try {
			Exception signUpException = (Exception) req.getAttribute("exceptions");
			if (signUpException instanceof Exception) {
				log(signUpException.getMessage());
				throw new InvalidInputException(signUpException.getMessage());
			} else {
				LoginAppDelegate loginDelegate = new LoginAppDelegate();
				loginDelegate.handleInput(req);
				rd = req.getRequestDispatcher("/signup");
				rd.include(req, resp);
				AvoidBrowserCache browserCache = new AvoidBrowserCache();
				browserCache.avoidCacheFromBrowser(resp);

			}
		} catch (InvalidInputException e) {
			if (isSignUpException(req)) {
				rd = req.getRequestDispatcher("/HTML/signup.html");
				rd.include(req, resp);
				out.write(
						"<p style='color:black; font-weight: bold; text-align:center; padding: 10px; background-color: pink; width: 500px; margin: auto;'>"
								+ e.getMessage() + "</p>");
			} else {
				rd = req.getRequestDispatcher(Constants.LOGIN);
				rd.include(req, resp);
				out.write(
						"<p style='color:black; font-weight: bold; text-align:center; padding: 10px; background-color: pink; width: 500px; margin: auto;'>"
								+ e.getMessage() + "</p>");
			}
		}
	}

	private boolean isSignUpException(HttpServletRequest req) {
		Exception signUpException = (Exception) req.getAttribute("exceptions");
		return signUpException != null ? true : false;

	}
}
