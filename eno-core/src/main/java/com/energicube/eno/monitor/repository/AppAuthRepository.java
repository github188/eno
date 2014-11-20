package com.energicube.eno.monitor.repository;

import com.energicube.eno.monitor.model.AppAuth;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 应用定义数据操作代码
 *
 * @author CHENPING
 */
public interface AppAuthRepository extends JpaRepository<AppAuth, Long> {

    public List<AppAuth> findByGroupname(String groupname);

    public List<AppAuth> findByGroupnameAndAppcode(String groupname, String appcode);
}
