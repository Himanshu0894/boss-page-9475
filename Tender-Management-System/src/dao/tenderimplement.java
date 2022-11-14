package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import masai.tender;
import utility.util;
public class tenderimplement implements Tenderdao{

	@Override
	public List<tender> getAllTenders() {
		List<tender> tenderList =new ArrayList<tender>();
		Connection conne = util.provide();
		PreparedStatement ps =null;
		ResultSet rs =null;
		try {
			ps=conne.prepareStatement("select * from tender");
			rs =ps.executeQuery();
			while(rs.next()) {
				tender tender =new tender();
				tender.setTid(rs.getInt("tid"));
				tender.setTname(rs.getString("tname"));
				tender.setTtype(rs.getString("ttype"));
				tender.setTprice(rs.getInt("tprice"));
				tender.setTdesc(rs.getString("tdesc"));
				tender.setTstatus(rs.getString("tstatus"));
					tenderList.add(tender);
			}
		}
		catch(SQLException e) {
			System.out.println("Exception Occurred..");
		}
		finally {
			util.close(ps);
			util.close(rs);
			util.close(conne);
		}
		return tenderList;
	}

	@Override
	public String createTender(tender tender) {
		String status = "Tender Addition Failed!";
		Connection conne = util.provide();
		PreparedStatement stmt=null;
		PreparedStatement ps =null;
		try {
			ps = conne.prepareStatement("select * from tender where tname=? AND ttype=? AND tprice =? AND tdesc =?");
			ps.setString(1, tender.getTname());
			ps.setString(2, tender.getTtype());
			ps.setInt(3, tender.getTprice());
			ps.setString(4,tender.getTdesc());
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				status = "Tender Failed! \nTender already exists with ID: "+rs.getInt("tid");
			}
			else {
				try {
					stmt = conne.prepareStatement("insert into tender(tname,ttype,tprice,tdesc,tstatus) values(?,?,?,?,?)");
					stmt.setString(1, tender.getTname());
					stmt.setString(2, tender.getTtype());
					stmt.setInt(3, tender.getTprice());
					stmt.setString(4, tender.getTdesc());
					stmt.setString(5, tender.getTstatus());
					int a = stmt.executeUpdate();
					if(a>0) {
						status = "New tender insert \nYour Tender ID: "+ getTender(tender);
						
					}
				}
				catch(SQLException e) {
					status ="Error : "+e.getMessage();
				}
			}
		}
		catch(SQLException e) {
			status ="Error : "+e.getMessage();
		}
		finally {
			util.close(stmt);
			util.close(conne);
			util.close(ps);
		}
		return status;
	}
	@Override
	public int getTender(tender tender) {
	int tid =-1;
	Connection conne = util.provide();
	PreparedStatement ps =null;
	try {
		ps = conne.prepareStatement("select * from tender where tname =? AND ttype=? AND tprice=? AND tdesc =?");
		ps.setString(1, tender.getTname());
		ps.setString(2, tender.getTtype());
		ps.setInt(3, tender.getTprice());
		ps.setString(4, tender.getTdesc());
		ResultSet rs =ps.executeQuery();
		if(rs.next()) {
			tid = rs.getInt("tid");
		}
	}
	catch(SQLException e) {
		System.out.println("Exception occureed");
	}
	finally {
		util.close(ps);
		util.close(conne);
	}
		return tid;
	}

	@Override
	public String updateTender(tender tender) {
		String status = "Tender Updation Failed!";
	Connection conne = util.provide();
	PreparedStatement stmt =null;
		try {
			stmt = conne.prepareStatement("UPDATE tender SET tname=?,ttype =?,tprice=?,tdesc=?,tstatus=? where tid =?");
			stmt.setString(1, tender.getTname());
			stmt.setString(2, tender.getTtype());
			stmt.setInt(3, tender.getTprice());
			stmt.setString(4, tender.getTdesc());
			stmt.setString(5, tender.getTstatus());
			stmt.setInt(6, tender.getTid());
			
			int a = stmt.executeUpdate();
			if(a>0) {
				status ="Tender details updated Successfully!";
			}
		}
		catch(SQLException e) {
			status ="Error: "+e.getMessage();
		}
		finally {
			util.close(stmt);
			util.close(conne);
		}
		return status;
	}

	@Override
	public tender getTenderDataById(int tenderID) {
		tender tender =null;
		Connection conne = util.provide();
		PreparedStatement stmt = null;
		try {
			stmt = conne.prepareStatement("select * from tender");
			stmt.setInt(1, tenderID);
			ResultSet rs =stmt.executeQuery();
			if(rs.next()) {
				int id= rs.getInt(1);
				String name =rs.getString(2);
				String type = rs.getString(3);
				int price = rs.getInt(4);
				String desc = rs.getString(5);
				String status = rs.getString(6);
				tender = new tender(id,name,type,price,desc,status);
			}
		}
		catch(SQLException e) {
			System.out.println("Exception Occurred");
		}
		finally {
			util.close(stmt);
			util.close(conne);
		}
		return tender;
	}

	@Override
	public String getTenderStatus(int tenderID) {
		String status ="Not Assign";
		Connection conne = util.provide();
		PreparedStatement ps =null;
		ResultSet rs =null;
		try {
			ps = conne.prepareStatement("select * from tender where tid=?");
			ps.setInt(1, tenderID);
			rs=ps.executeQuery();
			if(rs.next()) {
				status = rs.getString("tstatus");
			}
			else {
				status = "Tender ID not found: "+ tenderID;
			}
		}
		catch(SQLException e) {
			status ="Error: "+e.getMessage();
		}
		finally {
			util.close(conne);
			util.close(ps);
			util.close(rs);
		}
		return status;
		
	}

	@Override
	public String assignTender(int tenderID) {
	String status = "Tender Assigning Failed";
	Connection conne =util.provide();
	PreparedStatement stmt=null;
	ResultSet rs =null;
	try {
		stmt=conne.prepareStatement("update tender set tstatus='Assigned' where tid=?");
		stmt.setInt(1,tenderID);
		int c =stmt.executeUpdate();
		if(c>0) {
			status ="Tender: "+tenderID+ "has been Assigned";
		}
	}
	catch(SQLException e) {
		status = status+e.getMessage();
	}
	finally {
		util.close(stmt);
		util.close(rs);
		util.close(conne);
	}
	return status;
	}

	@Override
	public List<tender> getAllAssignedTender() {
		List<tender>statusList = new ArrayList<tender>();
		Connection conne = util.provide();
		PreparedStatement ps =null;
		ResultSet rs =null;
		try {
			ps = conne.prepareStatement("select * from tender where tstatus='Assigned'");
			rs=ps.executeQuery();
			while(rs.next()) {
				tender tender = new tender();
				tender.setTid(rs.getInt("tid"));
				tender.setTname(rs.getString("tname"));
				tender.setTprice(rs.getInt("tprice"));
				tender.setTdesc(rs.getString("tdesc"));
				tender.setTstatus(rs.getString("tstatus"));
				statusList.add(tender);
			}
			}
		catch(SQLException e) {
			System.out.println("Exception occured");
		}
		finally {
			util.close(conne);
			util.close(ps);
			util.close(rs);
		}
		return statusList;
	}



}
