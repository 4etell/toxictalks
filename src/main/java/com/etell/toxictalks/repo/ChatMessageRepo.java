package com.etell.toxictalks.repo;

import com.etell.toxictalks.domain.ChatMessage;
import com.etell.toxictalks.projections.repo.ChatMessageProjectionRepo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatMessageRepo extends JpaRepository<ChatMessage, Long> {

    @Query(value = "SELECT id, text, author_id, update_date, chat_id, read " +
            "FROM chat_message " +
            "WHERE chat_id = ?1 " +
            "ORDER BY id", nativeQuery = true)
    List<ChatMessageProjectionRepo> findChatMessages(Long chatId);

    @Query(value = "SELECT * " +
            "FROM chat_message " +
            "WHERE chat_id = ?1 " +
            "AND read = false " +
            "AND author_id != ?2", nativeQuery = true)
    List<ChatMessage> findAllNotUserUnreadMessages(Long chatId, Long userId);

    @Query(value = "SELECT * " +
            "FROM chat_message " +
            "WHERE chat_id = ?1" , nativeQuery = true)
    List<ChatMessage> findAllByChatId(Long chatId);

}
