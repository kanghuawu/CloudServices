package cmpe282.restnosql.controller;

import cmpe282.restnosql.domain.Employee;
import cmpe282.restnosql.repository.EmployeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/cmpe282kanghuawu368/rest/employee")
public class EmployeeController {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);
    public EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeController(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity<List<Employee>> getAll() {
        logger.info("GET all employees");
        List<Employee> emps = employeeRepository.findAll();
        if (emps.size() == 0)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(emps, HttpStatus.OK);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity<Employee> post(@RequestBody Employee employee, HttpServletRequest request) throws URISyntaxException {
        logger.info("POST request body: " + employee);
        if (employeeRepository.findById(employee.getId()) != null)
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        employeeRepository.save(employee);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setLocation(new URI(request.getRequestURL().append(employee.getId()).toString()));
        return new ResponseEntity<>(responseHeaders, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Employee> get(@PathVariable(value="id")  int id) {
        logger.info("GET id: " + id);
        Employee emp = employeeRepository.findById(id);
        if (emp == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(emp, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Employee> update(@PathVariable(value="id")  int id, @RequestBody Employee req) {
        logger.info("PUT id: " + id + " request body: " + req);
        Employee emp = employeeRepository.findById(id);
        if (emp == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        if (req.getFirstName() != null)
            emp.setFirstName(req.getFirstName());
        if (req.getLastName() != null)
            emp.setLastName(req.getLastName());
        employeeRepository.save(emp);
        return new ResponseEntity<>(emp, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> delete(@PathVariable(value="id")  int id) {
        logger.info("DELETE id: " + id);
        Employee emp  = employeeRepository.findById(id);
        if (emp == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        employeeRepository.delete(emp);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
