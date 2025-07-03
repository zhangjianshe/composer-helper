package cn.cangling.docker.composer.server.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * control page navigation
 */
@Controller
public class PageController {
    /**
     * index page
     *
     * @return
     */
    @GetMapping("/")
    public String index() {
        return "index";
    }


}
