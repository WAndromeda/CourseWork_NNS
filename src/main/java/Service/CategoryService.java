package Service;

import Dao.CategoryDao;
import Entity.Category;

import java.io.UnsupportedEncodingException;
import java.util.List;

public class CategoryService {

    private CategoryDao categoryDao = new CategoryDao();

    public CategoryService() {}

    public Category getCategoryByID(final long id){
        return categoryDao.getCategoryByID(id);
    }

    public List<Category> getAllCategories(){
        return categoryDao.getAllCategories();
    }

    public List<Category> getLimitCategories(int limit){
        return categoryDao.getLimitCategories(limit);
    }

    public void deleteCategory(Category category) throws UnsupportedEncodingException {
        categoryDao.deleteCategory(category);
    }

    public void updateCategory(Category category) throws UnsupportedEncodingException {
        categoryDao.updateCategory(category);
    }

    public void addCategory(Category category) throws UnsupportedEncodingException {
        categoryDao.addCategory(category);
    }

}
