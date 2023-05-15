package com.es.elasticsearch.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.es.elasticsearch.entity.UserData;

public interface UserRepository extends JpaRepository<UserData, String> {

}
