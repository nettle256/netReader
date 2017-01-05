package netReader.Model;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Nettle on 2017/1/5.
 */
public interface UserRepository extends JpaRepository<User, Long> {
    public User findById(Long id);
    public User findByUsername(String username);
    public List<User> findAllById(List<Long> ids);
}
