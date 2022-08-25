package dat.persistence;

import dat.entities.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserMapperTest
{
    private Connection connection;
    private UserMapper userMapper;

    {
        try
        {
            userMapper = new UserMapper();
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
        catch (ClassNotFoundException e)
        {
            throw new RuntimeException(e);
        }
    }

    @BeforeEach
    void setUp() {
        System.out.println("TESTINNNNGGGG");
        Connection con = null;
        try {
            con = DBConnector.connection();
            String createTable = "CREATE TABLE IF NOT EXISTS `startcode_test`.`usertable` (\n" +
                    "  `id` INT NOT NULL AUTO_INCREMENT,\n" +
                    "  `fname` VARCHAR(45) NULL,\n" +
                    "  `lname` VARCHAR(45) NULL,\n" +
                    "  `pw` VARCHAR(45) NULL,\n" +
                    "  `phone` VARCHAR(45) NULL,\n" +
                    "  `address` VARCHAR(45) NULL,\n" +
                    "  PRIMARY KEY (`id`));";
            con.prepareStatement(createTable).executeUpdate();
            String deleteRows = "DELETE FROM `startcode_test`.`usertable`";
            con.prepareStatement(deleteRows).executeUpdate();
            String SQL = "INSERT INTO startcode_test.usertable (fname, lname, pw, phone, address) VALUES (?,?,?,?,?)";
            PreparedStatement ps = con.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, "Hans");
            ps.setString(2, "Hansen");
            ps.setString(3, "Hemmelig123");
            ps.setString(4, "40404040");
            ps.setString(5,"Rolighedsvej 3");
            ps.executeUpdate();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @AfterEach
    void tearDown()
    {
    }

    @Test
    void getUsers() throws SQLException, ClassNotFoundException
    {
        List<User> userList = null;
        userList = userMapper.getUsers();
        int expected = 1;
        int actual = userList.size();
        assertEquals(expected, actual);
    }

    @Test
    void getUserByName() throws SQLException
    {
        User user = userMapper.getUserByName("Hans");
        String expected = "Hans";
        String actual = user.getUsername();
        assertEquals(expected, actual);
    }


    @Test
    void updateUserByName() throws SQLException
    {
        User user = userMapper.getUserByName("Hans");
        User userToUpdate = new User("Hansi","nytfunkypassword");
        User expected = userToUpdate;
        User actual = userMapper.updateUserByName(user, userToUpdate);
        assertEquals(expected, actual);
    }
}