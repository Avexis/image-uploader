package no.avexis.image.uploader;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.validation.ValidationMethod;
import no.avexis.image.uploader.exceptions.ImageUploaderDirectoryMissingException;
import org.hibernate.validator.constraints.NotEmpty;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ImageUploaderFactory {

    @JsonProperty
    @NotEmpty
    private String directory;
    @JsonProperty
    private boolean createDirectory;
    @JsonProperty
    private String filenameFormat;
    @JsonProperty
    private ImageFileType forcedFileType;
    @JsonProperty
    private List<ImageFileType> allowedFileTypes;
    @JsonProperty
    @NotEmpty
    private List<ImageSize> resolutions;

    public ImageUploaderFactory() {
        this.directory = "";
        this.filenameFormat = "%1$s_%2$s_%3$s.%6$s";
        this.forcedFileType = null;
        this.allowedFileTypes = new ArrayList<>();
        this.resolutions = new ArrayList<>();
    }

    private String createFilename(final String filename, final ImageSize size, final String extension) {
        return String.format(this.filenameFormat, filename, size.getWidth(), size.getHeight(), size.getName(), size.isCrop() ? "crop" : "", extension);
    }

    @ValidationMethod(message = "Either forcedFileType or allowedFileTypes must contain some value")
    @JsonIgnore
    public boolean fileTypeSpecified() {
        return containsForcedFileType() || containsAllowedFileTypes();
    }

    @ValidationMethod(message = "Can not define both forcedFileType and allowedFileTypes")
    @JsonIgnore
    public boolean doesNotContainBothForcedAndAllowed() {
        return !(containsForcedFileType() && containsAllowedFileTypes());
    }

    @JsonIgnore
    private boolean containsForcedFileType() {
        return forcedFileType != null;
    }

    @JsonIgnore
    private boolean containsAllowedFileTypes() {
        return allowedFileTypes != null && !allowedFileTypes.isEmpty();
    }

    public ImageBuilder createBuilder() {
        initDirectory();
        return new ImageBuilder(directory, filenameFormat, forcedFileType, allowedFileTypes, resolutions);
    }

    private void initDirectory() {
        final File dir = new File(directory);
        if (!dir.exists() && !createDirectory) {
            throw new ImageUploaderDirectoryMissingException(String.format("Directory '%1$s' does not exist", directory));
        } else if (!dir.isDirectory()) {
            throw new ImageUploaderDirectoryMissingException(String.format("'%1$s' is not a directory", directory));
        } else if (!dir.canWrite()) {
            throw new ImageUploaderDirectoryMissingException(String.format("Can not write to directory '%1$s', insufficient permissions?", directory));
        }
        if (!dir.mkdirs()) {
            throw new ImageUploaderDirectoryMissingException(String.format("Could not create directory '%1$s'", directory));
        }
    }
}
