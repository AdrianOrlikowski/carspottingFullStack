package pl.orlikowski.carspottingBack.API;

import org.apache.commons.io.IOUtils;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import pl.orlikowski.carspottingBack.tools.Globals;

import java.io.IOException;
import java.io.InputStream;

@Controller
@RequestMapping
public class FrontController {

    @GetMapping
    public String getIndex() {
        return "index";
    }

    @GetMapping(path="/addspot")
    public String getAddspot() {
        return "addspot";
    }






}
