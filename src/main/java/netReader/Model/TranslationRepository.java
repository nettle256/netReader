package netReader.Model;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Nettle on 2017/1/9.
 */
public interface TranslationRepository extends JpaRepository<Translation, Long> {
    public Translation findById(Long id);
    public List<Translation> findAllBySrcId(Long srcId);
    public List<Translation> findAllByAuthorId(Long authorId);
}
