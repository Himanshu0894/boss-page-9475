package dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import masai.vendor;
import utility.util;
public class venderimplement implements vendordao {

	@Override
	public String registerVendor(vendor vendor) {
		String status ="Registration Failed!";
		PreparedStatement ps =null;
		PreparedStatement stmt = null;
		Connection conne = util.provide();
		try{
			stmt=conne.prepareStatement("select * from vendor where vid=? and vpassword =?");
			stmt.setString(1, vendor.getVid());
			stmt.setString(2, vendor.getVpassword());
			ResultSet rs = stmt.executeQuery();
			if(rs.next()) {
				status = "Registration Declined! Vendor Id already Register";
			}
			else {
				try {
					ps=conne.prepareStatement("insert into vendor(vendorID,vpassword) values(?,?)");
					ps.setString(1, vendor.getVid());
					ps.setString(2, vendor.getVpassword());
					int c =ps.executeUpdate();
					if(c>0) {
						status ="Registration Successfull. \nYour Vendor id: "+vendor.getVid()+
								"\nThanks for Registration";
					}
				}
				catch(SQLException e) {
					status ="Error: "+e.getErrorCode()+ " : "+e.getMessage();
				}
			}
			
		}
		catch(SQLException e) {
			status ="Error: "+e.getErrorCode()+" : "+e.getMessage();
		}
		finally {
			util.close(stmt);
			util.close(ps);
		}
		return status;
	}

	@Override
	public List<vendor> getAllVendors() {
		List<vendor>vendorList = new ArrayList<vendor>();
		Connection conne = util.provide();
		PreparedStatement ps = null;
		ResultSet rs =null;
		try {
			ps =conne.prepareStatement("select * from vendor");
			rs = ps.executeQuery();
			while(rs.next()) {
				vendor vendor = new vendor(rs.getString("vendorID"),rs.getString("vpassword"),rs.getString("vname"),
						rs.getString("vemail"),rs.getString("vmob"),rs.getString("company"),rs.getString("address"));
				vendorList.add(vendor);
			}
			
		}
		catch(SQLException e) {
			System.out.println("Exception Occured...");
		}
		finally {
			util.close(rs);
			util.close(ps);
		}
		return vendorList;
	}

	@Override
	public boolean validatePassword(String vendorId, String password) {
		boolean flag=false;
		Connection conne = util.provide();
		PreparedStatement stmt = null;
		ResultSet rs =null;
		try {
			stmt = conne.prepareStatement("select * from vendor where vid=? and vpassword=?");
			stmt.setString(1, vendorId);
			stmt.setString(2, password);
			rs=stmt.executeQuery();
			if(rs.next()) {
				flag =true;
			}
			
		}
		catch(SQLException e) {
			System.out.println("Exception Occurred");
		}
		finally {
			util.close(stmt);
			util.close(rs);
		}
		return flag;
	}

	@Override
	public String updateProfile(vendor vendor) {
		String status ="Account Update failed";
		String vendorID = vendor.getVid();
		String password =vendor.getVpassword();
		vendordao dao =new venderimplement();
		if(!dao.validatePassword(vendorID, password)) {
			status = status +"\nYou have Entered Wrong Password!";
			return status;
		}
		Connection conne =util.provide();
		PreparedStatement ps =null;
		try {
			ps =conne.prepareStatement("update vendor set vname=?,vmob=?,vemail=?,company=?,address=? where vendorIDa=?");
			ps.setString(1, vendor.getVname());
			ps.setString(2, vendor.getVmob());
			ps.setString(3, vendor.getVemail());
			ps.setString(4, vendor.getVcompany());
			ps.setString(5, vendor.getVaddress());
			ps.setString(6, vendor.getVid());
			int a =ps.executeUpdate();
			if(a>0) {
				status ="Account Updated Successfully!";
			}
		}
		catch(SQLException e) {
			status = "Error: "+e.getMessage();
			
		}
		finally {
			util.close(ps);
		}
		return status;
	}

	@Override
	public String changePassword(String vendorID, String oldPassword, String newPassword) {
		String status = "Password Updation Failed!";
		vendordao dao = new venderimplement();
		if(!dao.validatePassword(vendorID, oldPassword)) {
			status = status +"You have Entered Wrong OLDPassword!";
			return status;
		}
		Connection conne = util.provide();
		PreparedStatement ps = null;
		try {
			ps=conne.prepareStatement("update vendor set password =? where vid=?");
			ps.setString(1, newPassword);
			ps.setString(2, vendorID);
			int a =ps.executeUpdate();
			if(a>0) {
				status ="Password Updated Successfully!";
			}
		}
		catch(SQLException e) {
			status = status + " Error: "+e.getMessage();
		}
		finally {
			util.close(conne);
			util.close(ps);
		}
		return status;
	}

	@Override
	public vendor getVendorDataByID(String vendorID) {
		vendor vendor = null;
		Connection conne =util.provide();
		PreparedStatement ps =null;
		ResultSet rs =null;
		try {
			ps = conne.prepareStatement("select * from vendor where vid=?");
			ps.setString(1, vendorID);
			rs =ps.executeQuery();
			if(rs.next()) {
				vendor = new vendor(rs.getString("vid"),rs.getString("vpassword"),rs.getString("vname"),
						rs.getString("vemail"),rs.getString("vmob"),rs.getString("company"),rs.getString("address"));
			}
		}
		catch(SQLException e) {
			System.out.println("Exception Occurred");
		}
		finally {
			util.close(conne);
			util.close(ps);
			util.close(rs);
		}
		return vendor;
	}

}
