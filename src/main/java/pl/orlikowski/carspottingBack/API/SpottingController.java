package pl.orlikowski.carspottingBack.API;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.orlikowski.carspottingBack.businessClasses.*;
import pl.orlikowski.carspottingBack.exceptions.SpotAddException;

import pl.orlikowski.carspottingBack.services.*;
import pl.orlikowski.carspottingBack.tools.Tools;


import java.util.List;
import java.util.Objects;
import java.util.Optional;

//Rest controller handling http requests. Requests can come directly from http client like postman.
//Front-end templates provided by FrontController call these via javascript
@RestController
@RequestMapping(path = "data")
public class SpottingController {
    private final SpottingService spottingService;
    //private final UserService userService;
    private final DtoMapper dtoMapper;

    @Autowired
    public SpottingController(SpottingService spottingService, DtoMapper dtoMapper) {
        this.spottingService = spottingService;
        this.dtoMapper = dtoMapper;
    }

    @GetMapping(path = "/spots/{id}")
    public SpottingGetDTO getSpot(@PathVariable("id") Long id) throws RuntimeException {
        Optional<Spotting> spotOp = spottingService.getSpot(id);
        if(spotOp.isEmpty()) {
            throw new RuntimeException("Spot with selected id does not exist");
        } else {
            return dtoMapper.toSpottingDTO(spotOp.get());
        }
    }

    @GetMapping(path = "/spots")
    public List<SpottingGetDTO> getAllSpots(){
        return dtoMapper.toSpottingDTO(spottingService.getAllSpots());
    }

    @GetMapping(path="/search")
    public List<SpottingGetDTO> getSpots(@RequestParam("carMake") String carMake,
                                         @RequestParam(value = "carModel", required = false) String carModel) {
        if(carModel == null || carModel.equals("")) {
            return dtoMapper.toSpottingDTO(spottingService.getSpots(carMake));
        } else {
            return dtoMapper.toSpottingDTO(spottingService.getSpots(carMake, carModel));
        }
    }
    @GetMapping(path="/myspots")
    public List<SpottingGetDTO> getMySpots() {
        //getting the username
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        //finding the spots, mapping and returning;
        return dtoMapper.toSpottingDTO(spottingService.getSpotsByUser(username));
    }

    @GetMapping(path="/cars")
    public List<CarGetDTO> getAllCars() {
        return dtoMapper.toCarDTO(spottingService.getAllCars());
    }

    @CrossOrigin
    @PostMapping(path = "/addspot")
    public SpottingGetDTO addSpot(@RequestParam("carMake") String carMake,
                                  @RequestParam("carModel") String carModel,
                                  @RequestParam("carPicFile") MultipartFile carPicFile) {
        //Checking if the form was populated correcttly
        if(carPicFile == null || !Objects.equals(Tools.getExtension(carPicFile), "jpeg")) {
            throw new SpotAddException("Submitted file must be in a .jpeg format");
        } else if(carMake.equals("") || carModel.equals("")) {
            throw new SpotAddException("Car make and model must be provided");
        } else {
            //getting the username
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();

            //Calling the spoting repository
            SpottingPostDTO postDTO = new SpottingPostDTO(username, carMake, carModel, carPicFile);
            Spotting spot = spottingService.addSpot(postDTO);
            return dtoMapper.toSpottingDTO(spot);
        }

        /*
    @CrossOrigin
    @PostMapping(path = "/addspot")
    public SpottingDTO addSpot(@ModelAttribute SpottingPostDTO postDTO) throws SpotAddException, IOException {
        if(postDTO.getCarPicFile() == null || !Tools.getExtension(postDTO.getCarPicFile()).equals("jpeg")) {
            throw new SpotAddException("Submitted file must be in a .jpeg format");
        } else if(postDTO.getCarMake().equals("") || postDTO.getCarModel().equals("")) {
            throw new SpotAddException("Car make and model must be provided");
        } else {
            Spotting spot = spottingService.addSpot(postDTO);
            return dtoMapper.toSpottingDTO(spot);
        }
    }
    */

    }

    @CrossOrigin
    @DeleteMapping(path ="/spots/{id}")
    public SpottingGetDTO deleteSpot(@PathVariable("id") Long spotId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Spotting spotDeleted = spottingService.deleteSpot(spotId, username);
        return dtoMapper.toSpottingDTO(spotDeleted);
    }
}
