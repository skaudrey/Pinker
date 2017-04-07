package com.example.pinker.systemservice;

import java.io.Serializable;

@SuppressWarnings("serial")
class SystemPeople implements Serializable {

	public String peoplename;
	public String peopleage;

	public SystemPeople(String peoplename, String peopleage) {
		this.peoplename = peoplename;
		this.peopleage = peopleage;
	}
}