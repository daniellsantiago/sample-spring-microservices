package daniellsantiago.study.departmentservice.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import daniellsantaigo.study.commonclasses.model.Department;
import daniellsantaigo.study.commonclasses.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentService {
    private final DepartmentRepository departmentRepository;

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
        return List.of(new Department(1L, "Teste"));
    }
}
