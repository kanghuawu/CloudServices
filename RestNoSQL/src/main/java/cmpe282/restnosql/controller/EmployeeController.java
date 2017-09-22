package cmpe282.restnosql.controller;

import cmpe282.restnosql.domain.Employee;
import cmpe282.restnosql.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cmpe282Demo123/rest/employee")
public class EmployeeController {

    public EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeController(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public List<Employee> getAll() {
        return employeeRepository.findAll();
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public Employee create(@RequestBody Employee employee) {
        return employeeRepository.save(employee);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Employee findOne(@PathVariable(value="id")  String id) {
        return employeeRepository.findOne(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Employee update(@PathVariable(value="id")  String id, @RequestBody Employee body) {
        Employee employee = findOne(id);
        employee.setFirstName(body.getFirstName());
        employee.setLastName(body.getLastName());
        employeeRepository.save(employee);
        return employee;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable(value="id")  String id) {
        employeeRepository.delete(id);
    }
}
