package dao;
import java.util.List;

import masai.bidder;
public interface bidderdao {
public String acceptBid(int tenderID);
public String rejectBid(int tenderID);
public String bidTender(bidder bidder);
public List<bidder> getAllBidsofaTender(int tenderID);
public List<bidder> getAllBidsofaVendor(String vendorID);
public String getStatusofABid(int tid,String vendorID);
public bidder bestBids(int tenderID);
}
