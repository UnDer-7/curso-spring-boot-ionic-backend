package com.matus.cursomc.services;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

@Service
public class S3Service {
    private Logger LOG = LoggerFactory.getLogger(S3Service.class);

    @Autowired
    private AmazonS3 s3client;

    @Value("${s3.bucket}")
    private String bucketName;

    /**
     * Método responsável por fazer o upload de arquivo para o S3
     * @param multipartFile
     * @return URI - Retorna o endereço
     */
    public URI uploadFile(MultipartFile multipartFile) {
        try {
            String fileName = multipartFile.getOriginalFilename();
            InputStream inputStream = multipartFile.getInputStream();
            //Tipo de arquivo, um image, txt, etc
            String contentType = multipartFile.getContentType();
            return uploadFile(inputStream, fileName, contentType);
        } catch (IOException e) {
            throw new RuntimeException("Erro de IO: " + e.getMessage());
        }
    }

    public URI uploadFile(InputStream inputStream, String fileName, String contentType) {
        try {
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentType(contentType);
            LOG.info("Iniciando upload");
            s3client.putObject(bucketName, fileName, inputStream, objectMetadata);
            LOG.info("Upload finalizado");
            return s3client.getUrl(bucketName, fileName).toURI();
        } catch (URISyntaxException e) {
            throw new RuntimeException("Erro ao converter URL para URI");
        }
    }
}
