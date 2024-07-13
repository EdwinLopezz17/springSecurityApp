package com.app.SpringSecurityApp.app;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v7/app")
public class TestController {

    @PostMapping()
    //@PreAuthorize("hasAuthority('CREATE')")
    public String testPost() {
        return "test-post";
    }

    @GetMapping()
    public String testGet() {
        return "test-get";
    }

    @PutMapping()
    public String testPut() {
        return "test-put";
    }

    @DeleteMapping()
    //@PreAuthorize("hasAnyAuthority('DELETE')")
    public String testDelete() {
        return "test-delete";
    }
}
