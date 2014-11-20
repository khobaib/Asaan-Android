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
 * (build: 2014-11-17 18:43:33 UTC)
 * on 2014-11-19 at 22:20:28 UTC 
 * Modify at your own risk.
 */

package com.asaan.server.com.asaan.server.endpoint.storeendpoint.model;

/**
 * Model definition for Store.
 *
 * <p> This is the Java data model class that specifies how to parse/serialize into the JSON that is
 * transmitted over HTTP when working with the storeendpoint. For a detailed explanation see:
 * <a href="http://code.google.com/p/google-http-java-client/wiki/JSON">http://code.google.com/p/google-http-java-client/wiki/JSON</a>
 * </p>
 *
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public final class Store extends com.google.api.client.json.GenericJson {

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String address;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String backgroundImageUrl;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String backgroundThumbnailUrl;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key @com.google.api.client.json.JsonString
  private java.lang.Long beaconId;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String bssid;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String city;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key @com.google.api.client.json.JsonString
  private java.lang.Long createdDate;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Integer deliveryDistance;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String description;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String fbUrl;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String gplusUrl;

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
  @com.google.api.client.util.Key
  private java.lang.Boolean isActive;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Double lat;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Double lng;

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
  @com.google.api.client.util.Key
  private java.lang.String phone;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Integer priceRange;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Boolean providesCarryout;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Boolean providesDelivery;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String rewardsDescription;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Integer rewardsRate;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String ssid;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String state;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String subType;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.util.List<java.lang.String> trophies;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String twitterUrl;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String type;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String webSiteUrl;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String zip;

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getAddress() {
    return address;
  }

  /**
   * @param address address or {@code null} for none
   */
  public Store setAddress(java.lang.String address) {
    this.address = address;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getBackgroundImageUrl() {
    return backgroundImageUrl;
  }

  /**
   * @param backgroundImageUrl backgroundImageUrl or {@code null} for none
   */
  public Store setBackgroundImageUrl(java.lang.String backgroundImageUrl) {
    this.backgroundImageUrl = backgroundImageUrl;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getBackgroundThumbnailUrl() {
    return backgroundThumbnailUrl;
  }

  /**
   * @param backgroundThumbnailUrl backgroundThumbnailUrl or {@code null} for none
   */
  public Store setBackgroundThumbnailUrl(java.lang.String backgroundThumbnailUrl) {
    this.backgroundThumbnailUrl = backgroundThumbnailUrl;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Long getBeaconId() {
    return beaconId;
  }

  /**
   * @param beaconId beaconId or {@code null} for none
   */
  public Store setBeaconId(java.lang.Long beaconId) {
    this.beaconId = beaconId;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getBssid() {
    return bssid;
  }

  /**
   * @param bssid bssid or {@code null} for none
   */
  public Store setBssid(java.lang.String bssid) {
    this.bssid = bssid;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getCity() {
    return city;
  }

  /**
   * @param city city or {@code null} for none
   */
  public Store setCity(java.lang.String city) {
    this.city = city;
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
  public Store setCreatedDate(java.lang.Long createdDate) {
    this.createdDate = createdDate;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Integer getDeliveryDistance() {
    return deliveryDistance;
  }

  /**
   * @param deliveryDistance deliveryDistance or {@code null} for none
   */
  public Store setDeliveryDistance(java.lang.Integer deliveryDistance) {
    this.deliveryDistance = deliveryDistance;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getDescription() {
    return description;
  }

  /**
   * @param description description or {@code null} for none
   */
  public Store setDescription(java.lang.String description) {
    this.description = description;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getFbUrl() {
    return fbUrl;
  }

  /**
   * @param fbUrl fbUrl or {@code null} for none
   */
  public Store setFbUrl(java.lang.String fbUrl) {
    this.fbUrl = fbUrl;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getGplusUrl() {
    return gplusUrl;
  }

  /**
   * @param gplusUrl gplusUrl or {@code null} for none
   */
  public Store setGplusUrl(java.lang.String gplusUrl) {
    this.gplusUrl = gplusUrl;
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
  public Store setHours(java.lang.String hours) {
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
  public Store setId(java.lang.Long id) {
    this.id = id;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Boolean getIsActive() {
    return isActive;
  }

  /**
   * @param isActive isActive or {@code null} for none
   */
  public Store setIsActive(java.lang.Boolean isActive) {
    this.isActive = isActive;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Double getLat() {
    return lat;
  }

  /**
   * @param lat lat or {@code null} for none
   */
  public Store setLat(java.lang.Double lat) {
    this.lat = lat;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Double getLng() {
    return lng;
  }

  /**
   * @param lng lng or {@code null} for none
   */
  public Store setLng(java.lang.Double lng) {
    this.lng = lng;
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
  public Store setModifiedDate(java.lang.Long modifiedDate) {
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
  public Store setName(java.lang.String name) {
    this.name = name;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getPhone() {
    return phone;
  }

  /**
   * @param phone phone or {@code null} for none
   */
  public Store setPhone(java.lang.String phone) {
    this.phone = phone;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Integer getPriceRange() {
    return priceRange;
  }

  /**
   * @param priceRange priceRange or {@code null} for none
   */
  public Store setPriceRange(java.lang.Integer priceRange) {
    this.priceRange = priceRange;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Boolean getProvidesCarryout() {
    return providesCarryout;
  }

  /**
   * @param providesCarryout providesCarryout or {@code null} for none
   */
  public Store setProvidesCarryout(java.lang.Boolean providesCarryout) {
    this.providesCarryout = providesCarryout;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Boolean getProvidesDelivery() {
    return providesDelivery;
  }

  /**
   * @param providesDelivery providesDelivery or {@code null} for none
   */
  public Store setProvidesDelivery(java.lang.Boolean providesDelivery) {
    this.providesDelivery = providesDelivery;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getRewardsDescription() {
    return rewardsDescription;
  }

  /**
   * @param rewardsDescription rewardsDescription or {@code null} for none
   */
  public Store setRewardsDescription(java.lang.String rewardsDescription) {
    this.rewardsDescription = rewardsDescription;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Integer getRewardsRate() {
    return rewardsRate;
  }

  /**
   * @param rewardsRate rewardsRate or {@code null} for none
   */
  public Store setRewardsRate(java.lang.Integer rewardsRate) {
    this.rewardsRate = rewardsRate;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getSsid() {
    return ssid;
  }

  /**
   * @param ssid ssid or {@code null} for none
   */
  public Store setSsid(java.lang.String ssid) {
    this.ssid = ssid;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getState() {
    return state;
  }

  /**
   * @param state state or {@code null} for none
   */
  public Store setState(java.lang.String state) {
    this.state = state;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getSubType() {
    return subType;
  }

  /**
   * @param subType subType or {@code null} for none
   */
  public Store setSubType(java.lang.String subType) {
    this.subType = subType;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.util.List<java.lang.String> getTrophies() {
    return trophies;
  }

  /**
   * @param trophies trophies or {@code null} for none
   */
  public Store setTrophies(java.util.List<java.lang.String> trophies) {
    this.trophies = trophies;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getTwitterUrl() {
    return twitterUrl;
  }

  /**
   * @param twitterUrl twitterUrl or {@code null} for none
   */
  public Store setTwitterUrl(java.lang.String twitterUrl) {
    this.twitterUrl = twitterUrl;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getType() {
    return type;
  }

  /**
   * @param type type or {@code null} for none
   */
  public Store setType(java.lang.String type) {
    this.type = type;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getWebSiteUrl() {
    return webSiteUrl;
  }

  /**
   * @param webSiteUrl webSiteUrl or {@code null} for none
   */
  public Store setWebSiteUrl(java.lang.String webSiteUrl) {
    this.webSiteUrl = webSiteUrl;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getZip() {
    return zip;
  }

  /**
   * @param zip zip or {@code null} for none
   */
  public Store setZip(java.lang.String zip) {
    this.zip = zip;
    return this;
  }

  @Override
  public Store set(String fieldName, Object value) {
    return (Store) super.set(fieldName, value);
  }

  @Override
  public Store clone() {
    return (Store) super.clone();
  }

}
