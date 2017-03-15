package ccproj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//import org.apache.tomcat.util.http.fileupload.RequestContext;

import com.sun.net.httpserver.HttpExchange;

import sun.net.www.URLConnection;

/**
 * Servlet implementation class CloudReqResp
 */
// @WebServlet("/CloudReqResp")
public class CloudReqResp extends HttpServlet implements Servlet {
	private static final long serialVersionUID = 1L;
    
    /**
     * @see HttpServlet#HttpServlet()
     */
	
    public CloudReqResp() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */

	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
    	System.out.println("Inside Init");
    	
	}

	/**
	 * @see Servlet#destroy()
	 */
  
	public void destroy() {
		// TODO Auto-generated method stub
    	System.out.println("Inside Destroy");
	}

	/**
	 * @see Servlet#getServletConfig()
	 */

	public ServletConfig getServletConfig() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @see Servlet#getServletInfo()
	 */

	public String getServletInfo() {
		// TODO Auto-generated method stub
		return null; 
	}

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
    //	System.out.println("Inside Service");
    	//System.out.println(request.getMethod());
//    	StringBuffer requestURL = request.getRequestURL();
//    	if (request.getQueryString() != null) {
//    	    requestURL.append("?").append(request.getQueryString());
//    	}
//    	String completeURL = requestURL.toString();
    	    	
//    	System.out.println(request.getContextPath());
//       	System.out.println(request.getParameterNames());
//       	System.out.println(request.getParameter("inputdata"));
    	if(request.getMethod().equals("GET")){
    		doGet(request, response);
    	}
    	//
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
    	//System.out.println(request.getInputStream().toString() + " Holla!!");
       //	System.out.println(request.getParameterMap());
		InputStream requestBodyInput = request.getInputStream();
		String inputdata = request.getParameter("inputdata");
		System.out.println(inputdata);
		//response.getWriter().append(" \n Served at: ").append(request.getContextPath());
		response.setContentType("text/html");
	     PrintWriter out = response.getWriter();
	     
	     // Calling the Java Function for Processing --> SQS code goes here
//	     TestWebapp ccproj_obj = new TestWebapp(inputdata);
//	     String output = ccproj_obj.TestWebapp_m(inputdata);
	     RequestPiValue rpv = new RequestPiValue(inputdata);
	     String output = rpv.retPiVal();
	    
	     // For output to screen
	     // out.println("\n" + output);
	     //out.println("Hello World!");
	     out.println(inputdata + " gives an output :  ");
	     
	     out.println(output + " is returned from the Server ");
	    // System.out.println(request.getQueryString());
/*	     if (request.getParameter("inputdata") == null) {
	         out.println("Please enter a number");
	     } else {
	         out.println("Hello <b>"+request. getParameter("inputdata")+"</b>!");
	     }
*/    
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
    	System.out.println("dgdgd" + request.getParameterMap().size());
		//doGet(request, response);
	}

	/**
	 * @see HttpServlet#doPut(HttpServletRequest, HttpServletResponse)
	 */

	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doDelete(HttpServletRequest, HttpServletResponse)
	 */

	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doHead(HttpServletRequest, HttpServletResponse)
	 */

	protected void doHead(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doOptions(HttpServletRequest, HttpServletResponse)
	 */

	protected void doOptions(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doTrace(HttpServletRequest, HttpServletResponse)
	 */
    
	protected void doTrace(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
