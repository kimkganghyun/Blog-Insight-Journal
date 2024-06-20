package org.example.blognest.service;

import lombok.RequiredArgsConstructor;
import org.example.blognest.model.User;
import org.example.blognest.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    // 비밀 번호
    public User register(User user) {
        return userRepository.save(user);
    }

    // 사용자 이름으로 사용자 찾기
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    // 사용자 중복 확인 여부
    public boolean usernameExists(String username) {
        return userRepository.existsByUsername(username);
    }

    // 이메일 주소가 존재 확인 여부
    public boolean emailExists(String email) {
        return userRepository.existsByEmail(email);
    }

    // 사용자 ID로 사용자 찾기
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    public User save(User user) {
        return userRepository.save(user);
    }



    // 프로필
    private String saveProfilePicture(MultipartFile profilePicture) {
        String fileName = UUID.randomUUID().toString() + "_" + profilePicture.getOriginalFilename();
        Path filePath = Paths.get("src/main/resources/static/images", fileName); // 경로 확인 및 수정
        try {
            Files.copy(profilePicture.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            return "/images/" + fileName;
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("프로필 사진을 저장할 수 없습니다.");
        }
    }

}
