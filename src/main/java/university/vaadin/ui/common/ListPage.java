package university.vaadin.ui.common;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasValidation;
import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

public abstract class ListPage<E,R> extends VerticalLayout implements ListView<E> {

    private final String title;
    private final String entityName;
    private final ListPresenter<E,R> presenter;
    private final Function<E,Long> mapper;
    private final Consumer<Long> detailes;

    protected final Grid<E> grid;
    private final Map<String, HasValidation> fields = new LinkedHashMap<>();
    private Dialog createDialog;

    protected ListPage(String title,
                       String entityName,
                       Class<E> entityClass,
                       ListPresenter<E,R> presenter,
                       Function<E, Long> mapper,
                       Consumer<Long> detailes) {
        this.title = title;
        this.entityName = entityName;
        this.presenter = presenter;
        this.mapper = mapper;
        this.detailes = detailes;
        this.grid = new Grid<>(entityClass,false);
    }

    protected abstract void configureColumns(Grid<E> grid);
    protected abstract Component createForm();
    protected abstract R buildRequest();

    protected void build(){
        setSizeFull();

        Button addButton = new Button("Add "+entityName, event -> openCreateDialog());
        addButton.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
        HorizontalLayout header = new HorizontalLayout();
        header.setWidthFull();

        header.add(new H2(title));
        header.addAndExpand(new Div()); // spacer
        header.add(addButton);

        configureColumns(grid);

        grid.addComponentColumn(s -> new Button("Details", e -> presenter.onDetailsClicked(mapper.apply(s))));
        grid.addComponentColumn(s -> {
            Button delete = new Button("Delete", e-> confirmDelete(mapper.apply(s)));
            delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
            return delete;
        });
        grid.setSizeFull();
        add(header, grid);
    }

    protected void registerField(String fieldName, HasValidation validation) {
        fields.put(fieldName, validation);
    }

    private void openCreateDialog() {
        for(HasValidation field : fields.values()){
            if(field instanceof HasValue<?, ?> value){
                value.clear();
            }
        }
        clearFieldError();
        createDialog = new Dialog();
        createDialog.setHeaderTitle("Create "+ entityName);
        createDialog.add(createForm());
        Button save =  new Button("Save", event -> presenter.onCreateClicked(buildRequest()));
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        Button cancel = new Button("Cancel", event -> createDialog.close());

        createDialog.getFooter().add(cancel, save);
        createDialog.open();
    }

    private void confirmDelete(Long id){

         ConfirmDialog dialog = new ConfirmDialog();
         dialog.setHeader("Delete " +  entityName);

        dialog.setText("Are you sure you want to delete this "+ entityName.toLowerCase() + " ?");
        dialog.setCancelable(true);
        dialog.setCancelText("Close");

        dialog.setConfirmButton("Delete", e -> presenter.onDeleteClicked(id));
        dialog.setConfirmButtonTheme("error primary");
        dialog.open();
    }


    @Override
    public void show(List<E> fields) {
        grid.setItems(fields);
    }

    @Override
    public void showSuccess(String message) {
        Notifications.success(message);
    }

    @Override
    public void showError(String message) {
        Notifications.error(message);
    }

    @Override
    public void navigateToDetails(Long id) {
        detailes.accept(id);
    }

    @Override
    public void showFieldError(String field, String message) {
        HasValidation validation = fields.get(field);

        if(validation == null){
            showError(message);
            return;
        }

        validation.setErrorMessage(message);
        validation.setInvalid(true);

    }

    @Override
    public void clearFieldError() {
        fields.values().forEach(field -> field.setInvalid(false));

    }

    @Override
    public void closeCreatedDialog() {
        if(createDialog != null ){
            createDialog.close();
        }
    }





}
