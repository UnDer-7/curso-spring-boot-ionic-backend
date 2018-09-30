package com.matus.cursomc.services;

import com.matus.cursomc.domain.Cliente;
import com.matus.cursomc.repositorys.ClienteRepository;
import com.matus.cursomc.services.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class AuthService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private EmailService emailService;

    private Random random = new Random();

    public void sendNewPassword(String email){
        Cliente cliente = clienteRepository.findByEmail(email);
        if(cliente == null){
            throw new ObjectNotFoundException("Email não encontrado");
        }
        String newPass = newPassword();
        cliente.setSenha(bCryptPasswordEncoder.encode(newPass));

        clienteRepository.save(cliente);
        emailService.sendNewPasswordEmail(cliente, newPass);
    }

    private String newPassword() {
        char[] vet = new char[10];
        for(int i = 0; i<10; i++){
            vet[i] = randomChar();
        }
        return  new String(vet);
    }

    private char randomChar() {
        //Gera numero inteiro de 0 ate 2
        int opt = random.nextInt(3);
        if(opt == 0){ //Gera um digito
            //(random.nextInt(10) ==> Gera um numero aleatorio de 0-9
            //48 ==> Codigo do 0 da tabela asciss
            return (char) (random.nextInt(10) + 48);
        }else if(opt == 1){ //Gera letra maiuscula
            return (char) (random.nextInt(26) + 65);
        }else { //Gera letra minuscula
            return (char) (random.nextInt(26) + 97);
        }
    }
}
