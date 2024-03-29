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
 * (build: 2014-07-22 21:53:01 UTC)
 * on 2014-10-28 at 02:45:33 UTC 
 * Modify at your own risk.
 */

package com.asaan.server.com.asaan.server.endpoint.storeendpoint.model;

/**
 * Model definition for StoreMenuHierarchy.
 *
 * <p> This is the Java data model class that specifies how to parse/serialize into the JSON that is
 * transmitted over HTTP when working with the storeendpoint. For a detailed explanation see:
 * <a href="http://code.google.com/p/google-http-java-client/wiki/JSON">http://code.google.com/p/google-http-java-client/wiki/JSON</a>
 * </p>
 *
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public final class StoreMenuHierarchy extends com.google.api.client.json.GenericJson {

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Boolean active;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key @com.google.api.client.json.JsonString
  private java.lang.Long createdDate;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String hours;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key @com.google.api.client.json.JsonString
  private java.lang.Long id;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key @com.google.api.client.json.JsonString
  private java.lang.Long level;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key @com.google.api.client.json.JsonString
  private java.lang.Long menuItemCount;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key @com.google.api.client.json.JsonString
  private java.lang.Long menuItemPosition;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key @com.google.api.client.json.JsonString
  private java.lang.Long menuPOSId;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key @com.google.api.client.json.JsonString
  private java.lang.Long menuType;

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
  @com.google.api.client.util.Key @com.google.api.client.json.JsonString
  private java.lang.Long storeId;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key @com.google.api.client.json.JsonString
  private java.lang.Long subMenuPOSId;

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Boolean getActive() {
    return active;
  }

  /**
   * @param active active or {@code null} for none
   */
  public StoreMenuHierarchy setActive(java.lang.Boolean active) {
    this.active = active;
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
  public StoreMenuHierarchy setCreatedDate(java.lang.Long createdDate) {
    this.createdDate = createdDate;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getHours() {
    return hours;
  }

  /**
   * @param hours hours or {@code null} for none
   */
  public StoreMenuHierarchy setHours(java.lang.String hours) {
    this.hours = hours;
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
  public StoreMenuHierarchy setId(java.lang.Long id) {
    this.id = id;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Long getLevel() {
    return level;
  }

  /**
   * @param level level or {@code null} for none
   */
  public StoreMenuHierarchy setLevel(java.lang.Long level) {
    this.level = level;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Long getMenuItemCount() {
    return menuItemCount;
  }

  /**
   * @param menuItemCount menuItemCount or {@code null} for none
   */
  public StoreMenuHierarchy setMenuItemCount(java.lang.Long menuItemCount) {
    this.menuItemCount = menuItemCount;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Long getMenuItemPosition() {
    return menuItemPosition;
  }

  /**
   * @param menuItemPosition menuItemPosition or {@code null} for none
   */
  public StoreMenuHierarchy setMenuItemPosition(java.lang.Long menuItemPosition) {
    this.menuItemPosition = menuItemPosition;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Long getMenuPOSId() {
    return menuPOSId;
  }

  /**
   * @param menuPOSId menuPOSId or {@code null} for none
   */
  public StoreMenuHierarchy setMenuPOSId(java.lang.Long menuPOSId) {
    this.menuPOSId = menuPOSId;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Long getMenuType() {
    return menuType;
  }

  /**
   * @param menuType menuType or {@code null} for none
   */
  public StoreMenuHierarchy setMenuType(java.lang.Long menuType) {
    this.menuType = menuType;
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
  public StoreMenuHierarchy setModifiedDate(java.lang.Long modifiedDate) {
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
  public StoreMenuHierarchy setName(java.lang.String name) {
    this.name = name;
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
  public StoreMenuHierarchy setStoreId(java.lang.Long storeId) {
    this.storeId = storeId;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Long getSubMenuPOSId() {
    return subMenuPOSId;
  }

  /**
   * @param subMenuPOSId subMenuPOSId or {@code null} for none
   */
  public StoreMenuHierarchy setSubMenuPOSId(java.lang.Long subMenuPOSId) {
    this.subMenuPOSId = subMenuPOSId;
    return this;
  }

  @Override
  public StoreMenuHierarchy set(String fieldName, Object value) {
    return (StoreMenuHierarchy) super.set(fieldName, value);
  }

  @Override
  public StoreMenuHierarchy clone() {
    return (StoreMenuHierarchy) super.clone();
  }

}
