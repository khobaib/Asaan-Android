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
 * (build: 2015-05-05 20:00:12 UTC)
 * on 2015-05-09 at 02:30:03 UTC 
 * Modify at your own risk.
 */

package com.asaan.server.com.asaan.server.endpoint.storeendpoint.model;

/**
 * Model definition for OrderAndReviews.
 *
 * <p> This is the Java data model class that specifies how to parse/serialize into the JSON that is
 * transmitted over HTTP when working with the storeendpoint. For a detailed explanation see:
 * <a href="http://code.google.com/p/google-http-java-client/wiki/JSON">http://code.google.com/p/google-http-java-client/wiki/JSON</a>
 * </p>
 *
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public final class OrderAndReviews extends com.google.api.client.json.GenericJson {

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private StoreOrder order;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private OrderReviewAndItemReviews orderAndItemsReview;

  /**
   * @return value or {@code null} for none
   */
  public StoreOrder getOrder() {
    return order;
  }

  /**
   * @param order order or {@code null} for none
   */
  public OrderAndReviews setOrder(StoreOrder order) {
    this.order = order;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public OrderReviewAndItemReviews getOrderAndItemsReview() {
    return orderAndItemsReview;
  }

  /**
   * @param orderAndItemsReview orderAndItemsReview or {@code null} for none
   */
  public OrderAndReviews setOrderAndItemsReview(OrderReviewAndItemReviews orderAndItemsReview) {
    this.orderAndItemsReview = orderAndItemsReview;
    return this;
  }

  @Override
  public OrderAndReviews set(String fieldName, Object value) {
    return (OrderAndReviews) super.set(fieldName, value);
  }

  @Override
  public OrderAndReviews clone() {
    return (OrderAndReviews) super.clone();
  }

}
