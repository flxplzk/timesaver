package de.flxplzk.frontend.ui.view.components;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.FileDownloader;
import com.vaadin.server.StreamResource;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import de.flxplzk.frontend.backend.domain.Report;
import de.flxplzk.vaadin.common.AbstractViewComponent;
import de.flxplzk.vaadin.common.AsyncGridComponent;
import de.flxplzk.vaadin.mvvm.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static de.flxplzk.frontend.backend.util.Printer.EXCEL_FILE_POSTFIX_PRINTER;

@SpringView(name = ReportViewComponent.VIEW_NAME)
@View(model = "reportViewModel")
public class ReportViewComponent extends AbstractViewComponent {

    public static final String VIEW_NAME = "Arbeitszeitlisten";

    private final Label title = new Label(VIEW_NAME);

    @ItemBound(to = "showCrudPanel")
    private final Property<Boolean> showCrudPanel = new Property<>(Boolean.FALSE);

    @ItemBound(to = "reportName")
    private final TextField reportName = new TextField("Name:");

    @ItemBound(to = "reportingFrom")
    private final DateField from = new DateField("Anfang Berichtszeitraum:");

    @ItemBound(to = "reportingTo")
    private final DateField to = new DateField("Ende Berichtszeitraum:");

    @OnClick(method = "createReport")
    private final Button save = new Button("Anlegen");

    @OnClick(method = "cancel")
    private final Button cancel = new Button("Abrechen");

    @OnClick(method = "newReport")
    private final Button newReportButton = new Button(VaadinIcons.PLUS_CIRCLE);

    @OnClick(method = "deleteSelectedReport")
    @EnableBound(to = "reportSelected")
    private final Button deleteReportButton = new Button(VaadinIcons.TRASH);

    @SelectionBound(to = "selectedReport")
    @ListingBound(to = "reports")
    private final Grid<Report> grid = new Grid<>();

    private final HorizontalLayout buttonPanel = new HorizontalLayout(
            this.newReportButton,
            this.deleteReportButton
    );

    private final HorizontalLayout headerPanel = new HorizontalLayout(
            this.title,
            this.buttonPanel
    );

    private final HorizontalLayout buttonPanelCrud = new HorizontalLayout(
            this.save,
            this.cancel
    );

    private final HorizontalLayout crudPanel = new HorizontalLayout(
            this.reportName,
            this.from,
            this.to,
            this.buttonPanelCrud
    );

    private final HorizontalLayout crudWrapper = new HorizontalLayout(
            this.crudPanel
    );

    private final VerticalLayout rootLayout = new VerticalLayout(
            this.headerPanel,
            this.crudWrapper,
            this.grid
    );


    public ReportViewComponent(ViewModelComposer viewModelComposer) {
        super(viewModelComposer);
        this.setCompositionRoot(this.rootLayout);
        this.init();
    }

    private void init() {
        this.buttonPanel.setMargin(false);
        this.buttonPanel.setSizeUndefined();

        this.headerPanel.setSizeFull();
        this.headerPanel.setMargin(false);
        this.headerPanel.setComponentAlignment(this.buttonPanel, Alignment.BOTTOM_RIGHT);

        this.rootLayout.setMargin(true);
        this.rootLayout.setSizeFull();

        this.crudPanel.setMargin(true);
        this.crudPanel.setSizeUndefined();
        this.crudPanel.setComponentAlignment(this.buttonPanelCrud, Alignment.BOTTOM_RIGHT);
        this.crudWrapper.addStyleName(ValoTheme.LAYOUT_CARD);

        this.crudWrapper.setMargin(false);
        this.crudWrapper.setSizeFull();
        this.crudWrapper.setComponentAlignment(this.crudPanel, Alignment.MIDDLE_CENTER);

        this.buttonPanelCrud.setSizeUndefined();

        this.grid.setSizeFull();

        this.title.addStyleName(ValoTheme.LABEL_H2);
        this.title.addStyleName(ValoTheme.LABEL_BOLD);

        this.deleteReportButton.addStyleName(ValoTheme.BUTTON_DANGER);
        this.newReportButton.addStyleName(ValoTheme.BUTTON_FRIENDLY);

        this.crudWrapper.setVisible(this.showCrudPanel.getValue());
        this.showCrudPanel.addValueChangeListener(event -> this.crudWrapper.setVisible(event.getValue()));

        this.grid.addColumn(Report::getId).setCaption("ID");
        this.grid.addColumn(Report::getReportName).setCaption("Name");
        this.grid.addColumn(report -> report.getReportStart().toString()).setCaption("Von");
        this.grid.addColumn(report -> report.getReportEnd().toString()).setCaption("Bis");
        this.grid.addColumn(report -> report.getCreated().toString()).setCaption("Erstellt am");
        this.grid.addComponentColumn(this::buildFileDownloader);
    }

    private <V extends Component> Button buildFileDownloader(Report report) {
        Button button = new Button(VaadinIcons.DOWNLOAD);
        button.addStyleName(ValoTheme.BUTTON_BORDERLESS);
        FileDownloader fileDownloader = new FileDownloader(new StreamResource((StreamResource.StreamSource) () -> new ByteArrayInputStream(report.getFile()), EXCEL_FILE_POSTFIX_PRINTER.print(report.getReportName())));
        fileDownloader.extend(button);
        return button;
    }
}
