package com.qlhs.server.restControl.Dai;

import com.qlhs.server.entity.TaiKhoan;
import com.qlhs.server.security.CustomUserDetails;
import com.qlhs.server.security.CustomUserDetailsService;
import com.qlhs.server.security.JwtUtil;
import com.qlhs.server.service.Dai.TaiKhoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/taikhoan")
public class TaiKhoanController {

    @Autowired
    private TaiKhoanService taiKhoanService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> loginData) {

        String username = loginData.get("tenDangNhap");
        String password = loginData.get("matKhau");

        try {
            // Xác thực với Spring Security
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Sai tài khoản hoặc mật khẩu");
        }

        // Nếu xác thực thành công, tạo Token
        final UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        final String jwt = jwtUtil.generateToken(userDetails);
        
        // Lấy thông tin user để trả về kèm
        TaiKhoan tk = ((CustomUserDetails) userDetails).getTaiKhoan();

        Map<String, Object> response = new HashMap<>();
        response.put("token", jwt);
        response.put("user", tk); // Trả về kèm thông tin user gốc để Client dễ dùng

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public List<TaiKhoan> getAllTaiKhoan() {
        return taiKhoanService.getAllTaiKhoan();
    }

    @GetMapping("/{tenDangNhap}")
    public ResponseEntity<TaiKhoan> getTaiKhoanByTenDangNhap(
            @PathVariable String tenDangNhap) {

        return taiKhoanService.getTaiKhoanByTenDangNhap(tenDangNhap)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/search")
    public List<TaiKhoan> searchTaiKhoan(
            @RequestParam String keyword) {

        return taiKhoanService.searchTaiKhoan(keyword);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')") // Ví dụ: Chỉ Admin mới được tạo tài khoản
    public TaiKhoan saveTaiKhoan(
            @RequestBody TaiKhoan taiKhoan) {

        return taiKhoanService.saveTaiKhoan(taiKhoan);
    }

    @DeleteMapping("/{tenDangNhap}")
    @PreAuthorize("hasRole('ADMIN')") // Chỉ Admin mới được xoá tài khoản
    public ResponseEntity<Void> deleteTaiKhoan(
            @PathVariable String tenDangNhap) {

        taiKhoanService.deleteTaiKhoan(tenDangNhap);

        return ResponseEntity.ok().build();
    }
}