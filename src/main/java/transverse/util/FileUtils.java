package transverse.util;

import java.io.File;

public class FileUtils {
    public static File getResourceFile(String filename) {
        return new File(Object.class.getResource(filename).getFile());
    }
}
