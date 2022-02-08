import ru.qatools.properties.PropertyLoader;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

public class Paths {

    private static final String PROPERTIES_FILE = "/application.properties";
    private static final String baseDir = System.getProperty("user.dir");

    public static String pathToJsons() {
        return baseDir + "/src/test/java/jsons/";
    }

    /**
     * Вспомогательный метод, возвращает свойства из файла /application.properties
     *
     * @return свойства из файла /application.properties
     */

    public static Properties getPropertiesInstance() {
        Properties instance = new Properties();
        InputStream resourceStream = PropertyLoader.class.getResourceAsStream(PROPERTIES_FILE);
        InputStreamReader inputStream = new InputStreamReader(resourceStream, StandardCharsets.UTF_8);

        try {
            instance.load(inputStream);
        } catch (Exception ignored) {
        }
        return instance;
    }
    public static String urlValue(String url) {
        Properties PROPERTIES = getPropertiesInstance();
        if (url.startsWith("http"))
            return url;
        if (url.startsWith("/")) {
            return PROPERTIES.getProperty("BaseURI") + url;
        }
        return null;
    }
}