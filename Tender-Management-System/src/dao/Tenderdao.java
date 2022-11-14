package dao;
import java.util.List;
import masai.tender;
public interface Tenderdao {
	public List<tender>getAllTenders();
	public String createTender(tender tender);
	public String updateTender(tender tender);
	public tender getTenderDataById(int tenderID);
	public String getTenderStatus(int tenderID);
	public String assignTender(int tenderID);
	public List<tender> getAllAssignedTender();
	public int getTender(tender tender);

}
