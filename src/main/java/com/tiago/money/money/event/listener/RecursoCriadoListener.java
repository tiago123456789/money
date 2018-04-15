package com.tiago.money.money.event.listener;

import com.tiago.money.money.event.RecursoCriadoEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import java.net.URI;

public class RecursoCriadoListener implements ApplicationListener<RecursoCriadoEvent> {


    @Override
    public void onApplicationEvent(RecursoCriadoEvent recursoCriadoEvent) {
        setLocationInHeader(
                recursoCriadoEvent.getResponse(),
                getLocation(recursoCriadoEvent.getId()).toString()
        );
    }

    private void setLocationInHeader(HttpServletResponse response, String location) {
        response.addHeader("Location", location);
    }

    private URI getLocation(Long id) {
        return ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(id).toUri();
    }
}
