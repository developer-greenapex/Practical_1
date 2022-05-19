package com.greenapex.CSV_Reader.ServiceImpl;

import com.greenapex.CSV_Reader.Exception.NotFoundException;
import com.greenapex.CSV_Reader.Model.EmployeeModel;
import com.greenapex.CSV_Reader.Repository.EmployeeRepository;
import com.greenapex.CSV_Reader.Service.EmployeeService;
import com.univocity.parsers.common.record.Record;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;


@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepo;

    Integer skipLineCounter = 0;
    Integer processedLineCounter = 0;

    @Override
    public EmployeeModel createEmp(EmployeeModel employeeModel) {
        employeeRepo.save(employeeModel);
        return employeeModel;
    }

    @Override
    public EmployeeModel findById(Long id) {
        return employeeRepo.findById(id).orElseThrow(() -> new NotFoundException("Employee Not Found"));
    }

    @Override
    public boolean hasCSVFormat(MultipartFile file) {
        String TYPE = "text/csv";
        return TYPE.equals(file.getContentType())
                || Objects.equals(file.getContentType(), "application/vnd.ms-excel");
    }


    @Override
    public Map<String, Integer> csvToDB(MultipartFile file) {
        Map<String, Integer> result = new HashMap<>();
        try {
            String[] HEADERs = {"Id", "Name", "Age", "Country"};
            List<EmployeeModel> employeeModelList = new ArrayList<>();
            InputStream inputStream = file.getInputStream();
            CsvParserSettings settings = new CsvParserSettings();
            settings.setHeaderExtractionEnabled(true);
            CsvParser parser = new CsvParser(settings);

            List<Record> recordList = parser.parseAllRecords(inputStream);
            recordList.forEach(record -> {
                EmployeeModel employeeModel = new EmployeeModel();
                employeeModel.setId(Long.parseLong(record.getString("Id")));
                employeeModel.setName(record.getString("Name"));
                employeeModel.setAge(String.valueOf(record.getString("Age")));
                employeeModel.setCountry(record.getString("Country"));
                if (record.getString("Age") == null || record.getString("Name") == null || record.getString("Country") == null) {
                    skipLineCounter++;
                } else {
                    processedLineCounter++;
                    createEmp(employeeModel);
                }

            });

        } catch (IOException e) {
            System.out.println("Error while reading csv");
        }
        result.put("skippedLines", skipLineCounter);
        result.put("processedLines", processedLineCounter);
        return result;


    }
}

