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
 * (build: 2014-10-28 17:08:27 UTC)
 * on 2014-11-05 at 21:22:24 UTC 
 * Modify at your own risk.
 */

package com.asaan.server.com.asaan.server.endpoint.userendpoint.model;

/**
 * Model definition for UserCard.
 *
 * <p> This is the Java data model class that specifies how to parse/serialize into the JSON that is
 * transmitted over HTTP when working with the userendpoint. For a detailed explanation see:
 * <a href="http://code.google.com/p/google-http-java-client/wiki/JSON">http://code.google.com/p/google-http-java-client/wiki/JSON</a>
 * </p>
 *
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public final class UserCard extends com.google.api.client.json.GenericJson {

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String accessToken;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String address;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String brand;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String city;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String country;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key @com.google.api.client.json.JsonString
  private java.lang.Long createdDate;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String currency;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key("default")
  private java.lang.Boolean default__;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key("exp_month")
  private java.lang.Integer expMonth;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key("exp_year")
  private java.lang.Integer expYear;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String fundingType;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key @com.google.api.client.json.JsonString
  private java.lang.Long id;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String last4;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key @com.google.api.client.json.JsonString
  private java.lang.Long modifiedDate;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String name;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String provider;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String providerCustomerId;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String refreshToken;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String state;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key @com.google.api.client.json.JsonString
  private java.lang.Long userId;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String zip;

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getAccessToken() {
    return accessToken;
  }

  /**
   * @param accessToken accessToken or {@code null} for none
   */
  public UserCard setAccessToken(java.lang.String accessToken) {
    this.accessToken = accessToken;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getAddress() {
    return address;
  }

  /**
   * @param address address or {@code null} for none
   */
  public UserCard setAddress(java.lang.String address) {
    this.address = address;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getBrand() {
    return brand;
  }

  /**
   * @param brand brand or {@code null} for none
   */
  public UserCard setBrand(java.lang.String brand) {
    this.brand = brand;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getCity() {
    return city;
  }

  /**
   * @param city city or {@code null} for none
   */
  public UserCard setCity(java.lang.String city) {
    this.city = city;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getCountry() {
    return country;
  }

  /**
   * @param country country or {@code null} for none
   */
  public UserCard setCountry(java.lang.String country) {
    this.country = country;
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
  public UserCard setCreatedDate(java.lang.Long createdDate) {
    this.createdDate = createdDate;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getCurrency() {
    return currency;
  }

  /**
   * @param currency currency or {@code null} for none
   */
  public UserCard setCurrency(java.lang.String currency) {
    this.currency = currency;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Boolean getDefault() {
    return default__;
  }

  /**
   * @param default__ default__ or {@code null} for none
   */
  public UserCard setDefault(java.lang.Boolean default__) {
    this.default__ = default__;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Integer getExpMonth() {
    return expMonth;
  }

  /**
   * @param expMonth expMonth or {@code null} for none
   */
  public UserCard setExpMonth(java.lang.Integer expMonth) {
    this.expMonth = expMonth;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Integer getExpYear() {
    return expYear;
  }

  /**
   * @param expYear expYear or {@code null} for none
   */
  public UserCard setExpYear(java.lang.Integer expYear) {
    this.expYear = expYear;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getFundingType() {
    return fundingType;
  }

  /**
   * @param fundingType fundingType or {@code null} for none
   */
  public UserCard setFundingType(java.lang.String fundingType) {
    this.fundingType = fundingType;
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
  public UserCard setId(java.lang.Long id) {
    this.id = id;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getLast4() {
    return last4;
  }

  /**
   * @param last4 last4 or {@code null} for none
   */
  public UserCard setLast4(java.lang.String last4) {
    this.last4 = last4;
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
  public UserCard setModifiedDate(java.lang.Long modifiedDate) {
    this.modifiedDate = modifiedDate;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getName() {
    return name;
  }

  /**
   * @param name name or {@code null} for none
   */
  public UserCard setName(java.lang.String name) {
    this.name = name;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getProvider() {
    return provider;
  }

  /**
   * @param provider provider or {@code null} for none
   */
  public UserCard setProvider(java.lang.String provider) {
    this.provider = provider;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getProviderCustomerId() {
    return providerCustomerId;
  }

  /**
   * @param providerCustomerId providerCustomerId or {@code null} for none
   */
  public UserCard setProviderCustomerId(java.lang.String providerCustomerId) {
    this.providerCustomerId = providerCustomerId;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getRefreshToken() {
    return refreshToken;
  }

  /**
   * @param refreshToken refreshToken or {@code null} for none
   */
  public UserCard setRefreshToken(java.lang.String refreshToken) {
    this.refreshToken = refreshToken;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getState() {
    return state;
  }

  /**
   * @param state state or {@code null} for none
   */
  public UserCard setState(java.lang.String state) {
    this.state = state;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Long getUserId() {
    return userId;
  }

  /**
   * @param userId userId or {@code null} for none
   */
  public UserCard setUserId(java.lang.Long userId) {
    this.userId = userId;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getZip() {
    return zip;
  }

  /**
   * @param zip zip or {@code null} for none
   */
  public UserCard setZip(java.lang.String zip) {
    this.zip = zip;
    return this;
  }

  @Override
  public UserCard set(String fieldName, Object value) {
    return (UserCard) super.set(fieldName, value);
  }

  @Override
  public UserCard clone() {
    return (UserCard) super.clone();
  }

}
