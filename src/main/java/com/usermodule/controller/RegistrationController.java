package com.usermodule.controller;

import com.usermodule.dto.Userdto;
import com.usermodule.dto.RegistrationService;
import com.usermodule.dto.payload.UploadFileResponse;


import com.usermodule.model.user.Role;
import com.usermodule.service.*;
import com.usermodule.utility.validator.annotations.StateId;
import io.swagger.annotations.*;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import org.springframework.core.io.Resource;
import javax.mail.Multipart;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import org.springframework.util.StringUtils;

@RestController
//@Api(tags = "User")
@RequestMapping("/usermodule")
public class RegistrationController {

    @Autowired
    private RegistrationService registrationservice;

    @Autowired
    private UserService userService;

    @Autowired
    private CountryService countryService;

    @Autowired
    private CityService cityService;

    @Autowired
    private StateService stateService;

    @Autowired
    private FileStorageService fileStorageService;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    //This Api is for Registration Form
    @PostMapping("/registration")
    @ApiOperation(value = "${UserController.registration}")
    @ApiResponses(value={//
            @ApiResponse(code = 400, message = "Something went wrong"), //
            @ApiResponse(code = 403, message = "Access Denied"),//
            @ApiResponse(code = 422, message = "Username is already in use")})
    public ResponseEntity<Userdto> register(
            @ApiParam("Sign up user")
            @RequestBody Userdto userdto){

        registrationservice.register(userdto);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    //This Api is for confirming api and enabling user
    @ApiOperation(value = "${UserController.confirm.token}")
    @GetMapping(path ="/registration/confirm")
    public ResponseEntity<?> confirm(
            @RequestParam("token") String token){

        registrationservice.confirmToken(token);

        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    //This Api is for  Signing form
    @PostMapping(path="/signin")
    @ApiOperation(value = "${UserController.signin}")
    @ApiResponses(value = {
            @ApiResponse(code = 400,message = "Something Went Wrong"), //
            @ApiResponse(code = 422,message = "Invalid username/password supplied")
    }
    )
    public ResponseEntity<?> login(//
        @ApiParam("username") @RequestParam String email, //
        @ApiParam("password") @RequestParam String password) {
        List<Role> token = userService.signin(email,password);
        return new ResponseEntity<>(token,HttpStatus.ACCEPTED);
    }

    //This APi will Return list of Countries
    @GetMapping("/registration/country")
  //  @ApiOperation(value = "${UserController.country}")
    @ApiResponses(value ={//
            @ApiResponse(code=400, message = "Something Went wrong"),//
            @ApiResponse(code= 403, message = "Access Denied")})
    public ResponseEntity<?> findAllCountry(){
        return new ResponseEntity<>(countryService.findAllCountry(),HttpStatus.OK);
    }


    //This api will return list of City According to State
    @GetMapping(value= "registration/city")
    public ResponseEntity<?> findCities(
            @Valid
            @StateId
            @RequestParam Long stateId
    ){
        return new ResponseEntity<>(cityService.getCitiesByState(stateId),HttpStatus.OK);
    }



    //This api will return list of State According to State
    @GetMapping(value= "registration/state")
    public ResponseEntity<?> findStates(
            @Valid
            @StateId
            @RequestParam Long countryId
    ){
        return new ResponseEntity<>(stateService.getStatesByCountry(countryId),HttpStatus.OK);
    }
    //This Api will Delete User From DB Based on Email.
    @DeleteMapping(value="/{username}")
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ApiOperation(value = "${UserController.delete}", authorizations = { @Authorization(value="apiKey")})
    @ApiResponses(value ={//
            @ApiResponse(code=400, message = "Something Went wrong"),//
            @ApiResponse(code= 403, message = "Access Denied"),//
            @ApiResponse(code = 404, message = "The user doesn't exist"),//
            @ApiResponse(code=500, message = "Expired or invalid token")
    })
    public String delete(
            @ApiParam("email")
            @PathVariable String email){

        userService.delete(email);
        return email;
    }

    //This API is for Uploading A file.
    @PostMapping("registration/uploadFile")
    public UploadFileResponse uploadFile(
            @RequestParam("file")MultipartFile file
            )
    {
        String filename = fileStorageService.storeFile(file);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(filename)
                .toUriString();

        return new UploadFileResponse(filename, fileDownloadUri, file.getContentType(), file.getSize());
    }

    //This Api is for Downloading file
    @GetMapping("/downloadFile/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(
            @PathVariable String fileName,
            HttpServletRequest request){

        //Load File As Resource
        Resource resource = fileStorageService.loadFileAsResource(fileName);

        //Try to determine file's content type
        String contentType = null;

        try{
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        }
        catch (IOException e){
            logger.info("Could not determine file type.");
        }

        //fallback to default content type if type could not be determined
        if(contentType == null){
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+ resource.getFilename() + "\"")
                .body(resource);
    }

    @GetMapping(path = "")
    public HttpStatus check() {
        return HttpStatus.OK;
    }
}

