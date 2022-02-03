import ru.qatools.properties.PropertyLoader;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

public class Paths {

    private static final String PROPERTIES_FILE = "/application.properties";

    /**
     * Вспомогательный метод, возвращает свойства из файла /application.properties
     *
     * @return свойства из файла /application.properties
     */

    public static Properties getPropertiesInstance() {
        Properties instance = new Properties();
        InputStream resourceStream = PropertyLoader.class.getResourceAsStream(PROPERTIES_FILE);
        InputStreamReader inputStream = new InputStreamReader(resourceStream, StandardCharsets.UTF_8);
        System.out.println(instance.getProperty("BaseURI"));
        System.out.println("It's not working!");
        instance.setProperty("propertyTest", "it's working after set property");
        System.out.println(instance.getProperty("propertyTest"));


        try {
            instance.load(inputStream);
        } catch (Exception ignored) {
        }
        return instance;
    }
}