package base_core.design.service;

import base_core.design.model.Design;
import base_core.design.model.DesignOrder;
import base_core.design.model.DesignPreview;
import base_core.design.view.DesignOrderView;
import base_core.design.view.DesignPreviewView;
import base_core.design.view.DesignView;

import java.util.List;

/**
 * created by ewang on 2018/6/14.
 */
public interface DesignViewService {

    List<DesignView> buildDesignView(List<Design> designList);

    List<DesignView> buildDesignViewById(List<Long> designIdList);

    List<DesignPreviewView> buildDesignPreviewView(List<DesignPreview> designPreviewList);

    List<DesignOrderView> buildDesignOrderView(List<DesignOrder> designOrderList);
}
