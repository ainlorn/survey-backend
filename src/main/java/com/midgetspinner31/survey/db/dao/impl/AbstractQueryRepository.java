package com.midgetspinner31.survey.db.dao.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.support.PageableExecutionUtils;

import java.lang.reflect.ParameterizedType;

public abstract class AbstractQueryRepository<T> {
    private final MongoTemplate mongoTemplate;
    private final Class<T> clazz;

    protected AbstractQueryRepository(MongoTemplate mongoTemplate) {
        this.clazz = (Class<T>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0];
        this.mongoTemplate = mongoTemplate;
    }

    protected Page<T> getPage(Query query, PageRequest pageRequest) {
        return PageableExecutionUtils.getPage(mongoTemplate.find(query, clazz), pageRequest,
                () -> mongoTemplate.count(Query.of(query).limit(-1).skip(-1), clazz));
    }
}
