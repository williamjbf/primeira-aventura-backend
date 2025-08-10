package github.williamjbf.primeiraaventura;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Test {

    @GetMapping("/api/hello")
    public String hello() {
        return "API está funcionando!";
    }
}