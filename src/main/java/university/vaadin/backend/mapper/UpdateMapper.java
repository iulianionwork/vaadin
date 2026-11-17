package university.vaadin.backend.mapper;

public interface UpdateMapper<D, E> {
    void updateEntity(D dto, E entity);
}
