package whs.jo20046.beans;

public class Data {

    String[] urls = new String[3];
    String[] notFoundTexts = new String[3];

    public Data() {
        for (int i = 0, urlsLength = urls.length; i < urlsLength; i++) {
            setUrl(i, "");
        }
        for (int i = 0, notFoundTextsLength = notFoundTexts.length; i < notFoundTextsLength; i++) {
            setNotFoundText(i, "");
        }
    }

    public String[] getUrls() {
        return urls;
    }

    public void setUrls(String[] urls) {
        this.urls = urls;
    }

    public String getUrl(int index) {
        return index >= 0 && index < urls.length ? urls[index] : "Index " + index + " out of bounds";
    }

    public boolean setUrl(int index, String newValue) {
        if (index >= 0 && index < urls.length) {
            urls[index] = newValue;
            return true;
        }
        return false;
    }

    public String[] getNotFoundTexts() {
        return notFoundTexts;
    }

    public void setNotFoundTexts(String[] notFoundTexts) {
        this.notFoundTexts = notFoundTexts;
    }

    public String getNotFoundText(int index) {
        return index >= 0 && index < notFoundTexts.length ? notFoundTexts[index] : "Index " + index + " out of bounds";
    }

    public boolean setNotFoundText(int index, String newValue) {
        if (index >= 0 && index < notFoundTexts.length) {
            notFoundTexts[index] = newValue;
            return true;
        }
        return false;
    }
}
