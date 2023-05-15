package com.es.elasticsearch.scheduler;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.es.elasticsearch.Repository.ElasticSearchQuery;
import com.es.elasticsearch.Repository.UserRepository;
import com.es.elasticsearch.entity.User;
import com.es.elasticsearch.entity.UserData;

@Component
public class FailedFetchScheduler {

	private static final Logger LOGGER = LoggerFactory.getLogger(FailedFetchScheduler.class);

	@Autowired
	private ElasticSearchQuery elasticSearchQuery;

	@Autowired
	private UserRepository userRepository;

	@Scheduled(cron = "0 * * ? * *") // runs every hour
	public void updateFailedUpdates() throws IOException {
		User user = new User();
		List<UserData> userlist = userRepository.findAll();

		if(!userlist.isEmpty() && userlist.size() > 0) {
			for (UserData u : userlist) {
				user.setId(u.getId());
				user.setFirstName(u.getFirstName());
				user.setLastName(u.getLastName());
				user.setEmail(u.getEmail());
				user.setMobileNumber(u.getMobileNumber());
				user.setCreateDate(u.getCreateDate());
				user.setUpdateDate(u.getUpdateDate());
				elasticSearchQuery.createOrUpdateDocument(user);
			}
			userRepository.deleteAll();
		}else {
			LOGGER.info("No data available to fetch");
		}	
	}
}
