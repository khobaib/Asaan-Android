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
 * on 2015-03-20 at 19:47:10 UTC 
 * Modify at your own risk.
 */

package com.asaan.server.com.asaan.server.endpoint.storeendpoint.model;

/**
 * Model definition for StoreStats.
 *
 * <p> This is the Java data model class that specifies how to parse/serialize into the JSON that is
 * transmitted over HTTP when working with the storeendpoint. For a detailed explanation see:
 * <a href="http://code.google.com/p/google-http-java-client/wiki/JSON">http://code.google.com/p/google-http-java-client/wiki/JSON</a>
 * </p>
 *
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public final class StoreStats extends com.google.api.client.json.GenericJson {

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key @com.google.api.client.json.JsonString
  private java.lang.Long createdDate;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Integer daysOfService;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key @com.google.api.client.json.JsonString
  private java.lang.Long foodDislikes;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key @com.google.api.client.json.JsonString
  private java.lang.Long foodLikes;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key @com.google.api.client.json.JsonString
  private java.lang.Long lastOrderPlacedDate;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key @com.google.api.client.json.JsonString
  private java.lang.Long modifiedDate;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key @com.google.api.client.json.JsonString
  private java.lang.Long orders;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key @com.google.api.client.json.JsonString
  private java.lang.Long recommendations;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key @com.google.api.client.json.JsonString
  private java.lang.Long serviceDislikes;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key @com.google.api.client.json.JsonString
  private java.lang.Long serviceLikes;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key @com.google.api.client.json.JsonString
  private java.lang.Long storeId;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key @com.google.api.client.json.JsonString
  private java.lang.Long visits;

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Long getCreatedDate() {
    return createdDate;
  }

  /**
   * @param createdDate createdDate or {@code null} for none
   */
  public StoreStats setCreatedDate(java.lang.Long createdDate) {
    this.createdDate = createdDate;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Integer getDaysOfService() {
    return daysOfService;
  }

  /**
   * @param daysOfService daysOfService or {@code null} for none
   */
  public StoreStats setDaysOfService(java.lang.Integer daysOfService) {
    this.daysOfService = daysOfService;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Long getFoodDislikes() {
    return foodDislikes;
  }

  /**
   * @param foodDislikes foodDislikes or {@code null} for none
   */
  public StoreStats setFoodDislikes(java.lang.Long foodDislikes) {
    this.foodDislikes = foodDislikes;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Long getFoodLikes() {
    return foodLikes;
  }

  /**
   * @param foodLikes foodLikes or {@code null} for none
   */
  public StoreStats setFoodLikes(java.lang.Long foodLikes) {
    this.foodLikes = foodLikes;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Long getLastOrderPlacedDate() {
    return lastOrderPlacedDate;
  }

  /**
   * @param lastOrderPlacedDate lastOrderPlacedDate or {@code null} for none
   */
  public StoreStats setLastOrderPlacedDate(java.lang.Long lastOrderPlacedDate) {
    this.lastOrderPlacedDate = lastOrderPlacedDate;
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
  public StoreStats setModifiedDate(java.lang.Long modifiedDate) {
    this.modifiedDate = modifiedDate;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Long getOrders() {
    return orders;
  }

  /**
   * @param orders orders or {@code null} for none
   */
  public StoreStats setOrders(java.lang.Long orders) {
    this.orders = orders;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Long getRecommendations() {
    return recommendations;
  }

  /**
   * @param recommendations recommendations or {@code null} for none
   */
  public StoreStats setRecommendations(java.lang.Long recommendations) {
    this.recommendations = recommendations;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Long getServiceDislikes() {
    return serviceDislikes;
  }

  /**
   * @param serviceDislikes serviceDislikes or {@code null} for none
   */
  public StoreStats setServiceDislikes(java.lang.Long serviceDislikes) {
    this.serviceDislikes = serviceDislikes;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Long getServiceLikes() {
    return serviceLikes;
  }

  /**
   * @param serviceLikes serviceLikes or {@code null} for none
   */
  public StoreStats setServiceLikes(java.lang.Long serviceLikes) {
    this.serviceLikes = serviceLikes;
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
  public StoreStats setStoreId(java.lang.Long storeId) {
    this.storeId = storeId;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Long getVisits() {
    return visits;
  }

  /**
   * @param visits visits or {@code null} for none
   */
  public StoreStats setVisits(java.lang.Long visits) {
    this.visits = visits;
    return this;
  }

  @Override
  public StoreStats set(String fieldName, Object value) {
    return (StoreStats) super.set(fieldName, value);
  }

  @Override
  public StoreStats clone() {
    return (StoreStats) super.clone();
  }

}
