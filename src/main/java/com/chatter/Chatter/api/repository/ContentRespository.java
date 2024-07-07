package com.chatter.Chatter.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chatter.Chatter.api.models.Content;
import com.chatter.Chatter.api.models.ContentID;

public interface ContentRespository extends JpaRepository<Content, ContentID> {

}
