package pl.orlikowski.carspottingBack.API.DTOs;

import java.time.LocalDateTime;

public class SpottingGetDTO {
    private Long spotId;
    private String appUserUsername;
    private LocalDateTime dateTime;
    private String carMake;
    private String carModel;
    private String picURL;

    public SpottingGetDTO() {}

    public SpottingGetDTO(String appUserUsername, LocalDateTime dateTime, String carMake, String carModel, String picURL) {
        this.appUserUsername = appUserUsername;
        this.dateTime = dateTime;
        this.carMake = carMake;
        this.carModel = carModel;
        this.picURL = picURL;
    }

    public SpottingGetDTO(Long spotId, String appUserUsername, LocalDateTime dateTime, String carMake, String carModel, String picURL) {
        this.spotId = spotId;
        this.appUserUsername = appUserUsername;
        this.dateTime = dateTime;
        this.carMake = carMake;
        this.carModel = carModel;
        this.picURL = picURL;
    }

    public Long getSpotId() { return spotId; }

    public void setSpotId(Long spotId) { this.spotId = spotId; }

    public String getAppUserUsername() { return appUserUsername; }

    public void setAppUserUsername(String appUserUsername) { this.appUserUsername = appUserUsername;}

    public LocalDateTime getDateTime() { return dateTime; }

    public void setDateTime(LocalDateTime dateTime) { this.dateTime = dateTime; }

    public String getCarMake() { return carMake; }

    public void setCarMake(String carMake) { this.carMake = carMake;}

    public String getCarModel() { return carModel; }

    public void setCarModel(String carModel) { this.carModel = carModel; }

    public String getPicURL() { return picURL; }

    public void setPicURL(String picURL) { this.picURL = picURL; }


    @Override
    public String toString() {
        return "SpottingGetDTO{" +
                "spotId=" + spotId +
                ", appUserUsername='" + appUserUsername + '\'' +
                ", dateTime=" + dateTime +
                ", carMake='" + carMake + '\'' +
                ", carModel='" + carModel + '\'' +
                ", picURL='" + picURL + '\'' +
                '}';
    }
}
