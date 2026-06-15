package com.hanpyeon.academyapi.config;

import com.hanpyeon.academyapi.account.entity.Member;
import com.hanpyeon.academyapi.account.repository.MemberRepository;
import com.hanpyeon.academyapi.dir.dao.Directory;
import com.hanpyeon.academyapi.dir.dao.DirectoryRepository;
import com.hanpyeon.academyapi.security.PasswordHandler;
import com.hanpyeon.academyapi.security.Role;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements ApplicationRunner {

    private final MemberRepository memberRepository;
    private final DirectoryRepository directoryRepository;
    private final PasswordHandler passwordHandler;

    @Value("${application.admin.account.id}")
    private String adminPhoneNumber;

    @Value("${application.admin.account.password}")
    private String adminPassword;

    @Value("${server.local.storage}")
    private String storagePath;

    @Value("${server.local.chunk.storage}")
    private String chunkStoragePath;

    @Override
    @Transactional
    public void run(ApplicationArguments args) {
        log.info("Starting data initialization...");

        // 파일 저장소 디렉토리 생성
        createStorageDirectoriesIfNotExists();

        // 관리자 계정 생성
        createAdminAccountIfNotExists();

        // 기본 디렉토리 생성
        createDefaultDirectoriesIfNotExists();

        log.info("Data initialization completed.");
    }

    private void createAdminAccountIfNotExists() {
        if (memberRepository.findMemberByPhoneNumberAndRemovedIsFalse(adminPhoneNumber).isEmpty()) {
            log.info("Creating admin account with phone number: {}", adminPhoneNumber);

            Member admin = Member.builder()
                    .name("관리자")
                    .phoneNumber(adminPhoneNumber)
                    .encryptedPassword(passwordHandler.getEncodedPassword(adminPassword))
                    .role(Role.ADMIN)
                    .registeredDate(LocalDateTime.now())
                    .build();

            memberRepository.save(admin);
            log.info("Admin account created successfully.");
        } else {
            log.info("Admin account already exists. Skipping creation.");
        }
    }

    private void createDefaultDirectoriesIfNotExists() {
        Member admin = memberRepository.findMemberByPhoneNumberAndRemovedIsFalse(adminPhoneNumber)
                .orElseThrow(() -> new IllegalStateException("Admin account must exist before creating directories"));

        // 루트 디렉토리 생성
        if (directoryRepository.findDirectoryByPath("/").isEmpty()) {
            log.info("Creating root directory: /");

            Directory rootDir = new Directory(admin, "/", true, true);
            directoryRepository.save(rootDir);
            log.info("Root directory created successfully.");
        } else {
            log.info("Root directory already exists. Skipping creation.");
        }

        // /teachers 디렉토리 생성
        if (directoryRepository.findDirectoryByPath("/teachers/").isEmpty()) {
            log.info("Creating teachers directory: /teachers/");

            Directory teachersDir = new Directory(admin, "/teachers/", true, true);
            directoryRepository.save(teachersDir);
            log.info("Teachers directory created successfully.");
        } else {
            log.info("Teachers directory already exists. Skipping creation.");
        }
    }

    private void createStorageDirectoriesIfNotExists() {
        try {
            Path storage = Paths.get(storagePath);
            Path chunkStorage = Paths.get(chunkStoragePath);

            if (!Files.exists(storage)) {
                Files.createDirectories(storage);
                log.info("Created storage directory: {}", storagePath);
            } else {
                log.info("Storage directory already exists: {}", storagePath);
            }

            if (!Files.exists(chunkStorage)) {
                Files.createDirectories(chunkStorage);
                log.info("Created chunk storage directory: {}", chunkStoragePath);
            } else {
                log.info("Chunk storage directory already exists: {}", chunkStoragePath);
            }
        } catch (IOException e) {
            log.error("Failed to create storage directories", e);
            throw new RuntimeException("Failed to create storage directories", e);
        }
    }
}
