package com.es.elasticsearch.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "UserData")
public class UserData {

	@Id
	private String id;

	@Column(name = "firstName")
	private String firstName;

	@Column(name = "lastName")
	private String lastName;

	@Column(name = "email")
	private String email;

	@Column(name = "mobileNumber")
	private long mobileNumber;

	@Column(name = "gender")
	private String gender;

	@Column(name = "createDate")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date createDate;

	@Column(name = "updateDate")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date updateDate;

	@Override
	public String toString() {
		return "UserData [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email
				+ ", mobileNumber=" + mobileNumber + ", gender=" + gender + ", createDate=" + createDate
				+ ", updateDate=" + updateDate + "]";
	}
}
