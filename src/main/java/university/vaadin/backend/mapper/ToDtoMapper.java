package university.vaadin.backend.mapper;

public interface ToDtoMapper<E,D> {
    D toDto(E entity);

}
