package com.example.demo.controller.usercontroller;
import java.util.UUID;
import javax.validation.Valid;
import com.example.demo.application.userapplication.UserDTOIn;
import com.example.demo.application.userapplication.UserDTOOut;
import com.example.demo.application.userapplication.UpdateDTO;
import com.example.demo.application.userapplication.UserApplication;
import com.example.demo.domain.userdomain.UserProjection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserApplication userApplication;

    @Autowired
    public UserController(final UserApplication userApplication){
        this.userApplication = userApplication;
    } 
    
    @PreAuthorize("hasRole('USER')")
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<UserDTOOut> create(@Valid @RequestBody UserDTOIn userDTOIn){
        return this.userApplication.add(userDTOIn);

    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE,  path = "/{id}")
    public Mono<ResponseEntity<UserDTOIn>> get(@Valid @PathVariable UUID id) {
        Mono<UserDTOIn> UserDTOIn = this.userApplication.get(id);
        return UserDTOIn.map(user -> ResponseEntity.ok(user)).defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE, path = "/{id}")
    public Mono<ResponseEntity<Void>> update(@PathVariable UUID id,@Valid @RequestBody UpdateDTO updateDTO) {
        return this.userApplication.update(id, updateDTO).map(response -> ResponseEntity.ok().<Void>build()).defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<UserProjection> getAll(
        @RequestParam(required = false) String firstname,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size
    ){
        return this.userApplication.getAll(firstname, page, size);
    }
}
