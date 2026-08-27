package com.sih.saksham.controller;

import com.sih.saksham.repository.UserRepository;
import com.sih.saksham.service.SakshamService;
import com.sih.saksham.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.Map;

@Controller
public class MainController {

    @Autowired
    private SakshamService service;
    
    @Autowired
    private UserRepository repo;

    @GetMapping("/")
    public String home() { return "index"; }

    @GetMapping("/dashboard")
    public String dashboard(Model m) { 
        m.addAttribute("users", repo.findAll()); 
        return "dashboard"; 
    }

    @GetMapping("/upload")
    public String uploadPage() { return "upload"; }
    
    @GetMapping("/test")
    public String test() { return "test"; }

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
        return Map.of("result", service.analyze(score));
    }
    
    @PostMapping("/api/submit-test")
    @ResponseBody
    public User submit(@RequestBody Map<String,Object> p){
        User u=new User(); 
        u.setName((String)p.get("name")); 
        u.setEmail((String)p.get("email"));
        u.setCommunicationScore((Integer)p.getOrDefault("comm", 0)); 
        u.setTechnicalScore((Integer)p.getOrDefault("tech", 0));
        return repo.save(u);
    }
}
