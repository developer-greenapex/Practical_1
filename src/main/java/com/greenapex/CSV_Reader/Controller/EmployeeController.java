package com.greenapex.CSV_Reader.Controller;

import com.greenapex.CSV_Reader.Model.EmployeeModel;
import com.greenapex.CSV_Reader.Service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping(value = "/upsertRecords" , produces = (MediaType.APPLICATION_JSON_VALUE))
    public Map<String, Integer> upsertRecords(@RequestParam("file") MultipartFile file) throws Exception {
        if (employeeService.hasCSVFormat(file)) {
            try {
                System.out.println("Uploaded the file successfully: " + file.getOriginalFilename());
                return employeeService.csvToDB(file);
            } catch (Exception e) {
                throw new Exception("Could not upload the file: ");
            }
        } else {
            throw new Exception("Please upload a csv file");
        }
    }
    @GetMapping("/fetchById")
    public ResponseEntity<Object> getEmpById(@RequestParam Long id) {
        EmployeeModel emp = employeeService.findById(id);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(emp);
    }
}
