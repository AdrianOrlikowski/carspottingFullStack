package pl.orlikowski.carspottingBack.API;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.orlikowski.carspottingBack.exceptions.SpotAddException;
import pl.orlikowski.carspottingBack.repository.*;
import pl.orlikowski.carspottingBack.service.*;
import pl.orlikowski.carspottingBack.tools.Tools;


import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping(path = "data")
public class SpottingController {
    private final SpottingService spottingService;
    private final UserService userService;
    private final ModelMapper modelMapper;

    @Autowired
    public SpottingController(SpottingService spottingService, UserService userService, ModelMapper modelMapper) {
        this.spottingService = spottingService;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping(path = "/spots/{id}")
    public SpottingGetDTO getSpot(@PathVariable("id") Long id) throws RuntimeException {
        Optional<Spotting> spotOp = spottingService.getSpot(id);
        if(spotOp.isEmpty()) {
            throw new RuntimeException("Spot with selected id does not exist");
        } else {
            return modelMapper.map(spotOp.get(), SpottingGetDTO.class);
        }
    }

    @GetMapping(path = "/spots")
    public List<SpottingGetDTO> getAllSpots(){
        return spottingService.getAllSpots()
                .stream()
                .map(spot -> modelMapper.map(spot, SpottingGetDTO.class))
                .toList();
    }

    @GetMapping(path="/search")
    public List<SpottingGetDTO> getSpots(@RequestParam("carMake") String carMake,
                                         @RequestParam(value = "carModel", required = false) String carModel) {
        if(carModel == null || carModel.equals("")) {
            return spottingService.getSpots(carMake)
                    .stream()
                    .map(spot -> modelMapper.map(spot, SpottingGetDTO.class))
                    .toList();
        } else {
            return spottingService.getSpots(carMake, carModel)
                    .stream()
                    .map(spot -> modelMapper.map(spot, SpottingGetDTO.class))
                    .toList();
        }
    }
    @GetMapping(path="/myspots")
    public List<SpottingGetDTO> getMySpots() {
        //getting the username
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
         return spottingService.getSpotsByUser(username)
                 .stream()
                 .map(spot -> modelMapper.map(spot, SpottingGetDTO.class))
                 .toList();
    }

    @GetMapping(path="/cars")
    public List<CarGetDTO> getAllCars() {
        return spottingService.getAllCars()
                .stream()
                .map(car -> modelMapper.map(car, CarGetDTO.class))
                .toList(); }

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
            SpottingGetDTO retDTO = modelMapper.map(spot, SpottingGetDTO.class);
            return  retDTO;
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
            SpottingDTO retDTO = modelMapper.map(spot, SpottingDTO.class);
            return  retDTO;
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
        return modelMapper.map(spotDeleted, SpottingGetDTO.class);

    }
}
