package kr.ac.hansung.cse.ProductManageAssignment2.controller;

import jakarta.validation.Valid;
import kr.ac.hansung.cse.ProductManageAssignment2.entity.Role;
import kr.ac.hansung.cse.ProductManageAssignment2.entity.User;
import kr.ac.hansung.cse.ProductManageAssignment2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class SignupController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public SignupController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // 회원가입 폼
    @GetMapping("/signup")
    public String signupForm(Model model) {
        model.addAttribute("user", new User());
        return "signup";
    }

    // 회원가입 처리 (ROLE_USER)
    @PostMapping("/signup")
    public String signupSubmit(@Valid @ModelAttribute("user") User user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "signup";
        }
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            model.addAttribute("emailExists", true);
            return "signup";
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.ROLE_USER); // 단일 권한 설정
        userRepository.save(user);

        return "redirect:/login?signup=true";
    }
}
