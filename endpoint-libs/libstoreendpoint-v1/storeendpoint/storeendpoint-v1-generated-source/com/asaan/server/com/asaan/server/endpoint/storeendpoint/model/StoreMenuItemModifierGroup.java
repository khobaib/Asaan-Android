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
 * Model definition for StoreMenuItemModifierGroup.
 *
 * <p> This is the Java data model class that specifies how to parse/serialize into the JSON that is
 * transmitted over HTTP when working with the storeendpoint. For a detailed explanation see:
 * <a href="http://code.google.com/p/google-http-java-client/wiki/JSON">http://code.google.com/p/google-http-java-client/wiki/JSON</a>
 * </p>
 *
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public final class StoreMenuItemModifierGroup extends com.google.api.client.json.GenericJson {

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
  @com.google.api.client.util.Key @com.google.api.client.json.JsonString
  private java.lang.Long menuItemPOSId;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key @com.google.api.client.json.JsonString
  private java.lang.Long modifiedDate;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key @com.google.api.client.json.JsonString
  private java.lang.Long modifierGroupPOSId;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key @com.google.api.client.json.JsonString
  private java.lang.Long storeId;

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Long getCreatedDate() {
    return createdDate;
  }

  /**
   * @param createdDate createdDate or {@code null} for none
   */
  public StoreMenuItemModifierGroup setCreatedDate(java.lang.Long createdDate) {
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
  public StoreMenuItemModifierGroup setId(java.lang.Long id) {
    this.id = id;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Long getMenuItemPOSId() {
    return menuItemPOSId;
  }

  /**
   * @param menuItemPOSId menuItemPOSId or {@code null} for none
   */
  public StoreMenuItemModifierGroup setMenuItemPOSId(java.lang.Long menuItemPOSId) {
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
  public StoreMenuItemModifierGroup setModifiedDate(java.lang.Long modifiedDate) {
    this.modifiedDate = modifiedDate;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Long getModifierGroupPOSId() {
    return modifierGroupPOSId;
  }

  /**
   * @param modifierGroupPOSId modifierGroupPOSId or {@code null} for none
   */
  public StoreMenuItemModifierGroup setModifierGroupPOSId(java.lang.Long modifierGroupPOSId) {
    this.modifierGroupPOSId = modifierGroupPOSId;
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
  public StoreMenuItemModifierGroup setStoreId(java.lang.Long storeId) {
    this.storeId = storeId;
    return this;
  }

  @Override
  public StoreMenuItemModifierGroup set(String fieldName, Object value) {
    return (StoreMenuItemModifierGroup) super.set(fieldName, value);
  }

  @Override
  public StoreMenuItemModifierGroup clone() {
    return (StoreMenuItemModifierGroup) super.clone();
  }

}
