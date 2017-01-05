package netReader.Model;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Nettle on 2017/1/4.
 */
public interface ArticleRepository extends JpaRepository<Article, Long> {
    public List<Article> findAllById(List<Long> ids);
    public Article findById(Long id);
}
