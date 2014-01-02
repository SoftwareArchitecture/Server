package src.main.swazam.dao;

import java.sql.Connection;

public class DBAccess {
	private Connection connect = null;
    private final String URL = "jdbc:mysql://localhost/swazam";
    private final String USER = "root";
    private final String PASSWORD = "";
    private String INSTRUCTIONS = new String();

    public static Connection getConnection() throws SQLException
    {
    	Class.forName("com.mysql.jdbc.Driver");
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
	
	public void createDB() {
		 String s            = new String();
	        StringBuffer sb = new StringBuffer();

	        try
	        {
	            FileReader fr = new FileReader(new File("swazam.sql"));
	            // be sure to not have line starting with "--" or "/*" or any other non aplhabetical character

	            BufferedReader br = new BufferedReader(fr);

	            while((s = br.readLine()) != null)
	            {
	                sb.append(s);
	            }
	            br.close();

	            // here is our splitter ! We use ";" as a delimiter for each request
	            // then we are sure to have well formed statements
	            String[] inst = sb.toString().split(";");

	            Connection c = Database.getConnection();
	            Statement st = c.createStatement();

	            for(int i = 0; i<inst.length; i++)
	            {
	                // we ensure that there is no spaces before or after the request string
	                // in order to not execute empty statements
	                if(!inst[i].trim().equals(""))
	                {
	                    st.executeUpdate(inst[i]);
	                    System.out.println(">>"+inst[i]);
	                }
	            }
	  
	        }
	        catch(Exception e)
	        {
	            System.out.println("*** Error : "+e.toString());
	            System.out.println("*** ");
	            System.out.println("*** Error : ");
	            e.printStackTrace();
	            System.out.println("################################################");
	            System.out.println(sb.toString());
	        }
	}
}
