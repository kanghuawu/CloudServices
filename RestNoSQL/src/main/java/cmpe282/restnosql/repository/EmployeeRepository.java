package cmpe282.restnosql.repository;

import cmpe282.restnosql.domain.Employee;
import org.springframework.stereotype.Repository;
import org.springframework.data.mongodb.repository.MongoRepository;

@Repository
public interface EmployeeRepository extends MongoRepository<Employee, String> {
}
