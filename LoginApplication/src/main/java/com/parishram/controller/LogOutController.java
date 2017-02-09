package com.parishram.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.parishram.common.AvoidBrowserCache;

public class LogOutController extends HttpServlet {

	/**
	 * The user logs out. The {@code session} is invalidated and cache browser
	 * data is removed. This is done so that when the user submits the form, and
	 * clicks back button, the sensitive information on page is not displayed.
	 * Then user is redirected back to login screen
	 */
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(LogOutController.class);

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession(false);
		AvoidBrowserCache browserCache = null;
		if (session != null) {
			browserCache = new AvoidBrowserCache();
			browserCache.avoidCacheFromBrowser(resp);
			resp.sendRedirect("/-LoginApplication");
			session.invalidate();
			logger.debug("Session invalidated");
		}
	}

}
