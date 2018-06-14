package base_core.constants.helper;

import base_core.constants.model.Constants;
import base_core.image.dao.ImageDAO;
import base_core.image.model.Image;
import common.DataAttributeBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * created by ewang on 2018/4/2.
 */
@Service
public class FileUploadHelper {

    @Autowired
    private ImageDAO imageDAO;

    public Long upload(MultipartFile file) {
        long id = imageDAO.insert(new DataAttributeBuilder().add(Image.KEY_VERSION, 0).buildString());

        FileOutputStream fos = null;
        BufferedImage bufferedImage = null;
        boolean success = false;
        String type = file.getContentType().split("/")[1];
        String filePath = Constants.ImagePath.getValue() + id + "." + type;
        File imageFile = new File(filePath);
        try {

            bufferedImage = ImageIO.read(file.getInputStream());
            byte[] bytes = file.getBytes();
            fos = new FileOutputStream(imageFile);
            fos.write(bytes);

            success = true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                imageFile.setReadable(true, false);
                if (fos != null) fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (success) {
            Image finalImage = imageDAO.getById(id);
            int version = finalImage.getVersion();
            DataAttributeBuilder builder = new DataAttributeBuilder()
                    .add(Image.KEY_URL, filePath)
                    .add(Image.KEY_TYPE, type)
                    .add(Image.KEY_HEIGHT, bufferedImage.getHeight())
                    .add(Image.KEY_WIDTH, bufferedImage.getWidth())
                    .add(Image.KEY_VERSION, version + 1);
            imageDAO.update(id, builder.buildString(), version);
            return finalImage.getId();
        } else {
            imageDAO.delete(id);
            return null;
        }
    }
}
