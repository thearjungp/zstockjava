package com.stockmarket.users;

import com.stockmarket.util.MySqlClient;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserService {

    Connection connection = MySqlClient.getConnection();

    public User getUserById(int id) throws Exception
    {
        PreparedStatement st = connection.prepareStatement("SELECT * FROM users WHERE user_id = ?");
        st.setInt(1, id);
        ResultSet rs  = st.executeQuery();

        if (!rs.isBeforeFirst()) {
            throw new Exception("Unable to find the user with the given User ID");
        }

        User user = new User();
        while(rs.next())
        {
            user.setId(rs.getInt("user_id"));
            user.setName(rs.getString("name"));
            user.setEmail(rs.getString("email"));
            user.setPhone(rs.getString("phone"));
            user.setPan(rs.getString("pan"));
            user.setAcntNumber(rs.getString("acnt_number"));
            user.setRole(rs.getInt("role"));
            user.setAvailableMargin(rs.getFloat("available_margin"));
        }
        return user;
    }


    public List<MinimalUser> getAllUsers() throws SQLException{

        List<MinimalUser> users = new ArrayList<>();
        Statement st = connection.createStatement();
        ResultSet rs  = st.executeQuery("SELECT user_id, name FROM users");

//        while(rs.next())
//        {
//            User user = new User();
//            user.setId(rs.getInt("user_id"));
//            user.setName(rs.getString("name"));
//            user.setEmail(rs.getString("email"));
//            user.setPhone(rs.getString("phone"));
//            user.setPan(rs.getString("pan"));
//            user.setAcntNumber(rs.getString("acnt_number"));
//            user.setRole(rs.getInt("role"));
//            user.setAvailableMargin(rs.getFloat("available_margin"));
//            users.add(user);
//        }

        while(rs.next())
        {
            MinimalUser user = new MinimalUser();
            user.setId(rs.getInt("user_id"));
            user.setName(rs.getString("name"));
            users.add(user);
        }

        return users;
    }

    public String createUser(User user) throws SQLException
    {
        PreparedStatement st = connection.prepareStatement("INSERT INTO users(name, email, phone, pan, acnt_number, role, available_margin) VALUES (?, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
        st.setString(1, user.getName());
        st.setString(2, user.getEmail());
        st.setString(3, user.getPhone());
        st.setString(4, user.getPan());
        st.setString(5, user.getAcntNumber());
        st.setInt(6, user.getRole());
        st.setFloat(7, user.getAvailableMargin());

        int affectedRows  = st.executeUpdate();
        ResultSet rs = st.getGeneratedKeys();
        rs.next();
        int insertedId = rs.getInt(1);
        return "New User has been created with a User ID " + insertedId ;
    }



    public String updateUser(int userId, User user) throws SQLException
    {
        PreparedStatement st = connection.prepareStatement("UPDATE users SET name=?, email=?, phone=?, pan=?, acnt_number=?, role=?, available_margin=? WHERE user_id=?");
        st.setString(1, user.getName());
        st.setString(2, user.getEmail());
        st.setString(3, user.getPhone());
        st.setString(4, user.getPan());
        st.setString(5, user.getAcntNumber());
        st.setInt(6, user.getRole());
        st.setFloat(7, user.getAvailableMargin());

        st.setInt(8, userId);

        int affectedRows  = st.executeUpdate();
        return "User UPDATED";
    }



    public int updateUserMargin(float amount, User user) throws SQLException
    {
        PreparedStatement st = connection.prepareStatement("UPDATE users SET available_margin=? WHERE user_id=?");
        st.setFloat(1, amount);
        st.setInt(2, user.getId());

        int insertId  = st.executeUpdate();
        return insertId;
    }



    public String deleteUser(User user) throws SQLException {
        PreparedStatement st = connection.prepareStatement("DELETE FROM users WHERE user_id=?");
        st.setInt(1, user.getId());

        int insertId  = st.executeUpdate();
        return "User DELETED";
    }

}
