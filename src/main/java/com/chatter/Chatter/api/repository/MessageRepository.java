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

	@Query(value="SELECT receiver_username,sender_username,time,message_id,text,photourl FROM Message WHERE (sender_username= :userone AND receiver_username= :usertwo) OR (sender_username= :usertwo AND receiver_username= :userone) ORDER BY time DESC ",nativeQuery = true)
	public Page<Message> getTopFiveMessages(@Param("userone") String userone,@Param("usertwo") String usertwo,Pageable pa);
	
	
	@Query("SELECT m FROM Message m WHERE m.sender= :senderid AND m.receiver= :receiverid AND m.time< :lastDate   ORDER BY m.time DESC ")
	public Page<Message> getOldMessages(@Param("senderid") String senderid,@Param("receiverid") String receiverid,@Param("lastDate") Date lastDate,Pageable pa);
	
	
	@Query(value = "SELECT * FROM message WHERE message_id IN (SELECT DISTINCT ON (GREATEST(sender_username, receiver_username), LEAST(sender_username, receiver_username)) message_id FROM message WHERE sender_username = :username OR receiver_username = :username ORDER BY GREATEST(sender_username, receiver_username), LEAST(sender_username, receiver_username), time DESC)", nativeQuery = true)
	public List<Message> getLatestMessageFromAllFriends(@Param("username") String username);













	
	
}
