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
 * Model definition for ItemReview.
 *
 * <p> This is the Java data model class that specifies how to parse/serialize into the JSON that is
 * transmitted over HTTP when working with the storeendpoint. For a detailed explanation see:
 * <a href="http://code.google.com/p/google-http-java-client/wiki/JSON">http://code.google.com/p/google-http-java-client/wiki/JSON</a>
 * </p>
 *
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public final class ItemReview extends com.google.api.client.json.GenericJson {

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key @com.google.api.client.json.JsonString
  private java.lang.Long createdDate;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key @com.google.api.client.json.JsonString
  private java.lang.Long id;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Integer itemLike;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String itemName;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Integer menuItemPOSId;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key @com.google.api.client.json.JsonString
  private java.lang.Long modifiedDate;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key @com.google.api.client.json.JsonString
  private java.lang.Long orderId;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key @com.google.api.client.json.JsonString
  private java.lang.Long storeId;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key @com.google.api.client.json.JsonString
  private java.lang.Long userId;

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Long getCreatedDate() {
    return createdDate;
  }

  /**
   * @param createdDate createdDate or {@code null} for none
   */
  public ItemReview setCreatedDate(java.lang.Long createdDate) {
    this.createdDate = createdDate;
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
  public ItemReview setId(java.lang.Long id) {
    this.id = id;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Integer getItemLike() {
    return itemLike;
  }

  /**
   * @param itemLike itemLike or {@code null} for none
   */
  public ItemReview setItemLike(java.lang.Integer itemLike) {
    this.itemLike = itemLike;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getItemName() {
    return itemName;
  }

  /**
   * @param itemName itemName or {@code null} for none
   */
  public ItemReview setItemName(java.lang.String itemName) {
    this.itemName = itemName;
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
  public ItemReview setMenuItemPOSId(java.lang.Integer menuItemPOSId) {
    this.menuItemPOSId = menuItemPOSId;
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
  public ItemReview setModifiedDate(java.lang.Long modifiedDate) {
    this.modifiedDate = modifiedDate;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Long getOrderId() {
    return orderId;
  }

  /**
   * @param orderId orderId or {@code null} for none
   */
  public ItemReview setOrderId(java.lang.Long orderId) {
    this.orderId = orderId;
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
  public ItemReview setStoreId(java.lang.Long storeId) {
    this.storeId = storeId;
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
  public ItemReview setUserId(java.lang.Long userId) {
    this.userId = userId;
    return this;
  }

  @Override
  public ItemReview set(String fieldName, Object value) {
    return (ItemReview) super.set(fieldName, value);
  }

  @Override
  public ItemReview clone() {
    return (ItemReview) super.clone();
  }

}
