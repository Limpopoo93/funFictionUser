package FunFictionUserProject.funFictionUser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Map;
@Controller
@SpringBootApplication
public class FunFictionUserApplication {

	public static void main(String[] args) {
		SpringApplication.run(FunFictionUserApplication.class, args);
	}
	@RequestMapping("/")
	String index() {
		return "index";
	}

}
