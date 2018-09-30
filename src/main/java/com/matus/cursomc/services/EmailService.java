package com.matus.cursomc.services;

import com.matus.cursomc.domain.Cliente;
import com.matus.cursomc.domain.Pedido;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;

@Service
public interface EmailService {

    //-----------------Envia como texto normal-----------------
    void sendOrderConfirmationEmail(Pedido pedido);

    void sendEmail(SimpleMailMessage smg);

    //-----------------Envia como HTML-----------------
    void sendOrderConfirmationHtmlEmail(Pedido obj);

    void sendHtmlEmail(MimeMessage msg);

    void sendNewPasswordEmail(Cliente cliente, String newPass);
}
