package fr.dauphine.activities.poi;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import fr.dauphine.activities.activity.Activity;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PointOfInterestService {

    private final MongoTemplate mongoTemplate;
    private final PointOfInterestRepository pointOfInterestRepository;

    public String savePoi(Map<String, Object> inputData) {
        PointOfInterest pointOfInterest = new PointOfInterest();
        pointOfInterest.setDescription(inputData);
        return pointOfInterestRepository.save(pointOfInterest).getId();
    }

    public List<PointOfInterest> getPois() {
        return pointOfInterestRepository.findAll();
    }

    public Optional<PointOfInterest> getPoiById(String id) {
        return pointOfInterestRepository.findById(id);
    }

    public List<PointOfInterest> getPoisByIds(List<String> ids) {
        return pointOfInterestRepository.findAllById(ids);
    }

    public List<PointOfInterest> searchByRawFilter(Map<String, Object> filterMap) {
        Criteria criteria = parseCriteria(filterMap);
        Query query = new Query(criteria);
        System.out.println("Generated query: " + query);
        return mongoTemplate.find(query, PointOfInterest.class, "pois");
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
