package cmpe282.restnosql.repository;

import cmpe282.restnosql.domain.Project;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

public interface ProjectRepository extends MongoRepository<Project, String>{
    public Project findById(int id);
    public Long deleteById(int id);
}
