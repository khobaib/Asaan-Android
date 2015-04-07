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
 * on 2015-04-04 at 16:51:05 UTC 
 * Modify at your own risk.
 */

package com.asaan.server.com.asaan.server.endpoint.storeendpoint.model;

/**
 * Model definition for StoreDiscount.
 *
 * <p> This is the Java data model class that specifies how to parse/serialize into the JSON that is
 * transmitted over HTTP when working with the storeendpoint. For a detailed explanation see:
 * <a href="http://code.google.com/p/google-http-java-client/wiki/JSON">http://code.google.com/p/google-http-java-client/wiki/JSON</a>
 * </p>
 *
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public final class StoreDiscount extends com.google.api.client.json.GenericJson {

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String code;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String dates;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String daysOfWeek;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key @com.google.api.client.json.JsonString
  private java.lang.Long id;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Boolean percentOrAmount;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key @com.google.api.client.json.JsonString
  private java.lang.Long posDiscountId;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key @com.google.api.client.json.JsonString
  private java.lang.Long storeId;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String times;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String title;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key @com.google.api.client.json.JsonString
  private java.lang.Long value;

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getCode() {
    return code;
  }

  /**
   * @param code code or {@code null} for none
   */
  public StoreDiscount setCode(java.lang.String code) {
    this.code = code;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getDates() {
    return dates;
  }

  /**
   * @param dates dates or {@code null} for none
   */
  public StoreDiscount setDates(java.lang.String dates) {
    this.dates = dates;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getDaysOfWeek() {
    return daysOfWeek;
  }

  /**
   * @param daysOfWeek daysOfWeek or {@code null} for none
   */
  public StoreDiscount setDaysOfWeek(java.lang.String daysOfWeek) {
    this.daysOfWeek = daysOfWeek;
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
  public StoreDiscount setId(java.lang.Long id) {
    this.id = id;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Boolean getPercentOrAmount() {
    return percentOrAmount;
  }

  /**
   * @param percentOrAmount percentOrAmount or {@code null} for none
   */
  public StoreDiscount setPercentOrAmount(java.lang.Boolean percentOrAmount) {
    this.percentOrAmount = percentOrAmount;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Long getPosDiscountId() {
    return posDiscountId;
  }

  /**
   * @param posDiscountId posDiscountId or {@code null} for none
   */
  public StoreDiscount setPosDiscountId(java.lang.Long posDiscountId) {
    this.posDiscountId = posDiscountId;
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
  public StoreDiscount setStoreId(java.lang.Long storeId) {
    this.storeId = storeId;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getTimes() {
    return times;
  }

  /**
   * @param times times or {@code null} for none
   */
  public StoreDiscount setTimes(java.lang.String times) {
    this.times = times;
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
  public StoreDiscount setTitle(java.lang.String title) {
    this.title = title;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Long getValue() {
    return value;
  }

  /**
   * @param value value or {@code null} for none
   */
  public StoreDiscount setValue(java.lang.Long value) {
    this.value = value;
    return this;
  }

  @Override
  public StoreDiscount set(String fieldName, Object value) {
    return (StoreDiscount) super.set(fieldName, value);
  }

  @Override
  public StoreDiscount clone() {
    return (StoreDiscount) super.clone();
  }

}
