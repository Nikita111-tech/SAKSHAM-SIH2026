package com.sih.saksham.service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.*;
@Service
public class GeminiService {
 @Value("${gemini.api.key}") private String apiKey;
 @Value("${gemini.api.url}") private String apiUrl;
 public String generateRoadmap(int comm, int tech, int team){
  String prompt = "Scores: Comm "+comm+", Tech "+tech+", Team "+team+". Gap=100-score. Give 6 week plan.";
  try{
   WebClient c = WebClient.create();
   Map body = Map.of("contents", List.of(Map.of("parts", List.of(Map.of("text", prompt)))));
   Map res = c.post().uri(apiUrl+"?key="+apiKey).bodyValue(body).retrieve().bodyToMono(Map.class).block();
   List cand = (List)res.get("candidates"); Map cont = (Map)((Map)cand.get(0)).get("content");
   List parts = (List)cont.get("parts"); return ((Map)parts.get(0)).get("text").toString();
  }catch(Exception e){ return "WEEK 1-2: Communication\nWEEK 3-4: Technical\nWEEK 5-6: Teamwork"; }
 }
}
