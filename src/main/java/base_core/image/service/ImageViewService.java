package base_core.image.service;

import base_core.image.dao.ImageDAO;
import base_core.image.model.Image;
import base_core.image.view.ImageView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * created by ewang on 2018/5/27.
 */
@Service
public class ImageViewService {

    @Autowired
    private ImageDAO imageDAO;

    public List<ImageView> buildViewById(List<Long> imageIds) {
        List<ImageView> imageViews = new ArrayList<>();
        for (Long id : imageIds) {
            if (id == null) {
                continue;
            }
            Image image = imageDAO.getById(id);
            imageViews.add(new ImageView(
                    image.getId(), image.getPath(), image.getType(), image.getHeight(), image.getWidth()
            ));
        }
        return imageViews;
    }
}
