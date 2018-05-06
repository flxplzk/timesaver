package de.flxplzk.frontend.backend.domain;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class Report {

    @Id
    @GeneratedValue
    @Column(name = Report_.ID)
    private long id;

    @Column(name = Report_.REPORT_NAME)
    private String reportName;

    @Column(name = Report_.REPORT_START)
    private LocalDate reportStart;

    @Column(name = Report_.REPORT_END)
    private LocalDate reportEnd;

    @Lob
    @Column(name = Report_.FILE)
    private byte[] file;

    public byte[] getFile() {
        return file;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }

    @Column(name = Report_.CREATION_DATE)
    private LocalDateTime created;

    public Report() {    }

    public LocalDate getReportStart() {
        return reportStart;
    }

    public String getReportName() {
        return reportName;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
    }

    public void setReportStart(LocalDate reportStart) {
        this.reportStart = reportStart;
    }

    public LocalDate getReportEnd() {
        return reportEnd;
    }

    public void setReportEnd(LocalDate reportEnd) {
        this.reportEnd = reportEnd;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public interface Report_ extends ColumnDefinition {
        String REPORT_NAME = "REPORT_NAME";
        String REPORT_START = "REPORT_START";
        String REPORT_END = "REPORT_END";
        String FILE = "file";
    }
}
