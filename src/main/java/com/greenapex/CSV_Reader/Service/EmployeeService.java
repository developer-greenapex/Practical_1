package com.greenapex.CSV_Reader.Service;

import com.greenapex.CSV_Reader.Model.EmployeeModel;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface EmployeeService {

    EmployeeModel createEmp(EmployeeModel employeeModel);

    EmployeeModel findById(Long id);

    boolean hasCSVFormat(MultipartFile file);

    Map<String, Integer> csvToDB(MultipartFile file);
}
