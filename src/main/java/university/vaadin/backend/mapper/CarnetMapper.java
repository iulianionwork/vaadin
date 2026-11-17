package university.vaadin.backend.mapper;

import org.springframework.stereotype.Component;
import university.vaadin.backend.dto.carnet.CarnetResponse;
import university.vaadin.backend.model.Carnet;

@Component
public class CarnetMapper implements ToDtoMapper<Carnet, CarnetResponse> {
    @Override
    public CarnetResponse toDto(Carnet entity) {
        return new CarnetResponse(entity.getId(),
                entity.getStudentCode(),
                entity.getIssueDate());
    }
}
