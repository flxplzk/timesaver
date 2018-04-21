package de.flxplzk.frontend.backend.repository;

import de.flxplzk.frontend.backend.domain.EmployeeProfile;
import org.springframework.data.repository.CrudRepository;

public interface EmployeeProfileRepository extends CrudRepository<EmployeeProfile, Long> {
}
