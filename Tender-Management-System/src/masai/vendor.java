package masai;

public class vendor {
private String vid;
private String vpassword;
private String vname;
private String vemail;
private String vmob;
private String vcompany;
private String vaddress;
public vendor(String username, String password) {
	super();
	// TODO Auto-generated constructor stub
}
public vendor(String vid, String vpassword, String vname, String vemail, String vmob, String vcompany,
		String vaddress) {
	super();
	this.vid = vid;
	this.vpassword = vpassword;
	this.vname = vname;
	this.vemail = vemail;
	this.vmob = vmob;
	this.vcompany = vcompany;
	this.vaddress = vaddress;
}
public String getVid() {
	return vid;
}
public void setVid(String vid) {
	this.vid = vid;
}
public String getVpassword() {
	return vpassword;
}
public void setVpassword(String vpassword) {
	this.vpassword = vpassword;
}
public String getVname() {
	return vname;
}
public void setVname(String vname) {
	this.vname = vname;
}
public String getVemail() {
	return vemail;
}
public void setVemail(String vemail) {
	this.vemail = vemail;
}
public String getVmob() {
	return vmob;
}
public void setVmob(String vmob) {
	this.vmob = vmob;
}
public String getVcompany() {
	return vcompany;
}
public void setVcompany(String vcompany) {
	this.vcompany = vcompany;
}
public String getVaddress() {
	return vaddress;
}
public void setVaddress(String vaddress) {
	this.vaddress = vaddress;
}
@Override
public String toString() {
	return "vendor [vid=" + vid + ", vpassword=" + vpassword + ", vname=" + vname + ", vemail=" + vemail + ", vmob="
			+ vmob + ", vcompany=" + vcompany + ", vaddress=" + vaddress + "]";
}

}
