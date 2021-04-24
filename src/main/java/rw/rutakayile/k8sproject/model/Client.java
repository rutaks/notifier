package rw.rutakayile.k8sproject.model;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "clients")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Client extends AuditModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String names;

    @OneToMany(mappedBy = "client", fetch = FetchType.LAZY)
    private Set<AccessCredential> accessCredentialSet;

}
