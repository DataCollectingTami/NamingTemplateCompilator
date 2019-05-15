

public class TagType {
    String tagName;
    String tagValue;
    int position;
    int occurrence;

    public TagType(String tagName, int position) {
        this.tagName = tagName;
        this.tagValue = tagValue;
        this.position = position;
    }

    public TagType(String tagName, String tagValue) {
        this.tagName = tagName;
        this.tagValue = tagValue;
        this.position = position;
    }
    public TagType(String tagName, String tagValue,int position) {
        this.tagName = tagName;
        this.tagValue = tagValue;
        this.position = position;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public String getTagValue() {
        return tagValue;
    }

    public void setTagValue(String tagValue) {
        this.tagValue = tagValue;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getOccurrence() {
        return occurrence;
    }

    public void setOccurrence(int occurrence) {
        this.occurrence = occurrence;
    }

    @Override
    public String toString() {
        return "TagType{" +
                "tagName='" + tagName + '\'' +
                ", tagValue='" + tagValue + '\'' +
                ", position=" + position +
                '}';
    }
}

