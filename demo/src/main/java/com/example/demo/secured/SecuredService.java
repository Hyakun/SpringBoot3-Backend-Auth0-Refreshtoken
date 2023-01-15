package com.example.demo.secured;

import com.example.demo.auth.RegisterRequest;
import com.example.demo.config.JwtService;
import com.example.demo.datamodels.*;
import com.example.demo.user.Role;
import com.example.demo.user.User;
import com.example.demo.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SecuredService {
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final StudentDetailsRepository studentDetailsRepository;
    private final ProfessorCodeRepository professorCodeRepository;
    private final MaterieRepository materieRepository;
    private final NotaRepository notaRepository;
    public String EditUserData(String token, RegisterRequest request){
        String numeHeader = jwtService.extractUsername(token);
        var user = userRepository.findByEmail(numeHeader).orElseThrow();
        user.setEmail(request.getEmail());
        user.setFirstname(request.getFirstname());
        user.setLastname(request.getLastname());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        userRepository.save(user);
        return "Modificarile au fost salvate";
    }

    public StudentDetails StudentInfo(String token){
        String numeHeader = jwtService.extractUsername(token);
        var user = userRepository.findByEmail(numeHeader).orElseThrow();
        StudentDetails details = studentDetailsRepository.findByUser(user).orElseThrow();
        return details;
    }

    public String EditStudentDetails(String token, StudentDetailsRequest request){
        String numeHeader = jwtService.extractUsername(token);
        var user = userRepository.findByEmail(numeHeader).orElseThrow();
        StudentDetails details = studentDetailsRepository.findByUser(user).orElseThrow();
        details.setCnp(request.getCnp());
        details.setNrTelefon(request.getNrTelefon());
        details.setNumeMama(request.getNumeMama());
        details.setNumeTata(request.getNumeTata());
        details.setNrTelefonMama(request.getNrTelefonMama());
        details.setNrTelefonTata(request.getNrTelefonTata());
        details.setAdresa(request.getAdresa());
        studentDetailsRepository.save(details);
        return "Detaliile dumneavoastra au fost salvate cu succes";
    }

    public String GetTeacherCode(String token){
        String numeHeader = jwtService.extractUsername(token);
        var user = userRepository.findByEmail(numeHeader).orElseThrow();
        if(user.getRole() != Role.ADMIN){
            return "Nu detineti acest drept";
        }

        ProfessorCode code = new ProfessorCode();

        professorCodeRepository.save(code);

        return code.getCode();
    }

    public String AddMaterie(String token, String numeMaterie){
        String numeHeader = jwtService.extractUsername(token);
        var user = userRepository.findByEmail(numeHeader).orElseThrow();
        if(user.getRole() == Role.USER){
            return "Nu detineti acest drept";
        }
        var materieVerify = materieRepository.findBynumeMaterie(numeMaterie);
        if(!materieVerify.isEmpty()){
            return "Exista deja aceasta materie";
        }
        Materie materie = new Materie();
        materie.setNumeMaterie(numeMaterie);
        materieRepository.save(materie);
        return "Materie adaugata cu succes!";
    }

    public List<Materie> GetMaterii(){
        return materieRepository.findAll();
    }

    public List<User>GetUsers(){
        return userRepository.findAll();
    }

    public String AddNota(String token, NotaRequest notaRequest){
        String numeHeader = jwtService.extractUsername(token);
        var user = userRepository.findByEmail(numeHeader).orElseThrow();
        if(user.getRole() == Role.USER){
            return "Nu detineti acest drept";
        }
        var student = userRepository.findById(notaRequest.getIdUser()).orElseThrow();
        var materie = materieRepository.findById(notaRequest.getIdMaterie()).orElseThrow();

        Nota nota = new Nota();
        nota.setNota(notaRequest.getNota());
        nota.setUser(student);
        nota.setMaterie(materie);

        notaRepository.save(nota);

        return "Nota salvata cu succes!";
    }

    public List<Nota> GetNotePersonale(String token){
        String numeHeader = jwtService.extractUsername(token);
        var user = userRepository.findByEmail(numeHeader).orElseThrow();
        return notaRepository.findAllByUser(user);
    }

    public List<Nota> GetNoteByUser(Integer idUser){
        var user = userRepository.findById(idUser).orElseThrow();
        if(user.getRole() == Role.USER){
            throw new RuntimeException();
        }
        return notaRepository.findAllByUser(user);
    }
    public List<Nota> GetNoteByMaterie(String token, Integer idMaterie){
        String numeHeader = jwtService.extractUsername(token);
        var user = userRepository.findByEmail(numeHeader).orElseThrow();
        if(user.getRole() == Role.USER){
            throw new RuntimeException();
        }
        var materie = materieRepository.findById(idMaterie).orElseThrow();
        return notaRepository.findAllByMaterie(materie);
    }


}
