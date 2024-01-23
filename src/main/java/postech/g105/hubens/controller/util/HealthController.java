package postech.g105.hubens.controller.util;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/health")
public class HealthController {
    @GetMapping
    public Mono<?> teste(@RequestParam(defaultValue = "123 Testando") String echo) {
        return Mono.just(echo + " .:. Backend rodando e respondendo.");
    }
}
