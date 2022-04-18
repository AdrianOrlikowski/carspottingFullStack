package pl.orlikowski.carspottingBack.API;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.orlikowski.carspottingBack.businessClasses.Car;
import pl.orlikowski.carspottingBack.businessClasses.Spotting;

import java.util.List;


@Service
public class DtoMapper {

    private final ModelMapper modelMapper;

    @Autowired
    public DtoMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public SpottingGetDTO toSpottingDTO(Spotting spotting) {
        return modelMapper.map(spotting, SpottingGetDTO.class);
    }

    public CarGetDTO toCarDTO(Car car) {
        return modelMapper.map(car, CarGetDTO.class);
    }

    public List<SpottingGetDTO> toSpottingDTO(List<Spotting> spottings) {
        return spottings.stream()
                .map(spot -> modelMapper.map(spot, SpottingGetDTO.class))
                .toList();
    }

    public List<CarGetDTO> toCarDTO(List<Car> cars) {
        return cars.stream()
                .map(car-> modelMapper.map(car, CarGetDTO.class))
                .toList();
    }


}
