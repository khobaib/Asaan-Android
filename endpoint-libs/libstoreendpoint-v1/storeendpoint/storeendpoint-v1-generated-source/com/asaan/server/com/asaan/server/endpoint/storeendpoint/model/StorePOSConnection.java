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
 * (build: 2015-03-26 20:30:19 UTC)
 * on 2015-04-07 at 04:40:21 UTC 
 * Modify at your own risk.
 */

package com.asaan.server.com.asaan.server.endpoint.storeendpoint.model;

/**
 * Model definition for StorePOSConnection.
 *
 * <p> This is the Java data model class that specifies how to parse/serialize into the JSON that is
 * transmitted over HTTP when working with the storeendpoint. For a detailed explanation see:
 * <a href="http://code.google.com/p/google-http-java-client/wiki/JSON">http://code.google.com/p/google-http-java-client/wiki/JSON</a>
 * </p>
 *
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public final class StorePOSConnection extends com.google.api.client.json.GenericJson {

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Integer asaanTenderId;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Integer carryoutModeId;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key @com.google.api.client.json.JsonString
  private java.lang.Long createdDate;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Integer deliveryModeId;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Integer dineInModeId;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String fallbackEmail;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String fallbackFax;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String hiddenHeaderName;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String hiddenHeaderValue;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key @com.google.api.client.json.JsonString
  private java.lang.Long modifiedDate;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Integer preOrderModeId;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key @com.google.api.client.json.JsonString
  private java.lang.Long status;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key @com.google.api.client.json.JsonString
  private java.lang.Long storeId;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String token;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String url;

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Integer getAsaanTenderId() {
    return asaanTenderId;
  }

  /**
   * @param asaanTenderId asaanTenderId or {@code null} for none
   */
  public StorePOSConnection setAsaanTenderId(java.lang.Integer asaanTenderId) {
    this.asaanTenderId = asaanTenderId;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Integer getCarryoutModeId() {
    return carryoutModeId;
  }

  /**
   * @param carryoutModeId carryoutModeId or {@code null} for none
   */
  public StorePOSConnection setCarryoutModeId(java.lang.Integer carryoutModeId) {
    this.carryoutModeId = carryoutModeId;
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
  public StorePOSConnection setCreatedDate(java.lang.Long createdDate) {
    this.createdDate = createdDate;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Integer getDeliveryModeId() {
    return deliveryModeId;
  }

  /**
   * @param deliveryModeId deliveryModeId or {@code null} for none
   */
  public StorePOSConnection setDeliveryModeId(java.lang.Integer deliveryModeId) {
    this.deliveryModeId = deliveryModeId;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Integer getDineInModeId() {
    return dineInModeId;
  }

  /**
   * @param dineInModeId dineInModeId or {@code null} for none
   */
  public StorePOSConnection setDineInModeId(java.lang.Integer dineInModeId) {
    this.dineInModeId = dineInModeId;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getFallbackEmail() {
    return fallbackEmail;
  }

  /**
   * @param fallbackEmail fallbackEmail or {@code null} for none
   */
  public StorePOSConnection setFallbackEmail(java.lang.String fallbackEmail) {
    this.fallbackEmail = fallbackEmail;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getFallbackFax() {
    return fallbackFax;
  }

  /**
   * @param fallbackFax fallbackFax or {@code null} for none
   */
  public StorePOSConnection setFallbackFax(java.lang.String fallbackFax) {
    this.fallbackFax = fallbackFax;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getHiddenHeaderName() {
    return hiddenHeaderName;
  }

  /**
   * @param hiddenHeaderName hiddenHeaderName or {@code null} for none
   */
  public StorePOSConnection setHiddenHeaderName(java.lang.String hiddenHeaderName) {
    this.hiddenHeaderName = hiddenHeaderName;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getHiddenHeaderValue() {
    return hiddenHeaderValue;
  }

  /**
   * @param hiddenHeaderValue hiddenHeaderValue or {@code null} for none
   */
  public StorePOSConnection setHiddenHeaderValue(java.lang.String hiddenHeaderValue) {
    this.hiddenHeaderValue = hiddenHeaderValue;
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
  public StorePOSConnection setModifiedDate(java.lang.Long modifiedDate) {
    this.modifiedDate = modifiedDate;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Integer getPreOrderModeId() {
    return preOrderModeId;
  }

  /**
   * @param preOrderModeId preOrderModeId or {@code null} for none
   */
  public StorePOSConnection setPreOrderModeId(java.lang.Integer preOrderModeId) {
    this.preOrderModeId = preOrderModeId;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Long getStatus() {
    return status;
  }

  /**
   * @param status status or {@code null} for none
   */
  public StorePOSConnection setStatus(java.lang.Long status) {
    this.status = status;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Long getStoreId() {
    return storeId;
  }

  /**
   * @param storeId storeId or {@code null} for none
   */
  public StorePOSConnection setStoreId(java.lang.Long storeId) {
    this.storeId = storeId;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getToken() {
    return token;
  }

  /**
   * @param token token or {@code null} for none
   */
  public StorePOSConnection setToken(java.lang.String token) {
    this.token = token;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getUrl() {
    return url;
  }

  /**
   * @param url url or {@code null} for none
   */
  public StorePOSConnection setUrl(java.lang.String url) {
    this.url = url;
    return this;
  }

  @Override
  public StorePOSConnection set(String fieldName, Object value) {
    return (StorePOSConnection) super.set(fieldName, value);
  }

  @Override
  public StorePOSConnection clone() {
    return (StorePOSConnection) super.clone();
  }

}
