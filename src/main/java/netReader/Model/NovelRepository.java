package netReader.Model;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Nettle on 2017/1/4.
 */
public interface NovelRepository extends JpaRepository<Novel, Long> {
    public List<Novel> findAllById(List<Long> ids);
    public List<Novel> findAllByDeleted(Boolean deleted);
}
