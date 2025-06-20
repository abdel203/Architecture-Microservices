package fr.dauphine.activities.activity;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ActivityService {

    private final MongoTemplate mongoTemplate;
    private final ActivityRepository activityRepository;

    public String saveActivity(Map<String, Object> inputData) {
        Activity activity = new Activity();
        activity.setDescription(inputData);
        return activityRepository.save(activity).getId();
    }

    public List<Activity> getAllActivities() {
        return activityRepository.findAll();
    }

    public Optional<Activity> getActivityById(String id) {
        return activityRepository.findById(id);
    }

    public List<Activity> getActivitiesByIds(List<String> ids) {
        return activityRepository.findAllById(ids);
    }

    public List<Activity> searchByRawFilter(Map<String, Object> filterMap) {
        Criteria criteria = parseCriteria(filterMap);
        Query query = new Query(criteria);
        System.out.println("Generated query: " + query);
        return mongoTemplate.find(query, Activity.class, "activities");
    }

    @SuppressWarnings("unchecked")
    private Criteria parseCriteria(Map<String, Object> filterMap) {
        Criteria root = new Criteria();

        for (Map.Entry<String, Object> entry : filterMap.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();

            switch (key) {
                case "$and": {
                    List<Map<String, Object>> conditions = (List<Map<String, Object>>) value;
                    List<Criteria> subCriteria = new ArrayList<>();
                    for (Map<String, Object> condition : conditions) {
                        subCriteria.add(parseCriteria(condition));
                    }
                    root = root.andOperator(subCriteria.toArray(new Criteria[0]));
                    break;
                }
                case "$or": {
                    List<Map<String, Object>> conditions = (List<Map<String, Object>>) value;
                    List<Criteria> subCriteria = new ArrayList<>();
                    for (Map<String, Object> condition : conditions) {
                        subCriteria.add(parseCriteria(condition));
                    }
                    root = root.orOperator(subCriteria.toArray(new Criteria[0]));
                    break;
                }
                case "$nor": {
                    List<Map<String, Object>> conditions = (List<Map<String, Object>>) value;
                    List<Criteria> subCriteria = new ArrayList<>();
                    for (Map<String, Object> condition : conditions) {
                        subCriteria.add(parseCriteria(condition));
                    }
                    root = root.norOperator(subCriteria.toArray(new Criteria[0]));
                    break;
                }
                default: {
                    // gestion des opérateurs de comparaison : {"field": { "$gt": 10 }}
                    if (value instanceof Map) {
                        Map<String, Object> operatorMap = (Map<String, Object>) value;
                        Criteria fieldCriteria = Criteria.where(key);
                        for (Map.Entry<String, Object> op : operatorMap.entrySet()) {
                            switch (op.getKey()) {
                                case "$eq":
                                    fieldCriteria.is(op.getValue());
                                    break;
                                case "$ne":
                                    fieldCriteria.ne(op.getValue());
                                    break;
                                case "$gt":
                                    fieldCriteria.gt(op.getValue());
                                    break;
                                case "$gte":
                                    fieldCriteria.gte(op.getValue());
                                    break;
                                case "$lt":
                                    fieldCriteria.lt(op.getValue());
                                    break;
                                case "$lte":
                                    fieldCriteria.lte(op.getValue());
                                    break;
                                case "$in":
                                    fieldCriteria.in((List<?>) op.getValue());
                                    break;
                                case "$nin":
                                    fieldCriteria.nin((List<?>) op.getValue());
                                    break;
                                case "$regex":
                                    fieldCriteria.regex(op.getValue().toString(), "i");
                                    break;
                                default:
                                    throw new IllegalArgumentException("Unsupported operator: " + op.getKey());
                            }
                        }
                        root = fieldCriteria;
                    } else {
                        // cas simple : {"field": value}
                        root = Criteria.where(key).is(value);
                    }
                    break;
                }
            }
        }
        return root;
    }

}
