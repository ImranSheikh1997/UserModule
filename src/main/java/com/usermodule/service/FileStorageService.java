package com.usermodule.service;

import com.usermodule.config.property.FileStorageProperties;
import com.usermodule.utility.exception.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.util.StringUtils;


@Service
public class FileStorageService {


    private Path fileStorageLocation;

    @Autowired
    private FileStorageProperties fileStorageProperties;

    public void createDirectory() {
        this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir())
                .toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new CustomException("Could not create the directory where the uploaded files will be stored.", HttpStatus.BAD_GATEWAY);
        }
    }

    public  Resource loadFileAsResource(String fileName) {

        createDirectory();
        try{
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()){
                return resource;
            }
            else{
                throw new CustomException("File Not Found",HttpStatus.NOT_FOUND);
            }
        } catch (MalformedURLException e) {
            throw new CustomException("File Not Found",HttpStatus.NOT_FOUND);
        }
    }


    public String storeFile(MultipartFile file) {
        createDirectory();
        //Normalize Name of file
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        //to check if filename contains any invalid characters
        try {
            if (fileName.contains("..")) {
                throw new CustomException("FileName Contains invalid Characters" + fileName, HttpStatus.UNPROCESSABLE_ENTITY);
            }

            //copy file to the target location(Replacing filename with the same name)
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return fileName;
        }
        catch (IOException e){
            throw new CustomException("Could not Store File",HttpStatus.BAD_REQUEST);
        }
    }
}