package com.greenapex.CSV_Reader.Config;

import com.greenapex.CSV_Reader.Model.EmployeeModel;
import com.univocity.parsers.common.record.Record;
import com.univocity.parsers.csv.CsvFormat;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;
import org.apache.commons.csv.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.*;

public class CSVHelper {

    public static String TYPE ="text/csv";
    static String[] HEADERs = {"Id","Name","Age","Country"};
    static int count;

    public static boolean hasCSVFormat(MultipartFile file) {
        return TYPE.equals(file.getContentType())
                || Objects.equals(file.getContentType(), "application/vnd.ms-excel");
    }

    public static List<EmployeeModel> csvToDB(MultipartFile file) throws IOException {
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
            if(record.getString("Age") == null || record.getString("Name") == null || record.getString("Country") == null) {
                employeeModelList.add(employeeModel);
                employeeModelList.remove(employeeModel);
                count++;
            } else {
                employeeModelList.add(employeeModel);
            }

        });
        return employeeModelList;
    }

//    public static List<EmployeeModel> csvToTutorials(InputStream is) {
//        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
//             CSVParser csvParser = new CSVParser(fileReader,
//                     CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());
//             ) {
//
//            List<EmployeeModel> employeeModelList = new ArrayList<>();
//
//            Iterable<CSVRecord> csvRecords = csvParser.getRecords();
//
//
//            for (CSVRecord csvRecord : csvRecords) {
////                EmployeeModel employeeModel = new EmployeeModel(
////                        Long.parseLong(csvRecord.get("Id")),
////                        csvRecord.get("Name"),
////                        String.valueOf(csvRecord.get("Age")),
////                        csvRecord.get("Country")
////                );
//                EmployeeModel employeeModel = new EmployeeModel();
//                employeeModel.setId(Long.parseLong(csvRecord.get("Id")));
//                employeeModel.setName(csvRecord.get("Name"));
//                employeeModel.setAge(String.valueOf(csvRecord.get("Age")));
//                employeeModel.setCountry(csvRecord.get("Country"));
//
//                if(csvRecord.get("Name") == null) {
//                    employeeModelList.add(employeeModel);
//                    employeeModelList.remove(employeeModel);
//                } else if(String.valueOf(csvRecord.get("Age")) == null) {
//                    employeeModelList.add(employeeModel);
//                    employeeModelList.remove(employeeModel);
//                } else if (csvRecord.get("Country") == null) {
//                    employeeModelList.add(employeeModel);
//                    employeeModelList.remove(employeeModel);
//                }
//                else {
//                    employeeModelList.add(employeeModel);
//                }
//
//            }
//
//            return employeeModelList;
//        } catch (IOException e) {
//            throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
//        }
//    }

    public static ByteArrayInputStream tutorialsToCSV(List<EmployeeModel> employeeModelList) {
        final CSVFormat format = CSVFormat.DEFAULT.withQuoteMode(QuoteMode.MINIMAL);

        try (ByteArrayOutputStream out = new ByteArrayOutputStream();
             CSVPrinter csvPrinter = new CSVPrinter(new PrintWriter(out), format);) {
            for (EmployeeModel employeeModel : employeeModelList) {
                List<String> data = Arrays.asList(
                        String.valueOf(employeeModel.getId()),
                        employeeModel.getCountry(),
                        employeeModel.getName(),
                        String.valueOf(employeeModel.getAge())
                );

                csvPrinter.printRecord(data);
            }

            csvPrinter.flush();
            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException("fail to import data to CSV file: " + e.getMessage());
        }
    }

}
