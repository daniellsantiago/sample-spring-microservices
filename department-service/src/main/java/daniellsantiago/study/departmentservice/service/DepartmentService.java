package daniellsantiago.study.departmentservice.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import daniellsantaigo.study.commonclasses.model.Department;
import daniellsantaigo.study.commonclasses.repository.DepartmentRepository;
import daniellsantiago.study.departmentservice.client.EmployeeClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentService {
    private final DepartmentRepository departmentRepository;
    private final EmployeeClient employeeClient;


    public Department add(Department department) {
        return departmentRepository.add(department);
    }

    public Department findById(Long id) {
        return departmentRepository.findById(id);
    }

    public List<Department> findAll() {
        return departmentRepository.findAll();
    }

    @HystrixCommand(fallbackMethod = "findByOrganizationFallback")
    public List<Department> findByOrganization(Long organizationId) {
        return departmentRepository.findByOrganization(organizationId);
    }

    public List<Department> findByOrganizationFallback(Long organizationId) {
        return List.of(new Department(organizationId, "Teste"));
    }

    public List<Department> findByOrganizationWithEmployees(Long organizationId) {
        List<Department> departments = departmentRepository.findByOrganization(organizationId);
        departments.forEach(department -> department.setEmployees(employeeClient.findByDepartment(department.getId())));
        return departments;
    }
}
