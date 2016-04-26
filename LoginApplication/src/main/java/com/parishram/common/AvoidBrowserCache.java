package com.parishram.common;

import javax.servlet.http.HttpServletResponse;

/**
 * Removes the browser cache.
 * 
 * @author Yogesh
 *
 */
public class AvoidBrowserCache {

	public void avoidCacheFromBrowser(HttpServletResponse response) {
		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP
																					// 1.1.
		response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
		response.setHeader("Expires", "0"); // Proxies.

	}

}
