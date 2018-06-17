package base_core.design.service.impl;

import base_core.design.dao.DesignDAO;
import base_core.design.model.Design;
import base_core.design.model.DesignOrder;
import base_core.design.model.DesignPreview;
import base_core.design.service.DesignViewService;
import base_core.design.view.DesignOrderView;
import base_core.design.view.DesignPreviewView;
import base_core.design.view.DesignView;
import base_core.image.service.ImageViewService;
import base_core.image.view.ImageView;
import base_core.user.service.UserViewService;
import base_core.user.view.UserAddressView;
import base_core.user.view.UserView;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * created by ewang on 2018/6/14.
 */
@Service
public class DesignViewServiceImpl implements DesignViewService {

    @Autowired
    private UserViewService userViewService;

    @Autowired
    private ImageViewService imageViewService;

    @Autowired
    private DesignDAO designDAO;

    @Override
    public List<DesignView> buildDesignView(List<Design> designList) {
        List<DesignView> designViewList = new ArrayList<>();
        for (Design d : designList) {
            List<UserView> userViewList = userViewService.buildUserViewById(Collections.singletonList(d.getOwnerId()));
            designViewList.add(new DesignView(d.getId(), CollectionUtils.isEmpty(userViewList) ? null : userViewList.get(0)));
        }
        return designViewList;
    }

    @Override
    public List<DesignView> buildDesignViewById(List<Long> designIdList) {
        List<DesignView> designViewList = new ArrayList<>();
        for (Long designId : designIdList) {
            if (designId == null) {
                continue;
            }
            Design d = designDAO.getById(designId);
            if (d == null) {
                continue;
            }
            List<UserView> userViewList = userViewService.buildUserViewById(Collections.singletonList(d.getOwnerId()));
            designViewList.add(new DesignView(d.getId(), CollectionUtils.isEmpty(userViewList) ? null : userViewList.get(0)));
        }
        return designViewList;
    }


    @Override
    public List<DesignPreviewView> buildDesignPreviewView(List<DesignPreview> designPreviewList) {
        List<DesignPreviewView> designPreviewViewList = new ArrayList<>();
        for (DesignPreview designPreview : designPreviewList) {

            List<DesignView> designViewList = buildDesignViewById(Collections.singletonList(designPreview.getDesignId()));
            List<ImageView> imageViewList = imageViewService.buildViewById(Collections.singletonList(designPreview.getImagePreview()));

            designPreviewViewList.add(new DesignPreviewView(designPreview.getId(),
                    CollectionUtils.isEmpty(designViewList) ? null : designViewList.get(0),
                    CollectionUtils.isEmpty(imageViewList) ? null : imageViewList.get(0),
                    designPreview.getDesignSide()));
        }
        return designPreviewViewList;
    }

    @Override
    public List<DesignOrderView> buildDesignOrderView(List<DesignOrder> designOrderList) {
        List<DesignOrderView> designOrderViewList = new ArrayList<>();
        for (DesignOrder order : designOrderList) {
            List<DesignView> designViewList = buildDesignViewById(Collections.singletonList(order.getDesignId()));
            List<UserView> userViewList = userViewService.buildUserViewById(Collections.singletonList(order.getUserId()));
            List<UserAddressView> userAddressViewList = userViewService.buildAddressViewById(Collections.singletonList(order.getAddressId()));

            designOrderViewList.add(new DesignOrderView(order.getId(),
                    CollectionUtils.isEmpty(designViewList) ? null : designViewList.get(0),
                    CollectionUtils.isEmpty(userViewList) ? null : userViewList.get(0),
                    CollectionUtils.isEmpty(userAddressViewList) ? null : userAddressViewList.get(0),
                    order.getDesignSize(), order.getPrice(), order.getOrderStatus()));
        }
        return designOrderViewList;
    }
}
