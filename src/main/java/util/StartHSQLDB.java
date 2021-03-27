package util;

import org.hsqldb.util.DatabaseManagerSwing;

public class StartHSQLDB {
	public static String FILE_URL = "file:~/Documents";
	public static String JDBC_FILE_URL = "jdbc:hsqldb:" + FILE_URL;
	public static String JDBC_MEMORY_URL = "jdbc:hsqldb:mem:exemplo-db"; 
	public static void main(String[] args) {
		System.out.println(JDBC_FILE_URL);
		//local();
		server();
	}

	public static void local() {
		final String[] dbArgs = { "--user", "sa", "--password", "", "--url", JDBC_FILE_URL};
		DatabaseManagerSwing.main(dbArgs);

	}
	public static void server() {
		final String[] dbArgs = { "--database.0", FILE_URL, "--dbname.0", "jpa-db", "--port", "5454" };
		org.hsqldb.server.Server.main(dbArgs);
		//final String[] serveArgs = { "--user", "sa", "--password", "", "--url", "jdbc:hsqldb:hsql://localhost:5454/jpa-db"};
		//DatabaseManagerSwing.main(serveArgs);
	}
}
