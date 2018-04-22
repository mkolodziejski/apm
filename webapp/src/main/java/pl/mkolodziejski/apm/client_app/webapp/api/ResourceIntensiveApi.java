package pl.mkolodziejski.apm.client_app.webapp.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.mkolodziejski.apm.client_app.webapp.service.IdentifierManager;
import pl.mkolodziejski.apm.client_app.webapp.service.PrimeNumbersManager;

import java.util.List;

@RestController
@RequestMapping(value = "/resource_intensive")
public class ResourceIntensiveApi {

    @Autowired
    private PrimeNumbersManager primeNumbersManager;


    @RequestMapping(value = "is_prime/{number}", method = RequestMethod.GET)
    public String isPrime(@PathVariable(name = "number") Integer number) {
        return "" + primeNumbersManager.isPrime(number);
    }


    @RequestMapping(value = "gc", method = RequestMethod.GET)
    public String gc() {
        System.gc();
        return "OK";
    }
}
