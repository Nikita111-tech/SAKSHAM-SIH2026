package com.sih.saksham.controller;

import com.sih.saksham.service.SakshamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.Map;

@Controller
public class MainController {

    @Autowired
    private SakshamService service;

    @GetMapping("/")
    public String home() { return "index"; }

    @GetMapping("/dashboard")
    public String dashboard() { return "dashboard"; }

    @GetMapping("/upload")
    public String uploadPage() { return "upload"; }

    @PostMapping("/api/generate")
    @ResponseBody
    public ResponseEntity<?> generate(@RequestParam("file") MultipartFile file) {
        try {
            String text = service.extractText(file);
            String mcqs = service.generateMcqs(text);
            return ResponseEntity.ok(mcqs);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @PostMapping("/api/analyze")
    @ResponseBody
    public Map<String, Object> analyze(@RequestParam("score") int score) {
        return service.analyzeGap(score);
    }
}
