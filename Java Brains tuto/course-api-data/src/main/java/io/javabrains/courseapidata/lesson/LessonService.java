package io.javabrains.courseapidata.lesson;

import io.javabrains.courseapidata.course.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LessonService {

    @Autowired
    private LessonRepository lessonRepository;

    public List<Lesson> getAllLesson(String lessonId) {
        List<Lesson> lessons = new ArrayList<>();
        lessonRepository.findByCourseId(lessonId).forEach(lessons::add);
        return lessons;
    }

    public Lesson getLesson(String id) {
        Lesson lesson;
        lesson = lessonRepository.findById(id).get();
        return lesson;
        // or return courseRepository.findOne(id);
    }

    public void addLesson(Lesson lesson) {
        lessonRepository.save(lesson);
    }

    public void updateLesson(Lesson lesson) {
        lessonRepository.save(lesson);
    }

    public void deleteLesson(String id) {
        lessonRepository.deleteById(id);
    }

}
