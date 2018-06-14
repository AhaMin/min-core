package base_core.image.view;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * created by ewang on 2018/5/27.
 */
public class ImageView {

    @JsonProperty
    private final long id;

    @JsonProperty
    private final String url;

    @JsonProperty
    private final String type;

    @JsonProperty
    private final int height;

    @JsonProperty
    private final int width;

    public ImageView(long id, String url, String type, int height, int width) {
        this.id = id;
        this.url = url;
        this.type = type;
        this.height = height;
        this.width = width;
    }
}
