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
 * on 2015-05-09 at 02:30:10 UTC 
 * Modify at your own risk.
 */

package com.asaan.server.com.asaan.server.endpoint.userendpoint.model;

/**
 * Model definition for UserCardCollection.
 *
 * <p> This is the Java data model class that specifies how to parse/serialize into the JSON that is
 * transmitted over HTTP when working with the userendpoint. For a detailed explanation see:
 * <a href="http://code.google.com/p/google-http-java-client/wiki/JSON">http://code.google.com/p/google-http-java-client/wiki/JSON</a>
 * </p>
 *
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public final class UserCardCollection extends com.google.api.client.json.GenericJson {

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.util.List<UserCard> items;

  /**
   * @return value or {@code null} for none
   */
  public java.util.List<UserCard> getItems() {
    return items;
  }

  /**
   * @param items items or {@code null} for none
   */
  public UserCardCollection setItems(java.util.List<UserCard> items) {
    this.items = items;
    return this;
  }

  @Override
  public UserCardCollection set(String fieldName, Object value) {
    return (UserCardCollection) super.set(fieldName, value);
  }

  @Override
  public UserCardCollection clone() {
    return (UserCardCollection) super.clone();
  }

}
