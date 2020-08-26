package daniellsantiago.study.organizationservice.controller;

import daniellsantaigo.study.commonclasses.model.Organization;
import daniellsantaigo.study.commonclasses.repository.OrganizationRepository;
import daniellsantiago.study.organizationservice.client.DepartmentClient;
import daniellsantiago.study.organizationservice.client.EmployeeClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class OrganizationController {

    private final OrganizationRepository repository;

    private final DepartmentClient departmentClient;

    private final EmployeeClient employeeClient;

    @PostMapping
    public Organization add(@RequestBody Organization organization) {
        log.info("Organization add: {}", organization);
        return repository.add(organization);
    }

    @GetMapping
    public List<Organization> findAll() {
        log.info("Organization find all");
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public Organization findById(@PathVariable("id") Long id) {
        log.info("Organization find: id={}", id);
        return repository.findById(id);
    }

    @GetMapping("/{id}/with-departments")
    public Organization findByIdWithDepartments(@PathVariable("id") Long id) {
        log.info("Organization find: id={}", id);
        Organization organization = repository.findById(id);
        organization.setDepartments(departmentClient.findByOrganization(organization.getId()));
        return organization;
    }

    @GetMapping("/{id}/with-departments-and-employees")
    public Organization findByIdWithDepartmentsAndEmployees(@PathVariable("id") Long id) {
        log.info("Organization find: id={}", id);
        Organization organization = repository.findById(id);
        organization.setDepartments(departmentClient.findByOrganizationWithEmployees(organization.getId()));
        return organization;
    }

    @GetMapping("/{id}/with-employees")
    public Organization findByIdWithEmployees(@PathVariable("id") Long id) {
        log.info("Organization find: id={}", id);
        Organization organization = repository.findById(id);
        organization.setEmployees(employeeClient.findByOrganization(organization.getId()));
        return organization;
    }
}
