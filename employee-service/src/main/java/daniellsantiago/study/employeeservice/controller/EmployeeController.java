package daniellsantiago.study.employeeservice.controller;

import daniellsantiago.study.commonclasses.model.Employee;
import daniellsantiago.study.commonclasses.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.List;

@RestController
@RequestMapping("/employee")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class EmployeeController {

    private final EmployeeRepository repository;

    @PostMapping
    public Employee add(@RequestBody Employee employee) {
        log.info("Employee add: {}", employee);
        return repository.add(employee);
    }

    @GetMapping("/{id}")
    public Employee findById(@PathVariable("id") Long id) {
        log.info("Employee find by id={}", id);
        return repository.findById(id);
    }

    @GetMapping
    public List<Employee> findAll() {
        log.info("Employee find all");
        return repository.findAll();
    }

    @GetMapping("/department/{departmentId}")
    public List<Employee> findByDepartment(@PathVariable("departmentId") Long departmentId) {
        log.info("Employee find by departmentId={}", departmentId);
        return repository.findByDepartment(departmentId);
    }

    @GetMapping("/organization/{organizationId}")
    public List<Employee> findByOrganization(@PathVariable("organizationId") Long organizationId) {
        log.info("Employee find by organizationId={}", organizationId);
        return repository.findByOrganization(organizationId);
    }

    @GetMapping("/willfail")
    public void failRequest() throws InterruptedException {
        log.info("Calling a method that will fail");
        Thread.sleep(Duration.ofSeconds(3).toMillis());
    }

    @GetMapping("/fallback")
    public String fallback(){
        log.info("Fallback method");
        return "Imagine something useful being done to continue the application flow";
    }
}
