package argus.model;

public class DataFilePair {

    public DataFilePair(String dataFileName, String metaFileName) {
        this.dataFileName = dataFileName;
        this.metaFileName = metaFileName;
    }
    
    public String getDataFileName() {
        return dataFileName;
    }

    public String getMetaFileName() {
        return metaFileName;
    }

    private String dataFileName;
    private String metaFileName;
}
