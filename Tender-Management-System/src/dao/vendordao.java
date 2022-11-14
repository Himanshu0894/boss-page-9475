package dao;
import java.util.List;
import masai.vendor;
public interface vendordao {

	public String registerVendor(vendor vendor);
	public List<vendor>getAllVendors();
	public boolean validatePassword(String vendorId,String password);
	public String updateProfile(vendor vendor);
	public String changePassword(String vendorID,String oldPassword,String newPassword);
	public vendor getVendorDataByID(String vendorID);
}
