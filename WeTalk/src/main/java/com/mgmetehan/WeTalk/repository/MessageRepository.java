package com.mgmetehan.WeTalk.repository;

import com.mgmetehan.WeTalk.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
}
