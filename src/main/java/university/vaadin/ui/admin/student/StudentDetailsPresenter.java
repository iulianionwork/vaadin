package university.vaadin.ui.admin.student;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import university.vaadin.backend.dto.course.CourseResponse;
import university.vaadin.backend.dto.group.GroupResponse;
import university.vaadin.backend.dto.student.StudentResponse;
import university.vaadin.backend.dto.student.UpdateStudentRequest;
import university.vaadin.backend.exception.NotFoundException;
import university.vaadin.backend.service.CarnetService;
import university.vaadin.backend.service.CourseService;
import university.vaadin.backend.service.GroupService;
import university.vaadin.backend.service.StudentService;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@RequiredArgsConstructor()
public class StudentDetailsPresenter {

    private final StudentService studentService;
    private final GroupService groupService;
    private final CourseService courseService;
    private final CarnetService carnetService;
    private final Validator validator;

    private StudentDetails view;
    Long studentId;

    public void init(StudentDetails view) {
        this.view = view;
    }

    public void onStudentSelected(Long studentId) {
        this.studentId = studentId;

        try{
            StudentResponse student = studentService.getStudentById(studentId);
            view.showStudent(student);
            showGroups(student);
            refreshCourses();
            refreshCarnet();
        }catch(NotFoundException e){
            view.showNotFound(e.getMessage());
        }
    }

    private void refreshCourses() {
        List<CourseResponse> courses = courseService.getCoursesOfStudentById(studentId);

        Set<Long> enrolledIds = courses.stream()
                .map(CourseResponse::id)
                .collect(Collectors.toSet());

        List<CourseResponse> allCourses = courseService.getAllCourses().stream()
                .filter(c -> !enrolledIds.contains(c.id()))
                .toList();

        view.showAvailableCourses(allCourses);
        view.showEnrolledCourses(courses);
    }

    private void showGroups(StudentResponse student) {
        List<GroupResponse> groups = groupService.getGroups();

        GroupResponse current = groups.stream()
                .filter(g -> g.groupName().equals(student.groupName()))
                .findFirst()
                .orElse(null);
        view.showGroups(groups, current);
    }

    public void onSaveClicked(String firstName, String lastName) {
        UpdateStudentRequest request = new UpdateStudentRequest(firstName.trim(), lastName.trim());
        view.clearFieldError();
        Set<ConstraintViolation<UpdateStudentRequest>> errors = validator.validate(request);
        if(!errors.isEmpty()) {
            for(ConstraintViolation<UpdateStudentRequest> error : errors) {
                view.showFieldError(error.getPropertyPath().toString(), error.getMessage());
            }
            return;
        }

        try{
            studentService.updateStudent(studentId, request);
            view.showStudent(studentService.getStudentById(studentId));
            view.showSuccess("Successfully updated student details");
        }catch(RuntimeException e){
            view.showError(e.getMessage());
        }

    }

    public void onGroupSelected(Long groupId) {
        if(groupId == null){
            view.showError("Please select a group");
            return;
        }

        try{
            studentService.assignStudentToGroup(groupId, studentId);
            view.showSuccess("Successfully assigned student to group");
        }catch(RuntimeException e){
            view.showError(e.getMessage());
        }


    }

    public void onEnrollClicked(Long courseId){
        if(courseId == null){
            view.showError("Please select a course");
            return;
        }

        try{
            courseService.enrollStudentToCourse(courseId, studentId);
            view.showSuccess("Successfully enrolled student to course");
            refreshCourses();
        }catch(RuntimeException e){
            view.showError(e.getMessage());
        }
    }

    public void onUnenrollClicked(Long courseId) {
        try {
            courseService.unenrollStudentFromCourse(courseId, studentId);
            refreshCourses();
            view.showSuccess("Successfully unenrolled student from course");
        } catch (RuntimeException e) {
            view.showError(e.getMessage());
        }
    }




    public void onIssueCarnetClicked() {
        try{
            carnetService.issueCarnet(studentId);
            view.showSuccess("Carnet issue successfully");
            refreshCarnet();
        }catch(RuntimeException e){
            view.showError(e.getMessage());
        }
    }

    public void onGroupAttachClicked(Long groupId) {
        try{
            studentService.assignStudentToGroup(groupId, studentId);
            view.showSuccess("Group assignment successfully");

        }catch(RuntimeException e){
            view.showError(e.getMessage());
        }
    }

    public void onBackClicked() {
        view.navigateBack();
    }


    private void refreshCarnet(){
        carnetService.findCarnetByStudentId(studentId)
                .ifPresentOrElse(view::showCarnet, view::showNoCarnet);
    }



    }



