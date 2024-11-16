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

	@Query(value="SELECT receiver_username,sender_username,time,message_id,text,photourl FROM Message WHERE ((sender_username= :userone AND receiver_username= :usertwo) OR (sender_username= :usertwo AND receiver_username= :userone)) AND (time >= :beforeDate AND time <= :afterDate) ORDER BY time ASC ",nativeQuery = true)
	public Page<Message> getNewMessages(@Param("userone") String userone,@Param("usertwo") String usertwo,@Param("afterDate") Date afterDate,@Param("beforeDate") Date beforeDate, Pageable pa);
	
	
	@Query(value="SELECT receiver_username,sender_username,time,message_id,text,photourl FROM Message WHERE ((sender_username= :userone AND receiver_username= :usertwo) OR (sender_username= :usertwo AND receiver_username= :userone)) AND (time >= :beforeDate AND time <= :afterDate) ORDER BY time DESC ",nativeQuery = true)
	public Page<Message> getOldMessages(@Param("userone") String userone,@Param("usertwo") String usertwo,@Param("afterDate") Date afterDate,@Param("beforeDate") Date beforeDate, Pageable pa);
	
	
	@Query(value = "SELECT * FROM message WHERE message_id IN (SELECT DISTINCT ON (GREATEST(sender_username, receiver_username), LEAST(sender_username, receiver_username)) message_id FROM message WHERE (sender_username = :username OR receiver_username = :username) AND (time <= :afterDate AND time >= :beforeDate) ORDER BY GREATEST(sender_username, receiver_username), LEAST(sender_username, receiver_username),time DESC) ORDER BY time DESC", 
			countQuery = "SELECT COUNT(*) FROM (SELECT DISTINCT ON (GREATEST(sender_username, receiver_username), LEAST(sender_username, receiver_username)) message_id FROM message WHERE (sender_username = :username OR receiver_username = :username) AND (time < :afterDate AND time > :beforeDate)) AS count_query",	nativeQuery = true)
	public Page<Message> getLatestMessageFromAllFriends(@Param("username") String username, @Param("afterDate") Date afterDate, @Param("beforeDate") Date beforeDate,Pageable pa);

		
}
