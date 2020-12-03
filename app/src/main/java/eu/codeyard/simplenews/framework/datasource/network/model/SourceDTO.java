package eu.codeyard.simplenews.framework.datasource.network.model;

public class SourceDTO {

    private String name;

    public SourceDTO(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
