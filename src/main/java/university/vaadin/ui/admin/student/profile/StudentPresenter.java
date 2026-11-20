package university.vaadin.ui.admin.student.profile;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import university.vaadin.backend.dto.course.CourseResponse;
import university.vaadin.backend.dto.group.GroupResponse;
import university.vaadin.backend.dto.student.StudentResponse;
import university.vaadin.backend.dto.student.UpdateStudentRequest;
import university.vaadin.backend.exception.NotFoundException;
import university.vaadin.backend.service.CarnetService;
import university.vaadin.backend.service.CourseService;
import university.vaadin.backend.service.GroupService;
import university.vaadin.backend.service.StudentService;
import university.vaadin.ui.common.annotation.Presenter;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Presenter
@RequiredArgsConstructor
public class StudentPresenter {

    private final StudentService studentService;
    private final GroupService groupService;
    private final CourseService courseService;
    private final CarnetService carnetService;
    private final Validator validator;
    private final HashMap<String, String> details = new LinkedHashMap<>();

    private StudentView view;
    private Long studentId;


    public void init(StudentView view) {
        this.view = view;
    }

    public void load(Long studentId) {
        this.studentId = studentId;
        try{
            StudentResponse student = studentService.getStudentById(studentId);

            view.showWelcome(student.firstName());

            details.put("First Name", student.firstName());
            details.put("Last Name", student.lastName());
            details.put("Email", student.email());
            details.put("Group", student.groupName() == null ? "Student is not assign to group yet!": student.groupName());
            view.showDetails(details);
            view.showStudent(student);
            refreshCourses();
            showGroups(student);
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
        view.showCourses(courses, false);
    }

    private void showGroups(StudentResponse student) {
        List<GroupResponse> groups = groupService.getGroups();

        GroupResponse current = groups.stream()
                .filter(g -> g.groupName().equals(student.groupName()))
                .findFirst()
                .orElse(null);
        view.showGroups(groups, current);
    }

    public void onSaveClicked(UpdateStudentRequest request, Long groupId) {
        view.clearFieldError();
        Set<ConstraintViolation<UpdateStudentRequest>> errors = validator.validate(request);
        if(!errors.isEmpty()) {
            for(ConstraintViolation<UpdateStudentRequest> error : errors) {
                view.showFieldError(error.getPropertyPath().toString(), error.getMessage());
            }
            return;
        }
        if(groupId == null){
            view.showError("Please select a group");
            return;
        }

        try{
            studentService.updateStudent(studentId, request);
            studentService.assignStudentToGroup(groupId, studentId);
            view.closeCreatedDialog();
            load(studentId);
            view.showSuccess("Successfully updated student details");
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



    public void onBackClicked() {
        view.navigateBack();
    }


    private void refreshCarnet(){
        carnetService.findCarnetByStudentId(studentId)
                .ifPresentOrElse(view::showCarnet, view::showNoCarnet);
    }

}



