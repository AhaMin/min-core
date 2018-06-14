package base_core.design.view;

import base_core.design.constants.DesignSide;
import base_core.image.view.ImageView;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * created by ewang on 2018/6/14.
 */
public class DesignPreviewView {

    @JsonProperty
    private final long id;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty
    private final DesignView design;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty
    private final ImageView previewImage;

    @JsonProperty
    private final DesignSide designSide;

    public DesignPreviewView(long id, DesignView design, ImageView previewImage, DesignSide designSide) {
        this.id = id;
        this.design = design;
        this.previewImage = previewImage;
        this.designSide = designSide;
    }
}
