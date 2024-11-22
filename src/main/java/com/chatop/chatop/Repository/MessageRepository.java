package com.chatop.chatop.Repository;

import com.chatop.chatop.Model.Message;
import org.springframework.data.repository.CrudRepository;

public interface MessageRepository extends CrudRepository<Message, Long> {
}
