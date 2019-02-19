package georef.strategies.sgimage;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import georef.Settings;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

public class SGImageGoogle {

    public String downloadImageOfAddress(String address) {
        try {
            String encodedAddress = URLEncoder.encode(address, "UTF-8");

            String endpoint = "https://maps.googleapis.com/maps/api/staticmap?center=" + encodedAddress + "&zoom=16&size=600x300&maptype=hybrid&key=" + Settings.GOOGLE_API_KEY;

            String fileName = "Google-" + address + System.currentTimeMillis() + ".jpg";
            String destinationPath = "./downloads/" + fileName;

            URL url = new URL(endpoint);
            InputStream inputStream = url.openStream();
            OutputStream outputStream = new FileOutputStream(destinationPath);

            byte[] b = new byte[2048];
            int length;

            while ((length = inputStream.read(b)) != -1) {
                outputStream.write(b, 0, length);
            }

            inputStream.close();
            outputStream.close();

            Cloudinary cloudinary = new Cloudinary(Settings.CLOUDINARY_URL);

            Map params = ObjectUtils.asMap(
                "public_id", "lapr4/" + "Google-" + address + System.currentTimeMillis(),
                "overwrite", true,
                "notification_url", "http://mysite/notify_endpoint",
                "resource_type", "image"
            );
            Map uploadResult = cloudinary.uploader().upload(new File(destinationPath), params);

            return uploadResult.get("url").toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
