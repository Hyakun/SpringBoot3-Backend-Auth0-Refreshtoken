package com.example.demo.secured;

import com.example.demo.auth.RegisterRequest;
import com.example.demo.datamodels.*;
import com.example.demo.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/secured")
public class SecuredController {
    private final SecuredService securedService;
    @PostMapping("/edit/user")
    public ResponseEntity<String> EditUserData(@RequestHeader(value="token") String token, @RequestBody RegisterRequest request){
        return ResponseEntity.ok(securedService.EditUserData(token, request));
    }
    @GetMapping("/get/details")
    public ResponseEntity<StudentDetails>GetDetailsStudent(@RequestHeader(value="token") String token){
        return ResponseEntity.ok(securedService.StudentInfo(token));
    }
    @GetMapping("/getall/user")
    public ResponseEntity<List<User>> GetUsers(){
        return ResponseEntity.ok(securedService.GetUsers());
    }
    @PostMapping("/update/details")
    public ResponseEntity<String>UpdateDetailsStudent(@RequestHeader(value="token") String token, @RequestBody StudentDetailsRequest request){
        return ResponseEntity.ok(securedService.EditStudentDetails(token, request));
    }
    @GetMapping("/teacher/code")
    public ResponseEntity<String> GetTeacherCode(@RequestHeader(value = "token") String token){
        return ResponseEntity.ok(securedService.GetTeacherCode(token));
    }
    @PostMapping("/add/materie")
    public ResponseEntity<String> AdaugaMaterie(@RequestHeader(value = "token") String token, @RequestBody String numeMaterie){
        return ResponseEntity.ok(securedService.AddMaterie(token, numeMaterie));
    }
    @GetMapping("/getall/materie")
    public ResponseEntity<List<Materie>> GetAllMaterii() {
        return ResponseEntity.ok(securedService.GetMaterii());
    }
    @PostMapping("/add/nota")
    public ResponseEntity<String>AddNota(@RequestHeader(value = "token") String token, @RequestBody NotaRequest notaRequest){
        return ResponseEntity.ok(securedService.AddNota(token, notaRequest));
    }
    @GetMapping("/get/note/personale")
    public ResponseEntity<List<Nota>>GetNotePersonale(@RequestHeader(value = "token") String token){
        return ResponseEntity.ok(securedService.GetNotePersonale(token));
    }

    @GetMapping("/get/note/student")
    public ResponseEntity<List<Nota>>GetNoteByUser(@RequestParam Integer idStudent){
        return ResponseEntity.ok(securedService.GetNoteByUser(idStudent));
    }
    @GetMapping("/get/note/materie")
    public ResponseEntity<List<Nota>>GetNoteByMaterie(@RequestHeader(value = "token") String token, @RequestParam Integer idMaterie){
        return ResponseEntity.ok(securedService.GetNoteByMaterie(token, idMaterie));
    }
}
