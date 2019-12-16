import java.util.HashMap;

public class Response {
    private String pathToResource;
    private String HTTPCode;
    private String typeOfResource;
    private String additionHeaders = "";
    private String dataString = "";
    private byte[] dataImage = null;
    private HashMap contextTemplate;

    public Response() {
        this.pathToResource = "";
        this.HTTPCode = "";
        this.typeOfResource = "";
        this.additionHeaders = "";
        dataString = "";
        dataImage = null;
        contextTemplate = new HashMap();
    }


    public Response(String pathToResource) {
        this.pathToResource = pathToResource;
    }

    public Response(String pathToResource, HashMap contextTemplate) {
        this.pathToResource = pathToResource;
        this.contextTemplate = contextTemplate;
    }

    public Response(String pathToResource, String HTTPCode, String typeOfResource) {
        this.pathToResource = pathToResource;
        this.HTTPCode = HTTPCode;
        this.typeOfResource = typeOfResource;
    }

    public Response(String pathToResource, String HTTPCode, String typeOfResource, HashMap contextTemplate) {
        this.pathToResource = pathToResource;
        this.HTTPCode = HTTPCode;
        this.typeOfResource = typeOfResource;
        this.contextTemplate = contextTemplate;
    }

    public Response(String pathToResource, String HTTPCode, String typeOfResource, String additionHeaders, String dataString) {
        this.pathToResource = pathToResource;
        this.HTTPCode = HTTPCode;
        this.typeOfResource = typeOfResource;
        this.additionHeaders = additionHeaders;
        this.dataString = dataString;
    }

    public Response(String pathToResource, String HTTPCode, String typeOfResource, String additionHeaders) {
        this.pathToResource = pathToResource;
        this.HTTPCode = HTTPCode;
        this.typeOfResource = typeOfResource;
        this.additionHeaders = additionHeaders;
    }

    public Response(String pathToResource, String HTTPCode, String typeOfResource, String additionHeaders, HashMap contextTemplate) {
        this.pathToResource = pathToResource;
        this.HTTPCode = HTTPCode;
        this.typeOfResource = typeOfResource;
        this.additionHeaders = additionHeaders;
        this.contextTemplate = contextTemplate;
    }

    public Response(String pathToResource, String HTTPCode, String typeOfResource, String additionHeaders, byte[] dataImage, HashMap contextTemplate) {
        this.pathToResource = pathToResource;
        this.HTTPCode = HTTPCode;
        this.typeOfResource = typeOfResource;
        this.additionHeaders = additionHeaders;
        this.dataImage = dataImage;
        this.contextTemplate = contextTemplate;
    }

    public Response(String pathToResource, String HTTPCode, String typeOfResource, String additionHeaders, String dataString, HashMap contextTemplate) {
        this.pathToResource = pathToResource;
        this.HTTPCode = HTTPCode;
        this.typeOfResource = typeOfResource;
        this.additionHeaders = additionHeaders;
        this.dataString = dataString;
        this.contextTemplate = contextTemplate;
    }

    public Response(String pathToResource, String HTTPCode, String typeOfResource, String additionHeaders, String dataString, byte[] dataImage, HashMap contextTemplate) {
        this.pathToResource = pathToResource;
        this.HTTPCode = HTTPCode;
        this.typeOfResource = typeOfResource;
        this.additionHeaders = additionHeaders;
        this.dataString = dataString;
        this.dataImage = dataImage;
        this.contextTemplate = contextTemplate;
    }

    public String getPathToResource() {
        return pathToResource;
    }

    public void setPathToResource(String pathToResource) {
        this.pathToResource = pathToResource;
    }

    public String getHTTPCode() {
        return HTTPCode;
    }

    public void setHTTPCode(String HTTPCode) {
        this.HTTPCode = HTTPCode;
    }

    public String getTypeOfResource() {
        return typeOfResource;
    }

    public void setTypeOfResource(String typeOfResource) {
        this.typeOfResource = typeOfResource;
    }

    public String getAdditionHeaders() {
        return additionHeaders;
    }

    public void setAdditionHeaders(String additionHeaders) {
        this.additionHeaders = additionHeaders;
    }

    public HashMap getContextTemplate() {
        return contextTemplate;
    }

    public void setContextTemplate(HashMap contextTemplate) {
        this.contextTemplate = contextTemplate;
    }

    public String getDataString() {
        return dataString;
    }

    public void setDataString(String dataString) {
        this.dataString = dataString;
    }

    public byte[] getDataImage() {
        return dataImage;
    }

    public void setDataImage(byte[] dataImage) {
        this.dataImage = dataImage;
    }
}
