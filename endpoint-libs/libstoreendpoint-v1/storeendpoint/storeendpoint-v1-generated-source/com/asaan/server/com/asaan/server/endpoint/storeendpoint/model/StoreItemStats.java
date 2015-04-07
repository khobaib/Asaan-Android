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
 * Model definition for StoreItemStats.
 *
 * <p> This is the Java data model class that specifies how to parse/serialize into the JSON that is
 * transmitted over HTTP when working with the storeendpoint. For a detailed explanation see:
 * <a href="http://code.google.com/p/google-http-java-client/wiki/JSON">http://code.google.com/p/google-http-java-client/wiki/JSON</a>
 * </p>
 *
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public final class StoreItemStats extends com.google.api.client.json.GenericJson {

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key @com.google.api.client.json.JsonString
  private java.lang.Long dislikes;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key @com.google.api.client.json.JsonString
  private java.lang.Long id;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key @com.google.api.client.json.JsonString
  private java.lang.Long likes;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Integer menuItemPOSId;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key @com.google.api.client.json.JsonString
  private java.lang.Long orders;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key @com.google.api.client.json.JsonString
  private java.lang.Long recommends;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key @com.google.api.client.json.JsonString
  private java.lang.Long storeId;

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Long getDislikes() {
    return dislikes;
  }

  /**
   * @param dislikes dislikes or {@code null} for none
   */
  public StoreItemStats setDislikes(java.lang.Long dislikes) {
    this.dislikes = dislikes;
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
  public StoreItemStats setId(java.lang.Long id) {
    this.id = id;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Long getLikes() {
    return likes;
  }

  /**
   * @param likes likes or {@code null} for none
   */
  public StoreItemStats setLikes(java.lang.Long likes) {
    this.likes = likes;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Integer getMenuItemPOSId() {
    return menuItemPOSId;
  }

  /**
   * @param menuItemPOSId menuItemPOSId or {@code null} for none
   */
  public StoreItemStats setMenuItemPOSId(java.lang.Integer menuItemPOSId) {
    this.menuItemPOSId = menuItemPOSId;
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
  public StoreItemStats setOrders(java.lang.Long orders) {
    this.orders = orders;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Long getRecommends() {
    return recommends;
  }

  /**
   * @param recommends recommends or {@code null} for none
   */
  public StoreItemStats setRecommends(java.lang.Long recommends) {
    this.recommends = recommends;
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
  public StoreItemStats setStoreId(java.lang.Long storeId) {
    this.storeId = storeId;
    return this;
  }

  @Override
  public StoreItemStats set(String fieldName, Object value) {
    return (StoreItemStats) super.set(fieldName, value);
  }

  @Override
  public StoreItemStats clone() {
    return (StoreItemStats) super.clone();
  }

}
