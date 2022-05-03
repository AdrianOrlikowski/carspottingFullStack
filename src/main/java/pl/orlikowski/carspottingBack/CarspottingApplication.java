package pl.orlikowski.carspottingBack;


import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import pl.orlikowski.carspottingBack.businessClasses.AppUser;
import pl.orlikowski.carspottingBack.businessClasses.Car;
import pl.orlikowski.carspottingBack.businessClasses.Spotting;
import pl.orlikowski.carspottingBack.services.TokenGenerator;
import pl.orlikowski.carspottingBack.repositories.*;
import pl.orlikowski.carspottingBack.services.*;
import pl.orlikowski.carspottingBack.security.PassEncoder;
import pl.orlikowski.carspottingBack.globals.*;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
public class CarspottingApplication {


	public static void main(String[] args) {
		SpringApplication.run(CarspottingApplication.class, args);
	}


	@Bean
	CommandLineRunner commandLineRunner(SpottingRepo spottingRepo, CarRepo carRepo, UserRepo userRepo, PassEncoder passEncoder,
										SpottingService spottingService, TokenGenerator tokenGenerator) {
		return args -> {
			System.out.println("Testing CommandLineRunner");

			//UserDetails testing
			BCryptPasswordEncoder encoder = passEncoder.bCryptPasswordEncoder();

			//Test user
			AppUser user1 = new AppUser("adrian","adrian@gmail.com",
					encoder.encode("adrian"),
					true, "adrian");

			userRepo.save(user1);

			AppUser user2 = new AppUser("orka","orka@gmail.com",
					encoder.encode("orka"),
					true, "orka");

			userRepo.save(user2);

			//Test cars
			Car car1 = new Car("Ferrari", "Testarossa");
			Car car2 = new Car("Jaguar", "E-Type");
			Car car3 = new Car("Mazda", "MX-5");
			carRepo.saveAll(List.of(car1, car2, car3));
			//Test spottings
			Spotting spot1 = new Spotting(user1, car1, LocalDateTime.now(),
					Globals.picPath + "1.jpeg");
			Spotting spot2 = new Spotting(user1, car2, LocalDateTime.now(),
					Globals.picPath + "2.jpeg");
			Spotting spot3 = new Spotting(user2, car3, LocalDateTime.now(),
					Globals.picPath + "3.jpeg");
			Spotting spot4 = new Spotting(user1, car3, LocalDateTime.now(),
					Globals.picPath + "4.jpeg");
			spottingRepo.saveAll(List.of(spot1, spot2, spot3, spot4));

		};


	}

}
