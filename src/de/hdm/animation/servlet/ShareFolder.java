package de.hdm.animation.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import de.hdm.animation.ShareSpace;

/**
 * Servlet implementation class
 */
@WebServlet("/ShareFolder")
public class ShareFolder extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static ShareSpace shareSpace = null;
    
    public static ShareSpace shareSpace() {
        if (shareSpace == null) {
            shareSpace = new ShareSpace();
        }
        return shareSpace;
    }

    public void destroy() {
        if (shareSpace != null) {
            shareSpace.remove();
        }
        super.destroy();
    }
    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String dir = request.getParameter("directory");        
        if (dir ==null && request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if (cookie.getName().equals("directory")) {
                    dir = cookie.getValue();
                    break;
                }
            }
        }

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        String docType = "<!doctype html public \"-//w3c//dtd html 4.0 " + "transitional//en\">\n";
        out.println(docType);
        out.println("<html><body>");

        out.println("<form action=\"ShareFolder\" method=\"Post\">");
        out.println("Sharing folder: <input type=\"submit\" name=\"directory\" value=\"" + dir + "\">");
        out.println("</form>");
        

        out.println("</body>");
        out.println("</html>");
        
        shareSpace().setDirectory(dir);  
        shareSpace().toFront();

        out.close();    

    }
    
        
    

}