package com.es.elasticsearch.entity;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(indexName = "userinformation_elasticsearch")
public class User {

	@Id
	private String id;

	@Field(type = FieldType.Text, name = "firstName")
	private String firstName;

	@Field(type = FieldType.Text, name = "lastName")
	private String lastName;

	@Field(type = FieldType.Text, name = "email")
	private String email;

	@Field(type = FieldType.Long, name = "mobileNumber")
	private long mobileNumber;

	@Field(type = FieldType.Text, name = "gender")
	private String gender;

	@Field(type = FieldType.Date, name = "createDate")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date createDate;

	@Field(type = FieldType.Date, name = "updateDate")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date updateDate;

	@Override
	public String toString() {
		return "User [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email
				+ ", mobileNumber=" + mobileNumber + ", gender=" + gender + ", createDate=" + createDate
				+ ", updateDate=" + updateDate + "]";
	}
	
	
}
