package university.vaadin.backend.mapper;

public interface ToEntityMapper<D,E> {
    E toEntity(D dto);

}
