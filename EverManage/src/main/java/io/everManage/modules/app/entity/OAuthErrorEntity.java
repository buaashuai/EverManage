package io.everManage.modules.app.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * 授权错误信息
 *
 * @author WangShuai
 * @date 2018/3/15 00:00
 */
public class OAuthErrorEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @SerializedName("error")
    private String error;

    @SerializedName("error_description")
    private String errorDescription;

    /**
     * token是否授权失败
     *
     * @return
     */
    public boolean isTokenError() {
        return (error.equals("invalid_token") || error.equals("unauthorized"));
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getErrorDescription() {
        return errorDescription;
    }

    public void setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
    }
}
