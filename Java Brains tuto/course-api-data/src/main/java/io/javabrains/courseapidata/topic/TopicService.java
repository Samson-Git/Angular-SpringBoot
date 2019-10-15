package io.javabrains.courseapidata.topic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class TopicService {

        @Autowired
        private TopicRepository topicRepository;

        public List<Topic> getAllTopics() {
            List<Topic> topics = new ArrayList<>();
            topicRepository.findAll().forEach(topics::add);
            return topics;
        }

        public Topic getTopic(String id) {
            Topic topic;
            topic = topicRepository.findById(id).get();
            return topic;
            // or return topicRepository.findOne(id);
        }

        public void addTopic(Topic topic) {

            topicRepository.save(topic);
        }

        public void updateTopic(Topic topic, String id) {
            topicRepository.save(topic);
        }

        public void deleteTopic(String id) {
            topicRepository.deleteById(id);
        }
}
