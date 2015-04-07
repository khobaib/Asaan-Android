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
 * Model definition for StoreWaitListQueueAndPosition.
 *
 * <p> This is the Java data model class that specifies how to parse/serialize into the JSON that is
 * transmitted over HTTP when working with the storeendpoint. For a detailed explanation see:
 * <a href="http://code.google.com/p/google-http-java-client/wiki/JSON">http://code.google.com/p/google-http-java-client/wiki/JSON</a>
 * </p>
 *
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public final class StoreWaitListQueueAndPosition extends com.google.api.client.json.GenericJson {

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key @com.google.api.client.json.JsonString
  private java.lang.Long partiesBeforeEntry;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private StoreWaitListQueue queueEntry;

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Long getPartiesBeforeEntry() {
    return partiesBeforeEntry;
  }

  /**
   * @param partiesBeforeEntry partiesBeforeEntry or {@code null} for none
   */
  public StoreWaitListQueueAndPosition setPartiesBeforeEntry(java.lang.Long partiesBeforeEntry) {
    this.partiesBeforeEntry = partiesBeforeEntry;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public StoreWaitListQueue getQueueEntry() {
    return queueEntry;
  }

  /**
   * @param queueEntry queueEntry or {@code null} for none
   */
  public StoreWaitListQueueAndPosition setQueueEntry(StoreWaitListQueue queueEntry) {
    this.queueEntry = queueEntry;
    return this;
  }

  @Override
  public StoreWaitListQueueAndPosition set(String fieldName, Object value) {
    return (StoreWaitListQueueAndPosition) super.set(fieldName, value);
  }

  @Override
  public StoreWaitListQueueAndPosition clone() {
    return (StoreWaitListQueueAndPosition) super.clone();
  }

}
