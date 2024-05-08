package com.example.shopapp.controllers;

import com.example.shopapp.dtos.ProductDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {
    @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createProduct(@Valid @ModelAttribute ProductDTO productDTO, BindingResult result) {
        try {
            if (result.hasErrors()) {
                List<String> errorMessages = result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .collect(Collectors.toList());
                return ResponseEntity.badRequest().body(errorMessages);
            }
            List<MultipartFile> files = productDTO.getFiles();
            // đoạn này xử lý coi có file gửi về không, nếu không xử lý ở đây sẽ bị exception
            files = files == null ? new ArrayList<MultipartFile>() : files;
            for (MultipartFile file : files) {
                if (file.getSize() == 0) {
                    continue;
                }
                // Kiểm tra kích thước file và định dạng
                if (file.getSize() > 10 * 1024 * 1024) { // Kích thước > 10MB
                    return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE)
                            .body("File is too large! Maximum size is 10MB");
                }
                String contentType = file.getContentType();
                if (contentType == null || !contentType.startsWith("image/")) {
                    return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                            .body("File must be an image");
                }
                // Lưu file và cập nhật thumbnail trong DTO
                String filename = storeFile(file); // Thay thế hàm này với code của bạn để lưu file
                //lưu vào đối tượng product trong DB => sẽ làm sau
                //lưu vào bảng product_images
            }
            return ResponseEntity.ok("Product created successfully");
        } catch (
                Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    private String storeFile(MultipartFile file) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        // Thêm uuid vào tên file để đảm bảo file là duy nhất
        String uniqueFileName = UUID.randomUUID().toString() + "_" + fileName;
        // Đường dẫn đến thư mục lưu file
        Path uploadDir = Paths.get("uploads");
        // Kiẻm tra nếu thư mục không tồn tại thì tạo mới
        if (!Files.exists(uploadDir)) {
            Files.createDirectories(uploadDir);
        }
        //  Đường dẫn đầy đủ của file
        Path destination = Paths.get(uploadDir.toString(), uniqueFileName);
        // Copy file vào thư mục lưu trữ
        Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);

        return uniqueFileName;
    }

    @GetMapping("")
    public ResponseEntity<String> getProducts(@RequestParam int page, @RequestParam int limit) {
        return ResponseEntity.ok("get products");
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> getProductById(@PathVariable("id") Long productId) {
        return ResponseEntity.ok("product id " + productId);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body("delete product " + id);
    }
}
