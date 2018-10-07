package com.matus.cursomc.services;

import com.matus.cursomc.services.exception.FileException;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

@Service
public class ImageService {

    /**
     * Método que faz a leitura da imagen e verfica se a mesma é JPG
     * @param uploadedFile
     * @return Retorna a imgagen no formato JPG
     */
    public BufferedImage getJpgImageFromFile(MultipartFile uploadedFile) {
        //Pega extensao do arquivo
        String ext = FilenameUtils.getExtension(uploadedFile.getOriginalFilename());

        if (!"png".equals(ext) && !"jpg".equals(ext)) {
            throw new FileException("Somente imagens PNG JPG são permitidas!");
        }

        try {
            BufferedImage img = ImageIO.read(uploadedFile.getInputStream());
            if ("png".equals(ext)) {
                img = pngToJpg(img);
            }
            return img;
        } catch (IOException e) {
            throw new FileException("Erro ao ler arquivo");
        }
    }

    /**
     * Método que faz a converção de Png para Jpg
     * @param img - BufferedImage
     * @return returna um BufferedImage em Jpg
     */
    public BufferedImage pngToJpg(BufferedImage img) {
        BufferedImage jpgImage = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
        jpgImage.createGraphics().drawImage(img, 0, 0, Color.WHITE, null);
        return jpgImage;
    }

    public InputStream getInputStream(BufferedImage img, String extension){
        try{
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ImageIO.write(img, extension, os);
            return new ByteArrayInputStream(os.toByteArray());
        }catch (IOException e){
            throw new FileException("Erro ao ler arquivo");
        }
    }
}
