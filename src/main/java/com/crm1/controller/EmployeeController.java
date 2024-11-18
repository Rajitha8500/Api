package com.crm1.controller;


import com.crm1.entity.Employee;
import com.crm1.payLoad.EmployeeDto;
import com.crm1.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/employee")
public class EmployeeController {

    private EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    // http://localhost:8080/api/v1/employee/add

    // creating a new record....
    @PostMapping("/add")
    public ResponseEntity<?> addEmployee(
           @Valid @RequestBody EmployeeDto dto,
            BindingResult result

    ){
           if(result.hasErrors()){
                 return new ResponseEntity<>(result.getFieldError().getDefaultMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
           }
        EmployeeDto employeeDto = employeeService.addEmployee(dto);
        return new ResponseEntity<>(employeeDto, HttpStatus.CREATED);
    }

//  http://localhost:8080/api/v1/employee?id=1
    // deleting the exsited record in database

    @DeleteMapping
    public ResponseEntity<String> deleteEmployee(
            @RequestParam Long id
            ){
        employeeService.deleteEmployee(id);
        return new ResponseEntity<>("deleted", HttpStatus.OK);
    }

  //  http://localhost:8080/api/v1/employee?id=1
    //updating the new record ....

    @PutMapping
    public ResponseEntity<EmployeeDto> UpdateEmployee(
            @RequestParam Long id,
            @RequestBody EmployeeDto dto
    ){
         EmployeeDto employeeDto = employeeService.updateEmployee(id,dto);
         return new ResponseEntity<>(employeeDto, HttpStatus.OK);
    }


    // http://localhost:8080/api/v1/employee?pageNo=0&pageSize=3&sortBy=email&soryDir=asc
    @GetMapping
    public ResponseEntity<List<EmployeeDto>> getEmployees(
            @RequestParam(name = "PageSize", required = false,defaultValue = "5") int pageSize,
            @RequestParam(name = "pageNo", required = false, defaultValue = "0") int pageNo,
           @RequestParam(name = "sortBy", required =false ,defaultValue = "name") String sortBy,
          @RequestParam(name = "sortDir",required = false,defaultValue = "asc") String sortDir

    ){
       List<EmployeeDto> employeesDto =  employeeService.getEmployee( pageNo,pageSize,sortBy,sortDir);
        return new ResponseEntity<>(employeesDto,HttpStatus.OK);
    }



   // http://localhost:8080/api/v1/employee/employeeId/1
    @GetMapping("/employeeId/{empId}")
    public ResponseEntity<EmployeeDto> getEmployeeById(
      @PathVariable long empId
    ){
         EmployeeDto dto = employeeService.getEmployeeById(empId);
        return new ResponseEntity<>(dto,HttpStatus.OK);
    }


}
