package net.javaguides.employeeservice.service.impl;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.AllArgsConstructor;
import net.javaguides.employeeservice.dto.APIResponseDto;
import net.javaguides.employeeservice.dto.DepartmentDto;
import net.javaguides.employeeservice.dto.EmployeeDto;
import net.javaguides.employeeservice.dto.OrganizationDto;
import net.javaguides.employeeservice.entity.Employee;
import net.javaguides.employeeservice.mapper.EmployeeMapper;
import net.javaguides.employeeservice.repository.EmployeeRepository;
import net.javaguides.employeeservice.service.APIClient;
import net.javaguides.employeeservice.service.EmployeeService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private EmployeeRepository employeeRepository;
//    private RestTemplate restTemplate;

    private WebClient webClient;

//    private APIClient apiClient;

    @Override
    public EmployeeDto saveEmployee(EmployeeDto employeeDto) {
        Employee employee = EmployeeMapper.mapToEmployee(employeeDto);


        Employee savedEmployee = employeeRepository.save(employee);


        return EmployeeMapper.mapToEmployeeDto(savedEmployee);
    }



//    @CircuitBreaker(name = "${spring.application.name}",fallbackMethod = "getDefaultDepartment")
    @Retry(name = "${spring.application.name}" , fallbackMethod = "getDefaultDepartment")
    @Override
    public APIResponseDto getEmployee(Long id) {
        Employee employee = employeeRepository.findById(id).orElseThrow(
                ()-> new RuntimeException("id does not exists"));

/*        ResponseEntity<DepartmentDto> responseEntity =  restTemplate.getForEntity("http://localhost:8080/api/departments/" +
          employee.getDepartmentCode() , DepartmentDto.class);
        DepartmentDto departmentDto =responseEntity.getBody();*/

        DepartmentDto departmentDto = webClient.get().uri("http://localhost:8080/api/departments/" +employee.getDepartmentCode()).
                retrieve().
                bodyToMono(DepartmentDto.class).
                block();

        OrganizationDto organizationDto = webClient.get().uri("http://localhost:8083/api/organizations/" +employee.getOrganizationCode()).
                retrieve().
                bodyToMono(OrganizationDto.class).
                block();


//        DepartmentDto departmentDto = apiClient.getDepartment(employee.getDepartmentCode());
        
        EmployeeDto employeeDto = EmployeeMapper.mapToEmployeeDto(employee);
        APIResponseDto apiResponseDto = new APIResponseDto();

        apiResponseDto.setEmployee(employeeDto);;
        apiResponseDto.setDepartment(departmentDto);
        apiResponseDto.setOrganization(organizationDto);


        return apiResponseDto;
    }

    public APIResponseDto getDefaultDepartment(Long id,Exception exception) {
        Employee employee = employeeRepository.findById(id).orElseThrow(
                ()-> new RuntimeException("id does not exists"));

        DepartmentDto departmentDto = new DepartmentDto();
        departmentDto.setDepartmentName("R&D department");
        departmentDto.setDepartmentCode("RD001");
        departmentDto.setDepartmentDescription("Research & development Department");

        EmployeeDto employeeDto = EmployeeMapper.mapToEmployeeDto(employee);
        APIResponseDto apiResponseDto = new APIResponseDto();

        apiResponseDto.setEmployee(employeeDto);;
        apiResponseDto.setDepartment(departmentDto);


        return apiResponseDto;


    }
}
