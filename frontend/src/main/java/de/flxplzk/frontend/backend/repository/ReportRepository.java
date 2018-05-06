package de.flxplzk.frontend.backend.repository;

import de.flxplzk.frontend.backend.domain.Report;
import org.springframework.data.repository.CrudRepository;

public interface ReportRepository extends CrudRepository<Report, Long> {
}