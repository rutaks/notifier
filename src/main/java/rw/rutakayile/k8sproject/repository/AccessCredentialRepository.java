package rw.rutakayile.k8sproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rw.rutakayile.k8sproject.model.AccessCredential;

import java.util.Optional;

@Repository
public interface AccessCredentialRepository extends JpaRepository<AccessCredential, Long> {
    AccessCredential findByApiKey(String apiKey);
}
