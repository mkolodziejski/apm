package pl.mkolodziejski.apm.client_app.webapp.service;

import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

@Component
public class IdentifierManager {

    private AtomicInteger nextId = new AtomicInteger(0);

    public Integer nextId() {
        return nextId.getAndIncrement();
    }
}
