package com.develop.pieceofcake.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.develop.pieceofcake.dto.ProductDTO;
import com.develop.pieceofcake.model.Category;
import com.develop.pieceofcake.model.Product;
import com.develop.pieceofcake.service.CategoryService;
import com.develop.pieceofcake.service.ProductService;

@Controller //Annotation
public class AdminController {

    public static String uploadDir = System.getProperty("user.dir") + "/src/main/resources/static/productImages";

    @Autowired
    CategoryService categoryService;
    @Autowired
    ProductService productService;

    // CATEGORIES SERVICEs SECTION
    @GetMapping("/admin") //Maps the pages to each other
    public String adminHome(){
        return "adminHome";
    }

    @GetMapping("/admin/categories")
    public String getCat(Model model){
        model.addAttribute("categories", categoryService.getAllCategory());
        return "categories";
    }

    @GetMapping("/admin/categories/add")
    public String getCatAdd(Model model){
        model.addAttribute("category",new Category());
        return "categoriesAdd";
    }
    
    @PostMapping("/admin/categories/add")
    public String postCatAdd(@ModelAttribute("category") Category category){
        categoryService.addCategory(category);
        return "redirect:/admin/categories";
    }

    @GetMapping("/admin/categories/delete/{id}")
    public String deleteCat(@PathVariable int id){
        categoryService.removeCategoryById(id);
        return "redirect:/admin/categories";
    }

    @GetMapping("/admin/categories/update/{id}")
    public String updateCat(@PathVariable int id, Model model){
        Optional<Category> category = categoryService.getCategoryById(id);
        if (category.isPresent()){
            model.addAttribute("category", category.get());
            return "categoriesAdd";
        }
        else return "404";  
    }

    // PRODUCTS SECTION
    @GetMapping("/admin/products")
    public String products(Model model){
        model.addAttribute("products", productService.getAllProduct());
        return "products";
    }

    @GetMapping("/admin/products/add")
    public String getProduct(Model model){
        model.addAttribute("productDTO", new ProductDTO());
        model.addAttribute("categories", categoryService.getAllCategory());
        return "productsAdd";
    }

    @PostMapping("/admin/products/add") //After adding the products information
    public String productAdd(@ModelAttribute("productDTO") ProductDTO productDTO, 
                            @RequestParam("productImage") MultipartFile file, 
                            @RequestParam("imgName")String imgName) throws IOException {
        Product product = new Product();
        product.setId(productDTO.getId());
        product.setName(productDTO.getName());
        product.setCategory(categoryService.getCategoryById(productDTO.getCategoryId()).get());
        product.setPrice(productDTO.getPrice());
        product.setWeight(productDTO.getWeight());
        product.setDescription(productDTO.getDescription());
        
        //Creating UUID for Image
        String imageUUID;
        if(!file.isEmpty()){
            imageUUID = file.getOriginalFilename();
            Path fileNameAndPath = Paths.get(uploadDir, imageUUID); //Creating file path and name
            Files.write(fileNameAndPath, file.getBytes()); //Writing the actual file in Binary (in bytes form) 
        }
        else {
            imageUUID = imgName;
        }
        product.setImageName(imageUUID);
        productService.addProduct(product);

        return "redirect:/admin/products";
        }

    @GetMapping("/admin/product/delete/{id}")
    public String deleteProduct(@PathVariable long id){
        productService.removeProductById(id);
        return "redirect:/admin/products";
    }

    @GetMapping("/admin/product/update/{id}") //Reverse Engineering The Above 
    public String updateProduct(@PathVariable long id, Model model){
        Product product = productService.getProductById(id).get();
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(product.getId());
        productDTO.setName(product.getName());
        productDTO.setCategoryId(product.getCategory().getId());
        productDTO.setPrice(product.getPrice());
        productDTO.setWeight(product.getWeight());
        productDTO.setDescription(product.getDescription());
        productDTO.setImageName(product.getImageName());

        model.addAttribute("categories", categoryService.getAllCategory());
        model.addAttribute("productDTO", productDTO);
        return "productsAdd";
    }

}
