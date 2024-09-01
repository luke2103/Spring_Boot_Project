package net.javaguides.organizationservice.controller;

import lombok.AllArgsConstructor;
import net.javaguides.organizationservice.dto.OrganizationDto;
import net.javaguides.organizationservice.service.OrganizationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/organizations")
@AllArgsConstructor
public class OrganizationController {

    private OrganizationService organizationService;

    @PostMapping
    public ResponseEntity<OrganizationDto> saveOrganization(@RequestBody OrganizationDto organizationDto){
        OrganizationDto savedOrganization = organizationService.saveOrganization(organizationDto);

        return new ResponseEntity<>(savedOrganization , HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<OrganizationDto> getOrganization(@PathVariable("id") String organizationCode){
        OrganizationDto getOrganization = organizationService.getOrganizationByCode(organizationCode);

        return new ResponseEntity<>(getOrganization , HttpStatus.OK);
    }
}
