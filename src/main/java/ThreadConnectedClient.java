import Entity.*;
import Exceptions.InputDataException;
import Interface.ClientInterface;
import Interface.Encoding;
import Service.*;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.LocalDate;
import org.json.JSONObject;
import org.springframework.security.crypto.bcrypt.BCrypt;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Socket;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.regex.Pattern;


public class ThreadConnectedClient implements Runnable, Encoding {

    private final int ConfirmationEmailAmountOfChecks = 10;

    public static final String httpCode200 = "200 OK",
            httpCode301 = "301 Moved Permanently", httpCode302 = "302 Found", httpCode307 = "307 Temporary Redirect",
    httpCode400 = "400 Bad Request", httpCode401 = "401 Unauthorized", httpCode404 = "404 Not Found", httpCode409 = "409 Conflict",
            httpCode422 = "422 Unprocessable Entity", httpCode424 = "424 Failed Dependency", httpCode500 = "500 Internal Server Error", httpCode550 = "550 Message was not accepted";

    enum TYPE{GET, GET_DATA, POST, DELETE, PUT, NULL}

    private Socket clientSocket;
    private int numOfClient;
    private Hashtable<String, String> httpQuery, params, cookie;
    private boolean fullRequest;
    private TYPE type;
    private String requestBody, extension;
    private Client authorizedClient = null;

    public ThreadConnectedClient(Socket clientSocket, int numOfClient){
        this.clientSocket = clientSocket;
        this.numOfClient = numOfClient;
        type = TYPE.NULL;
        fullRequest = false;
        params = new Hashtable<>();
        httpQuery = new Hashtable<>();
        cookie = new Hashtable<>();
    }

    @Override
    public void run() {
        try{
            DataInputStream dataInputStream = new DataInputStream(clientSocket.getInputStream());
            DataOutputStream dataOutputStream = new DataOutputStream(clientSocket.getOutputStream());
            System.out.println("/------------------------------------------\\");
            System.out.println("Server started read messages from CLIENT №" + numOfClient + " ||| " + new Date().toString());
            System.out.println("/------------------------------------------\\\n");
            while (!clientSocket.isClosed()) { //доступен ли клиент;
                Response response;
                if (readInputHeaders(dataInputStream) == null)
                    response = new Response("", httpCode400, "");
                else
                    response = analyseHTTP();
                if (response != null)
                    try {
                        writeResponse(getData(response), dataOutputStream);
                    }catch (TemplateException tEX){
                        System.out.println("FreeMarker - Error while processing template: " + response.getPathToResource());
                        tEX.printStackTrace();
                    }
                httpQuery.clear();
                clientSocket.close();
            }
            System.out.println("\n/------------------------------------------\\\n");
            System.out.println("CLIENT №" + numOfClient + " left from server!  ||| " + new Date().toString());
            System.out.println("/------------------------------------------\\\n");
            Server.removeClient();
        }catch (IOException ex) {
            try {
                System.out.println("ERROR happened on connection with CLIENT №" + numOfClient);
                System.out.println(ex);
                clientSocket.close();
                Server.removeClient();
            }catch (IOException IOex){
                System.out.println(IOex);
            }
        }

    }

    private String readInputHeaders(DataInputStream dataInputStream) throws IOException  {
        String query = "", str = "";
        if (!fullRequest) {
            int cnt = 0; // количество строк считано в процессе обработки HTTP запроса
            BufferedReader br = new BufferedReader(new InputStreamReader(dataInputStream));
            while (true){
                str = br.readLine();
                if (str == null || str.trim().length() == 0)
                    break;
                query += str + "\r\n";
                if (cnt == 0) {
                    int index = str.lastIndexOf("HTTP");
                    if (index == -1)
                        return null;
                    str = str.substring(0, index);
                    String mainQuery = str.substring(str.indexOf(" ") + 1);
                    mainQuery = mainQuery.substring(1);
                    System.out.println("MAIN QUERY  = " + mainQuery);
                    mainQuery = extractParams(mainQuery);
                    mainQuery = URLDecoder.decode(mainQuery, StandardCharsets.UTF_8.name());
                    httpQuery.put(str.split("\\s")[0], mainQuery);
                    type = identifyTYPE(str.split("\\s")[0].trim());
                }else
                    httpQuery.put(str.split(":")[0].trim(), str.split(":")[1].trim());
                cnt++;
            }

            System.out.println("\nОкончание считывания запроса!\n");
            System.out.println(query);
            System.out.println(httpQuery);
            System.out.println(params);
            extractCookie(httpQuery.get("Cookie"));
            System.out.println(cookie);
            fullRequest = true;
        }
        return query;
    }

    private TYPE identifyTYPE(String type){
        switch (type) {
            case "GET":
                return  TYPE.GET;
            case "POST":
                return TYPE.POST;
            case "PUT":
                return TYPE.PUT;
            case "DELETE":
                return TYPE.DELETE;
            default:
                return TYPE.NULL;
        }
    }

    private String extractParams(String value) throws UnsupportedEncodingException {
        if (value.contains("?")) {
            String paramsStr = value.split("\\?")[1];
            value = value.split("\\?")[0];
            if (paramsStr.contains("&")) {
                String[] p = paramsStr.split("&");
                for (int i = 0; i < p.length; i++)
                    if (p[i].contains("=") && p[i].split("=").length == 2) {
                        String parameterData = p[i].split("=")[1];
                        params.put(p[i].split("=")[0].trim(), URLDecoder.decode(parameterData, StandardCharsets.UTF_8.name()).trim());
                    }
            }
            else
                if (paramsStr.contains("=") && paramsStr.split("=").length >= 2) {
                    String param = paramsStr.substring(paramsStr.indexOf("=")+1);
                    System.out.println("PARAM = " + param);

                    params.put(paramsStr.split("=")[0].trim(), URLDecoder.decode(param, StandardCharsets.UTF_8.name()).trim());
                }
        }
        return value;
    }

    private void extractCookie(String value) throws UnsupportedEncodingException {
        if (value != null) {
            //System.out.println("\n\nVALUE = " + value + "\n\n");
            if (value.contains(";")) {
                String[] cookies = value.split(";");
                for (int i = 0; i < cookies.length; i++)
                    if (cookies[i].contains("=")) {
                        String parameterData = cookies[i].split("=")[1].trim();
                        cookie.put(cookies[i].split("=")[0].trim(), URLDecoder.decode(parameterData, StandardCharsets.UTF_8.name()).trim());
                    }
            } else if (value.contains("=")) {
                String param = value.substring(value.indexOf("=") + 1).trim();
                System.out.println("PARAM = " + param);
                cookie.put(value.split("=")[0].trim(), URLDecoder.decode(param, StandardCharsets.UTF_8.name()).trim());
            }
        }
    }

    private void writeResponse(Response response, DataOutputStream dataOutputStream) throws IOException {
        if (!response.getAdditionHeaders().trim().equals(""))
            response.setAdditionHeaders(response.getAdditionHeaders() + "\r\n");
        /*if (response.getDataString() != null && !response.getDataString().equals("") && type == TYPE.GET) {
           // response.setDataString(new String(response.getDataString().getBytes("windows-1251"), StandardCharsets.UTF_8));
            //System.out.println(response.getDataString() + "\n\n");
            //response.setDataString(response.getDataString().replaceAll("�\\?", character));
            //System.out.println(response.getDataString() + "\n\n");
        }*/
        String responseHeaders =
                "HTTP/1.1 " + response.getHTTPCode() +"\r\n" +
                "Date: " + new Date().toString() + "\r\n" +
                "Server: NNS_SHOP_Server\r\n" +
                "Content-Type: " + response.getTypeOfResource() + (response.getDataImage() != null ? "" : "; charset=UTF-8") + "\r\n" +
                "Content-Length: " + (response.getDataImage() == null ? response.getDataString().getBytes(StandardCharsets.UTF_8).length : response.getDataImage().length) + "\r\n" +
                response.getAdditionHeaders() +
                "Connection: close\r\n\r\n";
        //System.out.println(data + "\n\n");
        PrintWriter pW = new PrintWriter(new OutputStreamWriter(dataOutputStream, StandardCharsets.UTF_8.name()));
        pW.write(responseHeaders, 0, responseHeaders.length());
        if (response.getDataImage() == null) {
            pW.write(response.getDataString(), 0, response.getDataString().length());
            pW.flush();
            pW.close();
        }
        else{
            pW.flush();
            dataOutputStream.write(response.getDataImage(), 0, response.getDataImage().length);
            System.out.println("Должно было отправиться: " + response.getDataImage().length);
            dataOutputStream.flush();
            /*try {
                Thread.sleep(120000);
            }catch (InterruptedException ex){
                System.out.println(ex);
            }*/

        }
        dataOutputStream.close();
        System.out.println("\n------ RESPONSE WRITTEN ------\n");
    }

    private Response analyseHTTP() throws UnsupportedEncodingException {
        List<String> directoryAndFile = new LinkedList<>();
        if (type != TYPE.NULL) {

            if (cookie.get("login") != null && !cookie.get("login").trim().toLowerCase().equals("") && cookie.get("password") != null && !cookie.get("password").trim().equals("")) {
                String login = cookie.get("login").trim().toLowerCase(), password = cookie.get("password").trim();
                ClientService clientService = new ClientService();
                Client client = clientService.getClientByLogin(login);
                if ( client != null && client.getConfirmed() == Client.CONFIRMED.подтверждён && password.equals(client.getPassword()) ){
                    authorizedClient = client;
                }
            }

            String query = httpQuery.get(type.toString()).trim();
            switch (type) {
                case GET:
                    return responseGET(directoryAndFile, query);
                case POST:
                    return responsePOST(directoryAndFile, query);
                case DELETE:
                    return responseDELETE(directoryAndFile, query);
                case PUT:
                    return responsePUT(directoryAndFile, query);
                default:
                    return null;
            }
        }else
            return null;
    }



    private Query parseQuery(List<String> directoryAndFile, Query query){
        int cnt = 1; //Один такой символ есть всегда
        for (int i = 0; i < query.getQueryText().length(); i++){ //проверка количества символа '/'
            Character a = query.getQueryText().charAt(i);
            if (a.equals('/'))
                cnt++;
        }
        if (cnt > 1){
            for (String i : query.getQueryText().split("/"))
                if (!i.trim().equals(""))
                    directoryAndFile.add(i.trim());
                else
                    query.setNeedToRedirect(true);
        }
        if (directoryAndFile.size() != cnt && cnt != 1)
            query.setNeedToRedirect(true);
        if (directoryAndFile.isEmpty() && cnt > 1){
            query.setQueryText("");
            query.setNeedToRedirect(true);
        }
        return query;
    }

    private Response responseGET(List<String> directoryAndFile, String queryText){
        Query query = parseQuery(directoryAndFile, new Query(queryText, false));
        if (directoryAndFile.size() < 2) {
            if (!directoryAndFile.isEmpty())
                query.setQueryText(directoryAndFile.get(0));
        }else{
            query.setQueryText(getFullQuery(directoryAndFile));
        }
        extension = findExtension(query);
        String fullPath = findDirectoryOnExtension(extension);

        /*if (extension.equals("html")){
            query.setQueryText(query.getQueryText().substring(0, query.getQueryText().lastIndexOf(".")));
            return redirectToAnyResource(query, "html");
        }*/

        HashMap<String, PageData> page_data = new HashMap<>();
        PageData pageData = new PageData(0,0 , (ArrayList<Category>) new CategoryService().getLimitCategories(8));


        if (authorizedClient != null) {
            pageData.setClient(authorizedClient);
            pageData.setAmount_of_basket(new BasketService().getAmountOfBasketIDClient(authorizedClient.getId()));
            pageData.setAmount_of_favorite(new FavoriteService().getAmountOfFavoritesIDClient(authorizedClient.getId()));
        }
        page_data.put("PageData", pageData);
        if (query.equals("") || query.equals("/")) {
            extension = "html";
            fullPath = findDirectoryOnExtension(extension);
            ArrayList<Product> products =  (ArrayList<Product>) new ProductService().getAllProducts();
            if (authorizedClient != null) {
                pageData.setBaskets((ArrayList<Basket>) new BasketService().getBasketsByIDСlient(authorizedClient.getId()));
            }
            pageData.setProducts(products);
            if (query.isNeedToRedirect())
                return redirectToAnyResource(query);
            query.setQueryText("index.html");
            return new Response(fullPath + query.toString(), httpCode200, findType(extension),
                    "", page_data);
        }

        if (query.equals("product")){
            extension = "html";
            fullPath = findDirectoryOnExtension(extension);
            Pattern pattern = Pattern.compile("[\\d]+");
            System.out.println("ID = " + params.get("id"));
            System.out.println("ID_matches = " + (pattern.matcher(params.get("id").trim()).matches()));
            if (params.get("id") != null && (pattern.matcher(params.get("id").trim()).matches())) {
                Product product = new ProductService().getProductByID(Long.parseLong(params.get("id").trim()));
                if (product == null){
                    return redirectTo404();
                }
                //System.out.println("ХАРАКТЕРИСТИКИ: \n" + new JSONObject(product.getSpecification()).keys());
                if (authorizedClient != null){
                    pageData.setBaskets((ArrayList<Basket>) new BasketService().getBasketsByIDСlient(authorizedClient.getId()));
                    pageData.setFavorites((ArrayList<Favorite>) new FavoriteService().getFavoritesByIDClient(authorizedClient.getId()));
                }
                pageData.setSpecificProduct(product);
                pageData.setSpecification( new Specification( new JSONObject(product.getSpecification()) ) );
                if (query.isNeedToRedirect())
                    return redirectToAnyResource(query);
                query.setQueryText("product.html");
                return new Response(fullPath + query.toString(), httpCode200, findType(extension),
                        "", page_data);
            }else{
                query.setQueryText("");
                return redirectToAnyResource(query);
            }
        }

        if (query.equals("category")){
            extension = "html";
            fullPath = findDirectoryOnExtension(extension);
            Pattern pattern = Pattern.compile("[\\d]+");
            System.out.println("ID = " + params.get("id"));
            System.out.println("ID_matches = " + (pattern.matcher(params.get("id").trim()).matches()));
            if (params.get("id") != null && (pattern.matcher(params.get("id").trim()).matches())) {
                Long id = Long.parseLong(params.get("id").trim());
                Category category = new CategoryService().getCategoryByID(id);
                if (category == null){
                    return redirectTo404();
                }
                pageData.setCategory(category);
                ArrayList<Product> products = (ArrayList<Product>) new ProductService().getProductsByIDCategory(id);
                if (authorizedClient != null) {
                    pageData.setBaskets((ArrayList<Basket>) new BasketService().getBasketsByIDСlient(authorizedClient.getId()));
                }
                pageData.setProducts(products);
                if (query.isNeedToRedirect())
                    return redirectToAnyResource(query);
                query.setQueryText("category.html");
                return new Response(fullPath + query.toString(), httpCode200, findType(extension),
                        "", page_data);
            }else{
                query.setQueryText("");
                return redirectToAnyResource(query);
            }
        }

        if (query.equals("my-account")){
            extension = "html";
            fullPath = findDirectoryOnExtension(extension);
            if (authorizedClient != null) {
                if (query.isNeedToRedirect())
                    return redirectToAnyResource(query);
                OrderShopService shopService = new OrderShopService();
                pageData.setOrderShops((ArrayList<OrderShop>) shopService.getLimitOrdersShops(3, pageData.getClient().getId()));
                ProductsInOrderService productsInOrderService = new ProductsInOrderService();
                for (OrderShop order : pageData.getOrderShops()){
                    pageData.addProductsInOrders(order.getId(), (ArrayList<ProductsInOrder>) productsInOrderService.getProductsInOrderByIDOrder(order.getId()));
                }
                query.setQueryText("my-account.html");
                return new Response(fullPath + query.toString(), httpCode200, findType(extension),
                        "", page_data);
            }else{
                return redirectToLoginIfNotAuthorized();
            }
        }

        if (query.equals("favorite")){
            extension = "html";
            fullPath = findDirectoryOnExtension(extension);
            if (authorizedClient != null) {
                ArrayList<Product> productsInFavorite = (ArrayList<Product>) new ProductService().getProductInFavoriteByIDClient(pageData.getClient().getId());
                pageData.setBaskets((ArrayList<Basket>) new BasketService().getBasketsByIDСlient(authorizedClient.getId()));
                pageData.setProducts(productsInFavorite);
                if (query.isNeedToRedirect())
                    return redirectToAnyResource(query);
                query.setQueryText("favorite.html");
                return new Response(fullPath + query.toString(), httpCode200, findType(extension),
                        "", page_data);
            }else{
                return redirectToLoginIfNotAuthorized();
            }
        }

        if (query.equals("basket")){
            extension = "html";
            fullPath = findDirectoryOnExtension(extension);
            if (authorizedClient != null) {
                ProductService productService = new ProductService();
                ArrayList<Product> productsInBasket = (ArrayList<Product>) productService.getProductInBasketByIDClient(pageData.getClient().getId());
                BasketService basketService = new BasketService();
                ArrayList<Basket> basketsInf = (ArrayList<Basket>) basketService.getBasketsByIDСlient(pageData.getClient().getId());
                pageData.setBaskets(basketsInf);
                pageData.setProducts(productsInBasket);
                if (query.isNeedToRedirect())
                    return redirectToAnyResource(query);
                query.setQueryText("basket.html");
                return new Response(fullPath + query.toString(), httpCode200, findType(extension),
                        "", page_data);
            }else{
                return redirectToLoginIfNotAuthorized();
            }
        }

        if (query.equals("order-history")){
            extension = "html";
            fullPath = findDirectoryOnExtension(extension);
            if (authorizedClient != null) {
                pageData.setOrderShops((ArrayList<OrderShop>) new OrderShopService().getOrdersShopByIDClient(authorizedClient.getId()));
                ProductsInOrderService productsInOrderService = new ProductsInOrderService();
                for (OrderShop order : pageData.getOrderShops()){
                    pageData.addProductsInOrders(order.getId(), (ArrayList<ProductsInOrder>) productsInOrderService.getProductsInOrderByIDOrder(order.getId()));
                }
                if (query.isNeedToRedirect())
                    return redirectToAnyResource(query);
                query.setQueryText("order-history.html");
                return new Response(fullPath + query.toString(), httpCode200, findType(extension),
                        "", page_data);
            }else{
                return redirectToLoginIfNotAuthorized();
            }
        }

        if (query.equals("order-details")){
            extension = "html";
            fullPath = findDirectoryOnExtension(extension);
            Pattern pattern = Pattern.compile("[\\d]+");
            if (authorizedClient != null) {
                if (params.get("id_order") == null || params.get("id_order").trim().equals("") || !pattern.matcher(params.get("id_order").trim()).matches()){
                    return redirectTo404();
                }
                Long id_order = Long.parseLong(params.get("id_order").trim());
                OrderShop order = new OrderShopService().getOrderShopByIDOrderAndClient(id_order, authorizedClient.getId());
                if (order == null){
                    return redirectTo404();
                }
                pageData.setSpecificOrderShop(order);
                pageData.setSpecificPaymentMethod(new PaymentMethodService().getPaymentByID(order.getId_payment_method()));
                pageData.addProductsInSpecificOrder(order.getId(), (ArrayList<ProductsInOrder>) new ProductsInOrderService().getProductsInOrderByIDOrder(order.getId()));
                if (query.isNeedToRedirect())
                    return redirectToAnyResource(query);
                query.setQueryText("order-details.html");
                return new Response(fullPath + query.toString(), httpCode200, findType(extension),
                        "", page_data);
            }else{
                return redirectToLoginIfNotAuthorized();
            }
        }

        if (query.equals("purchase")){
            extension = "html";
            fullPath = findDirectoryOnExtension(extension);
            if (authorizedClient != null) {
                ArrayList<Basket> baskets = (ArrayList<Basket>) new BasketService().getBasketsByIDСlient(authorizedClient.getId());
                if (baskets == null || baskets.isEmpty()){
                    query.setQueryText("basket");
                    return redirectToAnyResource(query);
                }
                pageData.setBaskets(baskets);
                pageData.setPaymentMethods((ArrayList<PaymentMethod>) new PaymentMethodService().getAllPaymentMethods());
                pageData.setProducts((ArrayList<Product>) new ProductService().getProductInBasketByIDClient(pageData.getClient().getId()));
                if (query.isNeedToRedirect())
                    return redirectToAnyResource(query);
                query.setQueryText("purchase.html");
                return new Response(fullPath + query.toString(), httpCode200, findType(extension),
                        "", page_data);
            }else{
                return redirectToLoginIfNotAuthorized();
            }
        }

        if (query.equals("purchase-success")){
            extension = "html";
            fullPath = findDirectoryOnExtension(extension);
            if (authorizedClient != null) {
                Pattern pattern = Pattern.compile("[\\d]+");
                if (params.get("id_order") != null && !params.get("id_order").trim().equals("") && pattern.matcher(params.get("id_order").trim()).matches()) {
                    Long id_order = Long.parseLong(params.get("id_order").trim());
                    OrderShop orderShop = new OrderShopService().getOrderShopByID(id_order);
                    if (orderShop == null || orderShop.getId_client() != authorizedClient.getId() )
                        return redirectTo404();
                    else
                        pageData.setSpecificOrderShop(orderShop);
                }else{
                    return redirectTo404();
                }
                if (query.isNeedToRedirect())
                    return redirectToAnyResource(query);
                query.setQueryText("purchase-success.html");
                return new Response(fullPath + query.toString(), httpCode200, findType(extension),
                        "", page_data);
            }else{
                return redirectToLoginIfNotAuthorized();
            }
        }

        if (query.equals("login")){
            extension = "html";
            fullPath = findDirectoryOnExtension(extension);
            if (pageData.getClient() == null) {
                if (query.isNeedToRedirect())
                    return redirectToAnyResource(query);
                query.setQueryText("login.html");
                return new Response(fullPath + query.toString(), httpCode200, findType(extension),
                        "", page_data);
            }else{
                return redirectIfAlreadyAuthorized();
            }
        }

        if (query.equals("signup")){
            extension = "html";
            fullPath = findDirectoryOnExtension(extension);
            if (pageData.getClient() == null) {
                if (query.isNeedToRedirect())
                    return redirectToAnyResource(query);
                query.setQueryText("signup.html");
                return new Response(fullPath + query.toString(), httpCode200, findType(extension),
                        "", page_data);
            }else{
                return redirectIfAlreadyAuthorized();
            }
        }

        if (query.equals("privacy-policy")){
            extension = "html";
            fullPath = findDirectoryOnExtension(extension);
            if (query.isNeedToRedirect())
                return redirectToAnyResource(query);
            query.setQueryText("privacy-policy.html");
            return new Response(fullPath + query.toString(), httpCode200, findType(extension),
                    "", page_data);

        }

        if (query.equals("signup-success")){
            extension = "html";
            fullPath = findDirectoryOnExtension(extension);
            if (authorizedClient == null) {
                if (query.isNeedToRedirect())
                    return redirectToAnyResource(query);
                query.setQueryText("signup-success.html");
                return new Response(fullPath + query.toString(), httpCode200, findType(extension),
                        "", page_data);
            }else{
                return redirectIfAlreadyAuthorized();
            }
        }

        if (query.equals("email-confirmation")){
            extension = "html";
            fullPath = findDirectoryOnExtension(extension);
            if (authorizedClient == null) {
                try {
                    String id = params.get("id");
                    //Без перенаправление при подтверждении email-адреса
                    if (id == null || query.isNeedToRedirect()) {
                        query.setQueryText("404.html");
                        return new Response(fullPath + query.toString(), httpCode404, findType(extension),
                                "", page_data);
                    }
                    int index = id.indexOf("_");
                    long id_client = Long.parseLong(id.substring(0, index));
                    ConfirmationKeyService keyService = new ConfirmationKeyService();
                    ConfirmationKey confirmationKey = keyService.getConfirmationKeyByIDClient(id_client);
                    if (confirmationKey == null) {
                        query.setQueryText("404.html");
                        return new Response(fullPath + query.toString(), httpCode404, findType(extension),
                                "", page_data);
                    }
                    //Если проверка прошла успешно и лимит проверок не исчерпан
                    ClientService clientService = new ClientService();
                    Client client = clientService.getClientByID(id_client);
                    if (client.getConfirmed() == Client.CONFIRMED.не_подтверждён) {
                        if (confirmationKey.getConfirmation_key().equals(id.substring(index + 1).trim()) && confirmationKey.getAmount_of_checks() < ConfirmationEmailAmountOfChecks) {
                            client.setConfirmed(Client.CONFIRMED.подтверждён);
                            clientService.updateClient(client);
                            keyService.deleteConfirmationKey(confirmationKey);
                        } //Если проверка была неуспешна (ошибка пользователя или бот), то увеличит количество проверки на 1
                        else {
                            confirmationKey.increaseAmount_of_checks();
                            //Если количество проверок исчерпано, то удалить клиента из базы данных (всё остальное необходимое удалится по CASCADE)
                            if (confirmationKey.getAmount_of_checks() >= ConfirmationEmailAmountOfChecks) {
                                clientService.deleteClient(new Client(id_client));
                            }  //Иначе просто обновить ключ-подтверждения
                            else {
                                keyService.updateConfirmationKey(confirmationKey);
                            }
                            query.setQueryText("404.html");
                            return new Response(fullPath + query.toString(), httpCode404, findType(extension),
                                    "", page_data);
                        }
                    } else {
                        query.setQueryText("404.html");
                        return new Response(fullPath + query.toString(), httpCode404, findType(extension),
                                "", page_data);
                    }
                } catch (Exception ex) {
                    query.setQueryText("404.html");
                    return new Response(fullPath + query.toString(), httpCode404, findType(extension),
                            "", page_data);
                }
                query.setQueryText("email-confirmation.html");
                return new Response(fullPath + query.toString(), httpCode200, findType(extension),
                        "", page_data);
            }else{
                return redirectIfAlreadyAuthorized();
            }
        }

        if (query.equals("exit")){
            if (query.isNeedToRedirect())
                return redirectTo404();
            extension = "html";
            fullPath = findDirectoryOnExtension(extension);
            if (authorizedClient != null) {
                String loginCookie = "Set-Cookie: login=1; Path=/; Max-Age=0\r\n";
                String passwordCookie = "Set-Cookie: password=1; Path=/; Max-Age=0\r\n";
                String location = "Location: /";
                String addHeaders = loginCookie + passwordCookie + location;
                return new Response("", httpCode307, findType(extension),
                        addHeaders, page_data);
            }else{
                return redirectToLoginIfNotAuthorized();
            }
        }

        if (query.equals("404")){
            extension = "html";
            fullPath = findDirectoryOnExtension(extension);
            if (query.isNeedToRedirect())
                return redirectToAnyResource(query);
            query.setQueryText("404.html");
            return new Response(fullPath + query.toString(), httpCode404, findType(extension),
                    "", page_data);
        }

        File file = new File(fullPath + query);
        System.out.println("QUERY = " + fullPath + query + "\n\n");
        if (file.exists() && !file.isDirectory() &&  !query.equals("404.html")){
            if (!query.isNeedToRedirect())
                return  directTo200(query, extension);
            else
                return redirectToAnyResource(query);
        }else{
            if (extension.equals("html") || extension.equals("ftl"))
                return redirectTo404NotHTML();
            else
                return redirectTo404();
        }

    }

    private Response responsePUT(List<String> directoryAndFile, String query){

        if (query.equals("basket/change-amount")) {
            if (authorizedClient != null) {
                Pattern pattern = Pattern.compile("[\\d]+");
                if (params.get("id_product") == null || params.get("id_product").trim().equals("") || !pattern.matcher(params.get("id_product").trim()).matches() ||
                        params.get("amount") == null || params.get("amount").trim().equals("") || !pattern.matcher(params.get("amount").trim()).matches()) {
                    return new Response("", httpCode422, "text/plain");
                }else {
                    Long id_product = Long.parseLong(params.get("id_product").trim());
                    Long amount = Long.parseLong(params.get("amount").trim());
                    ProductService productService = new ProductService();
                    Product product = productService.getProductByID(id_product);
                    Basket changeAmountBasket = new Basket(authorizedClient.getId(), id_product, amount);
                    BasketService basketService = new BasketService();
                    if (product != null && changeAmountBasket.getAmount_of_product() >= 1 && product.getAmount() >= changeAmountBasket.getAmount_of_product() && basketService.getProductInBasketByIDClientAndProduct(changeAmountBasket.getId_client(), changeAmountBasket.getId_product()) != null ) {
                        basketService.updateBasket(changeAmountBasket);
                        return new Response("", httpCode200, "text/plain", "");
                    }else{
                        return new Response("", httpCode422, "text/plain");
                    }
                }
            }else{
                return  redirectIfAlreadyAuthorized();
            }
        }

        return new Response("", httpCode400, "text/plain");
    }

    private Response responseDELETE(List<String> directoryAndFile, String query){

        if (query.equals("favorite")){
            if (authorizedClient != null) {
                Pattern pattern = Pattern.compile("[\\d]+");
                if (params.get("id_product") == null || params.get("id_product").trim().equals("") || !pattern.matcher(params.get("id_product").trim()).matches() ) {
                    return new Response("", httpCode422, "text/plain");
                }else {
                    Long id_product = Long.parseLong(params.get("id_product").trim());
                    Favorite deleteFavorite = new Favorite(authorizedClient.getId(), id_product);
                    FavoriteService favoriteService = new FavoriteService();
                    if (favoriteService.getFavoriteByIDClientAndProduct(deleteFavorite.getId_client(), deleteFavorite.getId_product()) != null ) {
                        favoriteService.deleteFavorite(deleteFavorite);
                        return new Response("", httpCode200, "text/plain", "");
                    }else{
                        return new Response("", httpCode422, "text/plain");
                    }
                }
            }else{
                return  redirectIfAlreadyAuthorized();
            }
        }

        if (query.equals("basket")){
            if (authorizedClient != null) {
                Pattern pattern = Pattern.compile("[\\d]+");
                if (params.get("id_product") == null || params.get("id_product").trim().equals("") || !pattern.matcher(params.get("id_product").trim()).matches() ) {
                    return new Response("", httpCode422, "text/plain");
                }else {
                    Long id_product = Long.parseLong(params.get("id_product").trim());
                    Basket deleteProductInBasket = new Basket(authorizedClient.getId(), id_product, 0);
                    BasketService basketService = new BasketService();
                    if (basketService.getProductInBasketByIDClientAndProduct(deleteProductInBasket.getId_client(), deleteProductInBasket.getId_product()) != null ) {
                        basketService.deleteBasket(deleteProductInBasket);
                        return new Response("", httpCode200, "text/plain", "");
                    }else{
                        return new Response("", httpCode422, "text/plain");
                    }
                }
            }else{
                return  redirectIfAlreadyAuthorized();
            }
        }

        return new Response("", httpCode400, "text/plain");
    }
    private Response responsePOST(List<String> directoryAndFile, String query) throws UnsupportedEncodingException {

        System.out.println("QUERY = " + query);

        if (query.equals("signup")) {
            if (params.get("surname") == null || params.get("name") == null
                    || params.get("email") == null || params.get("phone") == null
                    || params.get("sex") == null || params.get("date_of_birth") == null
                    || params.get("password") == null)
                return new Response("", httpCode422, "text/plain");
            String patronymic = params.get("patronymic") == null ? null : params.get("patronymic").trim();
            String spare_phone = params.get("spare_phone") == null ? null : params.get("spare_phone").trim().toLowerCase();
            Client.SEX sex;
            if (params.get("sex").toLowerCase().equals("мужской"))
                sex = Client.SEX.мужской;
            else if (params.get("sex").toLowerCase().equals("женский"))
                sex = Client.SEX.женский;
            else
                sex = Client.SEX.не_определён;
            LocalDate date = new LocalDate(Integer.parseInt(params.get("date_of_birth").split("\\.")[2].trim()),
                    Integer.parseInt(params.get("date_of_birth").split("\\.")[1].trim()), Integer.parseInt(params.get("date_of_birth").split("\\.")[0].trim()));
            Client newClient = new Client(params.get("surname").trim(), params.get("name").trim(), patronymic, params.get("email").trim().toLowerCase(),
                    params.get("phone").trim(), spare_phone, sex, date, new DateTime(), params.get("password").trim());
            ClientService clientService = new ClientService();
            ClientService.OccupiedData occupiedData = clientService.isClientDataAlreadyOccupied(newClient);
            if (occupiedData == ClientInterface.OccupiedData.NONE) {
                BlockSendingMailService blockSendingMailService = new BlockSendingMailService();
                ArrayList<BlockSendingMail> blockSendingMails = (ArrayList<BlockSendingMail>) blockSendingMailService.getAllBlockSendingMails();
                if (!blockSendingMails.isEmpty()) {
                    DateTime today = new DateTime();
                    DateTime blockingDateTime = blockSendingMails.get(0).getDatetime_block_until_date();
                    Duration duration = new Duration(blockingDateTime, today);
                    if (Math.abs(duration.getStandardHours()) < 24) {
                        JSONObject json = new JSONObject();
                        json.put("is_email_sent", "no");
                        json.put("hours_to_wait", (24 - Math.abs(duration.getStandardHours())));
                        Response response = new Response("", httpCode500, "application/json", "");
                        response.setDataString(json.toString());
                        return response;
                    } else {
                        blockSendingMailService.deleteBlockSendingMail(blockSendingMails.get(0));
                    }
                }
                newClient.setPassword(BCrypt.hashpw(newClient.getPassword(), BCrypt.gensalt(10)));
                clientService.addClient(newClient);
                ConfirmationKey confirmKey = new ConfirmationKey(newClient.getId(), RandomStringUtils.randomAlphanumeric(30), ConfirmationKey.IS_EMAIL_SENT.нет, (short) 0);
                ConfirmationKeyService keyService = new ConfirmationKeyService();
                keyService.addConfirmationKey(confirmKey);
                try {
                    String responseStr = EmailSender.sendConfirmationEmail(newClient, confirmKey);
                    if (responseStr.equals(httpCode500)) {
                        clientService.deleteClient(newClient);
                        blockSendingMailService.addBlockSendingMail(new BlockSendingMail(0, new DateTime()));
                        JSONObject json = new JSONObject();
                        json.put("is_email_sent", "no");
                        json.put("hours_to_wait", 24);
                        Response response = new Response("", httpCode500, "application/json", "");
                        response.setDataString(json.toString());
                        return response;
                    }else if (responseStr.equals(httpCode550)){
                        clientService.deleteClient(newClient);
                        return new Response("", httpCode550, "text/plain", "");
                    }else {
                        confirmKey.setIs_email_sent(ConfirmationKey.IS_EMAIL_SENT.да);
                        keyService.updateConfirmationKey(confirmKey);
                    }
                } catch (InputDataException | NullPointerException ex) {
                    ex.printStackTrace();
                    clientService.deleteClient(newClient);
                    return new Response("", httpCode422, "text/plain", "");
                }
                return new Response("", httpCode200, "text/plain", "");
            } else {
                System.out.println("\n\n" + occupiedData.name() + "\n\n");
                JSONObject json = new JSONObject();
                json.put("occupied_data", occupiedData.name().trim().toLowerCase());
                System.out.println("\n\n" + json.toString() + "\n\n");
                Response response = new Response("", httpCode409, "application/json", "");
                response.setDataString(json.toString());
                return response;
            }
        }

        if (query.equals("login")) {
            if (params.get("login") == null || params.get("login").trim().toLowerCase().equals("") || params.get("password") == null || params.get("password").trim().equals(""))
                return new Response("", httpCode422, "text/plain");
            String login = params.get("login").trim().toLowerCase(), password = params.get("password").trim();
            ClientService clientService = new ClientService();
            Client client = clientService.getClientByLogin(login);
            if (client == null || client.getConfirmed() == Client.CONFIRMED.не_подтверждён || !BCrypt.checkpw(password, client.getPassword()))
                return new Response("", httpCode422, "text/plain");
            else {
                /*JSONObject json = new JSONObject();
                json.put("login",);
                json.put("password", );*/
                System.out.println("LOGIN = " + URLEncoder.encode(login.trim().toLowerCase(), StandardCharsets.UTF_8.name()).trim());
                String loginCookie = "Set-Cookie: login=" + URLEncoder.encode(login.trim().toLowerCase(), StandardCharsets.UTF_8.name()).trim() + "; Path=/; Max-Age=604800" + "\r\n";
                String passwordCookie = "Set-Cookie: password=" + URLEncoder.encode(client.getPassword().trim(), StandardCharsets.UTF_8.name()).trim().trim() + "; Path=/; Max-Age=604800";
                Response response = new Response("", httpCode200, "text/plain", loginCookie + passwordCookie);
                //response.setDataString(json.toString());
                return response;
            }
        }

        if (query.equals("favorite")) {
            if (authorizedClient != null) {
                Pattern pattern = Pattern.compile("[\\d]+");
                if (params.get("id_product") == null || params.get("id_product").trim().equals("") || !pattern.matcher(params.get("id_product").trim()).matches()) {
                    return new Response("", httpCode422, "text/plain");
                } else {
                    Long id_product = Long.parseLong(params.get("id_product").trim());
                    ProductService productService = new ProductService();
                    Favorite addFavorite = new Favorite(authorizedClient.getId(), id_product);
                    FavoriteService favoriteService = new FavoriteService();
                    if (productService.getProductByID(id_product) != null && favoriteService.getFavoriteByIDClientAndProduct(addFavorite.getId_client(), addFavorite.getId_product()) == null) {
                        favoriteService.addFavorite(addFavorite);
                        return new Response("", httpCode200, "text/plain", "");
                    } else {
                        return new Response("", httpCode422, "text/plain");
                    }

                }
            } else {
                return new Response("", httpCode200, "text/plain", "");
            }
        }

        if (query.equals("basket")) {
            if (authorizedClient != null) {
                Pattern pattern = Pattern.compile("[\\d]+");
                if (params.get("id_product") == null || params.get("id_product").trim().equals("") || !pattern.matcher(params.get("id_product").trim()).matches()) {
                    return new Response("", httpCode422, "text/plain");
                } else {
                    Long id_product = Long.parseLong(params.get("id_product").trim());
                    ProductService productService = new ProductService();
                    Product product = productService.getProductByID(id_product);
                    Basket addProductInBasket = new Basket(authorizedClient.getId(), id_product, 1);
                    BasketService basketService = new BasketService();
                    if (product != null && product.getAmount() > 0) {
                        if (basketService.getProductInBasketByIDClientAndProduct(addProductInBasket.getId_client(), addProductInBasket.getId_product()) == null)
                            basketService.addBasket(addProductInBasket);
                        return new Response("", httpCode200, "text/plain", "");
                    } else
                        return new Response("", httpCode422, "text/plain");
                }
            }else {
                return new Response("", httpCode200, "text/plain", "");
            }
        }

        if (query.equals("purchase")) {
            if (authorizedClient != null) {
                Pattern pattern = Pattern.compile("[\\d]+");
                if (    params.get("recipient_name") == null || params.get("recipient_email") == null
                        || params.get("recipient_phone") == null || params.get("id_payment_method") == null || !pattern.matcher(params.get("id_payment_method")).matches()
                        || params.get("address_shop") == null){
                    return new Response("", httpCode422, "text/plain");
                } else {
                    int id_payment_method = Integer.parseInt(params.get("id_payment_method").trim());
                    if (new PaymentMethodService().getPaymentByID(id_payment_method) == null)
                        return new Response("", httpCode422, "text/plain");
                    String name = params.get("recipient_name").trim() ;
                    String email = params.get("recipient_email").trim().toLowerCase();
                    String phone = params.get("recipient_phone").trim().toLowerCase();
                    String addressShop = params.get("address_shop").trim();

                    BasketService basketService = new BasketService();
                    ProductService productService = new ProductService();
                    ArrayList<Product> productsInBasket = (ArrayList<Product>) productService.getProductInBasketByIDClient(authorizedClient.getId());
                    ArrayList<Basket> baskets = (ArrayList<Basket>) basketService.getBasketsByIDСlient(authorizedClient.getId());

                    OrderShopService orderService = new OrderShopService();
                    long id_order = orderService.addOrderShop(new OrderShop(authorizedClient.getId(), id_payment_method, name, email, phone, addressShop, new LocalDate().plusDays(7), new DateTime()));
                    ProductsInOrderService productsInOrderService = new ProductsInOrderService();
                    for (Product p : productsInBasket){
                        for (Basket b : baskets){
                            if (b.getId_product() == p.getId()){
                                p.setAmount(p.getAmount() - b.getAmount_of_product());
                                ProductsInOrder productInOrder = new ProductsInOrder(id_order, p.getId(), p.getName(), p.getPrice(), p.getPrice_with_discount(), b.getAmount_of_product());
                                productsInOrderService.addProductInOrder(productInOrder);
                                basketService.deleteBasket(b);
                                productService.updateProduct(p);
                            }
                        }
                    }
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("id_order", id_order);
                    Response response = new Response("", httpCode200, findType("json"), "");
                    response.setDataString(jsonObject.toString());
                    return response;

                }
            }
            else {
                return new Response("", httpCode401, "text/plain");
            }
        }

        return new Response("", httpCode400, "text/plain");
    }

    private String getFullQuery(List<String> directoryAndFile){
        String fancyQuery = "";
        for (int i = 0; i < directoryAndFile.size(); i++) {
            fancyQuery += directoryAndFile.get(i).trim();
            if (i != directoryAndFile.size()-1)
                fancyQuery += "/";
        }
        return fancyQuery;
    }

    private Response directTo200(Query query, String extension){
        return new Response(findDirectoryOnExtension(extension) + query, httpCode200,
                findType(extension.trim()), "");
    }

    private Response redirectTo404NotHTML(){
        type = TYPE.GET_DATA;
        return new Response("", httpCode404, "text/plain",
                "");
    }

    private Response redirectTo404(){
        return new Response("/html/404.html", httpCode301, "text/html",
                "Location: /404");
    }

    private String findExtension(Query query){
        int index = query.getQueryText().lastIndexOf("."); //Получаем индекс последней точки для определения расширения файла
        String extension = "";
        if (index != -1) { //Если точка присутствует (т.е. присутствует расширение)
            extension = query.getQueryText().substring(index + 1).trim();
        }
        return extension;
    }

    //При попытке войти на страницу входа или регистрации авторизированного пользователя перекинет в его аккаунт (/my-account)
    private Response redirectIfAlreadyAuthorized(){
        return  new Response("", httpCode307, findType("html"),
                "Location: /my-account");
    }

    //При попытке войти на страницу аккаунта или другие ресурсы, требующие авторизации, неавторизированного пользователя перекинет на страницу "Входа" (/login)
    private Response redirectToLoginIfNotAuthorized(){
        return  new Response("", httpCode307, findType("html"),
                "Location: /login");
    }

    private Response redirectToAnyResource(Query query){
         return  new Response("", httpCode302, "text/plain",
                "Location: /" + query);
    }


    //Для чтения всех текстовых файлов используется технология FreeMarker для подставновки шаблонов в текст
    private Response getData(Response response) throws UnsupportedEncodingException, TemplateException {
        switch (type) {
            case GET:
                if (response.getTypeOfResource().contains("jpeg") || response.getTypeOfResource().contains("png")) {
                    try {
                        BufferedImage img = ImageIO.read(new File(response.getPathToResource()));
                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        ImageIO.write(img, extension, byteArrayOutputStream);
                        response.setDataImage(byteArrayOutputStream.toByteArray());
                    } catch (FileNotFoundException fnfEx) {
                        System.out.println(fnfEx);
                    } catch (IOException IOex) {
                        System.out.println(IOex);
                    }
                } else {
                    if (response.getTypeOfResource().contains("javascript")){
                        try {
                            String script = "";
                            FileInputStream inputStream = new FileInputStream(response.getPathToResource());
                            try {
                                script = IOUtils.toString(inputStream);
                            } finally {
                                inputStream.close();
                            }
                            response.setDataString(script);
                        }catch ( IOException ex){
                            ex.printStackTrace();
                        }
                    }else
                        if (!response.getPathToResource().trim().equals("")){
                            String contains = "";
                            try {
                                Configuration cfg = new Configuration(Configuration.VERSION_2_3_20);
                                //cfg.setEncoding(Locale.ENGLISH, StandardCharsets.UTF_8.name());
                                Template t = cfg.getTemplate(response.getPathToResource());
                                StringWriter sW = new StringWriter();
                                t.process(response.getContextTemplate(), sW);
                                contains = sW.toString();

                            } catch (FileNotFoundException ex) {
                                System.out.println(ex);
                            } catch (IOException IOex) {
                                System.out.println(IOex);
                            }
                            response.setDataString(contains);/*new String(contains.getBytes(StandardCharsets.ISO_8859_1.name()), StandardCharsets.UTF_8.name())*/
                        }else{
                            response.setDataString("");
                        }
                }
                break;
            case GET_DATA:
            case POST:
            case PUT:
            case DELETE:
                //  response.setDataString(URLEncoder.encode(response.getDataString() , StandardCharsets.UTF_8.name()));
                break;

        }
        return response;
    }

    private String findType(String extension){
        System.out.println("EXT = " + extension);
        switch (extension.toLowerCase()){
            case "json":
                return "application/json";
            case "js":
                return "application/javascript";
            case "html":
                return "text/html";
            case "css":
                return "text/css";
            case "jpg":
            case "jpeg":
                return "image/jpeg";
            case "png":
                return "image/png";
            case "txt":
            case "ftl":
            default:
                return "text/plain";
        }
    }

    private String findDirectoryOnExtension(String extension){
        String startDirectory = "./src";
        switch (extension.toLowerCase().trim()){
            case "html":
                return startDirectory + "/html/";
            case "ftl":
            case "freemarker":
            case "js":
            case "css":
            case "jpg":
            case "jpeg":
            case "png":
            default:
                return "";
        }
    }
}
