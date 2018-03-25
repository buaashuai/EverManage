package io.everManage.modules.app.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * 授权信息
 *
 * @author WangShuai
 * @date 2018/3/14 00:10
 */
public class OAuthEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @SerializedName("access_token")
    private String accessToken;

    @SerializedName("token_type")
    private String tokenType;

    @SerializedName("expires_in")
    private int expiresIn;

    @SerializedName("scope")
    private String scope;

    @SerializedName("client_id")
    private String clientId;

    /**
     * 获取授权的Authorization
     *
     * @return
     */
    public String getAuthorization() {
        return tokenType + " " + accessToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public int getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(int expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }
}
