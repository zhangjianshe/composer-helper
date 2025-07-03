package cn.cangling.docker.composer.server.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Rest123Controller {
    @GetMapping("/test")
    public String test() {
        return "Hello";
    }
}
