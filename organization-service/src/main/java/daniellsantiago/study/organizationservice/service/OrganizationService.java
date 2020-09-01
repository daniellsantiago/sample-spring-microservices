package daniellsantiago.study.organizationservice.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import daniellsantaigo.study.commonclasses.model.Organization;
import daniellsantaigo.study.commonclasses.repository.OrganizationRepository;
import daniellsantiago.study.organizationservice.client.DepartmentClient;
import daniellsantiago.study.organizationservice.client.EmployeeClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrganizationService {
    private final OrganizationRepository organizationRepository;
    private final DepartmentClient departmentClient;
    private final EmployeeClient employeeClient;

    public Organization add(Organization organization) {
        return organizationRepository.add(organization);
    }

    public Organization findById(Long id) {
        return organizationRepository.findById(id);
    }

    public List<Organization> findAll() {
        return organizationRepository.findAll();
    }

    public Organization findByIdWithDepartments(Long id) {
        Organization organization = organizationRepository.findById(id);
        organization.setDepartments(departmentClient.findByOrganization(organization.getId()));
        return organization;
    }

    public Organization findByIdWithDepartmentsAndEmployees(Long id) {
        Organization organization = organizationRepository.findById(id);
        organization.setDepartments(departmentClient.findByOrganizationWithEmployees(organization.getId()));
        return organization;
    }

    @HystrixCommand(fallbackMethod = "findByIdWithEmployeesFallback")
    public Organization findByIdWithEmployees(Long id) {
        Organization organization = organizationRepository.findById(id);
        organization.setEmployees(employeeClient.findByOrganization(organization.getId()));
        return organization;
    }

    public Organization findByIdWithEmployeesFallback(Long id) {
        return organizationRepository.findById(id);
    }
}
