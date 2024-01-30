package org.example.cookieretceptg27.saved.controller;

import lombok.RequiredArgsConstructor;
import org.example.cookieretceptg27.saved.dto.SavedResponseDto;
import org.example.cookieretceptg27.saved.service.SavedService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/saved")
public class SavedController {
    private final SavedService service;
    @PostMapping("/{id}")
    public ResponseEntity<SavedResponseDto>saved(@PathVariable("id")UUID id)
    {
        SavedResponseDto savedResponseDto=service.saved(id);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedResponseDto);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<List<SavedResponseDto>>unsaved(@PathVariable("id")UUID id)
    {
       List<SavedResponseDto>savedResponseDto=service.unsaved(id);
       return ResponseEntity.ok(savedResponseDto);
    }
    @GetMapping
    public ResponseEntity<List<SavedResponseDto>>getAllSaved(){
        List<SavedResponseDto>savedResponseDtos=service.getAll();
        return ResponseEntity.ok(savedResponseDtos);
    }

}
