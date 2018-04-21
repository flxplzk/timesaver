package de.flxplzk.vaadin.common;

import com.google.common.collect.Lists;
import com.vaadin.server.Sizeable;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

/**
 * CustomComponent that show a progress indicator, showContent is called with false.
 *
 * @author felix plazek
 */
public class AsyncGridComponent<T> extends HasValueComponent<Page<T>> {

    // ################################# UI COMPONENTS #################################

    private final Grid<T> mGrid = new Grid<>();
    private final ProgressBar mProgressBar = new ProgressBar();
    private final PagingComponent mPagingComponent = new PagingComponent();

    // ################################# UI STRUCTURE #################################

    private final HorizontalLayout mLayout = new HorizontalLayout(
            mGrid,
            mProgressBar
    );

    private final VerticalLayout rootLayout = new VerticalLayout(
            this.mLayout,
            this.mPagingComponent
    );

    public AsyncGridComponent() {
        super(new PageImpl<>(Lists.newArrayList(), new PageRequest(0, 1), 0));
        this.setCompositionRoot(this.rootLayout);
        this.setSizeFull();
        this.buildComponent();
    }

    public Grid<T> getGrid() {
        return this.mGrid;
    }

    private void initProgressBar() {
        this.mProgressBar.setIndeterminate(true);
        this.mProgressBar.setSizeFull();
    }

    private void initGrid() {
        this.mGrid.setWidth(100.0f, Sizeable.Unit.PERCENTAGE);
        this.mGrid.setHeight(100.0f, Sizeable.Unit.PERCENTAGE);
    }

    private void buildComponent() {
        initGrid();
        initProgressBar();
        this.rootLayout.setMargin(false);
        this.rootLayout.setSizeFull();
        this.mLayout.setSizeFull();
        this.mLayout.setComponentAlignment(this.mProgressBar, Alignment.TOP_CENTER);
        this.rootLayout.setExpandRatio(mLayout, 1.0f);
    }

    public void showContent(boolean showContent) {
        this.mProgressBar.setVisible(!showContent);
        this.mGrid.setVisible(showContent);
    }

    @Override
    protected void renderContent() {
        this.showContent(true);
        this.getGrid().setItems(this.valueProperty.getValue().getContent());
        this.mPagingComponent.renderContent();
    }

    private class PagingComponent extends CustomComponent {

        // ################################# UI COMPONENTS #################################

        private final Button firstPage = new Button("1");
        private final Label firstPageSeparator = new Label(" ... ");
        private final Button previousPage = new Button("2");
        private final Button currentPage = new Button("3");
        private final Button nextPage = new Button("4");
        private final Label lastPageSeparator = new Label(" ... ");
        private final Button lastPage = new Button("5");

        private final Button[] buttons = {
                this.firstPage,
                this.previousPage,
                this.currentPage,
                this.nextPage,
                this.lastPage
        };

        // ################################# UI STRUCTURE #################################

        private final HorizontalLayout pageComponent = new HorizontalLayout(
                this.firstPage,
                this.firstPageSeparator,
                this.previousPage,
                this.currentPage,
                this.nextPage,
                this.lastPageSeparator,
                this.lastPage
        );

        private final VerticalLayout rootLayout = new VerticalLayout(
                this.pageComponent
        );

        public PagingComponent() {
            this.setStyle();
        }

        private void setStyle() {
            this.setCompositionRoot(this.rootLayout);
            this.rootLayout.setComponentAlignment(pageComponent, Alignment.MIDDLE_CENTER);
            this.rootLayout.setMargin(false);
            this.rootLayout.setSizeFull();
            this.pageComponent.setMargin(false);
            for (Button button : this.buttons) {
                button.setStyleName(ValoTheme.BUTTON_BORDERLESS);
                button.addStyleName(ValoTheme.BUTTON_SMALL);
                button.addClickListener(this::buttonClicked);
            }
            this.currentPage.setStyleName(ValoTheme.BUTTON_SMALL);
            this.renderContent();
        }

        private void buttonClicked(Button.ClickEvent clickEvent) {
            int offset = Integer.valueOf(clickEvent.getButton().getCaption()) - 1;
            PageRequest pageRequest = new PageRequest(offset, 1);
            Page<T> page = new PageImpl<>(Lists.newArrayList(), pageRequest, 0);
            valueProperty.setValue(page);
            showContent(false);
        }

        public void renderContent() {
            this.renderFirstPage();
            this.renderPreviousPage();
            this.renderCurrentPage();
            this.renderNextPage();
            this.renderLastPage();
        }

        private void renderFirstPage() {
            if (valueProperty.getValue().isFirst()) {
                this.firstPage.setVisible(false);
            } else {
                this.firstPage.setVisible(true);
            }
        }

        private void renderPreviousPage() {
            int number = valueProperty.getValue().getNumber();
            if (number > 1) {
                this.previousPage.setVisible(true);
                this.previousPage.setCaption(String.valueOf(number));
            } else {
                this.previousPage.setVisible(false);
            }
        }

        private void renderCurrentPage() {
            int number = valueProperty.getValue().getNumber();
            this.currentPage.setCaption(String.valueOf(number + 1));
            firstPageSeparator.setVisible(number > 2);
            lastPageSeparator.setVisible(!(number + 3 >= valueProperty.getValue().getTotalPages()));
        }

        private void renderNextPage() {
            int number = valueProperty.getValue().getNumber();
            if (valueProperty.getValue().isLast() || valueProperty.getValue().getTotalPages() == number + 2) {
                this.nextPage.setVisible(false);
            } else {
                this.nextPage.setVisible(true);
                this.nextPage.setCaption(String.valueOf(number + 2));
            }
        }

        private void renderLastPage() {
            if (valueProperty.getValue().isLast()) {
                this.lastPage.setVisible(false);
            } else {
                int totalPages = valueProperty.getValue().getTotalPages();
                this.lastPage.setVisible(true);
                this.lastPage.setCaption(String.valueOf(totalPages));
            }
        }
    }
}