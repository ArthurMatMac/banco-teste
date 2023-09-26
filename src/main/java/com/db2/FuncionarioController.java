package com.db2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/funcionarios")
public class FuncionarioController {
    private final FuncionarioRepository funcionarioRepository;

    @Autowired
    public FuncionarioController(FuncionarioRepository funcionarioRepository){
        this.funcionarioRepository = funcionarioRepository;
    }

    @GetMapping("/lista")
    public List<Funcionario> listOfficials(){
        return funcionarioRepository.findAll();
    }

    @PostMapping("/")
    public ResponseEntity<Funcionario> addOfficial(
            @RequestBody Funcionario body
    ){
        funcionarioRepository.save(body);
        return ResponseEntity.ok(body);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Funcionario> getOfficialById(
            @PathVariable Integer id
    ){
        Funcionario funcionario = this.funcionarioRepository.findById(id).orElse(null);
        if(funcionario != null){
            return ResponseEntity.ok(funcionario);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("atualizar/{id}")
    public ResponseEntity<Funcionario> updateOfficialById(
            @PathVariable Integer id,
            @RequestBody Funcionario body
    ){
        Optional<Funcionario> funcionario = funcionarioRepository.findById(id);
        if(funcionario.isPresent()){
            Funcionario newFuncionario = funcionario.get();
            if(body.getCargo() != null){
                newFuncionario.setCargo(body.getCargo());
            }


            if (body.getSalario() != 0.0f) {
                newFuncionario.setSalario(body.getSalario());
            }

            if (body.getDepartamento() != null) {
                newFuncionario.setDepartamento(body.getDepartamento());
            }

            Funcionario func = this.funcionarioRepository.save(newFuncionario);
            return ResponseEntity.ok(func);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/remove/{date}")
    public ResponseEntity<String> removeByDate(
            @PathVariable String date
    ) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date data = formatter.parse(date);


        Funcionario func = funcionarioRepository.findFuncionarioByData_admissao(data);
        funcionarioRepository.delete(func);

        return ResponseEntity.ok("sumiu");
    }
}
