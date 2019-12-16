public class Query{
    private String queryText;
    private boolean needToRedirect;

    public Query(String queryText, boolean needToRedirect) {
        this.queryText = queryText;
        this.needToRedirect = needToRedirect;
    }

    public String getQueryText() {
        return queryText;
    }

    public void setQueryText(String queryText) {
        this.queryText = queryText;
    }

    public boolean isNeedToRedirect() {
        return needToRedirect;
    }

    public void setNeedToRedirect(boolean needToRedirect) {
        this.needToRedirect = needToRedirect;
    }

    @Override
    public boolean equals(Object obj) {
        return queryText.equals(obj);
    }

    @Override
    public String toString() {
        return queryText;
    }
}
