package amin.shop.app.controllers.api.products;

import amin.shop.app.entities.products.Product;
import amin.shop.app.enums.ResponseStatus;
import amin.shop.app.helper.ui.ServiceResponse;
import amin.shop.app.helper.uimodels.ProductVM;
import amin.shop.app.services.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {
    @Autowired
    private ProductService service;

    /**
     * pageination.
     *
     * @Return ServiceResponse<Product>(e)
     */
    @GetMapping("/getAll")
    public ServiceResponse<Product> getAll(@RequestParam Integer pageSize,
                                           @RequestParam Integer pageNumber) {
        try {
            List<Product> result = service.getAll(pageSize, pageNumber);
            long totalCount = service.getAllCount();
            return new ServiceResponse<Product>(ResponseStatus.SUCCESS, result, totalCount);
        } catch (Exception e) {
            return new ServiceResponse<Product>(e);
        }
    }

/*    @GetMapping("/find")
    public ServiceResponse<Product> find(@RequestParam long cid) {
        try {
            List<Product> result = service.findAllByCategory(cid);
            return new ServiceResponse<Product>(ResponseStatus.SUCCESS, result);
        } catch (Exception e) {
            //not god this exception handle. you need to search how exception handling with annotation and save to database(create new entities ExceptionLogs) or write filter,
            //and return << ServiceResponse<...>(e);
            return new ServiceResponse<Product>(e);
        }

    }*/

    //Find 6 products that were added by new date.
    @GetMapping("/newProducts")
    public ServiceResponse<ProductVM> newProducts() {
        try {
            List<ProductVM> result = service.findTop6ByOrderByAddDateDesc();
            return new ServiceResponse<ProductVM>(ResponseStatus.SUCCESS, result);
        } catch (Exception e) {
            return new ServiceResponse<ProductVM>(e);
        }

    }

    //Find 6 popular products.
    @GetMapping("/popularProducts")
    public ServiceResponse<ProductVM> popularProduct() {
        try {
            List<ProductVM> result = service.findTop6ByOrderByVisitCountDesc();
            return new ServiceResponse<ProductVM>(ResponseStatus.SUCCESS, result);
        } catch (Exception e) {
            return new ServiceResponse<ProductVM>(e);
        }

    }

    //Find 6 cheapest products.
    @GetMapping("/cheapestProducts")
    public ServiceResponse<ProductVM> cheapestProduct() {
        try {
            List<ProductVM> result = service.findTop6ByOrderByPriceAsc();
            return new ServiceResponse<ProductVM>(ResponseStatus.SUCCESS, result);
        } catch (Exception e) {
            return new ServiceResponse<ProductVM>(e);
        }

    }

    //Find 6 cheapest products.
    @GetMapping("/expensiveProducts")
    public ServiceResponse<ProductVM> expensiveProduct() {
        try {
            List<ProductVM> result = service.findTop6ByOrderByPriceDesc();
            return new ServiceResponse<ProductVM>(ResponseStatus.SUCCESS, result);
        } catch (Exception e) {
            return new ServiceResponse<ProductVM>(e);
        }

    }

    /**
     * pagination and find by categoryId(cid).
     */
    @GetMapping("/getAll/{cid}")
    public ServiceResponse<ProductVM> getAll(@RequestParam Integer pageSize,
                                             @RequestParam Integer pageNumber,
                                             @PathVariable long cid) {
        try {
            List<ProductVM> result = service.getAllByCategoryId(cid, pageSize, pageNumber);
            long totalCount = service.getAllCountByCategoryId(cid);
            return new ServiceResponse<ProductVM>(ResponseStatus.SUCCESS, result, totalCount);
        } catch (Exception e) {
            return new ServiceResponse<ProductVM>(e);
        }
    }

    @GetMapping("/search")
    public ServiceResponse<ProductVM> search(@RequestParam String key) {
        try {
            List<ProductVM> result = service.search(key);
            return new ServiceResponse<ProductVM>(ResponseStatus.SUCCESS, result);
        } catch (Exception e) {
            return new ServiceResponse<ProductVM>(e);
        }
    }

    @GetMapping("info/{id}")
    public ServiceResponse<ProductVM> getById(@PathVariable long id) {
        try {
            Product result = service.getById(id);
            //return only ID's.
            return new ServiceResponse<ProductVM>(ResponseStatus.SUCCESS, new ProductVM(result));
        } catch (Exception e) {
            return new ServiceResponse<ProductVM>(e);
        }
    }

    @PostMapping("/")
    public ServiceResponse<Product> add(@RequestBody ProductVM data) {
        try {
            Product result = service.add(data);
            return new ServiceResponse<Product>(ResponseStatus.SUCCESS, result);
        } catch (Exception e) {
            return new ServiceResponse<Product>(e);
        }
    }

    @PutMapping("/")
    public ServiceResponse<Product> update(@RequestBody ProductVM data) {
        try {
            Product result = service.update(data);
            return new ServiceResponse<Product>(ResponseStatus.SUCCESS, result);
        } catch (Exception e) {
            return new ServiceResponse<Product>(e);
        }
    }

    @DeleteMapping("/{id}")
    public ServiceResponse<Boolean> delete(@PathVariable long id) {
        try {
            boolean result = service.deleteById(id);
            return new ServiceResponse<Boolean>(ResponseStatus.SUCCESS, result);
        } catch (Exception e) {
            return new ServiceResponse<Boolean>(e);
        }
    }

}
