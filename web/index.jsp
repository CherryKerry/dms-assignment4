<%-- 
    Document   : index
    Created on : Jun 10, 2014, 9:51:13 PM
    Author     : Joel Compton
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Switch2wellWEB</title>
    </head>
    <body>
        <h1>Switch 2 well WEB</h1>
        
        <%
            String temp = request.getParameter("status");
            int status = 0;
            if (temp != null)
                status = Integer.parseInt(temp);
            String url = "/Switch2wellWEB/webresources/users";
            //out.print("<p>Output is ");
            switch(status) {
                case 1 :    //out.print("1");
                            System.err.println("INFO: getting users form REST");
                            RequestDispatcher dispatcher1 = getServletContext().getRequestDispatcher(url);
                            dispatcher1.forward(request, response);
                    break;
                case 2 :    String fn = request.getParameter("firstname");
                            String ln = request.getParameter("lastname");
                            //out.print("2 " + fn + " " + ln);
                            System.err.println("INFO: adding user in REST");
                            String ur2 = url + "/" + fn + "/" + ln;
                            RequestDispatcher dispatcher2 = getServletContext().getRequestDispatcher(ur2);
                            dispatcher2.forward(request, response);
                    break;
                case 3 :    String fm = request.getParameter("firstname");
                            String lm = request.getParameter("lastname");
                            //out.print("3 " + fm + " " + lm);
                            System.err.println("INFO: getting users points in REST");
                            String ur3 = url + "/" + fm + "/" + lm + "/points";
                            RequestDispatcher dispatcher3 = getServletContext().getRequestDispatcher(ur3);
                            dispatcher3.forward(request, response);
                    break;
                case 4 :    String fo = request.getParameter("firstname");
                            String lo = request.getParameter("lastname");
                            String po = request.getParameter("points");
                            //out.print("4 " + fo + " " + lo + " " + po);
                            System.err.println("INFO: adding users points in REST");
                            String ur4 = url + "/" + fo + "/" + lo + "/" + po;
                            RequestDispatcher dispatcher4 = getServletContext().getRequestDispatcher(ur4);
                            dispatcher4.forward(request, response);
                    break;
                default :   out.print("none");
                    break;
            }
            out.print("</p>");
        %>
        <form action="/Switch2wellWEB/index.jsp">
            <h3>View All Users</h3>
            <input type="hidden" name="status" value="1">
            <input type="submit" value="View Users">
        </form>
        <form action="/Switch2wellWEB/index.jsp">
            <h3>Add Users</h3>
            First Name: <input type="text" name="firstname"><br />
            Last Name: <input type="text" name="lastname"><br />
            <input type="hidden" name="status" value="2">
            <input type="submit" value="Add User">
        </form>
        <form action="/Switch2wellWEB/index.jsp">
            <h3>Get Users Points</h3>
            First Name: <input type="text" name="firstname"><br />
            Last Name: <input type="text" name="lastname"><br />
            <input type="hidden" name="status" value="3">
            <input type="submit" value="Get User Points"><br />
        </form>
        <form action="/Switch2wellWEB/index.jsp">
            <h3>Add Users Points</h3>
            First Name: <input type="text" name="firstname"><br />
            Last Name: <input type="text" name="lastname"><br />
            Points: <input type="text" name="points"><br />
            <input type="hidden" name="status" value="4">
            <input type="submit" value="Add Users Points">
        </form>
    </body>
</html>
