package main;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import masai.bidder;
import masai.tender;
import masai.vendor;
import dao.bidderdao;
import dao.daoimplement;
import dao.Tenderdao;
import dao.tenderimplement;
import dao.vendordao;
import dao.venderimplement;
import utility.util;

public class admin extends user{
	public admin() {
		super();
	}
	public admin(String username,String password) {
		super(username, password);
	}
	public boolean loginAdmin() {
		boolean loginStatus =false;
		Connection conne = util.provide();
		PreparedStatement ps =null;
		ResultSet rs =null;
		
		try {
			ps=conne.prepareStatement("select * from admin where username =? AND password =? ");
			ps.setString(1, this.getUsername());
			ps.setString(2, this.getPassword());
			rs = ps.executeQuery();
			if(rs.next()) {
				System.out.println("======================");
				System.out.println("Login Successful");
				System.out.println("===================");
				loginStatus =true;
			}
			else {
				System.out.println("================");
				System.out.println("Login Denied! Invalid user Details");
				System.out.println("=====================");
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		finally {
			util.close(ps);
			util.close(rs);
			util.close(conne);
		}
		return loginStatus;
	}
	
	public void registerVendor() {
		Scanner sc = new Scanner(System.in);
		try {
			System.out.println("Enter Vendor userId/username");
			String username = sc.nextLine();
			
			System.out.println("Enter Vendor Password");
			String password = sc.nextLine();
			String status = new venderimplement().registerVendor(new vendor(username,password));
			System.out.println("==========");
			System.out.println(status);
			System.out.println("===============");
		}
		catch(InputMismatchException e) {
			System.out.println("Invalid");
		}
	}
	public void viewAllVendor() {
		List<vendor>vendors =new venderimplement().getAllVendors();
		if(vendors.isEmpty()) {
			System.out.println("==========");
			System.out.println("No vendors Found");
			System.out.println("============");
		}
		else {
			System.out.println("==================");
			int count =1;
			for(int i=0;i<vendors.size();i++) {
				vendor v =vendors.get(i);
				System.out.println(count + "vendor Details ");
			}
		}
	}
	

}
