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
 * Model definition for PlaceOrderArguments.
 *
 * <p> This is the Java data model class that specifies how to parse/serialize into the JSON that is
 * transmitted over HTTP when working with the storeendpoint. For a detailed explanation see:
 * <a href="http://code.google.com/p/google-http-java-client/wiki/JSON">http://code.google.com/p/google-http-java-client/wiki/JSON</a>
 * </p>
 *
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public final class PlaceOrderArguments extends com.google.api.client.json.GenericJson {

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String cardid;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String customerId;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private StoreOrder order;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String strOrder;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String token;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key @com.google.api.client.json.JsonString
  private java.lang.Long userId;

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getCardid() {
    return cardid;
  }

  /**
   * @param cardid cardid or {@code null} for none
   */
  public PlaceOrderArguments setCardid(java.lang.String cardid) {
    this.cardid = cardid;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getCustomerId() {
    return customerId;
  }

  /**
   * @param customerId customerId or {@code null} for none
   */
  public PlaceOrderArguments setCustomerId(java.lang.String customerId) {
    this.customerId = customerId;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public StoreOrder getOrder() {
    return order;
  }

  /**
   * @param order order or {@code null} for none
   */
  public PlaceOrderArguments setOrder(StoreOrder order) {
    this.order = order;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getStrOrder() {
    return strOrder;
  }

  /**
   * @param strOrder strOrder or {@code null} for none
   */
  public PlaceOrderArguments setStrOrder(java.lang.String strOrder) {
    this.strOrder = strOrder;
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
  public PlaceOrderArguments setToken(java.lang.String token) {
    this.token = token;
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
  public PlaceOrderArguments setUserId(java.lang.Long userId) {
    this.userId = userId;
    return this;
  }

  @Override
  public PlaceOrderArguments set(String fieldName, Object value) {
    return (PlaceOrderArguments) super.set(fieldName, value);
  }

  @Override
  public PlaceOrderArguments clone() {
    return (PlaceOrderArguments) super.clone();
  }

}
