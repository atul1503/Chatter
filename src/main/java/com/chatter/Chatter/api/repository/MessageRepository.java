package com.chatter.Chatter.api.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.chatter.Chatter.api.models.Message;


public interface MessageRepository extends JpaRepository<Message, Long> {

	@Query("SELECT m FROM Message m WHERE m.sender.username= :senderid AND m.receiver.username= :receiverid ORDER BY m.time DESC ")
	public Page<Message> getTopFiveMessages(@Param("senderid") String senderid,@Param("receiverid") String receiverid,Pageable pa);
	
	
	@Query("SELECT m FROM Message m WHERE m.sender= :senderid AND m.receiver= :receiverid AND m.time< :lastDate   ORDER BY m.time DESC ")
	public Page<Message> getOldMessages(@Param("senderid") String senderid,@Param("receiverid") String receiverid,@Param("lastDate") Date lastDate,Pageable pa);
	
	
	@Query(value = "SELECT * FROM message WHERE message_id IN (SELECT DISTINCT ON (GREATEST(sender_username, receiver_username), LEAST(sender_username, receiver_username)) message_id FROM message WHERE sender_username = :username OR receiver_username = :username ORDER BY GREATEST(sender_username, receiver_username), LEAST(sender_username, receiver_username), time DESC)", nativeQuery = true)
	public List<Message> getLatestMessageFromAllFriends(@Param("username") String username);













	
	
}
