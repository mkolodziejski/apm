package pl.mkolodziejski.apm.client_app.webapp.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pl.mkolodziejski.apm.client_app.webapp.service.IdentifierManager;

@RestController
@RequestMapping(value = "/delayed")
public class DelayedApi {

    @Autowired
    private IdentifierManager identifierManager;

    @RequestMapping(value = "nextId", method = RequestMethod.GET)
    public String nextId() {

        try {
            Thread.sleep(1500L);
        } catch (InterruptedException e) {
            throw new RuntimeException("Interrupted");
        }

        return "" + identifierManager.nextId();
    }
}
