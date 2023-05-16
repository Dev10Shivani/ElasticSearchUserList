package com.es.elasticsearch.Repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import com.es.elasticsearch.entity.User;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.FieldSort;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch.core.DeleteRequest;
import co.elastic.clients.elasticsearch.core.DeleteResponse;
import co.elastic.clients.elasticsearch.core.GetResponse;
import co.elastic.clients.elasticsearch.core.IndexResponse;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;

@Repository
public class ElasticSearchQuery {

	@Autowired
	private ElasticsearchClient elasticsearchClient;
	
	@Autowired
	private UserESRepository userESRepository;

	private final String indexName = "userinformation_elasticsearch";

	public String createOrUpdateDocument(User user) throws IOException {

		IndexResponse response = elasticsearchClient.index(i -> i.index(indexName).id(user.getId()).document(user));
		if (response.result().name().equals("Created")) {
			return new StringBuilder("Document has been successfully created.").toString();
		} else if (response.result().name().equals("Updated")) {
			return new StringBuilder("Document has been successfully updated.").toString();
		}
		return new StringBuilder("Error while performing the operation.").toString();
	}

	public List<User> searchAllDocuments() throws IOException {

		SearchRequest searchRequest = SearchRequest.of(s -> s.index(indexName));
		SearchResponse searchResponse = elasticsearchClient.search(s -> {
			return s.index(indexName).from(0).size(10)
					.sort(so -> so.field(FieldSort.of(f -> f.field("id.keyword").order(SortOrder.Asc))));
		}, User.class);
		List<Hit> hits = searchResponse.hits().hits();
		List<User> users = new ArrayList<>();
		for (Hit object : hits) {
			// System.out.print(((User) object.source()));
			users.add((User) object.source());
		}
		return users;
	}
	
	public Page<User> findPaginated(int pageNo, int pageSize) {
		PageRequest pageable = PageRequest.of(pageNo, pageSize);
		return this.userESRepository.findAll(pageable);
	}

	public List<User> searchByKeyword(String keyword) throws IOException {
		SearchRequest searchRequest = SearchRequest.of(s -> s.index(indexName).size(10)
				.sort(so -> so.field(FieldSort.of(f -> f.field("id.keyword").order(SortOrder.Asc))))
				.query(q -> q.multiMatch(t -> t.fields("firstName", "lastName", "email").query(keyword))));
		SearchResponse searchResponse = elasticsearchClient.search(searchRequest, User.class);
		List<Hit> hits = searchResponse.hits().hits();
		List<User> users = new ArrayList<>();
		for (Hit object : hits) {
			// System.out.print(((User) object.source()));
			users.add((User) object.source());
		}
		return users;
	}

	public User getDocumentById(String userId) throws IOException {
		User user = null;
		GetResponse<User> response = elasticsearchClient.get(g -> g.index(indexName).id(userId), User.class);

		if (response.found()) {
			user = response.source();
			System.out.println("User Name : " + user.getFirstName() + " " + user.getLastName());
		} else {
			System.out.println("User not found");
		}

		return user;
	}

	public String deleteDocumentById(String userId) throws IOException {

		DeleteRequest request = DeleteRequest.of(d -> d.index(indexName).id(userId));

		DeleteResponse deleteResponse = elasticsearchClient.delete(request);
		if (Objects.nonNull(deleteResponse.result()) && !deleteResponse.result().name().equals("NotFound")) {
			return new StringBuilder("User with id " + deleteResponse.id() + " has been deleted.").toString();
		}
		System.out.println("User not found");
		return new StringBuilder("User with id " + deleteResponse.id() + " does not exist.").toString();
	}
}
