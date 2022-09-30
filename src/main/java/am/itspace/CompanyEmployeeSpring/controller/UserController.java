package am.itspace.CompanyEmployeeSpring.controller;


import am.itspace.CompanyEmployeeSpring.entity.Company;
import am.itspace.CompanyEmployeeSpring.entity.User;
import am.itspace.CompanyEmployeeSpring.repository.UserRepository;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;


@Controller
public class UserController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Value("${company.employee.spring.images.folder}")
    private String folderPath;

    @GetMapping("/users")
    public String employees(ModelMap modelMap) {
        List<User> users = userRepository.findAll();
        modelMap.addAttribute("users", users);
        return "users";
    }

    @GetMapping("/users/add")
    public String addEmployeePage(ModelMap modelMap) {
        return "addUser";
    }

    @PostMapping("/users/add")
    public String addEmployee(@ModelAttribute User user,
                              @RequestParam("userImage") MultipartFile file) throws IOException {
        if (!file.isEmpty() && file.getSize() > 0) {
            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            File newFile = new File(folderPath + File.separator + fileName);
            file.transferTo(newFile);
            user.setPicUrl(fileName);
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return "redirect:/users";
    }

    @GetMapping(value = "/users/getImage", produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody byte[] getImage(@RequestParam("fileName") String fileName) throws IOException {
        InputStream inputStream = new FileInputStream(folderPath + File.separator + fileName);
        return IOUtils.toByteArray(inputStream);
    }

    @GetMapping("/users/delete")
    public String delete(@RequestParam("id") int id) {
        userRepository.deleteById(id);
        return "redirect:/users";
    }
}
