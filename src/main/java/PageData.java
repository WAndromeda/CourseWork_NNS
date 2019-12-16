import Entity.*;
import Interface.DateFunctions;

import java.util.ArrayList;
import java.util.Hashtable;

public class PageData {

    public static Integer maxAmountOfProductInBasket = 5;

    private DateFunctions dateFuncs = new DateFunctions();
    private long amount_of_basket;
    private long amount_of_favorite;
    private ArrayList<Category> categories;
    private ArrayList<PaymentMethod> paymentMethods;
    private Client client;
    private ArrayList<Favorite> favorites;
    private ArrayList<Basket> baskets;
    private Hashtable<Long, ArrayList<Integer> > hashCountProductRemain;
    private Product specificProduct;
    private Specification specification;
    private OrderShop specificOrderShop;
    private PaymentMethod specificPaymentMethod;
    private ArrayList<Product> products;
    private Category category;
    private ArrayList<OrderShop> orderShops;
    private Hashtable<Long, ArrayList<ProductsInOrder> > hashProductsInOrders;


    public PageData(long amount_of_basket, long amount_of_favorite, ArrayList<Category> categories, Client client, ArrayList<Favorite> favorites, ArrayList<Basket> baskets,
                    Product specificProduct, ArrayList<Product> products, Category category, ArrayList<OrderShop> orderShops, Hashtable<Long, ArrayList<ProductsInOrder>> hashProductsInOrders) {
        this.amount_of_basket = amount_of_basket;
        this.amount_of_favorite = amount_of_favorite;
        this.categories = categories;
        this.client = client;
        this.favorites = favorites;
        this.baskets = baskets;
        this.specificProduct = specificProduct;
        this.products = products;
        this.category = category;
        this.orderShops = orderShops;
        this.hashProductsInOrders = hashProductsInOrders;
    }

    public PageData(long amount_of_basket, long amount_of_favorite, ArrayList<Category> categories, Client client,
                    Product specificProduct, ArrayList<Product> products, Category category, ArrayList<OrderShop> orderShops, Hashtable<Long, ArrayList<ProductsInOrder>> hashProductsInOrders) {
        this.amount_of_basket = amount_of_basket;
        this.amount_of_favorite = amount_of_favorite;
        this.categories = categories;
        this.client = client;
        this.specificProduct = specificProduct;
        this.products = products;
        this.category = category;
        this.orderShops = orderShops;
        this.hashProductsInOrders = hashProductsInOrders;
    }

    public PageData(long amount_of_basket, long amount_of_favorite, ArrayList<Category> categories, Client client,
                    Product specificProduct, ArrayList<Product> products, ArrayList<OrderShop> orderShops, Hashtable<Long, ArrayList<ProductsInOrder>> hashProductsInOrders) {
        this.amount_of_basket = amount_of_basket;
        this.amount_of_favorite = amount_of_favorite;
        this.categories = categories;
        this.client = client;
        this.specificProduct = specificProduct;
        this.products = products;
        this.orderShops = orderShops;
        this.hashProductsInOrders = hashProductsInOrders;
    }

    public PageData(long amount_of_basket, long amount_of_favorite, ArrayList<Category> categories, Client client, ArrayList<OrderShop> orderShops, Hashtable<Long, ArrayList<ProductsInOrder>> hashProductsInOrders) {
        this.amount_of_basket = amount_of_basket;
        this.amount_of_favorite = amount_of_favorite;
        this.categories = categories;
        this.client = client;
        this.orderShops = orderShops;
        this.hashProductsInOrders = hashProductsInOrders;
    }

    public PageData(long amount_of_basket, long amount_of_favorite, ArrayList<Category> categories, Client client) {
        this.amount_of_basket = amount_of_basket;
        this.amount_of_favorite = amount_of_favorite;
        this.categories = categories;
        this.client = client;
    }

    public PageData(long amount_of_basket, long amount_of_favorite, ArrayList<Category> categories) {
        this.amount_of_basket = amount_of_basket;
        this.amount_of_favorite = amount_of_favorite;
        this.categories = categories;
        client = null;
    }

    public Specification getSpecification() {
        return specification;
    }

    public void setSpecification(Specification specification) {
        this.specification = specification;
    }

    public ArrayList<Category> getCategories() {
        return categories;
    }

    public void setCategories(ArrayList<Category> categories) {
        this.categories = categories;
    }

    public long getAmount_of_basket() {
        return amount_of_basket;
    }

    public void setAmount_of_basket(long amount_of_basket) {
        this.amount_of_basket = amount_of_basket;
    }

    public long getAmount_of_favorite() {
        return amount_of_favorite;
    }

    public void setAmount_of_favorite(long amount_of_favorite) {
        this.amount_of_favorite = amount_of_favorite;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public ArrayList<OrderShop> getOrderShops() {
        return orderShops;
    }

    public void setOrderShops(ArrayList<OrderShop> orderShops) {
        this.orderShops = orderShops;
    }

    public Hashtable<Long, ArrayList<ProductsInOrder>> getHashProductsInOrders() {
        return hashProductsInOrders;
    }

    public void setHashProductsInOrders(Hashtable<Long, ArrayList<ProductsInOrder>> hashProductsInOrders) {
        this.hashProductsInOrders = hashProductsInOrders;
    }

    public Product getSpecificProduct() {
        return specificProduct;
    }

    public void setSpecificProduct(Product specificProduct) {
        this.specificProduct = specificProduct;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public ArrayList<Favorite> getFavorites() {
        return favorites;
    }

    public void setFavorites(ArrayList<Favorite> favorites) {
        this.favorites = favorites;
    }

    public ArrayList<Basket> getBaskets() {
        return baskets;
    }

    public void setBaskets(ArrayList<Basket> baskets) {
        this.baskets = baskets;
    }

    public ArrayList<PaymentMethod> getPaymentMethods() {
        return paymentMethods;
    }

    public OrderShop getSpecificOrderShop() {
        return specificOrderShop;
    }

    public void setSpecificOrderShop(OrderShop specificOrderShop) {
        this.specificOrderShop = specificOrderShop;
    }

    public PaymentMethod getSpecificPaymentMethod() {
        return specificPaymentMethod;
    }

    public void setSpecificPaymentMethod(PaymentMethod specificPaymentMethod) {
        this.specificPaymentMethod = specificPaymentMethod;
    }

    public DateFunctions getDateFuncs() {
        return dateFuncs;
    }

    public void setDateFuncs(DateFunctions dateFuncs) {
        this.dateFuncs = dateFuncs;
    }

    public int getAmountOfAllProductInBasket(){
        int cnt = 0;
        for (Basket b : baskets){
            cnt += b.getAmount_of_product();
        }
        return cnt;
    }

    public long getFactualSumOfOrder(final long id_order){
        long sum = 0;
        for (ProductsInOrder p: hashProductsInOrders.get(id_order)){
            sum += (p.getFactualPrice() * p.getAmount_of_product() );
        }
        return sum;
    }

    public long getSumNoDiscountOfOrder(final long id_order){
        long sum = 0;
        for (ProductsInOrder p: hashProductsInOrders.get(id_order)){
            sum += ( p.getPrice() * p.getAmount_of_product() );
        }
        return sum;
    }

    public long getSumOrderWithDelivery(OrderShop order){
        return getFactualSumOfOrder(order.getId()) + order.getDelivery_price();
    }

    public long getSumNoDiscountOrderWithDelivery(OrderShop order){
        return getSumNoDiscountOfOrder(order.getId()) + order.getDelivery_price();
    }

    public long countAmountOfProductsInOrder(final long id_order){
        long cnt = 0;
        for (ProductsInOrder p: hashProductsInOrders.get(id_order)){
            cnt += p.getAmount_of_product();
        }
        return cnt;
    }

    public long getSummaryPrice(){
        return  (Math.min(sumPriceWithDiscountOfBasket(), sumPriceOfBasket()));
    }

    public void setPaymentMethods(ArrayList<PaymentMethod> paymentMethods) {
        this.paymentMethods = paymentMethods;
    }

    public boolean addProductsInSpecificOrder(final Long id_order, ArrayList<ProductsInOrder> productsInOrder){
        if (hashProductsInOrders == null)
            hashProductsInOrders = new Hashtable<>();
        if (specificOrderShop.getId() == id_order){
                hashProductsInOrders.put(id_order, productsInOrder);
                return true;
        }
        return false;
    }

    public ArrayList<ProductsInOrder> getProductInOrderByIDOrder(final long id_order){
        if (hashProductsInOrders == null)
            return null;
        return hashProductsInOrders.get(id_order);
    }

    public boolean addProductsInOrders(final Long id_order, ArrayList<ProductsInOrder> productsInOrder){
        if (hashProductsInOrders == null)
            hashProductsInOrders = new Hashtable<>();
        for (OrderShop order : orderShops){
            if (order.getId() == id_order){
                hashProductsInOrders.put(id_order, productsInOrder);
                return true;
            }
        }
        return false;
    }

    public long getSumPriceOfOrder(long id_order){
        if (hashProductsInOrders == null)
            return -1;
        long sum = 0;
        System.out.println("ID_ORDER = " + id_order);
        for (ProductsInOrder p : hashProductsInOrders.get(id_order)){
            System.out.println(p.getFactualPrice());
            System.out.println(p.getAmount_of_product());
            sum += ( p.getFactualPrice() * p.getAmount_of_product());
        }
        if (sum == 0)
            return -1;
        return sum;
    }

    public boolean checkAmountOfProductRemain(){
        if (baskets == null || products == null || baskets.size() != products.size())
            return false;
        hashCountProductRemain = new Hashtable<>();
        boolean  isOKIter = false;
        for (Product p: products){
            for (Basket b: baskets){
                if (p.getId() == b.getId_product()){
                    hashCountProductRemain.put(p.getId(), getArrayListOfNums(p.getAmount()));
                    isOKIter = true;
                    break;
                }
            }
            if (!isOKIter){
                hashCountProductRemain = null;
                return false;
            }
            else isOKIter = false;
        }
        return true;
    }

    public ArrayList<Integer> getListAmountProductRemainID(Long id_product){
        if (hashCountProductRemain == null)
            if (!checkAmountOfProductRemain())
                return null;
        return hashCountProductRemain.get(id_product);
    }

    public long getAmountProductBasketIDProduct(Long id_product){
        for (Basket b: baskets){
            if (b.getId_product() == id_product)
                return b.getAmount_of_product();
        }
        return -1;
    }

    public long sumPriceOfBasket(){
        if (baskets == null || products == null || baskets.size() != products.size())
            return -1;
        if (hashCountProductRemain == null)
            if (!checkAmountOfProductRemain())
                return -1;
        long sum = 0;
        boolean  isOKIter = false;
        for (Product p: products){
            for (Basket b: baskets){
                if (p.getId() == b.getId_product()){
                    sum += ( p.getPrice() * getAmountProductBasketIDProduct(p.getId()));
                    isOKIter = true;
                    break;
                }
            }
            if (!isOKIter) return  -1;
            else isOKIter = false;
        }
        return sum;
    }

    public long sumPriceWithDiscountOfBasket(){
        if (baskets == null || products == null || baskets.size() != products.size())
            return -1;
        if (hashCountProductRemain == null)
            if (!checkAmountOfProductRemain())
                return -1;
        long sumWithDiscount = 0;
        boolean  isOKIter = false;
        for (Product p: products){
            for (Basket b: baskets){
                if (p.getId() == b.getId_product()){
                    if (p.getPrice_with_discount() >= 99 && p.getPrice_with_discount() < p.getPrice())
                        sumWithDiscount += ( p.getPrice_with_discount() * getAmountProductBasketIDProduct(p.getId()));
                    else
                        sumWithDiscount += ( p.getPrice() * getAmountProductBasketIDProduct(p.getId()));
                    isOKIter = true;
                    break;
                }
            }
            if (!isOKIter) return  -1;
            else isOKIter = false;
        }
        return sumWithDiscount;
    }

    private ArrayList<Integer> getArrayListOfNums(long amount){
        ArrayList<Integer> nums = new ArrayList<>();
        if (amount > maxAmountOfProductInBasket)
            amount = maxAmountOfProductInBasket;
        else
            if (amount < 1)
                return null;
        for (int i = 1; i <= amount; i++){
            nums.add(i);
        }
        return nums;
    }

    public Hashtable<Long, ArrayList<Integer>> getHashCountProductRemain() {
        return hashCountProductRemain;
    }

    public void setHashCountProductRemain(Hashtable<Long, ArrayList<Integer>> hashCountProductRemain) {
        this.hashCountProductRemain = hashCountProductRemain;
    }
}
