package FunFictionUserProject.funFictionUser.repository;

import FunFictionUserProject.funFictionUser.view.Tags;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagsRepository extends JpaRepository<Tags, Long> {
    Tags findByTypeTags(String typeTag);
}
