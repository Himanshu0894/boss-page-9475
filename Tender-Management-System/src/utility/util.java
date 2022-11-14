package utility;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
public class util {
private static Connection conn=null;
public util() {
	
}
public static Connection provide() {
	try {
		if(conn==null || conn.isClosed()==true) {
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
			}
			catch(ClassNotFoundException e) {
				e.printStackTrace();
			}
			String url="jdbc:mysql://localhost:3306/tendersystem";
			conn = DriverManager.getConnection(url,"root","root");
		}
	}
	catch(SQLException e) {
		e.printStackTrace();
	}
	return conn;
}
public static void close(PreparedStatement stmt) {
	try {
		if(stmt !=null) {
			stmt.close();
		}
	}
	catch(SQLException e) {
		e.printStackTrace();
	}
}
public static void close(ResultSet rs) {
	try {
		if(rs!=null) {
			rs.close();
		}
	}
	catch(SQLException e) {
		e.printStackTrace();
	}
}
public static void close(Connection conne) {
	try {
		if(conne!=null) {
			conne.close();
		}
	}
	catch(SQLException e) {
		e.printStackTrace();
		
	}
}
}
