package Entity;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class Specification {
    private JSONObject jsonObject;
    public ArrayList<String> headers = new ArrayList<>();
    public HashMap<String, HashMap<String, String> > headersContent = new HashMap<>();
    public HashMap<String, ArrayList<String>> headersAndSubHeaders = new HashMap<>();

    public Specification() {
    }

    public Specification(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
        Iterator<String> headersIter = jsonObject.keys();
        while (headersIter.hasNext()){
            String headerT = headersIter.next();
            headers.add(headerT);
            JSONObject subHeadersJSON = jsonObject.getJSONObject(headerT);
            Iterator<String> subHeadersIter = subHeadersJSON.keys();
            HashMap<String, String> subHeadersHash = new HashMap<>();
            ArrayList<String> subHeaders = new ArrayList<>();
            while (subHeadersIter.hasNext()){
                String subT = subHeadersIter.next();
                subHeaders.add(subT);
                subHeadersHash.put(subT, subHeadersJSON.getString(subT));
            }
            headersAndSubHeaders.put(headerT, subHeaders);
            headersContent.put(headerT, subHeadersHash);
        }
        headers.sort((o1, o2) -> {
            if (o1.toLowerCase().contains("допол") || o2.toLowerCase().contains("общие")) return 1;
            if (o1.toLowerCase().contains("общие") || o2.toLowerCase().contains("допол")) return -1;
            return 0;
        });
    }

    public ArrayList<String> getSubHeadersByHeader(String header){
        return headersAndSubHeaders.get(header);
    }

    public String getSubHeaderContentByHeaderAndSubHeader(String header, String subHeader){
        return headersContent.get(header).get(subHeader);
    }

    public Specification(ArrayList<String> headers) {
        this.headers = headers;
    }

    public Specification(ArrayList<String> headers, HashMap<String, HashMap<String, String>> headersContent) {
        this.headers = headers;
        this.headersContent = headersContent;
    }



    public ArrayList<String> getHeaders() {
        return headers;
    }

    public void setHeaders(ArrayList<String> headers) {
        this.headers = headers;
    }

    public HashMap<String, HashMap<String, String>> getHeadersContent() {
        return headersContent;
    }

    public void setHeadersContent(HashMap<String, HashMap<String, String>> headersContent) {
        this.headersContent = headersContent;
    }


    public HashMap<String, ArrayList<String>> getHeadersAndSubHeaders() {
        return headersAndSubHeaders;
    }

    public void setHeadersAndSubHeaders(HashMap<String, ArrayList<String>> headersAndSubHeaders) {
        this.headersAndSubHeaders = headersAndSubHeaders;
    }

    public JSONObject getJsonObject() {
        return jsonObject;
    }

    public void setJsonObject(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }
}
