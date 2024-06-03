package com.documentupload.Controller;
import java.util.List;
import java.util.stream.Collectors;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import com.documentupload.Model.Document;
import com.documentupload.Model.ResponseMessage;
import com.documentupload.Service.DocumentStorageService;

@Controller
@CrossOrigin("http://localhost:4200")

public class DocumentController {
    @Autowired
    DocumentStorageService documentStorageService;

    @PostMapping("/document/upload")
    public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file) {
        String message = "";
        try {
            documentStorageService.save(file);

            message = "File: " +  file.getOriginalFilename() + " uploaded correctly";
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        } catch (Exception e) {
            message = "Could not upload the file: " + file.getOriginalFilename() + ". Error: " + e.getMessage();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }

    @GetMapping("/document/documents")
    public ResponseEntity<List<Document>> getListFiles() {
        List<Document> documents = documentStorageService.loadAll().map(path -> {
            String fileName = path.getFileName().toString();
            String url = MvcUriComponentsBuilder
                    .fromMethodName(DocumentController.class, "getFile", path.getFileName().toString()).build().toString();

            return new Document(fileName, url);
        }).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(documents);
    }

    @GetMapping("/document/{fileName}")
    @ResponseBody
    public ResponseEntity<Resource> getFile(@PathVariable("fileName") String fileName) {
        Resource file = documentStorageService.load(fileName);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; fileName=\"" + file.getFilename() + "\"").body(file);
    }

    @DeleteMapping("/document/{fileName}")
    public ResponseEntity<ResponseMessage> deleteFile(@PathVariable("fileName") String fileName) {
        String message = "";

        try {
            boolean existed = documentStorageService.delete(fileName);

            if (existed) {
                message = "File: " + fileName + " deleted";
                return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
            }

            message = "File: " + fileName + " does not exist!";
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseMessage(message));
        } catch (Exception e) {
            message = "Could not delete file: " + fileName + ". Error: " + e.getMessage();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseMessage(message));
        }
    }

    @PutMapping("/document/{fileName}")
    public ResponseEntity<ResponseMessage> updateFile(@PathVariable("fileName") String fileName) {
       String message= "File: " + fileName + " successfully updated";
        return ResponseEntity.accepted().body(new ResponseMessage(message));
    }

}

