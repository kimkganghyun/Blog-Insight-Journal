package org.example.blognest.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.example.blognest.model.User;
import org.example.blognest.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    // 사용자 등록 처리
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        User registeredUser = userService.save(user);
        if (registeredUser != null) {
            Map<String, String> response = new HashMap<>();
            response.put("redirectUrl", "/profile?username=" + registeredUser.getUsername());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("회원가입 실패");
        }
    }

    // 사용자 이름 찾기
    @GetMapping("/find")
    public ResponseEntity<Optional<User>> findByUsername(@RequestParam String username) {
        return ResponseEntity.ok(userService.findByUsername(username));
    }

    // 아이디 중복 확인 여부
    @GetMapping("/check-username")
    public ResponseEntity<Boolean> checkUsername(@RequestParam String username) {
        boolean exists = userService.usernameExists(username);
        return ResponseEntity.ok(exists);
    }

    // 이메일 존재 확인 여부
    @GetMapping("/check-email")
    public ResponseEntity<Boolean> checkEmail(@RequestParam String email) {
        boolean exists = userService.emailExists(email);
        return ResponseEntity.ok(exists);
    }

    // 로그인 처리
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestParam String username, @RequestParam String password, HttpSession session) {
        Optional<User> user = userService.findByUsername(username);
        if (user.isPresent() && user.get().getPassword().equals(password)) {
            session.setAttribute("loggedInUser", user.get().getUsername());
            Map<String, String> response = new HashMap<>();
            response.put("redirectUrl", "/home");
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "로그인 실패"));
    }

    // 로그아웃 처리
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpSession session) {
        session.invalidate();
        return ResponseEntity.ok().build();
    }

    private String saveProfilePicture(MultipartFile profilePicture) {
        String fileName = UUID.randomUUID().toString() + "_" + profilePicture.getOriginalFilename();
        Path filePath = Paths.get("path/to/save/directory", fileName);
        try {
            Files.copy(profilePicture.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            return filePath.toString();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("프로필 저장 못했습니다.");
        }
    }

    // 프로필 저장 처리
    @PostMapping("/profile")
    public ResponseEntity<?> saveProfile(@RequestParam String username, @RequestParam(required = false) MultipartFile profilePicture, @RequestParam String description) {
        User user = userService.findByUsername(username).orElse(null);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("사용자 찾을 수 없음");
        }

        String profilePictureUrl;
        try {
            if (profilePicture != null && !profilePicture.isEmpty()) {
                profilePictureUrl = saveProfilePicture(profilePicture);
            } else {
                profilePictureUrl = "/images/blog_image.png";
            }
            user.setProfilePicture(profilePictureUrl);
            user.setDescription(description);

            userService.save(user);

            Map<String, String> response = new HashMap<>();
            response.put("redirectUrl", "/home");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("프로필 저장 실패: " + e.getMessage());
        }
    }

    // 현재 로그인된 사용자 정보 가져오기
    @GetMapping("/loggedInUser")
    public ResponseEntity<Map<String, String>> getLoggedInUser(HttpSession session) {
        String loggedInUser = (String) session.getAttribute("loggedInUser");
        Map<String, String> response = new HashMap<>();
        response.put("username", loggedInUser);
        return ResponseEntity.ok(response);
    }
}
