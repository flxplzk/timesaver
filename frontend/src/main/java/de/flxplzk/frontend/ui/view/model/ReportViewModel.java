package de.flxplzk.frontend.ui.view.model;

import com.google.common.collect.Lists;
import com.vaadin.ui.UI;
import de.flxplzk.frontend.backend.domain.Report;
import de.flxplzk.frontend.backend.service.ReportService;
import de.flxplzk.vaadin.common.AsyncTask;
import de.flxplzk.vaadin.common.ConfirmationDialog;
import de.flxplzk.vaadin.common.NotificationManager;
import de.flxplzk.vaadin.mvvm.Property;

import java.time.LocalDate;
import java.util.List;

public class ReportViewModel {

    private static final Report DEFAULT_REPORT = new Report();
    private static final String EMPTY_STRING = "";

    private final Property<List<Report>> reports = new Property<>(Lists.newArrayList());
    private final Property<Report> selectedReport = new Property<>(DEFAULT_REPORT);
    private final Property<Boolean> reportSelected = new Property<>(Boolean.FALSE);
    private final Property<String> reportName = new Property<>(EMPTY_STRING);
    private final Property<LocalDate> reportingFrom = new Property<>(LocalDate.now());
    private final Property<LocalDate> reportingTo = new Property<>(LocalDate.now());
    private final Property<Boolean> showCrudPanel = new Property<>(Boolean.FALSE);

    private final ReportService reportService;
    private final UI currentUI;
    private final NotificationManager notificationManager;

    public ReportViewModel(ReportService reportService, UI currentUI, NotificationManager notificationManager) {
        this.reportService = reportService;
        this.currentUI = currentUI;
        this.notificationManager = notificationManager;
        new FetchExistingReportsTask().execute();
        this.selectedReport.addValueChangeListener(changeEvent ->
                this.reportSelected.setValue((changeEvent.getValue() != null)));
        this.reportService.addListener(changeEvent -> new FetchExistingReportsTask().execute());
    }

    public void deleteSelectedReport() {
        ConfirmationDialog dialog = new ConfirmationDialog();
        dialog.confirm("Soll der Bericht wirklich unwiederruflich gelöscht werden?", this.currentUI, this::handleDeleteConfirmation);
    }

    private void handleDeleteConfirmation(ConfirmationDialog.ConfirmationCallback.ResponseType responseType) {
        if (ConfirmationDialog.ConfirmationCallback.ResponseType.OK.equals(responseType))
            this.reportService.delete(this.selectedReport.getValue());
    }

    public void newReport() {
        this.showCrudPanel.setValue(Boolean.TRUE);
    }

    public void createReport() {
        new CreateReportTask().execute();
    }

    public void cancel() {
        this.resetCrudElements();
    }

    private void resetCrudElements() {
        this.showCrudPanel.setValue(Boolean.FALSE);
        this.reportName.setValue(EMPTY_STRING);
        this.reportingFrom.setValue(LocalDate.now());
        this.reportingTo.setValue(LocalDate.now());
    }

    class FetchExistingReportsTask extends AsyncTask<Void, List<Report>> {
        @Override
        protected List<Report> doInBackground(Void... params) {
            return reportService.findAll();
        }

        @Override
        protected void onPostExecute(List<Report> result) {
            reports.setValue(result);
        }
    }

    class CreateReportTask extends AsyncTask<Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            Report report = new Report();
            report.setReportName(reportName.getValue());
            report.setReportStart(reportingFrom.getValue());
            report.setReportEnd(reportingTo.getValue());
            reportService.save(report);
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            notificationManager.showNotification("Bericht wurde erfogreichgespeichert und kann nun runtergeladen werden", NotificationManager.NotificationStyle.SUCCESS);
            currentUI.access(ReportViewModel.this::resetCrudElements);
        }

        @Override
        protected void onError(RuntimeException cause) {
            notificationManager.showNotification("Fehler bei der Reporterstellung: " + cause.getMessage(), NotificationManager.NotificationStyle.ERROR);
        }
    }
}
