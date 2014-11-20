/*
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
/*
 * This code was generated by https://code.google.com/p/google-apis-client-generator/
 * (build: 2014-11-17 18:43:33 UTC)
 * on 2014-11-19 at 22:20:34 UTC 
 * Modify at your own risk.
 */

package com.asaan.server.com.asaan.server.endpoint.userendpoint.model;

/**
 * Model definition for User.
 *
 * <p> This is the Java data model class that specifies how to parse/serialize into the JSON that is
 * transmitted over HTTP when working with the userendpoint. For a detailed explanation see:
 * <a href="http://code.google.com/p/google-http-java-client/wiki/JSON">http://code.google.com/p/google-http-java-client/wiki/JSON</a>
 * </p>
 *
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public final class User extends com.google.api.client.json.GenericJson {

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Boolean admin;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String authToken;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key @com.google.api.client.json.JsonString
  private java.lang.Long createdDate;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key @com.google.api.client.json.JsonString
  private java.lang.Long defaultTip;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String email;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String firstName;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key @com.google.api.client.json.JsonString
  private java.lang.Long id;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String lastName;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key @com.google.api.client.json.JsonString
  private java.lang.Long modifiedDate;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String parseAuthData;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String parseCreatedAt;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String parseObjectId;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String parseUpdatedAt;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String phone;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String profilePhotoUrl;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String userId;

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Boolean getAdmin() {
    return admin;
  }

  /**
   * @param admin admin or {@code null} for none
   */
  public User setAdmin(java.lang.Boolean admin) {
    this.admin = admin;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getAuthToken() {
    return authToken;
  }

  /**
   * @param authToken authToken or {@code null} for none
   */
  public User setAuthToken(java.lang.String authToken) {
    this.authToken = authToken;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Long getCreatedDate() {
    return createdDate;
  }

  /**
   * @param createdDate createdDate or {@code null} for none
   */
  public User setCreatedDate(java.lang.Long createdDate) {
    this.createdDate = createdDate;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Long getDefaultTip() {
    return defaultTip;
  }

  /**
   * @param defaultTip defaultTip or {@code null} for none
   */
  public User setDefaultTip(java.lang.Long defaultTip) {
    this.defaultTip = defaultTip;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getEmail() {
    return email;
  }

  /**
   * @param email email or {@code null} for none
   */
  public User setEmail(java.lang.String email) {
    this.email = email;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getFirstName() {
    return firstName;
  }

  /**
   * @param firstName firstName or {@code null} for none
   */
  public User setFirstName(java.lang.String firstName) {
    this.firstName = firstName;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Long getId() {
    return id;
  }

  /**
   * @param id id or {@code null} for none
   */
  public User setId(java.lang.Long id) {
    this.id = id;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getLastName() {
    return lastName;
  }

  /**
   * @param lastName lastName or {@code null} for none
   */
  public User setLastName(java.lang.String lastName) {
    this.lastName = lastName;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Long getModifiedDate() {
    return modifiedDate;
  }

  /**
   * @param modifiedDate modifiedDate or {@code null} for none
   */
  public User setModifiedDate(java.lang.Long modifiedDate) {
    this.modifiedDate = modifiedDate;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getParseAuthData() {
    return parseAuthData;
  }

  /**
   * @param parseAuthData parseAuthData or {@code null} for none
   */
  public User setParseAuthData(java.lang.String parseAuthData) {
    this.parseAuthData = parseAuthData;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getParseCreatedAt() {
    return parseCreatedAt;
  }

  /**
   * @param parseCreatedAt parseCreatedAt or {@code null} for none
   */
  public User setParseCreatedAt(java.lang.String parseCreatedAt) {
    this.parseCreatedAt = parseCreatedAt;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getParseObjectId() {
    return parseObjectId;
  }

  /**
   * @param parseObjectId parseObjectId or {@code null} for none
   */
  public User setParseObjectId(java.lang.String parseObjectId) {
    this.parseObjectId = parseObjectId;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getParseUpdatedAt() {
    return parseUpdatedAt;
  }

  /**
   * @param parseUpdatedAt parseUpdatedAt or {@code null} for none
   */
  public User setParseUpdatedAt(java.lang.String parseUpdatedAt) {
    this.parseUpdatedAt = parseUpdatedAt;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getPhone() {
    return phone;
  }

  /**
   * @param phone phone or {@code null} for none
   */
  public User setPhone(java.lang.String phone) {
    this.phone = phone;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getProfilePhotoUrl() {
    return profilePhotoUrl;
  }

  /**
   * @param profilePhotoUrl profilePhotoUrl or {@code null} for none
   */
  public User setProfilePhotoUrl(java.lang.String profilePhotoUrl) {
    this.profilePhotoUrl = profilePhotoUrl;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getUserId() {
    return userId;
  }

  /**
   * @param userId userId or {@code null} for none
   */
  public User setUserId(java.lang.String userId) {
    this.userId = userId;
    return this;
  }

  @Override
  public User set(String fieldName, Object value) {
    return (User) super.set(fieldName, value);
  }

  @Override
  public User clone() {
    return (User) super.clone();
  }

}
