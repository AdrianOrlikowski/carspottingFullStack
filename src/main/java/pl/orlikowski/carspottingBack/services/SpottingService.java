package pl.orlikowski.carspottingBack.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.orlikowski.carspottingBack.API.DTOs.SpottingPostDTO;
import pl.orlikowski.carspottingBack.businessClasses.AppUser;
import pl.orlikowski.carspottingBack.businessClasses.Car;
import pl.orlikowski.carspottingBack.businessClasses.Spotting;
import pl.orlikowski.carspottingBack.exceptions.SpotAddException;
import pl.orlikowski.carspottingBack.repositories.*;
import pl.orlikowski.carspottingBack.globals.Globals;
import pl.orlikowski.carspottingBack.globals.Tools;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class SpottingService {

    private final SpottingRepo spottingRepo;
    private final CarRepo carRepo;
    private final UserRepo userRepo;

    @Autowired
    public SpottingService(SpottingRepo spottingRepo, CarRepo carRepo, UserRepo userRepo) {
        this.spottingRepo = spottingRepo;
        this.carRepo = carRepo;
        this.userRepo = userRepo;
    }

    public List<Spotting> getAllSpots() { return spottingRepo.findAll(); }

    public Optional<Spotting> getSpot(Long id) { return spottingRepo.findById(id);}

    public List<Spotting> getSpots(String carMake, String carModel) {
        return spottingRepo.findAllByCarMakeIgnoreCaseAndCarModelIgnoreCase(carMake, carModel);
    }
    public List<Spotting> getSpots(String carMake) {
        return spottingRepo.findAllByCarMakeIgnoreCase(carMake);
    }

    public List<Spotting> getSpotsByUser(String AppUserUsername) {
        return spottingRepo.findAllByAppUserUsername(AppUserUsername);
    }

    public List<Car> getAllCars() { return carRepo.findAll(); }

    public Spotting addSpot(SpottingPostDTO postDTO) throws RuntimeException {
        Optional<AppUser> optUser = userRepo.findUserByUsernameIgnoreCase(postDTO.getAppUserUsername());
        if(optUser.isEmpty()) {
            throw new SpotAddException("only registered users can add spottings");
        } else {
            Spotting spot = null;
            //Checking if submitted car is already in the database
            Optional<Car> optCar = carRepo.findFirst1ByMakeIgnoreCaseAndModelIgnoreCase(postDTO.getCarMake(), postDTO.getCarModel());
            if (optCar.isEmpty()) {
                Car newCar = new Car(postDTO.getCarMake(), postDTO.getCarModel());
                carRepo.save(newCar);
                spot = new Spotting(optUser.get(), newCar, LocalDateTime.now());
            } else {
                spot = new Spotting(optUser.get(), optCar.get(), LocalDateTime.now());
            }
            spottingRepo.save(spot);
            //getting the assigned spotId
            Long spotId = spot.getSpotId();

            //saving the submitted picture
            try {
                File file = new File(Globals.savePath + spotId + ".jpeg");
                postDTO.getCarPicFile().transferTo(file);
                spot.setPicURL(Globals.picPath + spotId + ".jpeg");

                //Extracting the date taken from picture and saving in the spot if available.
                //If not submission time retrieved earlier by .now() is used
                LocalDateTime dateTaken = Tools.getDateTaken(file);
                if (dateTaken != null) {
                    spot.setDateTime(dateTaken);
                }
                //saving changes in Spotting object
                spottingRepo.save(spot);
                return spot;
            } catch (IOException e) {
                //if the pic could not be saved we clear the spot from the repository and throw SpotAddException
                spottingRepo.deleteById(spotId);
                throw new SpotAddException("Could not save the picture, spot not added");
            }

        }
    }

    public Spotting deleteSpot(Long spotId, String appUserUsername) {
        Optional<Spotting>spotToDelete = spottingRepo.findById(spotId);
        if(spotToDelete.isEmpty()) {
            throw new RuntimeException("Spot not found");
        }
        String addedBy = spotToDelete.get().getAppUser().getUsername();
        if(!addedBy.equals(appUserUsername)) {
               throw new RuntimeException("You don't have privileges to delete this spot." +
                       "Only OP can delete his/her spots.") ;
        }
            spottingRepo.deleteById(spotId);
            return spotToDelete.get();
    }


}
