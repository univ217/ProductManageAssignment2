package kr.ac.hansung.cse.ProductManageAssignment2.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import kr.ac.hansung.cse.ProductManageAssignment2.entity.Product;
import kr.ac.hansung.cse.ProductManageAssignment2.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    // 상품 목록 조회
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')") // ROLE_USER 이상 접근 가능
    @GetMapping
    public String viewHomePage(Model model, HttpSession session) {
        List<Product> listProducts = productService.listAll();
        model.addAttribute("listProducts", listProducts);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("userEmail", auth.getName());

        // 세션 메시지 한 번만 꺼내기
        String loginMsg = (String) session.getAttribute("loginSuccessMessage");
        if (loginMsg != null) {
            model.addAttribute("loginSuccessMessage", loginMsg);
            session.removeAttribute("loginSuccessMessage");
        }
        return "product_list";  // product_list.html
    }

    // 상품 등록 폼
    @PreAuthorize("hasRole('ADMIN')") // ROLE_ADMIN만 접근 가능
    @GetMapping("/new")
    public String showNewProductForm(Model model) {
        model.addAttribute("product", new Product());
        return "new_product";  // new_product.html
    }

    // 상품 등록 처리
    @PreAuthorize("hasRole('ADMIN')") // ROLE_ADMIN만 접근 가능
    @PostMapping("/save")
    public String saveProduct(@Valid @ModelAttribute("product") Product product,
                              BindingResult result,
                              Model model) {
        if (result.hasErrors()) {
            return (product.getId() == null) ? "new_product" : "edit_product";
        }
        productService.save(product);
        return "redirect:/products";
    }

    // 상품 수정 폼
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/edit/{id}")
    public String showEditProductForm(@PathVariable(name="id") Long id, Model model) {
        Product product = productService.get(id);
        model.addAttribute("product", product);
        return "edit_product";  // edit_product.html
    }

    // 상품 수정 처리
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/update/{id}")
    public String updateProduct(@PathVariable("id") Long id,
                                @Valid @ModelAttribute("product") Product product,
                                BindingResult result) {
        if (result.hasErrors()) {
            return "edit_product";
        }
        product.setId(id);
        productService.save(product);
        return "redirect:/products";
    }

    // 상품 삭제
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/delete/{id}")
    public String deleteProduct(@PathVariable("id") Long id) {
        productService.delete(id);
        return "redirect:/products";
    }
}
