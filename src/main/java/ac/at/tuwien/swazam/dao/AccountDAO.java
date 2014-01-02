package ac.at.tuwien.swazam.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import ac.at.tuwien.swazam.model.Account;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

public class AccountDAO {
	private Connection connection;

    /*public AccountDAO() {
    	try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("insert into users(firstname,lastname,dob,email) values (?, ?, ?, ? )");
            // Parameters start with 1
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setDate(3, new java.sql.Date(user.getDob().getTime()));
            preparedStatement.setString(4, user.getEmail());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }*/
    
    public Account getUserById(int accountid) {
        Account user = new Account();
        try {
            PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement("select * from users where userid=?");
            preparedStatement.setInt(1, accountid);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
             /*   user.setUserid(rs.getInt("userid"));
                user.setFirstName(rs.getString("firstname"));
                user.setLastName(rs.getString("lastname"));
                user.setDob(rs.getDate("dob"));
                user.setEmail(rs.getString("email"));*/
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }
}
