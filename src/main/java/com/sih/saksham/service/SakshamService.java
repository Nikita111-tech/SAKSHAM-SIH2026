package com.sih.saksham.service;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import java.util.*;

@Service
public class SakshamService {

    @Value("${gemini.api.key}")
    private String apiKey;
    @Value("${gemini.api.url}")
    private String apiUrl;

    public String extractText(MultipartFile file) throws Exception {
        PDDocument doc = PDDocument.load(file.getInputStream());
        String text = new PDFTextStripper().getText(doc);
        doc.close();
        if(text.length() > 7000) text = text.substring(0, 7000);
        return text;
    }

    public String generateMcqs(String text) {
        try {
            RestTemplate rest = new RestTemplate();
            String url = apiUrl + "?key=" + apiKey;

            String prompt = "You are expert for Indian Official Statistics. From below text, generate 8 MCQs in JSON array format only. Format: [{\"question\":\"...\",\"optionA\":\"...\",\"optionB\":\"...\",\"optionC\":\"...\",\"optionD\":\"...\",\"correctAnswer\":\"B\",\"explanation\":\"Because page says...\"}]. No extra text. Text: " + text;

            Map<String, Object> body = Map.of(
                "contents", List.of(
                    Map.of("parts", List.of(Map.of("text", prompt)))
                )
            );

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);

            ResponseEntity<String> res = rest.exchange(url, HttpMethod.POST, entity, String.class);
            return res.getBody();
        } catch (Exception e) {
            return "{\"error\":\"" + e.getMessage() + " - Check API Key\"}";
        }
    }

    public Map<String, Object> analyzeGap(int score) {
        Map<String, Object> result = new HashMap<>();
        if(score <= 3) {
            result.put("level", "L2 - Beginner");
            result.put("gap", "Data Collection & Python Basics");
            result.put("courses", List.of(
                Map.of("name", "Data Analytics with Python - iGOT", "link", "https://portal.igotkarmayogi.gov.in/", "hours", "4.5 hrs"),
                Map.of("name", "Survey Methodology for Official Statistics - iGOT", "link", "https://portal.igotkarmayogi.gov.in/", "hours", "3 hrs")
            ));
        } else if(score <= 7) {
            result.put("level", "L3 - Intermediate");
            result.put("gap", "Data Visualization & SNA");
            result.put("courses", List.of(
                Map.of("name", "Data Visualization using Power BI - iGOT", "link", "https://portal.igotkarmayogi.gov.in/", "hours", "6 hrs"),
                Map.of("name", "System of National Accounts - NSSTA", "link", "https://portal.igotkarmayogi.gov.in/", "hours", "5 hrs")
            ));
        } else {
            result.put("level", "L4 - Advanced");
            result.put("gap", "Advanced Sampling & AI for Statistics");
            result.put("courses", List.of(
                Map.of("name", "AI/ML for Official Statistics - iGOT", "link", "https://portal.igotkarmayogi.gov.in/", "hours", "8 hrs")
            ));
        }
        return result;
    }
              }
