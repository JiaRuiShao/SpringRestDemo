package rest_demo.service;

import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Service
public class UriComponentsBuilderService {
    public URI buildURI(String path, String param, UriComponentsBuilder ucBuilder) {
        return ucBuilder.path(path).buildAndExpand(param).toUri();
    }
}
