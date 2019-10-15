package io.javabrains.courseapidata.lesson;

import io.javabrains.courseapidata.course.Course;
import io.javabrains.courseapidata.course.CourseService;
import io.javabrains.courseapidata.topic.Topic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class LessonController {

    @Autowired
    private LessonService lessonService;

    @RequestMapping("/topics/{topicId}/courses/{courseId}/lessons")
    public List<Lesson> getAllLessons(@PathVariable String courseId, @PathVariable String topicId) {
        return lessonService.getAllLesson(courseId);
    }

    @RequestMapping("/topics/{topicId}/courses/{courseId}/lessons/{id}")
    public Lesson getLesson(@PathVariable String topicId, @PathVariable String courseId, @PathVariable String id) {
        return lessonService.getLesson(id);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/topics/{topicId}/courses/{courseId}/lessons")
    public void addLesson(@RequestBody Lesson lesson, @PathVariable String topicId, @PathVariable String courseId) {
        lesson.setCourse(new Course(courseId, "", "",""));
        lessonService.addLesson(lesson);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/topics/{topicId}/courses/{courseId}/lessons/{id}")
    public void updateLesson(@RequestBody Lesson lesson, @PathVariable String courseId, @PathVariable String topicId, @PathVariable String id) {
        lesson.setCourse(new Course(courseId, "", "",""));
        lessonService.updateLesson(lesson);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/topics/{topicId}/courses/{courseId}/lessons/{id}")
    public void deleteLesson(@PathVariable String courseId, @PathVariable String topicId, @PathVariable String id) {
        lessonService.deleteLesson(id);
    }
}
