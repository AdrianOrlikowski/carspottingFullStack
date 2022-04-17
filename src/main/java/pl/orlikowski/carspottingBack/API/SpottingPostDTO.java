package pl.orlikowski.carspottingBack.API;

import org.springframework.web.multipart.MultipartFile;
import pl.orlikowski.carspottingBack.tools.Tools;

import java.time.LocalDateTime;

public class SpottingPostDTO {
    private Long spotId;
    private String appUserUsername;
    private LocalDateTime dateTime;
    private String carMake;
    private String carModel;
    private MultipartFile carPicFile;

    public SpottingPostDTO(String appUserUsername, String carMake, String carModel, MultipartFile multipartFile) {
        this.appUserUsername = appUserUsername;
        this.carMake = carMake;
        this.carModel = carModel;
        this.carPicFile = multipartFile;
    }

    public SpottingPostDTO() {}

    public Long getSpotId() { return spotId; }

    public void setSpotId(Long spotId) { this.spotId = spotId; }

    public String getAppUserUsername() { return appUserUsername; }

    public void setAppUserUsername(String appUserUsername) { this.appUserUsername = appUserUsername; }

    public LocalDateTime getDateTime() { return dateTime; }

    public void setDateTime(LocalDateTime dateTime) { this.dateTime = dateTime; }

    public String getCarMake() { return carMake; }

    public void setCarMake(String carMake) { this.carMake = Tools.capitalize(carMake); }

    public String getCarModel() { return carModel; }

    public void setCarModel(String carModel) { this.carModel = Tools.capitalize(carModel); }

    public MultipartFile getCarPicFile() { return carPicFile; }

    public void setCarPicFile(MultipartFile carPicFile) { this.carPicFile = carPicFile; }

    @Override
    public String toString() {
        return "SpottingPostDTO{" +
                "spotId=" + spotId +
                ", appUserUsername='" + appUserUsername + '\'' +
                ", dateTime=" + dateTime +
                ", carMake='" + carMake + '\'' +
                ", carModel='" + carModel + '\'' +
                ", carPicFile=" + carPicFile +
                '}';
    }
}
