package pl.orlikowski.carspottingBack.repository;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Entity(name = "Spotting")
@Table(name = "spotting")
public class Spotting {
    @Id
    @SequenceGenerator(
            name = "spot_id_sequence",
            sequenceName = "spot_id_sequence"
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "spot_id_sequence"
    )
    @Column(
            name = "spot_id",
            updatable = false)
    private Long spotId;
    @Column(nullable = false)
    private LocalDateTime dateTime;
    @ManyToOne()
    @JoinColumn(
            name = "user_id",
            referencedColumnName = "user_id",
            foreignKey = @ForeignKey(name = "user_id_fk")
    )
    private AppUser appUser;
    @ManyToOne()
    @JoinColumn(
            name = "car_id",
            referencedColumnName = "car_id",
            foreignKey = @ForeignKey(name = "car_id_fk")
    )
    private Car car;
    @Column(name = "pic_url")
    private String picURL;
    //private PicLocation PicLocation;


    public Spotting(AppUser appUser, Car car, LocalDateTime dateTime, String picURL) {
        this.dateTime = dateTime.truncatedTo(ChronoUnit.SECONDS);
        this.appUser = appUser;
        this.car = car;
        this.picURL = picURL;
    }

    public Spotting(AppUser appUser, Car car, LocalDateTime dateTime) {
        this.appUser = appUser;
        this.car = car;
        this.dateTime = dateTime;
    }

    public Spotting() {}

    public Long getSpotId() { return spotId; }

    public AppUser getAppUser() {
        return appUser;
    }

    public void setAppUser(AppUser appUser) {
        this.appUser = appUser;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public LocalDateTime getDateTime() { return dateTime; }

    public void setDateTime(LocalDateTime dateTime) { this.dateTime = dateTime.truncatedTo(ChronoUnit.SECONDS); }

    public String getPicURL() { return picURL; }

    public void setPicURL(String picURL) { this.picURL = picURL; }
}
