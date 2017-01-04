package netReader.Model;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Nettle on 2017/1/4.
 */
public interface ChapterRepository extends JpaRepository<Chapter, Long> {
    public List<Chapter> findAllById(List<Long> ids);
    public List<Chapter> findAllByNovelId(Long id);
}
