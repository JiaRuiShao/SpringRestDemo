package rest_demo.controller;

import io.swagger.annotations.ResponseHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import rest_demo.dto.ErrorResponse;
import rest_demo.entity.Provider;
import rest_demo.exception.ProviderNotFoundException;
import rest_demo.service.ProviderService;

import javax.servlet.annotation.HttpConstraint;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/provider")
public class ProviderController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProviderController.class);

    ProviderService providerService;

    public ProviderController(ProviderService providerService) {
        this.providerService = providerService;
    }

    @GetMapping("/{id}")
    public Provider findById(@PathVariable(required = true) long id) { // localhost:8080/provider?id=12345
        Optional<Provider> provider = providerService.findById(id);
        if (!provider.isPresent()) throw new ProviderNotFoundException("PROVIDER_NOT_FOUND");
        return provider.get();
    }

    @GetMapping("/name")
    public List<Provider> findByName(@RequestParam(required = true) String name, @RequestParam(required = false) String direction) { // localhost:8080/provider?id=12345
        return providerService.findByNamePattern("name" + "%", direction);
        // or return providerService.findByNameStartingWith(name);
    }

    @GetMapping("/year")
    public List<Provider> findByContractYear(@RequestParam(required = false, defaultValue = "0") int year) {
        return providerService.findByContractYearGreaterThanEqual(year);
    }

    @PostMapping
    public ResponseEntity<Provider> saveProvider(@Validated @RequestBody Provider provider,
                                 @RequestHeader("Authorization") String token,
                                 UriComponentsBuilder ucBuilder) {
        // valid the token using jjwt
        // after validate, save the provider info
        Provider savedProvider = providerService.saveProvider(provider);
        HttpHeaders header = new HttpHeaders();
        header.setLocation(ucBuilder.path("/provider/{id}").buildAndExpand(provider.getId()).toUri());
        return new ResponseEntity<Provider>(savedProvider, header, HttpStatus.CREATED);

    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    public Provider deleteProvider(@RequestParam long id, @RequestHeader("Authorization") String token) {
        // valid the token using jjwt
        // after validate, save the provider info
        Optional<Provider> deletedProvider = providerService.findById(id);
        if (!deletedProvider.isPresent()) throw new ProviderNotFoundException("PROVIDER_NOT_FOUND");
        providerService.deleteById(id);
        return deletedProvider.get();
    }

    @PutMapping
    public Provider updateProvider(@RequestBody Provider provider, @RequestHeader("Authorization") String token) {
        // valid the token using jjwt
        // after validate, save the provider info
        Optional<Provider> updatedProvider = providerService.findById(provider.getId());
        if (!updatedProvider.isPresent()) throw new ProviderNotFoundException("PROVIDER_NOT_FOUND");
        providerService.updateProvider(provider);
        return providerService.findById(provider.getId()).get();
    }

    @PatchMapping
    public Provider partialUpdateProvider(@RequestBody Provider provider, @RequestHeader("Authorization") String token) {
        // valid the token using jjwt
        // after validate, save the provider info
        Optional<Provider> updatedProvider = providerService.findById(provider.getId());
        if (!updatedProvider.isPresent()) throw new ProviderNotFoundException("PROVIDER_NOT_FOUND");
        providerService.updateProvider(provider);
        return providerService.findById(provider.getId()).get();
    }

}
