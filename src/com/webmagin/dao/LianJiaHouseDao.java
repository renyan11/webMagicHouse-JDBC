package com.webmagin.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import com.webmagin.entity.LianJiaHouseBean;

public class LianJiaHouseDao implements Cloneable{
	private Connection conn = null;
	private Statement stmt = null;
	
	public LianJiaHouseDao() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://localhost:3306/webmagic?user=root&password=root";
			conn = DriverManager.getConnection(url);
			stmt = conn.createStatement();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	public static LianJiaHouseDao getOne() {  
        try {  
        	LianJiaHouseDao ljhd = new LianJiaHouseDao();
            return (LianJiaHouseDao) ljhd.clone();  
        } catch (CloneNotSupportedException e) {  
            e.printStackTrace();  
        }  
        return null;  
    }  
	/**
	 * ´æÊý¾Ý¿â
	 * @param ljhb
	 * @return
	 */
	public int addHouseInfo(LianJiaHouseBean ljhb){
		try {
			String sql = "INSERT INTO `lianjiahouse` (`id`, `houseState`, `houseName`, `price`, `updateDate`,`tags`, `wuyeType`, `address`, `openOrderDate`,`contextPhone`) VALUES (?, ?, ?,?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, ljhb.getId());
			ps.setString(2, ljhb.getHouseState());
			ps.setString(3, ljhb.getHouseName());
			ps.setString(4, ljhb.getPrice());
			ps.setString(5, ljhb.getUpdateDate());
			ps.setString(6, ljhb.getTags());
			ps.setString(7, ljhb.getWuyeType());
			ps.setString(8, ljhb.getAddress());
			ps.setString(9, ljhb.getOpenOrderDate());
			ps.setString(10, ljhb.getContextPhone());
			return ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(conn != null)
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			if(stmt != null)
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		return -1;
	}
}
