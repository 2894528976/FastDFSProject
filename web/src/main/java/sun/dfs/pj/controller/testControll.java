package sun.dfs.pj.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping
@CrossOrigin
public class testControll {
    @RequestMapping("/test")
    public String test(){
        return "test";
    }
}
