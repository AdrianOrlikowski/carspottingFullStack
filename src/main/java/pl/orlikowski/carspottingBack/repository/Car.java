package pl.orlikowski.carspottingBack.repository;

import pl.orlikowski.carspottingBack.tools.Tools;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity(name = "Car")
@Table(name = "car")
public class Car {
    @Id
    @SequenceGenerator(
            name = "car_id_sequence",
            sequenceName = "car_id_sequence"
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "car_id_sequence"
    )
    @Column(
            name = "car_id",
            updatable = false)
    private Long carId;
    @Column(
            name = "make",
            nullable = false
    )
    private String make;
    @Column(
            name = "model",
            nullable = false
    )
    private String model;
    @OneToMany(
            mappedBy = "car",
            orphanRemoval = true
    )
    private List<Spotting> spottings; // = new ArrayList<>();
    ///////////////////////////////////////////////////////
    //Constructors, getters, setters & toString
    public Car(String make, String model) {
        this.make = Tools.capitalize(make);
        this.model = Tools.capitalize(model);
    }

    public Car() {}

    public Long getCarId() {
        return carId;
    }

    public void setCarId(Long carId) {
        this.carId = carId;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = Tools.capitalize(make);
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = Tools.capitalize(model);
    }

    public List<Spotting> getSpottings() {
        return spottings;
    }

    public void setSpottings(List<Spotting> spottings) {
        this.spottings = spottings;
    }

    @Override
    public String toString() {
        return "Car{" +
                "carId=" + carId +
                ", make='" + make + '\'' +
                ", model='" + model + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return make.toLowerCase().equals(car.make.toLowerCase()) && model.toLowerCase().equals(car.model.toLowerCase());
    }

    @Override
    public int hashCode() {
        return Objects.hash(make, model);
    }
}
