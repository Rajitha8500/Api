package com.crm1.service;

import com.crm1.entity.Employee;
import com.crm1.exception.ResourceNotFound;
import com.crm1.payLoad.EmployeeDto;
import com.crm1.repository.EmployeeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    private EmployeeRepository employeeRepository;

    private ModelMapper modelMapper;


    public EmployeeService(EmployeeRepository employeeRepository, ModelMapper modelMapper) {
        this.employeeRepository = employeeRepository;
        this.modelMapper = modelMapper;
    }


    public EmployeeDto addEmployee(EmployeeDto dto) {
        Employee employee = mapToEntity(dto);
        Employee emp = employeeRepository.save(employee);
        EmployeeDto employeeDto = mapToDto(emp);
       // employeeDto.setDate(new Date());
        return employeeDto;

    }

    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }

    public EmployeeDto updateEmployee(Long id, EmployeeDto dto) {
         Employee employee = mapToEntity(dto);
         employee.setId(id);
       Employee UpdateEmployee =  employeeRepository.save(employee);
      EmployeeDto employeeDto = mapToDto(UpdateEmployee);
       // employeeDto.setDate(new Date());
      return employeeDto;
    }

    public List<EmployeeDto> getEmployee(int pageNo, int pageSize,String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable page =  PageRequest.of(pageNo,pageSize, sort);
        Page<Employee> all = employeeRepository.findAll(page);
        List<Employee> employees = all.getContent();
        List<EmployeeDto> dto = employees.stream().map(e -> mapToDto(e)).collect(Collectors.toList());
        return dto;

    }

     EmployeeDto mapToDto(Employee employee){ // take the data from entity and  convert it into a Dto object

         EmployeeDto dto = modelMapper.map(employee, EmployeeDto.class);
//
        return dto;

    }

    // convert dto to entity   suplie dto object
    Employee  mapToEntity(EmployeeDto dto){

          Employee emp =  modelMapper.map(dto, Employee.class);
        return emp;


    }


    public EmployeeDto getEmployeeById(long empId) {

       Employee employee =  employeeRepository.findById(empId).orElseThrow(
               ()->new ResourceNotFound("Record not found with id: "+empId)

        );
        EmployeeDto dto = mapToDto(employee);
        return dto;

    }


}
