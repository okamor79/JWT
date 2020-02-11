package com.idea.useradmin.controller;

import com.idea.useradmin.repository.UtilsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.sql.Clob;

@RestController
@CrossOrigin(maxAge = 3600)
public class MainController {

    @Autowired
    UtilsRepository utilsRepository;

    @GetMapping(path = "/getVersion")
    public ResponseEntity<String> getVersion() {
        return ResponseEntity.ok(this.utilsRepository.getVersion());
    }

    @GetMapping(path = "/getRoles", produces = "application/json; charset=UTF-8")
    public ResponseEntity<String> getRoles() {
        return ResponseEntity.ok(this.utilsRepository.getRoles());
    }

    @GetMapping(path = "/getUserList", produces = "application/json; charset=UTF-8")
    public ResponseEntity<String> getUsers()  {
        return ResponseEntity.ok(this.utilsRepository.getUserList());
    }

    @PostMapping(path = "/resetPassword", produces = "application/json")
    public ResponseEntity<String> resetPassword(@RequestParam(required = true) String username) {
        return ResponseEntity.ok(this.utilsRepository.resetPassword(username));
    }

}
