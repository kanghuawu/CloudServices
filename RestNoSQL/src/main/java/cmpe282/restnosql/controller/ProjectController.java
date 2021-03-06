package cmpe282.restnosql.controller;

import cmpe282.restnosql.domain.Project;
import cmpe282.restnosql.repository.ProjectRepository;
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
@RequestMapping("/cmpe282kanghuawu368/rest/project")
public class ProjectController {

    private static final Logger logger = LoggerFactory.getLogger(ProjectController.class);
    private ProjectRepository projectRepository;

    @Autowired
    public ProjectController(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public ResponseEntity<List<Project>> getAll() {
        logger.info("GET all projects");
        List<Project> projects = projectRepository.findAll();
        if (projects.size() == 0) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    @RequestMapping(path = "/", method = RequestMethod.POST)
    public ResponseEntity<Project> post(@RequestBody Project project, HttpServletRequest request) throws URISyntaxException {
        logger.info("POST request body: " + project);
        if (projectRepository.findById(project.getId()) != null)
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        projectRepository.save(project);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setLocation(new URI(request.getRequestURL().append(project.getId()).toString()));
        return new ResponseEntity<>(responseHeaders, HttpStatus.CREATED);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Project> get(@PathVariable(value = "id")  int id) {
        logger.info("GET id: " + id);
        Project project = projectRepository.findById(id);
        if (project == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(project, HttpStatus.OK);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Project> put(@PathVariable(value = "id")  int id, @RequestBody Project request) {
        logger.info("PUT id: " + id + " request body: " + request);
        Project project = projectRepository.findById(id);
        if (project == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        if (request.getName() != null)
            project.setName(request.getName());
        if (request.getBudget() != null)
            project.setBudget(request.getBudget());
        projectRepository.save(project);
        return new ResponseEntity<>(project, HttpStatus.OK);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> delete(@PathVariable(value = "id")  int id) {
        logger.info("DELETE id: " + id);
        Project project = projectRepository.findById(id);
        if (project == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        projectRepository.delete(project);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

