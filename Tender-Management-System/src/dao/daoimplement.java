package dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import masai.bidder;
import utility.util;
public  class daoimplement implements bidderdao {

	@Override
	public String acceptBid(int tenderID) {
		String status ="Assignment Fail";
		Connection conne =util.provide();
		PreparedStatement ps =null;
		PreparedStatement stmt =null;
		ResultSet rs =null;
		try {
			ps = conne.prepareStatement("select * from tendersystem where tid=? AND tstatus ='Assign'");
			ps.setInt(1, tenderID);
			rs =ps.executeQuery();
			if(rs.next()) {
				status ="Tender Already Declare";
			}
			else {
				bidder b = bestBids(tenderID);
				if(b==null) {
					status ="No Bids for the Tendor is found";
				}
				else {
					stmt = conne.prepareStatement("update bidder set status =? where bid=? and status=?");
					stmt.setString(1, "Accepted");
					stmt.setInt(2, b.getBid());
					stmt.setString(3,"Pending");
					int a = stmt.executeUpdate();
					if(a>0) {
						status ="Bid Accepted Successfully!";
						Tenderdao dao = new tenderimplement();
						status = status +"\n"+ dao.assignTender(tenderID);
					}
				}
				
			}
		}
		catch(SQLException e) {
			status = status +"Error: "+e.getMessage();
		}
		finally {
			util.close(conne);
			util.close(ps);
			util.close(stmt);
		}
		return status;
	}

	@Override
	public String rejectBid(int tenderID) {
		String status ="Bid Rejection failed";
		Connection conne = util.provide();
		PreparedStatement ps =null;
		try {
			ps =conne.prepareStatement("update bidder set status =? where tid=? and status=?");
			ps.setString(1, "Rejected");
			ps.setInt(2,tenderID);
			ps.setString(3, "Pending");
			
			int b=ps.executeUpdate();
			if(b>0) {
				status ="Bid has been rejected";
				
			}
		}
			catch(SQLException e) {
				status = status + "Error: "+e.getMessage();
			}
			finally {
				util.close(conne);
				util.close(ps);
			}
			
		
		return status;
	}

	@Override
	public String bidTender(bidder bidder) {
		String status = "Tender Bidding Failed!";
		Connection conne = util.provide();
		PreparedStatement ps = null;
		try {
			ps = conne.prepareStatement("insert into bidder(vid,tid,bidAmount,status,biddate) values(?,?,?,?sysdate())");
			ps.setString(1,bidder.getVid());
			ps.setInt(2, bidder.getTid());
			ps.setInt(3, bidder.getBidAmount());
			ps.setString(4,bidder.getStatus());
			int a =ps.executeUpdate();
			if(a>0) {
				status = "You have successfully Bid for the tender";
				
			}
		}
		catch(SQLException e) {
			status = status +"Duplicate Bid found or Invalid TenderDetails Found";
		}
		finally {
			util.close(conne);
			util.close(ps);
		}
		return status;
	}

	@Override
	public List<bidder> getAllBidsofaTender(int tenderID) {
		List<bidder>bidderList = new ArrayList<bidder>();
		Connection conne = util.provide();
		PreparedStatement ps =null;
		ResultSet rs =null;
		try {
			ps =conne.prepareStatement("select * from bidder where tid=?");
			ps.setInt(1, tenderID);
			rs =ps.executeQuery();
			while(rs.next()) {
				bidder bidder =new bidder();
				bidder.setBidAmount(rs.getInt("bid"));
				bidder.setStatus(rs.getString("status"));
				bidder.setTid(rs.getInt("tid"));
				bidder.setVid(rs.getString("vid"));
				bidderList.add(bidder);
			}
		}
		catch(SQLException e) {
			System.out.println("Exception occurred");
		}
		finally {
			util.close(conne);
			util.close(ps);
			util.close(rs);
		}
		return bidderList;
	}

	@Override
	public List<bidder> getAllBidsofaVendor(String vendorID) {
		List<bidder>bidderList = new ArrayList<bidder>();
		Connection conne = util.provide();
		PreparedStatement ps =null;
		ResultSet rs = null;
		try {
			ps = conne.prepareStatement("select * from bidder where vid =?");
			ps.setString(1, vendorID);
			rs =ps.executeQuery();
			while(rs.next()) {
				bidder bidder = new bidder();
				bidder.setBid(rs.getInt("bid"));
				bidder.setVid(rs.getString("vid"));
				bidder.setTid(rs.getInt("tid"));
				bidder.setBidAmount(rs.getInt("bidamount"));
				bidder.setStatus(rs.getString("status"));
				bidderList.add(bidder);
			}
		}
		catch(SQLException e) {
			System.out.println("Exception occurred");
		}
		finally {
			util.close(conne);
			util.close(ps);
			util.close(rs);
		}
		return bidderList;
	}

	@Override
	public String getStatusofABid(int tid, String vendorID) {
		String status ="Bid Not Found.....";
		Connection conne = util.provide();
		PreparedStatement ps =null;
		ResultSet rs =null;
		try {
			ps =conne.prepareStatement("select * from bidder where tid=? AND vid=?");
			ps.setInt(1, tid);
			ps.setString(2, vendorID);
			rs = ps.executeQuery();
			if(rs.next()) {
				status = rs.getString("status");
			}
		}
		catch(SQLException e) {
			status = status + "Exception Occurred";
			
		}
		finally {
			util.close(conne);
			util.close(ps);
			util.close(rs);
		}
		return status;
	}

	@Override
	public bidder bestBids(int tendorID) {
		bidder bidder =null;
		Connection conne = util.provide();
		PreparedStatement ps =null;
		ResultSet rs =null;
		try {
			ps = conne.prepareStatement("select * from bidder where tid=? AND bidAmount = " 
					+ "(select min(bidAmount) from bidder where tid=?) AND biddate ="
					+"(select min(biddate) from bidder where tid=?)");
			ps.setInt(1, tendorID);
			ps.setInt(2, tendorID);
			ps.setInt(3, tendorID);
			rs = ps.executeQuery();
			if(rs.next()) {
				bidder = new bidder();
				bidder.setBid(rs.getInt("bid"));
				bidder.setVid(rs.getString("vid"));
				bidder.setTid(rs.getInt("tid"));
				bidder.setBidAmount(rs.getInt("bidamount"));
				bidder.setStatus(rs.getString("status"));
			}
		}
		catch(SQLException e) {
			System.out.println("Exception Occurred");
		}
		finally {
			util.close(ps);
			util.close(rs);
		}
		return bidder;
	}

	
}
