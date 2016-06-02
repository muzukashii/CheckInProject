package camt.se331;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;

@SpringBootApplication
public class Lab11Application {
	public static void main(String[] args) {
		SpringApplication.run(Lab11Application.class, args);
		DBManager dbManager = new DBManager();
		dbManager.start();
	}
}

class DBManager extends Thread {
	public void run(){
		System.setProperty("java.awt.headless", "false");
//		org.hsqldb.util.DatabaseManagerSwing.main(new String[] {
//				"--url",  "jdbc:hsqldb:mem:testdb", "--noexit"
//		});
	}

}