package com.greenapex.CSV_Reader.Repository;

import com.greenapex.CSV_Reader.Model.EmployeeModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeModel, Long> {

}
