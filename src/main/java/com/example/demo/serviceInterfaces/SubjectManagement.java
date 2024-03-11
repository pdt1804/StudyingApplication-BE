package com.example.demo.serviceInterfaces;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.example.demo.entities.Blog;
import com.example.demo.entities.Comment;
import com.example.demo.entities.Reply;
import com.example.demo.entities.Subject;

public interface SubjectManagement {

	public List<Subject> getAllSubject(int groupID);
	
	public List<Subject> findSubjectByName(int groupID, String input);
	
	public void createSubject(int groupID, String nameSubject);

	public void updateSubject(int subjectID, String newNameSubject);
	
	public void sureToDeleteSubject(int groupID, int subjectID);
}
