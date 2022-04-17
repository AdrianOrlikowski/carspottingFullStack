package pl.orlikowski.carspottingBack.API;

import pl.orlikowski.carspottingBack.tools.Tools;

public class CarGetDTO {
    private Long carId;
    private String make;
    private String model;

    public CarGetDTO() {}

    public CarGetDTO(String make, String model) {
        this.make = make;
        this.model = model;
    }

    public CarGetDTO(Long carId, String make, String model) {
        this.carId = carId;
        this.make = make;
        this.model = model;
    }

    public Long getCarId() { return carId; }

    public void setCarId(Long carId) { this.carId = carId; }

    public String getMake() { return make; }

    public void setMake(String make) { this.make = Tools.capitalize(make);}

    public String getModel() { return model; }

    public void setModel(String model) { this.model = Tools.capitalize(model); }
}
