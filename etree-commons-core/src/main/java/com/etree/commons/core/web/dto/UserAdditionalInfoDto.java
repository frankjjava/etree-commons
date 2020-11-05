/**
 * Copyright © 2020 eTree Technologies Pvt. Ltd.
 *
 * @author  Franklin Joshua
 * @version 1.0
 * @since   2020-11-04 
 */
package com.etree.commons.core.web.dto;

import java.sql.Timestamp;
import java.util.List;

public abstract class UserAdditionalInfoDto {
	
	private String title;
	private String userType;
	private Boolean changePassword;
	private String password;

	private String officeId;
	private String officeCode;
	private String officeIataCode;
	private String officeEmailId;
	private String organizationId;
	private String organizationCode;
	private String organizationType;
	private String organizationName;
	private String facebookId;
	private String twitterId;
	private String googleId;
	private String email;
	
	private String timeZone;
	private String convenienceFee;
	private String paymentOptions;

	private String phoneNumbers;
	private List<String> languageCodes;
	private String address;
	private String localeAddress;
	private String city;
	private String state;
	private String zipCode;
	private String country;
	private String status;
	private Timestamp createdDate;
	private Timestamp updatedDate;
	
	private boolean agencyAdmin;
	private boolean corporateCustomer;
	private String appVersion;
	private String bookingEmail;	
	private String cancellationEmail;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	public Boolean isChangePassword() {
		return changePassword;
	}
	public void setChangePassword(Boolean changePassword) {
		this.changePassword = changePassword;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getOfficeId() {
		return officeId;
	}
	public void setOfficeId(String officeId) {
		this.officeId = officeId;
	}
	public String getOfficeCode() {
		return officeCode;
	}
	public void setOfficeCode(String officeCode) {
		this.officeCode = officeCode;
	}
	public String getOfficeIataCode() {
		return officeIataCode;
	}
	public void setOfficeIataCode(String officeIataCode) {
		this.officeIataCode = officeIataCode;
	}
	public String getOfficeEmailId() {
		return officeEmailId;
	}
	public void setOfficeEmailId(String officeEmailId) {
		this.officeEmailId = officeEmailId;
	}
	public String getOrganizationId() {
		return organizationId;
	}
	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
	}
	public String getOrganizationCode() {
		return organizationCode;
	}
	public void setOrganizationCode(String organizationCode) {
		this.organizationCode = organizationCode;
	}
	public String getOrganizationType() {
		return organizationType;
	}
	public void setOrganizationType(String organizationType) {
		this.organizationType = organizationType;
	}
	public String getOrganizationName() {
		return organizationName;
	}
	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}
	public String getFacebookId() {
		return facebookId;
	}
	public void setFacebookId(String facebookId) {
		this.facebookId = facebookId;
	}
	public String getTwitterId() {
		return twitterId;
	}
	public void setTwitterId(String twitterId) {
		this.twitterId = twitterId;
	}
	public String getGoogleId() {
		return googleId;
	}
	public void setGoogleId(String googleId) {
		this.googleId = googleId;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTimeZone() {
		return timeZone;
	}
	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
	}
	public String getConvenienceFee() {
		return convenienceFee;
	}
	public void setConvenienceFee(String convenienceFee) {
		this.convenienceFee = convenienceFee;
	}
	public String getPaymentOptions() {
		return paymentOptions;
	}
	public void setPaymentOptions(String paymentOptions) {
		this.paymentOptions = paymentOptions;
	}
	public String getPhoneNumbers() {
		return phoneNumbers;
	}
	public void setPhoneNumbers(String phoneNumbers) {
		this.phoneNumbers = phoneNumbers;
	}

	public List<String> getLanguageCodes() {
		return languageCodes;
	}
	public void setLanguageCodes(List<String> languageCodes) {
		this.languageCodes = languageCodes;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getLocaleAddress() {
		return localeAddress;
	}
	public void setLocaleAddress(String localeAddress) {
		this.localeAddress = localeAddress;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getZipCode() {
		return zipCode;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Timestamp getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}
	public Timestamp getUpdatedDate() {
		return updatedDate;
	}
	public void setUpdatedDate(Timestamp updatedDate) {
		this.updatedDate = updatedDate;
	}
	public Boolean getChangePassword() {
		return changePassword;
	}

	public boolean isAgencyAdmin() {
		return agencyAdmin;
	}

	public void setAgencyAdmin(boolean agencyAdmin) {
		this.agencyAdmin = agencyAdmin;
	}
	public boolean isCorporateCustomer() {
		return corporateCustomer;
	}
	public void setCorporateCustomer(boolean corporateCustomer) {
		this.corporateCustomer = corporateCustomer;
	}
	public String getAppVersion() {
		return appVersion;
	}
	public void setAppVersion(String appVersion) {
		this.appVersion = appVersion;
	}
	public String getBookingEmail() {
		return bookingEmail;
	}
	public void setBookingEmail(String bookingEmail) {
		this.bookingEmail = bookingEmail;
	}
	public String getCancellationEmail() {
		return cancellationEmail;
	}
	public void setCancellationEmail(String cancellationEmail) {
		this.cancellationEmail = cancellationEmail;
	}

}