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
 * on 2015-03-20 at 19:47:14 UTC 
 * Modify at your own risk.
 */

package com.asaan.server.com.asaan.server.endpoint.userendpoint;

/**
 * Service definition for Userendpoint (v1).
 *
 * <p>
 * This is an API
 * </p>
 *
 * <p>
 * For more information about this service, see the
 * <a href="" target="_blank">API Documentation</a>
 * </p>
 *
 * <p>
 * This service uses {@link UserendpointRequestInitializer} to initialize global parameters via its
 * {@link Builder}.
 * </p>
 *
 * @since 1.3
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public class Userendpoint extends com.google.api.client.googleapis.services.json.AbstractGoogleJsonClient {

  // Note: Leave this static initializer at the top of the file.
  static {
    com.google.api.client.util.Preconditions.checkState(
        com.google.api.client.googleapis.GoogleUtils.MAJOR_VERSION == 1 &&
        com.google.api.client.googleapis.GoogleUtils.MINOR_VERSION >= 15,
        "You are currently running with version %s of google-api-client. " +
        "You need at least version 1.15 of google-api-client to run version " +
        "1.18.0-rc of the userendpoint library.", com.google.api.client.googleapis.GoogleUtils.VERSION);
  }

  /**
   * The default encoded root URL of the service. This is determined when the library is generated
   * and normally should not be changed.
   *
   * @since 1.7
   */
  public static final String DEFAULT_ROOT_URL = "https://blissful-mantis-89513.appspot.com/_ah/api/";

  /**
   * The default encoded service path of the service. This is determined when the library is
   * generated and normally should not be changed.
   *
   * @since 1.7
   */
  public static final String DEFAULT_SERVICE_PATH = "userendpoint/v1/";

  /**
   * The default encoded base URL of the service. This is determined when the library is generated
   * and normally should not be changed.
   */
  public static final String DEFAULT_BASE_URL = DEFAULT_ROOT_URL + DEFAULT_SERVICE_PATH;

  /**
   * Constructor.
   *
   * <p>
   * Use {@link Builder} if you need to specify any of the optional parameters.
   * </p>
   *
   * @param transport HTTP transport, which should normally be:
   *        <ul>
   *        <li>Google App Engine:
   *        {@code com.google.api.client.extensions.appengine.http.UrlFetchTransport}</li>
   *        <li>Android: {@code newCompatibleTransport} from
   *        {@code com.google.api.client.extensions.android.http.AndroidHttp}</li>
   *        <li>Java: {@link com.google.api.client.googleapis.javanet.GoogleNetHttpTransport#newTrustedTransport()}
   *        </li>
   *        </ul>
   * @param jsonFactory JSON factory, which may be:
   *        <ul>
   *        <li>Jackson: {@code com.google.api.client.json.jackson2.JacksonFactory}</li>
   *        <li>Google GSON: {@code com.google.api.client.json.gson.GsonFactory}</li>
   *        <li>Android Honeycomb or higher:
   *        {@code com.google.api.client.extensions.android.json.AndroidJsonFactory}</li>
   *        </ul>
   * @param httpRequestInitializer HTTP request initializer or {@code null} for none
   * @since 1.7
   */
  public Userendpoint(com.google.api.client.http.HttpTransport transport, com.google.api.client.json.JsonFactory jsonFactory,
      com.google.api.client.http.HttpRequestInitializer httpRequestInitializer) {
    this(new Builder(transport, jsonFactory, httpRequestInitializer));
  }

  /**
   * @param builder builder
   */
  Userendpoint(Builder builder) {
    super(builder);
  }

  @Override
  protected void initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest<?> httpClientRequest) throws java.io.IOException {
    super.initialize(httpClientRequest);
  }

  /**
   * Create a request for the method "getCurrentUser".
   *
   * This request holds the parameters needed by the userendpoint server.  After setting any optional
   * parameters, call the {@link GetCurrentUser#execute()} method to invoke the remote operation.
   *
   * @return the request
   */
  public GetCurrentUser getCurrentUser() throws java.io.IOException {
    GetCurrentUser result = new GetCurrentUser();
    initialize(result);
    return result;
  }

  public class GetCurrentUser extends UserendpointRequest<com.asaan.server.com.asaan.server.endpoint.userendpoint.model.User> {

    private static final String REST_PATH = "user";

    /**
     * Create a request for the method "getCurrentUser".
     *
     * This request holds the parameters needed by the the userendpoint server.  After setting any
     * optional parameters, call the {@link GetCurrentUser#execute()} method to invoke the remote
     * operation. <p> {@link GetCurrentUser#initialize(com.google.api.client.googleapis.services.Abstr
     * actGoogleClientRequest)} must be called to initialize this instance immediately after invoking
     * the constructor. </p>
     *
     * @since 1.13
     */
    protected GetCurrentUser() {
      super(Userendpoint.this, "POST", REST_PATH, null, com.asaan.server.com.asaan.server.endpoint.userendpoint.model.User.class);
    }

    @Override
    public GetCurrentUser setAlt(java.lang.String alt) {
      return (GetCurrentUser) super.setAlt(alt);
    }

    @Override
    public GetCurrentUser setFields(java.lang.String fields) {
      return (GetCurrentUser) super.setFields(fields);
    }

    @Override
    public GetCurrentUser setKey(java.lang.String key) {
      return (GetCurrentUser) super.setKey(key);
    }

    @Override
    public GetCurrentUser setOauthToken(java.lang.String oauthToken) {
      return (GetCurrentUser) super.setOauthToken(oauthToken);
    }

    @Override
    public GetCurrentUser setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (GetCurrentUser) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public GetCurrentUser setQuotaUser(java.lang.String quotaUser) {
      return (GetCurrentUser) super.setQuotaUser(quotaUser);
    }

    @Override
    public GetCurrentUser setUserIp(java.lang.String userIp) {
      return (GetCurrentUser) super.setUserIp(userIp);
    }

    @Override
    public GetCurrentUser set(String parameterName, Object value) {
      return (GetCurrentUser) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "getUserAddresses".
   *
   * This request holds the parameters needed by the userendpoint server.  After setting any optional
   * parameters, call the {@link GetUserAddresses#execute()} method to invoke the remote operation.
   *
   * @return the request
   */
  public GetUserAddresses getUserAddresses() throws java.io.IOException {
    GetUserAddresses result = new GetUserAddresses();
    initialize(result);
    return result;
  }

  public class GetUserAddresses extends UserendpointRequest<com.asaan.server.com.asaan.server.endpoint.userendpoint.model.UserAddressCollection> {

    private static final String REST_PATH = "useraddresscollection";

    /**
     * Create a request for the method "getUserAddresses".
     *
     * This request holds the parameters needed by the the userendpoint server.  After setting any
     * optional parameters, call the {@link GetUserAddresses#execute()} method to invoke the remote
     * operation. <p> {@link GetUserAddresses#initialize(com.google.api.client.googleapis.services.Abs
     * tractGoogleClientRequest)} must be called to initialize this instance immediately after
     * invoking the constructor. </p>
     *
     * @since 1.13
     */
    protected GetUserAddresses() {
      super(Userendpoint.this, "POST", REST_PATH, null, com.asaan.server.com.asaan.server.endpoint.userendpoint.model.UserAddressCollection.class);
    }

    @Override
    public GetUserAddresses setAlt(java.lang.String alt) {
      return (GetUserAddresses) super.setAlt(alt);
    }

    @Override
    public GetUserAddresses setFields(java.lang.String fields) {
      return (GetUserAddresses) super.setFields(fields);
    }

    @Override
    public GetUserAddresses setKey(java.lang.String key) {
      return (GetUserAddresses) super.setKey(key);
    }

    @Override
    public GetUserAddresses setOauthToken(java.lang.String oauthToken) {
      return (GetUserAddresses) super.setOauthToken(oauthToken);
    }

    @Override
    public GetUserAddresses setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (GetUserAddresses) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public GetUserAddresses setQuotaUser(java.lang.String quotaUser) {
      return (GetUserAddresses) super.setQuotaUser(quotaUser);
    }

    @Override
    public GetUserAddresses setUserIp(java.lang.String userIp) {
      return (GetUserAddresses) super.setUserIp(userIp);
    }

    @Override
    public GetUserAddresses set(String parameterName, Object value) {
      return (GetUserAddresses) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "getUserByEmail".
   *
   * This request holds the parameters needed by the userendpoint server.  After setting any optional
   * parameters, call the {@link GetUserByEmail#execute()} method to invoke the remote operation.
   *
   * @param email
   * @return the request
   */
  public GetUserByEmail getUserByEmail(java.lang.String email) throws java.io.IOException {
    GetUserByEmail result = new GetUserByEmail(email);
    initialize(result);
    return result;
  }

  public class GetUserByEmail extends UserendpointRequest<com.asaan.server.com.asaan.server.endpoint.userendpoint.model.User> {

    private static final String REST_PATH = "user/{email}";

    /**
     * Create a request for the method "getUserByEmail".
     *
     * This request holds the parameters needed by the the userendpoint server.  After setting any
     * optional parameters, call the {@link GetUserByEmail#execute()} method to invoke the remote
     * operation. <p> {@link GetUserByEmail#initialize(com.google.api.client.googleapis.services.Abstr
     * actGoogleClientRequest)} must be called to initialize this instance immediately after invoking
     * the constructor. </p>
     *
     * @param email
     * @since 1.13
     */
    protected GetUserByEmail(java.lang.String email) {
      super(Userendpoint.this, "POST", REST_PATH, null, com.asaan.server.com.asaan.server.endpoint.userendpoint.model.User.class);
      this.email = com.google.api.client.util.Preconditions.checkNotNull(email, "Required parameter email must be specified.");
    }

    @Override
    public GetUserByEmail setAlt(java.lang.String alt) {
      return (GetUserByEmail) super.setAlt(alt);
    }

    @Override
    public GetUserByEmail setFields(java.lang.String fields) {
      return (GetUserByEmail) super.setFields(fields);
    }

    @Override
    public GetUserByEmail setKey(java.lang.String key) {
      return (GetUserByEmail) super.setKey(key);
    }

    @Override
    public GetUserByEmail setOauthToken(java.lang.String oauthToken) {
      return (GetUserByEmail) super.setOauthToken(oauthToken);
    }

    @Override
    public GetUserByEmail setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (GetUserByEmail) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public GetUserByEmail setQuotaUser(java.lang.String quotaUser) {
      return (GetUserByEmail) super.setQuotaUser(quotaUser);
    }

    @Override
    public GetUserByEmail setUserIp(java.lang.String userIp) {
      return (GetUserByEmail) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.String email;

    /**

     */
    public java.lang.String getEmail() {
      return email;
    }

    public GetUserByEmail setEmail(java.lang.String email) {
      this.email = email;
      return this;
    }

    @Override
    public GetUserByEmail set(String parameterName, Object value) {
      return (GetUserByEmail) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "getUserByPhone".
   *
   * This request holds the parameters needed by the userendpoint server.  After setting any optional
   * parameters, call the {@link GetUserByPhone#execute()} method to invoke the remote operation.
   *
   * @param phone
   * @return the request
   */
  public GetUserByPhone getUserByPhone(java.lang.String phone) throws java.io.IOException {
    GetUserByPhone result = new GetUserByPhone(phone);
    initialize(result);
    return result;
  }

  public class GetUserByPhone extends UserendpointRequest<com.asaan.server.com.asaan.server.endpoint.userendpoint.model.ChatUser> {

    private static final String REST_PATH = "chatuser/{phone}";

    /**
     * Create a request for the method "getUserByPhone".
     *
     * This request holds the parameters needed by the the userendpoint server.  After setting any
     * optional parameters, call the {@link GetUserByPhone#execute()} method to invoke the remote
     * operation. <p> {@link GetUserByPhone#initialize(com.google.api.client.googleapis.services.Abstr
     * actGoogleClientRequest)} must be called to initialize this instance immediately after invoking
     * the constructor. </p>
     *
     * @param phone
     * @since 1.13
     */
    protected GetUserByPhone(java.lang.String phone) {
      super(Userendpoint.this, "POST", REST_PATH, null, com.asaan.server.com.asaan.server.endpoint.userendpoint.model.ChatUser.class);
      this.phone = com.google.api.client.util.Preconditions.checkNotNull(phone, "Required parameter phone must be specified.");
    }

    @Override
    public GetUserByPhone setAlt(java.lang.String alt) {
      return (GetUserByPhone) super.setAlt(alt);
    }

    @Override
    public GetUserByPhone setFields(java.lang.String fields) {
      return (GetUserByPhone) super.setFields(fields);
    }

    @Override
    public GetUserByPhone setKey(java.lang.String key) {
      return (GetUserByPhone) super.setKey(key);
    }

    @Override
    public GetUserByPhone setOauthToken(java.lang.String oauthToken) {
      return (GetUserByPhone) super.setOauthToken(oauthToken);
    }

    @Override
    public GetUserByPhone setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (GetUserByPhone) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public GetUserByPhone setQuotaUser(java.lang.String quotaUser) {
      return (GetUserByPhone) super.setQuotaUser(quotaUser);
    }

    @Override
    public GetUserByPhone setUserIp(java.lang.String userIp) {
      return (GetUserByPhone) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.String phone;

    /**

     */
    public java.lang.String getPhone() {
      return phone;
    }

    public GetUserByPhone setPhone(java.lang.String phone) {
      this.phone = phone;
      return this;
    }

    @Override
    public GetUserByPhone set(String parameterName, Object value) {
      return (GetUserByPhone) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "getUserCards".
   *
   * This request holds the parameters needed by the userendpoint server.  After setting any optional
   * parameters, call the {@link GetUserCards#execute()} method to invoke the remote operation.
   *
   * @return the request
   */
  public GetUserCards getUserCards() throws java.io.IOException {
    GetUserCards result = new GetUserCards();
    initialize(result);
    return result;
  }

  public class GetUserCards extends UserendpointRequest<com.asaan.server.com.asaan.server.endpoint.userendpoint.model.UserCardCollection> {

    private static final String REST_PATH = "usercardcollection";

    /**
     * Create a request for the method "getUserCards".
     *
     * This request holds the parameters needed by the the userendpoint server.  After setting any
     * optional parameters, call the {@link GetUserCards#execute()} method to invoke the remote
     * operation. <p> {@link
     * GetUserCards#initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest)}
     * must be called to initialize this instance immediately after invoking the constructor. </p>
     *
     * @since 1.13
     */
    protected GetUserCards() {
      super(Userendpoint.this, "POST", REST_PATH, null, com.asaan.server.com.asaan.server.endpoint.userendpoint.model.UserCardCollection.class);
    }

    @Override
    public GetUserCards setAlt(java.lang.String alt) {
      return (GetUserCards) super.setAlt(alt);
    }

    @Override
    public GetUserCards setFields(java.lang.String fields) {
      return (GetUserCards) super.setFields(fields);
    }

    @Override
    public GetUserCards setKey(java.lang.String key) {
      return (GetUserCards) super.setKey(key);
    }

    @Override
    public GetUserCards setOauthToken(java.lang.String oauthToken) {
      return (GetUserCards) super.setOauthToken(oauthToken);
    }

    @Override
    public GetUserCards setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (GetUserCards) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public GetUserCards setQuotaUser(java.lang.String quotaUser) {
      return (GetUserCards) super.setQuotaUser(quotaUser);
    }

    @Override
    public GetUserCards setUserIp(java.lang.String userIp) {
      return (GetUserCards) super.setUserIp(userIp);
    }

    @Override
    public GetUserCards set(String parameterName, Object value) {
      return (GetUserCards) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "saveUser".
   *
   * This request holds the parameters needed by the userendpoint server.  After setting any optional
   * parameters, call the {@link SaveUser#execute()} method to invoke the remote operation.
   *
   * @param content the {@link com.asaan.server.com.asaan.server.endpoint.userendpoint.model.User}
   * @return the request
   */
  public SaveUser saveUser(com.asaan.server.com.asaan.server.endpoint.userendpoint.model.User content) throws java.io.IOException {
    SaveUser result = new SaveUser(content);
    initialize(result);
    return result;
  }

  public class SaveUser extends UserendpointRequest<Void> {

    private static final String REST_PATH = "saveUser";

    /**
     * Create a request for the method "saveUser".
     *
     * This request holds the parameters needed by the the userendpoint server.  After setting any
     * optional parameters, call the {@link SaveUser#execute()} method to invoke the remote operation.
     * <p> {@link
     * SaveUser#initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest)}
     * must be called to initialize this instance immediately after invoking the constructor. </p>
     *
     * @param content the {@link com.asaan.server.com.asaan.server.endpoint.userendpoint.model.User}
     * @since 1.13
     */
    protected SaveUser(com.asaan.server.com.asaan.server.endpoint.userendpoint.model.User content) {
      super(Userendpoint.this, "POST", REST_PATH, content, Void.class);
    }

    @Override
    public SaveUser setAlt(java.lang.String alt) {
      return (SaveUser) super.setAlt(alt);
    }

    @Override
    public SaveUser setFields(java.lang.String fields) {
      return (SaveUser) super.setFields(fields);
    }

    @Override
    public SaveUser setKey(java.lang.String key) {
      return (SaveUser) super.setKey(key);
    }

    @Override
    public SaveUser setOauthToken(java.lang.String oauthToken) {
      return (SaveUser) super.setOauthToken(oauthToken);
    }

    @Override
    public SaveUser setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (SaveUser) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public SaveUser setQuotaUser(java.lang.String quotaUser) {
      return (SaveUser) super.setQuotaUser(quotaUser);
    }

    @Override
    public SaveUser setUserIp(java.lang.String userIp) {
      return (SaveUser) super.setUserIp(userIp);
    }

    @Override
    public SaveUser set(String parameterName, Object value) {
      return (SaveUser) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "saveUserAddress".
   *
   * This request holds the parameters needed by the userendpoint server.  After setting any optional
   * parameters, call the {@link SaveUserAddress#execute()} method to invoke the remote operation.
   *
   * @param content the {@link com.asaan.server.com.asaan.server.endpoint.userendpoint.model.UserAddress}
   * @return the request
   */
  public SaveUserAddress saveUserAddress(com.asaan.server.com.asaan.server.endpoint.userendpoint.model.UserAddress content) throws java.io.IOException {
    SaveUserAddress result = new SaveUserAddress(content);
    initialize(result);
    return result;
  }

  public class SaveUserAddress extends UserendpointRequest<com.asaan.server.com.asaan.server.endpoint.userendpoint.model.UserAddress> {

    private static final String REST_PATH = "saveUserAddress";

    /**
     * Create a request for the method "saveUserAddress".
     *
     * This request holds the parameters needed by the the userendpoint server.  After setting any
     * optional parameters, call the {@link SaveUserAddress#execute()} method to invoke the remote
     * operation. <p> {@link SaveUserAddress#initialize(com.google.api.client.googleapis.services.Abst
     * ractGoogleClientRequest)} must be called to initialize this instance immediately after invoking
     * the constructor. </p>
     *
     * @param content the {@link com.asaan.server.com.asaan.server.endpoint.userendpoint.model.UserAddress}
     * @since 1.13
     */
    protected SaveUserAddress(com.asaan.server.com.asaan.server.endpoint.userendpoint.model.UserAddress content) {
      super(Userendpoint.this, "POST", REST_PATH, content, com.asaan.server.com.asaan.server.endpoint.userendpoint.model.UserAddress.class);
    }

    @Override
    public SaveUserAddress setAlt(java.lang.String alt) {
      return (SaveUserAddress) super.setAlt(alt);
    }

    @Override
    public SaveUserAddress setFields(java.lang.String fields) {
      return (SaveUserAddress) super.setFields(fields);
    }

    @Override
    public SaveUserAddress setKey(java.lang.String key) {
      return (SaveUserAddress) super.setKey(key);
    }

    @Override
    public SaveUserAddress setOauthToken(java.lang.String oauthToken) {
      return (SaveUserAddress) super.setOauthToken(oauthToken);
    }

    @Override
    public SaveUserAddress setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (SaveUserAddress) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public SaveUserAddress setQuotaUser(java.lang.String quotaUser) {
      return (SaveUserAddress) super.setQuotaUser(quotaUser);
    }

    @Override
    public SaveUserAddress setUserIp(java.lang.String userIp) {
      return (SaveUserAddress) super.setUserIp(userIp);
    }

    @Override
    public SaveUserAddress set(String parameterName, Object value) {
      return (SaveUserAddress) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "saveUserCard".
   *
   * This request holds the parameters needed by the userendpoint server.  After setting any optional
   * parameters, call the {@link SaveUserCard#execute()} method to invoke the remote operation.
   *
   * @param content the {@link com.asaan.server.com.asaan.server.endpoint.userendpoint.model.UserCard}
   * @return the request
   */
  public SaveUserCard saveUserCard(com.asaan.server.com.asaan.server.endpoint.userendpoint.model.UserCard content) throws java.io.IOException {
    SaveUserCard result = new SaveUserCard(content);
    initialize(result);
    return result;
  }

  public class SaveUserCard extends UserendpointRequest<com.asaan.server.com.asaan.server.endpoint.userendpoint.model.UserCard> {

    private static final String REST_PATH = "saveUserCard";

    /**
     * Create a request for the method "saveUserCard".
     *
     * This request holds the parameters needed by the the userendpoint server.  After setting any
     * optional parameters, call the {@link SaveUserCard#execute()} method to invoke the remote
     * operation. <p> {@link
     * SaveUserCard#initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest)}
     * must be called to initialize this instance immediately after invoking the constructor. </p>
     *
     * @param content the {@link com.asaan.server.com.asaan.server.endpoint.userendpoint.model.UserCard}
     * @since 1.13
     */
    protected SaveUserCard(com.asaan.server.com.asaan.server.endpoint.userendpoint.model.UserCard content) {
      super(Userendpoint.this, "POST", REST_PATH, content, com.asaan.server.com.asaan.server.endpoint.userendpoint.model.UserCard.class);
    }

    @Override
    public SaveUserCard setAlt(java.lang.String alt) {
      return (SaveUserCard) super.setAlt(alt);
    }

    @Override
    public SaveUserCard setFields(java.lang.String fields) {
      return (SaveUserCard) super.setFields(fields);
    }

    @Override
    public SaveUserCard setKey(java.lang.String key) {
      return (SaveUserCard) super.setKey(key);
    }

    @Override
    public SaveUserCard setOauthToken(java.lang.String oauthToken) {
      return (SaveUserCard) super.setOauthToken(oauthToken);
    }

    @Override
    public SaveUserCard setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (SaveUserCard) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public SaveUserCard setQuotaUser(java.lang.String quotaUser) {
      return (SaveUserCard) super.setQuotaUser(quotaUser);
    }

    @Override
    public SaveUserCard setUserIp(java.lang.String userIp) {
      return (SaveUserCard) super.setUserIp(userIp);
    }

    @Override
    public SaveUserCard set(String parameterName, Object value) {
      return (SaveUserCard) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "saveUserProfile".
   *
   * This request holds the parameters needed by the userendpoint server.  After setting any optional
   * parameters, call the {@link SaveUserProfile#execute()} method to invoke the remote operation.
   *
   * @param content the {@link com.asaan.server.com.asaan.server.endpoint.userendpoint.model.User}
   * @return the request
   */
  public SaveUserProfile saveUserProfile(com.asaan.server.com.asaan.server.endpoint.userendpoint.model.User content) throws java.io.IOException {
    SaveUserProfile result = new SaveUserProfile(content);
    initialize(result);
    return result;
  }

  public class SaveUserProfile extends UserendpointRequest<com.asaan.server.com.asaan.server.endpoint.userendpoint.model.User> {

    private static final String REST_PATH = "saveUserProfile";

    /**
     * Create a request for the method "saveUserProfile".
     *
     * This request holds the parameters needed by the the userendpoint server.  After setting any
     * optional parameters, call the {@link SaveUserProfile#execute()} method to invoke the remote
     * operation. <p> {@link SaveUserProfile#initialize(com.google.api.client.googleapis.services.Abst
     * ractGoogleClientRequest)} must be called to initialize this instance immediately after invoking
     * the constructor. </p>
     *
     * @param content the {@link com.asaan.server.com.asaan.server.endpoint.userendpoint.model.User}
     * @since 1.13
     */
    protected SaveUserProfile(com.asaan.server.com.asaan.server.endpoint.userendpoint.model.User content) {
      super(Userendpoint.this, "POST", REST_PATH, content, com.asaan.server.com.asaan.server.endpoint.userendpoint.model.User.class);
    }

    @Override
    public SaveUserProfile setAlt(java.lang.String alt) {
      return (SaveUserProfile) super.setAlt(alt);
    }

    @Override
    public SaveUserProfile setFields(java.lang.String fields) {
      return (SaveUserProfile) super.setFields(fields);
    }

    @Override
    public SaveUserProfile setKey(java.lang.String key) {
      return (SaveUserProfile) super.setKey(key);
    }

    @Override
    public SaveUserProfile setOauthToken(java.lang.String oauthToken) {
      return (SaveUserProfile) super.setOauthToken(oauthToken);
    }

    @Override
    public SaveUserProfile setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (SaveUserProfile) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public SaveUserProfile setQuotaUser(java.lang.String quotaUser) {
      return (SaveUserProfile) super.setQuotaUser(quotaUser);
    }

    @Override
    public SaveUserProfile setUserIp(java.lang.String userIp) {
      return (SaveUserProfile) super.setUserIp(userIp);
    }

    @Override
    public SaveUserProfile set(String parameterName, Object value) {
      return (SaveUserProfile) super.set(parameterName, value);
    }
  }

  /**
   * Builder for {@link Userendpoint}.
   *
   * <p>
   * Implementation is not thread-safe.
   * </p>
   *
   * @since 1.3.0
   */
  public static final class Builder extends com.google.api.client.googleapis.services.json.AbstractGoogleJsonClient.Builder {

    /**
     * Returns an instance of a new builder.
     *
     * @param transport HTTP transport, which should normally be:
     *        <ul>
     *        <li>Google App Engine:
     *        {@code com.google.api.client.extensions.appengine.http.UrlFetchTransport}</li>
     *        <li>Android: {@code newCompatibleTransport} from
     *        {@code com.google.api.client.extensions.android.http.AndroidHttp}</li>
     *        <li>Java: {@link com.google.api.client.googleapis.javanet.GoogleNetHttpTransport#newTrustedTransport()}
     *        </li>
     *        </ul>
     * @param jsonFactory JSON factory, which may be:
     *        <ul>
     *        <li>Jackson: {@code com.google.api.client.json.jackson2.JacksonFactory}</li>
     *        <li>Google GSON: {@code com.google.api.client.json.gson.GsonFactory}</li>
     *        <li>Android Honeycomb or higher:
     *        {@code com.google.api.client.extensions.android.json.AndroidJsonFactory}</li>
     *        </ul>
     * @param httpRequestInitializer HTTP request initializer or {@code null} for none
     * @since 1.7
     */
    public Builder(com.google.api.client.http.HttpTransport transport, com.google.api.client.json.JsonFactory jsonFactory,
        com.google.api.client.http.HttpRequestInitializer httpRequestInitializer) {
      super(
          transport,
          jsonFactory,
          DEFAULT_ROOT_URL,
          DEFAULT_SERVICE_PATH,
          httpRequestInitializer,
          false);
    }

    /** Builds a new instance of {@link Userendpoint}. */
    @Override
    public Userendpoint build() {
      return new Userendpoint(this);
    }

    @Override
    public Builder setRootUrl(String rootUrl) {
      return (Builder) super.setRootUrl(rootUrl);
    }

    @Override
    public Builder setServicePath(String servicePath) {
      return (Builder) super.setServicePath(servicePath);
    }

    @Override
    public Builder setHttpRequestInitializer(com.google.api.client.http.HttpRequestInitializer httpRequestInitializer) {
      return (Builder) super.setHttpRequestInitializer(httpRequestInitializer);
    }

    @Override
    public Builder setApplicationName(String applicationName) {
      return (Builder) super.setApplicationName(applicationName);
    }

    @Override
    public Builder setSuppressPatternChecks(boolean suppressPatternChecks) {
      return (Builder) super.setSuppressPatternChecks(suppressPatternChecks);
    }

    @Override
    public Builder setSuppressRequiredParameterChecks(boolean suppressRequiredParameterChecks) {
      return (Builder) super.setSuppressRequiredParameterChecks(suppressRequiredParameterChecks);
    }

    @Override
    public Builder setSuppressAllChecks(boolean suppressAllChecks) {
      return (Builder) super.setSuppressAllChecks(suppressAllChecks);
    }

    /**
     * Set the {@link UserendpointRequestInitializer}.
     *
     * @since 1.12
     */
    public Builder setUserendpointRequestInitializer(
        UserendpointRequestInitializer userendpointRequestInitializer) {
      return (Builder) super.setGoogleClientRequestInitializer(userendpointRequestInitializer);
    }

    @Override
    public Builder setGoogleClientRequestInitializer(
        com.google.api.client.googleapis.services.GoogleClientRequestInitializer googleClientRequestInitializer) {
      return (Builder) super.setGoogleClientRequestInitializer(googleClientRequestInitializer);
    }
  }
}
