package blog.com.Blog.Application.repository;

import blog.com.Blog.Application.model.LatestUpdates;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LatestUpdatesRepository extends JpaRepository<LatestUpdates,Long> {


    @Query(value = "SELECT * FROM latest_update WHERE is_live = 1 ORDER BY id DESC LIMIT 1", nativeQuery = true)
    LatestUpdates getTheLatestUpdateFromDB();


}
