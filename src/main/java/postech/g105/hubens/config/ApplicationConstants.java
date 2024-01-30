package postech.g105.hubens.config;

import java.nio.file.Path;
import java.nio.file.Paths;

public class ApplicationConstants {
    public static final String BASE_URL = "http://localhost:8080/";
    public static final String VIDEO_STREAM_URL = BASE_URL + "video_stream/";
    public static final String VIDEO_STORAGE_STRING = "video_storage/";
    public static final Path VIDEO_STORAGE_PATH = Paths.get(VIDEO_STORAGE_STRING);

}
