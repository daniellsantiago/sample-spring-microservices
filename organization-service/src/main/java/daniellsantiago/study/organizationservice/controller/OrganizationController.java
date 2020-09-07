package daniellsantiago.study.organizationservice.controller;

import daniellsantiago.study.commonclasses.model.Organization;
import daniellsantiago.study.organizationservice.service.OrganizationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/organization")
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class OrganizationController {

    private final OrganizationService organizationService;

    @PostMapping
    public Organization add(@RequestBody Organization organization) {
        log.info("Organization add: {}", organization);
        return organizationService.add(organization);
    }

    @GetMapping
    public List<Organization> findAll() {
        log.info("Organization find all");
        return organizationService.findAll();
    }

    @GetMapping("/{id}")
    public Organization findById(@PathVariable("id") Long id) {
        log.info("Organization find: id={}", id);
        return organizationService.findById(id);
    }

    @GetMapping("/{id}/with-departments")
    public Organization findByIdWithDepartments(@PathVariable("id") Long id) {
        log.info("Organization find: id={}", id);
        return organizationService.findByIdWithDepartments(id);
    }

    @GetMapping("/{id}/with-departments-and-employees")
    public Organization findByIdWithDepartmentsAndEmployees(@PathVariable("id") Long id) {
        log.info("Organization find: id={}", id);
        return organizationService.findByIdWithDepartmentsAndEmployees(id);
    }

    @GetMapping("/{id}/with-employees")
    public Organization findByIdWithEmployees(@PathVariable("id") Long id) {
        log.info("Organization find: id={}", id);
        return organizationService.findByIdWithEmployees(id);
    }

    @GetMapping("/fallback")
    public String fallback(){
        log.info("Fallback method");
        return "Imagine something useful being done to continue the application flow";
    }
}
