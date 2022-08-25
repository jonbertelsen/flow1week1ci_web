package dat.demo;

import dat.persistence.UserMapper;

import java.io.*;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "hello", value = "/hello")
public class HelloServlet extends HttpServlet
{
    private String message;
    private UserMapper userMapper = new UserMapper();

    public HelloServlet() throws SQLException, ClassNotFoundException
    {
    }

    public void init()
    {
        message = "Hello World!";
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
    {
        response.setContentType("text/html");
        try
        {
            request.setAttribute("userlist", userMapper.getUsers());
        }
        catch (SQLException | ClassNotFoundException e)
        {
            throw new RuntimeException(e);
        }

        request.getRequestDispatcher("index.jsp").forward(request, response);

    }

    public void destroy()
    {
    }
}