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
 * on 2014-11-05 at 21:22:19 UTC 
 * Modify at your own risk.
 */

package com.asaan.server.com.asaan.server.endpoint.storeendpoint.model;

/**
 * Model definition for StoreOrder.
 *
 * <p> This is the Java data model class that specifies how to parse/serialize into the JSON that is
 * transmitted over HTTP when working with the storeendpoint. For a detailed explanation see:
 * <a href="http://code.google.com/p/google-http-java-client/wiki/JSON">http://code.google.com/p/google-http-java-client/wiki/JSON</a>
 * </p>
 *
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public final class StoreOrder extends com.google.api.client.json.GenericJson {

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key @com.google.api.client.json.JsonString
  private java.lang.Long closeDate;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key @com.google.api.client.json.JsonString
  private java.lang.Long createdDate;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key @com.google.api.client.json.JsonString
  private java.lang.Long discount;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String discountDescription;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String employeeName;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Integer employeePOSId;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key @com.google.api.client.json.JsonString
  private java.lang.Long finalTotal;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Integer guestCount;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key @com.google.api.client.json.JsonString
  private java.lang.Long id;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key @com.google.api.client.json.JsonString
  private java.lang.Long modifiedDate;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String note;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String orderDetails;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Integer orderMode;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Integer poscheckId;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Integer posintCheckId;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key @com.google.api.client.json.JsonString
  private java.lang.Long serviceCharge;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String status;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key @com.google.api.client.json.JsonString
  private java.lang.Long storeId;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key @com.google.api.client.json.JsonString
  private java.lang.Long subTotal;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Integer tableNumber;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key @com.google.api.client.json.JsonString
  private java.lang.Long tax;

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Long getCloseDate() {
    return closeDate;
  }

  /**
   * @param closeDate closeDate or {@code null} for none
   */
  public StoreOrder setCloseDate(java.lang.Long closeDate) {
    this.closeDate = closeDate;
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
  public StoreOrder setCreatedDate(java.lang.Long createdDate) {
    this.createdDate = createdDate;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Long getDiscount() {
    return discount;
  }

  /**
   * @param discount discount or {@code null} for none
   */
  public StoreOrder setDiscount(java.lang.Long discount) {
    this.discount = discount;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getDiscountDescription() {
    return discountDescription;
  }

  /**
   * @param discountDescription discountDescription or {@code null} for none
   */
  public StoreOrder setDiscountDescription(java.lang.String discountDescription) {
    this.discountDescription = discountDescription;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getEmployeeName() {
    return employeeName;
  }

  /**
   * @param employeeName employeeName or {@code null} for none
   */
  public StoreOrder setEmployeeName(java.lang.String employeeName) {
    this.employeeName = employeeName;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Integer getEmployeePOSId() {
    return employeePOSId;
  }

  /**
   * @param employeePOSId employeePOSId or {@code null} for none
   */
  public StoreOrder setEmployeePOSId(java.lang.Integer employeePOSId) {
    this.employeePOSId = employeePOSId;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Long getFinalTotal() {
    return finalTotal;
  }

  /**
   * @param finalTotal finalTotal or {@code null} for none
   */
  public StoreOrder setFinalTotal(java.lang.Long finalTotal) {
    this.finalTotal = finalTotal;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Integer getGuestCount() {
    return guestCount;
  }

  /**
   * @param guestCount guestCount or {@code null} for none
   */
  public StoreOrder setGuestCount(java.lang.Integer guestCount) {
    this.guestCount = guestCount;
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
  public StoreOrder setId(java.lang.Long id) {
    this.id = id;
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
  public StoreOrder setModifiedDate(java.lang.Long modifiedDate) {
    this.modifiedDate = modifiedDate;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getNote() {
    return note;
  }

  /**
   * @param note note or {@code null} for none
   */
  public StoreOrder setNote(java.lang.String note) {
    this.note = note;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getOrderDetails() {
    return orderDetails;
  }

  /**
   * @param orderDetails orderDetails or {@code null} for none
   */
  public StoreOrder setOrderDetails(java.lang.String orderDetails) {
    this.orderDetails = orderDetails;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Integer getOrderMode() {
    return orderMode;
  }

  /**
   * @param orderMode orderMode or {@code null} for none
   */
  public StoreOrder setOrderMode(java.lang.Integer orderMode) {
    this.orderMode = orderMode;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Integer getPoscheckId() {
    return poscheckId;
  }

  /**
   * @param poscheckId poscheckId or {@code null} for none
   */
  public StoreOrder setPoscheckId(java.lang.Integer poscheckId) {
    this.poscheckId = poscheckId;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Integer getPosintCheckId() {
    return posintCheckId;
  }

  /**
   * @param posintCheckId posintCheckId or {@code null} for none
   */
  public StoreOrder setPosintCheckId(java.lang.Integer posintCheckId) {
    this.posintCheckId = posintCheckId;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Long getServiceCharge() {
    return serviceCharge;
  }

  /**
   * @param serviceCharge serviceCharge or {@code null} for none
   */
  public StoreOrder setServiceCharge(java.lang.Long serviceCharge) {
    this.serviceCharge = serviceCharge;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getStatus() {
    return status;
  }

  /**
   * @param status status or {@code null} for none
   */
  public StoreOrder setStatus(java.lang.String status) {
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
  public StoreOrder setStoreId(java.lang.Long storeId) {
    this.storeId = storeId;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Long getSubTotal() {
    return subTotal;
  }

  /**
   * @param subTotal subTotal or {@code null} for none
   */
  public StoreOrder setSubTotal(java.lang.Long subTotal) {
    this.subTotal = subTotal;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Integer getTableNumber() {
    return tableNumber;
  }

  /**
   * @param tableNumber tableNumber or {@code null} for none
   */
  public StoreOrder setTableNumber(java.lang.Integer tableNumber) {
    this.tableNumber = tableNumber;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Long getTax() {
    return tax;
  }

  /**
   * @param tax tax or {@code null} for none
   */
  public StoreOrder setTax(java.lang.Long tax) {
    this.tax = tax;
    return this;
  }

  @Override
  public StoreOrder set(String fieldName, Object value) {
    return (StoreOrder) super.set(fieldName, value);
  }

  @Override
  public StoreOrder clone() {
    return (StoreOrder) super.clone();
  }

}
