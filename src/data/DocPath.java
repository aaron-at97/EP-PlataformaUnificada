package data;

import java.io.IOException;
import java.util.Objects;

final public class DocPath {

    private final String path;

    public DocPath(String code) {
        this.path = code;
    }

    public String getPath() throws Exception {
        if (!compPathCode())
            throw new Exception("The Path is not valid. \n");
        return path;
    }

    public Boolean compPathCode() {
        return path != null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DocPath docPath = (DocPath) o;
        return Objects.equals(path, docPath.path);
    }

    @Override
    public int hashCode() {
        return Objects.hash(path);
    }

    @Override
    public String toString() {
        return "DocPath{" +
                "path='" + path + '\'' +
                '}';
    }
}
