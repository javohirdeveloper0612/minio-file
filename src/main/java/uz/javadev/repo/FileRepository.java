package uz.javadev.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.javadev.domain.FileEntity;

import java.util.UUID;

@Repository
public interface FileRepository extends JpaRepository<FileEntity, UUID> {

}
