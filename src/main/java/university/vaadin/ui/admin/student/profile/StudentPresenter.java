package university.vaadin.ui.admin.student.profile;

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
import university.vaadin.ui.common.presenter.BasePresenter;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Presenter
@RequiredArgsConstructor
public class StudentPresenter extends BasePresenter<StudentView> {

    private final StudentService studentService;
    private final GroupService groupService;
    private final CourseService courseService;
    private final CarnetService carnetService;
    private final HashMap<String, String> details = new LinkedHashMap<>();

    private Long studentId;

    public void load(Long studentId) {
        this.studentId = studentId;
        try{
            StudentResponse student = studentService.getStudentById(studentId);

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
        view.showCourses(courses);
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
        if(!isValid(request)){
            return;
        }
        if(groupId == null){
            view.showFieldError("groupId","Please select a group");
            return;
        }

        if(execute(()->{
            studentService.updateStudent(studentId, request);
            studentService.assignStudentToGroup(groupId, studentId);
        },"Successfully updated student details" )){
            view.closeCreatedDialog();
            load(studentId);
        }


    }

    public void onEnrollClicked(Long courseId){
        if(courseId == null){
            view.showFieldError("courseId","Please select a course");
            return;
        }

        if(execute(()-> courseService.enrollStudentToCourse(courseId, studentId),
                "Successfully enrolled student to course" )){
            refreshCourses();
        }

    }

    public void onUnenrollClicked(Long courseId) {

        if(execute(()->  courseService.unenrollStudentFromCourse(courseId, studentId),
                "Successfully unenrolled student from course" )){
            refreshCourses();
        }

    }

    public void onIssueCarnetClicked() {

        if(execute(()->  carnetService.issueCarnet(studentId),
                "Carnet issue successfully" )){
            refreshCarnet();
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



