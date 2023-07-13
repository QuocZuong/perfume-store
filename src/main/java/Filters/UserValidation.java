package Filters;

import DAOs.UserDAO;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class UserValidation implements Filter {

	// The filter configuration object we are associated with. If
	// this value is null, this filter instance is not currently
	// configured.
	private FilterConfig filterConfig = null;
	private boolean debug = true;
	private final UserDAO userDAO = new UserDAO();
	private final String[] FOLDER_URL_LIST = { "/ADMIN_PAGE", "/CLIENT_PAGE", "/LOGIN_PAGE", "/PRODUCT_PAGE",
			"/USER_PAGE" };

	public UserValidation() {
	}

	/**
	 * Validate cookies and redirect user to login page if not valid.
	 *
	 * @param request  The servlet request we are processing
	 * @param response The servlet response we are creating
	 * @param chain    The filter chain we are processing
	 *
	 * @exception IOException      if an input/output error occurs
	 * @exception ServletException if a servlet error occurs
	 */
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;

		boolean isAdmin = isAdmin(req, res);
		boolean isClient = isClient(req, res);

		final String URI = req.getRequestURI();
		final String URL = req.getRequestURL().toString();

		// --------------------------PREVENT FOLDER LINKS----------------------
		for (String folder : FOLDER_URL_LIST) {
			if (URI.startsWith(folder)) {
				System.out.println("User trying to go to folder links, so redirect to home page");
				// DO NOT MODIFIED
				res.sendError(403);
				return;
			}
		}

		// --------------------------SKIP LOGIN IF IS USER----------------------
		// If in Login page and is an admin or client, go to product list.
		if (URI.startsWith("/Log/Login")) {
			if (isClient) {
				res.sendRedirect("/Client/User");
				return;
			}
			if (isAdmin) {
				res.sendRedirect("/Admin");
				return;
			}
		}

		// --------------------------PREVENT UNAUTHORISED USER----------------------
		if (URI.startsWith("/Client")) {
            if (isAdmin) {
                System.out.println("Going admin");
                res.sendRedirect("/Admin");
				return;
            }

			if (!isClient) {
				System.out.println("Not Client, so redirect to /Log/Login");
				res.sendRedirect("/Log/Login");
				return;
			}
		}

		// --------------------------PREVENT UNAUTHORISED ADMIN----------------------
		if (URI.startsWith("/Admin")) {
			if (!isAdmin) {
				System.out.println("Not Admin, so redirect to home page");
				res.sendError(403);
				return;
			}
		}

		Throwable problem = null;

		try {
			chain.doFilter(request, response);
		} catch (Throwable t) {
			// If an exception is thrown somewhere down the filter chain,
			// we still want to execute our after processing, and then
			// rethrow the problem after that.
			problem = t;
			t.printStackTrace();
		}

		// If there was a problem, we want to rethrow it if it is
		// a known type, otherwise log it.
		if (problem != null) {
			if (problem instanceof ServletException) {
				throw (ServletException) problem;
			}
			if (problem instanceof IOException) {
				throw (IOException) problem;
			}
			sendProcessingError(problem, response);
		}
	}

	public boolean isAdmin(HttpServletRequest request, HttpServletResponse response) {
		Cookie[] cookies = request.getCookies();

		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				if (cookies[i].getName().equals("Admin")) {

					if (!userDAO.isExistUsername(cookies[i].getValue()) && userDAO.isAdmin(cookies[i].getValue())) {
						cookies[i].setMaxAge(0);
						cookies[i].setPath("/");
						response.addCookie(cookies[i]);
						return false;
					}

					request.getSession().setAttribute("userCookie", cookies[i]);
					return true;
				}
			}
		}

		return false;
	}

	public boolean isClient(HttpServletRequest request, HttpServletResponse response) {
		Cookie[] cookies = request.getCookies();

		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				if (cookies[i].getName().equals("Client")) {

					if (!userDAO.isExistUsername(cookies[i].getValue()) && userDAO.isClient(cookies[i].getValue())) {
						cookies[i].setMaxAge(0);
						cookies[i].setPath("/");
						response.addCookie(cookies[i]);
						return false;
					}

					cookies[i].setPath("/");
					request.getSession().setAttribute("userCookie", cookies[i]);
					return true;
				}
			}
		}

		return false;
	}

	/**
	 * Return the filter configuration object for this filter.
	 */
	public FilterConfig getFilterConfig() {
		return (this.filterConfig);
	}

	/**
	 * Set the filter configuration object for this filter.
	 *
	 * @param filterConfig The filter configuration object
	 */
	public void setFilterConfig(FilterConfig filterConfig) {
		this.filterConfig = filterConfig;
	}

	/**
	 * Destroy method for this filter
	 */
	public void destroy() {
	}

	/**
	 * Init method for this filter
	 */
	public void init(FilterConfig filterConfig) {
		this.filterConfig = filterConfig;
		if (filterConfig != null) {
			if (debug) {
				log("UserValidation:Initializing filter");
			}
		}
	}

	/**
	 * Return a String representation of this object.
	 */
	@Override
	public String toString() {
		if (filterConfig == null) {
			return ("UserValidation()");
		}
		StringBuffer sb = new StringBuffer("UserValidation(");
		sb.append(filterConfig);
		sb.append(")");
		return (sb.toString());
	}

	private void sendProcessingError(Throwable t, ServletResponse response) {
		String stackTrace = getStackTrace(t);

		if (stackTrace != null && !stackTrace.equals("")) {
			try {
				response.setContentType("text/html");
				PrintStream ps = new PrintStream(response.getOutputStream());
				PrintWriter pw = new PrintWriter(ps);
				pw.print("<html>\n<head>\n<title>Error</title>\n</head>\n<body>\n"); // NOI18N

				// PENDING! Localize this for next official release
				pw.print("<h1>The resource did not process correctly</h1>\n<pre>\n");
				pw.print(stackTrace);
				pw.print("</pre></body>\n</html>"); // NOI18N
				pw.close();
				ps.close();
				response.getOutputStream().close();
			} catch (Exception ex) {
			}
		} else {
			try {
				PrintStream ps = new PrintStream(response.getOutputStream());
				t.printStackTrace(ps);
				ps.close();
				response.getOutputStream().close();
			} catch (Exception ex) {
			}
		}
	}

	public static String getStackTrace(Throwable t) {
		String stackTrace = null;
		try {
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			t.printStackTrace(pw);
			pw.close();
			sw.close();
			stackTrace = sw.getBuffer().toString();
		} catch (Exception ex) {
		}
		return stackTrace;
	}

	public void log(String msg) {
		filterConfig.getServletContext().log(msg);
	}

}
