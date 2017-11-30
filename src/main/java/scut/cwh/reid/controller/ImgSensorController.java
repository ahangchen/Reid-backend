package scut.cwh.reid.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import scut.cwh.reid.domain.Greeting;
import scut.cwh.reid.config.FileServerProperties;

import java.util.concurrent.atomic.AtomicLong;

//
//public class CityRestController {
//
//    @Autowired
//    private CityService cityService;
//
//    @RequestMapping(value = "/api/city/{id}", method = RequestMethod.GET)
//    public City findOneCity(@PathVariable("id") Long id) {
//        return cityService.findCityById(id);
//    }
//
//    @RequestMapping(value = "/api/city", method = RequestMethod.GET)
//    public List<City> findAllCity() {
//        return cityService.findAllCity();
//    }
//
//    @RequestMapping(value = "/api/city", method = RequestMethod.POST)
//    public void createCity(@RequestBody City city) {
//        cityService.saveCity(city);
//    }
//
//    @RequestMapping(value = "/api/city", method = RequestMethod.PUT)
//    public void modifyCity(@RequestBody City city) {
//        cityService.updateCity(city);
//    }
//
//    @RequestMapping(value = "/api/city/{id}", method = RequestMethod.DELETE)
//    public void modifyCity(@PathVariable("id") Long id) {
//        cityService.deleteCity(id);
//    }
//}

@RequestMapping("/sensor/img")
@Controller
public class ImgSensorController {
    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();


//
//    public ImgSensorController(FileServerProperties fileServerProperties) {
//        this.fileServerProperties = fileServerProperties;
//    }

    @RequestMapping(value="/{sensor_id}/{year}/{month}/{day}/{hour}/{minute}/{second}", method = RequestMethod.PUT)
    public @ResponseBody
    Greeting recordImg(@PathVariable("sensor_id") Long sensorId,
                       @PathVariable("year") Integer year, @PathVariable("month") Integer month, @PathVariable("day") Integer day,
                       @PathVariable("hour") Integer hour, @PathVariable("minute") Integer minute, @PathVariable("second") Integer second
                       ) {
        return new Greeting(counter.incrementAndGet(), new FileServerProperties().getHost());
    }

//    @RequestMapping(value={"/{name}", "/{name}/hello"}, method = {RequestMethod.GET})

}