package scut.cwh.reid.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import scut.cwh.reid.domain.Greeting;
import scut.cwh.reid.config.FileServerProperties;

import java.util.concurrent.atomic.AtomicLong;

@Controller
@RequestMapping("/hello")
public class HelloWorldController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();
    @Autowired
    private FileServerProperties fileServerProperties;

    @RequestMapping(method=RequestMethod.GET)
    public @ResponseBody
    Greeting sayHello(@RequestParam(value="name", required=false, defaultValue="Stranger") String name) {
        return new Greeting(counter.incrementAndGet(), String.format(template, name));
    }

    @GetMapping(value={"/{name}"})
    public @ResponseBody String testApi(@PathVariable("name") String name, @RequestParam(value="id", defaultValue = "-1") Integer id) {
        return fileServerProperties.getHost() + ',' + name + "," + id;
    }

    @GetMapping(value={"/{name}/hi"})
    public @ResponseBody Object testList(@PathVariable("name") String name, @RequestParam(value="id", defaultValue = "-1") Integer id) {
        return fileServerProperties.getHost() + ',' + name + "," + id;
    }

}