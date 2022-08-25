package dat.persistence;

import dat.entities.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserMapper
{
    private Connection connection;

    public UserMapper() throws SQLException, ClassNotFoundException
    {
        connection = DBConnector.connection();
    }

    public List<User> getUsers() throws SQLException, ClassNotFoundException
    {
        List<User> userList = new ArrayList<>();
        String sql = "SELECT * FROM usertable";

        try (PreparedStatement ps = connection.prepareStatement(sql))
        {
            ResultSet rs = ps.executeQuery();
            while (rs.next())
            {
                String username = rs.getString("fname");
                String password = rs.getString("pw");
                userList.add(new User(username, password));
            }
        }
        catch (SQLException ex)
        {
            throw new SQLException("Fejl i DB query: " + ex.getMessage());
        }
        return userList;
    }

    public User getUserByName(String name) throws SQLException
    {
       User user = null;
       String sql = "SELECT * FROM usertable WHERE fname = ?";

       try (PreparedStatement ps = connection.prepareStatement(sql))
       {
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            if (rs.next())
            {
                String username = rs.getString("fname");
                String password = rs.getString("pw");
                user = new User(username, password);
            }
        }
        catch (SQLException ex)
        {
            throw new SQLException("Fejl i DB query: " + ex.getMessage());
        }
        return user;
    }

    public User updateUserByName(User user, User userToUpdate) throws SQLException
    {
        String sql = "UPDATE usertable SET fname = ?, pw = ? WHERE fname = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql))
        {
            ps.setString(1, userToUpdate.getUsername());
            ps.setString(2, userToUpdate.getPassword());
            ps.setString(3, user.getUsername());
            int rows = ps.executeUpdate();
            if (rows == 1)
            {
                return userToUpdate;
            } else {
                return null;
            }
        }
        catch (SQLException ex)
        {
            throw new SQLException("Fejl i DB query: " + ex.getMessage());
        }
    }
}
