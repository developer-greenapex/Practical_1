package com.greenapex.CSV_Reader.Service;

import com.greenapex.CSV_Reader.Model.EmployeeModel;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

public interface EmployeeService {

    EmployeeModel createEmp(EmployeeModel employeeModel);

    void save(MultipartFile file);

    List<EmployeeModel> getAllEmployees();

    public ByteArrayInputStream load();
}
