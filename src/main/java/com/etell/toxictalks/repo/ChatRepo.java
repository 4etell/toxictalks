package com.etell.toxictalks.repo;

import com.etell.toxictalks.domain.Chat;
import com.etell.toxictalks.projections.repo.ChatProjectionRepo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRepo extends JpaRepository<Chat, Long> {

    @Query(value = "SELECT id, name, creator_id, participant_id, attitude, status " +
            "FROM chat " +
            "WHERE creator_id = ?1 " +
            "OR participant_id = ?1", nativeQuery = true)
    List<ChatProjectionRepo> findUserChats(Long userId);


    @Query(value = "SELECT id, name, creator_id, participant_id, attitude " +
            "FROM chat " +
            "WHERE creator_id = ?1 " +
            "AND participant_id IS NULL " +
            "AND status = 'ACTIVE'", nativeQuery = true)
    List<ChatProjectionRepo> findUserTopics(Long userId);

    @Query(value = "SELECT id, name, creator_id, participant_id, attitude " +
            "FROM chat " +
            "WHERE creator_id != ?1 " +
            "AND participant_id IS NULL " +
            "AND status = 'ACTIVE' " +
            "ORDER BY id DESC " +
            "LIMIT 400", nativeQuery = true)
    List<ChatProjectionRepo> findAllTopics(Long userId);


    @Query(value = "SELECT * " +
            "FROM chat " +
            "WHERE creator_id = ?1 " +
            "OR participant_id = ?1", nativeQuery = true)
    List<Chat> findUserChatsAndTopics(Long userId);
}
