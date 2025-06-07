package kr.ac.hansung.cse.ProductManageAssignment2.controller;
import jakarta.annotation.PostConstruct;
import kr.ac.hansung.cse.ProductManageAssignment2.entity.Role;
import kr.ac.hansung.cse.ProductManageAssignment2.entity.User;
import kr.ac.hansung.cse.ProductManageAssignment2.repository.UserRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserRepository userRepository;

    public AdminController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // 관리자만 접근 가능한 전체 사용자 목록
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/users")
    public String viewAllUsers(Model model) {
        List<User> allUsers = userRepository.findAll();
        model.addAttribute("users", allUsers);
        return "users";
    }

    @PostConstruct
    public void setAdmin() {
        String adEmail = "admin@hansung.ac.kr";
        String adPassword = "1234";

        if (userRepository.findByEmail(adEmail).isEmpty()) {
            User admin = new User();
            admin.setEmail(adEmail);
            admin.setPassword(new org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder().encode(adPassword));
            admin.setRole(Role.ROLE_ADMIN);
            userRepository.save(admin);
        }
    }
}

