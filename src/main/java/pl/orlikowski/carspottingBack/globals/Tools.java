package pl.orlikowski.carspottingBack.globals;
import org.apache.commons.imaging.Imaging;

import org.apache.commons.imaging.formats.jpeg.JpegImageMetadata;
import org.apache.commons.imaging.formats.tiff.TiffField;
import org.apache.commons.imaging.formats.tiff.TiffImageMetadata;
import org.apache.commons.imaging.formats.tiff.constants.ExifTagConstants;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

//Class containing useful static methods
public abstract class Tools {

    //Function returning the file extension from filename. If it cannot retrieve it returns null
    public static String getExtension(MultipartFile file) {
        try {
            String name = file.getOriginalFilename();
            String[] splitName = name.split("\\.");
            return splitName[splitName.length - 1];
        } catch(Exception e) {
            return null;
        }
    }

    //Function extracting dateTime from jpeg file. If it cannot retrieve it returns null
    public static LocalDateTime getDateTaken(File file) {
        try {
            JpegImageMetadata meta = (JpegImageMetadata) Imaging.getMetadata(file);
            TiffImageMetadata exif = meta.getExif();
            TiffField dateTimeTE = exif.findField(ExifTagConstants.EXIF_TAG_DATE_TIME_ORIGINAL);
            String dateTimeStr = (String) dateTimeTE.getValue();
            return LocalDateTime.parse(dateTimeStr, DateTimeFormatter.ofPattern("yyyy:MM:dd HH:mm:ss"));
        } catch (Exception e) {
            return null;
        }
    }
    //Function capitalizing Strings
    public static String capitalize(String str)
    {
        if(str == null) return str;
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }


}



