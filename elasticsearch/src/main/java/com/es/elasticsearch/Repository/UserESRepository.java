package com.es.elasticsearch.Repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.es.elasticsearch.entity.User;

public interface UserESRepository extends ElasticsearchRepository<User, String> {

}
