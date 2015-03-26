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
 * (build: 2015-01-14 17:53:03 UTC)
 * on 2015-03-20 at 19:47:14 UTC 
 * Modify at your own risk.
 */

package com.asaan.server.com.asaan.server.endpoint.userendpoint.model;

/**
 * Model definition for UserAddress.
 *
 * <p> This is the Java data model class that specifies how to parse/serialize into the JSON that is
 * transmitted over HTTP when working with the userendpoint. For a detailed explanation see:
 * <a href="http://code.google.com/p/google-http-java-client/wiki/JSON">http://code.google.com/p/google-http-java-client/wiki/JSON</a>
 * </p>
 *
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public final class UserAddress extends com.google.api.client.json.GenericJson {

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String apartment;

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
  @com.google.api.client.util.Key
  private java.lang.String county;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String crossStreet;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key("default")
  private java.lang.Boolean default__;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String fullAddress;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key @com.google.api.client.json.JsonString
  private java.lang.Long id;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String isocountryCode;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Double lat;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Double lng;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String name;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String neighbourhood;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String notes;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String state;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String street;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String streetNumber;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String title;

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
  public java.lang.String getApartment() {
    return apartment;
  }

  /**
   * @param apartment apartment or {@code null} for none
   */
  public UserAddress setApartment(java.lang.String apartment) {
    this.apartment = apartment;
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
  public UserAddress setCity(java.lang.String city) {
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
  public UserAddress setCountry(java.lang.String country) {
    this.country = country;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getCounty() {
    return county;
  }

  /**
   * @param county county or {@code null} for none
   */
  public UserAddress setCounty(java.lang.String county) {
    this.county = county;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getCrossStreet() {
    return crossStreet;
  }

  /**
   * @param crossStreet crossStreet or {@code null} for none
   */
  public UserAddress setCrossStreet(java.lang.String crossStreet) {
    this.crossStreet = crossStreet;
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
  public UserAddress setDefault(java.lang.Boolean default__) {
    this.default__ = default__;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getFullAddress() {
    return fullAddress;
  }

  /**
   * @param fullAddress fullAddress or {@code null} for none
   */
  public UserAddress setFullAddress(java.lang.String fullAddress) {
    this.fullAddress = fullAddress;
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
  public UserAddress setId(java.lang.Long id) {
    this.id = id;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getIsocountryCode() {
    return isocountryCode;
  }

  /**
   * @param isocountryCode isocountryCode or {@code null} for none
   */
  public UserAddress setIsocountryCode(java.lang.String isocountryCode) {
    this.isocountryCode = isocountryCode;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Double getLat() {
    return lat;
  }

  /**
   * @param lat lat or {@code null} for none
   */
  public UserAddress setLat(java.lang.Double lat) {
    this.lat = lat;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Double getLng() {
    return lng;
  }

  /**
   * @param lng lng or {@code null} for none
   */
  public UserAddress setLng(java.lang.Double lng) {
    this.lng = lng;
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
  public UserAddress setName(java.lang.String name) {
    this.name = name;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getNeighbourhood() {
    return neighbourhood;
  }

  /**
   * @param neighbourhood neighbourhood or {@code null} for none
   */
  public UserAddress setNeighbourhood(java.lang.String neighbourhood) {
    this.neighbourhood = neighbourhood;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getNotes() {
    return notes;
  }

  /**
   * @param notes notes or {@code null} for none
   */
  public UserAddress setNotes(java.lang.String notes) {
    this.notes = notes;
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
  public UserAddress setState(java.lang.String state) {
    this.state = state;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getStreet() {
    return street;
  }

  /**
   * @param street street or {@code null} for none
   */
  public UserAddress setStreet(java.lang.String street) {
    this.street = street;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getStreetNumber() {
    return streetNumber;
  }

  /**
   * @param streetNumber streetNumber or {@code null} for none
   */
  public UserAddress setStreetNumber(java.lang.String streetNumber) {
    this.streetNumber = streetNumber;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getTitle() {
    return title;
  }

  /**
   * @param title title or {@code null} for none
   */
  public UserAddress setTitle(java.lang.String title) {
    this.title = title;
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
  public UserAddress setUserId(java.lang.Long userId) {
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
  public UserAddress setZip(java.lang.String zip) {
    this.zip = zip;
    return this;
  }

  @Override
  public UserAddress set(String fieldName, Object value) {
    return (UserAddress) super.set(fieldName, value);
  }

  @Override
  public UserAddress clone() {
    return (UserAddress) super.clone();
  }

}
