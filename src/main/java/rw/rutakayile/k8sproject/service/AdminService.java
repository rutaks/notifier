package rw.rutakayile.k8sproject.service;

import rw.rutakayile.k8sproject.dto.CreateAdminDto;
import rw.rutakayile.k8sproject.model.Admin;

public interface AdminService {
    public Admin createAdmin(CreateAdminDto dto);
    public Admin findAdminByEmail(String email);
}
