package com.matus.cursomc.services;

import com.matus.cursomc.security.UserSS;
import org.springframework.security.core.context.SecurityContextHolder;

public class UserService {

    public static UserSS authnticated(){
        //Retorna usuario que estiver logado no sistema
        try{
            return (UserSS) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        }catch (Exception e){
            return null;
        }
    }
}
