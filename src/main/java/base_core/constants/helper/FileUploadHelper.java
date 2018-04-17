package base_core.constants.helper;

import base_core.constants.model.Constants;
import base_core.image.dao.ImageDAO;
import base_core.image.model.Image;
import common.DataAttributeBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

/**
 * created by ewang on 2018/4/2.
 */
@Service
public class FileUploadHelper {

    @Autowired
    private ImageDAO imageDAO;

    public Long upload(MultipartFile file) {
        long id = imageDAO.insert(new DataAttributeBuilder().add(Image.KEY_VERSION, 1).buildString());

        FileOutputStream fos = null;
        boolean success = false;
        String type = file.getContentType();
        String filePath = Constants.ImagePath.getValue() + id + "." + type.split("/")[1];
        try {

            byte[] bytes = file.getBytes();
            fos = new FileOutputStream(filePath);
            fos.write(bytes);

            success = true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fos != null) fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (success) {
            Image finalImage = imageDAO.getById(id);
            int version = finalImage.getVersion();
            DataAttributeBuilder builder = new DataAttributeBuilder()
                    .add(Image.KEY_PATH, filePath)
                    .add(Image.KEY_VERSION, version + 1);
            imageDAO.update(id, builder.buildString(), version);
            return finalImage.getId();
        } else {
            imageDAO.delete(id);
            return null;
        }
    }
}