package net.javaguides.organizationservice.service;

import net.javaguides.organizationservice.dto.OrganizationDto;

public interface OrganizationService {

    public OrganizationDto saveOrganization(OrganizationDto organizationDto);

    public OrganizationDto getOrganizationByCode(String organizationCode);
}
