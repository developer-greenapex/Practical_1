package com.greenapex.CSV_Reader.ServiceImpl;

import com.greenapex.CSV_Reader.Config.CSVHelper;
import com.greenapex.CSV_Reader.Model.EmployeeModel;
import com.greenapex.CSV_Reader.Repository.EmployeeRepository;
import com.greenapex.CSV_Reader.Service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;


@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepo;

    @Override
    public EmployeeModel createEmp(EmployeeModel employeeModel) {
        employeeRepo.save(employeeModel);
        return employeeModel;
    }

    @Override
    public void save(MultipartFile file) {
        try {
//            List<EmployeeModel> tutorials = CSVHelper.csvToTutorials(file.getInputStream());
            List<EmployeeModel> tutorials = CSVHelper.csvToDB(file);
            employeeRepo.saveAll(tutorials);
        } catch (IOException e) {
            throw new RuntimeException("fail to store csv data: " + e.getMessage());
        }
    }

    @Override
    public List<EmployeeModel> getAllEmployees() {
        return employeeRepo.findAll();
    }

    @Override
    public ByteArrayInputStream load() {
        List<EmployeeModel> tutorials = employeeRepo.findAll();

        return CSVHelper.tutorialsToCSV(tutorials);
    }

}
