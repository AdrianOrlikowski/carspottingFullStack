package pl.orlikowski.carspottingBack;

import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import pl.orlikowski.carspottingBack.API.SpottingDTO;
import pl.orlikowski.carspottingBack.repository.*;
import pl.orlikowski.carspottingBack.security.PassEncoder;
import pl.orlikowski.carspottingBack.tools.Globals;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
public class CarspottingApplication {


	public static void main(String[] args) {
		SpringApplication.run(CarspottingApplication.class, args);
	}

	@Bean
	ModelMapper modelMapper() { return new ModelMapper();}

	@Bean
	CommandLineRunner commandLineRunner(SpottingRepo spottingRepo, CarRepo carRepo, UserRepo userRepo, PassEncoder passEncoder) {
		return args -> {
			System.out.println("Testing CommandLineRunner");

			//UserDetails testing
			BCryptPasswordEncoder encoder = passEncoder.bCryptPasswordEncoder();

			//Test user
			AppUser user1 = new AppUser("adrian","adrian@gmail.com",
					encoder.encode("adrian"));
			userRepo.save(user1);

			//Test cars
			Car car1 = new Car("Ferrari", "Testarossa");
			Car car2 = new Car("Jaguar", "E-Type");
			Car car3 = new Car("Mazda", "MX-5");
			carRepo.saveAll(List.of(car1, car2, car3));
			//Test spotting
			Spotting spot1 = new Spotting(user1, car1, LocalDateTime.now(),
					Globals.picPath + "1.jpeg");
			Spotting spot2 = new Spotting(user1, car2, LocalDateTime.now(),
					Globals.picPath + "2.jpeg");
			Spotting spot3 = new Spotting(user1, car3, LocalDateTime.now(),
					Globals.picPath + "3.jpeg");
			spottingRepo.saveAll(List.of(spot1, spot2, spot3));

			//ModelMapper testing
			ModelMapper modelMapper1 = new ModelMapper();

			SpottingDTO getSpot = modelMapper1.map(spot1, SpottingDTO.class);
			System.out.println(getSpot);




			//spottingRepo.deleteById(1L);
			//carRepo.deleteById(1L);
			//user1.addSpot(car3);
			//userRepo.save(user1);



		};


	}

}
