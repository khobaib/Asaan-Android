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
  private java.lang.String address1;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String address2;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String address3;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String city;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String crossStreet;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key @com.google.api.client.json.JsonString
  private java.lang.Long id;

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
  public java.lang.String getAddress1() {
    return address1;
  }

  /**
   * @param address1 address1 or {@code null} for none
   */
  public UserAddress setAddress1(java.lang.String address1) {
    this.address1 = address1;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getAddress2() {
    return address2;
  }

  /**
   * @param address2 address2 or {@code null} for none
   */
  public UserAddress setAddress2(java.lang.String address2) {
    this.address2 = address2;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getAddress3() {
    return address3;
  }

  /**
   * @param address3 address3 or {@code null} for none
   */
  public UserAddress setAddress3(java.lang.String address3) {
    this.address3 = address3;
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
