package rw.rutakayile.k8sproject.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import rw.rutakayile.k8sproject.dto.CreateAdminDto;
import rw.rutakayile.k8sproject.exception.EntityNotFoundException;
import rw.rutakayile.k8sproject.model.Admin;
import rw.rutakayile.k8sproject.repository.AdminRepository;
import rw.rutakayile.k8sproject.service.AdminService;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminRepository adminRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Admin createAdmin(CreateAdminDto dto) {
        Admin admin = modelMapper.map(dto, Admin.class);
        admin.setPassword(passwordEncoder.encode(dto.getPassword()));
        return adminRepo.save(admin);
    }

    @Override
    public Admin findAdminByEmail(String email) {
        return adminRepo.findAdminByEmail(email).orElseThrow(() -> new EntityNotFoundException("Admin not found"));
    }
}
