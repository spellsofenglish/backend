package com.pat.soe.controller;

import org.springframework.web.bind.annotation.CrossOrigin;

import static org.springframework.web.bind.annotation.RequestMethod.*;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;

@CrossOrigin(origins = "http://localhost:3000",
        methods = {GET, POST, PUT, DELETE},
        allowedHeaders = "authorization, content-type",
        allowCredentials = "true", maxAge = 3600)
public interface GlobalController {
}
