package org.hans.upanddownload.constants;

public class ResponseConstant {
    public static final String CLASS_CANNOT_BE_INSTANTIATED = "Class cannot be instantiated";
    public static final String INVALID_FILENAME = "Invalid filename";
    public static final String FILES_UPLOADED = "Files successfully uploaded";
    public static final String FILE_NOT_FOUND = "File not found";


    private ResponseConstant() {
        throw new RuntimeException(CLASS_CANNOT_BE_INSTANTIATED);
    }
}
