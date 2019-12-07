
package com.example.xebialabstest.data.api.apiresponse;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Medium {

    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("subtype")
    @Expose
    private String subtype;
    @SerializedName("caption")
    @Expose
    private Object caption;
    @SerializedName("copyright")
    @Expose
    private String copyright;
    @SerializedName("approved_for_syndication")
    @Expose
    private int approvedForSyndication;
    @SerializedName("media-metadata")
    @Expose
    private List<MediaMetadatum> mediaMetadata = null;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSubtype() {
        return subtype;
    }

    public void setSubtype(String subtype) {
        this.subtype = subtype;
    }

    public Object getCaption() {
        return caption;
    }

    public void setCaption(Object caption) {
        this.caption = caption;
    }

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    public Integer getApprovedForSyndication() {
        return approvedForSyndication;
    }

    public void setApprovedForSyndication(Integer approvedForSyndication) {
        this.approvedForSyndication = approvedForSyndication;
    }

    public List<MediaMetadatum> getMediaMetadata() {
        return mediaMetadata;
    }

    public void setMediaMetadata(List<MediaMetadatum> mediaMetadata) {
        this.mediaMetadata = mediaMetadata;
    }

}
