package university.vaadin.ui.common.page;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import university.vaadin.backend.dto.student.StudentResponse;
import university.vaadin.ui.common.view.DetailsView;
import university.vaadin.ui.common.presenter.DetailsPresenter;

import java.util.List;

public abstract class DetailsPage<E> extends BasePage implements DetailsView<E>, HasUrlParameter<Long> {

    protected final DetailsPresenter<E> presenter;
    protected final H2 title = new H2();
    protected final Span info = new Span();
    protected final Grid<StudentResponse> grid =  new Grid<>(StudentResponse.class, false);

    protected DetailsPage(DetailsPresenter<E> presenter, String backText){
        this.presenter = presenter;

        Button back = new Button(backText, e -> presenter.onBackClicked());
        back.addClickShortcut(Key.ESCAPE);
        back.addThemeVariants(ButtonVariant.LUMO_WARNING);

        grid.addColumn(StudentResponse::firstName).setHeader("First Name").setSortable(true);
        grid.addColumn(StudentResponse::lastName).setHeader("Last Name").setSortable(true);
        grid.addColumn(StudentResponse::email).setHeader("Email");

        HorizontalLayout header = new HorizontalLayout();
        header.setWidthFull();

        header.add(title);
        header.addAndExpand(new Div()); // spacer
        header.add(back);


        add(header, info, new H3("Students:"), grid);

        presenter.init(this);
    }

    @Override
    public void setParameter(BeforeEvent beforeEvent, Long aLong) {
        presenter.load(aLong);
    }

    @Override
    public void showStudents(List<StudentResponse> students) {
        grid.setItems(students);
    }
}
