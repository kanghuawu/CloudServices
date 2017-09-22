package cmpe282.restnosql.repository;

import cmpe282.restnosql.domain.Project;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends MongoRepository<Project, String>{
    public Project findById(int id);
}
