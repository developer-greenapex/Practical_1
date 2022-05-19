package com.greenapex.CSV_Reader.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "employees")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeModel {

    @Id
    private Long id;
    private String name;
    private String age;
    private String country;


    public EmployeeModel(long id, String name, String age, String country) {
    }
}
