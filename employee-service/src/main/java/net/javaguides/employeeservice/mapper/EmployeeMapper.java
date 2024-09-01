package net.javaguides.employeeservice.mapper;

import net.javaguides.employeeservice.dto.EmployeeDto;
import net.javaguides.employeeservice.entity.Employee;

import java.util.Locale;

public class EmployeeMapper {

    public static EmployeeDto mapToEmployeeDto(Employee employee) {

        return new EmployeeDto(
                employee.getId(),
                employee.getFirstName(),
                employee.getLastName(),
                employee.getEmail(),
                employee.getDepartmentCode(),
                employee.getOrganizationCode()
        );

    }

    //Convert userDto into UserJpa entity
    public static Employee mapToEmployee(EmployeeDto employeeDto) {

        return new Employee(
                employeeDto.getId(),
                employeeDto.getFirstName(),
                employeeDto.getLastName(),
                employeeDto.getEmail(),
                employeeDto.getDepartmentCode(),
                employeeDto.getOrganizationCode()
        );
    }
}
